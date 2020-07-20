package com.lwc.shanxiu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.InformationDetailsActivity;
import com.lwc.shanxiu.activity.KnowledgeBaseActivity;
import com.lwc.shanxiu.activity.MainActivity;
import com.lwc.shanxiu.activity.NewMainActivity;
import com.lwc.shanxiu.activity.QuestionBaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.bean.ADInfo;
import com.lwc.shanxiu.module.lease_parts.activity.LeaseGoodsDetailActivity;
import com.lwc.shanxiu.module.lease_parts.activity.LeaseHomeActivity;
import com.lwc.shanxiu.module.lease_parts.bean.IndexAdBean;
import com.lwc.shanxiu.module.message.adapter.KnowledgeBaseAdapter;
import com.lwc.shanxiu.module.message.bean.KnowledgeBaseBean;
import com.lwc.shanxiu.module.message.ui.KnowledgeDetailWebActivity;
import com.lwc.shanxiu.module.question.bean.QuestionIndexBean;
import com.lwc.shanxiu.module.question.ui.activity.QuestionDetailActivity;
import com.lwc.shanxiu.module.question.ui.adapter.RequestQuestionAdapter;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.ImageCycleView;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewMainFragment extends BaseFragment {

    @BindView(R.id.ad_view)
    ImageCycleView mAdView;
    @BindView(R.id.rv_hot_anwser)
    RecyclerView rv_hot_anwser;
    @BindView(R.id.rv_hot_article)
    RecyclerView rv_hot_article;

    //List<KnowledgeBaseBean> articleList = new ArrayList<>();
    private KnowledgeBaseAdapter knowledgeAdapter;

    //List<QuestionIndexBean> questionList = new ArrayList<>();
    private RequestQuestionAdapter questionAdapter;

    /**
     * 轮播图数据
     */
    private ArrayList<ADInfo> infos;

    @Override
    public void init() {
    }

    @Override
    public void initGetData() {
    }

    private void bindRecylceView() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser  && getActivity() != null){
            ImmersionBar.with(getActivity())
                    .statusBarColor(R.color.white)
                    .statusBarDarkFont(true).init();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindRecylceView();
        initData();
    }

    @Override
    protected View getViews() {
        return View.inflate(getActivity(), R.layout.fragment_new_main, null);
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        setTitle("首页");

    }

    @Override
    protected void widgetListener() {
    }


    public void initData() {
        rv_hot_anwser.setLayoutManager(new LinearLayoutManager(getActivity()));
        //rv_hot_anwser.setHasFixedSize(true);
        rv_hot_anwser.setNestedScrollingEnabled(false);
        questionAdapter = new RequestQuestionAdapter(getActivity(), null, R.layout.item_request_question_2);
        rv_hot_anwser.setAdapter(questionAdapter);
        questionAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("quesionId", questionAdapter.getItem(position).getQuesionId());
                IntentUtil.gotoActivity(getActivity(), QuestionDetailActivity.class, bundle);
                //记录点击了item
                SharedPreferencesUtils.setParam(getActivity(),"itemPosition",position);
            }
        });

        rv_hot_article.setLayoutManager(new LinearLayoutManager(getActivity()));
      //  rv_hot_article.setHasFixedSize(true);
        rv_hot_article.setNestedScrollingEnabled(false);
        knowledgeAdapter = new KnowledgeBaseAdapter(getActivity(), null, R.layout.item_knowledge);
        rv_hot_article.setAdapter(knowledgeAdapter);

        knowledgeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("knowledgeId", knowledgeAdapter.getItem(position).getKnowledgeId());
                IntentUtil.gotoActivity(getActivity(), KnowledgeDetailWebActivity.class, bundle);
            }
        });

        getWheelPic();

    }


    /*
     *  获取轮播图
     */
    public void getWheelPic() {
        Map<String,String> params = new HashMap<>();
        params.put("image_type","1");//租赁商城页
        HttpRequestUtils.httpRequest(getActivity(), "获取首页租赁轮播图", RequestValue.GET_PARTSMANAGE_GETPARTSMAGES, params, "GET", new HttpRequestUtils.ResponseListener() {

            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        List<IndexAdBean> indexAdBeans = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<IndexAdBean>>() {});

                        infos = new ArrayList<>();
                        for(int i = 0;i < indexAdBeans.size(); i++){
                            ADInfo adInfo = new ADInfo();
                            adInfo.setAdvertisingImageUrl(indexAdBeans.get(i).getImageUrl());
                            adInfo.setAdvertisingTitle(indexAdBeans.get(i).getTitle());
                            adInfo.setUrlType(String.valueOf(indexAdBeans.get(i).getUrlType()));
                            adInfo.setAdvertisingId(indexAdBeans.get(i).getUrl());
                            adInfo.setAdvertisingUrl(indexAdBeans.get(i).getUrl());
                            infos.add(adInfo);
                        }

                        if(infos != null && infos.size() > 0){
                            setWheelPic();
                        }

                        getQuestion();
                        break;
                    default:
                        LLog.i("获取首页轮播图" + common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    private void getQuestion(){
        Map<String, String> map = new HashMap<>();
        map.put("curPage", "1");
        HttpRequestUtils.httpRequest(getActivity(), "问答首页", RequestValue.QUESION_SEARCH_SIMPLE, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        List<QuestionIndexBean> beanList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"),new TypeToken<ArrayList<QuestionIndexBean>>(){});
                        List<QuestionIndexBean> knowledgeBeanList2 = new ArrayList<>();
                        if(beanList.size() >= 2){
                            for(int i = 0; i < 2;i++){
                                knowledgeBeanList2.add(beanList.get(i));
                            }
                        }
                        questionAdapter.replaceAll(knowledgeBeanList2);

                        getKnowledge();
                        break;
                    default:
                        ToastUtil.showToast(getActivity(), common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showToast(getActivity(),msg);
            }
        });
    }

    private void getKnowledge(){

        Map<String, String> map = new HashMap<>();
        map.put("curPage", "1");
        HttpRequestUtils.httpRequest(getActivity(), "知识图谱首页", RequestValue.GET_KNOWLEDGE_SEARCH_SIMPLE, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        List<KnowledgeBaseBean> knowledgeBeanList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"),new TypeToken<ArrayList<KnowledgeBaseBean>>(){});
                        List<KnowledgeBaseBean> knowledgeBeanList2 = new ArrayList<>();
                        if(knowledgeBeanList.size() >= 2){
                            for(int i = 0; i < 2;i++){
                                knowledgeBeanList2.add(knowledgeBeanList.get(i));
                            }
                        }
                        knowledgeAdapter.replaceAll(knowledgeBeanList2);
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

    /**
     * 设置轮播图
     */
    public void setWheelPic(){
        mAdView.setImageResources(infos, mAdCycleViewListener);
        mAdView.startImageCycle();
    }

    /**
     * 轮播图点击事件
     */
    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if("1".equals(info.getUrlType())){ //0 无链接 1租赁商品详情 2外部链接
                Bundle bundle = new Bundle();
                bundle.putString("goodId",info.getAdvertisingId());
                IntentUtil.gotoActivity(getActivity(), LeaseGoodsDetailActivity.class,bundle);
            }else if("2".equals(info.getUrlType())){  //其他为外部链接 直接打开网页
                Bundle bundle = new Bundle();
                if (TextUtils.isEmpty(info.getAdvertisingUrl())){
                    return;
                }
                bundle.putString("url", info.getAdvertisingUrl());
                if (!TextUtils.isEmpty(info.getAdvertisingTitle()))
                    bundle.putString("title", info.getAdvertisingTitle());
                IntentUtil.gotoActivity(MainActivity.activity, InformationDetailsActivity.class, bundle);
            }
        }

        @Override
        public void displayImage(final String imageURL, final ImageView imageView) {
            ImageLoaderUtil.getInstance().displayFromNetDCircular(getActivity(), imageURL, imageView,R.drawable.image_default_picture);// 使用ImageLoader对图片进行加装！
        }
    };


    @OnClick({R.id.iv_beijian,R.id.iv_knowledge,R.id.iv_customer,R.id.iv_question,R.id.tv_hot_article,R.id.tv_electric})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.iv_beijian:
                IntentUtil.gotoActivity(getContext(), LeaseHomeActivity.class);
                break;
            case R.id.iv_knowledge:
            case R.id.tv_hot_article:
                IntentUtil.gotoActivity(getContext(), KnowledgeBaseActivity.class);
                break;
            case R.id.iv_customer:
                Bundle bundle = new Bundle();
                bundle.putString("url","https://www.ls-mx.com/main/adminlte/customerService.html");
                bundle.putString("title","在线客服");
                IntentUtil.gotoActivity(getContext(),InformationDetailsActivity.class,bundle);
                break;
            case R.id.iv_question:
            case R.id.tv_electric:
                IntentUtil.gotoActivity(getContext(), QuestionBaseActivity.class);
                break;
        }
    }
}
