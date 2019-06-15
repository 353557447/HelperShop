package com.shuiwangzhijia.shuidian.ui;

import android.content.Intent;
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
import com.shuiwangzhijia.shuidian.adapter.CustomerManageAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.CustomerBean;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerManageActivity extends BaseAct implements SwipeRefreshLayout.OnRefreshListener, CustomerManageAdapter.OnViewItemClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rlEmpty)
    RelativeLayout rlEmpty;
    private CustomerManageAdapter mCustomerManageAdapter;
    private LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_manage);
        ButterKnife.bind(this);
        setTitle("客户管理");
        initRecyclerView();
    }

    private void getData() {
        RetrofitUtils.getInstances().create().getCustomerList(page, PageSize).enqueue(new Callback<EntityObject<ArrayList<CustomerBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<CustomerBean>>> call, Response<EntityObject<ArrayList<CustomerBean>>> response) {
                completeSwipeRefresh();
                hintLoad();
                EntityObject<ArrayList<CustomerBean>> body = response.body();
                if (body.getCode() == 200) {
                    ArrayList<CustomerBean> result = body.getResult();
                    if (result == null || result.size() == 0) {
                        loading = false;
                        rlEmpty.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setVisibility(View.GONE);
                        return;
                    } else {
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                        rlEmpty.setVisibility(View.GONE);
                        if (result.size() < PageSize) {
                            loading = false;
                        } else {
                            loading = true;
                        }
                        if (page == 0) {
                            mCustomerManageAdapter.setData(result);
                        } else {
                            mCustomerManageAdapter.addData(result);
                        }
                    }

                } else {
                    if (page == 0) {
                        rlEmpty.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<CustomerBean>>> call, Throwable t) {
                rlEmpty.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.GONE);
            }
        });
    }

    private void initRecyclerView() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.divider_bg));
        mRecyclerView.addItemDecoration(divider);
        mCustomerManageAdapter = new CustomerManageAdapter(this);
        mCustomerManageAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mCustomerManageAdapter);
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
                    if (loading) {
                        loading = false;
                        new LoadDataThread().start();
                    }
                }
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        });
    }


    @Override
    public void onRefresh() {
        showLoad();
        page = -1;
        new LoadDataThread().start();
    }

    private void completeSwipeRefresh() {
        if (swipeRefreshLayout == null)
            return;
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(int position) {
        Intent in=new Intent(this,CustomerDetailActivity.class);
        in.putExtra("id",mCustomerManageAdapter.getItem(position).getId());
        startActivity(in);
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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x101:
                    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                        completeSwipeRefresh();
                    }

                    break;
            }
        }
    };
}
