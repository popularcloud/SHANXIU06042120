package com.lwc.shanxiu.module.authentication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.BaseFragment;
import com.lwc.shanxiu.module.authentication.activity.TopicActivity;
import com.lwc.shanxiu.module.authentication.adapter.ExaminationAdapter;
import com.lwc.shanxiu.module.authentication.adapter.TrainAdapter;
import com.lwc.shanxiu.module.authentication.bean.ExaminationBean;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.module.question.bean.QuestionIndexBean;
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
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 考试
 */
public class ExaminationFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    private ExaminationAdapter adapter;
    @BindView(R.id.textTip)
    TextView textTip;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    //加载的page页
    private int page = 1;

    private SharedPreferencesUtils preferencesUtils = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_train, null);
        ButterKnife.bind(this, view);
        BGARefreshLayoutUtils.initRefreshLayout(getContext(), mBGARefreshLayout,false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEngines(view);
        init();
        setListener();
        bindRecycleView();

        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true).init();

        tv_submit.setVisibility(View.VISIBLE);
    }


    private void bindRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ExaminationAdapter(getContext(), null, R.layout.item_examination);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                //if(Utils.isFastClick(3000)){
                    isCanTest(adapter.getItem(position).getPaperId());
                //}

            }
        });
        recyclerView.setAdapter(adapter);

        mBGARefreshLayout.beginRefreshing();
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    public void init() {
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
        mBGARefreshLayout.setIsShowLoadingMoreView(false);
        mBGARefreshLayout.setPullDownRefreshEnable(false);
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

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply();
            }
        });
    }

    private void loadData(){
        Map<String, String> map = new HashMap<>();
        map.put("curPage", ""+page);
        HttpRequestUtils.httpRequest(getActivity(), "试卷列表", RequestValue.EXAMMANAGE_GETEXAMPAPER, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        List<ExaminationBean> beanList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"),new TypeToken<ArrayList<ExaminationBean>>(){});
                        if(page == 1){
                            notifyData(beanList);
                            mBGARefreshLayout.endRefreshing();
                        }else{
                            addData(beanList);
                            mBGARefreshLayout.endLoadingMore();
                        }

                        break;
                    default:
                        ToastUtil.showToast(getContext(), common.getInfo());
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

    private void apply(){
        HttpRequestUtils.httpRequest(getActivity(), "申请认证", RequestValue.EXAMMANAGE_APPLYSECRECY, null, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        ToastUtil.showToast(getContext(), common.getInfo());
                        break;
                    default:
                        ToastUtil.showToast(getContext(), common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showToast(getContext(), msg);
            }
        });
    }

    private void isCanTest(final String parentId){
        HttpRequestUtils.httpRequest(getActivity(), "是否可以考试", RequestValue.EXAMMANAGE_GETEXAMSTATUS, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                       // ToastUtil.showToast(getContext(), common.getInfo());
                        String data = JsonUtil.getGsonValueByKey(response, "data");

                        if("1".equals(data)){
                            Bundle bundle = new Bundle();
                            bundle.putString("parentId",parentId);
                            IntentUtil.gotoActivity(getContext(), TopicActivity.class,bundle);
                       }else{
                            ToastUtil.showToast(getActivity(),"抱歉,因为您还未看完视频,所以还不能考试");
                        }
                        break;
                    default:
                        ToastUtil.showToast(getContext(), common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showToast(getContext(), msg);
            }
        });
    }

    public void notifyData(List<ExaminationBean> myOrders) {
        adapter.replaceAll(myOrders);
        if(myOrders!= null && myOrders.size() > 0) {
            textTip.setVisibility(View.GONE);
        } else {
            textTip.setVisibility(View.VISIBLE);
        }
    }

    public void addData(List<ExaminationBean> myOrders) {
        if (myOrders == null || myOrders.size() == 0) {
            ToastUtil.showLongToast(getActivity(),"暂无更多数据");
            page = 1;
            return;
        }
        adapter.addAll(myOrders);
    }

}
