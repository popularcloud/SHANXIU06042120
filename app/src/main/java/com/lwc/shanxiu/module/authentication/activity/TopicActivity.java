package com.lwc.shanxiu.module.authentication.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.authentication.adapter.MyPagerAdapter;
import com.lwc.shanxiu.module.authentication.bean.SubmitTopicBean;
import com.lwc.shanxiu.module.authentication.bean.TopicBean;
import com.lwc.shanxiu.module.authentication.widget.AnswerTopicrDialog;
import com.lwc.shanxiu.module.bean.Parts;
import com.lwc.shanxiu.utils.CommonUtils;
import com.lwc.shanxiu.utils.DialogUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.widget.CommonDialog;
import com.lwc.shanxiu.widget.CustomDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 题目
 */
public class TopicActivity extends BaseActivity {

    @BindView(R.id.ll_answer_topic)
    LinearLayout ll_answer_topic;
    @BindView(R.id.vp_topics)
    ViewPager vp_topics;

    List<String> list = new ArrayList<>();
    private AnswerTopicrDialog answerTopicrDialog;
    private MyPagerAdapter myPagerAdapter;
    private String parentId;

    private List<SubmitTopicBean> submitTopicBeans = new ArrayList<>(100);

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_topic;
    }

    @Override
    protected void findViews() {
        showBack();
        setTitle("考题");
    }

    @Override
    protected void init() {
        parentId = getIntent().getStringExtra("parentId");
        initViewPager();
    }

    private void initViewPager(){
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),parentId);
        vp_topics.setAdapter(myPagerAdapter);
    }

    @Override
    protected void initGetData() {
        for(int i = 0; i < 100;i++){
            list.add((i+1)+"");
        }
    }

    @Override
    protected void widgetListener() {
        vp_topics.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.ll_answer_topic})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.ll_answer_topic:
                answerTopicrDialog = new AnswerTopicrDialog(TopicActivity.this, list, submitTopicBeans,new AnswerTopicrDialog.CallBack() {
                    @Override
                    public void onSubmit(int position) {
                        if(position == -1){
                            answerTopicrDialog.dismiss();

                            DialogUtil.showMessageDg(TopicActivity.this, "温馨提示", "交卷后将不能修改答案\n，分数将会记录下来", new CustomDialog.OnClickListener() {

                                @Override
                                public void onClick(CustomDialog dialog, int id, Object object) {
                                    dialog.dismiss();
                                    submitTest();
                                }
                            });


                        }else{
                            if(vp_topics != null){
                                vp_topics.setCurrentItem(position,true);
                                answerTopicrDialog.dismiss();
                            }
                        }
                    }
                });
                answerTopicrDialog.show();
                break;
        }
    }

    private void submitTest(){



      /*  StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for(int i = 0;i < submitTopicBeans.size();i++){
            SubmitTopicBean submitTopicBean = submitTopicBeans.get(i);
            stringBuilder.append("{'answer':'"+submitTopicBean.getAnswer()+"','num':'"+submitTopicBean.getNum()+"'}");
        }
        stringBuilder.append("]");*/

        JSONObject map = new JSONObject();
        try {
            JSONArray topicAnswerArray = new JSONArray();
            for (int i = 0; i < submitTopicBeans.size(); i++) {
                SubmitTopicBean submitTopicBean = submitTopicBeans.get(i);
                JSONObject o = new JSONObject();
                o.put("answer", submitTopicBean.getAnswer());
                o.put("num", submitTopicBean.getNum());
                topicAnswerArray.put(o);
            }
            map.put("parentId", parentId);
            map.put("saveExam",topicAnswerArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpRequestUtils.httpRequestJson(this, "提交试卷", RequestValue.EXAMMANAGE_SAVEEXAMPAPER,map, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        finish();
                        Bundle bundle = new Bundle();
                        bundle.putString("answerDetail",response);
                        IntentUtil.gotoActivity(TopicActivity.this,ExaminationStatusActivity.class,bundle);
                        break;
                    default:
                        ToastUtil.showToast(TopicActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showToast(TopicActivity.this, msg);
            }
        });
    }

    public void changeTopicAnswer(int num,String answer){
        boolean isHas = false;
        for(int i = 0;i < submitTopicBeans.size();i++){
            SubmitTopicBean submitTopicBean = submitTopicBeans.get(i);
            if(submitTopicBean.getNum() == num){
                submitTopicBean.setAnswer(answer);
                isHas = true;
            }
        }
        if(!isHas){
            submitTopicBeans.add(new SubmitTopicBean(num,answer));
        }
    }

    public void viewPageNext(int num){
        vp_topics.setCurrentItem(num,true);
    }

}
