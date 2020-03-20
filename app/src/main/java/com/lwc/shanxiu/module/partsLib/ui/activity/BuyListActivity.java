package com.lwc.shanxiu.module.partsLib.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Dimension;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.order.ui.activity.QuoteAffirmActivity;
import com.lwc.shanxiu.module.partsLib.ui.bean.PartsBean;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.widget.TagsLayout;
import com.wyh.slideAdapter.ItemBind;
import com.wyh.slideAdapter.ItemView;
import com.wyh.slideAdapter.SlideAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @author younge
 * @date 2018/12/19 0019
 * @email 2276559259@qq.com
 */
public class BuyListActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    @BindView(R.id.tctTip)
    TextView tctTip;
    @BindView(R.id.tv_SumMoney)
    TextView tv_SumMoney;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.cb_box)
    CheckBox cb_box;


    /**
     * 购物清单数据
     */
    private List<PartsBean> addListBeans = new ArrayList<>();
    private SlideAdapter slideAdapter;
    /**
     * 所有选中的价格
     */
    private Double allMoney = 0d;
    private int checkedSum;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_buy_list;
    }

    @Override
    protected void findViews() {
        showBack();
        setTitle("硬件清单");
        BGARefreshLayoutUtils.initRefreshLayout(this, mBGARefreshLayout, false);
        /*buyListAdapter = new BuyListAdapter(this,addListBeans, new BuyListItemInterface() {

            @Override
            public void onItemDel(int position) {
               *//* if(addListBeans.contains(addListBeans.get(position))){
                    addListBeans.remove(addListBeans.get(position));
                    ToastUtil.showToast(BuyListActivity.this,"删除成功!");
                    buyListAdapter.notifyDataSetChanged();
                }*//*
                //保存到本地
                SharedPreferencesUtils.getInstance(BuyListActivity.this).setParam(BuyListActivity.this,"addListBeans",JsonUtil.parserObjectToGson(addListBeans));
            }

            @Override
            public void onItemClick(int position) {
                Bundle bundle1 = new Bundle();
                bundle1.putString("accessories_id",addListBeans.get(position).getAccessoriesId());
                IntentUtil.gotoActivity(BuyListActivity.this,PartsDetailActivity.class,bundle1);
            }
        });*/
        recyclerView.setLayoutManager(new LinearLayoutManager(BuyListActivity.this));
        //recyclerView.setAdapter(buyListAdapter);

        mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                refreshLayout.endRefreshing();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                return true;
            }
        });


        ItemBind itemBind = new ItemBind<PartsBean>() {
            @Override
            public void onBind(ItemView itemView, final PartsBean data, final int position) {

                final EditText et_sum = itemView.getView(R.id.et_sum);
               // final CheckBox cb_add = itemView.getView(R.id.cb_isAdd);

                final TagsLayout tl_tags = itemView.getView(R.id.tl_tags);
                final TextView tv_prices = itemView.getView(R.id.tv_prices);

                ImageView iv_display = itemView.getView(R.id.iv_display);
                ImageLoaderUtil.getInstance().displayFromNetDCircular(BuyListActivity.this,data.getAccessoriesImg(),iv_display,R.drawable.image_default_picture);
                if(!TextUtils.isEmpty(data.getAttributeName())){
                    String[] tags =data.getAttributeName().split(",");
                    tl_tags.removeAllViews();
                    for (int i = 0; i < tags.length; i++) {
                        if(i > 2){
                            break;
                        }
                        TextView textView = new TextView(BuyListActivity.this);
                        textView.setText(tags[i]);
                        textView.setTextColor(Color.parseColor("#666666"));
                        textView.setTextSize(Dimension.SP,12);
                        textView.setGravity(Gravity.CENTER);
                        textView.setPadding(5,0,5,0);
                        textView.setBackgroundResource(R.drawable.round_square_gray);
                        int index = tags[i].indexOf("：");
                        String tagStr = tags[i].substring(index+1);
                        textView.setText(tagStr);
                        tl_tags.addView(textView);
                    }
                }else{
                    tl_tags.setVisibility(View.INVISIBLE);
                }
/*
                if(data.isItemIsChecked()){
                    cb_add.setChecked(true);
                }else{
                    cb_add.setChecked(false);
                }*/
                calculatedTotalPrice();

                et_sum.setText(String.valueOf(data.getSumSize()));

               /* String moneyStr = "￥"+ Utils.getMoney(String.valueOf(data.getAccessoriesPrice()/100));
                SpannableStringBuilder stringBuilder=new SpannableStringBuilder(moneyStr);
                AbsoluteSizeSpan ab=new AbsoluteSizeSpan(12,true);
                //文本字体绝对的大小
                stringBuilder.setSpan(ab,0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/

                if(data.getAccessoriesPrice() == null){
                    tv_prices.setText("￥ 0.00");
                }else{
                    String moneyStr = "￥"+ Utils.getMoney(String.valueOf(data.getAccessoriesPrice()/100));
                    SpannableStringBuilder stringBuilder=new SpannableStringBuilder(moneyStr);
                    AbsoluteSizeSpan ab=new AbsoluteSizeSpan(12,true);
                    //文本字体绝对的大小
                    stringBuilder.setSpan(ab,0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_prices.setText(stringBuilder);
                }


                itemView.setText(R.id.tv_title, data.getAccessoriesName())
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                               //ToastUtil.showToast(BuyListActivity.this,"点击item");
                                Bundle bundle1 = new Bundle();
                                bundle1.putString("accessories_id", data.getAccessoriesId());
                                IntentUtil.gotoActivity(BuyListActivity.this,PartsDetailActivity.class,bundle1);
                            }
                        })
                        .setOnClickListener(R.id.tv_prices, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                               // ToastUtil.showToast(BuyListActivity.this,"点击价格");
                            }
                        })
                        .setOnClickListener(R.id.ll_reduce, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               // ToastUtil.showToast(BuyListActivity.this,"减少数量");
                                int sumSize = data.getSumSize() - 1;
                                if(sumSize < 1){
                                  //  ToastUtil.showToast(BuyListActivity.this,"最少需要购买一个");
                                    addListBeans.remove(position);
                                    //if(data.isItemIsChecked()){
                                        calculatedTotalPrice();
                                  //  }
                                    slideAdapter.notifyDataSetChanged();
                                }else{
                                    data.setSumSize(sumSize);
                                    et_sum.setText(String.valueOf(sumSize));
                                    calculatedTotalPrice();
                                }

                            }
                        })
                        .setOnClickListener(R.id.ll_add, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               // ToastUtil.showToast(BuyListActivity.this,"增加数量");
                                int sumSize = data.getSumSize() + 1;
                                if(sumSize > 100){
                                    ToastUtil.showToast(BuyListActivity.this,"最多不能超过100个");
                                    return;
                                }
                                data.setSumSize(sumSize);
                                et_sum.setText(String.valueOf(sumSize));
                                calculatedTotalPrice();
                            }
                        })
                        .setOnClickListener(R.id.hide_delete, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addListBeans.remove(position);
                                calculatedTotalPrice();
                                slideAdapter.notifyDataSetChanged();
                                //SharedPreferencesUtils.getInstance(BuyListActivity.this).setParam(BuyListActivity.this,"addListBeans",JsonUtil.parserObjectToGson(addListBeans));
                            }
                        });
                /*cb_add.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            data.setItemIsChecked(true);
                            int checkedItem = 0;
                            for(int i = 0;i < addListBeans.size();i++){
                                PartsBean p = addListBeans.get(i);
                                if(p.isItemIsChecked()){
                                    checkedItem++;
                                }
                            }
                            if(checkedItem == addListBeans.size()){
                                cb_box.setChecked(true);
                            }
                        }else{
                            data.setItemIsChecked(false);
                            cb_box.setChecked(false);
                        }
                        calculatedTotalPrice();
                    }
                });*/
            }
        };

        slideAdapter = SlideAdapter.load(addListBeans)           //加载数据
                .item(R.layout.item_buy_list,0,0,R.layout.hide_drag_item,0.20f)//指定布局
                .bind(itemBind)
                .padding(0)
                .into(recyclerView);  //填充到recyclerView中

        //全选
        /*cb_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for(int i = 0;i < addListBeans.size();i++){
                    if(isChecked){
                        addListBeans.get(i).setItemIsChecked(true);
                    }else{
                        addListBeans.get(i).setItemIsChecked(false);
                    }
                }
                slideAdapter.notifyDataSetChanged();
                calculatedTotalPrice();
            }
        });*/
      /*  cb_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        if(cb_box.isChecked()){
                            for(int i = 0;i < addListBeans.size();i++) {
                                addListBeans.get(i).setItemIsChecked(true);
                            }
                        }else{
                            for(int i = 0;i < addListBeans.size();i++) {
                                addListBeans.get(i).setItemIsChecked(false);
                            }
                        }
                slideAdapter.notifyDataSetChanged();
            }
        });*/

    }

    /**
     * 计算总价
     */
    private void calculatedTotalPrice(){
        allMoney = 0d;
        checkedSum = 0;
        for(int i = 0;i < addListBeans.size();i++){
            PartsBean partsBean =  addListBeans.get(i);
                allMoney += partsBean.getAccessoriesPrice() * partsBean.getSumSize();
                checkedSum += partsBean.getSumSize();
        }
        tv_SumMoney.setText("￥"+Utils.getMoney(String.valueOf(allMoney/100)));
        tv_submit.setText("提交("+checkedSum+")");
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.tv_submit})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.tv_submit:
               /* if(allMoney <= 0){
                    ToastUtil.showToast(BuyListActivity.this,"请选择你要购买的商品");
                    return;
                }*/
                SharedPreferencesUtils.getInstance(BuyListActivity.this).setParam(BuyListActivity.this,"addListBeans",JsonUtil.parserObjectToGson(addListBeans));

                Bundle bundle = new Bundle();
                bundle.putDouble("allMoney",allMoney);
                bundle.putString("checkedSum",String.valueOf(checkedSum));
                IntentUtil.gotoActivity(BuyListActivity.this,QuoteAffirmActivity.class,bundle);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initGetData() {
        //获取购物清单
        String addListsStr = (String) SharedPreferencesUtils.getParam(this,"addListBeans","");
        List<PartsBean> beanArrayList = JsonUtil.parserGsonToArray(addListsStr, new TypeToken<ArrayList<PartsBean>>() {});
        if(beanArrayList != null){
            addListBeans.addAll(beanArrayList);
            slideAdapter.notifyDataSetChanged();
        }else{
            tctTip.setText("暂无数据");
        }
     //   buyListAdapter.notifyDataSetChanged();
    }
    
    private void searchData(){

    }

    @Override
    protected void widgetListener() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferencesUtils.getInstance(BuyListActivity.this).setParam(BuyListActivity.this,"addListBeans",JsonUtil.parserObjectToGson(addListBeans));
    }
}
