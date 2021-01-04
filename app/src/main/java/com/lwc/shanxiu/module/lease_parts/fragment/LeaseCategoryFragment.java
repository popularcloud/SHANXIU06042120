package com.lwc.shanxiu.module.lease_parts.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.BaseFragment;
import com.lwc.shanxiu.module.lease_parts.activity.LeaseGoodsListActivity;
import com.lwc.shanxiu.module.lease_parts.adapter.LeftTypeAdapter;
import com.lwc.shanxiu.module.lease_parts.adapter.RightGoodsAdapter;
import com.lwc.shanxiu.module.lease_parts.bean.LeaseGoodsTypeBig;
import com.lwc.shanxiu.module.lease_parts.bean.LeaseGoodsTypeSmall;
import com.lwc.shanxiu.module.message.bean.MyMsg;
import com.lwc.shanxiu.module.message.ui.MsgListActivity;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.MsgReadUtil;
import com.lwc.shanxiu.utils.ToastUtil;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by maxcion_home on 2017/9/8.
 */

public class LeaseCategoryFragment extends BaseFragment {

    @BindView(R.id.rv_left_menu)
    RecyclerView rv_left_menu;
    @BindView(R.id.rv_right_goods)
    RecyclerView rv_right_goods;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;

    private List<LeaseGoodsTypeBig> leaseLeftBeans = new ArrayList<>();;
    private List<LeaseGoodsTypeSmall>   leaseRightBeans = new ArrayList<>();;
    private LeftTypeAdapter leftTypeAdapter;
    private RightGoodsAdapter rightGoodsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lease_category, container, false);
       ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
       // et_search.setEnabled(false);
        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //IntentUtil.gotoActivity(getContext(), LeaseSearchActivity.class);
            }
        });
        et_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    //IntentUtil.gotoActivity(getContext(), LeaseSearchActivity.class);
                }
            }
        });
        initLeftRecyclerView();
        initRightRecyclerView();
        getBigGoodType();
    }

    @OnClick({R.id.iv_right,R.id.tv_msg})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.iv_right:
            case R.id.tv_msg:
                MyMsg msg = new MyMsg();
                msg.setMessageType("6");
                msg.setMessageTitle("租赁消息");
                Bundle bundle = new Bundle();
                bundle.putSerializable("myMsg", msg);
                IntentUtil.gotoActivity(getContext(), MsgListActivity.class,bundle);
                break;
        }
    }

    @Override
    public void initEngines(View view) {

    }

    @Override
    public void getIntentData() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void onResume() {
        super.onResume();
        //获取未读租赁消息
        MsgReadUtil.hasMessage(getActivity(),tv_msg);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && getActivity() != null){
            ImmersionBar.with(getActivity())
                    .statusBarColor(R.color.white)
                    .statusBarDarkFont(true).init();

            //获取未读租赁消息
            MsgReadUtil.hasMessage(getActivity(),tv_msg);
        }
    }

    private void initLeftRecyclerView() {
        leftTypeAdapter = new LeftTypeAdapter(getContext(),leaseLeftBeans,R.layout.item_lease_left_menu);
        rv_left_menu.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_left_menu.setAdapter(leftTypeAdapter);

        leftTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                leftTypeAdapter.setSelectPos(position);
                leftTypeAdapter.notifyDataSetChanged();

                getSmalGoodType(leaseLeftBeans.get(position).getDeviceTypeId());
            }
        });
    }

    private void initRightRecyclerView() {
        rightGoodsAdapter = new RightGoodsAdapter(getContext(),leaseRightBeans,R.layout.item_lease_right_goods);
        rv_right_goods.setLayoutManager(new GridLayoutManager(getContext(),3));
        rv_right_goods.setAdapter(rightGoodsAdapter);

        rightGoodsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("typeDetailId",rightGoodsAdapter.getItem(position).getTypeDetailId());
                bundle.putString("typeId",rightGoodsAdapter.getItem(position).getDeviceTypeId());
                IntentUtil.gotoActivity(getActivity(), LeaseGoodsListActivity.class,bundle);
            }
        });
    }

    private void getBigGoodType(){
        HttpRequestUtils.httpRequest(getActivity(), "获取商品类型大类", RequestValue.GET_PARTSMANAGE_GETDEVICETYPES, null, "GET", new HttpRequestUtils.ResponseListener() {

            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if("1".equals(common.getStatus())){
                    leaseLeftBeans = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response,"data"),new TypeToken<ArrayList<LeaseGoodsTypeBig>>(){});
                    if(leaseLeftBeans != null && leaseLeftBeans.size() > 0){
                        leftTypeAdapter.addAll(leaseLeftBeans);
                        getSmalGoodType(leaseLeftBeans.get(0).getDeviceTypeId());
                    }

                }else{
                    ToastUtil.showToast(getActivity(),common.getInfo());
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    private void getSmalGoodType(String id){
        rightGoodsAdapter.clear();
        HttpRequestUtils.httpRequest(getActivity(), "获取商品类型小类", RequestValue.GET_PARTSMANAGE_GETDEVICETYPEDETAILS +"?device_type_id="+id, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if("1".equals(common.getStatus())){
                    leaseRightBeans = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response,"data"),new TypeToken<ArrayList<LeaseGoodsTypeSmall>>(){});
                    rightGoodsAdapter.addAll(leaseRightBeans);
                }else{
                    ToastUtil.showToast(getActivity(),common.getInfo());
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }
}
