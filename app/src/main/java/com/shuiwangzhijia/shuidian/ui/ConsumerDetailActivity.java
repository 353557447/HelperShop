package com.shuiwangzhijia.shuidian.ui;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.ConsumerDetailAdapter;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.ConsumerDetailBean;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.view.BaseBar;
import com.socks.library.KLog;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_consumer_detail, title = "收益明细")
public class ConsumerDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private ConsumerDetailAdapter mConsumerDetailAdapter;
    private LinearLayoutManager layoutManager;
    public int PageSize = 10;
    public boolean loading;
    public int page = 0;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        mBaseBar.setBarListener(new BaseBar.BarListener() {
            @Override
            public void onLeftClick() {
                closeActivity();
            }

            @Override
            public void onRightClick() {

            }
        });
        initRecyclerView();
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        getData();
    }

    @Override
    protected void initEvent() {

    }

    private void getData() {
        RetrofitUtils.getInstances().create().getConsumerDetailList(page, PageSize).enqueue(new Callback<EntityObject<ArrayList<ConsumerDetailBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<ConsumerDetailBean>>> call, Response<EntityObject<ArrayList<ConsumerDetailBean>>> response) {
                KLog.e("thread:" + Thread.currentThread().getName());
                completeSwipeRefresh();
                dismissLoadingDialog();
                EntityObject<ArrayList<ConsumerDetailBean>> body = response.body();
                if (body.getCode() == 200) {
                    ArrayList<ConsumerDetailBean> result = body.getResult();
                    if (result == null || result.size() == 0) {
                        if (page == 0) {
                            setCentreViewShow(R.drawable.no_consume_data, "您还没有消费明细~");
                        }
                        return;
                    } else {
                        setCentreViewDismiss();
                    }
                    if (result.size() < PageSize) {
                        loading = false;
                    } else {
                        loading = true;
                    }
                    if (page == 0) {
                        mConsumerDetailAdapter.setData(result);
                    } else {
                        mConsumerDetailAdapter.addData(result);
                    }
                } else if (body.getCode() == -300) {
                    setCentreViewShow(R.drawable.no_consume_data, "您还没有收益明细~");
                } else {
                    showErrorToast();
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<ConsumerDetailBean>>> call, Throwable t) {
                Log.i("json", t.getMessage());

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
                        showLoadingDialog();
                        new LoadDataThread().start();
                    }
                }
            }
        });
        mConsumerDetailAdapter = new ConsumerDetailAdapter(this);
        mRecyclerView.setAdapter(mConsumerDetailAdapter);
    }

    @Override
    public void onRefresh() {
        page = -1;
        showLoadingDialog();
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
