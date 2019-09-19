package com.lwc.shanxiu.module.message.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.interf.SoftKeyBoardListener;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.PhotoToken;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.message.bean.PublishAndRequestBean;
import com.lwc.shanxiu.utils.DisplayUtil;
import com.lwc.shanxiu.utils.FileUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.QiniuUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.richeditor.RichEditor;

public class PublishActivity extends BaseActivity {

    private static final int GET_PHOTO_REQUEST_CODE = 1;
    private static final int CROP_REQUEST_CODE = 2;
    @BindView(R.id.ivBold)
    ImageView ivBold;
    @BindView(R.id.ivItalic)
    ImageView ivItalic;
    @BindView(R.id.ivPicture)
    ImageView ivPicture;
    @BindView(R.id.editor)
    RichEditor mEditor;
    @BindView(R.id.editText)
    EditText editTextTitle;
    @BindView(R.id.tv_error_tip)
    TextView tv_error_tip;
    @BindView(R.id.ll_tips)
    LinearLayout ll_tips;
    @BindView(R.id.tv_tips_title)
    TextView tv_tips_title;
    @BindView(R.id.tv_tips_content)
    TextView tv_tips_content;
    @BindView(R.id.ll_btn)
    LinearLayout ll_btn;
    @BindView(R.id.sl_scroll)
    ScrollView sl_scroll;
    @BindView(R.id.cb_isAnonymous)
    CheckBox cb_isAnonymous;  //是否匿名

    private boolean isAnonymous = false;

