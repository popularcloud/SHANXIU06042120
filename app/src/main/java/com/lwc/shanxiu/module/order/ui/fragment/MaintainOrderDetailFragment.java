package com.lwc.shanxiu.module.order.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.BaseFragment;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.order.ui.IMaintainOrderDetailView;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.widget.CustomItem1;
import com.lwc.shanxiu.widget.PhotoBigFrameDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建于 2017/4/29.
 * 作者： 何栋
 * 邮箱： 294663966@qq.com
 */
public class MaintainOrderDetailFragment  extends BaseFragment implements IMaintainOrderDetailView {


    @BindView(R.id.cItemUnderTheSingle)
    CustomItem1 cItemUnderTheSingle;
    @BindView(R.id.cItemMaintainType)
    CustomItem1 cItemMaintainType;
    @BindView(R.id.cItemMalfunction)
    CustomItem1 cItemMalfunction;
    @BindView(R.id.imgPicture1)
    ImageView imgPicture1;
    @BindView(R.id.imgPicture2)
    ImageView imgPicture2;
    @BindView(R.id.cItemOrderNo)
    CustomItem1 cItemOrderNo;
    @BindView(R.id.cItemOrderTime)
    CustomItem1 cItemOrderTime;
    @BindView(R.id.cItemPayType)
    CustomItem1 cItemPayType;
    @BindView(R.id.cItemContacts)
    CustomItem1 cItemContacts;
    @BindView(R.id.cItemUnit)
    CustomItem1 cItemUnit;
    @BindView(R.id.lLayoutIcon)
    LinearLayout lLayoutIcon;

    private Order myOrder = null;
    private ImageLoaderUtil imageLoaderUtil;
    private PhotoBigFrameDialog frameDialog = null;

    private SharedPreferencesUtils preferencesUtils = null;
    private User user = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maintain_order_detail, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEngines(view);
        getIntentData();
        init();
        setListener();
        setDeviceDetailInfor(myOrder);
        getData();
    }

    /**
     * 图片放大框
     *
     * @param flag 0 ：第一张图   1 ： 第二张图
     */
    private void showPhotoBigFrameDialog(int flag) {
        String img = myOrder.getOrderImage();
        if (img != null) {
            String[] imgs = img.split(",");
            if (!"".equals(imgs[0])) {
                imageLoaderUtil.displayFromNet(getContext(), imgs[0], imgPicture1);
            }
            if (imgs.length >= 2) {
                if (!"".equals(imgs[1])) {
                    imageLoaderUtil.displayFromNet(getContext(), imgs[1], imgPicture2);
                }
            }
            frameDialog = new PhotoBigFrameDialog(getContext(), getActivity(), imgs[flag]);
            frameDialog.showNoticeDialog();
        }
    }

    /**
     * 请求网络获取数据
     */
    private void getData() {
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    public void init() {
        user = preferencesUtils.loadObjectData(User.class);
    }

    @Override
    public void initEngines(View view) {
        imageLoaderUtil = ImageLoaderUtil.getInstance();
        preferencesUtils = SharedPreferencesUtils.getInstance(getContext());
    }

    @Override
    public void getIntentData() {
        myOrder = (Order) getArguments().getSerializable("data");
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setDeviceDetailInfor(Order myOrder) {

        LLog.i(myOrder.toString());
        this.myOrder = myOrder;
        if (myOrder.getUserRealname() != null) {
            cItemUnderTheSingle.setRightText(myOrder.getUserRealname());
        } else {
            cItemUnderTheSingle.setRightText("暂无");
        }
        if (myOrder.getDeviceTypeName() != null) {
            cItemMaintainType.setRightText(myOrder.getDeviceTypeName());
        } else {
            cItemMaintainType.setRightText("暂无");
        }
        if (myOrder.getOrderDescription() != null) {
            cItemMalfunction.setRightText(myOrder.getOrderDescription());
        } else {
            cItemMalfunction.setRightText("暂无");
        }
        String img = myOrder.getOrderImage();

        if (img != null) {
            String[] imgs = img.split(",");
            if (!"".equals(imgs[0])) {
                imageLoaderUtil.displayFromNet(getContext(), imgs[0], imgPicture1);
            }
            if (imgs.length >= 2) {
                if (!"".equals(imgs[1])) {
                    imageLoaderUtil.displayFromNet(getContext(), imgs[1], imgPicture2);
                }
            }
        } else {
            lLayoutIcon.setVisibility(View.GONE);
        }
        cItemOrderNo.setRightText(myOrder.getOrderId() + "");
        cItemPayType.setRightText("线下支付");
        if (myOrder.getCreateTime() != null) {
            cItemOrderTime.setRightText(myOrder.getCreateTime());
        } else {
            cItemOrderTime.setRightText("暂无");
        }
        if (myOrder.getOrderContactPhone() != null) {
            cItemContacts.setRightText(myOrder.getOrderContactPhone());
        } else {
            cItemContacts.setRightText("暂无");
        }
        if (myOrder.getUserCompanyName() != null) {
            cItemUnit.setRightText(myOrder.getUserCompanyName());
        } else {
            cItemUnit.setRightText("暂无");
        }
    }

    @OnClick({R.id.imgPicture1, R.id.imgPicture2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgPicture1:
                showPhotoBigFrameDialog(0);
                break;
            case R.id.imgPicture2:
                showPhotoBigFrameDialog(1);
                break;
        }
    }
}
