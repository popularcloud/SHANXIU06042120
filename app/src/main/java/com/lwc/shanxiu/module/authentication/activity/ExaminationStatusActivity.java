package com.lwc.shanxiu.module.authentication.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.authentication.adapter.AnswerTopicAdapter;
import com.lwc.shanxiu.module.authentication.adapter.AnswerTopicReturnAdapter;
import com.lwc.shanxiu.module.authentication.bean.AnswerReturnBean;
import com.lwc.shanxiu.module.authentication.bean.SubmitTopicBean;
import com.lwc.shanxiu.module.authentication.bean.TopicBean;
import com.lwc.shanxiu.module.authentication.widget.AnswerTopicrDialog;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.ToastUtil;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 考试状态
 */
public class ExaminationStatusActivity extends BaseActivity {

    @BindView(R.id.rv_myData)
    RecyclerView rv_myData;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.tv_score)
    TextView tv_score;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.iv_status)
    ImageView iv_status;

    private AnswerTopicReturnAdapter answerTopicAdapter;
    List<String> list = new ArrayList<>();

  //  private List<SubmitTopicBean> submitTopicBeans = new ArrayList<>(100);

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_examination_status;
    }

    @Override
    protected void findViews() {
        showBack();
        setTitle("成绩报告");
        setRightText("错题本","#1481ff", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("position",2);
                IntentUtil.gotoActivity(ExaminationStatusActivity.this,AuthenticationMainActivity.class,bundle);
            }
        });
        initRecycleView();
    }

    private void initRecycleView() {

        for(int i = 0; i < 100;i++){
            list.add(i+"");
        }

        String answerDetail = getIntent().getStringExtra("answerDetail");
        if(answerDetail != null){
            AnswerReturnBean answerReturnBean = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(answerDetail, "data"), AnswerReturnBean.class);

            if(answerReturnBean != null){
                answerTopicAdapter = new AnswerTopicReturnAdapter(this, list,answerReturnBean.getPaper(),R.layout.item_answer_topic);

                answerTopicAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int viewType, int position) {

                    }
                });
                rv_myData.setLayoutManager(new GridLayoutManager(this,7));
                rv_myData.setAdapter(answerTopicAdapter);

                 String goodsNameStr = answerReturnBean.getScore()+"分";

                SpannableStringBuilder showGoodsName = Utils.getSpannableStringBuilder(goodsNameStr.length()-1, goodsNameStr.length(),getResources().getColor(R.color.btn_blue_nomal), goodsNameStr, 12, false);

                tv_score.setText(goodsNameStr);
                if(answerReturnBean.getPass() == 1){ //通过考试
                    tv_status.setText("恭喜通过");
                    ImageLoaderUtil.getInstance().displayFromLocal(ExaminationStatusActivity.this,iv_status,R.drawable.ic_test_pass);
                    tv_submit.setText("申请认证");
                    tv_submit.setVisibility(View.VISIBLE);
                    tv_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            apply();
                        }
                    });
                }else{
                    tv_status.setText("很遗憾，未通过!");
                    ImageLoaderUtil.getInstance().displayFromLocal(ExaminationStatusActivity.this,iv_status,R.drawable.ic_test_no_pass);
                    tv_submit.setText("重考");
                    tv_submit.setVisibility(View.VISIBLE);
                    tv_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            Bundle bundle = new Bundle();
                            bundle.putInt("position",1);
                            IntentUtil.gotoActivity(ExaminationStatusActivity.this,AuthenticationMainActivity.class,bundle);
                        }
                    });
                }
            }
        }

    }

    private void apply(){
        HttpRequestUtils.httpRequest(ExaminationStatusActivity.this, "申请认证", RequestValue.EXAMMANAGE_APPLYSECRECY, null, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        ToastUtil.showToast(ExaminationStatusActivity.this, common.getInfo());
                        finish();
                        Bundle bundle = new Bundle();
                        bundle.putInt("position",1);
                        IntentUtil.gotoActivity(ExaminationStatusActivity.this,AuthenticationMainActivity.class,bundle);
                        break;
                    default:
                        ToastUtil.showToast(ExaminationStatusActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showToast(ExaminationStatusActivity.this, msg);
            }
        });
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {
    }
}
