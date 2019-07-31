package com.lwc.shanxiu.module.order.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;

import java.util.HashMap;

/**
 * 报价
 *
 * @author 何栋
 * @Description TODO
 * @date 2015年12月22日
 * @Copyright: lwc
 */
public class ToBillActivity extends BaseActivity {

    protected String oid;
    private EditText et_repair_des;
    private Button btn_send_repair;
    private EditText et_content;
    private EditText et_pjname;
    private EditText et_pjtype;
    private User user;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_apply_to_bill;
    }

    @Override
    protected void findViews() {
        showBack();
        setTitle("提交报价");
        et_pjname = (EditText) findViewById(R.id.et_pjname);
        et_pjtype = (EditText) findViewById(R.id.et_pjtype);
        et_repair_des = (EditText) findViewById(R.id.et_repair_des);
        et_content = (EditText) findViewById(R.id.et_content);
        btn_send_repair = (Button) findViewById(R.id.btn_send_repair);
    }

    @Override
    protected void initGetData() {
        user  = SharedPreferencesUtils.getInstance(this).loadObjectData(User.class);
        Bundle bundle = getIntent().getExtras();
        Order b = (Order) bundle.getSerializable(getResources().getString(R.string.intent_key_data));
        oid = b.getOrderId() + "";
    }

    @Override
    protected void widgetListener() {
        btn_send_repair.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (checkData()) {
                    sendRepair();
                }
            }
        });
    }

    /**
     * 检查数据
     *
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    protected boolean checkData() {
//        if (TextUtils.isEmpty(et_content.getText().toString().trim())) {
//            ToastUtil.showToast(ToBillActivity.this, "请输入报价金额！");
//            et_content.setSelected(false);
//            return false;
//        }
        if (TextUtils.isEmpty(et_pjname.getText().toString().trim())) {
            ToastUtil.showToast(ToBillActivity.this, "请输入配件名称！");
            et_pjname.setSelected(false);
            return false;
        }
        if (TextUtils.isEmpty(et_pjtype.getText().toString().trim())) {
            ToastUtil.showToast(ToBillActivity.this, "请输入配件型号！");
            et_pjtype.setSelected(false);
            return false;
        }
        if (TextUtils.isEmpty(et_repair_des.getText().toString().trim())) {
            ToastUtil.showToast(ToBillActivity.this, "请输入报价描述！");
            et_repair_des.setSelected(false);
            return false;
        }
        return true;
    }

    @Override
    protected void init() {
    }

    /**
     * 进行申请报修
     *
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    private void sendRepair() {
        if (Utils.isFastClick(1000)) {
            return;
        }
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("orderId", oid);
        if (!TextUtils.isEmpty(et_repair_des.getText().toString().trim())) {
            params.put("remark", et_repair_des.getText().toString().trim());
        } else {
            params.put("remark", "");
        }
        if (!TextUtils.isEmpty(et_pjname.getText().toString().trim())) {
            params.put("name", et_pjname.getText().toString().trim());
        }
        if (!TextUtils.isEmpty(et_pjtype.getText().toString().trim())) {
            params.put("model", et_pjtype.getText().toString().trim());
        }
        if (!TextUtils.isEmpty(et_content.getText().toString().trim())) {
            params.put("price", et_content.getText().toString().trim());
        } else {
            params.put("price", "");
        }
        HttpRequestUtils.httpRequest(this, "sendRepair", RequestValue.METHOD_ORDER_STATUS_SET, params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);

                switch (common.getStatus()) {
                    case "1":
                        setResult(RESULT_OK);
                        finish();
                        ToastUtil.showToast(ToBillActivity.this, "报价成功！");
                        break;
                    default:
                        ToastUtil.showToast(ToBillActivity.this, common.getInfo());
                        break;
                }

            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showToast(ToBillActivity.this, msg);
            }
        });
    }
}
