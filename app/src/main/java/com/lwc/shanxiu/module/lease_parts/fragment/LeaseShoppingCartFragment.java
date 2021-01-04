package com.lwc.shanxiu.module.lease_parts.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.BaseFragment;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.lease_parts.activity.ConfirmLeaseOrderActivity;
import com.lwc.shanxiu.module.lease_parts.activity.LeaseGoodsDetailActivity;
import com.lwc.shanxiu.module.lease_parts.activity.LeaseGoodsListActivity;
import com.lwc.shanxiu.module.lease_parts.bean.LeaseSpecsBean;
import com.lwc.shanxiu.module.lease_parts.bean.ShopCarBean;
import com.lwc.shanxiu.module.lease_parts.widget.SelectGoodTypeDialog;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.wyh.slideAdapter.ItemBind;
import com.wyh.slideAdapter.ItemView;
import com.wyh.slideAdapter.SlideAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;


public class LeaseShoppingCartFragment extends BaseFragment {


    @BindView(R.id.txtActionbarTitle)
    TextView txtActionbarTitle;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.txtRight)
    TextView txtRight;
    @BindView(R.id.tv_goodSum)
    TextView tv_goodSum;
    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;
    @BindView(R.id.ll_bottom_button)
    LinearLayout ll_bottom_button;
    @BindView(R.id.ll_bottom_button02)
    LinearLayout ll_bottom_button02;
    @BindView(R.id.rl_title)
    RelativeLayout rl_title;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    @BindView(R.id.tv_SumMoney)
    TextView tv_SumMoney;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.cb_all_box)
    CheckBox cb_all_box;
    @BindView(R.id.cb_all_box02)
    CheckBox cb_all_box02;

    @BindView(R.id.tv_to_collect)
    TextView tv_to_collect;
    @BindView(R.id.tv_del_all)
    TextView tv_del_all;
    @BindView(R.id.tv_to_shopping)
    TextView tv_to_shopping;

    /**
     * 购物车数据
     */
    private List<ShopCarBean> shopCarBeans = new ArrayList<>();

    private SlideAdapter slideAdapter;
    private int goodsSum;
    private ItemBind<ShopCarBean> itemBind;
    private boolean isManager = false;
    private SelectGoodTypeDialog selectGoodTypeDialog;

    private SharedPreferencesUtils preferencesUtils;
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lease_shopping_cart, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int isShowBack = getArguments().getInt("isShowBack",0);
        if(isShowBack == 1){
            img_back.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rl_title.getLayoutParams();
            layoutParams.setMargins(0,30,0,0);
            rl_title.setLayoutParams(layoutParams);

            img_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                /*    LeaseGoodsDetailActivity leaseOrderDetailActivity = (LeaseGoodsDetailActivity) getActivity();
                    leaseOrderDetailActivity.removeFragment();*/
                    getActivity().finish();

                }
            });
        }else{
            img_back.setVisibility(View.VISIBLE);
        }
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected void lazyLoad() {

    }


    @Override
    public void init() {

        ImmersionBar.with(getActivity())
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true).init();

        txtActionbarTitle.setText("购物车");
        txtRight.setText("管理");
        txtRight.setVisibility(View.VISIBLE);
        initRecyclerView();

        txtRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtS = txtRight.getText().toString();
                if("管理".equals(txtS)){
                    txtRight.setText("完成");
                    isManager = true;
                    ll_bottom_button02.setVisibility(View.VISIBLE);
                    ll_bottom_button.setVisibility(View.GONE);
                }else{
                    txtRight.setText("管理");
                    isManager = false;
                    ll_bottom_button02.setVisibility(View.GONE);
                    ll_bottom_button.setVisibility(View.VISIBLE);
                }

            }
        });
        cb_all_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for(int i = 0;i < shopCarBeans.size(); i++){
                    if (isChecked){
                        shopCarBeans.get(i).setChecked(true);
                    }else{
                        shopCarBeans.get(i).setChecked(false);
                    }
                }
                //CalculationTotal();
                slideAdapter.notifyDataSetChanged();
            }
        });

        cb_all_box02.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for(int i = 0;i < shopCarBeans.size(); i++){
                    if (isChecked){
                        shopCarBeans.get(i).setChecked(true);
                    }else{
                        shopCarBeans.get(i).setChecked(false);
                    }
                }
                //CalculationTotal();
                slideAdapter.notifyDataSetChanged();
            }
        });

        preferencesUtils = SharedPreferencesUtils.getInstance(getActivity());
        user = preferencesUtils.loadObjectData(User.class);
    }

    @Override
    public void initEngines(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mBGARefreshLayout.beginRefreshing();
    }

    @Override
    public void getIntentData() {

    }

    @Override
    public void setListener() {

    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && getActivity() != null){
            ImmersionBar.with(getActivity())
                    .statusBarColor(R.color.white)
                    .statusBarDarkFont(true).init();

        }
    }

    @OnClick({R.id.tv_submit,R.id.tv_to_collect,R.id.ll_no_data,R.id.tv_del_all,R.id.tv_to_shopping})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.tv_submit:

                if(goodsSum==0){
                    ToastUtil.showToast(getContext(),"请选择的商品");
                    return;
                }

                 List<ShopCarBean> submitBeans = new ArrayList<>();
                    for(ShopCarBean shopCarBean : shopCarBeans){
                      if(shopCarBean.isChecked()){
                         submitBeans.add(shopCarBean);
                       }
                        }
                 Bundle bundle = new Bundle();
                 bundle.putString("shopCarBeans", JsonUtil.parserObjectToGson(submitBeans));
                 IntentUtil.gotoActivity(getContext(), ConfirmLeaseOrderActivity.class,bundle);
                break;
             case R.id.tv_to_collect:
                 StringBuilder sbCollect = new StringBuilder();

                 for(ShopCarBean shopCarBean : shopCarBeans){
                     if(shopCarBean.isChecked()){
                         sbCollect.append(shopCarBean.getCarId()+",");
                     }
                 }

                 if(TextUtils.isEmpty(sbCollect)){
                     ToastUtil.showToast(getActivity(),"请选择要收藏的商品");
                     return;
                 }else{
                     carGoodsToCell(sbCollect.toString());
                 }

                 break;
             case R.id.tv_del_all:
                 StringBuilder sbDel = new StringBuilder();

                 for(ShopCarBean shopCarBean : shopCarBeans){
                     if(shopCarBean.isChecked()){
                         sbDel.append(shopCarBean.getCarId()+",");
                     }
                 }

                 if(TextUtils.isEmpty(sbDel)){
                     ToastUtil.showToast(getActivity(),"请选择要删除的商品");
                     return;
                 }else{
                     delCarGoods(sbDel.toString());
                 }
                 break;
              case R.id.ll_no_data:
                  mBGARefreshLayout.beginRefreshing();
                 break;
              case R.id.tv_to_shopping:
                  IntentUtil.gotoActivity(getContext(), LeaseGoodsListActivity.class);
                 break;
        }
    }

    private void initRecyclerView() {
        BGARefreshLayoutUtils.initRefreshLayout(getContext(), mBGARefreshLayout, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
              //  shopCarBeans.clear();
                getCarGoods();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                return true;
            }
        });

        itemBind = new ItemBind<ShopCarBean>() {
            @Override
            public void onBind(final ItemView itemView, final ShopCarBean data, final int position) {

                TextView tv_reduce = itemView.getView(R.id.tv_reduce);
                TextView tv_add = itemView.getView(R.id.tv_add);
                TextView tv_spece = itemView.getView(R.id.tv_spece);
                TextView tv_prices = itemView.getView(R.id.tv_prices);
                TextView tv_title = itemView.getView(R.id.tv_title);
                ImageView iv_display = itemView.getView(R.id.iv_display);
                CheckBox cb_isAdd = itemView.getView(R.id.cb_isAdd);
                final EditText et_sum = itemView.getView(R.id.et_sum);
                String goodsPrice = Utils.getMoney(Utils.chu(data.getGoodsPrice(), "100"));
                String goodsPriceStr = "￥" + goodsPrice;

                SpannableStringBuilder showPrices = Utils.getSpannableStringBuilder(1,  goodsPrice.length()-2,getActivity().getResources().getColor(R.color.red_money), goodsPriceStr, 18);
                tv_prices.setText(showPrices);

                String goodsName = data.getGoodsName();
                //String goodsNameStr = "租赁  " + goodsName;
              //  SpannableStringBuilder showGoodsName = Utils.getSpannableStringBuilder(0, 2, getResources().getColor(R.color.transparent), goodsNameStr, 10, true);
                tv_title.setText(goodsName);


                ImageLoaderUtil.getInstance().displayFromNetDCircular(getContext(),data.getGoodsImg(),iv_display,R.drawable.image_default_picture);
               /* tv_spece.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getLeaseSpecs(data.getLeaseSpecsTypeId(),data.getGoodsPrice(),data.getGoodsImg(),data.getCarId());
                    }
                });*/

                et_sum.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        if(TextUtils.isEmpty(s)){
                            return;
                        }

                        int currentSum = Integer.parseInt(s.toString());
                        if(currentSum < 1 || currentSum > 200){
                            ToastUtil.showToast(getContext(),"请输入正确的数量(1~200)");
                            et_sum.setText("1");
                            return;
                        }

                        shopCarBeans.get(position).setGoodsNum(currentSum);

                        CalculationTotal();

                        Map<String,String> params = new HashMap<>();
                        params.put("car_id",data.getCarId());
                        params.put("goods_num",String.valueOf(currentSum));
                        updateCar(params);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                itemView.setText(R.id.tv_spece,data.getLeaseSpecs())
                      //  .setText(R.id.tv_prices,showPrices.toString())
                        .setText(R.id.et_sum,String.valueOf(data.getGoodsNum()));

                if(data.isChecked()){
                    cb_isAdd.setChecked(true);
                }else{
                    cb_isAdd.setChecked(false);
                }

                cb_isAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            shopCarBeans.get(position).setChecked(true);
                        }else{
                            shopCarBeans.get(position).setChecked(false);
                        }

                        CalculationTotal();
                    }
                });

                tv_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("goodId",data.getGoodsId());
                        IntentUtil.gotoActivity(getContext(), LeaseGoodsDetailActivity.class,bundle);
                    }
                });

                iv_display.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("goodId",data.getGoodsId());
                        IntentUtil.gotoActivity(getActivity(), LeaseGoodsDetailActivity.class,bundle);
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                             /*   Bundle bundle = new Bundle();
                                bundle.putString("goodId",data.getGoodsId());
                                IntentUtil.gotoActivity(getActivity(), LeaseGoodsDetailActivity.class,bundle);*/
                            }
                        })
                        .setOnClickListener(R.id.ll_reduce, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int currentSum = 0;
                                if(!TextUtils.isEmpty(et_sum.getText())){
                                    currentSum = Integer.parseInt(et_sum.getText().toString().trim());
                                }
                                if(currentSum == 1){
                                    ToastUtil.showToast(getContext(),"最少购买一件商品!");
                                    return;
                                }else{
                                    currentSum = currentSum - 1;
                                    shopCarBeans.get(position).setGoodsNum(currentSum);
                                    et_sum.setText(String.valueOf(currentSum));

                                    Map<String,String> params = new HashMap<>();
                                    params.put("car_id",data.getCarId());
                                    params.put("goods_num",String.valueOf(currentSum));
                                    updateCar(params);
                                }
                            }
                        })
                        .setOnClickListener(R.id.ll_add, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int currentSum = 0;
                                if(!TextUtils.isEmpty(et_sum.getText())){
                                    currentSum = Integer.parseInt(et_sum.getText().toString().trim());
                                }
                                currentSum = currentSum + 1;
                                shopCarBeans.get(position).setGoodsNum(currentSum);
                                et_sum.setText(String.valueOf(currentSum));
                                CalculationTotal();
                            }
                        })
                        .setOnClickListener(R.id.hide_delete, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //ToastUtil.showToast(getContext(),"删除");
                                delCarGoods(data.getCarId());
                            }
                        })
                        .setOnClickListener(R.id.add_favorites, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //ToastUtil.showToast(getContext(),"加入收藏夹");
                                carGoodsToCell(data.getCarId());
                            }
                        });
            }
        };
    }


    private void getCarGoods(){
        HttpRequestUtils.httpRequest(getActivity(), "查看购物车信息", RequestValue.GET_PARTSMANAGE_QUERYPARTSGOODSCAR, null, "GET", new HttpRequestUtils.ResponseListener() {

            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        shopCarBeans = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response,"data"),new TypeToken<ArrayList<ShopCarBean>>(){});
                        if(shopCarBeans != null && shopCarBeans.size() > 0){
                            tv_goodSum.setText("共计"+shopCarBeans.size()+"件宝贝");

                           // slideAdapter.loadMore(shopCarBeans);
                            ll_no_data.setVisibility(View.GONE);
                            ll_bottom_button.setVisibility(View.VISIBLE);
                            mBGARefreshLayout.setVisibility(View.VISIBLE);
                            tv_goodSum.setVisibility(View.VISIBLE);

                            slideAdapter = SlideAdapter.load(shopCarBeans)           //加载数据
                                    .item(R.layout.item_shopping_lease_good,0,0,R.layout.lease_hide_drag_item,0.40f)//指定布局
                                    .bind(itemBind)
                                    .padding(0)
                                    .into(recyclerView);  //填充到recyclerView中

                            txtRight.setVisibility(View.VISIBLE);
                            txtRight.setText("管理");
                            ll_bottom_button.setVisibility(View.VISIBLE);
                            ll_bottom_button02.setVisibility(View.GONE);
                        }else{
                            tv_goodSum.setText("共计0件宝贝");
                            ll_no_data.setVisibility(View.VISIBLE);
                            ll_bottom_button.setVisibility(View.GONE);
                            mBGARefreshLayout.setVisibility(View.GONE);
                            tv_goodSum.setVisibility(View.GONE);

                            txtRight.setVisibility(View.GONE);
                            ll_bottom_button.setVisibility(View.GONE);
                            ll_bottom_button02.setVisibility(View.GONE);
                        }
                        break;
                    default:
                        LLog.i("获取租赁推荐栏目" + common.getInfo());
                        break;
                }

                mBGARefreshLayout.endRefreshing();
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
                mBGARefreshLayout.endRefreshing();
            }
        });
    }

    /**
     * 删除
     * @param delId
     */
    private void delCarGoods(String delId){
        HttpRequestUtils.httpRequest(getActivity(), "删除购物车商品", RequestValue.GET_PARTSMANAGE_DELPARTSGOODSCAR+"?in_car_id="+delId, null, "POST", new HttpRequestUtils.ResponseListener() {

            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                      ToastUtil.showToast(getActivity(),common.getInfo());
                      mBGARefreshLayout.beginRefreshing();
                        break;
                    default:
                        ToastUtil.showToast(getActivity(),common.getInfo());
                        break;
                }

            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    /**
     * 移入收藏夹
     * @param delId
     */
    private void carGoodsToCell(String delId){
        HttpRequestUtils.httpRequest(getActivity(), "移入收藏夹", RequestValue.GET_PARTSMANAGE_PARTSGOODSCARTOCOLLE+"?in_car_id="+delId, null, "POST", new HttpRequestUtils.ResponseListener() {

            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        ToastUtil.showToast(getActivity(),common.getInfo());
                        mBGARefreshLayout.beginRefreshing();
                        break;
                    default:
                        ToastUtil.showToast(getActivity(),common.getInfo());
                        break;
                }

            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    @Deprecated
    private void getLeaseSpecs(String leaseSpacsTypeId, final String price, final String goodImg, final String carId){
        HttpRequestUtils.httpRequest(getActivity(), "获取租赁商品相关规格", RequestValue.GET_PARTSMANAGE_GETPARTSSPECSREVELENCE+"?lease_specs_type_id="+leaseSpacsTypeId, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        ArrayList<LeaseSpecsBean>  leaseSpecsBeans = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response,"data"),new TypeToken<ArrayList<LeaseSpecsBean>>(){});
                        if(leaseSpecsBeans == null){
                            return;
                        }
                        selectGoodTypeDialog = new SelectGoodTypeDialog(getContext(), new SelectGoodTypeDialog.CallBack() {
                            @Override
                            public void onSubmit(Object o) {

                                Map<String,String> params = (Map<String, String>) o;
                                params.put("car_id",carId);

                                selectGoodTypeDialog.dismiss();

                                //updateCar(params);

                            }
                        }, leaseSpecsBeans,price,goodImg);
                        selectGoodTypeDialog.show();
                        break;
                    default:
                        ToastUtil.showToast(getContext(),"获取数据失败!"+common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    private void updateCar(Map<String,String> param){
        HttpRequestUtils.httpRequest(getActivity(), "修改购物车", RequestValue.GET_PARTSMANAGE_MODPARTSGOODSCAR, param, "POST", new HttpRequestUtils.ResponseListener() {

            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        //ToastUtil.showToast(getActivity(),common.getInfo());
                       // mBGARefreshLayout.beginRefreshing();

                        break;
                    default:
                        ToastUtil.showToast(getActivity(),common.getInfo());
                        break;
                }

            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    private void CalculationTotal(){
        double totalMoney = 0;
        goodsSum = 0;
        
        for(int i = 0;i < shopCarBeans.size();i++){
            ShopCarBean shopCarBean = shopCarBeans.get(i);
            if(shopCarBean.isChecked()){
     /*           if("2".equals(shopCarBean.getPayType())){
                    totalMoney =totalMoney + Double.parseDouble(shopCarBean.getGoodsPrice()) * shopCarBean.getGoodsNum() * 3;
                    goodsSum = goodsSum + shopCarBean.getGoodsNum();
                }else{*/
                    totalMoney =totalMoney + Double.parseDouble(shopCarBean.getGoodsPrice()) * shopCarBean.getGoodsNum();
                    goodsSum = goodsSum + shopCarBean.getGoodsNum();
               // }

            }
        }
        tv_SumMoney.setText("￥"+Utils.getMoney(Utils.chu(String.valueOf(totalMoney),"100")));
        tv_submit.setText("结算("+ goodsSum +")");
        tv_goodSum.setText("共计"+shopCarBeans.size()+"件宝贝");
    }
}
