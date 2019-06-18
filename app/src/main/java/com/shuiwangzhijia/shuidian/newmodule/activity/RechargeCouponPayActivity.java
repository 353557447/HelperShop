package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.App;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.PayBean;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.ui.OrderPayActivity;
import com.shuiwangzhijia.shuidian.ui.PurchaseOrderActivity;
import com.shuiwangzhijia.shuidian.utils.DateUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;
import com.shuiwangzhijia.shuidian.view.BaseBar;
import com.google.gson.Gson;
import com.pingplusplus.android.Pingpp;
import com.socks.library.KLog;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_recharge_coupon_pay,title = "充值券支付")
public class RechargeCouponPayActivity extends BaseActivity implements View.OnClickListener {
    private static final int SDK_PAY_FLAG = 100;
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
    private PayBean mSubmitOrderWxData;
    private String mUnifyOrderAliDataBean;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    try {
                        String aliPayResult = (String) msg.obj;
                        KLog.e(aliPayResult);
                        if (aliPayResult.contains("resultStatus=9000")) {
                            ToastUitl.showToastCustom("充值成功");
                            skipActivity(MyWalletNewActivity.class);
                            finish();
                        } else {


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        KLog.e(e.getMessage());
                    }

                    break;
            }
        }
    };

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
        if(pay_type==1){
            showLoadingDialog();
            RetrofitUtils.getInstances().create().getPayChannelInfo(mOrderNum,"wx" ).enqueue(new Callback<EntityObject<PayBean>>() {
                @Override
                public void onResponse(Call<EntityObject<PayBean>> call, Response<EntityObject<PayBean>> response) {
                    dismissLoadingDialog();
                    EntityObject<PayBean> body = response.body();
                    KLog.e(new Gson().toJson(body.getResult()));
                    mSubmitOrderWxData = body.getResult();
                    toWXPay();
                }

                @Override
                public void onFailure(Call<EntityObject<PayBean>> call, Throwable t) {
                    dismissLoadingDialog();
                }
            });
        }else{
            //支付宝支付
            showLoadingDialog();
            RetrofitUtils.getInstances().create().getAliPayChannelInfo(mOrderNum, "alipay").enqueue(new Callback<EntityObject<String>>() {
                @Override
                public void onResponse(Call<EntityObject<String>> call, Response<EntityObject<String>> response) {
                    dismissLoadingDialog();
                    EntityObject<String> body = response.body();
                    KLog.e(new Gson().toJson(body.getResult()));
                    mUnifyOrderAliDataBean = body.getResult();
                    KLog.e("我执行了么2");
                    toAliPay();
            /*    hint(body.getMsg());
                Pingpp.createPayment(OrderPayActivity.this, new Gson().toJson(body.getResult()));*/
                }

                @Override
                public void onFailure(Call<EntityObject<String>> call, Throwable t) {
                    KLog.e(t.getMessage());
                }
            });
        }

    }


    //调起支付宝支付的方法
    private void toAliPay() {
        final String orderInfo = mUnifyOrderAliDataBean; // 订单信息（app支付请求参数字符串，主要包含商户的订单信息，key=value形式，以&连接。）
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(RechargeCouponPayActivity.this);
                String result = String.valueOf(alipay.payV2(orderInfo, true));
                Message msg = Message.obtain();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 调起微信支付的方法
     **/
    private void toWXPay() {
        IWXAPI wxapi = App.getIwxapi(); //应用ID 即微信开放平台审核通过的应用APPID
        PayReq request = new PayReq(); //调起微信APP的对象
        //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
        request.appId = mSubmitOrderWxData.getAppid();
        request.partnerId = mSubmitOrderWxData.getPartnerid();
        request.prepayId = mSubmitOrderWxData.getPrepayid();
        request.packageValue = "Sign=WXPay";
        request.nonceStr = mSubmitOrderWxData.getNoncestr();
        request.timeStamp = mSubmitOrderWxData.getTimestamp() + "";
        request.sign = mSubmitOrderWxData.getSign();
        //EventBus.getDefault().postSticky(new WeChatPaySuccessEvent(mOrderId, mOrderCode, "buy"));
        wxapi.sendReq(request);//发送调起微信的请求
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
