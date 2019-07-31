package com.lwc.shanxiu.module.wallet;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.TradingRecordBean;
import com.lwc.shanxiu.map.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 收支详情
 * 
 * @author 何栋
 * @date 2017年11月28日
 */
public class WalletDetailsActivity extends BaseActivity {

	@BindView(R.id.tv_money)
	TextView tv_money;
	@BindView(R.id.tv_liushui)
	TextView tv_liushui;
	@BindView(R.id.tv_type)
	TextView tv_type;
	@BindView(R.id.tv_time)
	TextView tv_time;
	@BindView(R.id.tv_status)
	TextView tv_status;
	@BindView(R.id.tv_remak)
	TextView tv_remak;

	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.activity_wallet_details;
	}

	@Override
	protected void findViews() {
		ButterKnife.bind(this);
		setTitle("收支详情");
		showBack();
	}

	@Override
	protected void init() {
		TradingRecordBean tradingRecordBean = (TradingRecordBean)getIntent().getSerializableExtra("tradingRecordBean");

		if (!TextUtils.isEmpty(tradingRecordBean.getTransactionNo()))
			tv_liushui.setText(tradingRecordBean.getTransactionNo());
		String jj = "+";
		if (!TextUtils.isEmpty(tradingRecordBean.getTransactionRemark()))
			tv_remak.setText(tradingRecordBean.getTransactionRemark());
		if (!TextUtils.isEmpty(tradingRecordBean.getCreateTime()))
			tv_time.setText(tradingRecordBean.getCreateTime());
		if (tradingRecordBean.getTransactionScene() == 1) {
			if (tradingRecordBean.getPaymentType().equals("0")) {
				tv_type.setText("红包收入");
			} else {
				jj = "-";
				tv_type.setText("红包支出");
			}
		} else if (tradingRecordBean.getTransactionScene() == 2) {
			if (tradingRecordBean.getTransactionMeans().equals("1")) {
				if (tradingRecordBean.getPaymentType().equals("0")) {
					tv_type.setText("订单收入");
				} else {
					jj = "-";
					tv_type.setText("钱包支付");
				}
			} else if (tradingRecordBean.getTransactionMeans().equals("2")) {
				if (tradingRecordBean.getPaymentType().equals("0")) {
					tv_type.setText("订单收入");
				} else {
					jj = "-";
					tv_type.setText("支付宝支付");
				}
			} else if (tradingRecordBean.getTransactionMeans().equals("3")) {
				if (tradingRecordBean.getPaymentType().equals("0")) {
					tv_type.setText("订单收入");
				} else {
					jj = "-";
					tv_type.setText("微信支付");
				}
			}
		} else {
			if (tradingRecordBean.getTransactionMeans().equals("1")) {
				if (tradingRecordBean.getPaymentType().equals("0")) {
					tv_type.setText("钱包收入");
				} else {
					jj = "-";
					tv_type.setText("钱包支出");
				}
			} else if (tradingRecordBean.getTransactionMeans().equals("2")) {
				if (tradingRecordBean.getPaymentType().equals("0")) {
					tv_type.setText("支付宝充值");
				} else {
					jj = "-";
					tv_type.setText("支付宝提现");
				}
			} else if (tradingRecordBean.getTransactionMeans().equals("3")) {
				if (tradingRecordBean.getPaymentType().equals("0")) {
					tv_type.setText("微信充值");
				} else {
					jj = "-";
					tv_type.setText("微信提现");
				}
			}
		}
		tv_money.setText(jj+ Utils.getMoney(tradingRecordBean.getTransactionAmount()));
		if (tradingRecordBean.getTransactionStatus().equals("1")) {
			tv_status.setText("已完成");
		} else if (tradingRecordBean.getTransactionStatus().equals("2")) {
			tv_status.setText("系统处理中");
		} else if (tradingRecordBean.getTransactionStatus().equals("3")) {
			tv_status.setText("审核失败");
		} else if (tradingRecordBean.getTransactionStatus().equals("4")) {
			tv_status.setText("已取消");
		}
	}

	@Override
	protected void initGetData() {
	}

	@Override
	protected void widgetListener() {
	}
}
