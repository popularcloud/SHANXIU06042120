package com.lwc.shanxiu.module.order.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;


import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.bean.FeeStandardBean;
import com.lwc.shanxiu.module.order.ui.adapter.FeeStandardAdapter;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class FeeStandardActivity extends BaseActivity {

    @BindView(R.id.expand_list)
    ExpandableListView expand_list;
    @BindView(R.id.tv_remark)
    TextView tv_remark;
    private String deviceTypeMold;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_fee_standard;
    }

    @Override
    protected void findViews() {
        deviceTypeMold = getIntent().getStringExtra("deviceTypeMold");
        showBack();
        if("1".equals(deviceTypeMold)){  //办公设备
            setTitle("收费标准(办公设备)");
        }else{
            setTitle("收费标准(家用电器)");
        }

        expand_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
            //设置点击不关闭（不收回）
                v.setClickable(true);
                return true;
            }
        });

    }

    @Override
    protected void init() {

    }

    @Override
    protected void initGetData() {

        Map<String,String> params = new HashMap<>();
        params.put("deviceTypeMold",deviceTypeMold);
        HttpRequestUtils.httpRequest(this, "获取收费标准", RequestValue.GET_INFORMATION_CHARGINGSTANDARD,params, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        FeeStandardBean feeStandardBean = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response,"data"), FeeStandardBean.class);
                       if(feeStandardBean != null && feeStandardBean.getData() != null){
                           tv_remark.setText(feeStandardBean.getRemark());
                           loadData(feeStandardBean.getData());
                       }else{
                           ToastUtil.showToast(FeeStandardActivity.this, "获取失败");
                       }

                        break;
                    default:
                        ToastUtil.showToast(FeeStandardActivity.this, common.getInfo());
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showToast(FeeStandardActivity.this, msg);
            }
        });
    }

    private void loadData(List<FeeStandardBean.DataBeanX> list) {
        FeeStandardAdapter adapter = new FeeStandardAdapter(this,list);
        expand_list.setAdapter(adapter);

        for(int i = 0; i < adapter.getGroupCount(); i++){
            expand_list.expandGroup(i);
        }
    }

    @Override
    protected void widgetListener() {

    }
}
