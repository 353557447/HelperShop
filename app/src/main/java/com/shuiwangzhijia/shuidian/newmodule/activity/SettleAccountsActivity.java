package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.RMCheckOutOrderListBean;
import com.shuiwangzhijia.shuidian.view.MyScrollView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

@FndViewInject(contentViewId = R.layout.activity_settle_accounts)
public class SettleAccountsActivity extends BaseActivity {
    @BindView(R.id.r_type)
    TextView mRType;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.rule_details)
    TextView mRuleDetails;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.nest_scroll_view)
    MyScrollView mNestScrollView;
    @BindView(R.id.hintBtn)
    TextView mHintBtn;
    @BindView(R.id.money)
    TextView mMoney;
    @BindView(R.id.payBtn)
    TextView mPayBtn;
    @BindView(R.id.back_return)
    RelativeLayout mBackReturn;
    @BindView(R.id.title_bar_rl)
    RelativeLayout mTitleBarRl;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        setBaseBarHide();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.time, R.id.payBtn, R.id.back_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.time:
              //  RMCheckOutOrderListBean
                initTimePickView();
                break;
            case R.id.payBtn:
                break;
            case R.id.back_return:
                closeActivity();
                break;
        }
    }

    private void initTimePickView() {
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                String format = sdf.format(date);
               // monthTime = DateUtils.getMonthTime("" + format.split("-")[0] + "-" + format.split("-")[1] + "-01 00:00:00");
                mTime.setText(format.split("-")[0] + "年" + format.split("-")[1] + "月");
            }
        }).setType(new boolean[]{true, true, false, false, false, false})// 默认全部显示
                .setLabel("年     ","月     ","","","","")
                .setCancelColor(Color.parseColor("#FE0233"))
                .setSubmitColor(Color.parseColor("#FE0233"))
                .setSubmitText("完成")
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }
}
