package com.lwc.shanxiu.module.lease_parts.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Dimension;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.lease_parts.adapter.GoodsNameAdapter;
import com.lwc.shanxiu.module.lease_parts.adapter.HotSearchAdapter;
import com.lwc.shanxiu.module.lease_parts.bean.SearchListWordBean;
import com.lwc.shanxiu.utils.DisplayUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.TagListView;
import com.lwc.shanxiu.widget.TagsLayout;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LeaseSearchActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tl_tags)
    TagListView tl_tags;
    @BindView(R.id.rv_data)
    RecyclerView rv_data;
    @BindView(R.id.rv_searchData)
    RecyclerView rv_searchData;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.iv_del)
    ImageView iv_del;

    private HotSearchAdapter hotSearchAdapter;
    private GoodsNameAdapter goodsNameAdapter;
    private List<String> goodsName = new ArrayList<>();

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_lease_search;
    }

    @Override
    protected void findViews() {
        img_back.setVisibility(View.GONE);
        tv_search.setText("取消");
        tv_search.setTextColor(Color.parseColor("#666666"));
        et_search.setHint("请输入关键字");
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //IntentUtil.gotoActivity(LeaseSearchActivity.this,LeaseGoodsListActivity.class);
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
             /*   if(!TextUtils.isEmpty(s.toString().trim())){
                    onGetGoodNameList(s.toString().trim());
                }else{
                    ll_search.setVisibility(View.VISIBLE);
                    rv_searchData.setVisibility(View.GONE);
                }*/
            }
        });

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    String input=et_search.getText().toString();
                    if(!TextUtils.isEmpty(input)){
                        Bundle bundle = new Bundle();
                        bundle.putString("searchText",input);
                        IntentUtil.gotoActivity(LeaseSearchActivity.this,LeaseGoodsListActivity.class,bundle);
                        finish();
                    }else{
                        ToastUtil.showToast(LeaseSearchActivity.this,"请输入你要搜索的关键字");
                    }
                    return true;
                }
                return false;
            }
        });

        initGoodListRecyclerView();
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initGetData() {
        onGetKeyWord();
    }

    @Override
    protected void widgetListener() {
        iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delKeyWord();
            }
        });
    }

    /**
     * 获取搜索关键字
     */
    private void onGetKeyWord(){
       HttpRequestUtils.httpRequest(this, "租赁商品搜索历史及热搜榜", RequestValue.PARTSMANAGE_PARTSGOODSKEYWORD, null, "GET",  new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        SearchListWordBean searchKeyWordBeen = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"),SearchListWordBean.class);
                        if(searchKeyWordBeen != null){

                            List<SearchListWordBean.LeaseGoodsKeywordBean> leaseGoodsKeywordBeans = searchKeyWordBeen.getPartsGoodsKeyword();

                            if(leaseGoodsKeywordBeans != null && leaseGoodsKeywordBeans.size() >0){
                                tl_tags.setVisibility(View.VISIBLE);
                                tl_tags.removeAllViews();
                                for(int i = 0; i < leaseGoodsKeywordBeans.size();i++){
                                    SearchListWordBean.LeaseGoodsKeywordBean searchKeyWordBean = leaseGoodsKeywordBeans.get(i);
                                    final TextView textView = new TextView(LeaseSearchActivity.this);
                                    textView.setText(searchKeyWordBean.getKeyword_name());
                                    textView.setTextColor(Color.parseColor("#333333"));
                                    textView.setTextSize(Dimension.SP,12);
                                    textView.setHeight(DisplayUtil.dip2px(LeaseSearchActivity.this,25));
                                    textView.setGravity(Gravity.CENTER);
                                    textView.setMaxEms(5);
                                    textView.setSingleLine(true);
                                    textView.setEllipsize(TextUtils.TruncateAt.END);
                                    textView.setPadding(25,0,25,0);
                                    textView.setBackgroundResource(R.drawable.round_square_gray_f0);
                                    textView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String key = ((TextView)view).getText().toString();
                                          //  et_search.setText(key);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("searchText",key);
                                            IntentUtil.gotoActivity(LeaseSearchActivity.this,LeaseGoodsListActivity.class,bundle);
                                        }
                                    });
                                    tl_tags.addView(textView);
                                }
                            }else{
                                tl_tags.setVisibility(View.GONE);
                            }

                            List<SearchListWordBean.LeaseGoodsKeywordBean> leaseGoodsKeywordNewBeans = searchKeyWordBeen.getPartsGoodsKeywordNew();

                            if(leaseGoodsKeywordNewBeans != null){
                                initLeftRecyclerView(leaseGoodsKeywordNewBeans);
                            }
                        }
                        break;
                    default:
                        ToastUtil.showToast(LeaseSearchActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
            }
        });
    }

    private void delKeyWord(){
        HttpRequestUtils.httpRequest(this, "清空搜索历史", RequestValue.PARTSMANAGE_DELPARTSGOODSKEYWORD, null, "POST",  new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        ToastUtil.showToast(LeaseSearchActivity.this, "搜索历史已清空");
                        tl_tags.removeAllViews();
                        break;
                    default:
                        ToastUtil.showToast(LeaseSearchActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showToast(LeaseSearchActivity.this,msg);
            }
        });
    }

    private void onGetGoodNameList(String name){
        HttpRequestUtils.httpRequest(this, "商品名称列表", RequestValue.PARTSMANAGE_GOODSPARTSINFO+"?goods_name="+name, null, "GET",  new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        List<String> searchGoodList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"),new TypeToken<ArrayList<String>>(){} );
                        if(searchGoodList != null && searchGoodList.size() > 0){
                            rv_searchData.setVisibility(View.VISIBLE);
                            ll_search.setVisibility(View.GONE);
                            goodsName.clear();
                            goodsName.addAll(searchGoodList);

                        }else{
                            rv_searchData.setVisibility(View.GONE);
                            ll_search.setVisibility(View.VISIBLE);
                            ToastUtil.showToast(LeaseSearchActivity.this,"抱歉,没有找到你想要的商品");
                        }
                        break;
                    default:
                        ToastUtil.showToast(LeaseSearchActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
            }
        });
    }

    /**
     * 初始化热搜列表
     * @param beans
     */
    private void initLeftRecyclerView(final List<SearchListWordBean.LeaseGoodsKeywordBean> beans) {
        hotSearchAdapter = new HotSearchAdapter(this,beans,R.layout.item_hot_search);
        rv_data.setLayoutManager(new GridLayoutManager(this,2));
        rv_data.setAdapter(hotSearchAdapter);

        hotSearchAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("searchText",beans.get(position).getKeyword_name());
                IntentUtil.gotoActivity(LeaseSearchActivity.this,LeaseGoodsListActivity.class,bundle);
            }
        });
    }


    /**
     * 初始化商品列表
     */
    private void initGoodListRecyclerView() {
        goodsNameAdapter = new GoodsNameAdapter(this,goodsName,R.layout.item_goods_name);
        rv_searchData.setLayoutManager(new LinearLayoutManager(this));
        rv_searchData.setAdapter(goodsNameAdapter);

        goodsNameAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("searchText",goodsNameAdapter.getItem(position));
                IntentUtil.gotoActivity(LeaseSearchActivity.this,LeaseGoodsListActivity.class,bundle);
            }
        });
    }
}
