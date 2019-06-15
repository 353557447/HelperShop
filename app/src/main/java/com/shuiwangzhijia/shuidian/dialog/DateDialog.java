package com.shuiwangzhijia.shuidian.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.wheelview.OnWheelChangedListener;
import com.shuiwangzhijia.shuidian.wheelview.WheelView;
import com.shuiwangzhijia.shuidian.wheelview.adapter.NumericWheelAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 時間
 * created by wangsuli on 2018/8/20.
 */
public class DateDialog extends BottomDialog {

    @BindView(R.id.firstWheelView)
    WheelView firstWheelView;
    @BindView(R.id.secondWheelView)
    WheelView secondWheelView;
    @BindView(R.id.cancelBtn)
    TextView cancelBtn;
    @BindView(R.id.sureBtn)
    TextView sureBtn;
    private List<String> hourData;
    private List<String> secondData;
    private OnConfirmClickListener listener;
    private String hour;
    private String second;
    private Context mContext;

    public DateDialog(Context context, final OnConfirmClickListener listener) {
        super(context);
        setContentView(R.layout.dialog_date);
        setCanceledOnTouchOutside(true);
        ButterKnife.bind(this);
        mContext = context;
        this.listener = listener;
        hourData = new ArrayList<>();
        secondData = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            if (i < 24) {
                hourData.add("" + i);
            }
            secondData.add("" + i);
        }
        NumericWheelAdapter numericAdapter1 = new NumericWheelAdapter(context, 0, 23, "%02d");
        numericAdapter1.setTextSize(20);
        firstWheelView.setViewAdapter(numericAdapter1);
        firstWheelView.setCyclic(true);// 可循环滚动
        firstWheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                hour = "" + newValue;
            }

        });
        NumericWheelAdapter secondAdapter = new NumericWheelAdapter(context, 0, 59, "%02d");
        numericAdapter1.setTextSize(20);
        secondWheelView.setViewAdapter(secondAdapter);
        secondWheelView.setCyclic(true);// 可循环滚动
        secondWheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                second = "" + newValue;
            }
        });

    }

    @OnClick({R.id.cancelBtn, R.id.sureBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancelBtn:
                dismiss();
                break;
            case R.id.sureBtn:
                if (TextUtils.isEmpty(hour)) {
                    hour = "00";
                }
                if (TextUtils.isEmpty(second)) {
                    second = "00";
                }
                listener.onConfirm(hour + ":" + second);
                dismiss();
                break;
        }
    }

    public interface OnConfirmClickListener {
        void onConfirm(String time);
    }
}
