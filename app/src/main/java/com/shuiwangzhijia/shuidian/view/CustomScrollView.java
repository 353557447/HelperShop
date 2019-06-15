package com.shuiwangzhijia.shuidian.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * Created by xxc on 2019/4/2.
 */

public class CustomScrollView extends NestedScrollView {
    public interface OnScrollListener{
        void onScroll(int l,int t,int oldl,int oldt);
    }

    private OnScrollListener mOnScrollListener;

    public CustomScrollView(@NonNull Context context) {
        super(context);
    }

    public CustomScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollListener != null){
            mOnScrollListener.onScroll(l,t,oldl,oldt);
        }
    }

    //设置监听
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }
}
