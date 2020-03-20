package com.lwc.shanxiu.module.wallet;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
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
	@BindView(R.id.rBtnIncome)
	RadioButton rBtnIncome;
	@BindView(R.id.rBtnWithdrawal)
	RadioButton rBtnWithdrawal;
	ArrayList<TradingRecordBean> list = new ArrayList<>();
	private User user;
	private TradingRecordAdapter adapter;
	private HashMap rButtonHashMap;
	private SharedPreferencesUtils preferencesUtils;
	private int curPage=1;
	@BindView(R.id.viewLine1)
	View viewLine1;
	@BindView(R.id.viewLine3)
	View viewLine3;
	private int payment_type = 0; // 0转入 1转出

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wallet);
		ButterKnife.bind(this);
		BGARefreshLayoutUtils.initRefreshLayout(this, mBGARefreshLayout);
		init();
		ImmersionBar.with(this)
				.statusBarColor(R.color.btn_blue_nomal)
				.statusBarDarkFont(true)
				.navigationBarColor(R.color.white).init();
	}

	public void onBack(View view) {
		onBackPressed();
	}

	/**
	 * 往rButtonHashMap中添加 RadioButton Id
	 */
	private void addRadioButtonIdInList() {

		rButtonHashMap = new HashMap<>();
		rButtonHashMap.put(0, rBtnIncome);
		rButtonHashMap.put(1, rBtnWithdrawal);
	}

	@OnClick({R.id.txtPay, R.id.txtWithdraw, R.id.txtManage,R.id.rBtnIncome,R.id.rBtnWithdrawal})
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
		case R.id.rBtnIncome:
				showLineView(1);
				break;
		case R.id.rBtnWithdrawal:
				showLineView(2);
				break;
		default:
			break;
		}
	}


	/**
	 * 显示RadioButton线条
	 *
	 * @param num 1 ： 显示已发布下的线条  2 ： 显示未完成下的线条  3： 显示已完成下的线条  。未选中的线条将会被隐藏
	 */
	private void showLineView(int num) {
		switch (num) {
			case 1:
				viewLine1.setVisibility(View.VISIBLE);
				viewLine3.setVisibility(View.INVISIBLE);
				payment_type = 0;
				mBGARefreshLayout.beginRefreshing();
				break;
			case 2:
				viewLine3.setVisibility(View.VISIBLE);
				viewLine1.setVisibility(View.INVISIBLE);
				payment_type = 1;
				mBGARefreshLayout.beginRefreshing();
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
		map.put("payment_type", payment_type+"");
		HttpRequestUtils.httpRequest(WalletActivity.this, "收支记录", RequestValue.GET_WALLET_HISTORY, map, "GET", new HttpRequestUtils.ResponseListener() {
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
										adapter.clear();
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
							FMoneyTV.setText(Utils.getMoney(user.getBanlance()));
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
