package com.lwc.shanxiu.module.user;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hedgehog.ratingbar.RatingBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.bean.Repairman;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.bean.Evaluate;
import com.lwc.shanxiu.module.common_adapter.EvaluateListAdapter;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.Tag;
import com.lwc.shanxiu.view.TagListView;
import com.lwc.shanxiu.widget.CircleImageView;
import com.lwc.shanxiu.widget.PhotoBigFrameDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by 何栋 on 2017/10/15.
 * 294663966@qq.com
 * 维修员信息
 */
public class RepairmanInfoActivity extends BaseActivity {

    @BindView(R.id.txtActionbarTitle)
    TextView txtActionbarTitle;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.txtOrderCount)
    TextView txtOrderCount;
    @BindView(R.id.imgHead)
    CircleImageView imgHead;
    @BindView(R.id.tagview)
    TagListView mTagListView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    @BindView(R.id.tv_msg)
    TextView tv_msg;

    private final ArrayList<Tag> mTags = new ArrayList<>();
    private Repairman repairman;
    private ImageLoaderUtil imageLoaderUtil;
    private String repair_id;
    private int page=1;
    private EvaluateListAdapter adapter;
    private ArrayList<Evaluate> evaluates = new ArrayList<>();

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_repairman_info;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        BGARefreshLayoutUtils.initRefreshLayout(this, mBGARefreshLayout);
        imgBack.setVisibility(View.VISIBLE);
        setViewData();
    }

    @Override
    public void init() {
        txtActionbarTitle.setText("我的评价");
        repair_id = getIntent().getStringExtra("repair_id");
        if (repairman == null && !TextUtils.isEmpty(repair_id)) {
            getMaintenanceInfo();
        } else if (repairman != null) {
            setViewData();
        }
        getEvaluates();
    }

    @Override
    protected void initGetData() {
    }

    @Override
    protected void widgetListener() {
    }


    /**
     * 获取维修员信息
     */
    private void getMaintenanceInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("maintenanceId", repair_id);
        HttpRequestUtils.httpRequest(this, "查询工程师信息", RequestValue.POST_MAINTENANCE_INFO, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        repairman = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"), Repairman.class);
                        if (repairman == null) {
                            ToastUtil.showLongToast(RepairmanInfoActivity.this, "维修员数据异常");
                            onBackPressed();
                            return;
                        }
                        setViewData();
                        break;
                    default:
                        ToastUtil.showLongToast(RepairmanInfoActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
                ToastUtil.showLongToast(RepairmanInfoActivity.this, msg);
            }
        });
    }

    /**
     * 设置view数据
     */
    private void setViewData() {
        imageLoaderUtil = ImageLoaderUtil.getInstance();
        if (repairman != null) {

            String picture = repairman.getMaintenanceHeadImage();
            if (picture != null && !TextUtils.isEmpty(picture)) {
                imageLoaderUtil.displayFromNet(this, picture, imgHead);
            } else {
                imageLoaderUtil.displayFromLocal(this, imgHead, R.drawable.default_portrait_100);
            }

            txtOrderCount.setText(repairman.getOrderCount());   //完成订单总数

            String name = repairman.getMaintenanceName();
            if (name != null && !TextUtils.isEmpty(name)) {
                txtName.setText(""+name);
            } else {
                txtName.setText("暂无");
            }
//            txtSkills.setText(repairman.getSkills());
            if (!TextUtils.isEmpty(repairman.getMaintenanceStar())) {
                Float avgservice = Float.parseFloat(repairman.getMaintenanceStar());
                ratingBar.setStarCount(5);
                ratingBar.setStar(avgservice);
            } else {
                ratingBar.setStar(0);
            }
            if (!TextUtils.isEmpty(repairman.getMaintenanceLabelNames())) {
                mTagListView.setVisibility(View.VISIBLE);
                mTagListView.setDeleteMode(false);
                mTagListView.setTagViewTextColorRes(R.color.black);
                mTagListView.setTagViewBackgroundRes(R.drawable.tag_bg3);
                String[] labels = repairman.getMaintenanceLabelNames().split(",");
                if (labels != null && labels.length > 0) {
                    for (int i=0; i<labels.length; i++) {
                        String [] names = labels[i].split("_");
                        Tag tag = new Tag();
                        tag.setLabelName(names[0]+"  "+names[1]);
                        mTags.add(tag);
                    }
                    mTagListView.setTags(mTags);
                }else{
                    mTagListView.setVisibility(View.GONE);
                }
            } else {
                mTagListView.setVisibility(View.GONE);
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EvaluateListAdapter(this, evaluates, R.layout.item_evaluate);
        recyclerView.setAdapter(adapter);
        //刷新控件监听器
        mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                page = 1;
                getEvaluates();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                page++;
                getEvaluates();
                return true;
            }
        });
    }
    private void getEvaluates() {
        HashMap<String, String> map = new HashMap<>();
        map.put("curPage",page+"");
        map.put("maintenanceId", repair_id);
        HttpRequestUtils.httpRequest(this, "getEvaluates", RequestValue.GET_COMMENT_LIST, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String result) {
                Common common = JsonUtil.parserGsonToObject(result, Common.class);
                if (common.getStatus().equals("1")) {
                    ArrayList<Evaluate> current = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(result, "data"), new TypeToken<ArrayList<Evaluate>>() {
                    });
                    if (page == 1) {
                        if (current != null && current.size() > 0) {
                            evaluates = current;
                        } else {
                            evaluates = new ArrayList<>();
                        }
                    } else if (page > 1) {
                        if (current != null && current.size() > 0) {
                            evaluates.addAll(current);
                        } else {
                            ToastUtil.showToast(RepairmanInfoActivity.this, "暂无更多评价信息");
                            page--;
                        }
                    }
                    adapter.replaceAll(evaluates);
                    if (evaluates.size() > 0) {
                        tv_msg.setVisibility(View.GONE);
                    } else {
                        tv_msg.setVisibility(View.VISIBLE);
                    }
                } else {
                    ToastUtil.showToast(RepairmanInfoActivity.this, common.getInfo());
                }
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
                BGARefreshLayoutUtils.endLoadingMore(mBGARefreshLayout);
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showToast(RepairmanInfoActivity.this, msg);
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
                BGARefreshLayoutUtils.endLoadingMore(mBGARefreshLayout);
            }
        });
    }
    @OnClick({R.id.imgHead})
    public void onViewClicked(View view) {
        if (!TextUtils.isEmpty(repairman.getMaintenanceHeadImage())) {
            PhotoBigFrameDialog frameDialog = new PhotoBigFrameDialog(RepairmanInfoActivity.this, RepairmanInfoActivity.this, repairman.getMaintenanceHeadImage());
            frameDialog.showNoticeDialog();
        }
    }
}
