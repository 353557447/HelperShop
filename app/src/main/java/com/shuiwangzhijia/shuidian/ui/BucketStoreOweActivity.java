package com.shuiwangzhijia.shuidian.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.BaseFmAdapter;
import com.shuiwangzhijia.shuidian.adapter.SaveOweAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.BucketSaveData;
import com.shuiwangzhijia.shuidian.bean.SaveOweBean;
import com.shuiwangzhijia.shuidian.fragment.BucketRecordBaseFragment;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.GridDividerItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BucketStoreOweActivity extends BaseAct {
    @BindView(R.id.name)
    TextView nameTv;
    @BindView(R.id.tabLayout)
    XTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    private List<String> titles = new ArrayList<>();
    private ArrayList<Object> pageList = new ArrayList<>();
    private BaseFmAdapter adapter;
    private String name;
    private int did;

    public static void startAct(Context context, int did, String name) {
        Intent intent = new Intent(context, BucketStoreOweActivity.class);
        intent.putExtra("did", did);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_store_owe);
        ButterKnife.bind(this);
        name = getIntent().getStringExtra("name");
        did = getIntent().getIntExtra("did", -1);
        if (name != null){
            nameTv.setText("水厂名称：" + name);
        }
        initViewPager();
        setTitle("存欠记录");
        getList();
    }

    private void getList() {
        RetrofitUtils.getInstances().create().saveOwnDetail(did).enqueue(new Callback<EntityObject<ArrayList<SaveOweBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<SaveOweBean>>> call, Response<EntityObject<ArrayList<SaveOweBean>>> response) {
                EntityObject<ArrayList<SaveOweBean>> body = response.body();
                if (body.getCode() == 200){
                    ArrayList<SaveOweBean> result = body.getResult();
                    initRecycle(result);
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<SaveOweBean>>> call, Throwable t) {

            }
        });
    }

    private void initRecycle(ArrayList<SaveOweBean> result) {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new GridDividerItemDecoration(1,getResources().getColor(R.color.color_e5e5e5)));
        SaveOweAdapter adapter = new SaveOweAdapter(result,this);
        mRecyclerView.setAdapter(adapter);
    }

    private void initViewPager() {
        titles = Arrays.asList(new String[]{"全部", "存桶数量", "欠桶数量"});
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
        bundle.putInt("did", did);
        bundle.putBoolean("fromStoreOwe", true);
        BucketRecordBaseFragment orderBaseFragment = new BucketRecordBaseFragment();
        orderBaseFragment.setArguments(bundle);
        return orderBaseFragment;
    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(BucketSaveData bean) {
        titles.set(0,"全部("+bean.getAll_sum()+")");
        titles.set(1,"存桶数量("+bean.getSave_sum()+")");
        titles.set(2,"欠桶数量("+bean.getOwe_sum()+")");
        adapter.setTitles(titles);
    }
}
