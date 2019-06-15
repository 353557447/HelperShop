package com.shuiwangzhijia.shuidian.newmodule.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseLazyFragment;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.ShopEarnApplyWdBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.SpEarnWDingAdapter;
import com.shuiwangzhijia.shuidian.view.SpacesItemDecoration;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@FndViewInject(contentViewId = R.layout.fragment_sp_earn_with_drawing)
public class SpEarnWithDrawingFragment extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<ShopEarnApplyWdBean.DataBean> mList;
    private SpEarnWDingAdapter mSpEarnWDingAdapter;

    public SpEarnWithDrawingFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView(View view) {
        initRv();
    }

    private void initRv() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_30adfd));
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mSpEarnWDingAdapter = new SpEarnWDingAdapter(mContext);
        mList = new ArrayList<>();
        mRv.addItemDecoration(new SpacesItemDecoration(20, mContext));
        mSpEarnWDingAdapter.setData(mList);
        mRv.setAdapter(mSpEarnWDingAdapter);
    }

    @Override
    protected void lazyLoadData() {
        getData();
    }


    private void getData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().shopEarnApplyWDList(mToken, mPage, mLimit, 3).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (code == 200) {
                        ShopEarnApplyWdBean shopEarnApplyWdBean = mGson.fromJson(datas, ShopEarnApplyWdBean.class);
                        List<ShopEarnApplyWdBean.DataBean> list = shopEarnApplyWdBean.getData();
                        if (!isLoadMore) {
                            mList.clear();
                            if(list.size()==0)
                            setCentreViewShow(R.drawable.no_order_data_icon, "还没有提现中收益~");
                        }
                        mList.addAll(list);
                        mSpEarnWDingAdapter.notifyDataSetChanged();
                    } else {
                        if (!isLoadMore)
                            setCentreViewShow(R.drawable.no_order_data_icon, "还没有提现中收益~");
                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                } finally {
                    dismissLoadingDialog();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                showErrorToast();
                KLog.e(t.getMessage());
                dismissLoadingDialog();
            }
        });
    }

    @Override
    protected void initEvent() {
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isSlideToBottom(recyclerView)) {
                    isLoadMore = true;
                    mPage++;
                    getData();
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        mPage = 0;
        isLoadMore = false;
        getData();
    }
}
