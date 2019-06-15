package com.shuiwangzhijia.shuidian.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.BaseFragmentPagerAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.fragment.PurchaseOrderFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PurchaseOrderActivity extends BaseAct{

    @BindView(R.id.my_order_xTablayout)
    XTabLayout mMyOrderXTablayout;
    @BindView(R.id.my_order_vp)
    ViewPager mMyOrderVp;

    public static void statAct(Context context, int type) {
        Intent intent = new Intent(context, PurchaseOrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_order);
        ButterKnife.bind(this);
        int checkedPosition = 0;
        if (getIntent().getExtras() != null){
            checkedPosition=getIntent().getExtras().getInt("type");
        }
        setTitle("采购订单");
        //初始化mTab
        initTab();
        //初始化mVp
        initVp();
        //关联mTab和mVp
        mMyOrderXTablayout.setupWithViewPager(mMyOrderVp);
        mMyOrderVp.setCurrentItem(checkedPosition);
    }


    private void initTab() {
        mMyOrderXTablayout.setxTabDisplayNum(6);
        mMyOrderXTablayout.addTab(mMyOrderXTablayout.newTab().setText("待付款"), true);
        mMyOrderXTablayout.addTab(mMyOrderXTablayout.newTab().setText("待配送"), false);
        mMyOrderXTablayout.addTab(mMyOrderXTablayout.newTab().setText("配送中"), false);
        mMyOrderXTablayout.addTab(mMyOrderXTablayout.newTab().setText("已完成"), false);
        mMyOrderXTablayout.addTab(mMyOrderXTablayout.newTab().setText("欠款中"), false);
        mMyOrderXTablayout.addTab(mMyOrderXTablayout.newTab().setText("待自提"), false);
    }

    private void initVp() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(setFragment(1));
        fragmentList.add(setFragment(2));
        fragmentList.add(setFragment(3));
        fragmentList.add(setFragment(4));
        fragmentList.add(setFragment(5));
        fragmentList.add(setFragment(6));
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("待付款");
        titleList.add("待配送");
        titleList.add("配送中");
        titleList.add("已完成");
        titleList.add("欠款中");
        titleList.add("待自提");
        FragmentManager fm = getSupportFragmentManager();
        mMyOrderVp.setAdapter(new BaseFragmentPagerAdapter(fm, fragmentList, titleList));
    }

    private Fragment setFragment(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        PurchaseOrderFragment orderBaseFragment = new PurchaseOrderFragment();
        orderBaseFragment.setArguments(bundle);
        return orderBaseFragment;
    }

















}
