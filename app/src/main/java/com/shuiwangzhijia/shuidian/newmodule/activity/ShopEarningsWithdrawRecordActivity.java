package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.SpEarnWdRecordBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.SpEarnWDRecordAdapter;
import com.shuiwangzhijia.shuidian.view.BaseBar;
import com.shuiwangzhijia.shuidian.view.SpacesItemDecoration;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_shop_earnings_withdraw_record, title = "提现记录")
public class ShopEarningsWithdrawRecordActivity extends BaseActivity implements SpEarnWDRecordAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private SpEarnWDRecordAdapter mSpEarnWDRecordAdapter;
    private List<SpEarnWdRecordBean.DataBean> mList;
    private List<SpEarnWdRecordBean.DataBean> mData;

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
        initRv();
    }

    private void initRv() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_30adfd));
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addItemDecoration(new SpacesItemDecoration(20, this));
        mSpEarnWDRecordAdapter = new SpEarnWDRecordAdapter(this);
        mList = new ArrayList<>();
        mSpEarnWDRecordAdapter.setData(mList);
        mSpEarnWDRecordAdapter.setOnItemClickListener(this);
        mRv.setAdapter(mSpEarnWDRecordAdapter);
    }

    @Override
    protected void initData() {
getData();
    }

    private void getData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getShopEarnWdRecord(mToken, mPage, mLimit).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (code == 200) {
                        SpEarnWdRecordBean spEarnWdRecordBean = mGson.fromJson(datas, SpEarnWdRecordBean.class);
                        mData = spEarnWdRecordBean.getData();
                        if (!isLoadMore) {
                            mList.clear();
                            if(mData.isEmpty())
                            setCentreViewShow(R.drawable.no_order_data_icon, "还没有提现记录~");
                        }
                        // mList.addAll(list);
                        mList.addAll(mData);
                        mSpEarnWDRecordAdapter.notifyDataSetChanged();
                    } else {

                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                } finally {
                    dismissLoadingDialog();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
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
    public void onItemClick(int position) {
        Bundle bundle=new Bundle();
        bundle.putString("forwardCode",mList.get(position).getForward_code());
        skipActivity(SpEarnWdRecordDetailsActivity.class,bundle);
    }

    @Override
    public void onRefresh() {
        mPage = 0;
        isLoadMore = false;
        getData();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
