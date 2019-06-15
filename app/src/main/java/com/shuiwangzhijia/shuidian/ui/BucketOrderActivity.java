package com.shuiwangzhijia.shuidian.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.BaseFmAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.fragment.BucketBaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 空桶订单
 * created by wangsuli on 2018/10/17.
 */
public class BucketOrderActivity extends BaseAct {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<Fragment> pageList = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private BaseFmAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_order);
        ButterKnife.bind(this);
        setTitle("空桶订单");
        titles = Arrays.asList(new String[]{"待处理", "已完成"});
        for (int i = 0; i < titles.size(); i++) {
            pageList.add(setFragment(i));
        }
        adapter = new BaseFmAdapter(getSupportFragmentManager(), pageList, titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    private Fragment setFragment(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        BucketBaseFragment orderBaseFragment = new BucketBaseFragment();
        orderBaseFragment.setArguments(bundle);
        return orderBaseFragment;
    }
}
