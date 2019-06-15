package com.shuiwangzhijia.shuidian.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by cw5628 on 2018/3/20.
 */

public abstract class CentreCommonDialog extends AppCompatActivity {
    private Context mContext;
    private View view;
    private Dialog dialog;

/*    private DisplayMetrics metric = icon_wait_new DisplayMetrics();*/

    //private Vibrator vibrator;
    //vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);


    public CentreCommonDialog() {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //        WindowManager windowManager = getWindowManager();
        //        Display display = windowManager.getDefaultDisplay();
        //        int screenWidth = display.getWidth();
        //        int screenHeight = display.getHeight();
        //
        //        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rl_background.getLayoutParams();
        //
        //        layoutParams.width = screenWidth * 5 / 6;
        //        layoutParams.height = screenHeight * 3 / 5;
        //        rl_background.setLayoutParams(layoutParams);
    }

    public CentreCommonDialog(Context context, int layoutId) {
        this.mContext = context;
        view = LayoutInflater.from(mContext).inflate(layoutId, null);
        dialog = new Dialog(mContext);
        //却掉样式
        dialog.getWindow().setBackgroundDrawable(new BitmapDrawable());
        //        dialog.getWindow().setBackgroundDrawable(icon_wait_new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //却掉dialog样式 包括title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        initViewAndEvent(view);
        Window win = dialog.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.0f;//背景全透明， 不然加载速度过快 会闪一下 ，那样用户体验不好
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;  //设置背景全屏
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;  //设置背景全屏
        dialog.getWindow().setAttributes(lp);
        dialog.setCancelable(true); //背景不可点击
    }

    protected abstract void initViewAndEvent(View view);


    public void show() {
        if (dialog != null) {
            if (!dialog.isShowing()) {
                try {
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void dismiss() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

}
