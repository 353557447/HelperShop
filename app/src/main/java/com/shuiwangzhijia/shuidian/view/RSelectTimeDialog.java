package com.shuiwangzhijia.shuidian.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bruce.pickerview.LoopScrollListener;
import com.bruce.pickerview.LoopView;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.FlashSaleSessionBean;
import com.shuiwangzhijia.shuidian.bean.MyReturnMoneyListBean;
import com.shuiwangzhijia.shuidian.dialog.BottomDialog;
import com.socks.library.KLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RSelectTimeDialog extends BottomDialog {
    private final Window window;
    private final int width;
    @BindView(R.id.cancelBtn)
    TextView cancelBtn;
    @BindView(R.id.sureBtn)
    TextView sureBtn;
    @BindView(R.id.dateLoopView)
    LoopView dateLoopView;
    private OnItemClickListener clickListener;
    private int selectTimePosition;
    private List<String> dateList = new ArrayList<>();
    private final SimpleDateFormat mSdf;

    @OnClick({R.id.cancelBtn, R.id.sureBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancelBtn:
                dismiss();
                break;
            case R.id.sureBtn:
                clickListener.onSureClick(this, selectTimePosition);
                break;
        }
    }


    public interface OnItemClickListener {
        void onSureClick(RSelectTimeDialog dialog, int selectTimePosition);
    }

    public RSelectTimeDialog(Context context, int type, int mReturnType, List<MyReturnMoneyListBean.DataBean.ConductBean.TimeBean> mTimeUnderway, List<MyReturnMoneyListBean.DataBean.HistoryBean.TimeBean> mTimeCancel, OnItemClickListener clickListener) {
        super(context);
        window = getWindow();
        width = window.getWindowManager().getDefaultDisplay().getWidth();
        this.clickListener = clickListener;
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_rselect_time);
        ButterKnife.bind(this);
        //  mSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mSdf = new SimpleDateFormat("yyyy-MM-dd");
        if (type == 1) {
            switch (mReturnType) {
                case 2:
                   // mRType.setText("月度返利");
                    for (int i = 0; i < mTimeUnderway.size(); i++) {
                        dateList.add(mTimeUnderway.get(i).getTime());
                    }
                    break;
                case 3:
                    //mRType.setText("季度返利");
                    for (int i = 0; i < mTimeUnderway.size(); i++) {
                        dateList.add(mTimeUnderway.get(i).getTime());
                    }
                    break;
            }
        } else {
            switch (mReturnType) {
                case 2:
                    // mRType.setText("月度返利");
                    for (int i = 0; i < mTimeCancel.size(); i++) {
                        dateList.add(mTimeCancel.get(i).getTime());
                    }
                    break;
                case 3:
                    //mRType.setText("季度返利");
                    for (int i = 0; i < mTimeCancel.size(); i++) {
                        dateList.add(mTimeCancel.get(i).getTime());
                    }
                    break;
            }
        }

        //getDateList();
        dateLoopView.setInitPosition(0);
        dateLoopView.setCanLoop(false);
        dateLoopView.setLoopListener(new LoopScrollListener() {
            @Override
            public void onItemSelect(int item) {
                try {
                    selectTimePosition = item;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        dateLoopView.setDataList(dateList);
    }

  /*  private void getDateList() {
        dateList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dateList.add(DateUtils.getCustomDateWithWeek(new Date(), i));
        }
        timeList.add("09:00--12:00");
        timeList.add("14:00--18:00");
        date = dateList.get(0);
        time = timeList.get(0);
    }*/


    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams attributesParams = window.getAttributes();
        attributesParams.gravity = Gravity.BOTTOM;
        attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        attributesParams.dimAmount = 0.4f;
        attributesParams.width = width;
        attributesParams.x = 0;
        window.setAttributes(attributesParams);
    }
}
