package com.shuiwangzhijia.shuidian.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.androidkun.xtablayout.XTabLayout;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.BaseFragmentPagerAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.fragment.MycouFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCouActivity extends BaseAct {
    @BindView(R.id.coupons_xTablayout)
    XTabLayout mCouponsXTablayout;
    @BindView(R.id.coupons_vp)
    ViewPager mCouponsVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cou);
        ButterKnife.bind(this);
        setTitle("我的优惠券");
        //初始化mTab
        initTab();
        //初始化mVp
        initVp();
        //关联mTab和mVp
        mCouponsXTablayout.setupWithViewPager(mCouponsVp);
//        mMyOrderVp.setCurrentItem(checkedPosition);
    }

    private void initTab() {
        mCouponsXTablayout.setxTabDisplayNum(3);
        mCouponsXTablayout.addTab(mCouponsXTablayout.newTab().setText("未使用"), true);
        mCouponsXTablayout.addTab(mCouponsXTablayout.newTab().setText("已使用"), false);
        mCouponsXTablayout.addTab(mCouponsXTablayout.newTab().setText("已失效"), false);
    }

    private void initVp() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(setFragment(1));
        fragmentList.add(setFragment(2));
        fragmentList.add(setFragment(3));
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("未使用");
        titleList.add("已使用");
        titleList.add("已失效");
        FragmentManager fm = getSupportFragmentManager();
        mCouponsVp.setAdapter(new BaseFragmentPagerAdapter(fm, fragmentList, titleList));
    }
    private Fragment setFragment(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        MycouFragment mycouFragment = new MycouFragment();
        mycouFragment.setArguments(bundle);
        return mycouFragment;
    }
}
