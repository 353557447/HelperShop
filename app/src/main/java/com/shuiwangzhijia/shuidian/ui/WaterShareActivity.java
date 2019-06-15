package com.shuiwangzhijia.shuidian.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

import com.androidkun.xtablayout.XTabLayout;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.BaseFmAdapter;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.fragment.shopPersonalityFragment;
import com.shuiwangzhijia.shuidian.fragment.shopQrcodeFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@FndViewInject(contentViewId = R.layout.activity_water_share)
public class WaterShareActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    XTabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.back_return)
    RelativeLayout mBackReturn;
    private List<String> titles;
    private ArrayList<Object> pageList;
    private BaseFmAdapter adapter;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, WaterShareActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void beforeSetContentView() {

    }

    protected void initView() {
        setBaseBarHide();
        titles = Arrays.asList(new String[]{"店铺二维码分享", "店铺个性分享"});
        pageList = new ArrayList<>();
        pageList.add(new shopQrcodeFragment());
        pageList.add(new shopPersonalityFragment());
        adapter = new BaseFmAdapter(getSupportFragmentManager(), pageList, titles);
        mViewpager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewpager);
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
}
