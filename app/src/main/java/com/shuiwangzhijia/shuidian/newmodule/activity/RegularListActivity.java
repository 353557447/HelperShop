package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

import com.androidkun.xtablayout.XTabLayout;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;

import butterknife.BindView;
import butterknife.OnClick;

@FndViewInject(contentViewId = R.layout.activity_regular_list)
public class RegularListActivity extends BaseActivity {
    @BindView(R.id.back_return)
    RelativeLayout mBackReturn;
    @BindView(R.id.tabLayout)
    XTabLayout mTabLayout;
    @BindView(R.id.vp)
    ViewPager mVp;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        setBaseBarHide();
        //初始化mTab
        initTab();
        //初始化mVp
        initVp();
        //关联mTab和mVp
      //  mTab.setupWithViewPager(mVp);
    }

    private void initTab() {
       /* mTab.setxTabDisplayNum(6);
        mTab.addTab(mTab.newTab().setText("全部"), true);
        mTab.addTab(mTab.newTab().setText("单笔"), false);
        mTab.addTab(mTab.newTab().setText("月度"), false);
        mTab.addTab(mTab.newTab().setText("季度"), false);
        mTab.addTab(mTab.newTab().setText("年度"), false);
        mTab.addTab(mTab.newTab().setText("其它周期"), false);*/
    }

    private void initVp() {
       /* ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(getFragment(0));
        fragmentList.add(getFragment(1));
        fragmentList.add(getFragment(2));
        fragmentList.add(getFragment(3));
        fragmentList.add(getFragment(4));
        fragmentList.add(getFragment(5));
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("全部");
        titleList.add("单笔");
        titleList.add("月度");
        titleList.add("季度");
        titleList.add("年度");
        titleList.add("其它周期");
        FragmentManager fm = getSupportFragmentManager();
        mVp.setAdapter(new BaseFragmentPagerAdapter(fm, fragmentList, titleList));*/
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick(R.id.back_return)
    public void onViewClicked() {
        closeActivity();
    }


 /*   public Fragment getFragment(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("did", mDid);
        MyReturnMoneyFragment fragment = new MyReturnMoneyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }*/
}
