package com.lwc.shanxiu.module.lease_parts.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Dimension;
import android.support.v4.app.FragmentManager;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.activity.ImageBrowseActivity;
import com.lwc.shanxiu.activity.MainActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.ADInfo;
import com.lwc.shanxiu.module.lease_parts.bean.LeaseGoodBean;
import com.lwc.shanxiu.module.lease_parts.bean.LeaseSpecsBean;
import com.lwc.shanxiu.module.lease_parts.bean.ShopCarBean;
import com.lwc.shanxiu.module.lease_parts.fragment.LeaseShoppingCartFragment;
import com.lwc.shanxiu.module.lease_parts.inteface_callback.OnPageSelectCallBack;
import com.lwc.shanxiu.module.lease_parts.widget.GetPhoneDialog;
import com.lwc.shanxiu.module.lease_parts.widget.SelectGoodTypeDialog;
import com.lwc.shanxiu.utils.DisplayUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.ImageCycleView;
import com.lwc.shanxiu.widget.TagsLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author younge
 * @date 2018/12/19 0019
 * @email 2276559259@qq.com
 */
public class LeaseGoodsDetailActivity extends BaseActivity {

    @BindView(R.id.ad_view)
    ImageCycleView ad_view;
    @BindView(R.id.tv_add_cart)
    TextView tv_add_cart;
    @BindView(R.id.tv_prices)
    TextView tv_prices;
    @BindView(R.id.tl_tags)
    TagsLayout tl_tags;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_shopCart)
    TextView tv_shopCart;
    @BindView(R.id.imgRightTwo)
    ImageView imgRightTwo;
    @BindView(R.id.wv_content)
    WebView wv_content;
    @BindView(R.id.tv_collect)
    TextView tv_collect;

    @BindView(R.id.tv_good_detail)
    TextView tv_good_detail;
    @BindView(R.id.tv_lease_desc)
    TextView tv_lease_desc;
    @BindView(R.id.tv_lease_rule)
    TextView tv_lease_rule;
    @BindView(R.id.tv_good_detail_line)
    TextView tv_good_detail_line;
    @BindView(R.id.tv_lease_desc_line)
    TextView tv_lease_desc_line;
    @BindView(R.id.tv_lease_rule_line)
    TextView tv_lease_rule_line;

    @BindView(R.id.tv_good_detail_2)
    TextView tv_good_detail_2;
    @BindView(R.id.tv_lease_desc_2)
    TextView tv_lease_desc_2;
    @BindView(R.id.tv_lease_rule_2)
    TextView tv_lease_rule_2;
    @BindView(R.id.tv_good_detail_line_2)
    TextView tv_good_detail_line_2;
    @BindView(R.id.tv_lease_desc_line_2)
    TextView tv_lease_desc_line_2;
    @BindView(R.id.tv_lease_rule_line_2)
    TextView tv_lease_rule_line_2;

    @BindView(R.id.sv_scroll)
    ScrollView sv_scroll;
    @BindView(R.id.ll_title_bar)
    LinearLayout ll_title_bar;
    @BindView(R.id.ll_title_line)
    LinearLayout ll_title_line;
    @BindView(R.id.ll_title_2)
    RelativeLayout ll_title_2;
    @BindView(R.id.ll_title_bar_2)
    LinearLayout ll_title_bar_2;
    @BindView(R.id.ll_title_line_2)
    LinearLayout ll_title_line_2;

    @BindView(R.id.tv_msg)
    TextView tv_msg;

    @BindView(R.id.tv_send_goods)
    TextView tv_send_goods;
    @BindView(R.id.tv_ensuer)
    TextView tv_ensuer;

    @BindView(R.id.fragment_container)
    FrameLayout fragment_container;
    private LeaseShoppingCartFragment leaseShoppingCartFragment;
    private FragmentManager fragmentManager;

    private SelectGoodTypeDialog selectGoodTypeDialog;
    private ArrayList<ADInfo> infos;

    private LeaseGoodBean leaseGoodBean;
    private List<LeaseSpecsBean> leaseSpecsBeans;
    private String goodId;
    private String[] headerImgs;




    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_lease_good_detail;
    }

    @Override
    protected void findViews() {
    }

    @Override
    protected void init() {

        int screenWidth = DisplayUtil.getScreenWidth(this);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ad_view.getLayoutParams();
        layoutParams.height = screenWidth;
        ad_view.setLayoutParams(layoutParams);

        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(false)
                .init();

        leaseShoppingCartFragment = new LeaseShoppingCartFragment();
        fragmentManager = getSupportFragmentManager();
    }

    @OnClick({R.id.tv_add_cart,R.id.tv_shopCart,R.id.imgRight,R.id.imgRightTwo,R.id.tv_customer,R.id.tv_collect,R.id.tv_right_lease,R.id.tv_good_detail,R.id.tv_lease_desc,
            R.id.tv_lease_rule,R.id.tv_good_detail_2,R.id.tv_lease_desc_2,R.id.tv_lease_rule_2})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.tv_add_cart:
                if(leaseSpecsBeans == null){
                    return;
                }
              selectGoodTypeDialog = new SelectGoodTypeDialog(LeaseGoodsDetailActivity.this, new SelectGoodTypeDialog.CallBack() {
                    @Override
                    public void onSubmit(Object o) {

                        Map<String,String> params = (Map<String, String>) o;

                        addToCar(params);
                        //IntentUtil.gotoActivity(LeaseGoodsDetailActivity.this,ConfirmLeaseOrderActivity.class);
                    }
                }, leaseSpecsBeans, leaseGoodBean.getGoodsPrice(),leaseGoodBean.getGoodsImg());
                selectGoodTypeDialog.show();
                break;
            case R.id.tv_right_lease:
                if(leaseSpecsBeans == null){
                    return;
                }
                selectGoodTypeDialog = new SelectGoodTypeDialog(LeaseGoodsDetailActivity.this, new SelectGoodTypeDialog.CallBack() {
                    @Override
                    public void onSubmit(Object o) {

                        selectGoodTypeDialog.dismiss();

                        Map<String,String> params = (Map<String, String>) o;
                        params.put("goodsName",leaseGoodBean.getGoodsName());
                        params.put("goodsPrice",leaseGoodBean.getGoodsName());

                        List<ShopCarBean> shopCarBeans = new ArrayList<>();

                        ShopCarBean shopCarBean = new ShopCarBean();
                        shopCarBean.setGoodsId(leaseGoodBean.getGoodsId());
                        shopCarBean.setGoodsName(leaseGoodBean.getGoodsName());
                        shopCarBean.setLeaseSpecs(params.get("leaseSpace"));
                        shopCarBean.setGoodsNum(Integer.parseInt(params.get("goods_num")));
                        shopCarBean.setPayType(params.get("pay_type"));
                        shopCarBean.setLeaseMonTime(params.get("lease_mon_time"));
                        shopCarBean.setGoodsImg(leaseGoodBean.getGoodsImg());
                        shopCarBean.setGoodsPrice(leaseGoodBean.getGoodsPrice());
                        shopCarBeans.add(shopCarBean);

                        Bundle bundle = new Bundle();
                        bundle.putString("shopCarBeans", JsonUtil.parserObjectToGson(shopCarBeans));
                        IntentUtil.gotoActivity(LeaseGoodsDetailActivity.this,ConfirmLeaseOrderActivity.class,bundle);
                    }
                }, leaseSpecsBeans, leaseGoodBean.getGoodsPrice(),leaseGoodBean.getGoodsImg());
                selectGoodTypeDialog.show();
                break;

            case R.id.tv_shopCart:
         /*       Bundle bundle = new Bundle();
                bundle.putInt("startType",2);
                IntentUtil.gotoActivity(this, LeaseHomeActivity.class,bundle);*/
                Bundle bundle = new Bundle();
                bundle.putInt("isShowBack",1);
                leaseShoppingCartFragment.setArguments(bundle);
                fragment_container.setVisibility(View.VISIBLE);
                fragmentManager    //
                        .beginTransaction()
                        .add(R.id.fragment_container,leaseShoppingCartFragment)   // 此处的R.id.fragment_container是要盛放fragment的父容器
                        .commit();
                break;
            case R.id.imgRight:
                IntentUtil.gotoActivity(this, ShareType2Activity.class);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.imgRightTwo:
              //  PopupWindowUtil.showHeaderPopupWindow(LeaseGoodsDetailActivity.this,imgRightTwo,leaseShoppingCartFragment,fragment_container,fragmentManager);
                break;
            case R.id.tv_collect:
                if("1".equals(leaseGoodBean.getIsCollection())){
                    delGood();
                }else{
                    addToCollect();
                }
                break;
             case R.id.tv_customer:
                 GetPhoneDialog picturePopupWindow = new GetPhoneDialog(this, new GetPhoneDialog.CallBack() {
                     @Override
                     public void twoClick() {
                         Utils.lxkf(LeaseGoodsDetailActivity.this, "400-881-0769");
                     }
                     @Override
                     public void cancelCallback() {
                     }
                 }, "", "呼叫 400-881-0769");
                 picturePopupWindow.show();
                break;
            case R.id.tv_good_detail:
            case R.id.tv_good_detail_2:
                showGoodDetail(0);
                break;
            case R.id.tv_lease_desc:
            case R.id.tv_lease_desc_2:
                showGoodDetail(1);
                break;
             case R.id.tv_lease_rule:
             case R.id.tv_lease_rule_2:
                showGoodDetail(2);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void removeFragment(){
        getSupportFragmentManager()    //
                .beginTransaction()
                .remove(leaseShoppingCartFragment)
                .commit();
        fragment_container.setVisibility(View.GONE);
    }

    @Override
    protected void initGetData() {

        goodId = getIntent().getStringExtra("goodId");
        
        HttpRequestUtils.httpRequest(this, "获取租赁商品详情", RequestValue.PARTSMANAGE_GETPARTSGOOD+"?goods_id="+goodId, null, "GET", new HttpRequestUtils.ResponseListener() {

            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        leaseGoodBean = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response,"data"),LeaseGoodBean.class);
                            if(leaseGoodBean != null) {


                                if (!TextUtils.isEmpty(leaseGoodBean.getGoodsDetailImg())) {
                                    headerImgs = leaseGoodBean.getGoodsDetailImg().split(",");
                                    if (headerImgs != null && headerImgs.length > 0) {
                                        infos = new ArrayList<>();
                                        for (String url : headerImgs) {
                                            infos.add(new ADInfo(url, url));
                                        }
                                        setWheelPic();
                                    }
                                }

                                String goodsPrice = Utils.chu(leaseGoodBean.getGoodsPrice(), "100");
                                String goodsPriceStr = "￥" + goodsPrice + "/月";
                                SpannableStringBuilder showPrices = Utils.getSpannableStringBuilder(1, goodsPrice.length()+1, LeaseGoodsDetailActivity.this.getResources().getColor(R.color.red_money), goodsPriceStr, 24, true);
                                tv_prices.setText(showPrices);

                                tv_send_goods.setText(leaseGoodBean.getGoodsDelivery());
                                tv_ensuer.setText(leaseGoodBean.getGoodsSecurity());

                                String lables = leaseGoodBean.getLabelName();
                                if (!TextUtils.isEmpty(lables)) {
                                    String[] tags = lables.split(",");
                                    if (tags.length > 0) {
                                        tl_tags.setVisibility(View.VISIBLE);
                                        //String[] tags =item.getLabelName().split(",");
                                        tl_tags.removeAllViews();
                                        for (int i = 0; i < tags.length; i++) {
                                            if (i > 3) {
                                                break;
                                            }
                                            TextView textView = new TextView(LeaseGoodsDetailActivity.this);
                                            textView.setText(tags[i]);
                                            textView.setTextColor(Color.parseColor("#ff3a3a"));
                                            textView.setTextSize(Dimension.SP, 9);
                                            textView.setGravity(Gravity.CENTER);
                                            textView.setPadding(5, 0, 5, 0);
                                            textView.setBackgroundResource(R.drawable.round_square_red_line);
                                            textView.setText(tags[i]);
                                            tl_tags.addView(textView);
                                        }
                                    } else {
                                        tl_tags.setVisibility(View.GONE);
                                    }
                                } else {
                                    //ToastUtil.showToast(LeaseGoodsDetailActivity.this, "获取数据失败!");
                                }

                                String goodsName = leaseGoodBean.getGoodsName();
                                String goodsNameStr = "租赁  " + goodsName;
                                SpannableStringBuilder showGoodsName = Utils.getSpannableStringBuilder(0, 2, LeaseGoodsDetailActivity.this.getResources().getColor(R.color.transparent), goodsNameStr, 10, true);
                               tv_title.setText(showGoodsName);


                                if("1".equals(leaseGoodBean.getIsCollection())){
                                    Drawable drawable = getResources().getDrawable(R.drawable.ic_detail_cellected);
                                    drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                                    tv_collect.setCompoundDrawables(null,drawable,null,null);
                                }else{
                                    Drawable drawable = getResources().getDrawable(R.drawable.ic_detail_collect);
                                    drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                                    tv_collect.setCompoundDrawables(null,drawable,null,null);
                                }

                                showGoodDetail(0);

                                //获取可选商品规格
                                getLeaseSpecs();
                            }
                        break;
                    default:
                        ToastUtil.showToast(LeaseGoodsDetailActivity.this,"获取数据失败!"+common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });

        getCarGoods();
    }


    private void showGoodDetail(int type) {
        String htmlContent = getNewContent(leaseGoodBean.getGoodsDetail());

        switch (type){
            case 0:
                wv_content.loadData(htmlContent,"text/html","UTF-8");
                tv_good_detail_line.setVisibility(View.VISIBLE);
                tv_lease_desc_line.setVisibility(View.INVISIBLE);
                tv_lease_rule_line.setVisibility(View.INVISIBLE);
                tv_good_detail.setTextColor(getResources().getColor(R.color.black));
                tv_good_detail.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tv_lease_desc.setTextColor(getResources().getColor(R.color.color_33));
                tv_lease_desc.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tv_lease_rule.setTextColor(getResources().getColor(R.color.color_33));
                tv_lease_rule.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                tv_good_detail_line_2.setVisibility(View.VISIBLE);
                tv_lease_desc_line_2.setVisibility(View.INVISIBLE);
                tv_lease_rule_line_2.setVisibility(View.INVISIBLE);
                tv_good_detail_2.setTextColor(getResources().getColor(R.color.black));
                tv_good_detail_2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tv_lease_desc_2.setTextColor(getResources().getColor(R.color.color_33));
                tv_lease_desc_2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tv_lease_rule_2.setTextColor(getResources().getColor(R.color.color_33));
                tv_lease_rule_2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                break;
            case 1:
                wv_content.loadData(leaseGoodBean.getGoodsInstructions(),"text/html","UTF-8");
                wv_content.setBackgroundColor(Color.parseColor("#EFF1F5"));
                tv_good_detail_line.setVisibility(View.INVISIBLE);
                tv_lease_desc_line.setVisibility(View.VISIBLE);
                tv_lease_rule_line.setVisibility(View.INVISIBLE);
                tv_good_detail.setTextColor(getResources().getColor(R.color.color_33));
                tv_good_detail.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tv_lease_desc.setTextColor(getResources().getColor(R.color.black));
                tv_lease_desc.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tv_lease_rule.setTextColor(getResources().getColor(R.color.color_33));
                tv_lease_rule.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                tv_good_detail_line_2.setVisibility(View.INVISIBLE);
                tv_lease_desc_line_2.setVisibility(View.VISIBLE);
                tv_lease_rule_line_2.setVisibility(View.INVISIBLE);
                tv_good_detail_2.setTextColor(getResources().getColor(R.color.color_33));
                tv_good_detail_2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tv_lease_desc_2.setTextColor(getResources().getColor(R.color.black));
                tv_lease_desc_2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tv_lease_rule_2.setTextColor(getResources().getColor(R.color.color_33));
                tv_lease_rule_2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                break;
            case 2:
                wv_content.loadData(leaseGoodBean.getGoodsRules(),"text/html","UTF-8");
                wv_content.setBackgroundColor(Color.parseColor("#EFF1F5"));
                tv_good_detail_line.setVisibility(View.INVISIBLE);
                tv_lease_desc_line.setVisibility(View.INVISIBLE);
                tv_lease_rule_line.setVisibility(View.VISIBLE);
                tv_good_detail.setTextColor(getResources().getColor(R.color.color_33));
                tv_good_detail.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tv_lease_desc.setTextColor(getResources().getColor(R.color.color_33));
                tv_lease_desc.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tv_lease_rule.setTextColor(getResources().getColor(R.color.black));
                tv_lease_rule.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                tv_good_detail_line_2.setVisibility(View.INVISIBLE);
                tv_lease_desc_line_2.setVisibility(View.INVISIBLE);
                tv_lease_rule_line_2.setVisibility(View.VISIBLE);
                tv_good_detail_2.setTextColor(getResources().getColor(R.color.color_33));
                tv_good_detail_2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tv_lease_desc_2.setTextColor(getResources().getColor(R.color.color_33));
                tv_lease_desc_2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tv_lease_rule_2.setTextColor(getResources().getColor(R.color.black));
                tv_lease_rule_2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                break;
        }
    }

    int[] location = new int[2];
    int[] location2 = new int[2];

    @Override
    protected void widgetListener() {

       /* sv_scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.d("滚动的距离:", location[0] + "=======" + location2[0]);

                ll_title_bar.getLocationOnScreen(location);
                ll_title_bar_2.getLocationOnScreen(location2);
                if(location[0] > location2[0]){
                    ll_title_bar_2.setVisibility(View.VISIBLE);
                    ll_title_line_2.setVisibility(View.VISIBLE);
                }else{
                    ll_title_bar_2.setVisibility(View.GONE);
                    ll_title_line_2.setVisibility(View.GONE);
                }
                return false;
            }

        });*/

        sv_scroll.setOnTouchListener(new View.OnTouchListener() {
            private int lastY = 0;
            private int touchEventId = -9983761;
            Handler handler = new Handler() {
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == touchEventId) {
                        if (lastY != sv_scroll.getScrollY()) {
                            //scrollview一直在滚动，会触发
                            handler.sendMessageDelayed(
                                    handler.obtainMessage(touchEventId, sv_scroll), 5);
                            lastY = sv_scroll.getScrollY();
                            ll_title_bar.getLocationOnScreen(location);
                            ll_title_2.getLocationOnScreen(location2);
                            //动的到静的位置时，静的显示。动的实际上还是网上滚动，但我们看到的是静止的那个
                            if (location[1] <= location2[1]) {
                                ll_title_2.setVisibility(View.VISIBLE);
                            } else {
                                //静止的隐藏了
                                ll_title_2.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            };

            public boolean onTouch(View v, MotionEvent event) {
                //必须两个都搞上，不然会有瑕疵
                //没有这段，手指按住拖动的时候没有效果
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    handler.sendMessageDelayed(
                            handler.obtainMessage(touchEventId, v), 5);
                }
                //没有这段，手指松开scroll继续滚动的时候，没有效果
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    handler.sendMessageDelayed(
                            handler.obtainMessage(touchEventId, v), 5);
                }
                return false;
            }
        });

    }


    /**
     * 设置轮播图
     */
    public void setWheelPic(){
        ad_view.setImageResources(infos, mAdCycleViewListener, new OnPageSelectCallBack() {
            @Override
            public void onPageSelect(int position) {
                //Log.d("下标为:",String.valueOf(position));
            }
        });
        ad_view.startImageCycle();
    }

    /**
     * 轮播图点击事件
     */
    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if(infos != null){
                ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(headerImgs));
                Intent intent = new Intent(LeaseGoodsDetailActivity.this, ImageBrowseActivity.class);
                intent.putExtra("index", position);
                intent.putStringArrayListExtra("list", arrayList);
                startActivity(intent);
            }

        }

        @Override
        public void displayImage(final String imageURL, final ImageView imageView) {
            ImageLoaderUtil.getInstance().displayFromNetD(LeaseGoodsDetailActivity.this, imageURL, imageView,R.drawable.image_default_picture);// 使用ImageLoader对图片进行加装！
        }


    };

    private void getLeaseSpecs(){
        HttpRequestUtils.httpRequest(this, "获取租赁商品相关规格", RequestValue.GET_PARTSMANAGE_GETPARTSSPECSREVELENCE+"?lease_specs_type_id="+leaseGoodBean.getLeaseSpecsTypeId(), null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        leaseSpecsBeans = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response,"data"),new TypeToken<ArrayList<LeaseSpecsBean>>(){});
                        break;
                    default:
                        ToastUtil.showToast(LeaseGoodsDetailActivity.this,"获取数据失败!"+common.getInfo());
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
     * 加入购物车
     */
    private void addToCar( Map<String,String> params){
        HttpRequestUtils.httpRequest(this, "加入购物车", RequestValue.PARTSMANAGE_ADDPARTSGOODSCAR, params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        selectGoodTypeDialog.dismiss();
                        ToastUtil.showToast(LeaseGoodsDetailActivity.this,"加入购物车成功");
                        int carSum = Integer.parseInt(tv_msg.getText().toString().trim());
                        tv_msg.setVisibility(View.VISIBLE);
                        tv_msg.setText(String.valueOf(carSum+1));
                        break;
                    default:
                        ToastUtil.showToast(LeaseGoodsDetailActivity.this,common.getInfo());
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
     * 获取购物车数量
     */
    private void getCarGoods(){
        HttpRequestUtils.httpRequest(LeaseGoodsDetailActivity.this, "查看购物车信息", RequestValue.GET_PARTSMANAGE_QUERYPARTSGOODSCAR, null, "GET", new HttpRequestUtils.ResponseListener() {

            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        List<ShopCarBean> shopCarBeans = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response,"data"),new TypeToken<ArrayList<ShopCarBean>>(){});
                        if(shopCarBeans != null && shopCarBeans.size() > 0){
                            tv_msg.setVisibility(View.VISIBLE);
                            tv_msg.setText(String.valueOf(shopCarBeans.size()));
                        }else{
                            tv_msg.setVisibility(View.GONE);
                        }
                        break;
                    default:
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
     * 加入收藏夹
     */
    private void addToCollect(){
        if(leaseGoodBean == null){
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("goods_id",leaseGoodBean.getGoodsId());
        HttpRequestUtils.httpRequest(this, "加入收藏夹", RequestValue.PARTSMANAGE_ADDPARTSGOODSCOLLECTION, params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        String collectId = JsonUtil.getGsonValueByKey(response,"data");
                        if(!TextUtils.isEmpty(collectId)){
                            leaseGoodBean.setCollectionId(collectId);
                        }
                        leaseGoodBean.setIsCollection("1");
                        Drawable drawable = getResources().getDrawable(R.drawable.ic_detail_cellected);
                        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                        tv_collect.setCompoundDrawables(null,drawable,null,null);

                        ToastUtil.showToast(LeaseGoodsDetailActivity.this,"收藏成功!");
                        break;
                    default:
                        ToastUtil.showToast(LeaseGoodsDetailActivity.this,"加入失败"+common.getInfo());
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
     * 移除收藏夹
     */
    private void delGood(){

        Map<String,String> params = new HashMap<>();
        params.put("in_collection_id",leaseGoodBean.getCollectionId());
        HttpRequestUtils.httpRequest(LeaseGoodsDetailActivity.this, "删除收藏",RequestValue.PARTSMANAGE_DELPARTSGOODSCOLLECTION, params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);


                ToastUtil.showToast(LeaseGoodsDetailActivity.this,"取消收藏成功!");
                Drawable drawable = getResources().getDrawable(R.drawable.ic_detail_collect);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                tv_collect.setCompoundDrawables(null,drawable,null,null);
                leaseGoodBean.setIsCollection("0");
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    /**
     * 将html文本内容中包含img标签的图片，宽度变为屏幕宽度，高度根据宽度比例自适应
     **/
    public String getNewContent(String htmltext) {
        try {
            Document doc = Jsoup.parse(htmltext);

    /*        Elements head = doc.getElementsByTag("head");
            for (Element element : head) {
                element.append("<meta name=\"viewport\" content=\"target-densitydpi=device-dpi, width=device-width, initial-scale=1.0, user-scalable=no\" />");
            }*/


            Elements elements = doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("style", "width: 100%; height: auto;");
            }
            Elements videos = doc.getElementsByTag("video");
            for (Element element : videos) {
                element.attr("style", "width: 100%; height: auto;");
            }
            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }
}
