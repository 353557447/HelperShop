package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.view.MyScrollView;

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
                break;
            case R.id.payBtn:
                break;
            case R.id.back_return:
                closeActivity();
                break;
        }
    }
}
