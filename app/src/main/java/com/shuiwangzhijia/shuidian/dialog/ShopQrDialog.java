package com.shuiwangzhijia.shuidian.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import com.shuiwangzhijia.shuidian.R;

import butterknife.ButterKnife;

public class ShopQrDialog extends BottomDialog{
    public ShopQrDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_shop_qr);
        setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);
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
