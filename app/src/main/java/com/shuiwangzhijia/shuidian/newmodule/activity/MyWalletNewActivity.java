package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.socks.library.KLog;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.MyWalletInfoBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.ui.CustomerManageActivity;
import com.shuiwangzhijia.shuidian.ui.MyCouActivity;
import com.shuiwangzhijia.shuidian.ui.TicketActivity;
import com.shuiwangzhijia.shuidian.view.MyScrollView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_my_wallet_new)
public class MyWalletNewActivity extends BaseActivity {
    @BindView(R.id.return_money_balance)
    TextView mReturnMoneyBalance;
    @BindView(R.id.act_info_tv)
    TextView mActInfoTv;
    @BindView(R.id.recharge_immediately)
    TextView mRechargeImmediately;
    @BindView(R.id.water_coupon_counts)
    TextView mWaterCouponCounts;
    @BindView(R.id.water_coupon_counts_ll)
    LinearLayout mWaterCouponCountsLl;
    @BindView(R.id.coupon_counts)
    TextView mCouponCounts;
    @BindView(R.id.coupon_counts_ll)
    LinearLayout mCouponCountsLl;
    @BindView(R.id.return_money_amount)
    TextView mReturnMoneyAmount;
    @BindView(R.id.return_money_amount_ll)
    LinearLayout mReturnMoneyAmountLl;
    @BindView(R.id.shop_earning)
    TextView mShopEarning;
    @BindView(R.id.shop_earning_ll)
    LinearLayout mShopEarningLl;
    @BindView(R.id.client_counts)
    TextView mClientCounts;
    @BindView(R.id.client_counts_ll)
    LinearLayout mClientCountsLl;
    @BindView(R.id.nest_scroll_view)
    MyScrollView mNestScrollView;
    @BindView(R.id.back_return)
    RelativeLayout mBackReturn;
    @BindView(R.id.expense_record)
    TextView mExpenseRecord;
    @BindView(R.id.title_bar_rl)
    RelativeLayout mTitleBarRl;
    private List<MyWalletInfoBean.DataBean.ListBean> mMyWalletList;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        setBaseBarHide();
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
                        mMyWalletList = myWalletInfoBean.getData().getList();
                        MyWalletInfoBean.DataBean.ListBean listBean = mMyWalletList.get(0);
                        mReturnMoneyBalance.setText("￥"+listBean.getBalance());
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

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.recharge_immediately, R.id.water_coupon_counts_ll, R.id.coupon_counts_ll, R.id.return_money_amount_ll, R.id.shop_earning_ll, R.id.client_counts_ll, R.id.back_return, R.id.expense_record})
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.recharge_immediately:
                skipActivity(RechargeCenterActivity.class);
                break;
            case R.id.water_coupon_counts_ll:
                skipActivity(TicketActivity.class);
                break;
            case R.id.coupon_counts_ll:
                skipActivity(MyCouActivity.class);
                break;
            case R.id.return_money_amount_ll:
                skipActivity(MyReturnMoneyNewActivity.class);
                break;
            case R.id.shop_earning_ll:
                skipActivity(ShopEarningsActivity.class);
                break;
            case R.id.client_counts_ll:
                skipActivity(CustomerManageActivity.class);
                break;
            case R.id.back_return:
                closeActivity();
                break;
            case R.id.expense_record:
                bundle=new Bundle();
                bundle.putInt("position", 0);
                bundle.putSerializable("list", (Serializable) mMyWalletList);
                skipActivity(BalanceWalletActivity.class,bundle);
                break;
        }
    }
}
