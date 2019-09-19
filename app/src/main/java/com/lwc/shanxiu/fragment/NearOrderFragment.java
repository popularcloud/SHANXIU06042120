package com.lwc.shanxiu.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.adapter.MyNameAdapter;
import com.lwc.shanxiu.adapter.NearbyOrderAdapter;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.bean.Location;
import com.lwc.shanxiu.bean.Shi;
import com.lwc.shanxiu.bean.Xian;
import com.lwc.shanxiu.configs.BroadcastFilters;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.bean.DeviceType;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.order.ui.activity.OrderDetailActivity;
import com.lwc.shanxiu.module.user.UserInfoActivity;
import com.lwc.shanxiu.utils.ACache;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.FileUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.SystemUtil;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.ProgressUtils;
import com.lwc.shanxiu.widget.DialogStyle2;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 附近订单
 */
public class NearOrderFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    @BindView(R.id.iv_nodate)
    ImageView iv_nodate;

    @BindView(R.id.tv_quyu)
    TextView tv_quyu;
    @BindView(R.id.tv_px)
    TextView tv_px;
    @BindView(R.id.tv_sb)
    TextView tv_sb;
    @BindView(R.id.ll_tj)
    LinearLayout ll_tj;
    @BindView(R.id.tjlist)
    ListView tjlist;

    private SharedPreferencesUtils preferencesUtils;
    private List<Order> orders;
    private List<DeviceType> deviceTypes;
    private NearbyOrderAdapter nearbyOrderAdapter;
    private User user = null;
    private int curPage=1;
    private ArrayList<Shi> shis;
    private ArrayList<Xian> xians = new ArrayList<Xian>();
    private ArrayList<String> strList = new ArrayList<>();
    private ArrayList<String> paixuList = new ArrayList<>();
    private ArrayList<String> sbList = new ArrayList<>();
    private String cityCode;
    private Shi selectedShi;
    private String cityId="", townId="", field="", sortord="", deviceTypeId="";
    private String strQuyu="", strPaixu="", strSb="";
    private MyNameAdapter adapter;
    private int selectType;
    private ProgressUtils progressUtils;
    private ACache aCache;
    /**
     * 显示跳转activity界面对话框
     */
    private void showIntenUseuInfoActivity() {
        DialogStyle2 dialogStyle2 = new DialogStyle2(getContext());
        dialogStyle2.initDialogContent("必须把个人信息标志《必填》的项，填写完整才能接单", "确定");
        dialogStyle2.setDialogClickListener(new DialogStyle2.DialogClickListener() {
            @Override
            public void affirmClick(Context context, DialogStyle2 dialog) {
                IntentUtil.gotoActivity(getContext(), UserInfoActivity.class);
                dialog.dismissDialog1();
            }
        });
        dialogStyle2.showDialog1();
    }

    @Override
    public void init() {
    }

    @Override
    public void initGetData() {
        progressUtils = new ProgressUtils();
        preferencesUtils = SharedPreferencesUtils.getInstance(getContext());
        user = preferencesUtils.loadObjectData(User.class);
        paixuList.add("最新发布");
        paixuList.add("发布最久");
        paixuList.add("离我最近");
    }

    private void bindRecylceView() {
        mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                curPage=1;
                getOrderList();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                curPage++;
                getOrderList();
                return true;
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setLayoutManager(new VegaLayoutManager(getContext()));
        nearbyOrderAdapter = new NearbyOrderAdapter(getContext(), orders, R.layout.item_order_listview);
        nearbyOrderAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {

                user = preferencesUtils.loadObjectData(User.class);
                String userName = user.getMaintenanceName();
                if (!TextUtils.isEmpty(userName)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("orderId", nearbyOrderAdapter.getItem(position).getOrderId());
                    IntentUtil.gotoActivity(getActivity(), OrderDetailActivity.class, bundle);
                } else {
                    showIntenUseuInfoActivity();
                }
            }
        });
        recyclerView.setAdapter(nearbyOrderAdapter);

        adapter = new MyNameAdapter(getActivity());
        adapter.setData(strList, strQuyu);
        tjlist.setAdapter(adapter);
        tjlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = adapter.getItem(position);
                updateOrder(name);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getDeviceType();
        curPage = 1;
        getOrderList();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser  && getActivity() != null){
            ImmersionBar.with(getActivity())
                    .statusBarColor(R.color.white)
                    .statusBarDarkFont(true)
                    .navigationBarColor(R.color.white).init();
        }
    }

    public void getDeviceType(){
        aCache = ACache.get(getContext());
        if (deviceTypes != null && deviceTypes.size() > 0) {
            if (sbList != null && sbList.size() > 0){
                return;
            } else {
                sbList = new ArrayList<>();
                sbList.add("全部");
                for (int i=0; i<deviceTypes.size(); i++) {
                    if (deviceTypes.get(i).getDeviceTypeName() != null)
                        sbList.add(deviceTypes.get(i).getDeviceTypeName());
                }
                return;
            }
        }
        String response = aCache.getAsString("deviceTypeData");
        if (!TextUtils.isEmpty(response)) {
            analysisData(response);
            return;
        }
        HttpRequestUtils.httpRequest(getActivity(), "getDeviceType", RequestValue.GET_OLL_DEVICE_TYPE+"?deviceMold="+ SharedPreferencesUtils.getParam(getContext(),"deviceTypeMold","1"), null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        analysisData(response);
                        aCache.put("deviceTypeData", response, 86400);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError("getDeviceType  " + e.toString());
            }
        });
    }

    private void analysisData(String response) {
        deviceTypes = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<DeviceType>>() {
        });
        if (deviceTypes != null && deviceTypes.size() > 0) {
            sbList.add("全部");
            for (int i=0; i<deviceTypes.size(); i++) {
                if (deviceTypes.get(i).getDeviceTypeName() != null)
                    sbList.add(deviceTypes.get(i).getDeviceTypeName());
            }
        }
    }

    /**
     * 获取附近订单
     */
    private void getOrderList() {
        if (cityId == null || cityId.equals("")){
            return;
        }
        if (orders == null || orders.size() == 0) {
            progressUtils.showCustomProgressDialog(getActivity());
        }
        Map<String, String> map = new HashMap<>();
        map.put("curPage", curPage+"");
        map.put("cityId", cityId);
        map.put("townId", townId);
        map.put("field", field);
        map.put("sortord", sortord);
        map.put("deviceTypeId", deviceTypeId);
        HttpRequestUtils.httpRequest(getActivity(), "getOrderList", RequestValue.NEARBY_ORDER2, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        List<Order> orderList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<Order>>() {
                        });
                        if (orderList != null && orderList.size() > 0) {
                            if (curPage > 1 && orders != null) {
                                orders.addAll(orderList);
                            } else {
                                orders = orderList;
                            }
                        } else {
                            if (curPage == 1){
                                orders = new ArrayList<>();
                            } else {
                                curPage--;
                                ToastUtil.showLongToast(getActivity(), "暂无更多订单");
                            }
                        }
                        nearbyOrderAdapter.replaceAll(orders);
                        if (orders != null && orders.size() > 0) {
                            iv_nodate.setVisibility(View.GONE);
                            mBGARefreshLayout.setVisibility(View.VISIBLE);
                        } else {
                            iv_nodate.setVisibility(View.VISIBLE);
                            mBGARefreshLayout.setVisibility(View.GONE);
                        }
                        break;
                    default:
                        break;
                }
                progressUtils.dismissCustomProgressDialog();
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
                BGARefreshLayoutUtils.endLoadingMore(mBGARefreshLayout);
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError("getOrderList  " + e.toString());
                progressUtils.dismissCustomProgressDialog();
                if (!TextUtils.isEmpty(msg))
                    ToastUtil.showLongToast(getContext(), msg);
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
                BGARefreshLayoutUtils.endLoadingMore(mBGARefreshLayout);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BGARefreshLayoutUtils.initRefreshLayout(getContext(), mBGARefreshLayout);
        bindRecylceView();
    }

    @Override
    protected View getViews() {
        return View.inflate(getActivity(), R.layout.fragment_order_list_nearby, null);
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        setTitle("附近订单");
    }

    @Override
    protected void widgetListener() {
    }

    @Override
    protected void onReceive(Context context, Intent intent) {
        if (BroadcastFilters.NOTIFI_GET_NEAR_ORDER.equals(intent.getAction())) {
            initData();
            if (SystemUtil.isBackground(getContext())) {
                return;
            }
            if (isVisible()) {
                curPage = 1;
                getOrderList();
            }
        }
    }

    public void initData() {
        if (selectedShi == null && strList.size() == 0) {
            String shi = FileUtil.readAssetsFile(getActivity(), "shi.json");
            String xian = FileUtil.readAssetsFile(getActivity(), "xian.json");
            shis = JsonUtil.parserGsonToArray(shi, new TypeToken<ArrayList<Shi>>() {
            });
            ArrayList<Xian> allXians = JsonUtil.parserGsonToArray(xian, new TypeToken<ArrayList<Xian>>() {
            });
            Location location = preferencesUtils.loadObjectData(Location.class);
            if (location != null) {
                String [] list = location.getStrValue().split("#");
                if (list != null && list.length > 0) {
                    Map<String, String> map = new HashMap<>();
                    for (int i=0; i<list.length; i++) {
                        String str = list[i];
                        if (str != null && !str.equals("")){
                            String [] arr = str.split("=");
                            if (arr != null && arr.length > 1) {
                                map.put(arr[0], arr[1]);
                            }
                        }
                    }
                    cityCode = map.get("cityCode");
                }
            }
            if (!TextUtils.isEmpty(cityCode) && shis != null) {
                for (int i=0; i<shis.size(); i++) {
                    if (shis.get(i).getCode().equals(cityCode)) {
                        selectedShi = shis.get(i);
                        cityId = selectedShi.getDmId();
                        break;
                    }
                }
                if (selectedShi != null) {
                    strList.add("全部");
                    for (int i=0; i<allXians.size(); i++) {
                        if (allXians.get(i).getParentid().equals(selectedShi.getDmId())) {
                            xians.add(allXians.get(i));
                            strList.add(allXians.get(i).getName().trim());
                        }
                    }
                }
            }
        }
    }

    public void updateOrder(String name) {
        if (selectType == 213 ) {
            strQuyu = name;
            if (strQuyu == null || strQuyu.equals("")){
                return;
            }
            if (strQuyu != null && strQuyu.equals("全部")){
                townId = "";
                curPage = 1;
                getOrderList();
            } else {
                for (int i=0; i<xians.size();i++) {
                    if (xians.get(i).getName().trim().equals(strQuyu)) {
                        townId = xians.get(i).getDmId();
                        break;
                    }
                }
                curPage = 1;
                getOrderList();
            }
            tv_quyu.setText(strQuyu);
            tv_quyu.setTextColor(getResources().getColor(R.color.btn_blue_nomal));
        } else if (selectType == 215) {
            strPaixu = name;
            if (strPaixu == null || strPaixu.equals("")){
                return;
            }
            if (strPaixu.equals("最新发布")) {
                field = "1";
                sortord = "2";
            } else if (strPaixu.equals("发布最久")) {
                field = "1";
                sortord = "1";
            } else if (strPaixu.equals("离我最近")) {
                field = "2";
                sortord = "1";
            }
            curPage = 1;
            getOrderList();
            tv_px.setText(strPaixu);
            tv_px.setTextColor(getResources().getColor(R.color.btn_blue_nomal));
        } else if (selectType == 216) {
            strSb = name;
            if (strSb == null || strSb.equals("")){
                return;
            }
            if (strSb != null && strSb.equals("全部")){
                deviceTypeId = "";
                curPage = 1;
                getOrderList();
            } else {
                for (int i=0; i<deviceTypes.size();i++) {
                    if (deviceTypes.get(i).getDeviceTypeName().equals(strSb)) {
                        deviceTypeId = deviceTypes.get(i).getDeviceTypeId();
                        break;
                    }
                }
                curPage = 1;
                getOrderList();
            }
            tv_sb.setText(strSb);
            tv_sb.setTextColor(getResources().getColor(R.color.btn_blue_nomal));
        }
        ll_tj.setVisibility(View.GONE);
        setDrawable();
    }

    @OnClick({R.id.ll_sb, R.id.ll_quyu, R.id.ll_px, R.id.ll_tj,R.id.iv_nodate})
    public void onViewClicked(View view) {
        int d = R.drawable.ner_up_h;
        switch (view.getId()) {
            case R.id.ll_quyu:
                setDrawable();
                if (selectType == 213 && ll_tj.isShown()){
                    ll_tj.setVisibility(View.GONE);
                    return;
                }
                if (strList != null && strList.size() > 0) {
                    if (strQuyu != null && !strQuyu.equals("")) {
                        d = R.drawable.ner_up_l;
                    }
                    tv_quyu.setCompoundDrawables(null, null,
                            getDrawable(d), null);
                    selectType = 213;
                    adapter.setData(strList, strQuyu);
                    adapter.notifyDataSetChanged();
                    ll_tj.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_px:
                setDrawable();
                if (selectType == 215 && ll_tj.isShown()){
                    ll_tj.setVisibility(View.GONE);
                    return;
                }
                if (paixuList != null && paixuList.size() > 0) {
                    if (strPaixu != null && !strPaixu.equals("")) {
                        d = R.drawable.ner_up_l;
                    }
                    tv_px.setCompoundDrawables(null, null,
                            getDrawable(d), null);
                    selectType = 215;
                    adapter.setData(paixuList, strPaixu);
                    adapter.notifyDataSetChanged();
                    ll_tj.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_sb:
                setDrawable();
                if (selectType == 216 && ll_tj.isShown()){
                    ll_tj.setVisibility(View.GONE);
                    return;
                }
                if (sbList != null && sbList.size() > 0) {
                    if (strSb != null && !strSb.equals("")) {
                        d = R.drawable.ner_up_l;
                    }
                    tv_sb.setCompoundDrawables(null, null,
                            getDrawable(d), null);
                    selectType = 216;
                    adapter.setData(sbList, strSb);
                    adapter.notifyDataSetChanged();
                    ll_tj.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_nodate:
                iv_nodate.setVisibility(View.GONE);
                mBGARefreshLayout.setVisibility(View.VISIBLE);
                mBGARefreshLayout.beginRefreshing();
                break;
            default:
                setDrawable();
                ll_tj.setVisibility(View.GONE);
        }
    }

    public void setDrawable() {
        try {
            int d = R.drawable.ner_down_h;
            if (selectType == 213) {
                if (strQuyu != null && !strQuyu.equals("")) {
                    d = R.drawable.ner_down_l;
                }
                tv_quyu.setCompoundDrawables(null, null,
                        getDrawable(d), null);
            } else if (selectType == 215) {
                if (strPaixu != null && !strPaixu.equals("")) {
                    d = R.drawable.ner_down_l;
                }
                tv_px.setCompoundDrawables(null, null,
                        getDrawable(d), null);
            } else if (selectType == 216) {
                if (strSb != null && !strSb.equals("")) {
                    d = R.drawable.ner_down_l;
                }
                tv_sb.setCompoundDrawables(null, null,
                        getDrawable(d), null);
            }
        }catch (Exception e){}
    }

    public Drawable getDrawable(int d) {
        Drawable drawable = getActivity().getResources().getDrawable(d);
        drawable.setBounds(0,0,drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

    public void goneTj() {
        setDrawable();
        try {
            if (ll_tj != null)
                ll_tj.setVisibility(View.GONE);
            if (mBGARefreshLayout != null) {
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
                BGARefreshLayoutUtils.endLoadingMore(mBGARefreshLayout);
                initData();
            }
        }catch (Exception e){}
    }
}
