package com.shuiwangzhijia.shuidian.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.MycouAdapter;
import com.shuiwangzhijia.shuidian.base.BaseFragment;
import com.shuiwangzhijia.shuidian.bean.MycouBean;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.socks.library.KLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MycouFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    protected View mRootView;
    Unbinder unbinder;
    @BindView(R.id.mRecyclerView)
    RecyclerView mMRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.rlEmpty)
    RelativeLayout mRlEmpty;
    private LinearLayoutManager layoutManager;
    private MycouAdapter mAdapter;
    private int type;
    private boolean isInit;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_my_cou, container, false);
            unbinder = ButterKnife.bind(this, mRootView);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            unbinder = ButterKnife.bind(this, mRootView);
        }
        initRecycleView();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        isInit = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            type = getArguments().getInt("type", 0);
            onRefresh();
            KLog.d(type);
        }
    }

    private void completeSwipeRefresh() {
        if (mSwipeRefreshLayout == null)
            return;
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void getList(){
        RetrofitUtils.getInstances().create().myCouponList(type,page,pageSize).enqueue(new Callback<EntityObject<MycouBean>>() {
            @Override
            public void onResponse(Call<EntityObject<MycouBean>> call, Response<EntityObject<MycouBean>> response) {
                hintLoad();
                EntityObject<MycouBean> body = response.body();
                if(body==null){
                    return;
                }
                if (body.getCode() == 200 ){
                    MycouBean result = body.getResult();
                    if(result==null)
                        return;
                    List<MycouBean.ListBean> list = result.getList();
                    if (list == null || (page == 0 && list.size() == 0)) {
                        mRlEmpty.setVisibility(View.VISIBLE);
                        mSwipeRefreshLayout.setVisibility(View.GONE);
                        return;
                    } else {
                        mRlEmpty.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                        if (list.size() < pageSize) {
                            loading = false;
                        } else {
                            loading = true;
                        }
                        mAdapter.setType(type);
                        if (page == 0) {
                            mAdapter.setData(list);
//                            adapter.setData(result);
                        } else {
                            mAdapter.addData(list);
//                            adapter.addData(result);
                        }
                    }

                }else {
                    KLog.d(page);
                    if (page == 0) {
                        mRlEmpty.setVisibility(View.VISIBLE);
                        mSwipeRefreshLayout.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<EntityObject<MycouBean>> call, Throwable t) {
                hintLoad();
                KLog.d(t.getMessage());
                if (page == 0) {
                    mRlEmpty.setVisibility(View.VISIBLE);
                    mSwipeRefreshLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initRecycleView() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(mContext);
        mMRecyclerView.setLayoutManager(layoutManager);
        mMRecyclerView.setHasFixedSize(true);
        if (mMRecyclerView.getItemDecorationCount() == 0){
            DividerItemDecoration divider = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
            divider.setDrawable(getResources().getDrawable(R.drawable.divider_bg_tran));
            mMRecyclerView.addItemDecoration(divider);
        }
        mAdapter = new MycouAdapter(mContext);
        mMRecyclerView.setAdapter(mAdapter);
        mMRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    @Override
    public void onRefresh() {
        page = -1;
        if (isInit){
            showLoad();
        }
        new LoadDataThread().start();
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
}
