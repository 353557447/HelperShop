package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.socks.library.KLog;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.Constant;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.MyReturnMoneyBean;
import com.shuiwangzhijia.shuidian.bean.MyReturnMoneyListBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.RMCancelAdapter;
import com.shuiwangzhijia.shuidian.newmodule.adapter.RmUnderwayAdapter;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.MeasureUtil;
import com.shuiwangzhijia.shuidian.view.MyScrollView;
import com.shuiwangzhijia.shuidian.view.SpacesItemDecoration;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_my_return_money_new)
public class MyReturnMoneyNewActivity extends BaseActivity implements MyScrollView.OnScrollListener {
    private static final int REQUEST_RETURN_MONEY_WITHDRAW = 100;
    @BindView(R.id.back_return)
    RelativeLayout mBackReturn;
    @BindView(R.id.go_to_balance)
    TextView mGoToBalance;
    @BindView(R.id.withdraw_deposit)
    TextView mWithdrawDeposit;
    @BindView(R.id.return_money_balance)
    TextView mReturnMoneyBalance;
    @BindView(R.id.r_water_coupon)
    TextView mRWaterCoupon;
    @BindView(R.id.r_water_coupon_ll)
    LinearLayout mRWaterCouponLl;
    @BindView(R.id.total_r)
    TextView mTotalR;
    @BindView(R.id.total_r_ll)
    LinearLayout mTotalRLl;
    @BindView(R.id.r_record)
    TextView mRRecord;
    @BindView(R.id.r_record_ll)
    LinearLayout mRRecordLl;
    @BindView(R.id.underway_rv)
    RecyclerView mUnderwayRv;
    @BindView(R.id.cancel_rv)
    RecyclerView mCancelRv;
    @BindView(R.id.nest_scroll_view)
    MyScrollView mNestScrollView;
    @BindView(R.id.title_bar_rl)
    RelativeLayout mTitleBarRl;
    private int mDid;
    private String mSelectedRebateAmount;
    private MyReturnMoneyBean mMyReturnMoneyBean;
    private RMCancelAdapter mRMCancelAdapter;
    private RmUnderwayAdapter mRmUnderwayAdapter;
    private List<MyReturnMoneyListBean.DataBean.ConductBean> mRMUnderwayList;
    private ArrayList<MyReturnMoneyListBean.DataBean.HistoryBean> mRMCancelList;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        setBaseBarHide();
        mDid = Integer.parseInt(CommonUtils.getDid());
        initUnderwayRv();
        initCancelRv();
    }


    private void initUnderwayRv() {
        mUnderwayRv.setLayoutManager(new LinearLayoutManager(this));
        mRmUnderwayAdapter = new RmUnderwayAdapter(this);
        mRMUnderwayList = new ArrayList<MyReturnMoneyListBean.DataBean.ConductBean>();
        mRmUnderwayAdapter.setData(mRMUnderwayList);
        mUnderwayRv.addItemDecoration(new SpacesItemDecoration(this, MeasureUtil.dip2px(this,12)));
        mUnderwayRv.setAdapter(mRmUnderwayAdapter);
    }

    private void initCancelRv() {
        mCancelRv.setLayoutManager(new LinearLayoutManager(this));
        mRMCancelAdapter = new RMCancelAdapter(this);
        mRMCancelList = new ArrayList<MyReturnMoneyListBean.DataBean.HistoryBean>();
        mRMCancelAdapter.setData(mRMCancelList);
        mCancelRv.addItemDecoration(new SpacesItemDecoration(this, MeasureUtil.dip2px(this,12)));
        mCancelRv.setAdapter(mRMCancelAdapter);
    }

    @Override
    protected void initData() {
        getReturnMoneyInfo();
        getReturnMoneyList();
    }

    private void getReturnMoneyList() {
        RetrofitUtils.getInstances().create().getMyReturnMoneyList(mDid).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        MyReturnMoneyListBean myReturnMoneyListBean = mGson.fromJson(datas, MyReturnMoneyListBean.class);
                        MyReturnMoneyListBean.DataBean data = myReturnMoneyListBean.getData();
                        List<MyReturnMoneyListBean.DataBean.ConductBean> conduct = data.getConduct();
                        List<MyReturnMoneyListBean.DataBean.HistoryBean> history = data.getHistory();
                        mRMUnderwayList.clear();
                        mRMCancelList.clear();
                        mRMUnderwayList.addAll(conduct);
                        mRMCancelList.addAll(history);
                        mRmUnderwayAdapter.notifyDataSetChanged();
                        mRMCancelAdapter.notifyDataSetChanged();
                    } else {

                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                } finally {
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                showErrorToast();
                KLog.e(t.getMessage());
            }
        });
    }


    private void getReturnMoneyInfo() {
        RetrofitUtils.getInstances().create().getMyReturnMoney(mToken, mDid).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        mMyReturnMoneyBean = mGson.fromJson(datas, MyReturnMoneyBean.class);
                        setData();
                    } else {

                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                } finally {
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                showErrorToast();
                KLog.e(t.getMessage());
            }
        });
    }

    private void setData() {
        MyReturnMoneyBean.DataBean data = mMyReturnMoneyBean.getData();
        mReturnMoneyBalance.setText(data.getRebate_amount());
        mSelectedRebateAmount = data.getRebate_amount();

        mTotalR.setText(data.getHistory_amount());
        mRWaterCoupon.setText(data.getAll_s_amount());

        mRRecord.setText(data.getAll_record() + "");


    }


    @Override
    protected void initEvent() {
        mNestScrollView.setOnScrollListener(this);
    }


    @OnClick({R.id.back_return, R.id.go_to_balance, R.id.withdraw_deposit, R.id.r_water_coupon_ll, R.id.total_r_ll, R.id.r_record_ll})
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.back_return:
                closeActivity();
                break;
            case R.id.go_to_balance:
                bundle = new Bundle();
                bundle.putInt("did", mDid);
                bundle.putString("rebateAmount", mSelectedRebateAmount);
                bundle.putString("waterFactoryName", Constant.WATER_FACTORY_NAME);
                skipActivity(GoToBalanceActivity.class, bundle);
                break;
            case R.id.withdraw_deposit:
                bundle = new Bundle();
                bundle.putInt("did", mDid);
                bundle.putString("rebateAmount", mSelectedRebateAmount);
                bundle.putString("waterFactoryName", Constant.WATER_FACTORY_NAME);
                skipActivityForResult(WalletWithdrawActivity.class, bundle, REQUEST_RETURN_MONEY_WITHDRAW);
                break;
            case R.id.r_water_coupon_ll:

                break;
            case R.id.total_r_ll:

                break;
            case R.id.r_record_ll:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_RETURN_MONEY_WITHDRAW) {
            boolean isRefreshData = data.getBooleanExtra("isRefreshData", true);
            if (isRefreshData)
                getReturnMoneyInfo();
        }
    }

    @Override
    public void onScroll(int scrollY) {
        if (scrollY < 500 && scrollY > 0) {
            int tras = scrollY / 5;
            if (tras > 10) {
                mTitleBarRl.setBackgroundColor(Color.parseColor("#" + tras + "FF014E"));
            }
        } else if (scrollY >= 500) {
            mTitleBarRl.setBackgroundColor(Color.parseColor("#FF014E"));
        } else {
            mTitleBarRl.setBackgroundColor(Color.parseColor("#00ffffff"));
        }
    }
}
