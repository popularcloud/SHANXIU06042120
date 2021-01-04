package com.lwc.shanxiu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.InformationDetailsActivity;
import com.lwc.shanxiu.activity.KnowledgeBaseActivity;
import com.lwc.shanxiu.activity.MainActivity;
import com.lwc.shanxiu.activity.QuestionBaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.ADInfo;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.module.lease_parts.activity.LeaseGoodsDetailActivity;
import com.lwc.shanxiu.module.lease_parts.activity.LeaseHomeActivity;
import com.lwc.shanxiu.module.message.adapter.KnowledgeBaseAdapter;
import com.lwc.shanxiu.module.message.bean.KnowledgeBaseBean;
import com.lwc.shanxiu.module.message.ui.KnowledgeDetailWebActivity;
import com.lwc.shanxiu.module.question.bean.QuestionIndexBean;
import com.lwc.shanxiu.module.question.ui.activity.QuestionDetailActivity;
import com.lwc.shanxiu.module.question.ui.adapter.RequestQuestionAdapter;
import com.lwc.shanxiu.module.zxing.ui.CaptureActivity;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.SystemUtil;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.ImageCycleView;
import com.lwc.shanxiu.widget.CircleImageView;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class NewMainFragment extends BaseFragment {

    @BindView(R.id.ad_view)
    ImageCycleView mAdView;
    @BindView(R.id.rv_hot_anwser)
    RecyclerView rv_hot_anwser;
    @BindView(R.id.rv_hot_article)
    RecyclerView rv_hot_article;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    @BindView(R.id.imgRight)
    ImageView imgRight;
    @BindView(R.id.img_icon)
    CircleImageView imgIcon;
    @BindView(R.id.txtOrderStatus)
    TextView txtOrderStatus;
    @BindView(R.id.txtDistance)
    TextView txtDistance;
    @BindView(R.id.txtMaintainName)
    TextView txtMaintainName;

    @BindView(R.id.ll_get_order_mention)
    RelativeLayout llGetOrderMention;

    //最新订单
    private Order newestOrder = null;

    public AMapLocation currentBestLocation;

    //List<KnowledgeBaseBean> articleList = new ArrayList<>();
    private KnowledgeBaseAdapter knowledgeAdapter;

    //List<QuestionIndexBean> questionList = new ArrayList<>();
    private RequestQuestionAdapter questionAdapter;

    /**
     * 轮播图数据
     */
    private ArrayList<ADInfo> infos = new ArrayList<>();

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
        //获取最新订单
        getNewestOrder();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser  && getActivity() != null){
            ImmersionBar.with(getActivity())
                    .statusBarColor(R.color.white)
                    .statusBarDarkFont(true).init();

            //获取最新订单
            getNewestOrder();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        BGARefreshLayoutUtils.initRefreshLayout(getContext(), mBGARefreshLayout,false);

        imgRight.setVisibility(View.VISIBLE);
        imgRight.setImageResource(R.drawable.sweep_code);
        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.gotoActivityForResult(getActivity(), CaptureActivity.class, 8869);
            }
        });
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


        mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                getBannerData();
                mBGARefreshLayout.endRefreshing();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                return false;
            }
        });

        getBannerData();

    }


    /*
     *  获取轮播图
     */
    /*public void getWheelPic() {
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
    }*/


    /**
     * 获取配件库首页轮播
     */
    public void getBannerData(){
        HttpRequestUtils.httpRequest(getActivity(), "getBannerData", RequestValue.GET_ADVERTISING+"?local=1&role=2",null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        ArrayList<ADInfo> adInfoArrayList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<ADInfo>>() {});
                        infos.clear();
                        infos.addAll(adInfoArrayList);
                        if ( infos != null && infos.size() > 0) {
                            mAdView.setImageResources(infos, mAdCycleViewListener);
                            if(infos.size() > 1){
                                mAdView.startImageCycle();
                            }else{
                                mAdView.pushImageCycle();
                            }
                        }
                        break;
                    default:
                        ToastUtil.showToast(getActivity(),common.getInfo());
                        break;
                }

                getQuestion();
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showToast(getActivity(),msg);
                getQuestion();
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
                getKnowledge();
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
     * 获取最新订单
     * 有显示地图视图相关的操作
     */
    public void getNewestOrder() {
        if (SystemUtil.isBackground(getContext()) || SystemUtil.isFastClick(10000)) {
            return;
        }
        //当uid存在
        HttpRequestUtils.httpRequest(getActivity(), "getNewestOrder", RequestValue.ORDER_VIEW, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        String date = JsonUtil.getGsonValueByKey(response, "data");
                        if (date == null || date.length() < 5) {
//                            aMap.clear();
//                            drawNavigationIcon();
                            llGetOrderMention.setVisibility(View.GONE);
                            break;
                        }
                        ArrayList<Order> newestOrders = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<Order>>() {
                        });
                        if (newestOrders != null && newestOrders.size() > 0) {
                            llGetOrderMention.setVisibility(View.VISIBLE);
                            newestOrder = newestOrders.get(0);
                            if (newestOrder != null) {
                                llGetOrderMention.setVisibility(View.VISIBLE);
                                setJuli();
                                String picture = newestOrder.getUserHeadImage();
                                if (!TextUtils.isEmpty(picture)) {
                                    ImageLoaderUtil.getInstance().displayFromNet(getContext(), newestOrder.getUserHeadImage(), imgIcon,R.drawable.ic_default_user);
                                } else {
                                    ImageLoaderUtil.getInstance().displayFromLocal(getContext(), imgIcon, R.drawable.ic_default_user);
                                }
                                txtOrderStatus.setText(newestOrder.getStatusName());
                                txtMaintainName.setText(newestOrder.getOrderContactName());
                            } else {
                                llGetOrderMention.setVisibility(View.GONE);
                            }
                        }
                        break;
                    case "0":
                        LLog.iNetSucceed("   getNewestOrder 9999   " + common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
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

    private void setJuli() {
        if (newestOrder == null || currentBestLocation == null) {
            return;
        }
        LatLng latLng2 = new LatLng(Double.parseDouble(newestOrder.getOrderLatitude()), Double.parseDouble(newestOrder.getOrderLongitude()));
        float calculateLineDistance = AMapUtils.calculateLineDistance(
                new LatLng(currentBestLocation.getLatitude(), currentBestLocation.getLongitude()), latLng2);
        txtDistance.setText("距离您  " + (calculateLineDistance > 1000 ? Utils.chu(calculateLineDistance+"", 1000+"") + "km" : (int)calculateLineDistance + " m"));
    }


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
