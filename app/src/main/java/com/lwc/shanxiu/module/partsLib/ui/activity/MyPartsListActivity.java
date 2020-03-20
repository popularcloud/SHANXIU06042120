package com.lwc.shanxiu.module.partsLib.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.interf.OnAddListCallBack;
import com.lwc.shanxiu.module.order.ui.activity.QuoteAffirmActivity;
import com.lwc.shanxiu.module.partsLib.presenter.PartsListPresenter;
import com.lwc.shanxiu.module.partsLib.ui.adapter.MyPartsListAdapter;
import com.lwc.shanxiu.module.partsLib.ui.bean.PartsBean;
import com.lwc.shanxiu.module.partsLib.ui.fragment.FilterFragment;
import com.lwc.shanxiu.module.partsLib.ui.view.PartsListView;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.widget.SlideRecyclerView;

import org.byteam.superadapter.OnItemClickListener;

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
public class MyPartsListActivity extends BaseActivity implements PartsListView {

    @BindView(R.id.recyclerView)
    SlideRecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    @BindView(R.id.tctTip)
    TextView tctTip;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_filter)
    TextView tv_filter;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.btnReturn)
    TextView btnReturn;
    @BindView(R.id.btnLook)
    TextView btnLook;

    private DrawerLayout mDrawerLayout;
    private FrameLayout mDrawerContent;

    private MyPartsListAdapter myPartsListAdapter;
    private List<PartsBean> partsBeenList = new ArrayList<>();

    /**
     * 购物清单数据
     */
    private List<PartsBean> addListBeans = new ArrayList<>();

    private PartsListPresenter partsListPresenter;
    private String searchText = "";
    private String typeId = "";
    private int currentPage = 1;
    private String attributeSeach = "";
    private int priceOrderStatus = 0; //0正常 1顺序  2倒叙
    private boolean isAdd;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_parts_list;
    }

    @Override
    protected void findViews() {
        partsListPresenter = new PartsListPresenter(this,this);
        BGARefreshLayoutUtils.initRefreshLayout(this, mBGARefreshLayout, false);
        //初始化侧边筛选布局
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerContent = (FrameLayout) findViewById(R.id.drawer_content);

        myPartsListAdapter = new MyPartsListAdapter(this,partsBeenList,R.layout.item_parts_list, new OnAddListCallBack() {
            @Override
            public void onAddListCallBack(PartsBean partsBean) {
                isAdd = false;
                int position = 0;
                for(int i =0;i<addListBeans.size();i++){
                    if(partsBean.getAccessoriesId().equals(addListBeans.get(i).getAccessoriesId())){
                        isAdd = true;
                        position = i;
                        break;
                    }
                }

                if(isAdd){
                    addListBeans.get(position).setSumSize(addListBeans.get(position).getSumSize()+1);
                    SharedPreferencesUtils.getInstance(MyPartsListActivity.this).setParam(MyPartsListActivity.this,"addListBeans",JsonUtil.parserObjectToGson(addListBeans));
                    ToastUtil.showToast(MyPartsListActivity.this,"添加成功!");
                }else{
                    //加入清单
                    addListBeans.add(partsBean);
                    //保存到本地
                    SharedPreferencesUtils.getInstance(MyPartsListActivity.this).setParam(MyPartsListActivity.this,"addListBeans",JsonUtil.parserObjectToGson(addListBeans));
                    ToastUtil.showToast(MyPartsListActivity.this,"添加成功!");
                }


            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(MyPartsListActivity.this));
        recyclerView.setAdapter(myPartsListAdapter);
        myPartsListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Bundle bundle1 = new Bundle();
                bundle1.putString("accessories_id", myPartsListAdapter.getItem(position).getAccessoriesId());
                IntentUtil.gotoActivity(MyPartsListActivity.this,PartsDetailActivity.class,bundle1);
            }
        });

        mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                currentPage = 1;
                searchData(false);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                currentPage ++;
                searchData(false);
                return true;
            }
        });
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.tv_filter,R.id.tv_search,R.id.tv_price,R.id.btnReturn,R.id.btnLook,R.id.tctTip})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.tv_filter:
                mDrawerLayout.openDrawer(mDrawerContent);
                break;
            case R.id.tv_search:
                searchText = et_search.getText().toString().trim();
                searchData(true);
                break;
            case R.id.tv_price:
                Drawable drawable = null;
                switch (priceOrderStatus){
                    case 0:
                        drawable = getResources().getDrawable(R.drawable.ic_price_up);
                        priceOrderStatus = 1;
                        break;
                    case 1:
                        drawable = getResources().getDrawable(R.drawable.ic_price_down);
                        priceOrderStatus = 2;
                        break;
                    case 2:
                        drawable = getResources().getDrawable(R.drawable.ic_price_normal);
                        priceOrderStatus = 0;
                        break;
                }
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                tv_price.setCompoundDrawables(null,null,drawable,null);
                mBGARefreshLayout.beginRefreshing();
                break;
            case R.id.btnReturn:
                IntentUtil.gotoActivity(MyPartsListActivity.this,QuoteAffirmActivity.class);
                break;
            case R.id.btnLook:
                IntentUtil.gotoActivity(MyPartsListActivity.this,BuyListActivity.class);
                break;
            case R.id.tctTip:
                tctTip.setVisibility(View.GONE);
                mBGARefreshLayout.setVisibility(View.VISIBLE);
                searchText = "";
                et_search.setText("");
                mBGARefreshLayout.beginRefreshing();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBGARefreshLayout.beginRefreshing();

        //获取购物清单
        String addListsStr = (String) SharedPreferencesUtils.getParam(this,"addListBeans","");
        List<PartsBean> beanArrayList = JsonUtil.parserGsonToArray(addListsStr, new TypeToken<ArrayList<PartsBean>>() {});
        addListBeans.clear();
        if(beanArrayList != null){
         addListBeans.addAll(beanArrayList);
        }
    }

    @Override
    protected void initGetData() {

         Intent myIntent = getIntent();
         searchText = myIntent.getStringExtra("searchText");
         typeId = myIntent.getStringExtra("typeId");
         et_search.setText(searchText);

        FilterFragment fragment = new FilterFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        final Bundle bundle = new Bundle();
        bundle.putString("departmentName","");
        bundle.putString("typeId",typeId);
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.drawer_content, fragment).commit();
    }
    
    private void searchData(boolean isClickSearch){
        if(isClickSearch){
            if(TextUtils.isEmpty(searchText)){
                ToastUtil.showToast(MyPartsListActivity.this,"请输入要搜索的内容");
                return;
            }
        }
        //获取列表数据
        partsListPresenter.searchPartsData(typeId,searchText,attributeSeach,String.valueOf(priceOrderStatus),currentPage);
    }

    @Override
    protected void widgetListener() {

    }


    public void onDefiniteScreen(String typeIds,String otherSeachs){
        typeId = typeIds;
        attributeSeach = otherSeachs;
        mBGARefreshLayout.beginRefreshing();
    }

    @Override
    public void onLoadDataList(List<PartsBean> returnPartsBeenList) {
        if(returnPartsBeenList != null && returnPartsBeenList.size() > 0){
            partsBeenList.clear();
            partsBeenList.addAll(returnPartsBeenList);
            tctTip.setVisibility(View.GONE);
            mBGARefreshLayout.setVisibility(View.VISIBLE);
        }else{
            if(currentPage == 1){
                partsBeenList.clear();
                tctTip.setVisibility(View.VISIBLE);
                mBGARefreshLayout.setVisibility(View.GONE);
                tctTip.setText("暂无搜索数据!");
            }else{
                /*partsBeenList.clear();
                tctTip.setVisibility(View.VISIBLE);
                mBGARefreshLayout.setVisibility(View.GONE);*/
                //tctTip.setText("没有更多数据了!");
                ToastUtil.showToast(MyPartsListActivity.this,"没有更多数据了");
            }

        }
        myPartsListAdapter.notifyDataSetChanged();
        mBGARefreshLayout.endRefreshing();
    }

    @Override
    public void onLoadError(String msg) {
        ToastUtil.showToast(this,msg);
        mBGARefreshLayout.endRefreshing();
    }

}
