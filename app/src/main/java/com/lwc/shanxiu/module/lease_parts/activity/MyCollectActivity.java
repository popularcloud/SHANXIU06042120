package com.lwc.shanxiu.module.lease_parts.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.lease_parts.adapter.LeaseCollectAdapter;
import com.lwc.shanxiu.module.lease_parts.bean.LeaseGoodBean;
import com.lwc.shanxiu.module.lease_parts.inteface_callback.OnOrderBtnClick;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @author younge
 * @date 2018/12/19 0019
 * @email 2276559259@qq.com
 */
public class MyCollectActivity extends BaseActivity {

    @BindView(R.id.tv_goodSum)
    TextView tv_goodSum;
    @BindView(R.id.cb_all_box)
    CheckBox cb_all_box;
    @BindView(R.id.tv_to_shopping)
    TextView tv_to_shopping;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;
    @BindView(R.id.ll_bottom_button)
    LinearLayout ll_bottom_button;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;

    List<LeaseGoodBean> leaseGoodBeans = new ArrayList<>();

    private LeaseCollectAdapter leaseCollectAdapter;
    private int page = 1;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_my_collect;
    }

    @Override
    protected void findViews() {
        bindRecycleView();
    }

    @Override
    protected void init() {
        setTitle("收藏夹");
        setRightText("管理", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) v;
                if("管理".equals(textView.getText().toString().trim())){
                    textView.setText("完成");
                    ll_bottom_button.setVisibility(View.VISIBLE);
                    leaseCollectAdapter.setManager(true);
                }else{
                    textView.setText("管理");
                    ll_bottom_button.setVisibility(View.GONE);
                    leaseCollectAdapter.setManager(false);
                }
            }
        });

        ll_no_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBGARefreshLayout.beginRefreshing();
            }
        });

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delGood();
            }
        });

        tv_to_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.gotoActivity(MyCollectActivity.this, LeaseGoodsListActivity.class);
            }
        });

        cb_all_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for(LeaseGoodBean leaseGoodBean : leaseCollectAdapter.getData()){
                    leaseGoodBean.setChecked(isChecked);
                }
                leaseCollectAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mBGARefreshLayout.beginRefreshing();
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void bindRecycleView() {

        leaseCollectAdapter = new LeaseCollectAdapter(this, leaseGoodBeans, R.layout.item_lease_good_list, new OnOrderBtnClick() {
            @Override
            public void onItemClick(String btnText, int position, Object o) {///选中之后 改变全选转态

                int count = 0;
                for(LeaseGoodBean leaseGoodBean : leaseCollectAdapter.getData()){
                    if(leaseGoodBean.isChecked()){
                        count = count + 1;
                    }
                }
                if(count >= leaseCollectAdapter.getData().size()){
                    cb_all_box.setChecked(true);
                }else{
                    cb_all_box.setChecked(false);
                }

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(MyCollectActivity.this));
        recyclerView.setAdapter(leaseCollectAdapter);
        leaseCollectAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("goodId",leaseCollectAdapter.getItem(position).getGoodsId());
                IntentUtil.gotoActivity(MyCollectActivity.this,LeaseGoodsDetailActivity.class,bundle);
            }
        });

        BGARefreshLayoutUtils.initRefreshLayout(this, mBGARefreshLayout,false);
        //刷新控件监听器
        mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
              //  page = 1;
                getDateList();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                //page ++;
                //getDateList();
                return true;
            }
        });

        mBGARefreshLayout.beginRefreshing();
    }

    /**
     * 获取我收藏的商品
     */
    private void getDateList(){
        HttpRequestUtils.httpRequest(MyCollectActivity.this, "获取我收藏的商品", RequestValue.PARTSMANAGE_QUERYPARTSGOODSCOLLECTION, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if("1".equals(common.getStatus())){
                    // ToastUtil.showToast(getActivity(),"获取我的订单成功!");
                    leaseGoodBeans = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response,"data"),new TypeToken<ArrayList<LeaseGoodBean>>(){});

                    if(leaseGoodBeans != null && leaseGoodBeans.size() > 0){

                        SharedPreferencesUtils.setParam(MyCollectActivity.this,"collectSize",leaseGoodBeans.size());

                        tv_goodSum.setText("共"+leaseGoodBeans.size()+"件宝贝");
                        ll_no_data.setVisibility(View.GONE);
                        ll_bottom_button.setVisibility(View.GONE);
                        mBGARefreshLayout.setVisibility(View.VISIBLE);
                        tv_goodSum.setVisibility(View.VISIBLE);

                        setRightText("管理", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView textView = (TextView) v;
                                if("管理".equals(textView.getText().toString().trim())){
                                    textView.setText("完成");
                                    ll_bottom_button.setVisibility(View.VISIBLE);
                                    leaseCollectAdapter.setManager(true);
                                }else{
                                    textView.setText("管理");
                                    ll_bottom_button.setVisibility(View.GONE);
                                    leaseCollectAdapter.setManager(false);
                                }
                            }
                        });
                        leaseCollectAdapter.setManager(false);
                    }else{
                        SharedPreferencesUtils.setParam(MyCollectActivity.this,"collectSize",0);
                        tv_goodSum.setText("共计0件宝贝");
                        ll_no_data.setVisibility(View.VISIBLE);
                        ll_bottom_button.setVisibility(View.GONE);
                        mBGARefreshLayout.setVisibility(View.GONE);
                        tv_goodSum.setVisibility(View.GONE);

                        setRightText("", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView textView = (TextView) v;
                                if("管理".equals(textView.getText().toString().trim())){
                                    textView.setText("完成");
                                    ll_bottom_button.setVisibility(View.VISIBLE);
                                    leaseCollectAdapter.setManager(true);
                                }else{
                                    textView.setText("管理");
                                    ll_bottom_button.setVisibility(View.GONE);
                                    leaseCollectAdapter.setManager(false);
                                }
                            }
                        });
                        leaseCollectAdapter.setManager(false);

                    }


                    if(page == 1){
                        leaseCollectAdapter.replaceAll(leaseGoodBeans);
                    }else{
                        leaseCollectAdapter.addAll(leaseGoodBeans);
                    }
                }else{
                    ToastUtil.showToast(MyCollectActivity.this,common.getInfo());
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
     */
    private void delGood(){
        if(leaseGoodBeans == null || leaseGoodBeans.size() < 1){
            return;
        }
        Map<String,String> params = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0;i < leaseGoodBeans.size();i++){
            if(leaseGoodBeans.get(i).isChecked()){
                stringBuilder.append(leaseGoodBeans.get(i).getCollectionId()+",");
            }
        }
        if(TextUtils.isEmpty(stringBuilder)){
            ToastUtil.showToast(MyCollectActivity.this,"请选择你要删除的商品");
            return;
        }
        params.put("in_collection_id",stringBuilder.toString());
        HttpRequestUtils.httpRequest(MyCollectActivity.this, "删除收藏",RequestValue.PARTSMANAGE_DELPARTSGOODSCOLLECTION, params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if("1".equals(common.getStatus())){
                    mBGARefreshLayout.beginRefreshing();
                }else{
                    ToastUtil.showToast(MyCollectActivity.this,common.getInfo());
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }
}
