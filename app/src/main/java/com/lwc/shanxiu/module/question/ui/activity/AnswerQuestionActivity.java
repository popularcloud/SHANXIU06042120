package com.lwc.shanxiu.module.question.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.PhotoToken;
import com.lwc.shanxiu.module.order.ui.activity.CannotMaintainActivity;
import com.lwc.shanxiu.module.question.bean.PublishQuestionDetialBean;
import com.lwc.shanxiu.module.question.ui.adapter.QuestionBigMyGridViewPhotoAdpter;
import com.lwc.shanxiu.module.question.ui.adapter.QuestionMyGridViewPhotoAdpter;
import com.lwc.shanxiu.utils.FileUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.PictureUtils;
import com.lwc.shanxiu.utils.QiniuUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.TimeUtil;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.MyGridView;
import com.lwc.shanxiu.widget.MyWebView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.zhihu.matisse.Matisse;

import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;

public class AnswerQuestionActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.gridview_show)
    MyGridView gridview_show;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_view_count)
    TextView tv_view_count;
    @BindView(R.id.tv_comment_count)
    TextView tv_comment_count;
    @BindView(R.id.tv_create_time)
    TextView tv_create_time;
    @BindView(R.id.tv_show_all)
    TextView tv_show_all;
    @BindView(R.id.cb_isAnonymous)
    CheckBox cb_isAnonymous;  //是否匿名

    @BindView(R.id.gridview_my)
    MyGridView myGridview;
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.btn_back)
    TextView btn_back;
    @BindView(R.id.rl_question_details)
    RelativeLayout rl_question_details;

    private String quesionId;
    private QuestionMyGridViewPhotoAdpter adpter;
    private QuestionBigMyGridViewPhotoAdpter adpterShow;
    private List<String> urlStrs = new ArrayList();
    private List<String> urlStrsShow = new ArrayList();
    private int countPhoto = 9;
    private PhotoToken token;
    private ProgressDialog pd;
    private String imgPath1;
    private File dataFile;

    private boolean isAnonymous = false;

    private boolean questionIsShow = false;

    private int ll_height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        return R.layout.activity_answer_question;
    }

    @Override
    protected void findViews() {
        setTitle("回答");
        //取消按钮
        btn_back.setVisibility(View.VISIBLE);
        setRightText("发布","#1481ff", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVerify()) {
                    urlStrs = adpter.getLists();
                    if (urlStrs != null && urlStrs.size() > 0 && !urlStrs.get(0).equals("")) {
                        uploadPhoto(new File(urlStrs.get(0)));
                    } else {
                        publishData();
                    }
                }
            }
        });
        et_content.requestFocus();
        et_content.findFocus();

        pd = new ProgressDialog(this);
        pd.setMessage("正在上传图片...");
        pd.setCancelable(false);

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
                    Intent intent = new Intent(AnswerQuestionActivity.this, ImageBrowseActivity.class);
                    intent.putExtra("index", position);
                    intent.putStringArrayListExtra("list", (ArrayList)adpter.getLists());
                    startActivity(intent);
                }
            }
        });


        adpterShow = new QuestionBigMyGridViewPhotoAdpter(this, urlStrsShow);
        adpterShow.setIsShowDel(false);
        gridview_show.setAdapter(adpterShow);
        gridview_show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AnswerQuestionActivity.this, ImageBrowseActivity.class);
                intent.putExtra("index", position);
                intent.putStringArrayListExtra("list", (ArrayList)adpterShow.getLists());
                startActivity(intent);
            }
        });
    }

    private void showTakePopupWindow1(int count) {
        ToastUtil.showPhotoSelect(this, count);
        getToken(null);
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
                            ToastUtil.showLongToast(AnswerQuestionActivity.this, "图片上传失败");
                        }
                    }
                }, null);
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

    @Override
    protected void init() {
        quesionId =  getIntent().getStringExtra("quesionId");
        loadData();
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {
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

    private void loadData(){
        HttpRequestUtils.httpRequest(this, "获取编辑信息", RequestValue.QUESION_DETAILS + "?quesion_id=" + quesionId, null, "GET", new HttpRequestUtils.ResponseListener() {


            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        PublishQuestionDetialBean questionDetialBean = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"), PublishQuestionDetialBean.class);
                        //显示问题详情
                        tv_title.setText(questionDetialBean.getQuesionTitle());
                        tv_content.setText(questionDetialBean.getQuesionDetail()+"");
                        tv_comment_count.setText(questionDetialBean.getQuesionMessage()+"个");
                        tv_view_count.setText(questionDetialBean.getBrowseNum()+"次");

                        if(!TextUtils.isEmpty(questionDetialBean.getCreateTime())){
                            tv_create_time.setText(questionDetialBean.getCreateTime().substring(0,10));
                        }

                        String img = questionDetialBean.getQuesionImg();
                        if (img != null && !img.equals("")) {
                            String[] imgs = img.split(",");
                            for (int i=0;i<imgs.length; i++) {
                                urlStrsShow.add(imgs[i]);
                            }
                            adpterShow.setLists(urlStrsShow);
                            adpterShow.notifyDataSetChanged();
                            tv_show_all.setVisibility(View.VISIBLE);
                            gridview_show.setVisibility(View.GONE);
                        }else{
                            tv_show_all.setVisibility(View.GONE);
                        }

                        ViewTreeObserver vto = tv_content.getViewTreeObserver();
                        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                tv_content.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                                ll_height =  tv_content.getHeight();
                                if(ll_height > 190){
                                    tv_content.setHeight(190);
                                    tv_show_all.setVisibility(View.VISIBLE);
                                }
                            }
                        });

                    /*    final int multiple = (urlStrsShow.size()+3-1)/3;
                        ViewTreeObserver vto = tv_content.getViewTreeObserver();
                        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                tv_content.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                                int contentHeight =  tv_content.getHeight();
                                ll_height = contentHeight + multiple*164 + 90;
                                if(ll_height > 270){
                                    LinearLayout.LayoutParams rlRequestionLayoutParams = (LinearLayout.LayoutParams) rl_question_details.getLayoutParams();
                                    rlRequestionLayoutParams.height = 270;
                                    rl_question_details.setLayoutParams(rlRequestionLayoutParams);
                                    tv_show_all.setVisibility(View.VISIBLE);
                                }else{
                                    tv_show_all.setVisibility(View.GONE);
                                }
                                rl_question_details.setVisibility(View.VISIBLE);
                            }
                        });*/
                    break;
                    default:
                        ToastUtil.showToast(AnswerQuestionActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    private boolean isVerify(){
        String content = et_content.getText().toString().trim();
        if (content == null || content.length() < 1) {
            ToastUtil.showToast(AnswerQuestionActivity.this, "请填写您的回答内容！");
            return false;
        }
        return true;
    }

    /**
     * 提交数据
     */
    private void publishData() {

        final String title = tv_title.getText().toString().trim();
        String content = et_content.getText().toString().trim();

        Map<String, String> params = new HashMap<>();

        String requestPath = RequestValue.QUESION_QUESIONANSWER;
        if (TextUtils.isEmpty(quesionId)) {return;}
        params.put("quesion_id", quesionId);

        /* int titleLength = title.length();
        if (titleLength < 5 || titleLength > 49) {
            ToastUtil.showToast(AnswerQuestionActivity.this, "请输入5-49个字的标题");
            return;
        }
        params.put("quesion_title", title);*/

  /*      if (content == null || content.length() < 1) {
            ToastUtil.showToast(AnswerQuestionActivity.this, "请填写您的回答内容！");
            return;
        }*/
        params.put("answer_detail", content);
        params.put("answer_img", imgPath1+"");
        params.put("quesion_title", title);
        if(isAnonymous){
            params.put("is_null","1");
        }else{
            params.put("is_null","0");
        }

        pd.setMessage("提交数据中,请稍后...");
        pd.show();
        txtRight.setEnabled(false);

        HttpRequestUtils.httpRequest(this, "回答问题", requestPath, params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                txtRight.setEnabled(true);
                pd.dismiss();
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        ToastUtil.showToast(AnswerQuestionActivity.this, "发布成功!");

                        et_content.setText("");
                        //mEditor.setHtml("");
                      //  clearCache();
                        SharedPreferencesUtils.setParam(AnswerQuestionActivity.this,"isAnswerQuestion",true);
                        finish();
                         //IntentUtil.gotoActivity(AnswerQuestionActivity.this, MyAnswerActivity.class);
                        break;
                    default:
                        ToastUtil.showToast(AnswerQuestionActivity.this, common.getInfo());
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
                            urlStrs.add(PictureUtils.getPath(AnswerQuestionActivity.this, uri));
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
                        File file = new Compressor.Builder(AnswerQuestionActivity.this).setMaxHeight(1080).setMaxWidth(1920)
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
                        ToastUtil.showToast(AnswerQuestionActivity.this, "选择图片失败");
                    }
                    break;
            }
        }
    }


    @OnClick({R.id.tv_show_all})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.tv_show_all:
                if(questionIsShow){
                    questionIsShow = false;
                    tv_show_all.setText("展开全部");
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_show_down_blue);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                            drawable.getMinimumHeight());
                    tv_show_all.setCompoundDrawables(null, null, drawable, null);
                    if(ll_height != 0){
                        tv_content.setHeight(190);
                    }
                    gridview_show.setVisibility(View.GONE);
                }else{
                    questionIsShow = true;
                    tv_show_all.setText("收起问题");
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_show_up_blue);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                            drawable.getMinimumHeight());
                    tv_show_all.setCompoundDrawables(null, null, drawable, null);
                    gridview_show.setVisibility(View.VISIBLE);

                    if(ll_height != 0){
                        tv_content.setHeight(ll_height);
                    }

                    if(urlStrs.size() > 0){
                        gridview_show.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
    }
}
