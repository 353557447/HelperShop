package com.shuiwangzhijia.shuidian.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.ShopCommentAdapter;
import com.shuiwangzhijia.shuidian.base.BaseFragment;
import com.shuiwangzhijia.shuidian.bean.CommentBean;
import com.shuiwangzhijia.shuidian.event.CommentEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 商家评论
 * created by wangsuli on 2018/8/20.
 */
public class ShopCommentFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rlEmpty)
    RelativeLayout rlEmpty;
    private Unbinder unbinder;
    private ShopCommentAdapter adapter;
    private LinearLayoutManager layoutManager;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_shop_comment, container, false);
            unbinder = ButterKnife.bind(this, mRootView);
            initRecyclerView();
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            unbinder = ButterKnife.bind(this, mRootView);
        }
        onRefresh();
        return mRootView;
    }


    private void getCommentData() {
        RetrofitUtils.getInstances().create().getCommentList(page, pageSize).enqueue(new Callback<EntityObject<ArrayList<CommentBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<CommentBean>>> call, Response<EntityObject<ArrayList<CommentBean>>> response) {
                hintLoad();
                completeSwipeRefresh();
                EntityObject<ArrayList<CommentBean>> body = response.body();
                if (body.getCode() == 200) {
                    ArrayList<CommentBean> result = body.getResult();
                    if (result == null || result.size() == 0) {
                        if (page == 0) {
                            swipeRefreshLayout.setVisibility(View.GONE);
                            rlEmpty.setVisibility(View.VISIBLE);
                        }
                        return;
                    }
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    rlEmpty.setVisibility(View.GONE);
                    if (page == 0) {
                        adapter.setData(result);
                    } else {
                        adapter.addData(result);
                    }
                    if (result.size() < pageSize) {
                        loading = false;
                    } else {
                        loading = true;
                    }
                    EventBus.getDefault().post(new CommentEvent(adapter.getItemCount()));
                } else {
                    if (page == 0) {
                        swipeRefreshLayout.setVisibility(View.GONE);
                        rlEmpty.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<CommentBean>>> call, Throwable t) {
                Log.i("json", "onFailure: " + t.getMessage());
                swipeRefreshLayout.setVisibility(View.GONE);
                rlEmpty.setVisibility(View.VISIBLE);
            }
        });
    }


    private void initRecyclerView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.color_30adfd));
        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.divider_bg));
        mRecyclerView.addItemDecoration(divider);
        adapter = new ShopCommentAdapter(getContext());
        mRecyclerView.setAdapter(adapter);
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
                        showLoad();
                        new LoadDataThread().start();
                    }
                }
            }
        });
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



    /**
     * 模拟加载数据的线程
     */
    class LoadDataThread extends Thread {
        @Override
        public void run() {
            page = page + 1;
            getCommentData();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0x101);//通过handler发送一个更新数据的标记
        }
    }

}
