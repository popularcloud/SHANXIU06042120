package com.lwc.shanxiu.module.message.ui;

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
import com.lwc.shanxiu.fragment.BaseFragment;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.message.adapter.MyMsgListAdapter;
import com.lwc.shanxiu.module.message.bean.HasMsg;
import com.lwc.shanxiu.module.message.bean.MyMsg;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.ToastUtil;

import org.byteam.superadapter.OnItemClickListener;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 我的消息
 *
 * @date 2018-03-27
 */
public class MyMsgFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    private ArrayList<MyMsg> myMsgs = new ArrayList<>();
    private List<HasMsg> hasMsg = new ArrayList<>();
    private MyMsgListAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        BGARefreshLayoutUtils.initRefreshLayout(getActivity(), mBGARefreshLayout);
        return rootView;
    }

    @Override
    protected void init() {
     //   hasMsg = DataSupport.findAll(HasMsg.class);
    }

    @Override
    public void initGetData() {

    }


    @Override
    protected View getViews() {
        return View.inflate(getActivity(), R.layout.activity_my_msg_list, null);
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        setTitle("我的消息");
    }

    @Override
    protected void widgetListener() {

    }

    @Override
    public void onResume() {
        super.onResume();
        hasMessage();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser  && getActivity() != null){
            ImmersionBar.with(getActivity())
                    .statusBarColor(R.color.white)
                    .statusBarDarkFont(true).init();

            hasMessage();
        }
    }

    private void readMsg(String type) {
        if (Utils.isFastClick(1000)) {
            return;
        }
        HttpRequestUtils.httpRequest(getActivity(), "readMsg", RequestValue.READ_MESSAGE+type, null, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String result) {
                ToastUtil.showToast(getActivity(),result);
            }
            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showToast(getActivity(),msg+e.getMessage());
            }
        });
    }

    private void hasMessage() {
        DataSupport.deleteAll(HasMsg.class);
        HttpRequestUtils.httpRequest(getActivity(), "hasMessage", RequestValue.HAS_MESSAGE, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if (common.getStatus().equals("1")) {
                    hasMsg = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<HasMsg>>() {
                    });
                    getMsgList();
                    if (hasMsg != null && hasMsg.size() > 0) {
                        DataSupport.saveAll(hasMsg);
                    }
                }else{
                    ToastUtil.showToast(getActivity(),common.getInfo());
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showToast(getActivity(),msg);
            }
        });
    }

    private void getMsgList() {
        HttpRequestUtils.httpRequest(getActivity(), "getMyMsgList", RequestValue.GET_MY_MESSAGE_LIST, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String result) {
                Common common = JsonUtil.parserGsonToObject(result, Common.class);
                if (common.getStatus().equals("1")) {
                    ArrayList<MyMsg> current = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(result, "data"), new TypeToken<ArrayList<MyMsg>>() {
                    });
                    if (current != null && current.size() > 0) {
                        myMsgs = current;
                    } else {
                        myMsgs = new ArrayList<>();
                    }
                    if (hasMsg != null && hasMsg.size() > 0 && myMsgs != null && myMsgs.size() > 0){
                        for (int j=0; j<myMsgs.size(); j++) {
                            for (int i=0; i<hasMsg.size(); i++) {
                                if (myMsgs.get(j).getMessageType().equals(hasMsg.get(i).getType())){
                                    myMsgs.get(j).setHasMessage(hasMsg.get(i).isHasMessage());
                                    break;
                                }
                            }
                        }
                    }
                    initRecycleView();
                   /* adapter.replaceAll(myMsgs);
                    adapter.notifyDataSetChanged();*/
                    if (myMsgs.size() > 0) {
                        tv_msg.setVisibility(View.GONE);
                    } else {
                        tv_msg.setVisibility(View.VISIBLE);
                    }
                } else {
                    ToastUtil.showToast(getActivity(), common.getInfo());
                }
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showToast(getActivity(), msg);
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
            }
        });
    }

    private void initRecycleView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyMsgListAdapter(getActivity(), myMsgs,hasMsg ,R.layout.item_my_msg);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                MyMsg msg = adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("myMsg", msg);
               /* for (int i=0; i<hasMsg.size(); i++) {
                    if (hasMsg.get(i).getType().equals(msg.getMessageType())) {
                        if (hasMsg.get(i).isHasMessage()) {
                            hasMsg.get(i).setHasMessage(false);
                            readMsg(msg.getMessageType());
                        }
                        break;
                    }
                }*/
                IntentUtil.gotoActivity(getActivity(), MsgListActivity.class, bundle);
            }
        });
        recyclerView.setAdapter(adapter);

        //刷新控件监听器
        mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                getMsgList();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                return false;
            }
        });
    }
}
