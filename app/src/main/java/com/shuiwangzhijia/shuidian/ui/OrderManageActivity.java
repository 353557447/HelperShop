package com.shuiwangzhijia.shuidian.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.BaseFmAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.event.OrderListFlashEvent;
import com.shuiwangzhijia.shuidian.event.PayFinishEvent;
import com.shuiwangzhijia.shuidian.fragment.OrderBaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;


public class OrderManageActivity extends BaseAct implements ViewPager.OnPageChangeListener {
    @BindView(R.id.tabLayout)
    XTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private List<String> titles = new ArrayList<>();
    private ArrayList<Object> pageList = new ArrayList<>();
    private BaseFmAdapter adapter;
    private int index;

    public static void startAtc(Context context, int index) {
        Intent intent = new Intent(context, OrderManageActivity.class);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manage);
        ButterKnife.bind(this);
        index = getIntent().getIntExtra("index", -1);
        setTitle("店铺订单");
        titles = Arrays.asList(new String[]{"新订单", "待配送", "配送中", "已完成"});
        initData();
        initView();
    }

    public void initView() {
        adapter = new BaseFmAdapter(getSupportFragmentManager(), pageList, titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(index);
    }

    private void initData() {
        pageList = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            pageList.add(setFragment(i));
        }
    }

    private Fragment setFragment(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        OrderBaseFragment orderBaseFragment = new OrderBaseFragment();
        orderBaseFragment.setArguments(bundle);
        return orderBaseFragment;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        EventBus.getDefault().post(new OrderListFlashEvent(false, position));

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(PayFinishEvent event) {
        finish();
    }
}
