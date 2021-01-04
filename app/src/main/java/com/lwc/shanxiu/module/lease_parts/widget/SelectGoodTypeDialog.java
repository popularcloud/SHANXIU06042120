package com.lwc.shanxiu.module.lease_parts.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.lease_parts.bean.LeaseSpecsBean;
import com.lwc.shanxiu.utils.DisplayUtil;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义对话框
 * 
 */
public class SelectGoodTypeDialog extends Dialog implements View.OnClickListener{

	/** 上下文 */
	private Context context;
	CallBack callBack;
	private List<LeaseSpecsBean> specsBeanList;
	private String money;
	private String img;
	private LinearLayout ll_space;

	private List<TextView> spaceTextViews = new ArrayList<>();
	private List<TextView> spaceTimeTextViews = new ArrayList<>();
	private List<TextView> payTypeTextViews = new ArrayList<>();

	//提交的数据
	private int sum = 1;
	private LeaseSpecsBean selSpecsBean;

	private ImageView iv_header;
	private TextView tv_price;
	private TextView tv_stock;

	public SelectGoodTypeDialog(Context context, CallBack callBack, List<LeaseSpecsBean> specsBeanList, String money, String img) {
		super(context, R.style.BottomDialogStyle);
		// 拿到Dialog的Window, 修改Window的属性
		Window window = getWindow();
		window.getDecorView().setPadding(0, 0, 0, 0);
		// 获取Window的LayoutParams
		WindowManager.LayoutParams attributes = window.getAttributes();
		attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
		attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
		// 一定要重新设置, 才能生效
		window.setAttributes(attributes);
		this.callBack = callBack;
		this.specsBeanList = specsBeanList;
		this.money = money;
		this.img = img;
		init(context);
	}

	/**
	 *
	 * @param context
	 * @param theme
     */
	public SelectGoodTypeDialog(Context context, int theme) {
		super(context, theme);
		init(context);
	}

	public SelectGoodTypeDialog(Context context) {
		super(context, R.style.dialog_style);
		init(context);
	}

	/**
	 * 初始话对话框
	 * 
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 * 
	 */
	protected void init(final Context context) {
		this.context = context;
		this.setCancelable(true);
		this.setCanceledOnTouchOutside(true);
		this.setContentView(R.layout.dialog_select_goods_type_layout);

		ImageView ic_close = findViewById(R.id.ic_close);
		iv_header = findViewById(R.id.iv_header);
		TextView tv_submit = findViewById(R.id.tv_submit);
		tv_price = findViewById(R.id.tv_price);
		tv_stock = findViewById(R.id.tv_stock);
		ImageView tv_reduce = findViewById(R.id.tv_reduce);
		ImageView tv_add = findViewById(R.id.tv_add);
		final EditText et_sum = findViewById(R.id.et_sum);
		ll_space = findViewById(R.id.ll_space);

		//默认选中第一个规格
		selSpecsBean = specsBeanList.get(0);

		spaceTextViews.clear();
		spaceTimeTextViews.clear();
		payTypeTextViews.clear();

		showGoodSpace();

		ImageLoaderUtil.getInstance().displayFromNetDCircular(context, img, iv_header,R.drawable.image_default_picture);// 使用ImageLoader对图片进行加装！

		String goodsPrice = Utils.chu(money, "100");
		String goodsPriceStr = "￥" + goodsPrice;
		SpannableStringBuilder showPrices = Utils.getSpannableStringBuilder(1, goodsPrice.length()+1,context.getResources().getColor(R.color.red_money), goodsPriceStr, 24, true);
		tv_price.setText(showPrices);
		tv_stock.setText("库存"+selSpecsBean.getNum()+"件");

		ic_close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		et_sum.setText(String.valueOf(sum));
		tv_submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(callBack != null){
					if(selSpecsBean == null){
						ToastUtil.showToast(context,"请选择规格!");
						return;
					}

					Map<String,String> params = new HashMap<>();
					params.put("goods_id",selSpecsBean.getGoodsId());
					params.put("goods_num",String.valueOf(sum));
					params.put("leaseSpace",selSpecsBean.getLeaseSpecs());
					callBack.onSubmit(params);
				}
			}
		});

		tv_reduce.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int reduceSum = sum - 1;
				if(reduceSum == 0){
					ToastUtil.showToast(context,"至少选择一件商品！");
					return;
				}

				sum = reduceSum;
				et_sum.setText(String.valueOf(sum));
			}
		});

		tv_add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sum = sum + 1;
				et_sum.setText(String.valueOf(sum));
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {}

	}

	public void showInput(final EditText et) {
		et.requestFocus();
		InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
	}

	protected void hideInput() {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
		View v = getWindow().peekDecorView();
		if (null != v) {
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
	}

	public interface CallBack {
		void onSubmit(Object o);
	}


	private void showGoodSpace(){
		if(specsBeanList != null){
			for(int i =0;i<specsBeanList.size();i++){
				LeaseSpecsBean leaseSpecsBean = specsBeanList.get(i);
				TextView textView = new TextView(context);
				textView.setTextColor(context.getResources().getColor(R.color.black));
				textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
				textView.setTag(specsBeanList.get(i));
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
				params.setMargins(0, DisplayUtil.dip2px(context,10),0,0);
				textView.setPadding(DisplayUtil.dip2px(context,10),DisplayUtil.dip2px(context,5),DisplayUtil.dip2px(context,10),DisplayUtil.dip2px(context,5));
				if(i == 0){
					textView.setBackgroundResource(R.drawable.button_red_round_shape);
				}else{
					textView.setBackgroundResource(R.drawable.button_gray_round_shape_f0);
				}

				textView.setText(leaseSpecsBean.getLeaseSpecs());
				textView.setLayoutParams(params);

				spaceTextViews.add(textView);

				ll_space.addView(textView);

				textView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						for(int j = 0;j<spaceTextViews.size();j++){
							if(v == spaceTextViews.get(j)){
								spaceTextViews.get(j).setBackgroundResource(R.drawable.button_red_round_shape);
								selSpecsBean = specsBeanList.get(j);
							}else{
								spaceTextViews.get(j).setBackgroundResource(R.drawable.button_gray_round_shape_f0);
							}
						}

						LeaseSpecsBean obj = (LeaseSpecsBean) v.getTag();
						//ImageLoaderUtil.getInstance().displayFromNetDCircular(context, img, iv_header,R.drawable.image_default_picture);// 使用ImageLoader对图片进行加装！

						String goodsPrice = Utils.chu(String.valueOf(obj.getGoodsPrice()), "100");
						String goodsPriceStr = "￥" + goodsPrice + "/月";
						SpannableStringBuilder showPrices = Utils.getSpannableStringBuilder(1, goodsPrice.length()+1,context.getResources().getColor(R.color.red_money), goodsPriceStr, 24, true);
						tv_price.setText(showPrices);
						tv_stock.setText("库存"+obj.getNum()+"件");
					}
				});
			}
		}
	}
}
