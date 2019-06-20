package com.shuiwangzhijia.shuidian.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HintDialog extends BottomDialog {

    @BindView(R.id.contentTv)
    TextView contentTv;
    @BindView(R.id.cancelBtn)
    TextView cancelBtn;
    @BindView(R.id.okBtn)
    TextView okBtn;
    private OnConfirmClickListener listener;

    public HintDialog(Context context, String content, final OnConfirmClickListener listener) {
        super(context);
        this.listener = listener;
        setContentView(R.layout.dialog_hint);
        setCanceledOnTouchOutside(true);

        ButterKnife.bind(this);
        contentTv.setText(content);

    }

    @OnClick({R.id.cancelBtn, R.id.okBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancelBtn:
                dismiss();
                break;
            case R.id.okBtn:
                listener.onConfirm(this);
                break;
        }
    }

    public interface OnConfirmClickListener {
        void onConfirm(Dialog dialog);
    }
    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams attributesParams = window.getAttributes();
        attributesParams.gravity = Gravity.CENTER;
        attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        attributesParams.dimAmount = 0.4f;
        attributesParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        attributesParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        attributesParams.x = 0;
        window.setAttributes(attributesParams);
        this.getWindow().setWindowAnimations(R.style.DialogOutAndInStyle);
    }
}
