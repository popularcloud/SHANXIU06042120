package com.lwc.shanxiu.module.lease_parts.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcssloop.widget.PagerGridLayoutManager;
import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.InformationDetailsActivity;
import com.lwc.shanxiu.activity.MainActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.bean.Repairs;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.BaseFragment;
import com.lwc.shanxiu.module.bean.ADInfo;
import com.lwc.shanxiu.module.lease_parts.activity.LeaseGoodsDetailActivity;
import com.lwc.shanxiu.module.lease_parts.activity.LeaseSearchActivity;
import com.lwc.shanxiu.module.lease_parts.adapter.LeaseGoodTypeAdapter;
import com.lwc.shanxiu.module.lease_parts.adapter.LeasePagerAdapter;
import com.lwc.shanxiu.module.lease_parts.bean.IndexAdBean;
import com.lwc.shanxiu.module.lease_parts.bean.RecommendItemBean;
import com.lwc.shanxiu.module.message.bean.MyMsg;
import com.lwc.shanxiu.module.message.ui.MsgListActivity;
import com.lwc.shanxiu.utils.DisplayUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.MsgReadUtil;
import com.lwc.shanxiu.view.ImageCycleView;
import com.lwc.shanxiu.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LeaseHomeFragment extends BaseFragment {


    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.user_head_container)
    LinearLayout user_head_container;
    @BindView(R.id.ll_hide_or_show)
    LinearLayout ll_hide_or_show;
