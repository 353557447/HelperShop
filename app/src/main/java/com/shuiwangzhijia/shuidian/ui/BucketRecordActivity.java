package com.shuiwangzhijia.shuidian.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.BaseFmAdapter;
import com.shuiwangzhijia.shuidian.adapter.ShopMarketItemAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.BucketRecordData;
import com.shuiwangzhijia.shuidian.bean.ShopBean;
import com.shuiwangzhijia.shuidian.bean.ShopMarketBean;
import com.shuiwangzhijia.shuidian.event.BucketListFlashEvent;
import com.shuiwangzhijia.shuidian.event.LoginOutEvent;
import com.shuiwangzhijia.shuidian.fragment.BucketRecordBaseFragment;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BucketRecordActivity extends BaseAct {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.recordBtn)
    TextView recordBtn;
    @BindView(R.id.tabLayout)
    XTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.selectShop)
    TextView selectShop;
    @BindView(R.id.rlSelectShop)
    RelativeLayout rlSelectShop;
    private LinearLayoutManager layoutManager;
    private ShopMarketItemAdapter mShopMarketItemAdapter;
    private List<String> titles = new ArrayList<>();
    private ArrayList<Object> pageList = new ArrayList<>();
    private BaseFmAdapter adapter;
    private BucketRecordData bucketRecordData;
    private List<ShopMarketBean> data = new ArrayList<>();
    private int did;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_record);
        ButterKnife.bind(this);
        initRecyclerView();
        setTitle("空桶记录");
        adapter = new BaseFmAdapter(getSupportFragmentManager(), pageList, titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        getData();
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(ShopBean event) {
        did = event.getId();
        getData();
    }

    private void getData() {
        RetrofitUtils.getInstances().create().getEmptyBucketRecord(did).enqueue(new Callback<EntityObject<BucketRecordData>>() {
            @Override
            public void onResponse(Call<EntityObject<BucketRecordData>> call, Response<EntityObject<BucketRecordData>> response) {
                EntityObject<BucketRecordData> body = response.body();
                if (body.getCode() == 200) {
                    bucketRecordData = body.getResult();
                    ShopMarketBean bean = null;
                    String title = "欠桶数量";
                    String num = "5";
                    data.clear();
                    for (int i = 0; i < 5; i++) {
                        bean = new ShopMarketBean();
                        switch (i) {
                            case 0:
                                title = "欠桶数量";
                                num = bucketRecordData.getOwe();
                                break;
                            case 1:
                                title = "存桶数量";
                                num = bucketRecordData.getSave();
                                break;
                            case 2:
                                title = "买桶数量";
                                num = bucketRecordData.getBuy();
                                break;
                            case 3:
                                title = "押桶数量";
                                num = bucketRecordData.getBet();
                                break;
                            case 4:
                                title = "回桶数量";
                                num = bucketRecordData.getBack();
                                break;
                        }
                        bean.setTitle(title);
                        bean.setNum(num);
                        data.add(bean);
                    }
                    mShopMarketItemAdapter.setData(data);
                    selectShop.setText(bucketRecordData.getSname());
                    did = bucketRecordData.getId();
                    EventBus.getDefault().post(new BucketListFlashEvent(did, 0));
                    initViewPager();

                } else if (body.getScode() == -200) {
                    EventBus.getDefault().post(new LoginOutEvent());
                }
            }

            @Override
            public void onFailure(Call<EntityObject<BucketRecordData>> call, Throwable t) {

            }
        });
    }

    private void initViewPager() {
        pageList.clear();
        titles = Arrays.asList(getResources().getStringArray(R.array.bucketTypeTitle));
        for (int i = 0; i < titles.size(); i++) {
            pageList.add(setFragment(i));
        }
        adapter.setList(pageList, titles);
    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,5));
        mRecyclerView.setHasFixedSize(true);
        mShopMarketItemAdapter = new ShopMarketItemAdapter(this, true);
        mRecyclerView.setAdapter(mShopMarketItemAdapter);

    }

    private Fragment setFragment(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("did", did);
        BucketRecordBaseFragment orderBaseFragment = new BucketRecordBaseFragment();
        orderBaseFragment.setArguments(bundle);
        return orderBaseFragment;
    }


    @OnClick({R.id.selectShop, R.id.recordBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.selectShop:
                startActivity(new Intent(this, SelectWaterPlantActivity.class));
                break;
            case R.id.recordBtn:
                if (bucketRecordData != null) {
                    BucketStoreOweActivity.startAct(this, did, bucketRecordData.getSname());
                }
                break;
        }
    }
}
