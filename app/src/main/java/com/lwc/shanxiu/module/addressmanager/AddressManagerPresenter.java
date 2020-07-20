package com.lwc.shanxiu.module.addressmanager;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.ToastUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by 何栋 on 2017/10/15.
 * 294663966@qq.com
 * 地址管理
 */
public class AddressManagerPresenter {

    private final String TAG = "AddressManagerPresenter";
    private IAddressManagerView iAddressManagerView;
    private Context context;
    private BGARefreshLayout mBGARefreshLayout;
    private Activity activity;


    public AddressManagerPresenter(Context context, Activity activity, IAddressManagerView iAddressManagerView, BGARefreshLayout mBGARefreshLayout) {
        this.iAddressManagerView = iAddressManagerView;
        this.context = context;
        this.activity = activity;
        this.mBGARefreshLayout = mBGARefreshLayout;
    }

    public void getUserAddressList() {
        HttpRequestUtils.httpRequest(activity, "getUserAddressList", RequestValue.GET_USER_ADDRESS, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":

                        String data = JsonUtil.getGsonValueByKey(response, "data");
                        List<Address> addresses;
                        if(!TextUtils.isEmpty(data)){
                            addresses = JsonUtil.parserGsonToArray(data, new TypeToken<ArrayList<Address>>() {
                            });
                            DataSupport.deleteAll(Address.class);
                            DataSupport.saveAll(addresses);
                        }else{
                            addresses = new ArrayList<>();
                            DataSupport.deleteAll(Address.class);
                        }

                        iAddressManagerView.notifyData(addresses);
                        break;
                    default:
                        ToastUtil.showLongToast(context, common.getInfo());
                        break;
                }
                mBGARefreshLayout.endRefreshing();
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(TAG, e.toString());
                mBGARefreshLayout.endRefreshing();
            }
        });
    }
}
