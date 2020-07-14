package com.lwc.shanxiu.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.Solution;
import com.lwc.shanxiu.module.order.ui.activity.QuoteAffirmActivity;


public class TypeItem extends LinearLayout {

	private View view;
	private Context mContext;
	private TextView txt_name,txt_money_unit;
	private EditText et_money;
	private ImageView iv_delete;
	private View iv_line;

	public TypeItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TypeItem(Context context) {
		this(context,null);
		this.mContext = context;
		view = View.inflate(context, R.layout.item_type, this);
		txt_name = (TextView) view.findViewById(R.id.txt_name);
		txt_money_unit = (TextView) view.findViewById(R.id.txt_money_unit);
		et_money = (EditText) view.findViewById(R.id.et_money);
		iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
		iv_line =  view.findViewById(R.id.iv_line);
	}

	public void initData(final Solution repair, final QuoteAffirmActivity quoteAffirmActivity){
		//初始化数据
		txt_name.setText(repair.getDeviceTypeName()+"->"+repair.getExampleName());
		et_money.setText(Utils.chu(repair.getMaintainCost(), "100"));
		if("1".equals(repair.getIsFixation())){  //价格固定
			et_money.setFocusable(false);
			et_money.setFocusableInTouchMode(false);
			et_money.setEnabled(false);
			txt_money_unit.setText("元");
		}else{ //价格可变
			et_money.setFocusable(true);
			et_money.setFocusableInTouchMode(true);
			et_money.setEnabled(true);
			txt_money_unit.setText("元起");
		}

		et_money.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {


			}

			@Override
			public void afterTextChanged(Editable s) {
				int originalMoney = Integer.parseInt(Utils.chu(repair.getMaintainCost(), "100"));
				/*if("".equals(s.toString())){
					et_money.setText(String.valueOf(originalMoney));
					return;
				}*/

				/*if(Integer.parseInt(s.toString()) < originalMoney){
					et_money.setText(String.valueOf(originalMoney));
					ToastUtil.show(quoteAffirmActivity,"价格不能低于原始价格");
					return;
				}*/

				if(quoteAffirmActivity != null){
					if("".equals(s.toString())){
						quoteAffirmActivity.setErrorMessage("(维修价格不能为空)");
						return;
					}
					if(Integer.parseInt(s.toString()) < originalMoney){
						quoteAffirmActivity.setErrorMessage("(价格不能低于原始价格)");
						return;
					}
					quoteAffirmActivity.RepairMoneyChange();
				}
			}
		});
	}

	public void setListener(Solution repair, OnClickListener listener) {
		iv_delete.setTag(repair);
		iv_delete.setOnClickListener(listener);
	}

	public void setLineGone() {
		iv_line.setVisibility(GONE);
	}
}
