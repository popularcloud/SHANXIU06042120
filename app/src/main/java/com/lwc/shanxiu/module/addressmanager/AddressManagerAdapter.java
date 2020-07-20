package com.lwc.shanxiu.module.addressmanager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.utils.DialogUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.ProgressUtils;
import com.lwc.shanxiu.widget.CustomDialog;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 何栋 on 2017/10/15.
 * 294663966@qq.com
 * 地址管理
 */
public class AddressManagerAdapter extends SuperAdapter<Address> {

    private List<Address> addresses;
    private Context context;
    private ProgressUtils progressUtils;
    private SharedPreferencesUtils preferencesUtils;
    private User user;

    public AddressManagerAdapter(Context context, List<Address> items, int layoutResId) {
        super(context, items, layoutResId);
        this.addresses = items;
        this.context = context;
        progressUtils = new ProgressUtils();
        preferencesUtils = SharedPreferencesUtils.getInstance(context);
        user = preferencesUtils.loadObjectData(User.class);
    }

    @Override
    public SuperViewHolder onCreate(View convertView, ViewGroup parent, int viewType) {
        final SuperViewHolder holder = super.onCreate(convertView, parent, viewType);

        holder.findViewById(R.id.lLayoutAmend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Bundle bundle = new Bundle();
                bundle.putSerializable("address", addresses.get(position));
                IntentUtil.gotoActivity(context, AddAddressActivity.class, bundle);
            }
        });

        holder.findViewById(R.id.LLayoutDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Address address = addresses.get(holder.getAdapterPosition());
                deleteAddress(address.getUserAddressId());
            }
        });

        final CheckBox checkBox = holder.getView(R.id.cBoxDefault);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Address address = addresses.get(holder.getAdapterPosition());
                setDefault(address.getUserAddressId());
            }
        });
        return holder;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Address item) {

        String name = item.getContactName();
        String phone = item.getContactPhone();
        String address = item.getContactAddress().replace("_","");
        if (name != null && !TextUtils.isEmpty(name)) {
            holder.setText(R.id.txtName,name);
        } else {
            holder.setText(R.id.txtName, "暂无");
        }

        if (!TextUtils.isEmpty(phone)) {
            holder.setText(R.id.txtPhone, phone);
        } else {
            holder.setText(R.id.txtPhone, "暂无");
        }

        if (!TextUtils.isEmpty(address)) {
        /*    String p = "";
            if (!TextUtils.isEmpty(item.getProvinceName())) {
                p=item.getProvinceName();
            }
            if (!TextUtils.isEmpty(item.getCityName())) {
                p=p+item.getCityName();
            }
            if (!TextUtils.isEmpty(item.getTownName())) {
                p=p+item.getTownName();
            }*/
            holder.setText(R.id.txtAddress, address);
        } else {
            holder.setText(R.id.txtPhone, "暂无");
        }

        int isDefault = item.getIsDefault();
        switch (isDefault) {
            case 1:
                holder.setChecked(R.id.cBoxDefault, true);
                break;
            case 0:
                holder.setChecked(R.id.cBoxDefault, false);
                break;
        }
    }

    private void deleteAddress(final String ua_id) {
        DialogUtil.showMessageDg(context, "温馨提示", "确定删除该地址吗？", new CustomDialog.OnClickListener() {

            @Override
            public void onClick(CustomDialog dialog, int id, Object object) {
                progressUtils.showCustomProgressDialog(context);
                HttpRequestUtils.httpRequest((Activity) context, "deleteAddress", RequestValue.DELETE_ADDRESS+ua_id, null, "POST", new HttpRequestUtils.ResponseListener() {
                    @Override
                    public void getResponseData(String response) {
                        Common common = JsonUtil.parserGsonToObject(response, Common.class);
                        switch (common.getStatus()) {
                            case "1":
                                getUserAddress();
                                break;
                            default:
                                ToastUtil.showLongToast(context, common.getInfo());
                                break;
                        }
                        progressUtils.dismissCustomProgressDialog();
                    }

                    @Override
                    public void returnException(Exception e, String msg) {
                        LLog.eNetError("deleteAddress   " + e.toString());
                        progressUtils.dismissCustomProgressDialog();
                        if (msg != null && !msg.equals("")) {
                            ToastUtil.showLongToast(context, msg);
                        }
                    }
                });
                dialog.dismiss();
            }
        });
    }


    /**
     * 设置默认
     *
     * @param ua_id
     */
    private void setDefault(String ua_id) {
        progressUtils.showCustomProgressDialog(context);
        Map<String, String> map = new HashMap<>();
        map.put("user_address_id", ua_id);
        map.put("is_default", "1");
        HttpRequestUtils.httpRequest((Activity) context, "setDefault", RequestValue.ADD_OR_AMEND_ADDRESS, map, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        getUserAddress();
                        break;
                    default:
                        ToastUtil.showLongToast(context, common.getInfo());
                        break;
                }
                progressUtils.dismissCustomProgressDialog();
            }

            @Override
            public void returnException(Exception e, String msg) {
                progressUtils.dismissCustomProgressDialog();
                if (msg != null && !msg.equals("")) {
                    ToastUtil.showLongToast(context, msg);
                }
            }
        });
    }

    /**
     * 获取用户地址
     */
    private void getUserAddress() {
        HttpRequestUtils.httpRequest((Activity) context, "getUserAddress", RequestValue.GET_USER_ADDRESS, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        List<Address> addresses = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<Address>>() {
                        });
                        DataSupport.deleteAll(Address.class);
                        DataSupport.saveAll(addresses);
                        replaceAll(addresses);
                        break;
                    default:
                        ToastUtil.showLongToast(context, common.getInfo());
                        break;
                }
                progressUtils.dismissCustomProgressDialog();
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError("getUserAddress   " + e.toString());
                progressUtils.dismissCustomProgressDialog();
                if (msg != null && !msg.equals("")) {
                    ToastUtil.showLongToast(context, msg);
                }
            }
        });
    }
}