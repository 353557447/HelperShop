package com.shuiwangzhijia.shuidian.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class CouponDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private Context context;

    public CouponDecoration(int space, Context context) {
        this.space = space;
        this.context = context;
    }

    public CouponDecoration(Context context, int space) {
        this.space = space;
        this.context = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
          /*  if ((parent.getChildPosition(view) - 1) % 4 == 0)
                outRect.left = space;
            if (parent.getChildPosition(view)+1 == parent.getAdapter().getItemCount()) {
                outRect.bottom = (int)context.getResources().getDimension(R.dimen.x110);
            }*/
        int childLayoutPosition = parent.getChildLayoutPosition(view);
        if(childLayoutPosition==0){
            outRect.top = space;
        }else{
            outRect.top = space / 2;
        }
        outRect.bottom = space/2;
    }
}