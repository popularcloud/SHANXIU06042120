package com.lwc.shanxiu.module.user;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.interf.OnTagClickCallBack;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.DeviceType;
import com.lwc.shanxiu.module.bean.Tag;
import com.lwc.shanxiu.module.common_adapter.TagsAdapter;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SpaceItemDecoration;
import com.lwc.shanxiu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 登陆或注册页面
 *
 * @Description TODO
 * @date 2015年11月20日
 * @Copyright: lwc
 */
public class RegistTwoActivity extends BaseActivity {

    @BindView(R.id.rv_repair_pro)
    RecyclerView recyclerView;
    @BindView(R.id.ll_work)
    LinearLayout ll_work;
    @BindView(R.id.tv_work)
    TextView tv_work;
    @BindView(R.id.cb_work)
    CheckBox cb_work;
    @BindView(R.id.ll_electric)
    LinearLayout ll_electric;
    @BindView(R.id.tv_electric)
    TextView tv_electric;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.cb_electric)
    CheckBox cb_electric;
    private ArrayList<Tag> mTags = new ArrayList<>();
    private Map<String,String> params = new HashMap<>();
    private ArrayList<DeviceType> deviceTypes;
    private TagsAdapter tagsAdapter;
    private String selectDeviceModel;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_regist_two;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);

        String datas = getIntent().getStringExtra("params");
        if(!TextUtils.isEmpty(datas)){
            params = JsonUtil.parserGsonToMap(datas,new TypeToken<HashMap<String, String>>(){});
        }
        showBack();
    }

    @Override
    protected void init() {
        setTitle("注册");
        initRecyclerView();
    }

    private void initRecyclerView() {
        tagsAdapter = new TagsAdapter(this, mTags, new OnTagClickCallBack() {
            @Override
            public void onTagClickListener(Tag tag) {
                if (tag.isChecked()) {
                    tag.setChecked(false);
                }else{
                    tag.setChecked(true);
                }
                for (int i=0;i<mTags.size();i++) {
                    Tag t = mTags.get(i);
                    if(t.getLabelName().equals(tag.getLabelName())) {
                        mTags.set(i, tag);
                        break;
                    }
                }
                tagsAdapter.notifyDataSetChanged();
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        // rv_tags.addItemDecoration(new GridSpacingItemDecoration(3,24,true));
        recyclerView.addItemDecoration(new SpaceItemDecoration(Utils.dip2px(RegistTwoActivity.this,12),0, SpaceItemDecoration.GRIDLAYOUT));
        recyclerView.setAdapter(tagsAdapter);
    }

    @Override
    protected void initGetData() {
    }

    @Override
    protected void widgetListener() {
        cb_work.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cb_electric.setChecked(false);
                    tv_work.setTextColor(Color.WHITE);
                    tv_electric.setTextColor(Color.parseColor("#666666"));
                    ll_work.setBackgroundResource(R.drawable.btn_blue_select_shape);
                    ll_electric.setBackgroundResource(R.drawable.btn_gray_select_shape);
                    selectDeviceModel = "1";
                    getTypeAll(selectDeviceModel);
                }
            }
        });
        cb_electric.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cb_work.setChecked(false);
                    tv_electric.setTextColor(Color.WHITE);
                    tv_work.setTextColor(Color.parseColor("#666666"));
                    ll_work.setBackgroundResource(R.drawable.btn_gray_select_shape);
                    ll_electric.setBackgroundResource(R.drawable.btn_blue_select_shape);
                    selectDeviceModel = "3";
                    getTypeAll(selectDeviceModel);
                }
            }
        });

        ll_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!cb_work.isChecked()){
                    cb_work.setChecked(true);
                    cb_electric.setChecked(false);
                }
            }
        });
        ll_electric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!cb_electric.isChecked()){
                    cb_electric.setChecked(true);
                    cb_work.setChecked(false);
                }
            }
        });
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(params == null){
                    ToastUtil.showToast(RegistTwoActivity.this,"获取注册信息失败，请重新注册");
                    finish();
                    return;
                }

                if(TextUtils.isEmpty(selectDeviceModel)){
                    ToastUtil.showToast(RegistTwoActivity.this,"请选择您的服务商类型");
                    return;
                }
                params.put("device_type_mold",selectDeviceModel);

                StringBuilder company_maintenance_margin = new StringBuilder();
                for(int i = 0;i<mTags.size();i++){
                    if(mTags.get(i).isChecked()){
                        company_maintenance_margin.append(mTags.get(i).getLabelId()+",");
                    }
                }
                if(TextUtils.isEmpty(company_maintenance_margin.toString())){
                ToastUtil.showToast(RegistTwoActivity.this,"请选择您擅长的维修项目");
                    return;
                }
                params.put("company_maintenance_margin",company_maintenance_margin.toString());

                Bundle bundle = new Bundle();
                bundle.putString("url", ServerConfig.DOMAIN.replace("https", "http")+"/main/h5/agreement.html?isEngineer=1");
                bundle.putString("title", "用户注册协议");
                bundle.putString("params",JsonUtil.parserObjectToGson(params));
                IntentUtil.gotoActivity(RegistTwoActivity.this,UserAgreementActivity.class,bundle);
            }
        });
    }


    private void getTypeAll(String deviceMold) {
        HttpRequestUtils.httpRequest(this, "getDeviceType", RequestValue.GET_OLL_DEVICE_TYPE+"?deviceMold="+ deviceMold
                , null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        deviceTypes = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<DeviceType>>() {
                        });
                        if (deviceTypes != null && deviceTypes.size() > 0) {
                            mTags.clear();
                            for (int i=0; i<deviceTypes.size(); i++) {
                                if (deviceTypes.get(i).getDeviceTypeName() != null){
                                    Tag tag = new Tag();
                                    tag.setLabelId(deviceTypes.get(i).getDeviceTypeId());
                                    tag.setLabelName(deviceTypes.get(i).getDeviceTypeName());
                                    mTags.add(tag);
                                    tagsAdapter.notifyDataSetChanged();
                                }
                            }
                        }
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

}
