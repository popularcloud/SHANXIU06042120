package com.lwc.shanxiu.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Dimension;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.configs.BroadcastFilters;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.message.adapter.KnowledgeBaseAdapter;
import com.lwc.shanxiu.module.message.adapter.Madapter;
import com.lwc.shanxiu.module.message.adapter.PopSelectAdapter;
import com.lwc.shanxiu.module.message.bean.KnowledgeBaseBean;
import com.lwc.shanxiu.module.message.bean.SearchConditionBean;
import com.lwc.shanxiu.module.message.bean.SearchKeyWordBean;
import com.lwc.shanxiu.module.message.ui.KnowledgeDetailActivity;
import com.lwc.shanxiu.module.message.ui.PublishActivity;
import com.lwc.shanxiu.module.message.ui.PublishAndRequestListActivity;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.DisplayUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.widget.DropDownMenu;
import com.lwc.shanxiu.widget.TagsLayout;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 我的
 */
public class KnowledgeBaseFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    @BindView(R.id.tl_tags)
    TagsLayout tl_tags;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.tv_device_type)
    TextView tv_device_type;
    @BindView(R.id.tv_brand)
    TextView tv_brand;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_request)
    TextView tv_request;
    @BindView(R.id.tv_publish)
    TextView tv_publish;
    @BindView(R.id.tv_agreeList)
    TextView tv_agreeList;
    @BindView(R.id.iv_no_data)
    ImageView iv_no_data;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;

    private boolean isClearCondition = true;

    List<KnowledgeBaseBean> beanList = new ArrayList<>();
    private KnowledgeBaseAdapter adapter;
    private int page = 1;
    private String type_id = "";
    private String brand_id = "";
    private String search_txt = "";
    private ArrayList<SearchConditionBean> searchConditionBeanList;
    private PopSelectAdapter popSelectAdapter;
    private DropDownMenu dropDownMenu;
    private TextView theLastText;
    private int scrollDistance = 0;
    private int scrollStatus = 0;  //0.表示禁止不动，1，手指拖动 2.自动滚动

    private List<SearchConditionBean.OptionsBean> typeCondition = new ArrayList<>();
    private Map<String,List<SearchConditionBean.OptionsBean>> brandConditon = new HashMap<>();


    @Override
    protected View getViews() {
        return View.inflate(getActivity(), R.layout.activity_knowledge_base, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle("知识库");
        imgBack.setVisibility(View.GONE);
        tv_search.setVisibility(View.GONE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_search.getLayoutParams();
        layoutParams.setMargins(30,0,30,0);
        ll_search.setLayoutParams(layoutParams);

        /*et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                search_txt = et_search.getText().toString().trim();
            *//*    if(TextUtils.isEmpty(search_txt)){
                    ToastUtil.showToast(getActivity(),"请输入要搜索的内容");
                    return;
                }*//*

                clearTypeSearch();

                if( i1 > i2 && theLastText != null){
                    theLastText.setTextColor(Color.parseColor("#999999"));
                }

                isClearCondition = false;
                mBGARefreshLayout.beginRefreshing();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“搜索”键*/
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    String key = et_search.getText().toString().trim();
                    if(TextUtils.isEmpty(key)){
                        ToastUtil.showToast(getActivity(),"请输入您想要搜索的关键字");
                        return true;
                    }

                    clearTypeSearch();
                    if(theLastText != null){
                        theLastText.setTextColor(Color.parseColor("#999999"));
                    }


                    //  开始搜索
                    isClearCondition = false;
                    mBGARefreshLayout.beginRefreshing();

                    //  这里记得一定要将键盘隐藏了
                    InputMethodManager imm = (InputMethodManager) v.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                    return true;
                }else{
                    //ToastUtil.showToast(getActivity(),"其他"+actionId);
                }
                return false;
            }
        });


        BGARefreshLayoutUtils.initRefreshLayout(getActivity(), mBGARefreshLayout);
        bindRecycleView();
        initSearchCondition();

        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                scrollDistance += dy;
                if(Math.abs(scrollDistance) >= 5 && scrollStatus != 1){
                    if(scrollDistance >= 0){
                        changeVisibleGone(View.GONE);
                    }else{
                        changeVisibleGone(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                scrollStatus = newState;
            }
        });


        //刷新控件监听器
        mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                page = 1;
                if(isClearCondition){
                    clearTextAndTagSearch();
                    clearTypeSearch();
                }
                loadData();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                page++;
                loadData();
                return true;
            }
        });

        HttpRequestUtils.httpRequest(getActivity(), "知识图谱筛选条件", RequestValue.GET_KNOWLEDGE_GETKNOWLEDGETYPEALL, null, "GET",  new HttpRequestUtils.ResponseListener() {
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
                        ToastUtil.showToast(getActivity(), common.getInfo());
                        break;
                }
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
            }

            @Override
            public void returnException(Exception e, String msg) {
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
            }
        });
        onGetKeyWord();

        mBGARefreshLayout.beginRefreshing();

        iv_no_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClearCondition = true;
                mBGARefreshLayout.beginRefreshing();
                iv_no_data.setVisibility(View.GONE);
                mBGARefreshLayout.setVisibility(View.VISIBLE);
            }
        });

    }


    public synchronized void changeVisibleGone(int viGo){
        if(viGo != tl_tags.getVisibility()){
            tl_tags.setVisibility(viGo);
            scrollDistance = 0;
        }
    }

    private void clearTypeSearch(){
        if(!TextUtils.isEmpty(type_id) || !TextUtils.isEmpty(brand_id)){
            type_id = "";
            brand_id = "";
            tv_device_type.setText("设备类型");
            tv_brand.setText("品牌");
            tv_device_type.setTextColor(Color.parseColor("#000000"));
            tv_brand.setTextColor(Color.parseColor("#000000"));

            tv_device_type.setCompoundDrawables(null, null,
                    getDrawable(R.drawable.ner_up_h), null);
            tv_brand.setCompoundDrawables(null, null,
                    getDrawable(R.drawable.ner_up_h), null);
        }
    }

    private void clearTextAndTagSearch(){
        if(!TextUtils.isEmpty(search_txt)){
            search_txt = "";
            et_search.setText("");

            if(theLastText != null){
                theLastText.setTextColor(Color.parseColor("#999999"));
            }
        }
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {

    }

    private void bindRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new KnowledgeBaseAdapter(getActivity(), beanList, R.layout.item_knowledge);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {

                Bundle bundle = new Bundle();
                bundle.putString("knowledgeId", adapter.getItem(position).getKnowledgeId());
                IntentUtil.gotoActivity(getActivity(), KnowledgeDetailActivity.class, bundle);
            }
        });
    }

    private void initSearchCondition() {
        dropDownMenu = DropDownMenu.getInstance(getActivity(), new DropDownMenu.OnListCkickListence() {
            @Override
            public void search(String code, String type,final String text) {
                System.out.println("======"+code+"========="+type);
                if("type".equals(type)){
                    type_id = code;
                    if("0".equals(code)){
                        type_id = "";
                        tv_device_type.setText("设备类型");
                        tv_device_type.setCompoundDrawables(null, null,
                                getDrawable(R.drawable.ner_up_h), null);
                    }else{
                        tv_device_type.setText(text);
                        tv_device_type.setCompoundDrawables(null, null,
                                 getDrawable(R.drawable.ner_up_l), null);
                    }
                    brand_id = "";
                    tv_brand.setText("品牌");
                    tv_brand.setCompoundDrawables(null, null,
                            getDrawable(R.drawable.ner_up_h), null);

                }else{
                    brand_id = code;
                    if("0".equals(code)){
                        brand_id = "";
                        tv_brand.setText("品牌");
                        tv_brand.setCompoundDrawables(null, null,
                                getDrawable(R.drawable.ner_up_h), null);
                    }else{
                        tv_brand.setCompoundDrawables(null, null,
                                getDrawable(R.drawable.ner_up_l), null);
                    }
                }
                clearTextAndTagSearch();
                isClearCondition = false;
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

        popSelectAdapter = new PopSelectAdapter(getActivity());
    }


    @OnClick({R.id.tv_device_type,R.id.tv_brand,R.id.tv_search,R.id.tv_request,R.id.tv_publish,R.id.tv_agreeList})
    public void onBtnClick(View v){
        switch (v.getId()){
            case R.id.tv_device_type:
                if(!TextUtils.isEmpty(type_id)){
                    tv_device_type.setCompoundDrawables(null, null,
                            getDrawable(R.drawable.ner_down_l), null);
                }else{
                    tv_device_type.setCompoundDrawables(null, null,
                            getDrawable(R.drawable.ner_down_h), null);
                }

                if(popSelectAdapter == null){
                    return;
                }
                if(typeCondition.size() > 0){
                    popSelectAdapter.setItems(typeCondition);
                    dropDownMenu.showSelectList(DisplayUtil.getScreenWidth(getActivity()),
                            DisplayUtil.getShowHeight(getActivity()), popSelectAdapter,
                            LayoutInflater.from(getActivity()).inflate(R.layout.pup_selectlist,null),
                            LayoutInflater.from(getActivity()).inflate(R.layout.item_pop_selectlist,null),tv_device_type, tv_device_type, "type", false);
                }
                // popSelectAdapter.setItems();
                break;
            case R.id.tv_brand:

                if(!TextUtils.isEmpty(brand_id)){
                    tv_brand.setCompoundDrawables(null, null,
                            getDrawable(R.drawable.ner_down_l), null);
                }else{
                    tv_brand.setCompoundDrawables(null, null,
                            getDrawable(R.drawable.ner_down_h), null);
                }

                if(popSelectAdapter == null){
                    return;
                }
                if(!TextUtils.isEmpty(type_id)){
                    List<SearchConditionBean.OptionsBean> optionsBeen = brandConditon.get(type_id);
                    if(optionsBeen == null || optionsBeen.size() < 0){
                        ToastUtil.showToast(getActivity(),"暂无品牌！");
                        return;
                    }
                    popSelectAdapter.setItems(optionsBeen);
                    dropDownMenu.showSelectList(DisplayUtil.getScreenWidth(getActivity()),
                            DisplayUtil.getShowHeight(getActivity()), popSelectAdapter,
                            LayoutInflater.from(getActivity()).inflate(R.layout.pup_selectlist,null),
                            LayoutInflater.from(getActivity()).inflate(R.layout.item_pop_selectlist,null),tv_brand, tv_brand, "brand", false);

                }else{
                    ToastUtil.showToast(getActivity(),"请先选择设备类型");
                }

                break;
            case R.id.tv_search:
                search_txt = et_search.getText().toString().trim();
                if(TextUtils.isEmpty(search_txt)){
                    ToastUtil.showToast(getActivity(),"请输入要搜索的内容");
                    return;
                }
                isClearCondition = false;
                mBGARefreshLayout.beginRefreshing();
                break;
            case R.id.tv_request:
                Bundle bundle = new Bundle();
                bundle.putInt("operateType",2);
                IntentUtil.gotoActivity(getActivity(), PublishActivity.class,bundle);
                break;
            case R.id.tv_publish:
                Bundle bundle2 = new Bundle();
                bundle2.putInt("operateType",1);
                IntentUtil.gotoActivity(getActivity(), PublishActivity.class,bundle2);
                break;
            case R.id.tv_agreeList:
                IntentUtil.gotoActivity(getActivity(), PublishAndRequestListActivity.class);
                break;
        }
    }

    public Drawable getDrawable(int d) {
        Drawable drawable = getActivity().getResources().getDrawable(d);
        drawable.setBounds(0,0,drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

    @Override
    protected void widgetListener() {

    }

    private void loadData(){

        search_txt = et_search.getText().toString().trim();
        Map<String, String> map = new HashMap<>();
        map.put("type_id",type_id);
        map.put("brand_id",brand_id);
        map.put("wd", search_txt);
        map.put("curPage", ""+page);
        HttpRequestUtils.httpRequest(getActivity(), "知识图谱首页", RequestValue.GET_KNOWLEDGE_INDEX, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                isClearCondition = true;
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        List<KnowledgeBaseBean> knowledgeBeanList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"),new TypeToken<ArrayList<KnowledgeBaseBean>>(){});
                        loadDataToAdapter(knowledgeBeanList);
                        break;
                    default:
                        ToastUtil.showToast(getActivity(), common.getInfo());
                        break;
                }
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
            }

            @Override
            public void returnException(Exception e, String msg) {
                isClearCondition = true;
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
                //ToastUtil.showToast(KnowledgeBaseActivity.this,"暂无数据");
                iv_no_data.setVisibility(View.VISIBLE);
                mBGARefreshLayout.setVisibility(View.GONE);
            }else{
                iv_no_data.setVisibility(View.GONE);
                mBGARefreshLayout.setVisibility(View.VISIBLE);
            }
        }else{
            beanList.addAll(datas);
            adapter.addAll(datas);
            BGARefreshLayoutUtils.endLoadingMore(mBGARefreshLayout);

            if(datas == null || datas.size() < 1){
                ToastUtil.showToast(getActivity(),"没有更多数据了");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mBGARefreshLayout != null){
            isClearCondition = true;
            mBGARefreshLayout.beginRefreshing();
        }


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser  && getActivity() != null){
            ImmersionBar.with(getActivity())
                    .statusBarColor(R.color.white)
                    .statusBarDarkFont(true)
                    .navigationBarColor(R.color.white).init();
        }
    }



    @Override
    protected void init() {
    }

    @Override
    public void initGetData() {


    }

    /**
     * 获取搜索关键字
     */
    private void onGetKeyWord(){
        HttpRequestUtils.httpRequest(getActivity(), "获取搜索关键词", RequestValue.GET_KNOWLEDGE_KNOWLEDGEKEYWORDRANK, null, "GET",  new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        List<SearchKeyWordBean> searchKeyWordBeenList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"),new TypeToken<ArrayList<SearchKeyWordBean>>(){});
                        if(searchKeyWordBeenList != null){
                            tl_tags.setVisibility(View.VISIBLE);
                            tl_tags.removeAllViews();
                            for(int i = 0; i < searchKeyWordBeenList.size();i++){
                                SearchKeyWordBean searchKeyWordBean = searchKeyWordBeenList.get(i);
                                final TextView textView = new TextView(getActivity());
                                textView.setText(searchKeyWordBean.getKeywordName());
                                textView.setTextColor(Color.parseColor("#999999"));
                                textView.setTextSize(Dimension.SP,12);
                                textView.setGravity(Gravity.CENTER);
                                textView.setPadding(20,15,20,15);
                                textView.setBackgroundResource(R.drawable.round_square_white);
                              /*  int index = searchKeyWordBean.getKeyword_name().indexOf("：");
                                String tagStr = searchKeyWordBean.getKeyword_name().substring(index+1);*/
                                //textView.setText(searchKeyWordBean.getKeyword_name());
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        et_search.setText(((TextView)view).getText());
                                        clearTypeSearch();
                                        isClearCondition = false;
                                        mBGARefreshLayout.beginRefreshing();
                                        if(theLastText != null){
                                            theLastText.setTextColor(Color.parseColor("#999999"));
                                        }
                                        theLastText =  ((TextView)view);
                                        theLastText.setTextColor(Color.parseColor("#1481ff"));
                                    }
                                });
                                tl_tags.addView(textView);
                            }

                        }else{
                            tl_tags.setVisibility(View.GONE);
                        }
                        break;
                    default:
                        ToastUtil.showToast(getActivity(), common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
            }
        });
    }

    @Override
    protected void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (BroadcastFilters.NOTIFI_GET_ORDER_COUNT.equals(intent.getAction())) {
        }
    }
}
