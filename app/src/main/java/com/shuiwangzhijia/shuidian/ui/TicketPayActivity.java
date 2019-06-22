package com.shuiwangzhijia.shuidian.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.App;
import com.shuiwangzhijia.shuidian.base.BaseAct;

import com.shuiwangzhijia.shuidian.bean.BucketOrderBean;
import com.shuiwangzhijia.shuidian.bean.PayBean;
import com.shuiwangzhijia.shuidian.bean.TicketBean;
import com.shuiwangzhijia.shuidian.bean.TicketOrderBean;
import com.shuiwangzhijia.shuidian.event.PayFinishEvent;
import com.shuiwangzhijia.shuidian.event.WechatPayFromWhichEvent;
import com.shuiwangzhijia.shuidian.event.WechatPayResultEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;

import com.shuiwangzhijia.shuidian.utils.DateUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;
import com.google.gson.Gson;
import com.pingplusplus.android.Pingpp;
import com.socks.library.KLog;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class TicketPayActivity extends BaseAct {
    private static final int SDK_PAY_FLAG = 100;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.weChatBtn)
    CheckBox weChatBtn;
    @BindView(R.id.aliPayBtn)
    CheckBox aliPayBtn;
    @BindView(R.id.yuer_pay)
    CheckBox mYuerPay;
    @BindView(R.id.payBtn)
    TextView payBtn;
    @BindView(R.id.shopName)
    TextView shopName;
    @BindView(R.id.goodsName)
    TextView goodsName;
    @BindView(R.id.limitNum)
    TextView limitNum;
    @BindView(R.id.limitDate)
    TextView limitDate;
    private TicketOrderBean data;
    private int pay_type = 1;// 1微信 2 支付宝
    private TicketBean ticketBean;
    @BindView(R.id.can_use_yuer)
    TextView mCanUseYuer;
    private String mOrder_no;
    private String mUnifyOrderAliDataBean;
    private PayBean mSubmitOrderWxData;

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
                            ToastUitl.showToastCustom("购买成功");
                            finish();
                        } else {
                            finish();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        KLog.e(e.getMessage());
                    }

                    break;
            }
        }
    };


    public static void startAtc(Context context, TicketOrderBean data) {
        Intent intent = new Intent(context, TicketPayActivity.class);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_pay);
        ButterKnife.bind(this);
        setTitle("支付");
        data = ((TicketOrderBean) getIntent().getSerializableExtra("data"));
        mOrder_no = data.getOrder_no();
        ticketBean = this.data.getData();
        shopName.setText("发行水厂:"+this.ticketBean.getSname());
        goodsName.setText("可用商品:"+this.ticketBean.getGname());
        limitNum.setText("购买数量:"+this.ticketBean.getSnum() + "张");
        if (this.ticketBean.getStart() > 0 && this.ticketBean.getEnd() > 0) {
            limitDate.setText("有限期限:"+DateUtils.getYMDTime(this.ticketBean.getStart()) + "~" + DateUtils.getYMDTime(this.ticketBean.getEnd()));
        } else {
            limitDate.setText("有效期限:"+"永久有效");
        }
        money.setText("￥" + this.ticketBean.getSprice());
        getList();
    }

    public void getList() {
        RetrofitUtils.getInstances().create().getBalance(ticketBean.getDid()).enqueue(new Callback<EntityObject<BucketOrderBean>>() {
            @Override
            public void onResponse(Call<EntityObject<BucketOrderBean>> call, Response<EntityObject<BucketOrderBean>> response) {
                EntityObject<BucketOrderBean> body = response.body();
                if (body.getCode() == 200) {
                    BucketOrderBean result = body.getResult();
                    String balance = result.getBalance();
                    mCanUseYuer.setText("余额：￥"+balance);
                }
            }

            @Override
            public void onFailure(Call<EntityObject<BucketOrderBean>> call, Throwable t) {

            }
        });
    }

    private void setTextStyle(TextView text, String first, String content) {
        SpannableString spanString = new SpannableString(first + content);
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_999999)), 0, first.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_333333)), first.length(), (content + first).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        text.setText(spanString);
    }

    @OnClick({R.id.payBtn, R.id.weChatBtn, R.id.aliPayBtn,R.id.yuer_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.payBtn:
                if (pay_type == 3){
                    getYuerInfo();
                }else {
                    getChannelInfo();
                }
                break;
            case R.id.weChatBtn:
                weChatBtn.setChecked(true);
                aliPayBtn.setChecked(false);
                mYuerPay.setChecked(false);
                pay_type = 1;
                break;
            case R.id.aliPayBtn:
                weChatBtn.setChecked(false);
                aliPayBtn.setChecked(true);
                mYuerPay.setChecked(false);
                pay_type = 2;
                break;
            case R.id.yuer_pay:
                weChatBtn.setChecked(false);
                aliPayBtn.setChecked(false);
                mYuerPay.setChecked(true);
                pay_type = 3;
                break;
        }

    }

    private void getYuerInfo() {
        showLoad();
        RetrofitUtils.getInstances().create().balancePaymentYuer(mOrder_no).enqueue(new Callback<EntityObject<PayBean>>() {
            @Override
            public void onResponse(Call<EntityObject<PayBean>> call, Response<EntityObject<PayBean>> response) {
                hintLoad();
                EntityObject<PayBean> body = response.body();
                if (body.getCode() == 200){
                    hint("支付成功~");
                    finish();
                }else {
                    ToastUitl.showToastCustom(body.getMsg());
                }
            }

            @Override
            public void onFailure(Call<EntityObject<PayBean>> call, Throwable t) {

            }
        });
    }

    private void getChannelInfo() {

        if(pay_type==1){
            //微信支付
            showLoad();
            RetrofitUtils.getInstances().create().getPayChannelInfo(data.getOrder_no(), "wx" ).enqueue(new Callback<EntityObject<PayBean>>() {
                @Override
                public void onResponse(Call<EntityObject<PayBean>> call, Response<EntityObject<PayBean>> response) {
                    hintLoad();
                    EntityObject<PayBean> body = response.body();
                    KLog.e(new Gson().toJson(body.getResult()));
                    mSubmitOrderWxData = body.getResult();
                    toWXPay();
            /*    hint(body.getMsg());
                Pingpp.createPayment(OrderPayActivity.this, new Gson().toJson(body.getResult()));*/
                }

                @Override
                public void onFailure(Call<EntityObject<PayBean>> call, Throwable t) {

                }
            });
        }else{
            //支付宝支付
            showLoad();
            RetrofitUtils.getInstances().create().getAliPayChannelInfo(data.getOrder_no(), "alipay").enqueue(new Callback<EntityObject<String>>() {
                @Override
                public void onResponse(Call<EntityObject<String>> call, Response<EntityObject<String>> response) {
                    hintLoad();
                    EntityObject<String> body = response.body();
                    KLog.e(new Gson().toJson(body.getResult()));
                    mUnifyOrderAliDataBean = body.getResult();
                    toAliPay();
                }

                @Override
                public void onFailure(Call<EntityObject<String>> call, Throwable t) {
                    KLog.e(t.getMessage());
                }
            });
        }


    }


    private void hint(String text) {
        ToastUitl.showToastCustom(text);
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
                   // TicketActivity.startAtc(TicketPayActivity.this,null);
                    finish();
                    EventBus.getDefault().post(new PayFinishEvent());
                } else {
                    hint("请重新支付");
                }

                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
            }
        }
    }

    //调起支付宝支付的方法
    private void toAliPay() {
        final String orderInfo = mUnifyOrderAliDataBean; // 订单信息（app支付请求参数字符串，主要包含商户的订单信息，key=value形式，以&连接。）
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(TicketPayActivity.this);
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
        EventBus.getDefault().postSticky(new WechatPayFromWhichEvent("TicketPayActivity"));
        wxapi.sendReq(request);//发送调起微信的请求
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void  wxPayResult(WechatPayResultEvent event){
        finish();
    }
}
