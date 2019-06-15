package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.RechargeRecordBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.RechargeRecordAdapter;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.view.BaseBar;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_recharge_record, title = "充值记录")
public class RechargeRecordActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<RechargeRecordBean.DataBean.ListBean> mList;
    private RechargeRecordAdapter mRechargeRecordAdapter;

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

    @Override
    protected void initData() {
        getData();
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

    private void getData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getRechargeRecordList(CommonUtils.getToken(), mPage, mLimit).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int scode = object.getInt("scode");
                    if (scode == 200) {
                        setCentreViewDismiss();
                        RechargeRecordBean rechargeRecordBean = mGson.fromJson(datas, RechargeRecordBean.class);
                        List<RechargeRecordBean.DataBean.ListBean> list = rechargeRecordBean.getData().getList();
                        if (!isLoadMore)
                            mList.clear();
                        mList.addAll(list);
                        mRechargeRecordAdapter.notifyDataSetChanged();
                    } else {
                        if (!isLoadMore)
                            setCentreViewShow(R.drawable.no_order_data_icon, "还没有充值记录");
                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                } finally {
                    dismissLoadingDialog();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                KLog.e(t.getMessage());
                dismissLoadingDialog();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initRv() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_30adfd));
        mList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(manager);
        mRechargeRecordAdapter = new RechargeRecordAdapter(this);
        mRechargeRecordAdapter.setData(mList);
        mRv.setAdapter(mRechargeRecordAdapter);
    }

    @Override
    public void onRefresh() {
        mPage = 0;
        isLoadMore = false;
        getData();
    }
}
