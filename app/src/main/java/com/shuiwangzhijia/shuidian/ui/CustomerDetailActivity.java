package com.shuiwangzhijia.shuidian.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.CustomerDetailAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.CustomerDetailBean;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 客户明细
 * created by wangsuli on 2018/8/20.
 */
public class CustomerDetailActivity extends BaseAct implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rlEmpty)
    RelativeLayout rlEmpty;
    private CustomerDetailAdapter mConsumerDetailAdapter;
    private LinearLayoutManager layoutManager;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        ButterKnife.bind(this);
        setTitle("客户明细");
        id = getIntent().getIntExtra("id", -1);
        initRecyclerView();
    }

    private void getData() {
        RetrofitUtils.getInstances().create().getCustomerDetailList(id, page, PageSize).enqueue(new Callback<EntityObject<ArrayList<CustomerDetailBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<CustomerDetailBean>>> call, Response<EntityObject<ArrayList<CustomerDetailBean>>> response) {
                completeSwipeRefresh();
                hintLoad();
                EntityObject<ArrayList<CustomerDetailBean>> body = response.body();
                if (body.getCode() == 200) {
                    ArrayList<CustomerDetailBean> result = body.getResult();
                    if (result == null || result.size() == 0) {
                        rlEmpty.setVisibility(View.VISIBLE);
                        mSwipeRefreshLayout.setVisibility(View.GONE);
                        return;
                    }
                    rlEmpty.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                    if (page == 0) {
                        mConsumerDetailAdapter.setData(result);
                    } else {
                        mConsumerDetailAdapter.addData(result);
                    }
                    if (result.size() < PageSize) {
                        loading = false;
                    } else {
                        loading = true;
                    }
                } else {
                    if (page == 0) {
                        rlEmpty.setVisibility(View.VISIBLE);
                        mSwipeRefreshLayout.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<CustomerDetailBean>>> call, Throwable t) {
                Log.i("json", t.getMessage());
                rlEmpty.setVisibility(View.VISIBLE);
                mSwipeRefreshLayout.setVisibility(View.GONE);
            }
        });
    }

    private void initRecyclerView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_30adfd));
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.divider_line));
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("test", "StateChanged = " + newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("test", "onScrolled");
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (dy > 0 && lastVisibleItemPosition + 1 == layoutManager.getItemCount()) {
                    if (isLoading) {
                        isLoading = false;
                        showLoad();
                        new LoadDataThread().start();
                    }
                }
            }
        });
        mConsumerDetailAdapter = new CustomerDetailAdapter(this);
        mRecyclerView.setAdapter(mConsumerDetailAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        page = -1;
        showLoad();
        new LoadDataThread().start();
    }

    private void completeSwipeRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 模拟加载数据的线程
     */
    class LoadDataThread extends Thread {
        @Override
        public void run() {
            page++;
            getData();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0x101);//通过handler发送一个更新数据的标记
        }
    }

    private boolean isLoading = false;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x101:
                    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                        completeSwipeRefresh();
                    }

                    break;
            }
        }
    };
}
