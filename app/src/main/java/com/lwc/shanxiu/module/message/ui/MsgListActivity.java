package com.lwc.shanxiu.module.message.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.activity.InformationDetailsActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.lease_parts.activity.LeaseOrderDetailActivity;
import com.lwc.shanxiu.module.message.adapter.MsgListAdapter;
import com.lwc.shanxiu.module.message.bean.MyMsg;
import com.lwc.shanxiu.module.order.ui.activity.OrderDetailActivity;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.ToastUtil;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 消息列表
 *
 * @date 2018-03-27
 */
public class MsgListActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    private int page = 1;
    private ArrayList<MyMsg> myMsgs = new ArrayList<>();
    private MsgListAdapter adapter;
    private MyMsg myMsg;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_my_msg_list;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        BGARefreshLayoutUtils.initRefreshLayout(this, mBGARefreshLayout);
    }

    @Override
    protected void init() {
    }

    @Override
    protected void initGetData() {
        showBack();
        myMsg = (MyMsg)getIntent().getSerializableExtra("myMsg");
        if (myMsg != null) {
            if (myMsg.getMessageType().equals("1")) {
                setTitle("系统消息");
            } else if (myMsg.getMessageType().equals("2")) {
                setTitle("资讯消息");
            } else if (myMsg.getMessageType().equals("3")) {
                setTitle("活动消息");
            } else if (myMsg.getMessageType().equals("4")) {
                setTitle("订单消息");
            }else if (myMsg.getMessageType().equals("6")) {
                setTitle("租赁消息");
            }
        } else {
            ToastUtil.showLongToast(this,"数据错误，请稍候重试!");
            finish();
        }
    }

    @Override
    protected void widgetListener() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MsgListAdapter(this, myMsgs, R.layout.item_msg);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                MyMsg msg = adapter.getItem(position);
                String type = msg.getMessageType();

                readMsg(msg.getObjectId());

                if (!TextUtils.isEmpty(type) && type.equals("1")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", msg.getClickUrl());
                    bundle.putString("title", "系统消息");
                    IntentUtil.gotoActivity(MsgListActivity.this, InformationDetailsActivity.class, bundle);
                } else if (!TextUtils.isEmpty(type) && (type.equals("2") || type.equals("3"))) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", msg.getClickUrl());
                    if (type.equals("2")) {
                        bundle.putString("title", "资讯详情");
                    } else {
                        bundle.putString("title", "活动详情");
                    }
                    IntentUtil.gotoActivity(MsgListActivity.this, InformationDetailsActivity.class, bundle);
                } else if (!TextUtils.isEmpty(type) && type.equals("4")) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("orderId", msg.getObjectId());
                    IntentUtil.gotoActivity(MsgListActivity.this, OrderDetailActivity.class, bundle2);
                }else if (!TextUtils.isEmpty(type) && type.equals("6")) {
                  /*  Bundle bundle2 = new Bundle();
                    bundle2.putString("orderId", msg.getObjectId());
                    IntentUtil.gotoActivity(MsgListActivity.this, OrderDetailActivity.class, bundle2);*/

                  if(!TextUtils.isEmpty(adapter.getItem(position).getObjectId())){
                      String [] ids = adapter.getItem(position).getObjectId().split(",");
                      Bundle bundle = new Bundle();
                      bundle.putString("order_id",ids[0]);
                      bundle.putInt("openType",1);
                      IntentUtil.gotoActivity(MsgListActivity.this, LeaseOrderDetailActivity.class,bundle);
                  }

                }
            }
        });
        recyclerView.setAdapter(adapter);

        //刷新控件监听器
        mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                page=1;
                getMsgList();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                page++;
                getMsgList();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        page=1;
        getMsgList();
    }

    private void getMsgList() {
        HashMap<String, String> map = new HashMap<>();
        map.put("curPage",page+"");
        HttpRequestUtils.httpRequest(this, "getMsgList", RequestValue.GET_MESSAGE_LIST+myMsg.getMessageType(), map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String result) {
                Common common = JsonUtil.parserGsonToObject(result, Common.class);
                if (common.getStatus().equals("1")) {
                    ArrayList<MyMsg> current = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(result, "data"), new TypeToken<ArrayList<MyMsg>>() {
                    });
                    if (page == 1) {
                        if (current != null && current.size() > 0) {
                            myMsgs = current;
                        } else {
                            myMsgs = new ArrayList<>();
                        }
                    } else if (page > 1) {
                        if (current != null && current.size() > 0) {
                            myMsgs.addAll(current);
                        } else {
                            ToastUtil.showToast(MsgListActivity.this, "暂无更多信息");
                            page--;
                        }
                    }
                    adapter.replaceAll(myMsgs);
                    if (myMsgs.size() > 0) {
                        tv_msg.setVisibility(View.GONE);
                    } else {
                        tv_msg.setVisibility(View.VISIBLE);
                    }
                } else {
                    ToastUtil.showToast(MsgListActivity.this, common.getInfo());
                }
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
                BGARefreshLayoutUtils.endLoadingMore(mBGARefreshLayout);
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showToast(MsgListActivity.this, msg);
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
                BGARefreshLayoutUtils.endLoadingMore(mBGARefreshLayout);
            }
        });
    }

    private void readMsg(String msgId) {
        HttpRequestUtils.httpRequest(this, "readMsg", RequestValue.READ_MESSAGE+msgId, null, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String result) {
            }
            @Override
            public void returnException(Exception e, String msg) {
            }
        });
    }
}
