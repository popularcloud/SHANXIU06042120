package com.lwc.shanxiu.module.wallet;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.adapter.TradingRecordAdapter;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.bean.TradingRecordBean;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.order.ui.activity.OrderDetailActivity;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.KeyboardUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;

import org.byteam.superadapter.OnItemClickListener;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @author 何栋
 * @Dete 2017-11-28
 */
public class WalletActivity extends Activity {

	@BindView(R.id.FMoneyTV)
	TextView FMoneyTV;
	@BindView(R.id.tv_msg)
	TextView tv_msg;
	@BindView(R.id.recyclerView)
	RecyclerView recyclerView;
	@BindView(R.id.mBGARefreshLayout)
	BGARefreshLayout mBGARefreshLayout;
	ArrayList<TradingRecordBean> list = new ArrayList<>();
	private User user;
	private TradingRecordAdapter adapter;
	private SharedPreferencesUtils preferencesUtils;
	private int curPage=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			//透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			//透明导航栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		setContentView(R.layout.activity_wallet);
		ButterKnife.bind(this);
		BGARefreshLayoutUtils.initRefreshLayout(this, mBGARefreshLayout);
		init();
	}

	public void onBack(View view) {
		onBackPressed();
	}

	@OnClick({R.id.txtPay, R.id.txtWithdraw, R.id.txtManage})
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txtWithdraw:
			Bundle bundle = new Bundle();
			bundle.putString("money", user.getBanlance());
			IntentUtil.gotoActivity(WalletActivity.this, WithdrawDepositActivity.class, bundle);
			break;
		case R.id.txtPay:
			IntentUtil.gotoActivity(WalletActivity.this, WithdrawPayActivity.class);
			break;
		case R.id.txtManage:
			IntentUtil.gotoActivity(WalletActivity.this, PaySettingActivity.class);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		KeyboardUtil.showInput(false, WalletActivity.this);
		getUserInfor();
		curPage = 1;
	}

	private void getHistory() {
		Map<String, String> map = new HashMap<>();
		map.put("curPage", curPage+"");
		HttpRequestUtils.httpRequest(WalletActivity.this, "getHistory", RequestValue.GET_WALLET_HISTORY, map, "GET", new HttpRequestUtils.ResponseListener() {
			@Override
			public void getResponseData(String response) {
				mBGARefreshLayout.endRefreshing();
				mBGARefreshLayout.endLoadingMore();
				Common common = JsonUtil.parserGsonToObject(response, Common.class);
				switch (common.getStatus()) {
					case "1":
						try {
							JSONObject object = new JSONObject(JsonUtil.getGsonValueByKey(response, "data"));
							if (object != null) {
								ArrayList<TradingRecordBean> listBean = JsonUtil.parserGsonToArray(object.optString("data"), new TypeToken<ArrayList<TradingRecordBean>>() {
								});
								if (listBean != null && listBean.size() > 0) {
									if (curPage == 1) {
										list = listBean;
									} else if (curPage > 1) {
										list.addAll(listBean);
									}
									tv_msg.setVisibility(View.GONE);
									adapter.replaceAll(list);
								} else {
									if (curPage > 1) {
										curPage--;
										ToastUtil.showToast(WalletActivity.this, "我是有底线的，已无更多记录！");
									} else if (curPage == 1) {
										tv_msg.setVisibility(View.VISIBLE);
									}
								}
							}
							break;
						} catch (Exception e) {
						}
					default:
						ToastUtil.showLongToast(WalletActivity.this, common.getInfo());
						LLog.i("getHistory" + common.getInfo());
						break;
				}

			}
			@Override
			public void returnException(Exception e, String msg) {
				LLog.eNetError(e.toString());
				mBGARefreshLayout.endRefreshing();
				mBGARefreshLayout.endLoadingMore();
				ToastUtil.showLongToast(WalletActivity.this, msg);
			}
		});
	}

	/**
	 * 获取个人信息
	 */
	private void getUserInfor() {
		HttpRequestUtils.httpRequest(WalletActivity.this, "getUserInfor", RequestValue.USER_INFO, null, "GET", new HttpRequestUtils.ResponseListener() {
			@Override
			public void getResponseData(String response) {
				getHistory();
				Common common = JsonUtil.parserGsonToObject(response, Common.class);
				switch (common.getStatus()) {
					case "1":
						user = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"), User.class);
						if(user != null) {
							if (TextUtils.isEmpty(user.getUserId())) {
								if (!TextUtils.isEmpty(user.getMaintenanceId())){
									user.setUserId(user.getMaintenanceId());
								}
							} else if (TextUtils.isEmpty(user.getMaintenanceId())) {
								user.setMaintenanceId(user.getUserId());
							}
							preferencesUtils.saveObjectData(user);
							FMoneyTV.setText(user.getBanlance());
						}
						break;
					default:
						break;
				}
			}

			@Override
			public void returnException(Exception e, String msg) {
				LLog.eNetError(e.toString());
			}
		});
	}

	protected void init() {
		preferencesUtils = SharedPreferencesUtils.getInstance(this);
		user = preferencesUtils.loadObjectData(User.class);
		if (adapter == null) {
			recyclerView.setLayoutManager(new LinearLayoutManager(this));
			adapter = new TradingRecordAdapter(this, list, R.layout.item_trading_record);
			adapter.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(View itemView, int viewType, int position) {
					TradingRecordBean tradingRecordBean = adapter.getItem(position);
					Bundle bundle = new Bundle();

					if("6".equals(tradingRecordBean.getUserRole())){
						bundle.putString("orderId", tradingRecordBean.getObjectId());
						IntentUtil.gotoActivity(WalletActivity.this, OrderDetailActivity.class, bundle);
					}else{
						bundle.putSerializable("tradingRecordBean", adapter.getItem(position));
						IntentUtil.gotoActivity(WalletActivity.this, WalletDetailsActivity.class, bundle);
					}

				}
			});
			recyclerView.setAdapter(adapter);
		}

		mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
			@Override
			public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
				curPage = 1;
				getHistory();
			}

			@Override
			public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
				curPage++;
				getHistory();
				return true;
			}
		});
		FMoneyTV.setText(Utils.getMoney(user.getBanlance()));
	}


}
