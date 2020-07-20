package com.lwc.shanxiu.module.lease_parts.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.MainActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.BaseFragment;
import com.lwc.shanxiu.module.addressmanager.AddressManagerActivity;
import com.lwc.shanxiu.module.authentication.activity.AuthenticationMainActivity;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.lease_parts.activity.LeaseNeedPayActivity;
import com.lwc.shanxiu.module.lease_parts.activity.LeaseOrderSearchActivity;
import com.lwc.shanxiu.module.lease_parts.activity.LeaseReturnOrderSearchActivity;
import com.lwc.shanxiu.module.lease_parts.activity.MyCollectActivity;
import com.lwc.shanxiu.module.lease_parts.activity.MyLeaseOrderListActivity;
import com.lwc.shanxiu.module.lease_parts.widget.GetPhoneDialog;
import com.lwc.shanxiu.module.message.bean.MyMsg;
import com.lwc.shanxiu.module.message.ui.MsgListActivity;
import com.lwc.shanxiu.module.wallet.WalletActivity;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LeaseMinetFragment extends BaseFragment {

    @BindView(R.id.ll_authentication)
    LinearLayout ll_authentication;
    @BindView(R.id.iv_header)
    ImageView iv_header;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_authentication)
    TextView tv_authentication;
    @BindView(R.id.tv_collect)
    TextView tv_collect;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_msg)
    TextView tv_msg;

    @BindView(R.id.tv_type01_msg)
    TextView tv_type01_msg;
    @BindView(R.id.tv_type02_msg)
    TextView tv_type02_msg;
    @BindView(R.id.tv_type03_msg)
    TextView tv_type03_msg;
    @BindView(R.id.tv_type04_msg)
    TextView tv_type04_msg;
    @BindView(R.id.tv_type05_msg)
    TextView tv_type05_msg;

    @BindView(R.id.tv_heaer_authentication)
    TextView tv_heaer_authentication;

    private SharedPreferencesUtils preferencesUtils = null;
    private User user = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lease_mine, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if(user != null){
            loadUI();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        preferencesUtils = SharedPreferencesUtils.getInstance(getContext());
        user = preferencesUtils.loadObjectData(User.class);

        int collectSize = (int) SharedPreferencesUtils.getParam(getActivity(),"collectSize",0);
        tv_collect.setText(String.valueOf(collectSize));

        getOrderMessage();
        loadUI();

        //获取未读租赁消息
        //MsgReadUtil.hasMessage(getActivity(),tv_msg);
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && getActivity() != null){
            ImmersionBar.with(getActivity())
                    .statusBarColor(R.color.white)
                    .statusBarDarkFont(true).init();

            preferencesUtils = SharedPreferencesUtils.getInstance(getContext());
            user = preferencesUtils.loadObjectData(User.class);

            //获取未读租赁消息
            //MsgReadUtil.hasMessage(getActivity(),tv_msg);

            loadUI();
        }
    }

    @OnClick({R.id.rl_collect,R.id.ll_authentication,R.id.tv_type01,R.id.tv_type02,R.id.tv_type03,R.id.tv_type04,
            R.id.tv_type05,R.id.ll_address,R.id.ll_kefu,R.id.tv_collect,
            R.id.tv_money,R.id.iv_right,R.id.tv_msg,R.id.tv_heaer_authentication,R.id.rl_wallet})
    public void onBtnClick(View view){
        Bundle bundle = new Bundle();
        switch (view.getId()){
            case R.id.rl_collect:
                IntentUtil.gotoActivity(getActivity(), MyCollectActivity.class);
                break;
            case R.id.rl_wallet:
                IntentUtil.gotoActivity(getActivity(), WalletActivity.class);
                break;
            case R.id.ll_authentication:
            case R.id.tv_heaer_authentication:
                IntentUtil.gotoActivity(getActivity(), AuthenticationMainActivity.class);
                break;
            case R.id.tv_type01:
                bundle.putInt("pageType",1);
                IntentUtil.gotoActivity(getActivity(), MyLeaseOrderListActivity.class,bundle);
                break;
            case R.id.tv_type02:
                bundle.putInt("pageType",2);
                IntentUtil.gotoActivity(getActivity(), MyLeaseOrderListActivity.class,bundle);
                break;
            case R.id.tv_type03:
                bundle.putInt("pageType",3);
                IntentUtil.gotoActivity(getActivity(), MyLeaseOrderListActivity.class,bundle);
                break;
            case R.id.tv_type04:
                bundle.putInt("pageType",4);
                IntentUtil.gotoActivity(getActivity(), LeaseReturnOrderSearchActivity.class,bundle);
                break;
            case R.id.tv_type05:
                bundle.putInt("pageType",5);
                IntentUtil.gotoActivity(getActivity(), MyLeaseOrderListActivity.class,bundle);
                break;
            case R.id.ll_address:
                IntentUtil.gotoActivity(getActivity(), AddressManagerActivity.class);
                break;
            case R.id.tv_money:
               // IntentUtil.gotoActivity(getActivity(), WalletActivity.class);
                break;
            case R.id.ll_kefu: //发票与报销
                GetPhoneDialog picturePopupWindow = new GetPhoneDialog(getContext(), new GetPhoneDialog.CallBack() {
                    @Override
                    public void twoClick() {
                        Utils.lxkf(getActivity(), "400-881-0769");
                    }
                    @Override
                    public void cancelCallback() {
                    }
                }, "", "呼叫 400-881-0769");
                picturePopupWindow.show();
                break;
            case R.id.iv_right:
            case R.id.tv_msg:
                MyMsg msg = new MyMsg();
                msg.setMessageType("5");
                msg.setMessageTitle("租赁消息");
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("myMsg", msg);
                IntentUtil.gotoActivity(getContext(), MsgListActivity.class,bundle1);
                break;
        }
    }

    private void loadUI(){
        ImageLoaderUtil.getInstance().displayFromNetDCircular(getContext(),user.getMaintenanceHeadImage(),iv_header,R.drawable.default_portrait_100);
        if(!TextUtils.isEmpty(user.getUserName())){
            tv_name.setText(user.getUserName());
        }else{
            tv_name.setText(user.getUserPhone());
        }

        tv_money.setText(user.getBanlance());
      /*switch (user.getIsCertification()){
          case "0":
              tv_authentication.setText("未认证");
              tv_heaer_authentication.setText("未实名 >");
              break;
          case "1":
              tv_authentication.setText("审核中");
              tv_heaer_authentication.setText("未实名 >");
              break;
          case "2":
              tv_authentication.setText("已认证");
              tv_heaer_authentication.setText("已实名 >");
              break;
          case "3":
              tv_authentication.setText("认证失败");
              tv_heaer_authentication.setText("未实名 >");
              break;
      }*/
    }

    private void getOrderMessage(){
        HttpRequestUtils.httpRequest(getActivity(), "查询用户订单数量", RequestValue.GET_PARTSMANAGE_ORDERNUMDATA, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String result) {
                Common common = JsonUtil.parserGsonToObject(result, Common.class);
                if (common.getStatus().equals("1")) {
                    HashMap<String,String> current = JsonUtil.parserGsonToMap(JsonUtil.getGsonValueByKey(result, "data"), new TypeToken<HashMap<String, String>>(){});

                    if("0".equals(current.get("1"))){  //待付款
                        tv_type01_msg.setVisibility(View.GONE);
                    }else{
                        tv_type01_msg.setVisibility(View.VISIBLE);
                        tv_type01_msg.setText(current.get("1"));
                    }

                    if("0".equals(current.get("2"))){ //代发货
                        tv_type02_msg.setVisibility(View.GONE);
                    }else{
                        tv_type02_msg.setVisibility(View.VISIBLE);
                        tv_type02_msg.setText(current.get("2"));
                    }

                    if("0".equals(current.get("3"))){  //待收货
                        tv_type03_msg.setVisibility(View.GONE);
                    }else{
                        tv_type03_msg.setVisibility(View.VISIBLE);
                        tv_type03_msg.setText(current.get("3"));
                    }

                    if("0".equals(current.get("4"))){  //租用中
                        tv_type04_msg.setVisibility(View.GONE);
                    }else{
                        tv_type04_msg.setVisibility(View.VISIBLE);
                        tv_type04_msg.setText(current.get("4"));
                    }

                    if("0".equals(current.get("0"))){  //租用中
                        tv_type05_msg.setVisibility(View.GONE);
                    }else{
                        tv_type05_msg.setVisibility(View.VISIBLE);
                        tv_type05_msg.setText(current.get("0"));
                    }
                }

            }

            @Override
            public void returnException(Exception e, String msg) {
                //ToastUtil.showToast(getContext(), msg);
            }
        });
    }
}
