package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.event.RefreshDataEvent;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.view.BaseBar;
import com.socks.library.KLog;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_go_to_balance, title = "转至余额")
public class GoToBalanceActivity extends BaseActivity {
    @BindView(R.id.withdraw_deposit_money)
    EditText mWithdrawDepositMoney;
    @BindView(R.id.withdraw_deposit_hint)
    TextView mWithdrawDepositHint;
    @BindView(R.id.submit)
    Button mSubmit;
    private int mDid;
    private String mRebateAmount;
    private String mWaterFactoryName;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        mDid = bundle.getInt("did");
        KLog.e(mDid);
        mRebateAmount = bundle.getString("rebateAmount");
        mWaterFactoryName = bundle.getString("waterFactoryName");
        mBaseBar.setBarListener(new BaseBar.BarListener() {
            @Override
            public void onLeftClick() {
                closeActivity();
            }

            @Override
            public void onRightClick() {

            }
        });
        mWithdrawDepositHint.setText("可转出金额（元）："+mRebateAmount);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        handleGoToBalance();
    }

    private void handleGoToBalance() {
        String withdrawDepositMoney = mWithdrawDepositMoney.getText().toString().trim();
        if(isEmpty(withdrawDepositMoney)){
            showToast("转出金额不能为空");
            return;
        }
        RetrofitUtils.getInstances().create().returnMoneyToBalance(mToken, CommonUtils.getDid(),withdrawDepositMoney).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object =new JSONObject(datas);
                    int code = object.getInt("code");
                    if(code==200){
                        showToast("转至余额成功~");
                        //mWithdrawDepositMoney.setText("");
                        EventBus.getDefault().post(new RefreshDataEvent("MyWalletActivity","getData"));
                        closeActivity();
                    }else{
                        showToast(object.getString("msg"));
                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                }finally {
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                showErrorToast();
                KLog.e(t.getMessage());
            }
        });
    }
}