/*    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;*/

    @BindView(R.id.ad_view)
    ImageCycleView mAdView;
    @BindView(R.id.rv_repair_type)
    RecyclerView rv_repair_type;
    @BindView(R.id.vp_goodList)
    CustomViewPager vp_goodList;

 /*   @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
*/

    private HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();
    private ArrayList<String> arrayList = new ArrayList<>();

    private boolean headerIdShow = true;

    /**
     * 轮播图数据
     */
    private ArrayList<ADInfo> infos;
    private ArrayList<Repairs> myLeaseTypeLists;
    private ArrayList<RecommendItemBean> recommendItemBeanArrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lease_home, container, false);
        ButterKnife.bind(this, view);
       // BGARefreshLayoutUtils.initRefreshLayout(getContext(), mBGARefreshLayout,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void init() {

        img_back.setImageResource(R.drawable.ic_black_back);
        tv_search.setTextColor(Color.parseColor("#1481ff"));
        tv_search.setText("搜索");

        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.gotoActivity(getContext(), LeaseSearchActivity.class);
            }
        });

        et_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                   IntentUtil.gotoActivity(getContext(), LeaseSearchActivity.class);
                }
            }
        });


        /*mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                getWheelPic();
                mBGARefreshLayout.endRefreshing();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                return false;
            }
        });*/


        calcWheelPicHeight();
        getWheelPic();
       //mBGARefreshLayout.beginRefreshing();
    }


    @OnClick({R.id.iv_right,R.id.tv_msg})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.iv_right:
            case R.id.tv_msg:
                MyMsg msg = new MyMsg();
                msg.setMessageType("6");
                msg.setMessageTitle("租赁消息");
                Bundle bundle = new Bundle();
                bundle.putSerializable("myMsg", msg);
                IntentUtil.gotoActivity(getContext(), MsgListActivity.class,bundle);
                break;
        }
    }

    @Override
    public void initEngines(View view) {

    }

    @Override
    public void getIntentData() {

    }

    @Override
    public void setListener() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && getActivity() != null){
            ImmersionBar.with(getActivity())
                    .statusBarColor(R.color.white)
                    .statusBarDarkFont(true).init();

            //获取未读租赁消息
            MsgReadUtil.hasMessage(getActivity(),tv_msg);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //获取未读租赁消息
        MsgReadUtil.hasMessage(getActivity(),tv_msg);
    }

    /*
      *  获取轮播图
     */
    public void getWheelPic() {

        HttpRequestUtils.httpRequest(getActivity(), "获取首页租赁轮播图", RequestValue.GET_PARTSMANAGE_GETPARTSMAGES, null, "GET", new HttpRequestUtils.ResponseListener() {

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
                        break;
                    default:
                        LLog.i("获取首页轮播图" + common.getInfo());
                        break;
                }
                getLeaseType();
            }

            @Override
            public void returnException(Exception e, String msg) {
                getLeaseType();
                LLog.eNetError(e.toString());
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

    /**
     * 获取租赁设备类型
     */
    public void getLeaseType() {
        HttpRequestUtils.httpRequest(getActivity(), "获取首页租赁设备类型", RequestValue.GET_PARTSMANAGE_GETDEVICETYPES, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        myLeaseTypeLists = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<Repairs>>() {
                        });
                        if(myLeaseTypeLists != null && myLeaseTypeLists.size() > 0){
                            setLeaseType();
                        }
                        break;
                    default:
                        LLog.i("获取租赁设备类型" + common.getInfo());
                        break;
                }

                getRecommendsType();
            }

            @Override
            public void returnException(Exception e, String msg) {
                getRecommendsType();
                LLog.eNetError(e.toString());
            }
        });
    }

    /**
     * 设置租赁设备类型
     */
    public void setLeaseType(){
        // 布局管理器
        PagerGridLayoutManager layoutManager = new PagerGridLayoutManager(1, 5, PagerGridLayoutManager.HORIZONTAL);
        rv_repair_type.setLayoutManager(layoutManager);

        // 滚动辅助器
   /*     PagerGridSnapHelper snapHelper = new PagerGridSnapHelper();
        snapHelper.attachToRecyclerView(rv_repair_type);*/

        //设置适配器
        LeaseGoodTypeAdapter repairTypeAdapter = new LeaseGoodTypeAdapter(getContext(),myLeaseTypeLists);
        rv_repair_type.setAdapter(repairTypeAdapter);
    }


    public void getRecommendsType() {

        Bundle bundle = new Bundle();
        LeaseGoodListFragment leaseOrderFragment0 = new LeaseGoodListFragment();
        fragmentHashMap.put(0,leaseOrderFragment0);
        initViewpager();

    }

    public void setViewpageHeight(int height) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) vp_goodList.getLayoutParams();
        layoutParams.height = height;
        vp_goodList.setLayoutParams(layoutParams);
    }

    /**
     * 初始化列表
     */
    private void initViewpager() {

        //是否滑动
        vp_goodList.setPagingEnabled(true);
        vp_goodList.setNestedScrollingEnabled(false);
        vp_goodList.setAdapter(new LeasePagerAdapter(getChildFragmentManager(), recommendItemBeanArrayList,fragmentHashMap));
      //  tabs.setViewPager(vp_goodList);
        vp_goodList.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        //vp_goodList.setCurrentItem(0, false);

/*        // 设置Tab底部线的高度,传入的是dp
        tabs.setUnderlineHeight(0);
        // 设置Tab 指示器Indicator的高度,传入的是dp
        tabs.setIndicatorHeight(1);
        // 设置Tab Indicator的颜色
        tabs.setIndicatorColor(getResources().getColor(R.color.red_money));
        // 设置Tab标题文字的大小,传入的是sp
        tabs.setTextSize(14);
        // 设置选中Tab文字的颜色
        tabs.setSelectedTextColor(getResources().getColor(R.color.black));
        //设置正常Tab文字的颜色
        tabs.setTextColor(getResources().getColor(R.color.color_33));
        tabs.setTextSize(14);*/
    }

   /* public void showOrHideHeader(boolean isShow){
        if(isShow && !headerIdShow){
            user_head_container.setVisibility(View.VISIBLE);
            rv_repair_type.setVisibility(View.VISIBLE);
            headerIdShow = true;
        }else if(!isShow && headerIdShow){
            user_head_container.setVisibility(View.GONE);
            rv_repair_type.setVisibility(View.GONE);
            headerIdShow = false;
        }
    }*/

    /**
     * 计算轮播图高度
     */
    private void calcWheelPicHeight() {
        int adWidth = DisplayUtil.getScreenWidth(getContext());
        LinearLayout.LayoutParams adParams = (LinearLayout.LayoutParams) mAdView.getLayoutParams();

        int adHeight = adWidth/3;

        adParams.height = adHeight;

        mAdView.setLayoutParams(adParams);

    }
}
