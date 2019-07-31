package com.lwc.shanxiu.module.order.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.interf.OnBtnClickCalBack;
import com.lwc.shanxiu.module.BaseFragment;
import com.lwc.shanxiu.module.bean.DeviceAllMsgBean;
import com.lwc.shanxiu.module.order.ui.activity.RepairHistoryNewActivity;
import com.lwc.shanxiu.module.order.ui.activity.ScrapActivity;
import com.lwc.shanxiu.module.zxing.ui.CaptureActivity;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.widget.DialogScanStyle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author younge
 * @date 2019/6/25 0025
 * @email 2276559259@qq.com
 * 报废/销毁
 */
public class ScrapFragment extends BaseFragment{


    @BindView(R.id.tv_scrap_record)
    TextView tv_scrap_record;
    @BindView(R.id.ll_scrap_record)
    LinearLayout ll_scrap_record;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_user)
    TextView tv_user;
    @BindView(R.id.tv_fault_desc)
    TextView tv_fault_desc;
    @BindView(R.id.tv_order_time)
    TextView tv_order_time;

    @BindView(R.id.tv_destruction_record)
    TextView tv_destruction_record;
    @BindView(R.id.ll_destruction_record)
    LinearLayout ll_destruction_record;
    @BindView(R.id.tv_destruction_time)
    TextView tv_destruction_time;
    @BindView(R.id.tv_destruction_user)
    TextView tv_destruction_user;

    @BindView(R.id.btnScrap)
    TextView btnScrap;
    @BindView(R.id.btnDestruction)
    TextView btnDestruction;
    private boolean isPageLoadFinish = false;

    private String relevanceId;
    private String qrcodeIndex;

    private int relevanceType;

   @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_scrap, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      //  ((RepairHistoryNewActivity)getActivity()).initData(1);
        isPageLoadFinish = true;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && isPageLoadFinish){
           // boolean needUpdate = (boolean) SharedPreferencesUtils.getParam(getActivity(),"needUpdate",false);
            //if(needUpdate){
                ((RepairHistoryNewActivity)getActivity()).initData(1);
            //}
        }
    }

    @Override
    public void init() {

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

    @OnClick({R.id.btnScrap,R.id.btnDestruction})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.btnScrap:
                if(!TextUtils.isEmpty(relevanceId)){
                    Bundle bundle = new Bundle();
                    bundle.putString("relevanceId",relevanceId);
                    IntentUtil.gotoActivity(getContext(), ScrapActivity.class,bundle);
                }else{
                    ToastUtil.showToast(getContext(),"编号为空");
                }

                break;
            case R.id.btnDestruction:
                DialogScanStyle dialogScanStyle = new DialogScanStyle(getContext(), new OnBtnClickCalBack() {
                    @Override
                    public void onClick(String scanStr) {

                        if(scanStr == null){
                            Bundle bundle = new Bundle();
                            bundle.putInt("type", 1);
                            IntentUtil.gotoActivityForResult(getActivity(), CaptureActivity.class, bundle, 8869);
                        }else{

                            if(!TextUtils.isEmpty(qrcodeIndex)){
                                if(qrcodeIndex.contains(scanStr)){
                                    submitDate(scanStr);
                                }else{
                                    ToastUtil.showToast(getActivity(),"二维码错误!");
                                }
                            }
                        }
                    }
                });
                dialogScanStyle.show();
                break;
        }
    }

    public void submitDate(String qrcodeIndex){
        Map<String, String> map = new HashMap<>();
        map.put("qrcodeIndex",qrcodeIndex);
        HttpRequestUtils.httpRequest(getActivity(), "销毁二维码设备信息", RequestValue.GET_SCAN_DESTRUCTIONDEVICEINFO, map, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        ToastUtil.showToast(getActivity(),"销毁成功！");
                        SharedPreferencesUtils.setParam(getActivity(),"needUpdate",true);
                        ((RepairHistoryNewActivity)getActivity()).initData(1);
                        break;
                    default:
                        ToastUtil.showToast(getActivity(),common.getInfo());
                        break;
                }
            }
            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showLongToast(getActivity(), e.toString());
            }
        });
    }

    public void loadData(DeviceAllMsgBean deviceAllMsgBeen,String qrcodeIndex) {
        this.relevanceId = deviceAllMsgBeen.getRelevanceId();
        this.qrcodeIndex = qrcodeIndex;
        relevanceType = deviceAllMsgBeen.getRelevanceType();
        List<DeviceAllMsgBean.RecordsBean> beans = deviceAllMsgBeen.getRecoreds();

        if(deviceAllMsgBeen.getIsRelevance() == 1){
            if(relevanceType == 1){
                btnScrap.setEnabled(true);
                btnScrap.setBackgroundResource(R.drawable.button_login_selector);
                btnDestruction.setEnabled(false);
                btnDestruction.setBackgroundResource(R.drawable.button_login_disenable);
            }else if(relevanceType == 2){
                btnScrap.setEnabled(false);
                btnScrap.setBackgroundResource(R.drawable.button_login_disenable);
                btnDestruction.setEnabled(true);
                btnDestruction.setBackgroundResource(R.drawable.button_red_selector);
                btnScrap.setText("已报废");
            }else if(relevanceType == 3){
          /*      btnScrap.setVisibility(View.GONE);
                btnDestruction.setVisibility(View.GONE);*/
                btnScrap.setEnabled(false);
                btnScrap.setBackgroundResource(R.drawable.button_login_disenable);
                btnDestruction.setEnabled(false);
                btnDestruction.setBackgroundResource(R.drawable.button_login_disenable);
                btnScrap.setText("已报废");
                btnDestruction.setText("已销毁");
            }else if(relevanceType == 4){
                btnScrap.setEnabled(false);
                btnScrap.setBackgroundResource(R.drawable.button_login_disenable);
                btnDestruction.setEnabled(false);
                btnDestruction.setBackgroundResource(R.drawable.button_login_disenable);
                btnScrap.setText("报废中");
            }
        }

        if(beans != null && beans.size() > 0){
            for(DeviceAllMsgBean.RecordsBean bean : beans){
                if(bean.getUpdateType() == 2 || bean.getUpdateType() == 4){
                    tv_scrap_record.setVisibility(View.VISIBLE);
                    ll_scrap_record.setVisibility(View.VISIBLE);
                    tv_time.setText(bean.getCreateTime());
                    tv_user.setText(bean.getMaintenanceName());
                    tv_fault_desc.setText(bean.getUpdateReason());
                    tv_order_time.setText(bean.getMakeTime());
                }

                if(bean.getUpdateType() == 3){
                    tv_destruction_record.setVisibility(View.VISIBLE);
                    ll_destruction_record.setVisibility(View.VISIBLE);
                    tv_destruction_time.setText(bean.getCreateTime());
                    tv_destruction_user.setText(bean.getMaintenanceName());
                }
            }
        }else{
            tv_scrap_record.setVisibility(View.GONE);
            ll_scrap_record.setVisibility(View.GONE);
            tv_destruction_record.setVisibility(View.GONE);
            ll_destruction_record.setVisibility(View.GONE);
            btnScrap.setEnabled(true);
            btnScrap.setBackgroundResource(R.drawable.button_login_selector);
            btnDestruction.setEnabled(false);
            btnDestruction.setBackgroundResource(R.drawable.button_login_disenable);
        }
    }
}
