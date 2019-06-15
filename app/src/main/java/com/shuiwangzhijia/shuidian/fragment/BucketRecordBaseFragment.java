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
import com.shuiwangzhijia.shuidian.adapter.BucketRecordAdapter;
import com.shuiwangzhijia.shuidian.base.BaseFragment;
import com.shuiwangzhijia.shuidian.bean.BucketRecordBean;
import com.shuiwangzhijia.shuidian.bean.BucketSaveData;
import com.shuiwangzhijia.shuidian.event.BucketListFlashEvent;
import com.shuiwangzhijia.shuidian.event.LoginOutEvent;
import com.shuiwangzhijia.shuidian.event.PayFinishEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.ui.BucketRecordDetailActivity;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 空桶记录 基础类
 * created by wangsuli on 2018/8/20.
 */
public class BucketRecordBaseFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BucketRecordAdapter.OnRecyclerViewItemClickListener {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rlEmpty)
    RelativeLayout rlEmpty;
    private Unbinder unbinder;
    private int type = 0, did;
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
    private LinearLayoutManager layoutManager;
    private BucketRecordAdapter mBucketRecordAdapter;
    private boolean fromStoreOwe = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        type = getArguments().getInt("type");
        did = getArguments().getInt("did");
        fromStoreOwe = getArguments().getBoolean("fromStoreOwe");
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_bucket_base, container, false);
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
        return mRootView;
    }


    private void getList() {
        if (fromStoreOwe) {
            RetrofitUtils.getInstances().create().getEmptyBucketSaveList(did, type, page, pageSize).enqueue(new Callback<EntityObject<BucketSaveData>>() {
                @Override
                public void onResponse(Call<EntityObject<BucketSaveData>> call, Response<EntityObject<BucketSaveData>> response) {
                    completeSwipeRefresh();
                    hintLoad();
                    EntityObject<BucketSaveData> body = response.body();
                    if (body.getCode() == 200) {
                        BucketSaveData result = body.getResult();
                        if (result == null) {
                            rlEmpty.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setVisibility(View.GONE);
                            return;
                        } else {
//                            List<BucketRecordBean> list = result.getList();
                            List<BucketRecordBean> list = result.getGoods();
                            rlEmpty.setVisibility(View.GONE);
                            swipeRefreshLayout.setVisibility(View.VISIBLE);
                            if (page == 0) {
                                EventBus.getDefault().post(result);
                                mBucketRecordAdapter.setData(list);
                            } else {
                                mBucketRecordAdapter.addData(list);
                            }
                            loading = list.size() < pageSize ? false : true;
                        }
                    } else {
                        if (page == 0) {
                            rlEmpty.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setVisibility(View.GONE);
                        }
                        if (body.getScode() == -200) {
                            EventBus.getDefault().post(new LoginOutEvent());
                        }
                    }
                }

                @Override
                public void onFailure(Call<EntityObject<BucketSaveData>> call, Throwable t) {

                }
            });

        } else {
            RetrofitUtils.getInstances().create().getEmptyBucketRecordList(did, type, page, pageSize).enqueue(new Callback<EntityObject<ArrayList<BucketRecordBean>>>() {
                @Override
                public void onResponse(Call<EntityObject<ArrayList<BucketRecordBean>>> call, Response<EntityObject<ArrayList<BucketRecordBean>>> response) {
                    completeSwipeRefresh();
                    hintLoad();
                    EntityObject<ArrayList<BucketRecordBean>> body = response.body();
                    if (body.getCode() == 200) {
                        ArrayList<BucketRecordBean> result = body.getResult();
                        if (result == null || result.size() == 0) {
                            rlEmpty.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setVisibility(View.GONE);
                            return;
                        } else {
                            rlEmpty.setVisibility(View.GONE);
                            swipeRefreshLayout.setVisibility(View.VISIBLE);
                            if (page == 0) {
                                mBucketRecordAdapter.setData(result);
                            } else {
                                mBucketRecordAdapter.addData(result);
                            }
                            loading = result.size() < pageSize ? false : true;
                        }
                    } else {
                        if (page == 0) {
                            rlEmpty.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setVisibility(View.GONE);
                        }
                        if (body.getScode() == -200) {
                            EventBus.getDefault().post(new LoginOutEvent());
                        }
                    }

                }

                @Override
                public void onFailure(Call<EntityObject<ArrayList<BucketRecordBean>>> call, Throwable t) {
                    Log.i("json 异常", t.getMessage());
                }
            });
        }
    }

    private void initRecyclerView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.color_30adfd));
        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(getActivity().getResources().getDrawable(R.drawable.divider_bg));
        mRecyclerView.addItemDecoration(divider);
        mBucketRecordAdapter = new BucketRecordAdapter(getActivity(), fromStoreOwe);
        mBucketRecordAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mBucketRecordAdapter);
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
                        showLoad();
                        new LoadDataThread().start();
                    }
                }
            }
        });
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
        if (!fromStoreOwe) {
            BucketRecordBean item = mBucketRecordAdapter.getItem(position);
            BucketRecordDetailActivity.startAtc(getContext(), item.getBid(), item.getType(), item.getStatus());
        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(BucketListFlashEvent event) {
        did = event.did;
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(PayFinishEvent event) {
        onRefresh();
    }

    private void hint(String text) {
        ToastUitl.showToastCustom(text);
    }
}
