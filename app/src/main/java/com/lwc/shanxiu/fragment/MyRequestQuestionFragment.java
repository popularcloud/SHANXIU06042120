package com.lwc.shanxiu.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.message.ui.KnowledgeSearchActivity;
import com.lwc.shanxiu.module.question.bean.QuestionIndexBean;
import com.lwc.shanxiu.module.question.ui.activity.MyAnswerActivity;
import com.lwc.shanxiu.module.question.ui.activity.MyRequestActivity;
import com.lwc.shanxiu.module.question.ui.activity.PublishQuestionActivity;
import com.lwc.shanxiu.module.question.ui.activity.QuestionDetailActivity;
import com.lwc.shanxiu.module.question.ui.activity.QuestionSearchActivity;
import com.lwc.shanxiu.module.question.ui.adapter.RequestQuestionAdapter;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 我的
 */
public class MyRequestQuestionFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.iv_request)
    ImageView iv_request;
    @BindView(R.id.iv_answer)
    ImageView iv_answer;
    @BindView(R.id.iv_question)
    ImageView iv_question;
    @BindView(R.id.iv_no_data)
    ImageView iv_no_data;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;

    List<QuestionIndexBean> beanList = new ArrayList<>();
    private RequestQuestionAdapter adapter;
    private int page = 1;
    private String search_txt = "";


    @Override
    protected View getViews() {
        return View.inflate(getActivity(), R.layout.activity_my_request_question, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        BGARefreshLayoutUtils.initRefreshLayout(getContext(), mBGARefreshLayout);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgBack.setVisibility(View.GONE);
        tv_search.setVisibility(View.GONE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_search.getLayoutParams();
        layoutParams.setMargins(30,0,30,0);
        ll_search.setLayoutParams(layoutParams);

        //搜索跳转到另一个页面
        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.gotoActivityForResult(getContext(), QuestionSearchActivity.class, ServerConfig.REQUESTCODE_QUESTION);
            }
        });

        et_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    IntentUtil.gotoActivityForResult(getContext(), QuestionSearchActivity.class, ServerConfig.REQUESTCODE_QUESTION);
                }
            }
        });

        ll_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.gotoActivityForResult(getContext(), QuestionSearchActivity.class, ServerConfig.REQUESTCODE_QUESTION);
            }
        });

        bindRecycleView();

        recyclerView.setAdapter(adapter);

        //刷新控件监听器
        mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                page = 1;
                loadData();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                    page++;
                    loadData();
                return true;
            }
        });

        mBGARefreshLayout.beginRefreshing();

        iv_no_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search.setText("");
                mBGARefreshLayout.beginRefreshing();
                iv_no_data.setVisibility(View.GONE);
                mBGARefreshLayout.setVisibility(View.VISIBLE);
            }
        });

    }

    private void clearTextAndTagSearch(){
        if(!TextUtils.isEmpty(search_txt)){
            search_txt = "";
            et_search.setText("");
        }
    }

    public void onSearched(String serachKey){
        if(TextUtils.isEmpty(serachKey)){
            ToastUtil.showToast(getActivity(),"请输入您想要搜索的关键字");
            return;
        }
        et_search.setText(serachKey);
        mBGARefreshLayout.beginRefreshing();
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {

    }

    private void bindRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RequestQuestionAdapter(getActivity(), beanList, R.layout.item_request_question_2);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("quesionId", adapter.getItem(position).getQuesionId());
                IntentUtil.gotoActivity(getActivity(), QuestionDetailActivity.class, bundle);
                //记录点击了item
                SharedPreferencesUtils.setParam(getActivity(),"itemPosition",position);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateReadAcount();
    }

    /**
     * 更新浏览次数
     */
    private void updateReadAcount(){
        int position = (int) SharedPreferencesUtils.getParam(getActivity(),"itemPosition",-1);
        int viewCount = (int) SharedPreferencesUtils.getParam(getActivity(),"addViewCount",0);
        if(adapter != null && position != -1 && viewCount != 0 && beanList != null && adapter.getData().size() > position){
            adapter.getData().get(position).setBrowseNum(viewCount);
            adapter.notifyItemChanged(position);

            SharedPreferencesUtils.setParam(getActivity(),"itemPosition",-1);
            SharedPreferencesUtils.setParam(getActivity(),"addViewCount",0);
        }
    }


    @OnClick({R.id.tv_search,R.id.iv_request,R.id.iv_question,R.id.iv_answer})
    public void onBtnClick(View v){
        switch (v.getId()){
            case R.id.tv_search:
                search_txt = et_search.getText().toString().trim();
                if(TextUtils.isEmpty(search_txt)){
                    ToastUtil.showToast(getActivity(),"请输入要搜索的内容");
                    return;
                }
                mBGARefreshLayout.beginRefreshing();
                break;
            case R.id.iv_request:
                Bundle bundle = new Bundle();
                bundle.putString("quesionId","");
                IntentUtil.gotoActivity(getActivity(), PublishQuestionActivity.class,bundle);
                break;
            case R.id.iv_question:
              // Bundle bundle2 = new Bundle();
                IntentUtil.gotoActivity(getActivity(), MyRequestActivity.class);
                break;
            case R.id.iv_answer:
                IntentUtil.gotoActivity(getActivity(), MyAnswerActivity.class);
                break;
        }
    }

    public Drawable getDrawable(int d) {
        Drawable drawable = getActivity().getResources().getDrawable(d);
        drawable.setBounds(0,0,drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

    @Override
    protected void widgetListener() {

    }

    private void loadData(){
        search_txt = et_search.getText().toString().trim();
        Map<String, String> map = new HashMap<>();
        map.put("wd", search_txt);
        map.put("curPage", ""+page);
        HttpRequestUtils.httpRequest(getActivity(), "问答首页", RequestValue.QUESION_INDEX, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        List<QuestionIndexBean> beanList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"),new TypeToken<ArrayList<QuestionIndexBean>>(){});
                        loadDataToAdapter(beanList);
                        break;
                    default:
                        ToastUtil.showToast(getActivity(), common.getInfo());
                        break;
                }
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
            }

            @Override
            public void returnException(Exception e, String msg) {
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
            }
        });
    }

    private void loadDataToAdapter(List<QuestionIndexBean> datas){
        if(page == 1){
            beanList = datas;
            adapter.replaceAll(datas);
            BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);

            if(datas == null || datas.size() < 1){
                //ToastUtil.showToast(KnowledgeBaseActivity.this,"暂无数据");
                iv_no_data.setVisibility(View.VISIBLE);
                mBGARefreshLayout.setVisibility(View.GONE);
            }else{
                iv_no_data.setVisibility(View.GONE);
                mBGARefreshLayout.setVisibility(View.VISIBLE);
            }
        }else{
            BGARefreshLayoutUtils.endLoadingMore(mBGARefreshLayout);
            if(datas == null || datas.size() < 1){
                ToastUtil.showToast(getActivity(),"没有更多数据了");
                return;
            }
            beanList.addAll(datas);
            adapter.addAll(datas);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser  && getActivity() != null){
            ImmersionBar.with(getActivity())
                    .statusBarColor(R.color.white)
                    .statusBarDarkFont(true)
                    .navigationBarColor(R.color.white).init();
        }
    }



    @Override
    protected void init() {
    }

    @Override
    public void initGetData() {


    }
}
