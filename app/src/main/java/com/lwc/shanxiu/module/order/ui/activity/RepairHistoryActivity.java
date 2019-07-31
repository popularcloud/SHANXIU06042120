package com.lwc.shanxiu.module.order.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.module.order.ui.adapter.OrderListAdapter;
import com.lwc.shanxiu.utils.ACache;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.ToastUtil;

import org.byteam.superadapter.OnItemClickListener;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 设备维修历史
 *
 * @author 何栋
 * @date 2018年4月16日
 * @Copyright: lwc
 */
public class RepairHistoryActivity extends BaseActivity {

	@BindView(R.id.tv_brand)
	TextView tv_brand;
	@BindView(R.id.tv_type)
	TextView tv_type;
	@BindView(R.id.tv_device)
	TextView tv_device;
	@BindView(R.id.tv_unit)
	TextView tv_unit;
	@BindView(R.id.tv_user_phone)
	TextView tv_user_phone;
	@BindView(R.id.tv_unit_address)
	TextView tv_unit_address;
	@BindView(R.id.tv_install_engineer)
	TextView tv_install_engineer;
	@BindView(R.id.tv_install_unit)
	TextView tv_install_unit;
	@BindView(R.id.recyclerView)
	RecyclerView recyclerView;
	@BindView(R.id.textTip)
	TextView textTip;
	@BindView(R.id.tv_create_time)
	TextView tv_create_time;
	@BindView(R.id.rl_type)
	RelativeLayout rl_type;
	private String qrcodeIndex;
	private ArrayList<Order> orderList = new ArrayList<>();
	private OrderListAdapter adapter;

	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.activity_repair_history;
	}

	@Override
	protected void findViews() {
		ButterKnife.bind(this);
		showBack();
		setTitle("设备维修历史");
	}

	@Override
	protected void initGetData() {
		qrcodeIndex = getIntent().getStringExtra("qrcodeIndex");
	}

	private void initData(String response) {
		try {
			JSONObject object = new JSONObject(JsonUtil.getGsonValueByKey(response, "data"));
			String deviceTypeModel = object.optString("deviceTypeModel");
			String deviceTypeName = object.optString("deviceTypeName");
			String deviceTypeBrand = object.optString("deviceTypeBrand");
			String unitName = object.optString("userCompanyName");//单位名称
			String userPhone = object.optString("userPhone"); //单位联系人

			String unitAddress = object.optString("companyProvinceName") + object.optString("companyCityName") + object.optString("companyTownName");
			String maintenanceName = object.optString("maintenanceName"); //安装工程师
			String maintenanceCompanyName = object.optString("maintenanceCompanyName"); //安装单位

			String createTime = object.optString("createTime");
			tv_device.setText(deviceTypeName);
			tv_brand.setText(deviceTypeBrand);
			tv_unit.setText(unitName);
			String userPhones = "null".endsWith(userPhone)?"":userPhone;
			tv_user_phone.setText(userPhones);
			tv_unit_address.setText(unitAddress);
			tv_install_engineer.setText(maintenanceName);
			tv_install_unit.setText(maintenanceCompanyName);
			if (!TextUtils.isEmpty(deviceTypeModel)) {
				rl_type.setVisibility(View.VISIBLE);
				tv_type.setText(deviceTypeModel);
			} else {
				rl_type.setVisibility(View.GONE);
			}
			tv_create_time.setText(createTime);
			if (!TextUtils.isEmpty(object.optString("orders"))) {
				orderList = JsonUtil.parserGsonToArray(object.optString("orders"), new TypeToken<ArrayList<Order>>() {
				});
				adapter.replaceAll(orderList);
			}
			if (orderList != null && orderList.size() > 0) {
				textTip.setVisibility(View.GONE);
			} else {
				textTip.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {
			com.lwc.shanxiu.map.ToastUtil.show(this, "数据解析错误");
			finish();
		}
	}

	@Override
	protected void widgetListener() {
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new OrderListAdapter(this, orderList, R.layout.item_order);
		adapter.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(View itemView, int viewType, int position) {
					Intent intent = new Intent(RepairHistoryActivity.this, OrderDetailActivity.class);
					intent.putExtra("orderId", orderList.get(position).getOrderId());
					startActivity(intent);
				}
		});
		recyclerView.setAdapter(adapter);

		String response = ACache.get(this).getAsString("qrcodeIndex"+qrcodeIndex);
		if (TextUtils.isEmpty(response)) {
			getScanCode();
		} else {
			initData(response);
		}
	}

	@Override
	protected void init() {
		getScanCode();
	}

	/**
	 * 获取维修历史
	 *
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 */
	private void getScanCode() {
		Map<String, String> map = new HashMap<>();
		map.put("qrcodeIndex", qrcodeIndex );
		HttpRequestUtils.httpRequest(this, "getScanCode", RequestValue.SCAN_CODE, map, "GET", new HttpRequestUtils.ResponseListener() {
			@Override
			public void getResponseData(String response) {
				Common common = JsonUtil.parserGsonToObject(response, Common.class);
				switch (common.getStatus()) {
					case "1":
						initData(response);
						break;
					default:
						break;
				}
			}

			@Override
			public void returnException(Exception e, String msg) {
				ToastUtil.showLongToast(RepairHistoryActivity.this, e.toString());
			}
		});
	}
}
