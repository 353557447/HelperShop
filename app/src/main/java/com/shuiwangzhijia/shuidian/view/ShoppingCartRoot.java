package com.shuiwangzhijia.shuidian.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.shuiwangzhijia.shuidian.utils.PreferenceUtils;

/**
 * Created by Administrator on 2018/12/27.
 */

public class ShoppingCartRoot extends RelativeLayout {
    public ShoppingCartRoot(Context context) {
        super(context);
    }

    public ShoppingCartRoot(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShoppingCartRoot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取SingleTouchView所在父布局的中心点
        ViewGroup mViewGroup = (ViewGroup) getParent();
        if(null != mViewGroup){
            int mParentWidth = mViewGroup.getWidth();
            int mParentHeight = mViewGroup.getHeight();
            PreferenceUtils.putInt("shopcart_root_width",mParentWidth);
            PreferenceUtils.putInt("shopcart_root_height",mParentHeight);
        }
    }
}
