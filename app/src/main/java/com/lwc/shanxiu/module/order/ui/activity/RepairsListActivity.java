package com.lwc.shanxiu.module.order.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.user.LoginOrRegistActivity;
import com.lwc.shanxiu.adapter.RepairListAdapter;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.bean.Repairman;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.DialogUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.widget.CustomDialog;

import org.byteam.superadapter.OnItemClickListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 工程师列表
 *
 * @Description TODO
 * @author 何栋
 * @date 2016年4月16日
 * @Copyright: lwc
 */
public class RepairsListActivity extends BaseActivity {

	RecyclerView recyclerView;
	BGARefreshLayout mBGARefreshLayout;
	TextView textTip;
	private SharedPreferencesUtils preferencesUtils;
	private ArrayList<Repairman> repairerBeenList;
	private RepairListAdapter nearbyOrderAdapter;
	private User user = null;
	private int pageNow = 1;
	private Order myOrder;
	private LinearLayout ll_cz;

	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.fragment_order_list_nearby;
	}

	@Override
	protected void findViews() {
		showBack();
		setTitle("工程师列表");
		recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
		mBGARefreshLayout = (BGARefreshLayout)findViewById(R.id.mBGARefreshLayout);
		textTip = (TextView) findViewById(R.id.textTip);
		ll_cz = (LinearLayout) findViewById(R.id.ll_cz);
		BGARefreshLayoutUtils.initRefreshLayout(this, mBGARefreshLayout, true);
		ll_cz.setVisibility(View.GONE);
	}

	@Override
	protected void initGetData() {
		myOrder = (Order) getIntent().getExtras().getSerializable("data");
	}

	@Override
	protected void widgetListener() {
		mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
			@Override
			public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
				pageNow = 1;
				getRepairList();
			}

			@Override
			public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
				pageNow++;
				getRepairList();
				return true;
			}
		});
		recyclerView.setLayoutManager(new LinearLayoutManager(RepairsListActivity.this));
		nearbyOrderAdapter = new RepairListAdapter(RepairsListActivity.this, repairerBeenList, R.layout.item_repair_listview);
		nearbyOrderAdapter.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(View itemView, int viewType, int position) {
				final Repairman repairerBeen = nearbyOrderAdapter.getItem(position);
				DialogUtil.showMessageDg(RepairsListActivity.this, "温馨提示", "确定将订单转给该工程师处理吗？", new CustomDialog.OnClickListener() {

					@Override
					public void onClick(CustomDialog dialog, int id, Object object) {
						upDataOrder(repairerBeen.getMaintenanceId(),myOrder.getOrderId());
						dialog.dismiss();
					}
				});
			}
		});
		recyclerView.setAdapter(nearbyOrderAdapter);
	}

	public void upDataOrder(String uid, String oid) {
		if (Utils.isFastClick(1000)) {
			return;
		}
		Map<String, String> map = new HashMap<>();
		map.put("orderId", oid);
		map.put("waitMaintenance", uid);
		HttpRequestUtils.httpRequest(this, "updateOrderStatus", RequestValue.CHANGE_ORDER, map, "POST", new HttpRequestUtils.ResponseListener() {
			@Override
			public void getResponseData(String response) {
				Common common = JsonUtil.parserGsonToObject(response, Common.class);
				switch (common.getStatus()) {
					case "1":
						ToastUtil.showLongToast(RepairsListActivity.this, "订单转派成功");
						setResult(RESULT_OK);
						onBackPressed();
						break;
					default:
						ToastUtil.showLongToast(RepairsListActivity.this, common.getInfo());
						break;
				}
			}

			@Override
			public void returnException(Exception e, String msg) {
				ToastUtil.showLongToast(RepairsListActivity.this, msg);
			}
		});
	}

	@Override
	protected void init() {
		preferencesUtils = SharedPreferencesUtils.getInstance(this);
		user = preferencesUtils.loadObjectData(User.class);
		if (user == null) {
			IntentUtil.gotoActivity(this, LoginOrRegistActivity.class);
			return;
		}
		pageNow = 1;
		getRepairList();
	}

	/**
	 * 获取工程师列表
	 *
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 */
	private void getRepairList() {
		Map<String, String> map = new HashMap<>();
		map.put("orderId", myOrder.getOrderId());
		map.put("type", "2");
		map.put("curPage", pageNow+"");
		HttpRequestUtils.httpRequest(this, "getRepairList", RequestValue.WXY_LIST, map, "GET", new HttpRequestUtils.ResponseListener() {
			@Override
			public void getResponseData(String response) {
				Common common = JsonUtil.parserGsonToObject(response, Common.class);
				switch (common.getStatus()) {
					case "1":
						try {
							JSONObject object = new JSONObject(JsonUtil.getGsonValueByKey(response, "data"));
							List<Repairman> repairmans = JsonUtil.parserGsonToArray(object.optString("data"), new TypeToken<ArrayList<Repairman>>() {
							});
							if (pageNow == 1) {
								repairerBeenList = new ArrayList<>();
							}
							if (repairmans != null && repairmans.size() > 0) {
								repairerBeenList.addAll(repairmans);
								nearbyOrderAdapter.replaceAll(repairerBeenList);
							} else {
								if (pageNow > 1) {
									ToastUtil.showLongToast(RepairsListActivity.this, "暂无更多工程师");
									pageNow--;
								}
							}
							if(repairerBeenList!= null && repairerBeenList.size() > 0) {
								textTip.setVisibility(View.GONE);
							} else {
								textTip.setVisibility(View.VISIBLE);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						break;
					default:
						break;
				}
				BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
				BGARefreshLayoutUtils.endLoadingMore(mBGARefreshLayout);
			}

			@Override
			public void returnException(Exception e, String msg) {
				ToastUtil.showLongToast(RepairsListActivity.this, e.toString());
				BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
				BGARefreshLayoutUtils.endLoadingMore(mBGARefreshLayout);
			}
		});
	}
}
