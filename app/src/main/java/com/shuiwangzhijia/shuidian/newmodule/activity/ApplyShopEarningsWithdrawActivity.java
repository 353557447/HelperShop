package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.BaseFragmentPagerAdapter;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.newmodule.fragment.SpEarnFreezingFragment;
import com.shuiwangzhijia.shuidian.newmodule.fragment.SpEarnWithDrawableFragment;
import com.shuiwangzhijia.shuidian.newmodule.fragment.SpEarnWithDrawingFragment;
import com.shuiwangzhijia.shuidian.view.BaseBar;

import java.util.ArrayList;

import butterknife.BindView;

@FndViewInject(contentViewId = R.layout.activity_apply_shop_earnings_withdraw, title = "申请提现", barRightTv = "提现记录")
public class ApplyShopEarningsWithdrawActivity extends BaseActivity {
    @BindView(R.id.tab)
    XTabLayout mTab;
    @BindView(R.id.vp)
    ViewPager mVp;


    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        mBaseBar.setRightTvColor("#2995F1");
        mBaseBar.setBarListener(new BaseBar.BarListener() {
            @Override
            public void onLeftClick() {
                closeActivity();
            }

            @Override
            public void onRightClick() {
                skipActivity(ShopEarningsWithdrawRecordActivity.class);
            }
        });

        //初始化mTab
        initTab();
        //初始化mVp
        initVp();
        //关联mTab和mVp
        mTab.setupWithViewPager(mVp);
    }

    private void initTab() {
        mTab.setxTabDisplayNum(3);
        mTab.addTab(mTab.newTab().setText("可提现收益"), true);
        mTab.addTab(mTab.newTab().setText("冻结中收益"), false);
        mTab.addTab(mTab.newTab().setText("提现中收益"), false);
    }


    private void initVp() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new SpEarnWithDrawableFragment());
        fragmentList.add(new SpEarnFreezingFragment());
        fragmentList.add(new SpEarnWithDrawingFragment());
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("可提现收益");
        titleList.add("冻结中收益");
        titleList.add("提现中收益");
        FragmentManager fm = getSupportFragmentManager();
        mVp.setAdapter(new BaseFragmentPagerAdapter(fm, fragmentList, titleList));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }
}
