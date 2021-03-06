package com.lwc.shanxiu.module.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.activity.CodeActivity;
import com.lwc.shanxiu.activity.WorkPositionActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.configs.FileConfig;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.controler.global.GlobalValue;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.PhotoToken;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.utils.GetImagePath;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.PictureUtils;
import com.lwc.shanxiu.utils.QiniuUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.SystemInvokeUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.widget.CircleImageView;
import com.lwc.shanxiu.widget.PicturePopupWindow;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户个人信息页面
 *
 * @author cc
 * @Description TODO
 * @date 2015年11月9日
 * @Copyright: lwc
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.imgHeadIcon)
    CircleImageView imgHeadIcon;
    @BindView(R.id.txtId)
    TextView txtId;
    @BindView(R.id.txtAccounts)
    TextView txtAccounts;
    @BindView(R.id.txtSex)
    TextView txtSex;
    @BindView(R.id.txtIde)
    TextView txtIde;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtPhone)
    TextView txtPhone;
    @BindView(R.id.txtCompany)
    TextView txtCompany;
    @BindView(R.id.txtSign)
    TextView txtSign;
    @BindView(R.id.txtAddress)
    TextView txtAddress;
    @BindView(R.id.rLayoutSex)
    RelativeLayout rLayoutSex;
    @BindView(R.id.rLayoutName)
    RelativeLayout rLayoutName;
    @BindView(R.id.rLayoutPhone)
    RelativeLayout rLayoutPhone;
    @BindView(R.id.rLayoutAddress)
    RelativeLayout rLayoutAddress;
    @BindView(R.id.rLayoutSign)
    RelativeLayout rLayoutSign;
    @BindView(R.id.rLayoutChangePwd)
    RelativeLayout rLayoutChangePwd;
    @BindView(R.id.rl_position)
    RelativeLayout rl_position;
    /**
     * 拍照弹出框
     **/
    private PicturePopupWindow picturePopupWindow;
    private final int REQUEST_CODE_UPDATE = 101;
    private SharedPreferencesUtils preferencesUtils;
    private User user;
   private File iconFile;
    private ImageLoaderUtil imageLoaderUtil;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private ProgressDialog pd;
    private PhotoToken token;
    private String isSecrecy;
    private Uri uritempFile;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_user_info;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        setTitle("个人资料");
        showBack();
        pd = new ProgressDialog(this);
        pd.setMessage("正在上传图片...");
        pd.setCancelable(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfor();
    }

    @Override
    public void init() {
    }

    @Override
    protected void initGetData() {
        preferencesUtils = SharedPreferencesUtils.getInstance(UserInfoActivity.this);
        imageLoaderUtil = ImageLoaderUtil.getInstance();
        user = preferencesUtils.loadObjectData(User.class);
        if (user == null) {
            IntentUtil.gotoActivityAndFinish(UserInfoActivity.this, LoginOrRegistActivity.class);
        }
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void widgetListener() {
    }

    /**
     * 初始化性别提示框
     */
    private void showSexPopupWindos() {
        picturePopupWindow = new PicturePopupWindow(UserInfoActivity.this, new PicturePopupWindow.CallBack() {
            @Override
            public void twoClick() {
                upUserInfor("0", null);
            }

            @Override
            public void oneClick() {
                upUserInfor("1", null);
            }

            @Override
            public void cancelCallback() {

            }

            @Override
            public void threeClick() {
            }

            @Override
            public void fourClick() {
            }
        }, "男", "女", null, null);
        picturePopupWindow.showWindow();
    }

    /**
     * 初始化拍照提示框
     */
    private void showTakePopupWindow() {
        ToastUtil.showPhotoSelect(this);
        getToken(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GlobalValue.PHOTO_REQUESTCODE:
                  /*  iconFile = PictureUtils.getFile(UserInfoActivity.this, data);
                    Uri myUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileProvider", iconFile);
                    SystemInvokeUtils.startImageZoom(this,myUri, uritempFile, GlobalValue.ACTIVITY_MODIFY_PHOTO_REQUESTCODE);*/

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果大于等于7.0使用FileProvider
                        File path_pre = new File(GetImagePath.getPath(UserInfoActivity.this, data.getData()));
                     //   File newFile = new File(ToastUtil.path, ToastUtil.date);
                       // Toast.makeText(UserInfoActivity.this, getString(R.string.msg_loading), Toast.LENGTH_SHORT).show();
                        try {
                           // Defined.copyFile(path_pre, newFile);
                            Uri dataUri = FileProvider.getUriForFile(UserInfoActivity.this, getApplicationContext().getPackageName() +".fileProvider", path_pre);
                            SystemInvokeUtils.startImageZoom(this,dataUri, uritempFile, GlobalValue.ACTIVITY_MODIFY_PHOTO_REQUESTCODE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        File path_pre = new File(GetImagePath.getPath(UserInfoActivity.this, data.getData()));
                      //  File newFile = new File(ToastUtil.path, ToastUtil.date);
                     //   Toast.makeText(PersonalSetting.this, getString(R.string.msg_loading), Toast.LENGTH_SHORT).show();
                        try {
                          //  Defined.copyFile(new File(path_pre), newFile);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        SystemInvokeUtils.startImageZoom(this,Uri.fromFile(path_pre), uritempFile, GlobalValue.ACTIVITY_MODIFY_PHOTO_REQUESTCODE);
                    }


                    break;
                case GlobalValue.CAMERA_REQUESTCODE:
                    File file = new File(ToastUtil.path,ToastUtil.date);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri inputUri = FileProvider.getUriForFile(UserInfoActivity.this, getApplicationContext().getPackageName() +".fileProvider", file);//通过FileProvider创建一个content类型的Uri
                        SystemInvokeUtils.startImageZoom(this, inputUri, uritempFile, GlobalValue.ACTIVITY_MODIFY_PHOTO_REQUESTCODE);
                    } else {
                        SystemInvokeUtils.startImageZoom(this, Uri.fromFile(file), uritempFile, GlobalValue.ACTIVITY_MODIFY_PHOTO_REQUESTCODE);
                    }

                    break;
                case GlobalValue.ACTIVITY_MODIFY_PHOTO_REQUESTCODE:
                    //获取到裁剪后的图像
                    Bitmap bm = null;//extras.getParcelable("data");
                    try {
                        bm = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    iconFile = PictureUtils.saveFile(bm, FileConfig.PATH_IMAGES, Utils.getImgName());
                            LLog.i("压缩前" + iconFile);
                            upIcon(iconFile.getAbsolutePath());
                    break;
                case REQUEST_CODE_UPDATE:
                    getUserInfor();
                    break;
            }
        }
    }

    /**
     * 更新用户信息
     *
     * @param sex
     * @param picture
     */
    private void upUserInfor(String sex, String picture) {
        if (Utils.isFastClick(1000)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        if (sex != null && !sex.equals(""))
            map.put("sex", sex);
        if (picture != null && !picture.equals(""))
            map.put("headImage", picture);
        HttpRequestUtils.httpRequest(this, "updateUserInfo", RequestValue.UP_USER_INFOR, map, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        getUserInfor();
                        break;
                    default:
                        ToastUtil.showLongToast(UserInfoActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
                ToastUtil.showLongToast(UserInfoActivity.this, msg);
            }
        });
    }

    /**
     * 获取个人信息
     */
    private void getUserInfor() {
        HttpRequestUtils.httpRequest(this, "getUserInfo", RequestValue.USER_INFO, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                LLog.iNetSucceed(" getUserInfo " + response);
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        user = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"), User.class);
                        if ((user.getUserId() == null || user.getUserId().equals("")) && user.getMaintenanceId() != null) {
                            user.setUserId(user.getMaintenanceId());
                        }
                        setUserInfor(user);
                        preferencesUtils.saveObjectData(user);
                        break;
                    default:
                        setUserInfor(null);
                        ToastUtil.showLongToast(UserInfoActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
                ToastUtil.showLongToast(UserInfoActivity.this, msg);
            }
        });
    }

    private void getToken(final String photoPath) {
        if (token != null) {
            return;
        }
        HttpRequestUtils.httpRequest(this, "获取上传图片token", RequestValue.GET_PICTURE, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                LLog.iNetSucceed(" getPhotoToken " + response);
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        token = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"), PhotoToken.class);
                        if (photoPath != null) {
                            upIcon(photoPath);
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    /**
     * 上传头像
     */
    private void upIcon(final String filePath) {
        if (token == null) {
            getToken(filePath);
            return;
        }
        pd.show();
        final String key = Utils.getImgName();
        UploadManager um = QiniuUtil.getUploadManager();
        um.put(filePath, key, token.getSecurityToken(),
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info,
                                         JSONObject response) {
                        if (info.isOK()) {
                            String url = "";
                            if(!TextUtils.isEmpty(token.getDomain())){
                                url = token.getDomain()+ key;
                            }else{
                                url = ServerConfig.RUL_IMG + key;
                            }

                            user.setMaintenanceHeadImage(url);
                            preferencesUtils.saveObjectData(user);
                            upUserInfor(null, url);
                            Log.d("联网 url", url);
                            pd.dismiss();
//                            Utils.deleteFile(filePath, UserInfoActivity.this);
                        } else {
                            pd.dismiss();
                            ToastUtil.showLongToast(UserInfoActivity.this, "图片上传失败");
                        }
                    }
                }, null);

    }

    private void setUserInfor(User user) {
        if (user == null) {
            user = preferencesUtils.loadObjectData(User.class);
        }
        isSecrecy = user.getIsSecrecy();
        txtId.setText(user.getUserId());
        txtAccounts.setText(user.getUserPhone());
        //设置性别
        if (1 == user.getMaintenanceSex()) {
            txtSex.setText("男");
        } else if (0 == user.getMaintenanceSex()) {
            txtSex.setText("女");
        } else {
            txtSex.setText("暂无");
        }

        //设置身份
        if (isSecrecy.equals("1")) {
            txtIde.setText("认证工程师");
            rl_position.setVisibility(View.VISIBLE);
        } else {
            txtIde.setText("工程师");
            rl_position.setVisibility(View.GONE);
        }

        if (user.getMaintenanceName() != null && !TextUtils.isEmpty(user.getMaintenanceName())) {
            txtName.setText(user.getMaintenanceName());
        } else {
            txtName.setText("暂无");
        }

        if (user.getUserPhone() != null && !TextUtils.isEmpty(user.getUserPhone())) {
            txtPhone.setText(user.getUserPhone());
        } else {
            txtPhone.setText("暂无");
        }

        String comanyName = "";
        if (!TextUtils.isEmpty(user.getCompanyName())) {
            comanyName = user.getCompanyName();
        } else if (!TextUtils.isEmpty(user.getParentCompanyName())) {
            comanyName = user.getParentCompanyName();
        } else {
            comanyName = "暂无";
        }
        if(comanyName.length() > 12){
            comanyName = comanyName.substring(0,11) + "...";
        }
        txtCompany.setText(comanyName);

        String maintenanceSignature = "";
        if (user.getMaintenanceSignature() != null && !TextUtils.isEmpty(user.getMaintenanceSignature())) {
            maintenanceSignature = user.getMaintenanceSignature();
        } else {
            maintenanceSignature  = "暂无";
        }
        if(maintenanceSignature.length() > 14){
            maintenanceSignature = maintenanceSignature.substring(0,13) + "...";
        }
        txtSign.setText(maintenanceSignature);

        if (!TextUtils.isEmpty(user.getMaintenanceHeadImage())) {
            imageLoaderUtil.displayFromNet(UserInfoActivity.this, user.getMaintenanceHeadImage(), imgHeadIcon);
        } else {
            imageLoaderUtil.displayFromLocal(UserInfoActivity.this, imgHeadIcon, R.drawable.default_portrait_100);
        }
    }

    @OnClick({R.id.rl_code, R.id.rLayoutSex, R.id.rLayoutName, R.id.rLayoutPhone, R.id.rLayoutAddress, R.id.rLayoutSign, R.id.rLayoutChangePwd, R.id.rl_head,R.id.rl_position})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_code:
                IntentUtil.gotoActivity(UserInfoActivity.this, CodeActivity.class);
                break;
            case R.id.rLayoutSex:
                showSexPopupWindos();
                break;
            case R.id.rLayoutName:
                String name = txtName.getText().toString().trim();
                if (name != null && !name.equals("") && !name.equals("暂无")) {
                    return;
                }
                UpdateUserInfoActivity.type = 4;
                IntentUtil.gotoActivityForResult(UserInfoActivity.this, UpdateUserInfoActivity.class, REQUEST_CODE_UPDATE);
                break;
            case R.id.rLayoutPhone:
                String phone = txtPhone.getText().toString().trim();
                if (phone != null && !phone.equals("") && !phone.equals("暂无")) {
                    return;
                }
                UpdateUserInfoActivity.type = 1;
                IntentUtil.gotoActivityForResult(UserInfoActivity.this, UpdateUserInfoActivity.class, REQUEST_CODE_UPDATE);
                break;
            case R.id.rLayoutAddress:
                UpdateUserInfoActivity.type = 2;
                IntentUtil.gotoActivityForResult(UserInfoActivity.this, UpdateUserInfoActivity.class, REQUEST_CODE_UPDATE);
                break;
            case R.id.rLayoutSign:
              /*  UpdateUserInfoActivity.type = 3;
                IntentUtil.gotoActivityForResult(UserInfoActivity.this, UpdateUserInfoActivity.class, REQUEST_CODE_UPDATE);
*/
                Bundle bundle3 = new Bundle();
                bundle3.putInt("type", 3);
                if(!TextUtils.isEmpty(user.getMaintenanceSignature())){
                    bundle3.putString("signature", user.getMaintenanceSignature());
                }
                IntentUtil.gotoActivityForResult(UserInfoActivity.this, UpdateSignActivity.class, bundle3, REQUEST_CODE_UPDATE);
                break;
            case R.id.rLayoutChangePwd:
                /*UpdateUserInfoActivity.type = 0;
                IntentUtil.gotoActivityForResult(UserInfoActivity.this, UpdateUserInfoActivity.class, REQUEST_CODE_UPDATE);*/
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type", 0);
                IntentUtil.gotoActivityForResult(UserInfoActivity.this, UpdatePassWordActivity.class, bundle1, REQUEST_CODE_UPDATE);
                break;
            case R.id.rl_head:
                if (isSecrecy.equals("1") || isSecrecy.equals("2")) {
                    ToastUtil.showToast(UserInfoActivity.this,"认证工程师请登录后台提交申请修改头像");
                    break;
                }
                showTakePopupWindow();
                break;
            case R.id.rl_position:
                IntentUtil.gotoActivity(UserInfoActivity.this, WorkPositionActivity.class);
                break;
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("UserInfo Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
