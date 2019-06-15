package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.view.BaseBar;
import com.socks.library.KLog;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_wallet_withdraw,title = "提现")
public class WalletWithdrawActivity extends BaseActivity {
    @BindView(R.id.withdraw_deposit_money)
    EditText mWithdrawDepositMoney;
    @BindView(R.id.withdraw_deposit_hint)
    TextView mWithdrawDepositHint;
    @BindView(R.id.submit)
    Button mSubmit;
    private int mDid;
    private String mRebateAmount;
    private String mWaterFactoryName;
    private int mAnInt;
    private boolean isRefreshData;
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
                Intent intent=new Intent();
                intent.putExtra("isRefreshData",isRefreshData);
                setResult(RESULT_OK,intent);
                closeActivity();
            }

            @Override
            public void onRightClick() {

            }
        });
        mWithdrawDepositHint.setText("可提现金额（元）："+mRebateAmount);
    }

    @Override
    protected void initData() {
        getWithdrawDepositState();
    }


    private void getWithdrawDepositState() {
        KLog.e(mToken);
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getWithdrawDepositInfo(mToken,mDid).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object =new JSONObject(datas);
                    int code = object.getInt("code");
                    if(code==200){
                        mAnInt = object.getJSONObject("data").getInt("deal");
                        //1待处理 2已处理
                        if(mAnInt ==1){
                            mSubmit.setText("提现申请中，请耐心等待");
                            mWithdrawDepositMoney.setText(object.getJSONObject("data").getString("amount"));
                            mWithdrawDepositMoney.clearFocus();
                            mWithdrawDepositMoney.setEnabled(false);
                            mWithdrawDepositMoney.setClickable(false);
                        }
                    }else if(code==300){
                        //未有申请 正在申请中
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

    @OnClick(R.id.submit)
    public void onViewClicked() {
        handleWithdraw();
    }
    private void handleWithdraw() {
        String withdrawDepositMoney = mWithdrawDepositMoney.getText().toString().trim();
        if(isEmpty(withdrawDepositMoney)){
            showToast("提现金额不能为空");
            return;
        }
        RetrofitUtils.getInstances().create().returnMoneyWithdrawDeposit(mToken,mDid,withdrawDepositMoney).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object =new JSONObject(datas);
                    int code = object.getInt("code");
                    if(code==200){
                        showToast("提现申请成功~");
                        isRefreshData=true;
                        getWithdrawDepositState();
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
