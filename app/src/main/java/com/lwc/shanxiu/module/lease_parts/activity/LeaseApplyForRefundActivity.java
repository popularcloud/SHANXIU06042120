package com.lwc.shanxiu.module.lease_parts.activity;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.lease_parts.bean.OrderDetailBean;
import com.lwc.shanxiu.module.lease_parts.widget.CancelOrderDialog;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 申请退款
 */
public class LeaseApplyForRefundActivity extends BaseActivity {

    @BindView(R.id.ll_refund_reason)
    LinearLayout ll_refund_reason;
    @BindView(R.id.et_return_desc)
    EditText et_return_desc;
    @BindView(R.id.tv_submit)
    TextView tv_submit;

    @BindView(R.id.iv_header)
    ImageView iv_header;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_spece)
    TextView tv_spece;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_reason)
    TextView tv_reason;
    @BindView(R.id.tv_return_money)
    TextView tv_return_money;
    @BindView(R.id.imgRight)
    ImageView imgRight;

    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.ll_reduce)
    LinearLayout ll_reduce;
    @BindView(R.id.et_sum)
    EditText et_sum;
    @BindView(R.id.tv_msg)
    TextView tv_msg;

    @BindView(R.id.tv_reason_name)
    TextView tv_reason_name;
    @BindView(R.id.tv_desc_name)
    TextView tv_desc_name;

    @BindView(R.id.tv_txt_num)
    TextView tv_txt_num;
    @BindView(R.id.ll_edit_num)
    LinearLayout ll_edit_num;

    private List<String> reasons = new ArrayList<>();

    private OrderDetailBean orderDetailBean;

    private int selPosition = -1;
    private CancelOrderDialog cancelOrderDialog;

    private int returnType = 0;  //0 退款（代发货） 1.退款（待收货） 2 退款退货 3.退租
    private String returnDesc = "请选择退款原因";
    private String returnTitle = "退款原因";
    private String singlePayPrice;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_apply_for_refund;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取未读租赁消息
       // MsgReadUtil.hasMessage(this,tv_msg);
    }

    @Override
    protected void init() {


        setRightImg(R.drawable.ic_more_black, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // PopupWindowUtil.showHeaderPopupWindow(LeaseApplyForRefundActivity.this,imgRight);
            }
        });

        ImmersionBar.with(LeaseApplyForRefundActivity.this)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true).init();

        orderDetailBean = (OrderDetailBean) getIntent().getSerializableExtra("orderDetailBean");
        returnType = getIntent().getIntExtra("returnType",0);

       // if(returnType == 0){ //未发货 退款
            reasons.add("多拍/拍错/不想要");
            reasons.add("商品少件/破损");
            reasons.add("质量问题");
            reasons.add("卖家发错货");
            reasons.add("其他原因");
            ll_reduce.setClickable(false);
            ll_add.setClickable(false);
            setTitle("申请退货");
            returnDesc = "请选择退货原因";
            returnTitle = "退货原因";
            ll_edit_num.setVisibility(View.GONE);
            tv_txt_num.setVisibility(View.VISIBLE);
        /*}else if(returnType == 1){ //已发货 退款
            reasons.add("多拍/拍错/不想要");
            reasons.add("未按约定时间发货");
            reasons.add("快递一直未送到");
            reasons.add("少货");
            reasons.add("其他原因");
            setTitle("申请退款");
            returnDesc = "请选择退款原因";
            returnTitle = "退款原因";
            ll_edit_num.setVisibility(View.GONE);
            tv_txt_num.setVisibility(View.VISIBLE);
        }else if(returnType == 2){ //已发货 退货退款
            reasons.add("多拍/拍错/不想要");
            reasons.add("商品少件/破损等");
            reasons.add("质量问题");
            reasons.add("卖家发错货");
            reasons.add("其他原因");
            setTitle("申请退货退款");
            returnDesc = "请选择退货退款原因";
            returnTitle = "退货退款原因";
            tv_reason_name.setText("退货退款原因");
            tv_desc_name.setText("退货说明");
            ll_edit_num.setVisibility(View.VISIBLE);
            tv_txt_num.setVisibility(View.GONE);
        }else if(returnType == 3){  //退租
            reasons.add("租用时间到期");
            reasons.add("不想续租了");
            reasons.add("商品损坏无法继续使用");
            reasons.add("商品不适用");
            reasons.add("其他原因");
            setTitle("申请退租");
            returnDesc = "请选择退租原因";
            returnTitle = "退租原因";
            tv_reason_name.setText("退租原因");
            tv_desc_name.setText("退租说明");
            ll_edit_num.setVisibility(View.VISIBLE);
            tv_txt_num.setVisibility(View.GONE);
        }*/
        loadToUI();
    }


    private void loadToUI(){
        if(orderDetailBean != null){
            ImageLoaderUtil.getInstance().displayFromNetDCircular(this,orderDetailBean.getGoodsImg(),iv_header,R.drawable.image_default_picture);

            String goodsName = orderDetailBean.getGoodsName();
          //  String goodsNameStr = "租赁  " + goodsName;
           // SpannableStringBuilder showGoodsName = Utils.getSpannableStringBuilder(0, 2, LeaseApplyForRefundActivity.this.getResources().getColor(R.color.transparent), goodsNameStr, 10, true);
            tv_title.setText(goodsName);

            String needShowMoney = "￥"+Utils.getMoney(Utils.chu(orderDetailBean.getGoodsPrice(),"100"));
            SpannableStringBuilder showGoodsPrice = Utils.getSpannableStringBuilder(1, needShowMoney.length() - 2, getResources().getColor(R.color.black), needShowMoney, 15, true);
            tv_price.setText(showGoodsPrice);

            singlePayPrice = Utils.chu(orderDetailBean.getPayPrice(),String.valueOf(orderDetailBean.getGoodsNum()));

            tv_return_money.setText("￥"+Utils.getMoney(Utils.cheng(Utils.chu(singlePayPrice,"100"),String.valueOf(orderDetailBean.getGoodsRealNum()))));
           // tv_reason.setText(reasons.get(selPosition));
            tv_spece.setText(orderDetailBean.getLeaseSpecs());
            et_sum.setText(String.valueOf(orderDetailBean.getGoodsRealNum()));
            tv_txt_num.setText("x"+orderDetailBean.getGoodsRealNum());
        }
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

    }


    @OnClick({R.id.ll_refund_reason, R.id.tv_submit,R.id.ll_add,R.id.ll_reduce})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.ll_refund_reason:
                if(cancelOrderDialog == null){
                    cancelOrderDialog = new CancelOrderDialog(this, new CancelOrderDialog.CallBack() {
                        @Override
                        public void onSubmit(int position) {
                            cancelOrderDialog.dismiss();
                            selPosition = position;
                            tv_reason.setText(reasons.get(selPosition));
                        }
                    },reasons,returnTitle,returnDesc,false);
                    cancelOrderDialog.show();
                }else{
                    cancelOrderDialog.show();
                }
                break;
            case R.id.tv_submit:
                String desc = et_return_desc.getText().toString().trim();
                if(selPosition == -1){
                    ToastUtil.showToast(LeaseApplyForRefundActivity.this,returnDesc);
                    return;
                }
                int sum = Integer.parseInt(et_sum.getText().toString().trim());
                applyReturnMoney(orderDetailBean.getOrderId(),String.valueOf(sum),reasons.get(selPosition),desc);
                break;
            case R.id.ll_add:
                int currentSumAdd = Integer.parseInt(et_sum.getText().toString().trim());
                if(currentSumAdd >= orderDetailBean.getGoodsRealNum()){
                    ToastUtil.showToast(this,"这已经是最大的退货数量了");
                    return;
                }else{
                    currentSumAdd = currentSumAdd + 1;
                    et_sum.setText(String.valueOf(currentSumAdd));
                    tv_return_money.setText("￥"+Utils.getMoney(Utils.cheng(Utils.chu(singlePayPrice,"100"),String.valueOf(currentSumAdd))));
                }
                break;
            case R.id.ll_reduce:
                int currentSum = Integer.parseInt(et_sum.getText().toString().trim());
                if(currentSum <= 1){
                    ToastUtil.showToast(this,"至少退一件商品");
                    return;
                }else{
                    currentSum = currentSum - 1;
                    et_sum.setText(String.valueOf(currentSum));
                    tv_return_money.setText("￥"+Utils.getMoney(Utils.cheng(Utils.chu(singlePayPrice,"100"),String.valueOf(currentSum))));
                }
                break;
        }
    }


    /**
     * 申请退款
     * @param orderId
     * @param number
     * @param reason
     * @param desc
     */
    private void applyReturnMoney(String orderId, String number, final String reason, String desc){

        Map<String,String> params = new HashMap<>();
        params.put("order_id",orderId);
        params.put("goods_num",number);
        params.put("apply_reason",reason);
        if(!TextUtils.isEmpty(desc)){
        params.put("instructions",desc);
        }

        String requestUrl = RequestValue.PARTSMANAGE_APPLYREFUNDGOODS;

        HttpRequestUtils.httpRequest(this, "用户申请退货", requestUrl, params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if("1".equals(common.getStatus())){
                    ToastUtil.showToast(LeaseApplyForRefundActivity.this,"退货成功");
                    String data = JsonUtil.getGsonValueByKey(response,"data");

                    Bundle bundle = new Bundle();
                    bundle.putString("branch_id",data);
                    if(returnType == 0 || returnType == 1 || returnType == 2){
                        bundle.putString("pageType","6");
                    }else{
                        bundle.putString("pageType","7");
                    }
                    IntentUtil.gotoActivity(LeaseApplyForRefundActivity.this,LeaseOrderRefundDetailActivity.class,bundle);
                }else{
                    ToastUtil.showToast(LeaseApplyForRefundActivity.this,common.getInfo());
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }
}
