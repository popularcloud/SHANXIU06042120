package com.lwc.shanxiu.module.lease_parts.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.addressmanager.Address;
import com.lwc.shanxiu.module.addressmanager.AddressManagerActivity;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.lease_parts.adapter.LeaseConfirmAdapter;
import com.lwc.shanxiu.module.lease_parts.bean.ShopCarBean;
import com.lwc.shanxiu.module.wallet.PayPwdActivity;
import com.lwc.shanxiu.utils.DialogUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.widget.CustomDialog;

import org.byteam.superadapter.OnItemClickListener;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 确认租赁订单
 */
public class ConfirmLeaseOrderActivity extends BaseActivity {


    /**
     * 跳转地址管理
     */
    private final int INTENT_ADDRESS_MANAGER = 1000;
    @BindView(R.id.txtAddress)
    TextView txtAddress;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtPhone)
    TextView txtPhone;
    @BindView(R.id.btnConfirm)
    TextView btnConfirm;
    @BindView(R.id.rLayoutAddress)
    RelativeLayout rLayoutAddress;

    @BindView(R.id.lLayout0)
    LinearLayout lLayout0;

    @BindView(R.id.rv_goods)
    RecyclerView rv_goods;

    @BindView(R.id.tv_total_money)
    TextView tv_total_money;
    @BindView(R.id.tv_discount_money)
    TextView tv_discount_money;

    @BindView(R.id.tv_total_money_two)
    TextView tv_total_money_two;

    private LeaseConfirmAdapter adapter;
    List<ShopCarBean> submitBeans;

    private List<Address> addressesList = new ArrayList<>();
    private Address checkedAdd;

   // private InvoiceInformationDialog invoiceInformationDialog;

  //  private PayTypeDialog payTypeDialog;
    //实付金额
    private String payPrice;

    private SharedPreferencesUtils preferencesUtils = null;
    private User user = null;
    private Dialog dialog;
    private String allMoney;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_confirm_lease_order;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        preferencesUtils = SharedPreferencesUtils.getInstance(this);
        user = preferencesUtils.loadObjectData(User.class);
    }

    @Override
    protected void init() {

        setTitle("确认订单");
        showBack();

        //获取默认地址
        addressesList = DataSupport.findAll(Address.class);
        setDefaultAddress();

        rLayoutAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.gotoActivityForResult(ConfirmLeaseOrderActivity.this, AddressManagerActivity.class, INTENT_ADDRESS_MANAGER);
            }
        });
        bindRecycleView();
    }

    private void getGoodActivity() {

        if(submitBeans == null || submitBeans.size() < 1){
            return;
        }
        
        allMoney = "0";
        for(int i = 0;i < submitBeans.size(); i++){
            ShopCarBean shopCarBean = submitBeans.get(i);
            String oneMonthprices = Utils.cheng(shopCarBean.getGoodsPrice(),String.valueOf(shopCarBean.getGoodsNum()));
            allMoney = Utils.jia(allMoney,oneMonthprices);
        }
        String moneyStr = Utils.getMoney(Utils.chu(allMoney,"100"));
        SpannableStringBuilder spannableStringBuilder = Utils.getSpannableStringBuilder(1,moneyStr.length() - 2,"￥"+ moneyStr,24,true);
        tv_total_money_two.setText(spannableStringBuilder);

    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

    }

    private void bindRecycleView() {

        String shopCarBeans = getIntent().getStringExtra("shopCarBeans");
        submitBeans = JsonUtil.parserGsonToArray(shopCarBeans,new TypeToken<ArrayList<ShopCarBean>>(){});

        getGoodActivity();
        rv_goods.setLayoutManager(new LinearLayoutManager(ConfirmLeaseOrderActivity.this));
        adapter = new LeaseConfirmAdapter(ConfirmLeaseOrderActivity.this, submitBeans, R.layout.item_confirm_order);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
            }
        });
        rv_goods.setAdapter(adapter);

        if(submitBeans != null){
            String total = "0";
            for(ShopCarBean shopCarBean : submitBeans){
                String singleMoney =Utils.cheng(shopCarBean.getGoodsPrice(),String.valueOf(shopCarBean.getGoodsNum()));
                if("2".equals(shopCarBean.getPayType())){
                    singleMoney = Utils.cheng(singleMoney,"3");
                }
                total = Utils.jia(total,singleMoney);
            }

            tv_total_money.setText("￥"+Utils.getMoney(Utils.chu(total,"100")));
        }
    }

    /**
     * 设置默认地址
     */
    private void setDefaultAddress() {
        for (int i = 0; i < addressesList.size(); i++) {
            Address address = addressesList.get(i);
            if (address.getIsDefault() == 1) {
                lLayout0.setVisibility(View.VISIBLE);
                checkedAdd = address;
                txtName.setText("" + checkedAdd.getContactName());
                txtPhone.setText(checkedAdd.getContactPhone());
                txtAddress.setText(checkedAdd.getProvinceName() + checkedAdd.getCityName()+ checkedAdd.getContactAddress().replace("_", ""));
                break;
            }
        }
        if(addressesList == null || addressesList.size() == 0){
            checkedAdd = null;
            lLayout0.setVisibility(View.GONE);
            txtName.setText("");
            txtPhone.setText("");
            txtAddress.setText("点击添加地址");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
             if(requestCode == INTENT_ADDRESS_MANAGER){
                checkedAdd = (Address) data.getSerializableExtra("address");
                txtName.setVisibility(View.VISIBLE);
                txtPhone.setVisibility(View.VISIBLE);
                txtName.setText("" + checkedAdd.getContactName());
                txtPhone.setText(checkedAdd.getContactPhone());
                txtAddress.setText("" + checkedAdd.getContactAddress().replace("_", ""));
           }else{
            if(requestCode == INTENT_ADDRESS_MANAGER){
                //获取默认地址
                addressesList = DataSupport.findAll(Address.class);
                setDefaultAddress();
            }
        }
        }else{
            //获取默认地址
            addressesList = DataSupport.findAll(Address.class);
            setDefaultAddress();
        }


    }

    @OnClick({R.id.btnConfirm})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.btnConfirm:
               if (Utils.isFastClick(3000)) {
                   return;
               }
               submitOrder();
                break;
        }
    }


    private void submitOrder(){


/*        LinearLayout linearLayout = (LinearLayout) adapter.getView(0,null,null);

        EditText editText = linearLayout.findViewById(R.id.et_message);
        String message = editText.getText().toString();
        Log.d("联网成功!",message);*/


        String ids = submitBeans.get(0).getCarId();
        Map<String,String> params = new HashMap<>();

        if(TextUtils.isEmpty(ids)){ //
            ids = submitBeans.get(0).getGoodsId();
            params.put("goods_id",ids);
            params.put("goods_num",String.valueOf(submitBeans.get(0).getGoodsNum()));
            if(!TextUtils.isEmpty(submitBeans.get(0).getRemark())){
                params.put("user_message",submitBeans.get(0).getRemark());
            }

        }else{
            updateCar(submitBeans.get(0).getCarId(),submitBeans.get(0).getRemark());
            for(int i = 1;i < submitBeans.size();i++){
                ShopCarBean shopCarBean = submitBeans.get(i);
                ids = ids + ","+submitBeans.get(i).getCarId();

                if(!TextUtils.isEmpty(shopCarBean.getRemark())){
                    updateCar(shopCarBean.getCarId(),shopCarBean.getRemark());
                }

            }
            params.put("in_car_id",ids);
        }

        if(checkedAdd == null){
            ToastUtil.showToast(this,"请选择地址");
            return;
        }


        params.put("address_id",checkedAdd.getUserAddressId());

        HttpRequestUtils.httpRequest(this, "确认商品", RequestValue.PARTSMANAGE_ORDER_SAVE, params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if("1".equals(common.getStatus())){
                    ToastUtil.showToast(ConfirmLeaseOrderActivity.this,"支付成功！");
                    Bundle bundle = new Bundle();
                    bundle.putString("price",allMoney);
                    IntentUtil.gotoActivity(ConfirmLeaseOrderActivity.this,PaySuccessActivity.class,bundle);
                    finish();
                }else{
                    ToastUtil.showToast(ConfirmLeaseOrderActivity.this,common.getInfo());
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    /**
     * 更新购物车提交过来的备注
     */
    private void updateCar(String carId,String message){
        Map<String,String> params = new HashMap<>();
        params.put("car_id",carId);
        params.put("user_message",message);
        HttpRequestUtils.httpRequest(ConfirmLeaseOrderActivity.this, "修改购物车", RequestValue.GET_PARTSMANAGE_MODPARTSGOODSCAR, params, "POST", new HttpRequestUtils.ResponseListener() {

            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        //ToastUtil.showToast(getActivity(),common.getInfo());
                        // mBGARefreshLayout.beginRefreshing();
                        break;
                    default:
                        ToastUtil.showToast(ConfirmLeaseOrderActivity.this,common.getInfo());
                        break;
                }

            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }
}
