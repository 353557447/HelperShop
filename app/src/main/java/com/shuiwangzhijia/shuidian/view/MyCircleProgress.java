package com.shuiwangzhijia.shuidian.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.shuiwangzhijia.shuidian.R;


/**
 * Created by Lijn on 2019/4/10.
 */

public class MyCircleProgress extends View{
    private Context mContext;
    private  Paint paint;
    private Paint textPaint;
    public int progress  = 0;//进度实际值,当前进度
    private String textProgress="0";
    /**
     * 自定义控件属性，可灵活的设置圆形进度条的大小、颜色、类型等
     */
    private int mR;//圆半径，决定圆大小
    private int bgColor;//圆或弧的背景颜色
    private int fgColor;//圆或弧的前景颜色，即绘制时的颜色
    private int drawStyle; //绘制类型 FILL画圆形进度条，STROKE绘制弧形进度条
    private int strokeWidth;//STROKE绘制弧形的弧线的宽度
    private int max;//最大值，设置进度的最大值
    private boolean opt;

    public MyCircleProgress(Context context) {
        super(context);
    }

    public MyCircleProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        //画圆

        this.paint = new Paint();
        this.paint.setAntiAlias(true); // 消除锯齿
        this.paint.setStyle(Paint.Style.STROKE); // 绘制空心圆或 空心矩形
        //画字

        textPaint=new Paint();
        textPaint.setStrokeWidth(0);
        //字体大小
        textPaint.setTextSize(36);
        //画笔颜色
        textPaint.setColor(Color.parseColor("#A53533"));
        textPaint.setAntiAlias(true);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        initProperty(attrs);
    }



    public MyCircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void initProperty(AttributeSet attrs) {
        TypedArray tArray = mContext.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        mR=tArray.getInteger(R.styleable.CircleProgressBar_r,10);
        bgColor=tArray.getColor(R.styleable.CircleProgressBar_bgColor, Color.GRAY);
        fgColor=tArray.getColor(R.styleable.CircleProgressBar_fgColor, Color.RED);
        drawStyle=tArray.getInt(R.styleable.CircleProgressBar_drawStyle, 0);
        strokeWidth=tArray.getInteger(R.styleable.CircleProgressBar_strokeWidth, 10);
        max=tArray.getInteger(R.styleable.CircleProgressBar_max, 100);
    }



    protected  void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth() / 2; // 圆心位置
        this.paint.setColor(bgColor);
        this.paint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(center, center, mR, this.paint);
        // 绘制圆环
        this.paint.setColor(fgColor);
        if(drawStyle==0){
            this.paint.setStyle(Paint.Style.STROKE);
            opt=false;
        }else{
            this.paint.setStyle(Paint.Style.FILL);
            opt=true;
        }
        int top = (center - mR);
        int bottom = (center + mR);
        RectF oval = new RectF(top, top, bottom, bottom);
        canvas.drawArc(oval, 270, 360*progress/max, opt, paint);

        Rect rect = new Rect();
        if(textProgress.length()>4){
            textProgress= textProgress.substring(0,3)+"..";
        }
        textPaint.getTextBounds(textProgress + "%", 0, (textProgress + "%").length(), rect);
        int w = rect.width();
        int h = rect.height();
        if (drawStyle == 0) {
            canvas.drawText(textProgress + "%", center - w/2, center + h / 2, textPaint);
        }

    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     */
    public synchronized void setProgress(int currentProgress) {
        textProgress=currentProgress+"";
        if(currentProgress>100){
            currentProgress=100;
        }
        ValueAnimator valueAnimator=ValueAnimator.ofInt(0,currentProgress);
        valueAnimator.setDuration(1500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                if(animatedValue<0){
                    animatedValue=0;
                }else if(animatedValue>max){
                    animatedValue=max;
                }else{
                    progress = animatedValue;
                }
                invalidate();
            }
        });
        valueAnimator.start();

    }
    public int getMax() {
        return max;
    }
}
