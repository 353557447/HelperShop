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


/**
 * 选择时间器
 * Created by wangsuli on 2017/5/5
 */
public class SelectTimeDialog extends BottomDialog {
    private final Window window;
    private final int width;
    @BindView(R.id.cancelBtn)
    TextView cancelBtn;
    @BindView(R.id.sureBtn)
    TextView sureBtn;
    @BindView(R.id.dateLoopView)
    LoopView dateLoopView;
    @BindView(R.id.timeLoopView)
    LoopView timeLoopView;
    private OnItemClickListener clickListener;
    private int selectTimePosition;
    private long selectDateTimestamp;
    private List<String> dateList= new ArrayList<>();
    private List<String> timeList = new ArrayList<>();
    private final SimpleDateFormat mSdf;

    @OnClick({R.id.cancelBtn, R.id.sureBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancelBtn:
                dismiss();
                break;
            case R.id.sureBtn:
                clickListener.onSureClick(this,selectDateTimestamp, selectTimePosition);
                break;
        }
    }


    public interface OnItemClickListener {
        void onSureClick(SelectTimeDialog dialog,long selectDateTimestamp, int selectTimePosition);
    }

    public SelectTimeDialog(Context context, List<FlashSaleSessionBean.DataBean> data, OnItemClickListener clickListener) {
        super(context);
        window = getWindow();
        width = window.getWindowManager().getDefaultDisplay().getWidth();
        this.clickListener = clickListener;
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_select_time);
        ButterKnife.bind(this);
      //  mSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mSdf = new SimpleDateFormat("yyyy-MM-dd");

        //getDateList();
        dateLoopView.setInitPosition(0);
        try {
            selectDateTimestamp = mSdf.parse(mSdf.format(new Date())).getTime()/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateLoopView.setCanLoop(false);
        dateLoopView.setLoopListener(new LoopScrollListener() {
            @Override
            public void onItemSelect(int item) {
                try {
                  //  selectDateTimestamp = mSdf.parse(dateList.get(item) + " " + "00:00:00").getTime()/1000;
                    selectDateTimestamp = mSdf.parse(dateList.get(item)).getTime()/1000;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        for (int i = 0; i < 30; i++) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, i);
            Date date = cal.getTime();
          //  dateList.add(mSdf.format(date).split(" ")[0]);
            dateList.add(mSdf.format(date));
            KLog.e(mSdf.format(date));
        }
        dateLoopView.setDataList(dateList);


        for (int i = 0; i < data.size(); i++) {
            FlashSaleSessionBean.DataBean dataBean = data.get(i);
            String startTime = dataBean.getStart_time();
            String endTime = dataBean.getEnd_time();
            timeList.add(startTime + "~" + endTime);
        }
        timeLoopView.setInitPosition(0);
        selectTimePosition = 0;
        timeLoopView.setCanLoop(false);
        timeLoopView.setLoopListener(new LoopScrollListener() {
            @Override
            public void onItemSelect(int item) {
                //time = timeList.get(item);
                selectTimePosition = item;
            }
        });
        timeLoopView.setDataList(timeList);
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
