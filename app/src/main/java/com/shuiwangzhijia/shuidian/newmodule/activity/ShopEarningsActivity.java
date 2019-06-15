package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.ui.ConsumerDetailActivity;
import com.socks.library.KLog;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_shop_earnings)
public class ShopEarningsActivity extends BaseActivity {
    @BindView(R.id.shop_earnings_money)
    TextView mShopEarningsMoney;
    @BindView(R.id.withdraw_deposit)
    LinearLayout mWithdrawDeposit;
    @BindView(R.id.consume_details)
    LinearLayout mConsumeDetails;
    @BindView(R.id.back_return)
    RelativeLayout mBackReturn;
    private String mTotalMoney;
    private String mBalance;

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
        RetrofitUtils.getInstances().create().getShopEarnBalance(mToken).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object =new JSONObject(datas);
                    int code = object.getInt("code");
                    if(code==200){
                        mBalance = object.getJSONObject("data").getString("banlance");
                        mShopEarningsMoney.setText(mBalance);
                    }else{

                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                }finally {
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

    @OnClick({R.id.withdraw_deposit, R.id.consume_details, R.id.back_return})
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.withdraw_deposit:
             /* bundle = new Bundle();
                bundle.putString("money", mTotalMoney);
                skipActivity(CashActivity.class, bundle);*/
                skipActivity(ApplyShopEarningsWithdrawActivity.class);
                break;
            case R.id.consume_details:
                skipActivity(ConsumerDetailActivity.class);
                break;
            case R.id.back_return:
                closeActivity();
                break;
        }
    }
}
