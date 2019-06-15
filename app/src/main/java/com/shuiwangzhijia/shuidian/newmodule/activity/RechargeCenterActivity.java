package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.RechargeCenterInfoBean;
import com.shuiwangzhijia.shuidian.bean.RechargeCouponOrderBean;
import com.shuiwangzhijia.shuidian.http.FndUtils;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.RechargeCenterAdapter;
import com.shuiwangzhijia.shuidian.newmodule.adapter.RechargeCenterCouponAdapter;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.view.SpacesItemDecoration;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_recharge_center)
public class RechargeCenterActivity extends BaseActivity implements RechargeCenterAdapter.OnItemClickListener, RechargeCenterCouponAdapter.OnCouponItemClickListener {
    @BindView(R.id.back_return)
    RelativeLayout mBackReturn;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.buy_immediately)
    TextView mBuyImmediately;
    @BindView(R.id.discount_coupon_rv)
    RecyclerView mDiscountCouponRv;
    @BindView(R.id.nest_scroll_view)
    NestedScrollView mNestScrollView;
    @BindView(R.id.title_bar_rl)
    RelativeLayout mTitleBarRl;
    @BindView(R.id.pay_price)
    TextView mPayPrice;
    @BindView(R.id.pay_regulation)
    TextView mPayRegulation;
    @BindView(R.id.no_recharge_coupon)
    TextView mNoRechargeCoupon;
    @BindView(R.id.bottom_bar_rl)
    RelativeLayout mBottomBarRl;
    private List<RechargeCenterInfoBean.DataBean.ListBean> mList;
    private RechargeCenterAdapter mRechargeCenterAdapter;
    private List<RechargeCenterInfoBean.DataBean.ListBean.RechargeBean> mRechargeCouponList;
    private RechargeCenterCouponAdapter mRechargeCenterCouponAdapter;
    private int selectedCouponRid = -1;
    private String selectedCouponPrice = null;


    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        setBaseBarHide();
        initRv();
        initDiscountCouponRv();
    }

    private void initRv() {
        mList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRv.setLayoutManager(manager);
        mRv.addItemDecoration(new SpacesItemDecoration(20, this));
        mRechargeCenterAdapter = new RechargeCenterAdapter(this);
        mRechargeCenterAdapter.setOnItemClickListener(this);
        mRechargeCenterAdapter.setData(mList);
        mRv.setAdapter(mRechargeCenterAdapter);
    }

    private void initDiscountCouponRv() {
        mRechargeCouponList = new ArrayList<>();
        mDiscountCouponRv.setLayoutManager(new GridLayoutManager(this, 2));
        mDiscountCouponRv.setNestedScrollingEnabled(false);
        mDiscountCouponRv.addItemDecoration(new SpacesItemDecoration(20, this));
        mRechargeCenterCouponAdapter = new RechargeCenterCouponAdapter(this);
        mRechargeCenterCouponAdapter.setData(mRechargeCouponList);
        mRechargeCenterCouponAdapter.setOnItemClickListener(this);
        mDiscountCouponRv.setAdapter(mRechargeCenterCouponAdapter);
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getRechargeCenterList(CommonUtils.getToken()).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int scode = object.getInt("scode");
                    if (scode == 200) {
                        RechargeCenterInfoBean rechargeCenterInfoBean = mGson.fromJson(datas, RechargeCenterInfoBean.class);
                        List<RechargeCenterInfoBean.DataBean.ListBean> list = rechargeCenterInfoBean.getData().getList();
                        if (list.size() == 1) {
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, FndUtils.dip2px(RechargeCenterActivity.this, 152));
                            params.topMargin = FndUtils.dip2px(RechargeCenterActivity.this, 70);
                            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                            mRv.setLayoutParams(params);
                            mRv.requestLayout();
                        }
                        mList.addAll(list);
                        mRechargeCenterAdapter.notifyDataSetChanged();

                        //循环全部设置第一个条目的充值券被选中
                        for (int i = 0; i < mList.size(); i++) {
                            List<RechargeCenterInfoBean.DataBean.ListBean.RechargeBean> recharge = mList.get(i).getRecharge();
                            if (recharge != null && recharge.size() != 0)
                                recharge.get(0).setChecked(true);
                        }

                        List<RechargeCenterInfoBean.DataBean.ListBean.RechargeBean> recharge = mList.get(0).getRecharge();
                        if (recharge != null && recharge.size() != 0) {
                            RechargeCenterInfoBean.DataBean.ListBean.RechargeBean rechargeBean = recharge.get(0);
                            selectedCouponRid = rechargeBean.getR_id();
                            selectedCouponPrice = rechargeBean.getSail_amount();
                            setSelectedCouponBottomBar(rechargeBean);

                            mRechargeCouponList.addAll(recharge);
                            mRechargeCenterCouponAdapter.notifyDataSetChanged();
                        } else {
                            selectedCouponRid = -1;
                            selectedCouponPrice = null;
                            setSelectedCouponBottomBar(null);
                        }
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
                KLog.e(t.getMessage());
                dismissLoadingDialog();
            }
        });
    }

    @Override
    protected void initEvent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mNestScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY < 500 && scrollY > 0) {
                        int tras = scrollY / 5;
                        if (tras > 10) {
                            mTitleBarRl.setBackgroundColor(Color.parseColor("#" + tras + "4F6389"));
                        }
                    } else if (scrollY >= 500) {
                        mTitleBarRl.setBackgroundColor(Color.parseColor("#4F6389"));
                    } else {
                        mTitleBarRl.setBackgroundColor(Color.parseColor("#00ffffff"));
                    }
                }
            });
        }
    }


    @OnClick({R.id.back_return, R.id.buy_immediately})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_return:
                closeActivity();
                break;
            case R.id.buy_immediately:
                handleBuy();
                break;
        }
    }

    private void handleBuy() {
        if (selectedCouponPrice == null) {
            showToast("请先选择充值券~");
            return;
        }
        showLoadingDialog();
        RetrofitUtils.getInstances().create().rechargeConfirmOrder(CommonUtils.getToken(), selectedCouponRid, selectedCouponPrice).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int scode = object.getInt("scode");
                    if (scode == 200) {
                        RechargeCouponOrderBean rechargeCouponOrderBean = mGson.fromJson(datas, RechargeCouponOrderBean.class);
                        RechargeCouponOrderBean.DataBean data = rechargeCouponOrderBean.getData();
                        Bundle bundle = new Bundle();
                        bundle.putString("orderNum", data.getOrder_no());
                        bundle.putLong("createTime", data.getOrder_time());
                        bundle.putString("price", data.getPrice());
                        skipActivity(RechargeCouponPayActivity.class, bundle);
                    } else {
                        showToast("充值券下单失败");
                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                } finally {
                    dismissLoadingDialog();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                KLog.e(t.getMessage());
                dismissLoadingDialog();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        RechargeCenterInfoBean.DataBean.ListBean listBean = mList.get(position);
        List<RechargeCenterInfoBean.DataBean.ListBean.RechargeBean> list = listBean.getRecharge();
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                RechargeCenterInfoBean.DataBean.ListBean.RechargeBean rechargeBean = list.get(i);
                if (rechargeBean.isChecked()) {
                    selectedCouponRid = rechargeBean.getR_id();
                    selectedCouponPrice = rechargeBean.getSail_amount();
                    setSelectedCouponBottomBar(rechargeBean);
                }
            }
        } else {
            selectedCouponRid = -1;
            selectedCouponPrice = null;
            setSelectedCouponBottomBar(null);
        }
        mRechargeCenterCouponAdapter.refreshData(list);
    }

    @Override
    public void onCouponItemClick(int position) {
        KLog.e(position);
        for (int i = 0; i < mRechargeCouponList.size(); i++) {
            RechargeCenterInfoBean.DataBean.ListBean.RechargeBean rechargeBean = mRechargeCouponList.get(i);
            if (i == position) {
                rechargeBean.setChecked(true);
                selectedCouponRid = rechargeBean.getR_id();
                selectedCouponPrice = rechargeBean.getSail_amount();
                setSelectedCouponBottomBar(rechargeBean);
            } else
                rechargeBean.setChecked(false);
        }
        mRechargeCenterCouponAdapter.notifyDataSetChanged();
    }


    private void setSelectedCouponBottomBar(RechargeCenterInfoBean.DataBean.ListBean.RechargeBean rechargeBean) {
        if (rechargeBean != null) {
            mNoRechargeCoupon.setVisibility(View.GONE);
            mBottomBarRl.setVisibility(View.VISIBLE);
            String sailAmount = rechargeBean.getSail_amount();
            String ramount = rechargeBean.getRamount();
            double sailAmountInt = Double.parseDouble(sailAmount);
            double ramountInt = Double.parseDouble(ramount);
            double gift = ramountInt - sailAmountInt;
            mPayRegulation.setText("充" + sailAmountInt + "赠" + gift);
            mPayPrice.setText("￥" + sailAmount);
        } else {
            mNoRechargeCoupon.setVisibility(View.VISIBLE);
            mBottomBarRl.setVisibility(View.GONE);
            mPayRegulation.setText("充" + "--" + "赠" + "--");
            mPayPrice.setText("￥--");
        }
    }
}
