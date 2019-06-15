package com.shuiwangzhijia.shuidian.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.socks.library.KLog;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseLazyFragment;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.GoodsBean;
import com.shuiwangzhijia.shuidian.event.GoodsEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.FeaturedContentAdapter;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.MeasureUtil;
import com.shuiwangzhijia.shuidian.utils.WaterHelperUtils;
import com.shuiwangzhijia.shuidian.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.fragment_purchase_base)
public class PurchaseBaseFragment extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener, FeaturedContentAdapter.OnJoinShoppingCartClickListener {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rlEmpty)
    RelativeLayout rlEmpty;
    private Unbinder unbinder;
   // private PurchaseAdapter mPurchaseAdapter;
    private String type;
    private LinearLayoutManager layoutManager;
    private FeaturedContentAdapter mFeaturedContentAdapter;
    private List<GoodsBean> mFeatureGoodsList;

    @Override
    protected void initView(View view) {
        type = getArguments().getString("type");
        initRecyclerView();
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected void initEvent() {

    }

    private void initRecyclerView() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mFeatureGoodsList = new ArrayList<GoodsBean>();
      //  mPurchaseAdapter = new PurchaseAdapter(getActivity(), true);
        mFeaturedContentAdapter = new FeaturedContentAdapter(mContext);
        mFeaturedContentAdapter.setData(mFeatureGoodsList);
        mFeaturedContentAdapter.setOnItemClickListener(this);
      //  mPurchaseAdapter.setOnSaleClickListener(this);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(mContext, MeasureUtil.dip2px(mContext,12)));
        mRecyclerView.setAdapter(mFeaturedContentAdapter);
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

    private void getGoodsList() {
        RetrofitUtils.getInstances().create().getGoodsList(type, page, pageSize).enqueue(new Callback<EntityObject<ArrayList<GoodsBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<GoodsBean>>> call, Response<EntityObject<ArrayList<GoodsBean>>> response) {
                completeSwipeRefresh();
                dismissLoadingDialog();
                EntityObject<ArrayList<GoodsBean>> body = response.body();
                if(body==null)
                    return;
                if (body.getCode() == 200) {
                    ArrayList<GoodsBean> result = body.getResult();
                    if (result == null) {
                        loading = false;
                        rlEmpty.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setVisibility(View.GONE);
                        return;
                    } else {
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                        rlEmpty.setVisibility(View.GONE);
                        if (result.size() < pageSize) {
                            loading = false;
                        } else {
                            loading = true;
                        }
                        if (page == 0) {
                            mFeatureGoodsList.clear();
                        }
                        mFeatureGoodsList.addAll(result);
                        mFeaturedContentAdapter.notifyDataSetChanged();
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
            public void onFailure(Call<EntityObject<ArrayList<GoodsBean>>> call, Throwable t) {
                rlEmpty.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.GONE);
                Log.i("json",t.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



    @Override
    public void onRefresh() {
        showLoadingDialog();
        page = -1;
        new LoadDataThread().start();
    }

    private void completeSwipeRefresh() {
        if (swipeRefreshLayout == null)
            return;
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onJoinShoppingCart(int position) {
        //添加到购物车
        List<GoodsBean> shopCart = CommonUtils.getShopCart();
        if (shopCart == null) {
            shopCart = new ArrayList<>();
        }
        GoodsBean item = mGson.fromJson(mGson.toJson(mFeatureGoodsList.get(position)), GoodsBean.class);
        item.setCount(item.getLeast_p());
        GoodsBean old = null;
        for (int i = 0; i < shopCart.size(); i++) {
            old = shopCart.get(i);
            if (old.getGid().equals(item.getGid())) {
                item.setCount(old.getCount() + item.getCount());
                shopCart.remove(old);
            }
        }
        shopCart.add(item);
        Gson gson = new Gson();
        KLog.e(gson.toJson(item));
        CommonUtils.saveShopCart(shopCart);
        KLog.e(gson.toJson(shopCart));
        EventBus.getDefault().post(new GoodsEvent(shopCart.size(), item.getPicturl()));
        showToast("加入购物车成功");
        WaterHelperUtils.updateRedPointCounts();
    }

    /**
     * 模拟加载数据的线程
     */
    class LoadDataThread extends Thread {
        @Override
        public void run() {
            page++;
            getGoodsList();
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
