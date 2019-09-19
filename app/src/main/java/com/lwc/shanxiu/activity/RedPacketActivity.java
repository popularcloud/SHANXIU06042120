package com.lwc.shanxiu.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.ActivityBean;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.utils.Constants;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.ToastUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RedPacketActivity extends BaseActivity {

    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.tv_money_unit)
    TextView tv_money_unit;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.rl_bg)
    RelativeLayout rl_bg;
    private ActivityBean activityBean;
    private boolean isClick;
    private String activityId;
    private String knowledgeId;
    private boolean isOpen = false;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_red_packet;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {100, 400, 100, 400}; // 停止 开启 停止 开启
        vibrator.vibrate(pattern, -1); //重复两次上面的pattern 如果只想震动一次，index设为-1
    }

    @Override
    protected void init() {
    }

    @Override
    protected void initGetData() {
        activityBean = (ActivityBean) getIntent().getSerializableExtra("activityBean");
        activityId =  getIntent().getStringExtra("activityId");
        knowledgeId = getIntent().getStringExtra("knowledgeId");
    }

    @Override
    protected void widgetListener() {
    }

    @OnClick({R.id.iv_close, R.id.rl_bg})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                setResult(Constants.RED_ID_RESULT);
                finish();
                break;
            case R.id.rl_bg:
                if(!isOpen){
                    openRedPacket();
                }else{
                    setResult(Constants.RED_ID_TO_RESULT);
                    finish();
                }
                break;
            default:
                break;
        }
    }

    public void openRedPacket() {
        if (Utils.isFastClick(1000)) {
            return;
        }
        if (isOpen){
            return;
        }
        Map<String, String> map = new HashMap<>();
        if(activityBean != null){
            map.put("activityId", activityBean.getActivityId());
        }else{
            map.put("activityId", activityId);
            map.put("knowledgeId", knowledgeId);
        }

        HttpRequestUtils.httpRequest(this, "openRedPacket", RequestValue.GET_RED_PACKET_MONEY, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        try {
                            if (isOpen){
                                return;
                            }
                            isOpen = true;
                            JSONObject obj = new JSONObject(JsonUtil.getGsonValueByKey(response, "data"));
                            String str = "";
                            double money = obj.optDouble("money");
                            if (money > 0) {
                                str = Utils.getMoney("" + money) + "元";
                                tv_money.setVisibility(View.VISIBLE);
                                tv_money.setText(Utils.getSpannableStringBuilder(0, str.length() - 1, getResources().getColor(R.color.money), str, 25));
                                rl_bg.setBackgroundResource(R.drawable.open_red_envelope_bg);
                            } else {
                                //TODO 未中奖处理
                                rl_bg.setBackgroundResource(R.drawable.red_envelope_ubg);
                            }
                        } catch (Exception e) {
                        }
                        break;
                    default:
                        isClick=false;
                        ToastUtil.showLongToast(RedPacketActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                isClick=false;
                ToastUtil.showLongToast(RedPacketActivity.this, msg);
            }
        });
    }

    public void finish() {
        super.finish();
//	    //关闭窗体动画显示  
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }
}
