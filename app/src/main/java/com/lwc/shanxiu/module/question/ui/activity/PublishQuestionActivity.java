package com.lwc.shanxiu.module.question.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.activity.ImageBrowseActivity;
import com.lwc.shanxiu.adapter.MyGridViewPhotoAdpter;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.configs.FileConfig;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.controler.global.GlobalValue;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.interf.SoftKeyBoardListener;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.PhotoToken;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.message.bean.PublishAndRequestBean;
import com.lwc.shanxiu.module.message.ui.PublishAndRequestListActivity;
import com.lwc.shanxiu.module.question.bean.PublishQuestionDetialBean;
import com.lwc.shanxiu.module.question.ui.adapter.QuestionMyGridViewPhotoAdpter;
import com.lwc.shanxiu.utils.DisplayUtil;
import com.lwc.shanxiu.utils.FileUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.PictureUtils;
import com.lwc.shanxiu.utils.QiniuUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.MyGridView;
import com.lwc.shanxiu.widget.CustomDialog;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.zhihu.matisse.Matisse;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import jp.wasabeef.richeditor.RichEditor;

public class PublishQuestionActivity extends BaseActivity {

    private static final int GET_PHOTO_REQUEST_CODE = 1;
    private static final int CROP_REQUEST_CODE = 2;
    @BindView(R.id.editText)
    EditText editTextTitle;
    @BindView(R.id.tv_error_tip)
    TextView tv_error_tip;
    @BindView(R.id.ll_tips)
    LinearLayout ll_tips;
/*    @BindView(R.id.tv_tips_title)
    TextView tv_tips_title;*/
    @BindView(R.id.tv_tips_content)
    TextView tv_tips_content;
    @BindView(R.id.ll_btn)
    LinearLayout ll_btn;
    @BindView(R.id.sl_scroll)
    ScrollView sl_scroll;
    @BindView(R.id.cb_isAnonymous)
    CheckBox cb_isAnonymous;  //是否匿名
    @BindView(R.id.gridview_my)
    MyGridView myGridview;
    @BindView(R.id.et_content)
    EditText et_content;

    private boolean isAnonymous = false;

    private PhotoToken token;
    private ProgressDialog pd;
    private String imgPath1;

    //private int operateType = 1; //1.发表，2，提问 ，3.发表编辑，4.提问编辑
    private String quesionId;  //问题id  用于判断是编辑还是发表提问
    private User user;
    private boolean isBold = false;
    private PopupWindow popWnd;

