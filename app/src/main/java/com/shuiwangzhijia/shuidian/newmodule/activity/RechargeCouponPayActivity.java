package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.PayBean;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.DateUtils;
import com.shuiwangzhijia.shuidian.view.BaseBar;
import com.google.gson.Gson;
import com.pingplusplus.android.Pingpp;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_recharge_coupon_pay,title = "充值券支付")
public class RechargeCouponPayActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.orderId)
    TextView orderId;
    @BindView(R.id.orderDate)
    TextView orderDate;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.onlineBtn)
    CheckBox onlineBtn;
    @BindView(R.id.weChatBtn)
    CheckBox weChatBtn;
    @BindView(R.id.aliPayBtn)
    CheckBox aliPayBtn;
    @BindView(R.id.llOnline)
    LinearLayout llOnline;
    @BindView(R.id.offlineBtn)
    CheckBox offlineBtn;
    @BindView(R.id.payBtn)
    TextView payBtn;
    @BindView(R.id.cash_delivery)
    LinearLayout mCashDelivery;
    private int pay_type = 1;// 1微信 2 支付宝
    private String mOrderNum;

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

        Bundle bundle = getIntent().getExtras();
        mOrderNum = bundle.getString("orderNum");
        long createTime = bundle.getLong("createTime");
        String price = bundle.getString("price");
        orderId.setText("订单号:" + mOrderNum);
        orderDate.setText("下单时间:" + DateUtils.getFormatDateStr(createTime * 1000));
        money.setText("￥" + price);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        weChatBtn.setOnClickListener(this);
        aliPayBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.weChatBtn:
                weChatBtn.setChecked(weChatBtn.isChecked());
                aliPayBtn.setChecked(!weChatBtn.isChecked());
                pay_type = 1;
                break;
            case R.id.aliPayBtn:
                weChatBtn.setChecked(!aliPayBtn.isChecked());
                aliPayBtn.setChecked(aliPayBtn.isChecked());
                pay_type = 2;
                break;
        }
    }
    @OnClick(R.id.payBtn)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.payBtn:
                getChannelInfo();
                break;
        }
    }


    private void getChannelInfo() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getPayChannelInfo(mOrderNum, pay_type == 1 ? "wx" : "alipay").enqueue(new Callback<EntityObject<PayBean>>() {
            @Override
            public void onResponse(Call<EntityObject<PayBean>> call, Response<EntityObject<PayBean>> response) {
                dismissLoadingDialog();
                EntityObject<PayBean> body = response.body();
                showToast(body.getMsg());
                Pingpp.createPayment(RechargeCouponPayActivity.this, new Gson().toJson(body.getResult()));
            }

            @Override
            public void onFailure(Call<EntityObject<PayBean>> call, Throwable t) {
                dismissLoadingDialog();
            }
        });
    }

    /**
     * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。
     * 最终支付成功根据异步通知为准
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
                if (result.equals("success")) {
                    //EventBus.getDefault().post(new PayFinishEvent());
                    showToast("您已支付成功，正在为您充值~");
                    closeActivity();
                    //支付成功
                } else {
                    showToast("请重新支付");
                }
                closeActivity();
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
            }
        }
    }
}
