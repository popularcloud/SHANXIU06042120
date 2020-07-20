package com.lwc.shanxiu.module.authentication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.BaseFragment;
import com.lwc.shanxiu.module.authentication.activity.TopicActivity;
import com.lwc.shanxiu.module.authentication.bean.TopicBean;
import com.lwc.shanxiu.module.authentication.bean.TrainBean;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TopicFragment extends BaseFragment {

    @BindView(R.id.tv_topic_type)
    TextView tv_topic_type;
    @BindView(R.id.tv_topic_num)
    TextView tv_topic_num;

    @BindView(R.id.tv_answerA)
    TextView tv_answerA;
    @BindView(R.id.tv_answerA_txt)
    TextView tv_answerA_txt;

    @BindView(R.id.tv_answerB)
    TextView tv_answerB;
    @BindView(R.id.tv_answerB_txt)
    TextView tv_answerB_txt;

    @BindView(R.id.tv_answerC)
    TextView tv_answerC;
    @BindView(R.id.tv_answerC_txt)
    TextView tv_answerC_txt;

    @BindView(R.id.tv_answerD)
    TextView tv_answerD;
    @BindView(R.id.tv_answerD_txt)
    TextView tv_answerD_txt;

    @BindView(R.id.ll_c)
    LinearLayout ll_c;
    @BindView(R.id.ll_d)
    LinearLayout ll_d;

    @BindView(R.id.tv_title)
    TextView tv_title;

    private SharedPreferencesUtils preferencesUtils = null;
    private int topicPosition;
    private String parentId;

    private int topicType = 1;

    private StringBuilder stringBuilder = new StringBuilder();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEngines(view);
        init();
        setListener();

        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true).init();
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    public void init() {
        topicPosition = getArguments().getInt("topicPosition");
        parentId = getArguments().getString("parentId");

        tv_topic_num.setText((topicPosition+1)+"/100");

        loadData();
    }

    @Override
    public void initEngines(View view) {
        preferencesUtils = SharedPreferencesUtils.getInstance(getContext());
    }

    @Override
    public void getIntentData() {

    }

    @Override
    public void setListener() {

    }

    private void loadData(){
        Map<String, String> map = new HashMap<>();
        map.put("parent_id", parentId);
        map.put("num",String.valueOf(topicPosition+1));
        HttpRequestUtils.httpRequest(getActivity(), "试卷题目", RequestValue.EXAMMANAGE_GETEXAMPAPERINFO, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        TopicBean topicBean = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"), TopicBean.class);

                        if(topicBean != null){
                            topicType = topicBean.getType();
                            switch (topicBean.getType()){
                                case 1:  //单选
                                    tv_topic_type.setText("单选题");
                                    tv_answerA_txt.setText(topicBean.getA());
                                    tv_answerB_txt.setText(topicBean.getB());
                                    tv_answerC_txt.setText(topicBean.getC());
                                    tv_answerD_txt.setText(topicBean.getD());
                                    if(TextUtils.isEmpty(topicBean.getC())){
                                        ll_c.setVisibility(View.GONE);
                                    }
                                    if(TextUtils.isEmpty(topicBean.getD())){
                                        ll_d.setVisibility(View.GONE);
                                    }
                                    break;
                                case 2: //多选
                                    tv_topic_type.setText("多选题");
                                    tv_answerA_txt.setText(topicBean.getA());
                                    tv_answerB_txt.setText(topicBean.getB());
                                    tv_answerC_txt.setText(topicBean.getC());
                                    tv_answerD_txt.setText(topicBean.getD());
                                    if(TextUtils.isEmpty(topicBean.getC())){
                                        ll_c.setVisibility(View.GONE);
                                    }
                                    if(TextUtils.isEmpty(topicBean.getD())){
                                        ll_d.setVisibility(View.GONE);
                                    }
                                    break;
                                case 3: //判断
                                    tv_topic_type.setText("判断题");
                                    tv_answerA_txt.setText(topicBean.getA());
                                    tv_answerB_txt.setText(topicBean.getB());
                                    ll_c.setVisibility(View.GONE);
                                    ll_d.setVisibility(View.GONE);
                                    break;
                            }

                            tv_title.setText(topicBean.getTitle());
                        }
                        break;
                    default:
                        ToastUtil.showToast(getActivity(), common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showToast(getActivity(), msg);
            }
        });
    }

    @OnClick({R.id.tv_answerA,R.id.tv_answerB,R.id.tv_answerC,R.id.tv_answerD})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.tv_answerA:
                if(topicType == 1 || topicType == 3){//单选 判断

                    tv_answerA.setBackgroundResource(R.drawable.circular_blue_shape);
                    tv_answerB.setBackgroundResource(R.drawable.circular_gray_line_shape);
                    tv_answerC.setBackgroundResource(R.drawable.circular_gray_line_shape);
                    tv_answerD.setBackgroundResource(R.drawable.circular_gray_line_shape);

                    tv_answerA.setTextColor(getActivity().getResources().getColor(R.color.white));
                    tv_answerB.setTextColor(getActivity().getResources().getColor(R.color.color_33));
                    tv_answerC.setTextColor(getActivity().getResources().getColor(R.color.color_33));
                    tv_answerD.setTextColor(getActivity().getResources().getColor(R.color.color_33));

                    ((TopicActivity)getActivity()).changeTopicAnswer(topicPosition+1,"A");
                    ((TopicActivity)getActivity()).viewPageNext(topicPosition+1);
                }else { //多选
                    if(tv_answerA.getTag() == null){
                        tv_answerA.setBackgroundResource(R.drawable.circular_blue_shape);
                        tv_answerA.setTextColor(getActivity().getResources().getColor(R.color.white));
                        tv_answerA.setTag("isSelect");
                        stringBuilder.append("A,");
                        Log.d("联网成功","msg"+stringBuilder.toString());
                        ((TopicActivity)getActivity()).changeTopicAnswer(topicPosition+1,stringBuilder.toString());
                    }else{
                        tv_answerA.setBackgroundResource(R.drawable.circular_gray_line_shape);
                        tv_answerA.setTextColor(getActivity().getResources().getColor(R.color.color_33));
                        tv_answerA.setTag(null);
                        int index = stringBuilder.indexOf("A,");
                        stringBuilder.delete(index,index+2);
                        Log.d("联网成功","msg"+stringBuilder.toString());
                        ((TopicActivity)getActivity()).changeTopicAnswer(topicPosition+1,stringBuilder.toString());
                    }
                }
                break;
            case R.id.tv_answerB:
                if(topicType == 1 || topicType == 3){//单选 判断

                    Log.d("联网成功","颜色id"+tv_answerA.getTextColors().toString());
                    Log.d("联网成功","背景id"+tv_answerA.getDrawingCacheBackgroundColor());
                    tv_answerA.setBackgroundResource(R.drawable.circular_gray_line_shape);
                    tv_answerB.setBackgroundResource(R.drawable.circular_blue_shape);
                    tv_answerC.setBackgroundResource(R.drawable.circular_gray_line_shape);
                    tv_answerD.setBackgroundResource(R.drawable.circular_gray_line_shape);

                    tv_answerA.setTextColor(getActivity().getResources().getColor(R.color.color_33));
                    tv_answerB.setTextColor(getActivity().getResources().getColor(R.color.white));
                    tv_answerC.setTextColor(getActivity().getResources().getColor(R.color.color_33));
                    tv_answerD.setTextColor(getActivity().getResources().getColor(R.color.color_33));

                    ((TopicActivity)getActivity()).changeTopicAnswer(topicPosition+1,"B");
                    ((TopicActivity)getActivity()).viewPageNext(topicPosition+1);
                }else { //多选
                    if(tv_answerB.getTag() == null){
                        tv_answerB.setBackgroundResource(R.drawable.circular_blue_shape);
                        tv_answerB.setTextColor(getActivity().getResources().getColor(R.color.white));
                        tv_answerB.setTag("isSelect");
                        stringBuilder.append("B,");
                        Log.d("联网成功","msg"+stringBuilder.toString());
                        ((TopicActivity)getActivity()).changeTopicAnswer(topicPosition+1,stringBuilder.toString());
                    }else{
                        tv_answerB.setBackgroundResource(R.drawable.circular_gray_line_shape);
                        tv_answerB.setTextColor(getActivity().getResources().getColor(R.color.color_33));
                        tv_answerB.setTag(null);
                        int index = stringBuilder.indexOf("B,");
                        stringBuilder.delete(index,index+2);
                        Log.d("联网成功","msg"+stringBuilder.toString());
                        ((TopicActivity)getActivity()).changeTopicAnswer(topicPosition+1,stringBuilder.toString());
                    }
                }
                break;
            case R.id.tv_answerC:
                if(topicType == 1 || topicType == 3){//单选 判断

                    tv_answerA.setBackgroundResource(R.drawable.circular_gray_line_shape);
                    tv_answerB.setBackgroundResource(R.drawable.circular_gray_line_shape);
                    tv_answerC.setBackgroundResource(R.drawable.circular_blue_shape);
                    tv_answerD.setBackgroundResource(R.drawable.circular_gray_line_shape);

                    tv_answerA.setTextColor(getActivity().getResources().getColor(R.color.color_33));
                    tv_answerB.setTextColor(getActivity().getResources().getColor(R.color.color_33));
                    tv_answerC.setTextColor(getActivity().getResources().getColor(R.color.white));
                    tv_answerD.setTextColor(getActivity().getResources().getColor(R.color.color_33));

                    ((TopicActivity)getActivity()).changeTopicAnswer(topicPosition+1,"C");
                    ((TopicActivity)getActivity()).viewPageNext(topicPosition+1);
                }else { //多选
                    if(tv_answerC.getTag() == null){
                        tv_answerC.setBackgroundResource(R.drawable.circular_blue_shape);
                        tv_answerC.setTextColor(getActivity().getResources().getColor(R.color.white));
                        tv_answerC.setTag("isSelect");
                        stringBuilder.append("C,");
                        Log.d("联网成功","msg"+stringBuilder.toString());
                        ((TopicActivity)getActivity()).changeTopicAnswer(topicPosition+1,stringBuilder.toString());
                    }else{
                        tv_answerC.setBackgroundResource(R.drawable.circular_gray_line_shape);
                        tv_answerC.setTextColor(getActivity().getResources().getColor(R.color.color_33));
                        tv_answerC.setTag(null);
                        int index = stringBuilder.indexOf("C,");
                        stringBuilder.delete(index,index+2);
                        Log.d("联网成功","msg"+stringBuilder.toString());
                        ((TopicActivity)getActivity()).changeTopicAnswer(topicPosition+1,stringBuilder.toString());
                    }
                }
                break;
            case R.id.tv_answerD:
                if(topicType == 1 || topicType == 3){//单选 判断

                    tv_answerA.setBackgroundResource(R.drawable.circular_gray_line_shape);
                    tv_answerA.setTextColor(getActivity().getResources().getColor(R.color.color_33));
                    tv_answerB.setTextColor(getActivity().getResources().getColor(R.color.color_33));
                    tv_answerC.setTextColor(getActivity().getResources().getColor(R.color.color_33));
                    tv_answerD.setTextColor(getActivity().getResources().getColor(R.color.white));
                    tv_answerB.setBackgroundResource(R.drawable.circular_gray_line_shape);
                    tv_answerC.setBackgroundResource(R.drawable.circular_gray_line_shape);
                    tv_answerD.setBackgroundResource(R.drawable.circular_blue_shape);

                    ((TopicActivity)getActivity()).changeTopicAnswer(topicPosition+1,"D");
                    ((TopicActivity)getActivity()).viewPageNext(topicPosition+1);
                }else { //多选
                    if(tv_answerD.getTag() == null){
                        tv_answerD.setBackgroundResource(R.drawable.circular_blue_shape);
                        tv_answerD.setTextColor(getActivity().getResources().getColor(R.color.white));
                        tv_answerD.setTag("isSelect");
                        stringBuilder.append("D,");
                        Log.d("联网成功","msg"+stringBuilder.toString());
                        ((TopicActivity)getActivity()).changeTopicAnswer(topicPosition+1,stringBuilder.toString());
                    }else{
                        tv_answerD.setBackgroundResource(R.drawable.circular_gray_line_shape);
                        tv_answerD.setTextColor(getActivity().getResources().getColor(R.color.color_33));
                        tv_answerD.setTag(null);
                        int index = stringBuilder.indexOf("D,");
                        stringBuilder.delete(index,index+2);
                        Log.d("联网成功","msg"+stringBuilder.toString());
                        ((TopicActivity)getActivity()).changeTopicAnswer(topicPosition+1,stringBuilder.toString());
                    }
                }
                break;
        }
    }
}
