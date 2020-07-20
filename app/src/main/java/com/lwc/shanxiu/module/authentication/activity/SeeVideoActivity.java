package com.lwc.shanxiu.module.authentication.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videocontroller.component.CompleteView;
import com.dueeeke.videocontroller.component.PrepareView;
import com.dueeeke.videocontroller.component.TitleView;
import com.dueeeke.videoplayer.player.VideoView;
import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.authentication.adapter.TrainAdapter;
import com.lwc.shanxiu.module.authentication.bean.TrainBean;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.ToastUtil;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;


/**
 * 播放视频
 */
public class SeeVideoActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    @BindView(R.id.vv_video)
    VideoView vv_video;

    @BindView(R.id.iv_header)
    ImageView iv_header;
    @BindView(R.id.iv_play)
    ImageView iv_play;
    @BindView(R.id.ll_current)
    LinearLayout ll_current;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.tv_time)
    TextView tv_time;

    private TrainAdapter adapter;
    private List<Order> myOrders = new ArrayList<>();

    private int page = 1;

    //加载指定的视频文件
    String path = "https://cdn.mixiu365.com/luo9VZ4Q5HGeEGYyP_zui1xtIVut";
    private TrainBean trainBean;

    private long videoLong; //视频长度
    private long videoCurrent;

    int isSeeFinish = 0;


    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_see_video;
    }

    @Override
    protected void findViews() {
        showBack();
    }

    @Override
    protected void init() {


        trainBean = (TrainBean) getIntent().getSerializableExtra("videoBean");
        bindRecycleView();

        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true).init();

        PrepareView prepareView = new PrepareView(this);//准备播放界面
        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent(trainBean.getVideoName(), false);
        controller.addControlComponent(prepareView);
        controller.addControlComponent(new CompleteView(this));//自动完成播放界面
        TitleView titleView = new TitleView(this);//标题栏
        controller.addControlComponent(titleView);
        vv_video.setVideoController(controller); //设置控制器
        loadVideo();
    }


    private void loadVideo(){

        vv_video.setUrl(trainBean.getUrl());
        if(!TextUtils.isEmpty(trainBean.getReadTime()) && "0".equals(trainBean.getIsPass())){
           Long longTime =Integer.valueOf(trainBean.getReadTime()) * 1000L;
            vv_video.seekTo(longTime);
        }

        vv_video.start(); //开始播放，不调用则不自动播放
        tv_title.setText(trainBean.getVideoName());
        tv_status.setText("播放中");
        ImageLoaderUtil.getInstance().displayFromNetDCircular8(this,trainBean.getImage(),iv_header,R.drawable.img_default_load);
        mBGARefreshLayout.beginRefreshing();
    }

    @Override
    protected void initGetData() {

    }

    private void bindRecycleView() {
        BGARefreshLayoutUtils.initRefreshLayout(this, mBGARefreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TrainAdapter(this, null, R.layout.item_train);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                trainBean = adapter.getItem(position);

                vv_video.release();
                vv_video.setUrl(trainBean.getUrl());
                vv_video.start();

            /*    tv_title.setText(trainBean.getVideoName());
                tv_status.setText("播放中");
                ImageLoaderUtil.getInstance().displayFromNetDCircular8(SeeVideoActivity.this,trainBean.getImage(),iv_header,R.drawable.img_default_load);*/
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void widgetListener() {

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

        vv_video.setOnStateChangeListener(new VideoView.OnStateChangeListener() {
            @Override
            public void onPlayerStateChanged(int playerState) {
                Log.d("联网成功",playerState+"videoLong"+videoLong + "=========videoCurrent"+videoCurrent);
            }

            @Override
            public void onPlayStateChanged(int playState) {

            }
        });
    }


    private void loadData(){
        Map<String, String> map = new HashMap<>();
        map.put("curPage", ""+page);
        HttpRequestUtils.httpRequest(SeeVideoActivity.this, "视频列表", RequestValue.EXAMMANAGE_GETEXAMVIDEO, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        List<TrainBean> beanList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"),new TypeToken<ArrayList<TrainBean>>(){});
                        if(page == 1){
                            notifyData(beanList);
                            mBGARefreshLayout.endRefreshing();
                        }else{
                            addData(beanList);
                            mBGARefreshLayout.endLoadingMore();
                        }
                        break;
                    default:
                        //ToastUtil.showToast(MyRequestActivity.this, common.getInfo());
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

    private void uploadSeeTime(){
        Map<String, String> map = new HashMap<>();
        map.put("id", trainBean.getId());
        map.put("read_time",String.valueOf(videoCurrent/1000));
        map.put("is_pass",String.valueOf(isSeeFinish));
        HttpRequestUtils.httpRequest(SeeVideoActivity.this, "上传视频观看时间", RequestValue.EXAMMANAGE_UPDATEEXAMVIDEO, map, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        List<TrainBean> beanList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"),new TypeToken<ArrayList<TrainBean>>(){});
                        if(page == 1){
                            notifyData(beanList);
                            mBGARefreshLayout.endRefreshing();
                        }else{
                            addData(beanList);
                            mBGARefreshLayout.endLoadingMore();
                        }
                        break;
                    default:
                        //ToastUtil.showToast(MyRequestActivity.this, common.getInfo());
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

    public void notifyData(List<TrainBean> myOrders) {
        adapter.replaceAll(myOrders);
        if(myOrders!= null && myOrders.size() > 0) {
           // textTip.setVisibility(View.GONE);
        } else {
            //textTip.setVisibility(View.VISIBLE);
        }
    }

    public void addData(List<TrainBean> myOrders) {
        if (myOrders == null || myOrders.size() == 0) {
            ToastUtil.showLongToast(SeeVideoActivity.this,"暂无更多数据");
            page--;
            return;
        }
        adapter.addAll(myOrders);
    }
    @Override
    protected void onPause() {
        super.onPause();
        videoLong = vv_video.getDuration();
        videoCurrent = vv_video.getCurrentPosition();
        if(videoCurrent >= videoLong){
            isSeeFinish = 1;
        }else{
            isSeeFinish = 0;
        }
        uploadSeeTime();
        Log.d("联网成功","videoLong"+videoLong + "=========videoCurrent"+videoCurrent);
        vv_video.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vv_video.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vv_video.release();
    }


    @Override
    public void onBackPressed() {
        if (!vv_video.onBackPressed()) {
            super.onBackPressed();
        }
    }


}
