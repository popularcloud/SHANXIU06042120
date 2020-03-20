package com.lwc.shanxiu.module.question.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.activity.ImageBrowseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.question.bean.PublishQuestionDetialBean;
import com.lwc.shanxiu.module.question.ui.adapter.QuestionBigMyGridViewPhotoAdpter;
import com.lwc.shanxiu.module.question.ui.adapter.QuestionDetailAdapter;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.MyGridView;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 问答详情
 */
public class QuestionDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_view_count)
    TextView tv_view_count;
    @BindView(R.id.tv_comment_count)
    TextView tv_comment_count;
    @BindView(R.id.tv_create_time)
    TextView tv_create_time;
    @BindView(R.id.tv_author)
    TextView tv_author;
    @BindView(R.id.tv_show_all)
    TextView tv_show_all;
    @BindView(R.id.tv_answer)
    TextView tv_answer;
    @BindView(R.id.gridview_show)
    MyGridView gridview_show;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_no_data)
    TextView iv_no_data;
    @BindView(R.id.tv_answer_count)
    TextView tv_answer_count;
    @BindView(R.id.ll_answer)
    LinearLayout ll_answer;
    @BindView(R.id.rl_question_details)
    RelativeLayout rl_question_details;

    /**
     * 问题id
     */
    private String quesionId;
    /**
     * 问题是否展示完全
     */
    private boolean questionIsShow = false;
    /**
     * 图片列表
     */
    private List<String> urlStrs = new ArrayList();
    /**
     * 图片列表适配器
     */
    private QuestionBigMyGridViewPhotoAdpter adpter;

    /**
     * 回答列表适配器
     */
    private QuestionDetailAdapter questionDetailAdapter;
    /**
     * 回答集合
     */
    List<PublishQuestionDetialBean.AnswersBean> answersBeans = new ArrayList<>();

    private boolean isSelfQuestion = false;
    private SharedPreferencesUtils preferencesUtils = null;
    private User user = null;
    private int ll_height = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_question_detail;
    }

    @Override
    protected void findViews() {
        setTitle("问题详情");
        showBack();
        setRightText("编辑","#1481ff", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(quesionId)){
                    Bundle bundle = new Bundle();
                    bundle.putString("quesionId",quesionId);
                    IntentUtil.gotoActivity(QuestionDetailActivity.this, PublishQuestionActivity.class,bundle);
                }else{
                    ToastUtil.showToast(QuestionDetailActivity.this,"数据加载中...,请稍后重试");
                }

            }
        });
        initRecycleView();
    }

    @Override
    protected void init() {
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isAnswerQuestion  = (boolean) SharedPreferencesUtils.getParam(QuestionDetailActivity.this,"isAnswerQuestion",false);
        if(isAnswerQuestion){
            SharedPreferencesUtils.setParam(QuestionDetailActivity.this,"isAnswerQuestion",false);
            quesionId =  getIntent().getStringExtra("quesionId");
            preferencesUtils = SharedPreferencesUtils.getInstance(this);
            user = preferencesUtils.loadObjectData(User.class);
            loadData();
        }

    }

    @Override
    protected void initGetData() {
        quesionId =  getIntent().getStringExtra("quesionId");
        preferencesUtils = SharedPreferencesUtils.getInstance(this);
        user = preferencesUtils.loadObjectData(User.class);
        loadData();
    }

    @Override
    protected void widgetListener() {
        adpter = new QuestionBigMyGridViewPhotoAdpter(this, urlStrs);
        adpter.setIsShowDel(false);
        gridview_show.setAdapter(adpter);
        gridview_show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(QuestionDetailActivity.this, ImageBrowseActivity.class);
                intent.putExtra("index", position);
                intent.putStringArrayListExtra("list", (ArrayList)adpter.getLists());
                startActivity(intent);
            }
        });
    }

    /**
     * 获取问题详情数据
     */
    private void loadData(){
        HttpRequestUtils.httpRequest(this, "获取编辑信息", RequestValue.QUESION_DETAILS + "?quesion_id=" + quesionId, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        PublishQuestionDetialBean questionDetialBean = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"), PublishQuestionDetialBean.class);

                        if(user != null){
                            if(user.getUserId().equals(questionDetialBean.getMaintenanceId())){ //自己的问题
                                QuestionDetailActivity.super.txtRight.setVisibility(View.VISIBLE);
                                isSelfQuestion = true;
                                ll_answer.setVisibility(View.GONE);
                                iv_no_data.setText("再调整一下问题，可以更快的获得答案");
                                questionDetialBean.setMaintenanceName("");
                            }else{//别人的问题
                                QuestionDetailActivity.super.txtRight.setVisibility(View.GONE);
                                isSelfQuestion = false;
                                ll_answer.setVisibility(View.VISIBLE);
                                if(questionDetialBean.getIsAnswer() == 0){ //是否已回答 0否 1是
                                    tv_answer.setClickable(true);
                                    tv_answer.setText("我来答");
                                }else{
                                    tv_answer.setClickable(false);
                                    tv_answer.setText("已作答");
                                    tv_answer.setTextColor(Color.parseColor("#999999"));
                                    tv_answer.setCompoundDrawables(null, null, null, null);
                                }
                                ll_answer.setVisibility(View.VISIBLE);
                                iv_no_data.setText("暂无回答，快来抢下第一个答案!");
                            }
                        }else{
                            return;
                        }

                        //显示问题详情
                        tv_title.setText(questionDetialBean.getQuesionTitle());
                        tv_content.setText(questionDetialBean.getQuesionDetail()+"");
                        tv_comment_count.setText(questionDetialBean.getQuesionMessage()+"个");

                        int viewCount = (questionDetialBean.getBrowseNum()+1);
                        tv_view_count.setText(viewCount+"次");
                        SharedPreferencesUtils.setParam(QuestionDetailActivity.this,"addViewCount",viewCount);

                        if(!TextUtils.isEmpty(questionDetialBean.getCreateTime())){
                            tv_create_time.setText(questionDetialBean.getCreateTime().substring(0,10));
                        }

                        if(TextUtils.isEmpty(questionDetialBean.getMaintenanceName())){
                            tv_author.setVisibility(View.GONE);
                        }else{
                            tv_author.setVisibility(View.VISIBLE);
                            tv_author.setText(questionDetialBean.getMaintenanceName());
                        }
                        String img = questionDetialBean.getQuesionImg();
                        if (img != null && !img.equals("")) {
                            urlStrs.clear();
                            String[] imgs = img.split(",");
                            for (int i=0;i<imgs.length; i++) {
                                urlStrs.add(imgs[i]);
                            }
                            adpter.setLists(urlStrs);
                            adpter.notifyDataSetChanged();

                            tv_show_all.setVisibility(View.VISIBLE);
                            gridview_show.setVisibility(View.GONE);
                        }else{
                            tv_show_all.setVisibility(View.GONE);
                            gridview_show.setVisibility(View.GONE);
                        }

                        ViewTreeObserver vto = tv_content.getViewTreeObserver();
                        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                tv_content.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                                ll_height =  tv_content.getHeight();
                                if(ll_height > 190){
                                    tv_content.setHeight(190);
                                    tv_show_all.setVisibility(View.VISIBLE);
                                }
                            }
                        });

                        answersBeans = questionDetialBean.getAnswers();
                        if(answersBeans == null || answersBeans.size() < 1){
                            tv_answer_count.setText("回答 0");
                            iv_no_data.setVisibility(View.VISIBLE);
                        }else{
                            tv_answer_count.setText("回答 "+answersBeans.size());
                            iv_no_data.setVisibility(View.GONE);
                            questionDetailAdapter.setIsSelefQuestion(isSelfQuestion);
                            questionDetailAdapter.replaceAll(questionDetialBean.getAnswers());
                            //questionDetailAdapter.notifyDataSetChanged();
                        }
                    break;
                    default:
                        ToastUtil.showToast(QuestionDetailActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    private void initRecycleView() {
   /*     recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);*/
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        questionDetailAdapter = new QuestionDetailAdapter(this, answersBeans, R.layout.item_question_detial_list, isSelfQuestion, new QuestionDetailAdapter.OnAdoptListener() {
            @Override
            public void onClick(int positon, String answerId) {  //被采纳
                onAnswerAdopt(answerId);
            }
        });

        questionDetailAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
            }
        });
        recyclerView.setAdapter(questionDetailAdapter);
    }



    private void onAnswerAdopt(String answerId){
        Map<String,String> params= new HashMap<>();
        params.put("answer_id",answerId);
        HttpRequestUtils.httpRequest(this, "采纳答案", RequestValue.QUESION_SELECTANSWER , params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        ToastUtil.showToast(QuestionDetailActivity.this,"采纳成功");
                        loadData();
                        break;
                    default:
                        ToastUtil.showToast(QuestionDetailActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    @OnClick({R.id.tv_show_all,R.id.tv_answer})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.tv_show_all:
                if(questionIsShow){
                    questionIsShow = false;
                    tv_show_all.setText("展开全部");
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_show_down_blue);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                            drawable.getMinimumHeight());
                    tv_show_all.setCompoundDrawables(null, null, drawable, null);
                    if(ll_height >=190){
                        tv_content.setHeight(190);
                    }else{
                        tv_content.setHeight(ll_height);
                    }
                    gridview_show.setVisibility(View.GONE);
                }else{
                    questionIsShow = true;
                    tv_show_all.setText("收起问题");
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_show_up_blue);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                            drawable.getMinimumHeight());
                    tv_show_all.setCompoundDrawables(null, null, drawable, null);

                    if(ll_height != 0){
                        tv_content.setHeight(ll_height);
                    }

                    if(urlStrs.size() > 0){
                        gridview_show.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.tv_answer:
                Bundle bundle = new Bundle();
                bundle.putString("quesionId", quesionId);
                IntentUtil.gotoActivity(QuestionDetailActivity.this, AnswerQuestionActivity.class, bundle);
                break;
        }
    }
}
