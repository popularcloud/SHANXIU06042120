package com.lwc.shanxiu.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.ActivityBean;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.utils.Constants;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.ToastUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RedPacketActivity extends BaseActivity {

    @BindView(R.id.rl_open)
    LinearLayout rl_open;
    @BindView(R.id.img_close)
    Button img_close;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.iv_bg)
    ImageView iv_bg;
    @BindView(R.id.tv_gz)
    TextView tv_gz;
    private ActivityBean activityBean;
    private boolean isClick;
    private String activityId;
    private String knowledgeId;

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

        if(activityBean != null){
            tv_desc.setText(activityBean.getActivityName());
        }else{
            tv_desc.setText("文章奖励");
            tv_gz.setVisibility(View.GONE);
        }

    }

    @Override
    protected void widgetListener() {
    }

    @OnClick({R.id.img_close, R.id.btnFind, R.id.tv_open, R.id.tv_gz})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.img_close:
                setResult(Constants.RED_ID_RESULT);
                finish();
                break;
            case R.id.tv_open:
                if (!isClick) {
                    isClick = true;
                    openRedPacket();
                }
                break;
            case R.id.btnFind:
                setResult(Constants.RED_ID_TO_RESULT);
                finish();
                break;
            case R.id.tv_gz:
                Bundle bundle = new Bundle();
                bundle.putString("url", activityBean.getRuleUrl());
                bundle.putString("title", "活动规则");
                IntentUtil.gotoActivity(RedPacketActivity.this, InformationDetailsActivity.class, bundle);
                break;
            default:
                break;
        }
    }

    public void openRedPacket() {
        if (Utils.isFastClick(1000)) {
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
                            JSONObject obj = new JSONObject(JsonUtil.getGsonValueByKey(response, "data"));
                            String str = "";
                            double money = obj.optDouble("money");
                            img_close.setVisibility(View.VISIBLE);
                            if (money > 0) {
                                rl_open.setVisibility(View.VISIBLE);
                                str = Utils.getMoney("" + money) + "元现金红包";
                                tv_money.setText(Utils.getSpannableStringBuilder(0, str.length() - 5, getResources().getColor(R.color.money), str, 25));
                                iv_bg.setImageResource(R.drawable.invitation_activities_redbag_open);
                                tv_desc.setVisibility(View.GONE);
                            } else {
                                //TODO 未中奖处理
                                tv_gz.setVisibility(View.GONE);
                                tv_desc.setVisibility(View.GONE);
                                iv_bg.setBackgroundResource(R.drawable.red_envelope_ubg);
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
