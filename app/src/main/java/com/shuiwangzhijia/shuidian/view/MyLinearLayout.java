package com.shuiwangzhijia.shuidian.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Lijn on 2019/4/11.
 */

public class MyLinearLayout extends LinearLayout {
    private float startDownY;

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startDownY = event.getRawY();
                break;
        }
        return false;
    }

    private OnMyClickListener mOnMyClickListener;

    public void setOnMyClickListener(OnMyClickListener mOnMyClickListener) {
        this.mOnMyClickListener = mOnMyClickListener;
    }

    public interface OnMyClickListener {
        void OnMyClick(View view);
    }

    public void myPerformClick(float endUpY) {
        if(startDownY!=0) {
            if (Math.abs((int) (endUpY - startDownY)) < 20) {
                if (mOnMyClickListener != null) {
                    mOnMyClickListener.OnMyClick(this);
                }
            }
        }
        startDownY=0;
    }
}
