package com.shuiwangzhijia.shuidian.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.App;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.BucketOrderBean;
import com.shuiwangzhijia.shuidian.bean.OrderBean;
import com.shuiwangzhijia.shuidian.bean.PayBean;
import com.shuiwangzhijia.shuidian.event.PayFinishEvent;
import com.shuiwangzhijia.shuidian.event.WechatPayResultEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.DateUtils;
import com.shuiwangzhijia.shuidian.utils.MyUtils;
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


public class OrderPayActivity extends BaseAct {
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
    @BindView(R.id.yuer_pay)
    CheckBox mYuerPay;
    @BindView(R.id.can_use_yuer)
    TextView mCanUseYuer;
    private OrderBean mData;
    private int pay_type = 1;//0表示货到付款 1微信 2 支付宝 3 余额
    private int fromType;   //2 提货操作界面
    private int mDid;
    private int mDelivery_type;  //0配送  1自提
    private String mBalance;
    private PayBean mSubmitOrderWxData;


    public static void startAtc(Context context, OrderBean data, int fromType) {
        Intent intent = new Intent(context, OrderPayActivity.class);
        intent.putExtra("data", data);
        intent.putExtra("fromType", fromType);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay);
        ButterKnife.bind(this);
        setTitle("订单支付");
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromType == 3){
                    fromType = 0;
                    finish();
                    PurchaseOrderActivity.statAct(OrderPayActivity.this,0);
                }else {
                    finish();
                }
            }
        });
        mData = (OrderBean) getIntent().getSerializableExtra("data");
        fromType = getIntent().getIntExtra("fromType", -1);
        mDelivery_type = mData.getDelivery_type();
        orderId.setText("订单号:" + mData.getOrder_no());
        mDid = mData.getDid();
        int status = mData.getStatus();
        int dstutas = mData.getDstutas();
        if (status == 4 || fromType == 2 || dstutas == 4) {
            mCashDelivery.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(mData.getCreate_time())) {
            orderDate.setText("下单时间:" + DateUtils.getFormatDateStr(mData.getOrderTime() * 1000));
        } else {
            orderDate.setText("下单时间:" + mData.getCreate_time());
        }
        if (fromType == 2) {
            String money = MyUtils.formatPrice(Double.parseDouble(mData.getPrice()));
            this.money.setText("￥" + money);
        } else {
            String money = MyUtils.formatPrice(mData.getAmount());
            this.money.setText("￥" + money);
        }
        onlineBtn.setOnClickListener(this);
        weChatBtn.setOnClickListener(this);
        aliPayBtn.setOnClickListener(this);
        mYuerPay.setOnClickListener(this);
        offlineBtn.setOnClickListener(this);
        changeState(1);
        getList();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (fromType == 3){
            finish();
            fromType = 0;
            PurchaseOrderActivity.statAct(OrderPayActivity.this,0);
        }
    }

    public void getList() {
        RetrofitUtils.getInstances().create().getBalance(mDid).enqueue(new Callback<EntityObject<BucketOrderBean>>() {
            @Override
            public void onResponse(Call<EntityObject<BucketOrderBean>> call, Response<EntityObject<BucketOrderBean>> response) {
                EntityObject<BucketOrderBean> body = response.body();
                if (body.getCode() == 200) {
                    BucketOrderBean result = body.getResult();
                    mBalance = result.getBalance();
                    mCanUseYuer.setText("可用余额：￥"+ mBalance);
                }
            }

            @Override
            public void onFailure(Call<EntityObject<BucketOrderBean>> call, Throwable t) {

            }
        });
    }

    private void getChannelInfo() {
        showLoad();
        RetrofitUtils.getInstances().create().getPayChannelInfo(mData.getOrder_no(), pay_type == 1 ? "wx" : "alipay").enqueue(new Callback<EntityObject<PayBean>>() {
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
    }

    private void getYuerInfo() {
        showLoad();
        RetrofitUtils.getInstances().create().balancePayment(mData.getOrder_no()).enqueue(new Callback<EntityObject<PayBean>>() {
            @Override
            public void onResponse(Call<EntityObject<PayBean>> call, Response<EntityObject<PayBean>> response) {
                hintLoad();
                EntityObject<PayBean> body = response.body();
                if (body.getCode() == 200){
                    hint(body.getMsg());
                    if (mDelivery_type == 0){
                        PurchaseOrderActivity.statAct(OrderPayActivity.this, 1);
                    }else if (mDelivery_type == 1){
                        PurchaseOrderActivity.statAct(OrderPayActivity.this, 5);
                    }
                }else {
                    ToastUitl.showToastCustom(body.getMsg());
                }
            }

            @Override
            public void onFailure(Call<EntityObject<PayBean>> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.payBtn)
    public void onViewClicked() {
        if (pay_type == 0) {
            payOffLine();
        } else if (pay_type == 1 || pay_type == 2){
            getChannelInfo();
        }else {
            KLog.d(mBalance);
            if (mBalance.equals("0.00")){
                ToastUitl.showToastCustom("没有可用余额");
                return;
            }
            getYuerInfo();
        }
    }

    private void payOffLine() {
        showLoad();
        RetrofitUtils.getInstances().create().payOffLine(mData.getOrder_no()).enqueue(new Callback<EntityObject<PayBean>>() {
            @Override
            public void onResponse(Call<EntityObject<PayBean>> call, Response<EntityObject<PayBean>> response) {
                hintLoad();
                EntityObject<PayBean> body = response.body();
                if (body.getCode() == 200) {
                    hint("提交成功");
                    EventBus.getDefault().post(new PayFinishEvent());
                } else {
                    hint("提交失败");
                }
                startActivity(new Intent(OrderPayActivity.this, PurchaseOrderActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<EntityObject<PayBean>> call, Throwable t) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.onlineBtn:
                changeState(1);
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
            case R.id.offlineBtn:
                changeState(0);
                break;
        }
    }

    private void hint(String text) {
        ToastUitl.showToastCustom(text);
    }

    private void changeState(int type) {
        pay_type = type;
        if (type == 0) {
            onlineBtn.setChecked(false);
            offlineBtn.setChecked(true);
            llOnline.setVisibility(View.GONE);
            payBtn.setText("确认到付");
        } else {
            payBtn.setText("立即支付");
            onlineBtn.setChecked(true);
            offlineBtn.setChecked(false);
            llOnline.setVisibility(View.VISIBLE);
            weChatBtn.setChecked(type == 1 ? true : false);
            aliPayBtn.setChecked(type == 2 ? true : false);
            mYuerPay.setChecked(type == 3 ? true : false);
        }
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
        //   EventBus.getDefault().postSticky(new WeChatPaySuccessEvent(mOrderId, mOrderCode, "buy"));
        wxapi.sendReq(request);//发送调起微信的请求
    }

    /**
     * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。
     * 最终支付成功根据异步通知为准
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        KLog.e("我执行了");
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                KLog.e("result:"+result);
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
                if (result.equals("success")) {
                    KLog.d("pay success");
                    KLog.e("pay success");
                    EventBus.getDefault().post(new PayFinishEvent());
                } else {
                    KLog.d("pay faild");
                    KLog.e("pay faild");
                    PurchaseOrderActivity.statAct(OrderPayActivity.this, 0);
                    hint("请重新支付");
                    return;
                }
                if (fromType == 2) {
                    Intent intent = new Intent(OrderPayActivity.this, OperationOrderActivityNew.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("ordersn", mData.getOrder_sn());
                    startActivity(intent);
                } else {
                    if (mDelivery_type == 0){
                        PurchaseOrderActivity.statAct(OrderPayActivity.this, 1);
                    }else if (mDelivery_type == 1){
                        PurchaseOrderActivity.statAct(OrderPayActivity.this, 5);
                    }
                }
                finish();
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void payResultEvent(WechatPayResultEvent event){
        if(event.getResult()==1){
            finish();
        }else if(event.getResult()==0){
            finish();
        }else{
            finish();
        }
    }
}
