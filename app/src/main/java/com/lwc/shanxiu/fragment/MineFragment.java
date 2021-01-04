package com.lwc.shanxiu.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.hedgehog.ratingbar.RatingBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.UserGuideActivity;
import com.lwc.shanxiu.configs.BroadcastFilters;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.authentication.activity.AuthenticationMainActivity;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.order.ui.activity.MyOrderListActivity;
import com.lwc.shanxiu.module.setting.SettingActivity;
import com.lwc.shanxiu.module.setting.ShareActivity;
import com.lwc.shanxiu.module.setting.SuggestActivity;
import com.lwc.shanxiu.module.user.LoginOrRegistActivity;
import com.lwc.shanxiu.module.user.MySkillsActivity;
import com.lwc.shanxiu.module.user.RepairmanInfoActivity;
import com.lwc.shanxiu.module.user.UserInfoActivity;
import com.lwc.shanxiu.module.wallet.WalletActivity;
import com.lwc.shanxiu.utils.DialogUtil;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.SystemUtil;
import com.lwc.shanxiu.widget.CircleImageView;
import com.lwc.shanxiu.widget.CustomDialog;
import com.lwc.shanxiu.widget.PhotoBigFrameDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.img_head)
    CircleImageView img_head;
    @BindView(R.id.txt_name)
    TextView txt_name;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.txt_jineng)
    TextView txt_jineng;
    @BindView(R.id.iv_red)
    ImageView iv_red;
    @BindView(R.id.imgNewMsg)
    ImageView imgNewMsg;
    @BindView(R.id.imgRight)
    ImageView imgRight;
    @BindView(R.id.txtOrderCount)
    TextView txtOrderCount;
    @BindView(R.id.txtmyMoney)
    TextView txtmyMoney;
    @BindView(R.id.tv_uthentication)
    TextView tv_uthentication;
    @BindView(R.id.ll_authentication)
    LinearLayout ll_authentication;

    private SharedPreferencesUtils preferencesUtils = null;
    private User user = null;
    private ImageLoaderUtil imageLoaderUtil = null;

    @Override
    protected View getViews() {
        return View.inflate(getActivity(), R.layout.fragment_mine, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        imgRight.setVisibility(View.VISIBLE);
        imgRight.setImageResource(R.drawable.ic_setting);
        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtil.gotoActivityForResult(getActivity(), SettingActivity.class, 1991);
            }
        });
        return rootView;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        preferencesUtils = SharedPreferencesUtils.getInstance(getContext());
        user = preferencesUtils.loadObjectData(User.class);

        setTitle("我的");
    }

    @OnClick({R.id.txtEvaluate, R.id.txtUserGuide, R.id.txtFeedback, R.id.txtMyOrder, R.id.img_head, R.id.txt_share,
            R.id.txt_jineng, R.id.txtUserInfor, R.id.txtWallet, R.id.ll_kf,R.id.ll_OrderCountTitle,R.id.ll_myMoneyTitle,R.id.ll_authentication})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.txtUserGuide:
                IntentUtil.gotoActivity(getActivity(), UserGuideActivity.class);
                break;
            case R.id.txtEvaluate:
                Bundle bundle = new Bundle();
                bundle.putSerializable("repair_id", user.getMaintenanceId());
                IntentUtil.gotoActivity(getActivity(), RepairmanInfoActivity.class, bundle);
                break;
            case R.id.img_head:
                if (!TextUtils.isEmpty(user.getMaintenanceHeadImage())) {
                    PhotoBigFrameDialog frameDialog = new PhotoBigFrameDialog(getContext(), getActivity(), user.getMaintenanceHeadImage());
                    frameDialog.showNoticeDialog();
                }
                break;
            case R.id.txtFeedback:
                IntentUtil.gotoActivity(getActivity(), SuggestActivity.class);
                break;
            case R.id.txt_share:
                IntentUtil.gotoActivity(getActivity(), ShareActivity.class);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.ll_kf:
                DialogUtil.showMessageDg(getActivity(), "拨打电话", "400-881-0769", new CustomDialog.OnClickListener() {

                    @Override
                    public void onClick(CustomDialog dialog, int id, Object object) {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:4008810769");
                        intent.setData(data);
                        startActivity(intent);
                    }
                });
                break;
            case R.id.txtWallet://钱包
            case R.id.ll_myMoneyTitle:
                IntentUtil.gotoActivity(getActivity(), WalletActivity.class);
                break;
            case R.id.txt_jineng:
                IntentUtil.gotoActivity(getActivity(), MySkillsActivity.class);
                break;
            case R.id.txtUserInfor:
                IntentUtil.gotoActivity(getActivity(), UserInfoActivity.class);
                break;
            case R.id.ll_authentication:  //工程师认证
              //  if("0".equals(user.getIsSecrecy())){
                    IntentUtil.gotoActivity(getActivity(), AuthenticationMainActivity.class);
               /* }else{

                }*/
                break;
            case R.id.txtMyOrder:
            case R.id.ll_OrderCountTitle:
                IntentUtil.gotoActivity(getActivity(), MyOrderListActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    protected void widgetListener() {
    }

    @Override
    public void onResume() {
        //被下面的方法替换
        txt_jineng.setVisibility(View.VISIBLE);
        user = preferencesUtils.loadObjectData(User.class);
        if (user == null) {
            IntentUtil.gotoActivityAndFinish(getActivity(), LoginOrRegistActivity.class);
            return;
        }
        if (!TextUtils.isEmpty(user.getMaintenanceName())) {
            String name = user.getMaintenanceName();
      /*      if (!TextUtils.isEmpty(user.getMaintenanceSignature())) {
                name = user.getMaintenanceName() + " (" + user.getMaintenanceSignature() + ")";
            }*/
            txt_name.setText(name);
        } else {
            String name = user.getUserName();
        /*    if (!TextUtils.isEmpty(user.getMaintenanceSignature())) {
                name = user.getMaintenanceName() + " (" + user.getMaintenanceSignature() + ")";
            }*/
            txt_name.setText(name);
        }
        if (user.getMaintenanceHeadImage() != null && !TextUtils.isEmpty(user.getMaintenanceHeadImage())) {
            imageLoaderUtil.displayFromNetD(getContext(), user.getMaintenanceHeadImage(), img_head);
        } else {
            imageLoaderUtil.displayFromLocal(getContext(), img_head, R.drawable.default_portrait_100);
        }
//        txt_sign.setText("综合评分：" + user.getMaintenanceStar());
        txtOrderCount.setText(String.valueOf(user.getOrderCount()));
        txtmyMoney.setText(Utils.getMoney(String.valueOf(user.getBanlance())));

        if (user.getMaintenanceStar() > 0) {
            float avgservice = user.getMaintenanceStar();
            ratingBar.setStarCount(5);
            if(avgservice != 0){
                ratingBar.setStar(avgservice);
           }
        } else {
            ratingBar.setStar(0);
        }


       if("1".equals(user.getIsSecrecy())){
            tv_uthentication.setText("已认证");
        }else if("2".equals(user.getIsSecrecy())){
            tv_uthentication.setText("申请中");
        }else{
           tv_uthentication.setText("未认证");
       }

      /*  if("3".equals(user.getCompanySecrecy())){
            ll_authentication.setVisibility(View.VISIBLE);
        }else{
            ll_authentication.setVisibility(View.GONE);
        }*/

        updateVersion();

        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser  && getActivity() != null){
            ImmersionBar.with(getActivity())
                    .statusBarColor(R.color.blue_37)
                    .statusBarDarkFont(true).init();
        }
    }

    public void updateVersion() {
        if (preferencesUtils == null) {
            preferencesUtils = SharedPreferencesUtils.getInstance(getContext());
        }
        String versionCode = preferencesUtils.loadString("versionCode");
        if (!TextUtils.isEmpty(versionCode) && Integer.parseInt(versionCode) > SystemUtil.getCurrentVersionCode()) {
            if (iv_red != null) {
                iv_red.setVisibility(View.VISIBLE);
            }
        } else {
            if (iv_red != null) {
                iv_red.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void init() {
        imageLoaderUtil = ImageLoaderUtil.getInstance();
    }

    @Override
    public void initGetData() {
    }

    @Override
    protected void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (BroadcastFilters.NOTIFI_GET_ORDER_COUNT.equals(intent.getAction())) {
        }
    }

    public void updateNewMsg(boolean isShow) {
     /*   if (isShow && imgNewMsg != null) {
            imgNewMsg.setVisibility(View.VISIBLE);
        } else if (imgNewMsg != null) {
            imgNewMsg.setVisibility(View.GONE);
        }*/
    }
}