    private PhotoToken token;
    private ProgressDialog pd;
    private String imgPath1;
    private List<String> urlStrs = new ArrayList();
    private int operateType = 1; //1.发表，2，提问 ，3.发表编辑，4.提问编辑
    private User user;
    private String knowledgeId;
    private boolean isBold = false;
    private PopupWindow popWnd;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_publish;
    }

    @Override
    protected void findViews() {
        Intent intent = getIntent();
        operateType = intent.getIntExtra("operateType", 1);

        String rightBtnTxt = "发表";
        switch (operateType) {
            case 1:
                setTitle("撰写文章");
                rightBtnTxt = "发表";
                editTextTitle.setHint("请输入问题标题(5-49个字)");
                mEditor.setPlaceholder("请输入文章内容...");
                mEditor.setTextColor(R.color.red_3a);
                mEditor.setEditorFontColor(R.color.blue);
                checkedCache();
                break;
            case 3:
                setTitle("撰写文章(编辑)");
                rightBtnTxt = "发表";
                editTextTitle.setHint("请输入问题标题(5-49个字)");
                mEditor.setPlaceholder("请输入文章内容...");
                knowledgeId = intent.getStringExtra("knowledgeId");
                loadEditData();
                break;
            case 2:
                setTitle("提个问题");
                rightBtnTxt = "提交";
                editTextTitle.setHint("请输入问题标题(5-49个字)");
                mEditor.setPlaceholder("请详细描述问题(选填)...");
                tv_tips_title.setVisibility(View.GONE);
                tv_tips_content.setText("·保持文字简练，表达清晰问题的关键点\n·可添加图片方便解答");
                checkedCache();
                break;
            case 4:
                setTitle("提个问题(编辑)");
                rightBtnTxt = "提交";
                editTextTitle.setHint("请输入问题标题(5-49个字)");
                mEditor.setPlaceholder("请详细描述问题(选填)...");
                knowledgeId = intent.getStringExtra("knowledgeId");
                loadEditData();
                break;
        }

        setRightText(rightBtnTxt, "#1481ff", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publishData();
            }
        });
        showBack();
    }

    /**
     * 检查是否有缓存数据
     */
    private void checkedCache() {
        switch (operateType) {
            case 1://文章
                String articleTitle = (String) SharedPreferencesUtils.getParam(this, "articleTitle", "");
                String articleContent = (String) SharedPreferencesUtils.getParam(this, "articleContent", "");
                if (!TextUtils.isEmpty(articleTitle)) {
                    editTextTitle.setText(articleTitle);
                }
                if (!TextUtils.isEmpty(articleContent)) {
                    mEditor.setHtml(articleContent);
                }
                break;
            case 2://提问
                String questionTitle = (String) SharedPreferencesUtils.getParam(this, "questionTitle", "");
                String questionContent = (String) SharedPreferencesUtils.getParam(this, "questionContent", "");
                if (!TextUtils.isEmpty(questionTitle)) {
                    editTextTitle.setText(questionTitle);
                }
                if (!TextUtils.isEmpty(questionContent)) {
                    mEditor.setHtml(questionContent);
                }
                break;
        }
    }

    /**
     * 保存缓存数据
     */
    private void saveCache() {
        switch (operateType) {
            case 1://文章
                String articleTitle = editTextTitle.getText().toString();
                String articleContent = mEditor.getHtml();
                if (!TextUtils.isEmpty(articleTitle)) {
                    SharedPreferencesUtils.getInstance(this).setParam(this, "articleTitle", articleTitle);
                }
                if (!TextUtils.isEmpty(articleContent)) {
                    SharedPreferencesUtils.getInstance(this).setParam(this, "articleContent", articleContent);
                }
                break;
            case 2://提问
                String questionTitle = editTextTitle.getText().toString();
                String questionContent = mEditor.getHtml();

                if (!TextUtils.isEmpty(questionTitle)) {
                    SharedPreferencesUtils.getInstance(this).setParam(this, "questionTitle", questionTitle);
                }
                if (!TextUtils.isEmpty(questionContent)) {
                    SharedPreferencesUtils.getInstance(this).setParam(this, "questionContent", questionContent);
                }
                break;
        }

    }

    /**
     * 保存缓存数据
     */
    private void clearCache() {
        switch (operateType) {
            case 1://文章
                SharedPreferencesUtils.getInstance(this).setParam(this, "articleTitle", "");
                SharedPreferencesUtils.getInstance(this).setParam(this, "articleContent", "");
                break;
            case 2://提问
                SharedPreferencesUtils.getInstance(this).setParam(this, "questionTitle", "");
                SharedPreferencesUtils.getInstance(this).setParam(this, "questionContent", "");
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveCache();
    }

    private void loadEditData() {
        HttpRequestUtils.httpRequest(this, "获取编辑信息", RequestValue.GET_KNOWLEDGE_DETAILS + "/" + knowledgeId, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        PublishAndRequestBean knowledgeBean = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"), PublishAndRequestBean.class);
                        knowledgeId = knowledgeBean.getKnowledgeId();
                        editTextTitle.setText(knowledgeBean.getKnowledgeTitle());
                        switch (operateType) {
                            case 1:
                                mEditor.setHtml(knowledgeBean.getKnowledgeDetails());
                                break;
                            case 3:
                                mEditor.setHtml(knowledgeBean.getKnowledgeDetails());
                                if (knowledgeBean.getStatus() == 3) {
                                    ll_tips.setVisibility(View.GONE);
                                    tv_error_tip.setVisibility(View.VISIBLE);
                                    tv_error_tip.setText(TextUtils.isEmpty(knowledgeBean.getReason()) ? "暂无" : knowledgeBean.getReason());
                                }
                                break;
                            case 2:
                                mEditor.setHtml(knowledgeBean.getKnowledgeDetails());
                                break;
                            case 4:
                                mEditor.setHtml(knowledgeBean.getKnowledgeDetails());
                                if (knowledgeBean.getStatus() == 3) {
                                    ll_tips.setVisibility(View.GONE);
                                    tv_error_tip.setVisibility(View.VISIBLE);
                                    tv_error_tip.setText(TextUtils.isEmpty(knowledgeBean.getReason()) ? "暂无" : knowledgeBean.getReason());
                                }
                                break;
                        }
                        break;
                    default:
                        ToastUtil.showToast(PublishActivity.this, common.getInfo());
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
     * 提交数据
     */
    private void publishData() {

        final String title = editTextTitle.getText().toString().trim();
        String content = mEditor.getHtml();

        Map<String, String> params = new HashMap<>();

        String requestPath = RequestValue.GET_KNOWLEDGE_KNOWLEDGEPUBLISH;
        if (!TextUtils.isEmpty(knowledgeId)) {
            params.put("knowledge_id", knowledgeId);
            requestPath = RequestValue.GET_KNOWLEDGE_UPDATEPUBLISH;
        }

        int titleLength = title.length();
        if (titleLength < 5 || titleLength > 49) {
            ToastUtil.showToast(PublishActivity.this, "请输入5-49个字的标题");
            return;
        }
        params.put("knowledge_title", title);

        switch (operateType) {
            case 1:
            case 3: //文章
                if (content == null || content.length() < 1) {
                    ToastUtil.showToast(PublishActivity.this, "请填写文章内容");
                    return;
                }
                params.put("type", "1");
                params.put("knowledge_details", content);
                break;
            case 2:
            case 4: //提问
                params.put("type", "2");
                if (content != null && content.length() > 0) {
                    params.put("knowledge_details", content);
                }
                break;
        }

        if (!isAnonymous) {
            user = SharedPreferencesUtils.getInstance(PublishActivity.this).loadObjectData(User.class);
            params.put("maintenance_name", user.getMaintenanceName());
        }

        pd.setMessage("提交数据中,请稍后...");
        pd.show();
        txtRight.setEnabled(false);
        HttpRequestUtils.httpRequest(this, "发表问题和文章", requestPath, params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                txtRight.setEnabled(true);
                pd.dismiss();
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        ToastUtil.showToast(PublishActivity.this, "发布成功!");

                        editTextTitle.setText("");
                        mEditor.setHtml("");
                        clearCache();
                        finish();
                        Bundle bundle = new Bundle();
                        if (operateType == 1 || operateType == 3) {
                            bundle.putInt("searchType", 1);
                        } else {
                            bundle.putInt("searchType", 2);
                        }
                        IntentUtil.gotoActivity(PublishActivity.this, PublishAndRequestListActivity.class, bundle);
                        break;
                    default:
                        ToastUtil.showToast(PublishActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    @Override
    protected void init() {
        mEditor.setEditorFontSize(15);
        mEditor.setEditorFontColor(Color.BLACK);
        mEditor.setPadding(10, 10, 10, 10);

        pd = new ProgressDialog(this);
        pd.setMessage("正在上传图片...");
        pd.setCancelable(false);

        View contentView = LayoutInflater.from(PublishActivity.this).inflate(R.layout.include_btn, null);
        popWnd = new PopupWindow(contentView,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popWnd.setTouchable(true);
        popWnd.setOutsideTouchable(false);
        popWnd.setFocusable(false);
        popWnd.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);

        final ImageView boldBtn = (ImageView) contentView.findViewById(R.id.ivBold);
        ImageView italicBtn = (ImageView) contentView.findViewById(R.id.ivItalic);
        ImageView pictureBtn = (ImageView) contentView.findViewById(R.id.ivPicture);
        final CheckBox isAnonymousBtn = (CheckBox) contentView.findViewById(R.id.cb_isAnonymous);

        boldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditor.setBold();
                if(isBold){
                    boldBtn.setImageResource(R.drawable.ic_bold);
                    isBold = false;
                }else{
                    boldBtn.setImageResource(R.drawable.ic_bold_select);
                    isBold = true;
                }
            }
        });

        italicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditor.setNumbers();
            }
        });

        pictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAddImage();
            }
        });

        isAnonymousBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    isAnonymousBtn.setTextColor(Color.parseColor("#000000"));
                    isAnonymous = true;
                }else{
                    isAnonymousBtn.setTextColor(Color.parseColor("#cccccc"));
                    isAnonymous = false;
                }
            }
        });
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {
        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAddImage();
            }
        });

        editTextTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    InputMethodManager imm = (InputMethodManager) view.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        });
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {

                if (editTextTitle.isFocused()) {
                    return;
                }

                ll_btn.setVisibility(View.GONE);
                Display defaultDisplay = getWindowManager().getDefaultDisplay();
                Point point = new Point();
                defaultDisplay.getSize(point);
                int y = point.y;
                //ToastUtil.showToast(PublishActivity.this,"屏幕高度:"+y+"键盘高度:"+height);

                if(DisplayUtil.isAllScreenDevice(PublishActivity.this)){
                    popWnd.showAtLocation(sl_scroll, Gravity.BOTTOM, 0,height+80);
                }else{
                    popWnd.showAtLocation(sl_scroll, Gravity.BOTTOM, 0,height);
                }

                // popWnd.showAsDropDown(sl_scroll,0,-(y-height));
            }

            @Override
            public void keyBoardHide(int height) {
                if (ll_btn.getVisibility() == View.GONE) {
                    ll_btn.setVisibility(View.VISIBLE);
                }
                popWnd.dismiss();
            }
        });

        cb_isAnonymous.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cb_isAnonymous.setTextColor(Color.parseColor("#000000"));
                    isAnonymous = true;
                } else {
                    cb_isAnonymous.setTextColor(Color.parseColor("#cccccc"));
                    isAnonymous = false;
                }
            }
        });
    }

    @OnClick({R.id.ivBold,R.id.ivItalic})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.ivBold:
                mEditor.setBold();
                if(isBold){
                    ivBold.setImageResource(R.drawable.ic_bold);
                    isBold = false;
                }else{
                    ivBold.setImageResource(R.drawable.ic_bold_select);
                    isBold = true;
                }
                break;
            case R.id.ivItalic:
                mEditor.setNumbers();
                break;
        }
    }
    /**
     * 处理插入图片
     */
    private void handleAddImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,GET_PHOTO_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            if (requestCode == GET_PHOTO_REQUEST_CODE) {
                Uri uri = data.getData();
                String filePath = FileUtil.getFileRealPath(this, uri);
                File file = getimage(filePath);
                urlStrs.add(file.getPath());
                uploadPhoto(file);

                //routeToCrop(uri);
            }else if(requestCode == CROP_REQUEST_CODE){
                Uri uri = data.getData();
                String filePath = FileUtil.getFileRealPath(this, uri);

                File file = getimage(filePath);

                urlStrs.add(file.getPath());
                uploadPhoto(file);
            }
        }
    }

    private void uploadPhoto(final File path) {
        if (token == null) {
            getToken(path);
            return;
        }
        pd.show();
        final String key = Utils.getImgName();
        UploadManager um = QiniuUtil.getUploadManager();
        um.put(path, key, token.getSecurityToken(),
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
                            if (TextUtils.isEmpty(imgPath1)) {
                                imgPath1 = url;
                            } else {
                                imgPath1 = imgPath1+","+url;
                            }
                            urlStrs.remove(path.getPath());
//                            Utils.deleteFile(path.getPath(), CannotMaintainActivity.this);
                            if (urlStrs.size() > 0 && !TextUtils.isEmpty(urlStrs.get(0))){
                                uploadPhoto(new File(urlStrs.get(0)));
                            } else {
                                mEditor.insertImage(url,
                                        url);
                                pd.dismiss();
                            }
                            LLog.i("联网  图片地址" + url);
                        } else {
                            pd.dismiss();
                            ToastUtil.showLongToast(PublishActivity.this, "图片上传失败");
                        }
                    }
                }, null);
    }

    /**
     * 质量压缩方法
     * @param image
     * @return
     */
    public static File compressImage(Bitmap image,String path) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        while (baos.toByteArray().length / 1024 > 1024) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片

        File file=new File(path);//将要保存图片的路径
        try{
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 图片按比例大小压缩方法
     * @param srcPath （根据路径获取图片并压缩）
     * @return
     */
    public static File getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        newOpts.inJustDecodeBounds = false;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 400f;// 这里设置高度为800f
        float ww = 240f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap,srcPath);// 压缩好比例大小后再进行质量压缩
    }


    private void getToken(final File photoPath) {
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
                            uploadPhoto(photoPath);
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
}
