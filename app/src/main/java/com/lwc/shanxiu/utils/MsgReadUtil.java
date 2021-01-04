package com.lwc.shanxiu.utils;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.message.bean.HasMsg;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

public class MsgReadUtil {

    public static void hasMessage(final Activity context,final TextView tv_msg) {
        HttpRequestUtils.httpRequest(context, "查看未读消息", RequestValue.HAS_MESSAGE, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if (common.getStatus().equals("1")) {
                    DataSupport.deleteAll(HasMsg.class);
                    ArrayList<HasMsg> current = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<HasMsg>>() {
                    });
                    if (current != null && current.size() > 0) {
                        DataSupport.saveAll(current);
                        for (int i = 0; i < current.size(); i++) {
                            HasMsg hasMsg = current.get(i);
                            if ("6".equals(hasMsg.getType())) {
                                if (hasMsg.isHasMessage()) {
                                    int leaseOrderCount = hasMsg.getCount();
                                    SharedPreferencesUtils.setParam(context,"leaseOrderCount",leaseOrderCount);
                                    tv_msg.setVisibility(View.VISIBLE);
                                    if(leaseOrderCount > 99){
                                        tv_msg.setText("...");
                                    }else{
                                        tv_msg.setText(String.valueOf(leaseOrderCount));
                                    }

                                }else{
                                    SharedPreferencesUtils.setParam(context,"leaseOrderCount",0);
                                    tv_msg.setVisibility(View.GONE);
                                }
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
            }
        });
    }
}