    private QuestionMyGridViewPhotoAdpter adpter;
    private List<String> urlStrs = new ArrayList();
    private int countPhoto = 9;
    private File dataFile;
    private CheckBox cb_include;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_publish_question;
    }

    @Override
    protected void findViews() {
        Intent intent = getIntent();

        quesionId = intent.getStringExtra("quesionId");

        setTitle("提个问题");
        if(TextUtils.isEmpty(quesionId)){ //发表提问
            editTextTitle.setHint("请输入问题标题(1-15个字)");
            urlStrs.add("");
            adpter = new QuestionMyGridViewPhotoAdpter(this, urlStrs);
            myGridview.setAdapter(adpter);
            myGridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
            myGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (adpter.getItem(position).equals("")) {
                        int count = countPhoto;
                        if (adpter.getCount() > 1) {
                            count = count - adpter.getCount()+1;
                        }
                        showTakePopupWindow1(count);
                    } else {
                        Intent intent = new Intent(PublishQuestionActivity.this, ImageBrowseActivity.class);
                        intent.putExtra("index", position);
                        intent.putStringArrayListExtra("list", (ArrayList)adpter.getLists());
                        startActivity(intent);
                    }
                }
            });
        }else{  //编辑提问
            loadEditData();
        }
        setRightText("提交", "#1481ff", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isVerify()) {//验证数据是否为空

                    final CustomDialog customDialog = new CustomDialog(PublishQuestionActivity.this);
                    customDialog.setTitle("温馨提示");
                    customDialog.setMessage("准确描述问题,可以更快获得答案");
                    customDialog.setButton1Text("继续提交");
                    customDialog.setButton2Text("修改问题");
                    customDialog.setEnterBtn(new CustomDialog.OnClickListener() {
                        @Override
                        public void onClick(CustomDialog dialog, int id, Object object) {
                            dialog.dismiss();
                            urlStrs = adpter.getLists();

                            //判断图片是否已经已经是七牛云上的图片  如果是，直接添加，如果不是保留到上传集合
                            Iterator<String> iterator = urlStrs.iterator();
                            while (iterator.hasNext()){
                                String testUrl = iterator.next();
                                if(!TextUtils.isEmpty(testUrl) && testUrl.contains(".com")){
                                    if (TextUtils.isEmpty(imgPath1)) {
                                        imgPath1 = testUrl;
                                    } else {
                                        imgPath1 = imgPath1+","+testUrl;
                                    }
                                    iterator.remove();
                                }
                            }
                            if (urlStrs != null && urlStrs.size() > 0 && !urlStrs.get(0).equals("")) {
                                uploadPhoto(new File(urlStrs.get(0)));
                            } else {
                                publishData();
                            }
                        }
                    });
                    customDialog.setCancelBtn(new CustomDialog.OnClickListener() {
                        @Override
                        public void onClick(CustomDialog dialog, int id, Object object) {
                            dialog.dismiss();
                        }
                    });
                    customDialog.show();
                }
            }
        });
        showBack();
    }

    private boolean isVerify(){
        String title = editTextTitle.getText().toString().trim();
        if (title.length() < 1 || title.length() > 15) {
            ToastUtil.showToast(PublishQuestionActivity.this, "请填写1-15个字的问题标题！");
            return false;
        }
        String content = et_content.getText().toString().trim();
        if (content == null || content.length() < 1) {
            ToastUtil.showToast(PublishQuestionActivity.this, "请填写您问题的详细描述！");
            return false;
        }
        return true;
    }

    private void showTakePopupWindow1(int count) {
        ToastUtil.showPhotoSelect(this, count);
        getToken(null);
    }

    /**
     * 检查是否有缓存数据
     */
    /*private void checkedCache() {
        String questionTitle = (String) SharedPreferencesUtils.getParam(this, "publishQuestionTitle", "");
        String questionContent = (String) SharedPreferencesUtils.getParam(this, "publishQuestionContent", "");
        if (!TextUtils.isEmpty(questionTitle)) {
            editTextTitle.setText(questionTitle);
        }
        if (!TextUtils.isEmpty(questionContent)) {
            mEditor.setHtml(questionContent);
        }
    }*/

    /**
     * 保存缓存数据
     */
    /*private void saveCache() {
        String questionTitle = editTextTitle.getText().toString();
        String questionContent = mEditor.getHtml();
        if (!TextUtils.isEmpty(questionTitle)) {
            SharedPreferencesUtils.getInstance(this).setParam(this, "publishQuestionTitle", questionTitle);
        }
        if (!TextUtils.isEmpty(questionContent)) {
            SharedPreferencesUtils.getInstance(this).setParam(this, "publishQuestionContent", questionContent);
        }
    }*/

    /**
     * 清除缓存数据
     */
   /* private void clearCache() {
        SharedPreferencesUtils.getInstance(this).setParam(this, "publishQuestionTitle", "");
        SharedPreferencesUtils.getInstance(this).setParam(this, "publishQuestionContent", "");
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //saveCache();
    }

    /**
     * 加载编辑信息
     */
    private void loadEditData() {
        HttpRequestUtils.httpRequest(this, "获取编辑信息", RequestValue.QUESION_DETAILS + "?quesion_id=" + quesionId, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        PublishQuestionDetialBean questionDetialBean = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"), PublishQuestionDetialBean.class);
                        quesionId = questionDetialBean.getQuesionId();
                        editTextTitle.setText(questionDetialBean.getQuesionTitle());
                        //mEditor.setHtml(questionDetialBean.getQuesionDetail());
                        et_content.setText(questionDetialBean.getQuesionDetail());

                        String img = questionDetialBean.getQuesionImg();
                        if (img != null && !img.equals("")) {
                            String[] imgs = img.split(",");
                            for (int i=0;i<imgs.length; i++) {
                                urlStrs.add(imgs[i]);
                            }
                            if(urlStrs.size() < 9){
                                urlStrs.add("");
                            }
                            adpter = new QuestionMyGridViewPhotoAdpter(PublishQuestionActivity.this, urlStrs);
                            //adpter.setIsShowDel(false);
                            myGridview.setAdapter(adpter);
                            myGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    if (adpter.getItem(position).equals("")) {
                                        int count = countPhoto;
                                        if (adpter.getCount() > 1) {
                                            count = count - adpter.getCount()+1;
                                        }
                                        showTakePopupWindow1(count);
                                    } else {
                                        Intent intent = new Intent(PublishQuestionActivity.this, ImageBrowseActivity.class);
                                        intent.putExtra("index", position);
                                        intent.putStringArrayListExtra("list", (ArrayList)adpter.getLists());
                                        startActivity(intent);
                                    }
                                }
                            });
                        }else{
                            urlStrs.add("");
                            adpter = new QuestionMyGridViewPhotoAdpter(PublishQuestionActivity.this, urlStrs);
                            myGridview.setAdapter(adpter);
                            myGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    if (adpter.getItem(position).equals("")) {
                                        int count = countPhoto;
                                        if (adpter.getCount() > 1) {
                                            count = count - adpter.getCount()+1;
                                        }
                                        showTakePopupWindow1(count);
                                    } else {
                                        Intent intent = new Intent(PublishQuestionActivity.this, ImageBrowseActivity.class);
                                        intent.putExtra("index", position);
                                        intent.putStringArrayListExtra("list", (ArrayList)adpter.getLists());
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                        break;
                    default:
                        ToastUtil.showToast(PublishQuestionActivity.this, common.getInfo());
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

        String title = editTextTitle.getText().toString().trim();
        String content = et_content.getText().toString();

        Map<String, String> params = new HashMap<>();

        String requestPath = RequestValue.QUESION_KNOWLEDGEPUBLISH;
        if (!TextUtils.isEmpty(quesionId)) {
            params.put("quesion_id", quesionId);
           // requestPath = RequestValue.QUESION_UPDATEPUBLISH;
        }else{
           // params.put("knowledge_id", quesionId);
          //  requestPath = RequestValue.QUESION_KNOWLEDGEPUBLISH;
        }

        params.put("quesion_title", title);

        params.put("quesion_detail", content);

        if(!TextUtils.isEmpty(imgPath1)){
            params.put("quesion_img", imgPath1);
        }

        if(isAnonymous){
            params.put("is_null","1");
        }else{
            params.put("is_null","0");
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
                        ToastUtil.showToast(PublishQuestionActivity.this, "发布成功!");
                        finish();
                        SharedPreferencesUtils.setParam(PublishQuestionActivity.this,"isRefresh",true);
                        IntentUtil.gotoActivity(PublishQuestionActivity.this,MyRequestActivity.class);
                        break;
                    default:
                        ToastUtil.showToast(PublishQuestionActivity.this, common.getInfo());
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

        pd = new ProgressDialog(this);
        pd.setMessage("正在上传图片...");
        pd.setCancelable(false);

        View contentView = LayoutInflater.from(PublishQuestionActivity.this).inflate(R.layout.include_btn, null);
        popWnd = new PopupWindow(contentView,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popWnd.setTouchable(true);
        popWnd.setOutsideTouchable(false);
        popWnd.setFocusable(false);
        popWnd.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);

        cb_include = contentView.findViewById(R.id.cb_isAnonymous);
        ImageView ivBold = contentView.findViewById(R.id.ivBold);
        ImageView ivItalic = contentView.findViewById(R.id.ivItalic);
        ImageView ivPicture = contentView.findViewById(R.id.ivPicture);

        ivBold.setVisibility(View.GONE);
        ivItalic.setVisibility(View.GONE);
        ivPicture.setVisibility(View.GONE);


        cb_include.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cb_isAnonymous.setChecked(true);
                }else{
                    cb_isAnonymous.setChecked(false);
                }
            }
        });
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {
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

                if(DisplayUtil.isAllScreenDevice(PublishQuestionActivity.this)){
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
                    cb_include.setChecked(true);
                    isAnonymous = true;
                } else {
                    cb_isAnonymous.setTextColor(Color.parseColor("#cccccc"));
                    cb_include.setChecked(false);
                    isAnonymous = false;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GlobalValue.PHOTO_REQUESTCODE:
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    if (mSelected != null && mSelected.size() > 0) {
                        urlStrs = adpter.getLists();
                        urlStrs.remove(urlStrs.size()-1);
                        for(int i=0; i<mSelected.size(); i++) {
                            Uri uri = mSelected.get(i);
                            urlStrs.add(PictureUtils.getPath(PublishQuestionActivity.this, uri));
                        }
                    }
                    if (urlStrs.size() < countPhoto) {
                        urlStrs.add("");
                    }
                    adpter.setLists(urlStrs);
                    adpter.notifyDataSetChanged();
                    break;
                case GlobalValue.CAMERA_REQUESTCODE:
                    //上传图片
                    try {
                        Bitmap bm = (Bitmap) data.getExtras().get("data");
                        if (bm == null) {
                            return;
                        }
                        File f = FileUtil.saveMyBitmap(bm);
                        File file = new Compressor.Builder(PublishQuestionActivity.this).setMaxHeight(1080).setMaxWidth(1920)
                                .setQuality(85).setCompressFormat(Bitmap.CompressFormat.PNG).setDestinationDirectoryPath(FileConfig.PATH_IMAGES)
                                .build().compressToFile(f);
                        if (file != null) {
                            dataFile = file;
                        } else {
                            dataFile = f;
                        }
                    } catch (Exception e) {
                    }
                    if (dataFile != null) {
                        if (urlStrs != null && urlStrs.get(urlStrs.size()-1).equals("")) {
                            urlStrs.remove(urlStrs.size()-1);
                        }
                        urlStrs.add(dataFile.getAbsolutePath());
                        if (urlStrs.size() < countPhoto) {
                            urlStrs.add("");
                        }
                        adpter.setLists(urlStrs);
                        adpter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showToast(PublishQuestionActivity.this, "选择图片失败");
                    }
                    break;
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
                                publishData();
                                pd.dismiss();
                            }
                            LLog.i("联网  图片地址" + url);
                        } else {
                            pd.dismiss();
                            ToastUtil.showLongToast(PublishQuestionActivity.this, "图片上传失败");
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
