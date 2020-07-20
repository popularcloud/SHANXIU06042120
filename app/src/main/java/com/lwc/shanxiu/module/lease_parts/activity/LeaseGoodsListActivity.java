package com.lwc.shanxiu.module.lease_parts.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.lease_parts.adapter.LeaseGoodListAdapter;
import com.lwc.shanxiu.module.lease_parts.bean.LeaseGoodBean;
import com.lwc.shanxiu.module.lease_parts.fragment.LeaseFilterFragment;
import com.lwc.shanxiu.module.lease_parts.fragment.LeaseShoppingCartFragment;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @author younge
 * @date 2018/12/19 0019
 * @email 2276559259@qq.com
 */
public class LeaseGoodsListActivity extends BaseActivity {

    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
/*    @BindView(R.id.tctTip)
    TextView tctTip;*/
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.tv_filter)
    TextView tv_filter;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;
    @BindView(R.id.iv_right)
    ImageView iv_right;
    @BindView(R.id.tv_recommend)
    TextView tv_recommend;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mDrawerContent;

    private LeaseGoodListAdapter leaseGoodListAdapter;
    private String searchText = "";
    private String typeId = "";
    private int currentPage = 1;
    private String attributeSeach = "";
    private int priceOrderStatus = 0; //0正常 1顺序  2倒叙
    private FragmentManager fragmentManager;
    private LeaseFilterFragment filterfragment;

    private ArrayList<LeaseGoodBean> leaseGoodBeans = new ArrayList<>();
    private String typeDetailId;

    @BindView(R.id.fragment_container)
    FrameLayout fragment_container;

    private LeaseShoppingCartFragment leaseShoppingCartFragment;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_lease_good_list;
    }

    @Override
    protected void findViews() {
        BGARefreshLayoutUtils.initRefreshLayout(this, mBGARefreshLayout, false);
        //初始化侧边筛选布局
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerContent = (FrameLayout) findViewById(R.id.drawer_content);

        leaseShoppingCartFragment = new LeaseShoppingCartFragment();
        fragmentManager = getSupportFragmentManager();

        leaseGoodListAdapter = new LeaseGoodListAdapter(this,leaseGoodBeans,R.layout.item_lease_good_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(LeaseGoodsListActivity.this));
        recyclerView.setAdapter(leaseGoodListAdapter);
        leaseGoodListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("goodId",leaseGoodListAdapter.getItem(position).getGoodsId());
                IntentUtil.gotoActivity(LeaseGoodsListActivity.this,LeaseGoodsDetailActivity.class,bundle);
            }
        });


        mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                currentPage = 1;
                searchData();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                currentPage ++;
                searchData();
                return true;
            }
        });


        Intent myIntent = getIntent();
        typeId = myIntent.getStringExtra("typeId");
        typeDetailId = myIntent.getStringExtra("typeDetailId");
        searchText = myIntent.getStringExtra("searchText");

        if(TextUtils.isEmpty(searchText)){
            mBGARefreshLayout.beginRefreshing();
        }else{
            et_search.setText(searchText);
        }
    }

    @Override
    protected void init() {
  /*      iv_right.setImageResource(R.drawable.ic_more_black);
        iv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PopupWindowUtil.showHeaderPopupWindow(LeaseGoodsListActivity.this,iv_right,);
               // PopupWindowUtil.showHeaderPopupWindow(LeaseGoodsListActivity.this,tv_msg,leaseShoppingCartFragment,fragment_container,fragmentManager);
            }
        });*/
    }

    @OnClick({R.id.tv_filter,R.id.tv_search,R.id.tv_price,R.id.ll_no_data,R.id.img_back})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.tv_filter:
                mDrawerLayout.openDrawer(mDrawerContent);
                break;
            case R.id.tv_price:
                Drawable drawable = null;
                switch (priceOrderStatus){
                    case 0:
                        drawable = getResources().getDrawable(R.drawable.ic_price_up_new);
                        priceOrderStatus = 1;
                        break;
                    case 1:
                        drawable = getResources().getDrawable(R.drawable.ic_price_down_new);
                        priceOrderStatus = 2;
                        break;
                    case 2:
                        drawable = getResources().getDrawable(R.drawable.ic_price_normal_new);
                        priceOrderStatus = 0;
                        break;
                }
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                tv_price.setCompoundDrawables(null,null,drawable,null);
                mBGARefreshLayout.beginRefreshing();
                break;
             case R.id.ll_no_data:
                 ll_no_data.setVisibility(View.GONE);
                 recyclerView.setVisibility(View.VISIBLE);
                 searchText = "";
                 et_search.setText("");
                 searchData();
                 break;
             case R.id.img_back:
                 finish();
                 break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mBGARefreshLayout.beginRefreshing();

        //获取未读租赁消息
       // MsgReadUtil.hasMessage(LeaseGoodsListActivity.this,tv_msg);
    }

    @Override
    protected void initGetData() {
    }
    
    private void searchData(){

        Log.d("调用方法","searchData");
        Map<String,String> params = new HashMap<>();
        if(!TextUtils.isEmpty(searchText) && !"null".equals(searchText)){
            params.put("wd",searchText);
        }

        if(priceOrderStatus != 0 || !TextUtils.isEmpty(attributeSeach)){
            tv_recommend.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            tv_recommend.setTypeface(Typeface.DEFAULT);
        }

        if(!TextUtils.isEmpty(typeId)){
            params.put("type_id",typeId);
        }

        if(!TextUtils.isEmpty(typeDetailId)){
            params.put("type_detail_id",typeDetailId);
        }

        params.put("regexp_attribute_name",attributeSeach);
        params.put("sortord",String.valueOf(priceOrderStatus));
        params.put("curPage",String.valueOf(currentPage));

        HttpRequestUtils.httpRequest(this, "获取租赁商品数据", RequestValue.GET_PARTSMANAGE_GETPARTSGOODS, params, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        leaseGoodListAdapter.clear();
                        String dataList = JsonUtil.getGsonValueByKey(response,"data");
                        String datas = JsonUtil.getGsonValueByKey(dataList,"data");
                        leaseGoodBeans = JsonUtil.parserGsonToArray((String) datas, new TypeToken<ArrayList<LeaseGoodBean>>() {});
                            if(leaseGoodBeans != null && leaseGoodBeans.size() > 0){
                                ll_no_data.setVisibility(View.GONE);
                                mBGARefreshLayout.setVisibility(View.VISIBLE);
                                if(currentPage == 1){
                                    leaseGoodListAdapter.replaceAll(leaseGoodBeans);
                                    initSearchCondition(leaseGoodBeans.get(0).getTypeId(),leaseGoodBeans.get(0).getTypeDetailId());

                                }else{
                                    leaseGoodListAdapter.addAll(leaseGoodBeans);
                                }

                            }else{
                                if(currentPage == 1){
                                    initSearchCondition(null,null);
                                    ll_no_data.setVisibility(View.VISIBLE);
                                    mBGARefreshLayout.setVisibility(View.GONE);
                                }
                            }

                        break;
                    default:
                        LLog.i("获取租赁商品数据" + common.getInfo());
                        break;
                }
                if(currentPage == 1){
                    mBGARefreshLayout.endRefreshing();
                }else{
                    mBGARefreshLayout.endLoadingMore();
                }

            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
                if(currentPage == 1){
                    mBGARefreshLayout.endRefreshing();
                }else{
                    mBGARefreshLayout.endLoadingMore();
                }
            }
        });
    }

    private void initSearchCondition(String mTypeId,String mTypeDetailId){

       // Intent myIntent = getIntent();
   /*     if(mTypeId != null){//获取第一条数据的类型id
            typeId = mTypeId;
            typeDetailId = mTypeDetailId;
        }*/

        filterfragment = new LeaseFilterFragment();
        final Bundle bundle = new Bundle();

        if(!TextUtils.isEmpty(mTypeDetailId)){
            bundle.putString("typeDetailId",mTypeDetailId);
        }

        if(!TextUtils.isEmpty(mTypeId)){
            bundle.putString("typeId",mTypeId);
        }

        filterfragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.drawer_content, filterfragment).commit();
    }

    @Override
    protected void widgetListener() {
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchText = s.toString();
                currentPage = 1;
                searchData();
            }
        });
    }


    public void onDefiniteScreen(String typeIds,String otherSeachs){
        typeId = typeIds;
        attributeSeach = otherSeachs;
        mBGARefreshLayout.beginRefreshing();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(fragmentManager != null){
            fragmentManager = null;
        }
    }
}
