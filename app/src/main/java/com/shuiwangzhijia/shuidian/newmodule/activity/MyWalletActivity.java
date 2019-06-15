package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;

import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.MyWalletInfoBean;
import com.shuiwangzhijia.shuidian.http.FndUtils;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.MyWalletAdapter;
import com.shuiwangzhijia.shuidian.ui.MyCouActivity;
import com.shuiwangzhijia.shuidian.ui.TicketActivity;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.view.SpacesItemDecoration;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_my_wallet)
public class MyWalletActivity extends BaseActivity {
    private static final int REQUEST_RETURN_MONEY_WITHDRAW = 100;
    @BindView(R.id.back_return)
    RelativeLayout mBackReturn;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.discount_coupon)
    LinearLayout mDiscountCoupon;
    @BindView(R.id.water_coupon)
    LinearLayout mWaterCoupon;
    @BindView(R.id.return_money)
    LinearLayout mReturnMoney;
    @BindView(R.id.recharge_record)
    LinearLayout mRechargeRecord;
    @BindView(R.id.withdraw_deposit)
    LinearLayout mWithdrawDeposit;
    @BindView(R.id.particulars)
    LinearLayout mParticulars;
    @BindView(R.id.go_to_balance)
    LinearLayout mGoToBalance;
    @BindView(R.id.water_factory_name)
    TextView mWaterFactoryName;
    @BindView(R.id.return_money_balance)
    TextView mReturnMoneyBalance;
    @BindView(R.id.return_money_water_coupons)
    TextView mReturnMoneyWaterCoupons;
    @BindView(R.id.return_money_policy)
    TextView mReturnMoneyPolicy;
    private List<MyWalletInfoBean.DataBean.ListBean> mList;
    private MyWalletAdapter mMyWalletAdapter;
    private MyWalletInfoBean.DataBean.ListBean mSelectedListBean;
    private String mSelectedRebateAmount;
    private int mSelectedPosition;


    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        setBaseBarHide();
        initRv();
    }

    private void initRv() {
        mList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRv.setLayoutManager(manager);
        mRv.addItemDecoration(new SpacesItemDecoration(20, this));
        mMyWalletAdapter = new MyWalletAdapter(this);
        mMyWalletAdapter.setOnItemClickListener(new MyWalletAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                MyWalletInfoBean.DataBean.ListBean listBean = mList.get(position);
                mSelectedListBean = listBean;
                mSelectedPosition = position;
                mWaterFactoryName.setText(listBean.getWater_name());
                getReturnMoneyInfo(listBean.getDid());
            }
        });
        mMyWalletAdapter.setData(mList);
        mRv.setAdapter(mMyWalletAdapter);
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getMyWalletInfo(mToken).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int scode = object.getInt("scode");
                    if (scode == 200) {
                        MyWalletInfoBean myWalletInfoBean = mGson.fromJson(datas, MyWalletInfoBean.class);
                        List<MyWalletInfoBean.DataBean.ListBean> list = myWalletInfoBean.getData().getList();
                        if (list.size() == 1) {
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, FndUtils.dip2px(MyWalletActivity.this, 152));
                            params.topMargin = FndUtils.dip2px(MyWalletActivity.this, 70);
                            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                            mRv.setLayoutParams(params);
                            mRv.requestLayout();
                        }
                        mList.clear();
                        mList.addAll(list);
                        mMyWalletAdapter.notifyDataSetChanged();
                        MyWalletInfoBean.DataBean.ListBean listBean = list.get(0);
                        if (listBean != null) {
                            mWaterFactoryName.setText(listBean.getWater_name());
                            mSelectedListBean = listBean;
                            mSelectedPosition = 0;
                            getReturnMoneyInfo(listBean.getDid());
                            int sid = listBean.getSid();
                        }
                    } else {

                    }
                } catch (JSONException e) {
                    KLog.e(e.getMessage());
                } finally {
                    dismissLoadingDialog();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                dismissLoadingDialog();
            }
        });
    }

    private void getReturnMoneyInfo(int did) {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getReturnMoneyInfo(CommonUtils.getToken(), did).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                     /*   rebate_amount	string	返利余额
                        already_pickets	string	已返利水票*/
                        mSelectedRebateAmount = object.getJSONObject("data").getString("rebate_amount");
                        mReturnMoneyBalance.setText(mSelectedRebateAmount);
                        mReturnMoneyWaterCoupons.setText(object.getJSONObject("data").getString("already_pickets"));
                    } else {

                    }
                } catch (JSONException e) {
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

    }


    @OnClick({R.id.discount_coupon, R.id.water_coupon, R.id.return_money, //
            R.id.recharge_record, R.id.back_return, //
            R.id.withdraw_deposit, R.id.particulars, //
            R.id.go_to_balance, R.id.return_money_policy})
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.back_return:
                closeActivity();
                break;
            case R.id.discount_coupon:
                skipActivity(MyCouActivity.class);
                break;
            case R.id.water_coupon:
                skipActivity(TicketActivity.class);
                break;
            case R.id.return_money:
                bundle = new Bundle();
                bundle.putInt("did", mSelectedListBean.getDid());
                skipActivity(MyReturnMoneyActivity.class, bundle);
                break;
            case R.id.recharge_record:
                skipActivity(RechargeRecordActivity.class);
                break;
            case R.id.withdraw_deposit:
                bundle = new Bundle();
                bundle.putInt("did", mSelectedListBean.getDid());
                bundle.putString("rebateAmount", mSelectedRebateAmount);
                bundle.putString("waterFactoryName", mSelectedListBean.getWater_name());
                skipActivityForResult(WalletWithdrawActivity.class, bundle, REQUEST_RETURN_MONEY_WITHDRAW);
                break;
            case R.id.particulars:
                bundle = new Bundle();
                bundle.putInt("position", mSelectedPosition);
                bundle.putSerializable("list", (Serializable) mList);
                skipActivity(ReturnMoneyDetailsActivity.class, bundle);
                break;
            case R.id.go_to_balance:
                bundle = new Bundle();
                bundle.putInt("did", mSelectedListBean.getDid());
                bundle.putString("rebateAmount", mSelectedRebateAmount);
                bundle.putString("waterFactoryName", mSelectedListBean.getWater_name());
                skipActivity(GoToBalanceActivity.class, bundle);
                break;
            case R.id.return_money_policy:
                bundle = new Bundle();
                bundle.putInt("did", mSelectedListBean.getDid());
                bundle.putString("waterFactoryName", mSelectedListBean.getWater_name());
                skipActivity(ReturnMoneyPolicyActivity.class, bundle);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_RETURN_MONEY_WITHDRAW) {
            boolean isRefreshData = data.getBooleanExtra("isRefreshData", true);
            if (isRefreshData)
                getReturnMoneyInfo(mSelectedListBean.getDid());
        }
    }
}
