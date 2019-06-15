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
import com.shuiwangzhijia.shuidian.adapter.BaseFmAdapter;
import com.shuiwangzhijia.shuidian.adapter.BaseFragmentPagerAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.TicketCountBean;
import com.shuiwangzhijia.shuidian.fragment.TicketBaseFragment;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TicketActivity extends BaseAct {
    @BindView(R.id.tabLayout)
    XTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private BaseFmAdapter adapter;
    private List<String> titles = new ArrayList<>();
    private ArrayList<Fragment> pageList = new ArrayList<>();
    private TicketCountBean data;

    public static void startAtc(Context context, TicketCountBean data) {
        Intent intent = new Intent(context, TicketActivity.class);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        ButterKnife.bind(this);
        setTitle("我的水票");
        if (data == null) {
            getCount();
        } else {
            data = (TicketCountBean) getIntent().getSerializableExtra("data");
            initData();
        }
    }

    private void getCount() {
        RetrofitUtils.getInstances().create().showMyTicketsCount().enqueue(new Callback<EntityObject<TicketCountBean>>() {
            @Override
            public void onResponse(Call<EntityObject<TicketCountBean>> call, Response<EntityObject<TicketCountBean>> response) {
                EntityObject<TicketCountBean> body = response.body();
                if (body.getCode() == 200) {
                    data = body.getResult();
                    initData();
                }
            }

            @Override
            public void onFailure(Call<EntityObject<TicketCountBean>> call, Throwable t) {

            }
        });
    }

    private void initData() {
        //初始化mTab
        initTab();
        //初始化mVp
        initVp();
        //关联mTab和mVp
        tabLayout.setupWithViewPager(viewPager);

        /*titles.add("可使用(" + data.getUse() + ")");
        titles.add("已使用(" + data.getNuse() + ")");
        titles.add("已过期(" + data.getOverdue() + ")");
        TicketBaseFragment ticketBaseFragment = null;
        for (int i = 0; i < titles.size(); i++) {
            ticketBaseFragment = new TicketBaseFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i + 1);
            ticketBaseFragment.setArguments(bundle);
            pageList.add(ticketBaseFragment);
        }
        adapter = new BaseFmAdapter(getSupportFragmentManager(), pageList, titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);*/
    }

    private void initTab() {
        tabLayout.setxTabDisplayNum(3);
        tabLayout.addTab(tabLayout.newTab().setText("未使用("+data.getUse()+")"), true);
        tabLayout.addTab(tabLayout.newTab().setText("已使用("+data.getNuse()+")"), false);
        tabLayout.addTab(tabLayout.newTab().setText("已失效("+data.getOverdue()+")"), false);
    }

    private void initVp() {
        TicketBaseFragment ticketBaseFragment = null;
        ArrayList<String> titleList = new ArrayList<>();
//        titleList.add("未使用("+data.getUse()+")");
//        titleList.add("已使用("+data.getNuse()+")");
//        titleList.add("已失效("+data.getOverdue()+")");
        titleList.add("未使用");
        titleList.add("已使用");
        titleList.add("已失效");
        for (int i = 0; i < titleList.size(); i++) {
            ticketBaseFragment = new TicketBaseFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i + 1);
            ticketBaseFragment.setArguments(bundle);
            pageList.add(ticketBaseFragment);
        }
        FragmentManager fm = getSupportFragmentManager();
        viewPager.setAdapter(new BaseFragmentPagerAdapter(fm, pageList, titleList));
    }
}
