package com.lwc.shanxiu.module.lease_parts.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.BaseFragment;
import com.lwc.shanxiu.module.lease_parts.activity.LeaseGoodsListActivity;
import com.lwc.shanxiu.module.lease_parts.adapter.LeaseScreenItemAdapter;
import com.lwc.shanxiu.module.lease_parts.adapter.LeaseScreenItemTypeAdapter;
import com.lwc.shanxiu.module.lease_parts.bean.LeaseTypeBean;
import com.lwc.shanxiu.module.partsLib.ui.bean.MyScreenItemBean;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author younge
 * @date 2018/12/25 0025
 * @email 2276559259@qq.com
 */
public class LeaseFilterFragment extends BaseFragment {

    private DrawerLayout mDrawerLayout;
    private FrameLayout mDrawerContent;
    private TextView btn_reset;
    private TextView btn_confirm;
    private LinearLayout ll_screening_contents;
    private String typeIds = "";
    private String typeDetailId = "";
    private StringBuilder ohterSeach = new StringBuilder();
    private int SHOW_HEIGHT;
    private Context mContext;

    private TextView selectTypeView;
    private int selectPositon = -1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lease_filter, null);
        mContext = getContext();
        SHOW_HEIGHT = Utils.dip2px(getContext(),45);
        initView(view);
        initEvent();

        Log.d("调用方法","onCreateView");
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("调用方法","onAttach");
    }

    private void initEvent() {
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // mDrawerLayout.closeDrawer(mDrawerContent);
                ohterSeach.setLength(0);
                selectTypeView = null;
                selectPositon = -1;

                getSearchCondition();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otherString = ohterSeach.toString();
                if(!TextUtils.isEmpty(ohterSeach)){
                    int index = ohterSeach.lastIndexOf("|");
                    otherString = ohterSeach.substring(0,index);
                }
                mDrawerLayout.closeDrawer(mDrawerContent);
                ((LeaseGoodsListActivity)getActivity()).onDefiniteScreen(typeIds.toString(),otherString);
            }
        });
    }

    private void initView(View view) {
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        mDrawerContent = (FrameLayout) getActivity().findViewById(R.id.drawer_content);
        ll_screening_contents = (LinearLayout) view.findViewById(R.id.ll_screening_contents);
        btn_reset = (TextView) view.findViewById(R.id.btn_reset);
        btn_confirm = (TextView) view.findViewById(R.id.btn_confirm);
        typeIds = getArguments().getString("typeId","");
        typeDetailId = getArguments().getString("typeDetailId","");

        getSearchCondition();
    }

    /**
     * 获取搜索条件
     *
     */
    private void getSearchCondition(){
        String netUrl = RequestValue.PARTSMANAGE_GETPARTSTYPEALL;
        ohterSeach.setLength(0);
        Map<String,String> params = new HashMap<>();
        if(!TextUtils.isEmpty(typeIds)){
            params.put("device_type_id",typeIds);
        }

        if(!TextUtils.isEmpty(typeDetailId)){
            params.put("type_detail_id",typeDetailId);
        }
        HttpRequestUtils.httpRequest(getActivity(), "租赁商品筛选内容",netUrl,params, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        LeaseTypeBean accessoriesTypeAll = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"),LeaseTypeBean.class);
                        onLoadUI(accessoriesTypeAll);
                        break;
                    default:
                        ToastUtil.showToast(getActivity(),common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showToast(getActivity(),msg);
            }
        });
    }

    @Override
    protected void lazyLoad() {
    }

    private void onLoadUI(LeaseTypeBean accessoriesTypeAll){
        if(mContext == null){
            return;
        }

        ll_screening_contents.removeAllViews();

        /**
         * 一级分类
         */
        if(accessoriesTypeAll != null && accessoriesTypeAll.getTypes() != null && accessoriesTypeAll.getTypes().size() > 0){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout baseLinearLayout = new LinearLayout(mContext);
            params.setMargins(0,20,0,0);
            baseLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            baseLinearLayout.setLayoutParams(params);

            TextView txtTitle = new TextView(mContext);
            txtTitle.setPadding(20,20,0,0);
            txtTitle.setGravity(Gravity.LEFT);
            txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
            txtTitle.setText("类型");
            txtTitle.setTextColor(getResources().getColor(R.color.black));
            txtTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
            LinearLayout.LayoutParams txtTitleParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,1);
            txtTitle.setLayoutParams(txtTitleParams);
            baseLinearLayout.addView(txtTitle);

            final TextView txtAll = new TextView(mContext);
            txtAll.setPadding(0,20,40,0);
            txtAll.setText("");
            txtAll.setGravity(Gravity.RIGHT);
            txtAll.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
            LinearLayout.LayoutParams txtAllParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,1);

            Drawable drawable = getResources().getDrawable(R.drawable.ic_up_select);
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            txtAll.setCompoundDrawables(null,null,drawable,null);

            //txtAll.setLayoutParams(txtAllParams);

            baseLinearLayout.addView(txtAll);

            ll_screening_contents.addView(baseLinearLayout);


            final List<MyScreenItemBean> types = new ArrayList<>();
            for (int i = 0;i< accessoriesTypeAll.getTypes().size();i++){
                MyScreenItemBean myScreenItemBean = new MyScreenItemBean(accessoriesTypeAll.getTypes().get(i).getTypeDetailName(),accessoriesTypeAll.getTypes().get(i).getTypeDetailId());
               if(!TextUtils.isEmpty(myScreenItemBean.getId())){
                   if(myScreenItemBean.getId().equals(typeDetailId)){
                       myScreenItemBean.setSelect(true);
                   }
               }
                types.add(myScreenItemBean);
            }

            final GridView gridView = new GridView(mContext);

            int line = types.size()/3;
            if(types.size() % 3 > 0){
                line = line + 1;
            }
            LinearLayout.LayoutParams gridViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,SHOW_HEIGHT*line);
            gridViewParams.setMargins(10,20,10,0);
            gridView.setLayoutParams(gridViewParams);
            gridView.setHorizontalSpacing(20);
            gridView.setVerticalSpacing(20);
            gridView.setNumColumns(3);
            gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

            gridView.setAdapter(new LeaseScreenItemTypeAdapter(mContext,types));
            ll_screening_contents.addView(gridView);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView textView = (TextView) view.findViewById(R.id.tv_content);
                    MyScreenItemBean myScreenItemBean = types.get(position);

                    if(!myScreenItemBean.isSelect()){
                        myScreenItemBean.setSelect(true);
                        textView.setBackgroundResource(R.drawable.button_red_round_shape);
                        textView.setTextColor(Color.parseColor("#ff3a3a"));

                        //处理上一次的item
                        if(selectTypeView != null){
                            selectTypeView.setTextColor(Color.parseColor("#333333"));
                            selectTypeView.setBackgroundResource(R.drawable.button_gray_round_shape_f0);
                        }
                        if(selectPositon != -1){
                            types.get(selectPositon).setSelect(false);
                        }

                        //赋值给当前item
                        selectTypeView = textView;
                        selectPositon = position;

                        //getSearchCondition();
                    }

                    if(myScreenItemBean.isSelect()){
                        textView.setBackgroundResource(R.drawable.button_gray_round_shape_f0);
                        textView.setTextColor(Color.parseColor("#333333"));
                        myScreenItemBean.setSelect(false);
                    }else{
                        textView.setBackgroundResource(R.drawable.button_red_round_shape);
                        textView.setTextColor(Color.parseColor("#ff3a3a"));
                        types.get(position).setSelect(true);
                    }

                    typeDetailId = myScreenItemBean.getId();
                    getSearchCondition();
                }
            });

            txtAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(gridView.getVisibility() == View.VISIBLE){
                        gridView.setVisibility(View.GONE);

                        Drawable drawable = getResources().getDrawable(R.drawable.ic_down_select);
                        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                        txtAll.setCompoundDrawables(null,null,drawable,null);
                      //  txtAll.setText("全部");
                    }else{
                        gridView.setVisibility(View.VISIBLE);

                        Drawable drawable = getResources().getDrawable(R.drawable.ic_up_select);
                        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                        txtAll.setCompoundDrawables(null,null,drawable,null);
                      //  txtAll.setText("收起");
                    }

                }
            });
        }

        /**
         * 二级分类
         */
        if(accessoriesTypeAll != null && accessoriesTypeAll.getAttributes() != null && accessoriesTypeAll.getAttributes().size() > 0){
            List<LeaseTypeBean.AttributesBean> attributesBeanList = accessoriesTypeAll.getAttributes();
            for(int i = 0;i < attributesBeanList.size(); i++){
                LeaseTypeBean.AttributesBean attributesBean = attributesBeanList.get(i);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,20,0,0);
                LinearLayout baseLinearLayout = new LinearLayout(mContext);
                baseLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                baseLinearLayout.setLayoutParams(params);

                TextView txtTitle = new TextView(mContext);
                txtTitle.setPadding(20,20,0,0);
                txtTitle.setGravity(Gravity.LEFT);
                txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
                txtTitle.setText(attributesBean.getAttributeName());
                txtTitle.setTextColor(getResources().getColor(R.color.black));
                txtTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                LinearLayout.LayoutParams txtTitleParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,1);
                txtTitle.setLayoutParams(txtTitleParams);
                baseLinearLayout.addView(txtTitle);

                final TextView txtAll = new TextView(mContext);
                txtAll.setPadding(0,20,40,0);
                txtAll.setText("");
                txtAll.setGravity(Gravity.RIGHT);
                txtAll.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
                LinearLayout.LayoutParams txtAllParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,1);

                Drawable drawable = getResources().getDrawable(R.drawable.ic_up_select);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                txtAll.setCompoundDrawables(null,null,drawable,null);

                txtAll.setLayoutParams(txtAllParams);
                baseLinearLayout.addView(txtAll);

                ll_screening_contents.addView(baseLinearLayout);

                final GridView gridView = new GridView(mContext);

                final List<MyScreenItemBean> types = new ArrayList<>();
                for (int j = 0;j< attributesBean.getOptions().size();j++){
                    types.add(new MyScreenItemBean(attributesBean.getOptions().get(j).getOptions_name(),attributesBean.getOptions().get(j).getOptions_id()));
                }

                int line = types.size()/3;
                if(types.size() % 3 > 0){
                    line = line + 1;
                }
                LinearLayout.LayoutParams gridViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,SHOW_HEIGHT*line);
                gridViewParams.setMargins(10,20,10,0);
                gridView.setLayoutParams(gridViewParams);

                gridView.setHorizontalSpacing(20);
                gridView.setVerticalSpacing(20);
                gridView.setNumColumns(3);
                gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

                gridView.setAdapter(new LeaseScreenItemAdapter(mContext,types));
                ll_screening_contents.addView(gridView);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView textView = (TextView) view.findViewById(R.id.tv_content);
                        if(types.get(position).isSelect()){
                            textView.setBackgroundResource(R.drawable.button_gray_round_shape_f0_30);
                            textView.setTextColor(Color.parseColor("#333333"));
                            types.get(position).setSelect(false);
                            String txtItem = textView.getText().toString().trim();
                            int txtIndex = ohterSeach.indexOf(txtItem);
                            ohterSeach.delete(txtIndex,txtIndex+txtItem.length()+1);
                        }else{
                            textView.setBackgroundResource(R.drawable.button_red_round_shape_30);
                            textView.setTextColor(Color.parseColor("#ff3a3a"));
                            types.get(position).setSelect(true);
                            ohterSeach.append(textView.getText().toString().trim()+"|");
                        }
                    }
                });

                txtAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(gridView.getVisibility() == View.VISIBLE){
                            gridView.setVisibility(View.GONE);
                            Drawable drawable = getResources().getDrawable(R.drawable.ic_down_select);
                            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                            txtAll.setCompoundDrawables(null,null,drawable,null);
                           // txtAll.setText("全部");
                        }else{
                            gridView.setVisibility(View.VISIBLE);
                            Drawable drawable = getResources().getDrawable(R.drawable.ic_up_select);
                            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                            txtAll.setCompoundDrawables(null,null,drawable,null);
                            //txtAll.setText("收起");
                        }
                    }
                });
            }
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void initEngines(View view) {

    }

    @Override
    public void getIntentData() {

    }

    @Override
    public void setListener() {

    }


}
