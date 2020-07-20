package com.m7.imkfsdk.chat;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.m7.imkfsdk.R;
import com.m7.imkfsdk.chat.model.Option;
import com.m7.imkfsdk.view.FlowRadioGroup;
import com.m7.imkfsdk.view.TagView;
import com.moor.imkf.IMChatManager;
import com.moor.imkf.listener.SubmitInvestigateListener;
import com.moor.imkf.model.entity.Investigate;

import java.util.ArrayList;
import java.util.List;

/**
 * 评价列表界面
 */
@SuppressLint("ValidFragment")
public class InvestigateDialog extends DialogFragment {

    private TextView investigateTitleTextView;
    private FlowRadioGroup investigateRadioGroup;
    private TagView investigateTag;
    private Button investigateOkBtn;
    private Button investigateCancelBtn;
    private EditText investigateEt;

    private SubmitPingjiaListener submitListener;
    private List<Investigate> investigates = new ArrayList<Investigate>();

    private SharedPreferences sp;
    String satisfyTitle;
    String name, value;
    List<Option> selectLabels = new ArrayList<>();
    private boolean labelRequired;
    private boolean proposalRequired;

    public InvestigateDialog() {
    }

    @SuppressLint("ValidFragment")
    public InvestigateDialog(SubmitPingjiaListener submitListener) {
        super();
        this.submitListener = submitListener;
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().setTitle("提交评价");

        getDialog().setCanceledOnTouchOutside(false);

        sp = getActivity().getSharedPreferences("moordata", 0);

        // Get the layout inflater
        View view = inflater.inflate(R.layout.kf_dialog_investigate, null);
        investigateTitleTextView = (TextView) view.findViewById(R.id.investigate_title);
        investigateRadioGroup = (FlowRadioGroup) view.findViewById(R.id.investigate_rg);
        investigateTag = (TagView) view.findViewById(R.id.investigate_second_tg);
        investigateOkBtn = (Button) view.findViewById(R.id.investigate_save_btn);
        investigateCancelBtn = (Button) view.findViewById(R.id.investigate_cancel_btn);
        investigateEt = (EditText) view.findViewById(R.id.investigate_et);
        investigates = IMChatManager.getInstance().getInvestigate();

        initView();

        investigateTag.setOnSelectedChangeListener(new TagView.OnSelectedChangeListener() {
            @Override
            public void getTagList(List<Option> options) {
                selectLabels = options;
            }
        });

        satisfyTitle = sp.getString("satisfyTitle", "感谢您使用我们的服务，请为此次服务评价！");
        if (satisfyTitle.equals("")) {
            satisfyTitle = "感谢您使用我们的服务，请为此次服务评价！";
        }
        investigateTitleTextView.setText(satisfyTitle);
        String satifyThank = sp.getString("satisfyThank", "感谢您对此次服务做出评价，祝您生活愉快，再见！");
        if (satifyThank.equals("")) {
            satifyThank = "感谢您对此次服务做出评价，祝您生活愉快，再见！";
        }

        final String finalSatifyThank = satifyThank;


        investigateOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (proposalRequired) {
                    if (investigateEt.getText().toString().trim().length() == 0) {
                        Toast.makeText(getActivity(), "请填写满意度评价原因", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                List<String> labels = new ArrayList<>();
                if (selectLabels.size() > 0) {
                    for (Option option : selectLabels) {
                        labels.add(option.name);
                    }
                }
                if (labelRequired) {
                    if (labels.size() == 0) {
                        Toast.makeText(getActivity(), "请选择满意度评价标签", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (name == null) {
                    Toast.makeText(getActivity(), "请选择评价选项", Toast.LENGTH_SHORT).show();
                    return;
                }
                IMChatManager.getInstance().submitInvestigate(name, value, labels, investigateEt.getText().toString().trim(), new SubmitInvestigateListener() {
                    @Override
                    public void onSuccess() {
                        submitListener.OnSubmitSuccess();
                        Toast.makeText(getActivity(), finalSatifyThank, Toast.LENGTH_SHORT).show();
                        dismiss();
                    }

                    @Override
                    public void onFailed() {
                        submitListener.OnSubmitFailed();
                        Toast.makeText(getActivity(), "评价提交失败", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                });
            }
        });

        investigateCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitListener.OnSubmitCancle();
                dismiss();
            }
        });
        return view;
    }

    private void initView() {

        for (int i = 0; i < investigates.size(); i++) {
            final Investigate investigate = investigates.get(i);
            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setText(" " + investigate.name + "  ");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(7, 7, 7, 7);
            radioButton.setLayoutParams(params);
            Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.kf_radiobutton_selector);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            radioButton.setCompoundDrawables(drawable, null, null, null);
            radioButton.setButtonDrawable(null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                radioButton.setBackground(null);
            }
            investigateRadioGroup.addView(radioButton);

            final int finalI = i;
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<Option> options = new ArrayList<>();
                    for (String reason : investigates.get(finalI).reason) {
                        Option option = new Option();
                        option.name = reason;
                        options.add(option);
                        name = investigates.get(finalI).name;
                        value = investigates.get(finalI).value;
                        labelRequired = investigates.get(finalI).labelRequired;
                        proposalRequired = investigates.get(finalI).proposalRequired;
                    }
                    if (investigates.get(finalI).reason.size() == 0) {
                        name = investigates.get(finalI).name;
                        value = investigates.get(finalI).value;
                        labelRequired = investigates.get(finalI).labelRequired;
                        proposalRequired = investigates.get(finalI).proposalRequired;
                    }
                    investigateTag.initTagView(options, 1);
                }
            });
        }

    }

    @Override
    public void show(android.app.FragmentManager manager, String tag) {
        if (!this.isAdded()) {
            try {
                super.show(manager, tag);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
        }

    }

    public interface SubmitPingjiaListener {
        void OnSubmitSuccess();//评价成功

        void OnSubmitCancle();//取消评价

        void OnSubmitFailed();//评价失败或者取消评价

    }


}
