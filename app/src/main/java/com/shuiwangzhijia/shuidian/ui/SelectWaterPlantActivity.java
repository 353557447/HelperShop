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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.WaterPlantAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.ShopBean;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 选择水厂
 * created by wangsuli on 2018/10/11.
 */
public class SelectWaterPlantActivity extends BaseAct implements SwipeRefreshLayout.OnRefreshListener, WaterPlantAdapter.OnRecyclerViewItemClickListener {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.rlEmpty)
    RelativeLayout rlEmpty;
    private LinearLayoutManager layoutManager;
    private WaterPlantAdapter mWaterPlantAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_water_plant);
        setTitle("选择水厂");
        ButterKnife.bind(this);
        initRecyclerView();
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
        mWaterPlantAdapter = new WaterPlantAdapter(this);
        mWaterPlantAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mWaterPlantAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (dy > 0 && lastVisibleItemPosition + 1 == layoutManager.getItemCount()) {
                    if (loading) {
                        loading = false;
                        new LoadDataThread().start();
                    }
                }
            }
        });


    }

    private void getList() {
        RetrofitUtils.getInstances().create().getWaterPlantList(page, PageSize).enqueue(new Callback<EntityObject<ArrayList<ShopBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<ShopBean>>> call, Response<EntityObject<ArrayList<ShopBean>>> response) {
                completeSwipeRefresh();
                hintLoad();
                EntityObject<ArrayList<ShopBean>> body = response.body();
                if (body.getCode() == 200) {
                    ArrayList<ShopBean> result = body.getResult();
                    if (result == null) {
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
                            mWaterPlantAdapter.setData(result);
                        } else {
                            mWaterPlantAdapter.addData(result);
                        }

                    }

                } else {
                    if (page == 0) {
                        rlEmpty.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setVisibility(View.GONE);
                    }
                    loading = false;
                }

            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<ShopBean>>> call, Throwable t) {
                rlEmpty.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.GONE);
                Log.i("json", t.getMessage());
            }
        });
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
        if (swipeRefreshLayout == null)
            return;
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onItemClick(int position) {
        ShopBean item = mWaterPlantAdapter.getItem(position);
        EventBus.getDefault().post(item);
        finish();
    }


    /**
     * 模拟加载数据的线程
     */
    class LoadDataThread extends Thread {
        @Override
        public void run() {
            page++;
            getList();
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
