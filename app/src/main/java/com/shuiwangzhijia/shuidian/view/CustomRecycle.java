package com.shuiwangzhijia.shuidian.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by xxc on 2019/2/20.
 */

public class CustomRecycle extends RecyclerView{
    public CustomRecycle(Context context) {
        super(context);
    }

    public CustomRecycle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecycle(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }
}
