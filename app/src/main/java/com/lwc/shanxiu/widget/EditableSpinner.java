package com.lwc.shanxiu.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.utils.DisplayUtil;
import com.lwc.shanxiu.utils.DisplayUtils;

public class EditableSpinner extends LinearLayout implements AdapterView.OnItemClickListener {

  //  private ImageButton mImgBtnDown;
    private EditText mEtInput;
    private ListPopupWindow mListPopupWindow;
    private OnItemClickListener mOnItemClickListener;
    private ArrayAdapter mArrayAdapter;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public EditableSpinner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.editable_spinner,this);

        mEtInput = findViewById(R.id.et_companyName);
        mListPopupWindow = new ListPopupWindow(context);

        TypedArray attrArr = context.obtainStyledAttributes(attrs, R.styleable.EditableSpinner);
      //  Drawable downImage = attrArr.getDrawable(R.styleable.EditableSpinner_editableSpinnerdownBtnImage);
       // float textSize = attrArr.getDimension(R.styleable.EditableSpinner_editableSpinnertextSize, 30);
       // int downBtnWidth = (int) attrArr.getDimension(R.styleable.EditableSpinner_editableSpinnerdownBtnWidth, DisplayUtil.dip2px(context, 40));
       // int textColor = attrArr.getColor(R.styleable.EditableSpinner_editableSpinnertextColor, 0x000000);
       // int downBtnBgColor = attrArr.getColor(R.styleable.EditableSpinner_editableSpinnerdownBtnBackground, 0x00ffffff);

//        mImgBtnDown.setImageDrawable(downImage);
      //  mImgBtnDown.setBackgroundColor(downBtnBgColor);
        //LinearLayout.LayoutParams lp = (LayoutParams) mImgBtnDown.getLayoutParams();
      //  lp.width = downBtnWidth;
       // mImgBtnDown.setLayoutParams(lp);

       /* mImgBtnDown.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListPopupWindow.show();
            }
        });*/

     /*   mEtInput.setTextSize(DisplayUtil.px2dip(context, textSize));
        mEtInput.setTextColor(textColor);*/
    }


    public void showEditableSpinner(){
        mListPopupWindow.show();
    }

    public void dismissEditableSpinner(){
        mListPopupWindow.dismiss();
    }

    public EditableSpinner setAdapter(ArrayAdapter<String> adapter) {

        mArrayAdapter = adapter;

        mListPopupWindow.setAdapter(adapter);
        mListPopupWindow.setAnchorView(mEtInput);
        mListPopupWindow.setModal(true);
        mListPopupWindow.setOnItemClickListener(this);
        return this;
    }

    public EditableSpinner setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        return this;
    }

    public String getSelectedItem() {
        return mEtInput.getText().toString();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mListPopupWindow.dismiss();
        mEtInput.setText((CharSequence) mArrayAdapter.getItem(position));
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(position);
        }
    }
}