package com.lwc.shanxiu.module.message.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.message.adapter.KnowledgeBaseAdapter;
import com.lwc.shanxiu.module.message.adapter.Madapter;
import com.lwc.shanxiu.module.message.adapter.PopSelectAdapter;
import com.lwc.shanxiu.module.message.bean.KnowledgeBaseBean;
import com.lwc.shanxiu.module.message.bean.SearchConditionBean;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.DisplayUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.widget.DropDownMenu;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 知识库列表
 */
public class KnowledgeBaseActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
/*    @BindView(R.id.textTip)
    TextView textTip;*/
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.tv_device_type)
    TextView tv_device_type;
    @BindView(R.id.tv_brand)
    TextView tv_brand;
    @BindView(R.id.et_search)
    EditText et_search;
    List<KnowledgeBaseBean> beanList = new ArrayList<>();
    private KnowledgeBaseAdapter adapter;
    private int page = 1;
    private String type_id = "";
    private String brand_id = "";
    private String search_txt = "";
    private ArrayList<SearchConditionBean> searchConditionBeanList;
    private PopSelectAdapter popSelectAdapter;
    private DropDownMenu dropDownMenu;

    private List<SearchConditionBean.OptionsBean> typeCondition = new ArrayList<>();
    private Map<String,List<SearchConditionBean.OptionsBean>> brandConditon = new HashMap<>();

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_knowledge_base;
    }

    @Override
    protected void findViews() {
        BGARefreshLayoutUtils.initRefreshLayout(this, mBGARefreshLayout);
        bindRecycleView();
        initSearchCondition();
    }

    private void initSearchCondition() {
        dropDownMenu = DropDownMenu.getInstance(this, new DropDownMenu.OnListCkickListence() {
            @Override
            public void search(String code, String type) {
                System.out.println("======"+code+"========="+type);
                    if("type".equals(type)){
                        type_id = code;
                        if("0".equals(code)){
                            type_id = "";
                            tv_device_type.setText("设备类型");
                        }
                        brand_id = "";
                        tv_brand.setText("品牌");
                    }else{
                        brand_id = code;
                        if("0".equals(code)){
                            brand_id = "";
                            tv_brand.setText("品牌");
                        }
                    }

                mBGARefreshLayout.beginRefreshing();
                }

            @Override
            public void changeSelectPanel(Madapter madapter, View view) {
            }
        });
        dropDownMenu.setIndexColor(R.color.black);//用来设置点击（性别、民族、国家...）后的颜色
        dropDownMenu.setShowShadow(true);//要不要在popwindow展示的时候背景变为半透明
        dropDownMenu.setShowName("brand_name");//listView适配器中返回数据的名字（比如：我在适配器中传入List<Dic> list,在这个list中有n个Dic类，我要在性别、民族...View中显示的值在Dic这个类中的名字’）
        dropDownMenu.setSelectName("brand_id");//listView适配器中返回数据的名字（返回用来查询的）

        popSelectAdapter = new PopSelectAdapter(this);
    }


    private void bindRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new KnowledgeBaseAdapter(this, beanList, R.layout.item_knowledge);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {

                Bundle bundle = new Bundle();
                bundle.putString("knowledgeId", adapter.getItem(position).getKnowledgeId());
                IntentUtil.gotoActivity(KnowledgeBaseActivity.this, KnowledgeDetailActivity.class, bundle);
            }
        });
    }

    @Override
    protected void initGetData() {
        HttpRequestUtils.httpRequest(this, "知识图谱筛选条件", RequestValue.GET_KNOWLEDGE_GETKNOWLEDGETYPEALL, null, "GET",  new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        searchConditionBeanList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"),new TypeToken<ArrayList<SearchConditionBean>>(){});

                        if(searchConditionBeanList != null){
                            typeCondition.add(0,new SearchConditionBean.OptionsBean("0","全部"));
                            for(int i = 0; i < searchConditionBeanList.size();i++){
                                typeCondition.add(new SearchConditionBean.OptionsBean(searchConditionBeanList.get(i).getTypeId(),searchConditionBeanList.get(i).getTypeName()));

                                List<SearchConditionBean.OptionsBean> options = searchConditionBeanList.get(i).getOptions();
                                options.add(0,new SearchConditionBean.OptionsBean("0","全部"));
                                brandConditon.put(searchConditionBeanList.get(i).getTypeId(),options);
                            }

                        }
                        break;
                    default:
                        ToastUtil.showToast(KnowledgeBaseActivity.this, common.getInfo());
                        break;
                }
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
            }

            @Override
            public void returnException(Exception e, String msg) {
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
            }
        });
    }

    @Override
    protected void widgetListener() {

        recyclerView.setAdapter(adapter);

        //刷新控件监听器
        mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                page = 1;
                loadData();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                page++;
                loadData();
                return true;
            }
        });

        mBGARefreshLayout.beginRefreshing();
    }


    @OnClick({R.id.tv_device_type,R.id.tv_brand,R.id.tv_search})
    public void onBtnClick(View v){
        switch (v.getId()){
            case R.id.tv_device_type:
                if(popSelectAdapter == null){
                    return;
                }
                if(typeCondition.size() > 0){
                    popSelectAdapter.setItems(typeCondition);
                    dropDownMenu.showSelectList(DisplayUtil.getScreenWidth(this),
                            DisplayUtil.getShowHeight(this), popSelectAdapter,
                            LayoutInflater.from(KnowledgeBaseActivity.this).inflate(R.layout.pup_selectlist,null),
                            LayoutInflater.from(KnowledgeBaseActivity.this).inflate(R.layout.item_pop_selectlist,null),tv_device_type, tv_device_type, "type", false);
                }
               // popSelectAdapter.setItems();
                break;
            case R.id.tv_brand:
                if(popSelectAdapter == null){
                    return;
                }
                    if(!TextUtils.isEmpty(type_id)){
                        List<SearchConditionBean.OptionsBean> optionsBeen = brandConditon.get(type_id);
                        if(optionsBeen == null || optionsBeen.size() < 0){
                            ToastUtil.showToast(KnowledgeBaseActivity.this,"暂无品牌！");
                            return;
                        }
                        popSelectAdapter.setItems(optionsBeen);
                        dropDownMenu.showSelectList(DisplayUtil.getScreenWidth(this),
                                DisplayUtil.getShowHeight(this), popSelectAdapter,
                                LayoutInflater.from(KnowledgeBaseActivity.this).inflate(R.layout.pup_selectlist,null),
                                LayoutInflater.from(KnowledgeBaseActivity.this).inflate(R.layout.item_pop_selectlist,null),tv_brand, tv_brand, "brand", false);

                    }else{
                       ToastUtil.showToast(KnowledgeBaseActivity.this,"请先选择设备类型");
                    }

                break;
            case R.id.tv_search:
                search_txt = et_search.getText().toString().trim();
                if(TextUtils.isEmpty(search_txt)){
                    ToastUtil.showToast(KnowledgeBaseActivity.this,"请输入要搜索的内容");
                    return;
                }
                mBGARefreshLayout.beginRefreshing();
                break;
        }
    }
    @Override
    protected void init() {

    }


    private void loadData(){

        search_txt = et_search.getText().toString().trim();

        Map<String, String> map = new HashMap<>();
            map.put("type_id",type_id);
            map.put("brand_id",brand_id);
            map.put("wd", search_txt);
            map.put("curPage", ""+page);
        HttpRequestUtils.httpRequest(this, "知识图谱首页", RequestValue.GET_KNOWLEDGE_INDEX, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        List<KnowledgeBaseBean> knowledgeBeanList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"),new TypeToken<ArrayList<KnowledgeBaseBean>>(){});

                        loadDataToAdapter(knowledgeBeanList);

                        break;
                    default:
                        ToastUtil.showToast(KnowledgeBaseActivity.this, common.getInfo());
                        break;
                }
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
            }

            @Override
            public void returnException(Exception e, String msg) {
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
            }
        });
    }

    private void loadDataToAdapter(List<KnowledgeBaseBean> datas){
        if(page == 1){
            beanList = datas;
            adapter.replaceAll(datas);
            BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);

            if(datas == null || datas.size() < 1){
                    ToastUtil.showToast(KnowledgeBaseActivity.this,"暂无数据");
            }
        }else{
            beanList.addAll(datas);
            adapter.addAll(datas);
            BGARefreshLayoutUtils.endLoadingMore(mBGARefreshLayout);

            if(datas == null || datas.size() < 1){
                ToastUtil.showToast(KnowledgeBaseActivity.this,"没有更多数据了");
            }
        }
    }
}
