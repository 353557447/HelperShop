package com.shuiwangzhijia.shuidian.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.shuiwangzhijia.shuidian.event.CommonEvent;

import de.greenrobot.event.EventBus;

public class MyFragmentScrollView extends ScrollView {
    private boolean isIntercept = true;

    private OnScrollListener listener;
    public float mRawY;

    public void setOnScrollListener(OnScrollListener listener) {
        this.listener = listener;
    }

    public MyFragmentScrollView(Context context) {
        super(context);
    }

    public MyFragmentScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFragmentScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //设置接口
    public interface OnScrollListener {
        void onScroll(int scrollY);
    }

    //重写原生onScrollChanged方法，将参数传递给接口，由接口传递出去
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null) {

            //这里我只传了垂直滑动的距离
            listener.onScroll(t);
        }
    }

    public void setIntercept(boolean isIntercept) {
        this.isIntercept = isIntercept;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isIntercept) {
            //拦截事件  但不拦截点击事件
            return super.onInterceptTouchEvent(ev);
        } else {
            //不拦截事件
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mRawY = ev.getRawY();
                EventBus.getDefault().post(new CommonEvent("up_cancel_has_changed"));
                break;
        }
        return super.onTouchEvent(ev);
    }
}
