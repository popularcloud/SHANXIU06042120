package com.lwc.shanxiu.module.order.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.ToastUtil;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.module.bean.Parts;
import com.lwc.shanxiu.module.bean.Solution;
import com.lwc.shanxiu.module.order.ui.adapter.PartsListAdapter;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.DialogUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.view.ProgressUtils;
import com.lwc.shanxiu.view.TileButton;
import com.lwc.shanxiu.widget.CustomDialog;

import org.json.JSONArray;
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
 * @version 1.0
 * @date 2017/3/9 14:51
 * @email 294663966@qq.com
 * 维修备件查询
 */
public class PartsListActivity extends BaseActivity{

    //没有数据时显示
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.iv_empty)
    ImageView iv_empty;
    @BindView(R.id.textTip)
    TextView textTip;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    @BindView(R.id.txtAllMoney)
    TextView txtAllMoney;
    @BindView(R.id.but_qd)
    TileButton but_qd;
    @BindView(R.id.rl_but)
    RelativeLayout rl_but;
    @BindView(R.id.ll_search)
    RelativeLayout ll_search;
    private PartsListAdapter orderListAdapter;
    private ArrayList<Parts> myParts = new ArrayList<>();
    private ProgressUtils progressUtils;
    private int curPage = 1;
    private String keyword;
    private PartsListActivity activity;
    private String imgs;
    private String desc;
    private String example_id;
    private Order myOrder;
    private int allMoney;
    private Solution solution;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_parts_search;
    }

    @Override
    protected void findViews() {
        activity = this;
        showBack();
        ButterKnife.bind(this);
        BGARefreshLayoutUtils.initRefreshLayout(this, mBGARefreshLayout);
        setTitle("维修配件");
        setRightImg(R.drawable.search_right, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_search.setVisibility(View.VISIBLE);
                rl_but.setVisibility(View.GONE);
                goneRight();
            }
        });
        goneRight();
        et_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }
            @Override
            public void afterTextChanged(Editable et) {
                String keyword = et.toString();
                if (keyword.length() > 0) {
                    iv_empty.setVisibility(View.VISIBLE);
                } else {
                    iv_empty.setVisibility(View.GONE);
                    myParts.clear();
                    orderListAdapter.replaceAll(myParts);
                    setMoney();
                }
            }
        });

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) et_search.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(PartsListActivity.this
                                            .getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);

                    // 跳转activity
                    keyword = et_search.getText().toString();
                    if (!TextUtils.isEmpty(keyword)) {
                        curPage = 1;
                        searchParts();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void updateNumber(int type, int position) {
        Parts parts = myParts.get(position);
        if (type == 1) {
            parts.setNumber(parts.getNumber()+1);
        } else {
            int number = parts.getNumber();
            if (number > 0) {
                number = number-1;
            } else {
                number = 0;
            }
            parts.setNumber(number);
        }
        myParts.set(position, parts);
        orderListAdapter.replaceAll(myParts);
        setMoney();
    }

    private void setMoney() {
        allMoney = 0;
        for (int i=0; i<myParts.size(); i++) {
            Parts p = myParts.get(i);
            if (p.getNumber() > 0) {
                allMoney = allMoney+(p.getNumber()*Integer.parseInt(p.getPartsPrice()));
            }
        }
        String tip;
        if (myOrder.getIsWarranty() == 1) {
            tip = "\n(保外，要收取用户上门费及零件费)";
        } else {
            tip = "\n(保内，不收取用户上门费及维修零件费)";
        }
        String str = "";
        if (allMoney > 0) {
            str = "合计费用："+Utils.chu(allMoney+"", "100")+"元"+tip;
            txtAllMoney.setText(Utils.getSpannableStringBuilder(0, Utils.chu(allMoney+"", "100").length()+6, getResources().getColor(R.color.red_money), str, 16));
        } else {
            str = "合计费用：0元"+tip;
            txtAllMoney.setText(Utils.getSpannableStringBuilder(0, 7, getResources().getColor(R.color.red_money), str, 15));
        }
    }

    @Override
    protected void init() {
        progressUtils = new ProgressUtils();
    }

    @Override
    protected void initGetData() {
        desc = getIntent().getStringExtra("fault_describe");
        example_id = getIntent().getStringExtra("example_id");
        imgs = getIntent().getStringExtra("imgs");
        myOrder = (Order) getIntent().getSerializableExtra("order");
        solution = (Solution) getIntent().getSerializableExtra("solution");
        if (solution != null) {
            example_id = solution.getExampleId();
        }
        if (!TextUtils.isEmpty(myOrder.getReqairName())) {
            et_search.setText(myOrder.getReqairName());
//            searchParts();
            et_search.setSelection(myOrder.getReqairName().length());
        }
    }

    @Override
    protected void widgetListener() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderListAdapter = new PartsListAdapter(this, myParts, R.layout.item_parts);

//        orderListAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(View itemView, int viewType, int position) {
//
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("data", myParts.get(position));
//                IntentUtil.gotoActivity(PartsListActivity.this, OrderDetailActivity.class, bundle);
//            }
//        });
        orderListAdapter.setActivity(activity);
        recyclerView.setAdapter(orderListAdapter);
    }

    @OnClick({R.id.iv_empty, R.id.tv_search, R.id.but_qd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_qd:
                for (int i = 0; i < myParts.size(); i++) {
                    Parts p = myParts.get(i);
                    if (p.getNumber() > 0) {
                        affirm();
                        break;
                    }
                    if (i == myParts.size()-1) {
                        ToastUtil.show(PartsListActivity.this, "请选择需要更换的配件!");
                    }
                }
                break;
            case R.id.iv_empty:
                et_search.setText("");
                myParts.clear();
                orderListAdapter.replaceAll(myParts);
                break;
            case R.id.tv_search:
                keyword = et_search.getText().toString().trim();
                if (TextUtils.isEmpty(keyword)) {
                    ToastUtil.show(this, "请输入要搜索的配件信息");
                    return;
                }
                curPage=1;
                searchParts();
                break;
        }
    }

    /**
     * 搜索配件
     */
    private void searchParts() {
        if (Utils.isFastClick(1500)){
            if (curPage > 1){
                curPage--;
            }
            return;
        }
        visibleRight();
        ll_search.setVisibility(View.GONE);
        rl_but.setVisibility(View.VISIBLE);
        progressUtils.showCustomProgressDialog(this);
        Map<String, String> map = new HashMap<>();
        map.put("pageSize", "20");
        map.put("partName", keyword);
        HttpRequestUtils.httpRequest(this, "searchParts", RequestValue.GET_PARTS_LIST, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        ArrayList<Parts> list = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<Parts>>() {
                        });
                        if (list != null && list.size() > 0) {
                            myParts = list;
                        } else {
                            myParts = new ArrayList<>();
                        }
                        orderListAdapter.replaceAll(myParts);
                        setMoney();
                        if (myParts != null && myParts.size() > 0) {
                            textTip.setVisibility(View.GONE);
                        } else {
                            textTip.setVisibility(View.VISIBLE);
                        }
                        break;
                    default:
                        textTip.setVisibility(View.VISIBLE);
                        com.lwc.shanxiu.utils.ToastUtil.showLongToast(PartsListActivity.this, common.getInfo());
                        break;
                }
                progressUtils.dismissCustomProgressDialog();
                BGARefreshLayoutUtils.endLoadingMore(mBGARefreshLayout);
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError("searchParts  " + e.toString());
                BGARefreshLayoutUtils.endLoadingMore(mBGARefreshLayout);
                progressUtils.dismissCustomProgressDialog();
                if (!TextUtils.isEmpty(msg))
                    com.lwc.shanxiu.utils.ToastUtil.showLongToast(PartsListActivity.this, msg);
            }
        });
    }

    private void affirm() {
        if (Utils.isFastClick(1000)) {
            return;
        }
        JSONObject map = new JSONObject();
        try {
            map.put("order_id", myOrder.getOrderId());
            map.put("fault_describe", desc);
            map.put("example_id", example_id);
            if (!TextUtils.isEmpty(imgs)) {
                map.put("describe_images", imgs);
            }
            JSONArray FCarPhotos = new JSONArray();
            JSONObject obj = new JSONObject();
            for (int i = 0; i < myParts.size(); i++) {
                Parts p = myParts.get(i);
                if (p.getNumber() > 0) {
                    JSONObject o = new JSONObject();
                    o.put("partsId", p.getPartsId());
                    o.put("partsNum", p.getNumber());
                    FCarPhotos.put(o);
                }
            }
            obj.put("sumCost", "" + allMoney);
            obj.put("parts", FCarPhotos);
            map.put("partsJson", obj);
        } catch (Exception e){}

        HttpRequestUtils.httpRequestJson(this, "提交检测报告", RequestValue.POST_ORDER_PERSONAL_DETECTION, map, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        DialogUtil.showUpdateAppDg(PartsListActivity.this, "温馨提示", "我知道了", "你申请的配件已向厂家发出，厂家会及时向您寄出配件，请留意物流信息!", new CustomDialog.OnClickListener() {
                            @Override
                            public void onClick(CustomDialog dialog, int id, Object object) {
                                setResult(RESULT_OK);
                                finish();
                                dialog.dismiss();
                            }
                        });
                        break;
                    default:
                        com.lwc.shanxiu.utils.ToastUtil.showToast(PartsListActivity.this, common.getInfo());
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                com.lwc.shanxiu.utils.ToastUtil.showToast(PartsListActivity.this, msg);
            }
        });
    }
}
