package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.socks.library.KLog;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.BalanceInfoBean;
import com.shuiwangzhijia.shuidian.bean.RechargeCenterInfoBean;
import com.shuiwangzhijia.shuidian.http.FndUtils;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.BalanceRechargeAdapter;
import com.shuiwangzhijia.shuidian.newmodule.adapter.IncomeAndExpensesAdapter;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.view.BaseBar;
import com.shuiwangzhijia.shuidian.view.SpacesItemDecoration;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_balance_recharge, title = "余额明细")
public class BalanceRechargeActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BalanceRechargeAdapter.OnItemClickListener {
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.income_and_expenses_rv)
    RecyclerView mIncomeAndExpensesRv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<BalanceInfoBean.DataBean.ListBean> mIncomeAndExpensesList;
    private BalanceRechargeAdapter mBalanceRechargeAdapter;
    private IncomeAndExpensesAdapter mIncomeAndExpensesAdapter;
    private int mDid;
    private List<RechargeCenterInfoBean.DataBean.ListBean> mData;
    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        int position = bundle.getInt("position");
        mData = (List<RechargeCenterInfoBean.DataBean.ListBean>) bundle.getSerializable("list");
        if(mData.size()==1){
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, FndUtils.dip2px(this,85));
            params.gravity= Gravity.CENTER_HORIZONTAL;
            mRv.setLayoutParams(params);
            mRv.requestLayout();
        }
        mDid = mData.get(position).getDid();
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
        initIncomeAndExpensesRv();
    }

    @Override
    protected void initData() {
        getIncomeAndExpensesData();
    }


    private void getIncomeAndExpensesData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getMyRechargeList(CommonUtils.getToken(), CommonUtils.getDid(), mPage, mLimit).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object =new JSONObject(datas);
                    int scode = object.getInt("scode");
                    if (scode == 200) {
                        setCentreViewDismiss();
                        BalanceInfoBean touchBalanceInfoBean = mGson.fromJson(datas, BalanceInfoBean.class);
                        List<BalanceInfoBean.DataBean.ListBean> list = touchBalanceInfoBean.getData().getList();
                        if (!isLoadMore)
                            mIncomeAndExpensesList.clear();
                        mIncomeAndExpensesList.addAll(list);
                        mIncomeAndExpensesAdapter.notifyDataSetChanged();
                    } else {
                        if (!isLoadMore) {
                            mIncomeAndExpensesList.clear();
                            mIncomeAndExpensesAdapter.notifyDataSetChanged();
                            setCentreViewShow(R.drawable.no_order_data_icon, "还没有余额记录");
                        }
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

    @Override
    protected void initEvent() {
        mIncomeAndExpensesRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    getIncomeAndExpensesData();
                }
            }
        });
    }

    private void initRv() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRv.setLayoutManager(manager);
        mRv.addItemDecoration(new SpacesItemDecoration(20, this));
        mBalanceRechargeAdapter = new BalanceRechargeAdapter(this);
        mBalanceRechargeAdapter.setOnItemClickListener(this);
        mBalanceRechargeAdapter.setData(mData);
        mRv.setAdapter(mBalanceRechargeAdapter);
    }

    private void initIncomeAndExpensesRv() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_30adfd));
        mIncomeAndExpensesList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mIncomeAndExpensesRv.setLayoutManager(manager);
        mIncomeAndExpensesAdapter = new IncomeAndExpensesAdapter(this);
        mIncomeAndExpensesAdapter.setData(mIncomeAndExpensesList);
        mIncomeAndExpensesRv.setAdapter(mIncomeAndExpensesAdapter);
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        mPage = 0;
        getIncomeAndExpensesData();
    }

    @Override
    public void onItemClick(int position) {
        mPage = 0;
        isLoadMore = false;
        mDid = mData.get(position).getDid();
        getIncomeAndExpensesData();
    }
}
