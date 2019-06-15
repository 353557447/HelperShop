package com.shuiwangzhijia.shuidian.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.BucketBean;
import com.shuiwangzhijia.shuidian.bean.BucketOrderBean;
import com.shuiwangzhijia.shuidian.bean.BucketPayBean;
import com.shuiwangzhijia.shuidian.bean.PayBean;
import com.shuiwangzhijia.shuidian.dialog.EditBucketCountDialog;
import com.shuiwangzhijia.shuidian.event.PayFinishEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.CalculateUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;
import com.shuiwangzhijia.shuidian.view.BucketView;
import com.google.gson.Gson;
import com.pingplusplus.android.Pingpp;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 空桶订单支付页面
 * created by wangsuli on 2018/8/22.
 */
public class BucketOrderPayActivity extends BaseAct {
    @BindView(R.id.bucketView)
    BucketView bucketView;
    @BindView(R.id.onlineBtn)
    CheckBox onlineBtn;
    @BindView(R.id.weChatBtn)
    TextView weChatBtn;
    @BindView(R.id.aliPayBtn)
    TextView aliPayBtn;
    @BindView(R.id.llOnline)
    LinearLayout llOnline;
    @BindView(R.id.sureBtn)
    LinearLayout sureBtn;
    //    @BindView(R.id.name)
//    TextView name;
    @BindView(R.id.remove)
    TextView remove;
    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.add)
    TextView add;
    @BindView(R.id.moneyTv)
    TextView moneyTv;
    @BindView(R.id.can_use_yuer)
    TextView mCanUseYuer;
    @BindView(R.id.yuer_pay)
    TextView mYuerPay;
    private BucketBean data;
    private int pay_type = 1;//0表示货到付款 1微信 2 支付宝 3 余额
    private int count = 1;
    private String price;
    private int mDid;
    private int mGid;

    public static void startAtc(Context context, BucketBean data) {
        Intent intent = new Intent(context, BucketOrderPayActivity.class);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_order_pay);
        ButterKnife.bind(this);
        setTitle("空桶支付");
        data = (BucketBean) getIntent().getSerializableExtra("data");
        mGid = data.getGid();
        mDid = data.getDid();
        bucketView.initData(data);
        changeState(1);
        price = data.getEmpty_price();
        changeNum();
        getList();
    }

    public void getList() {
        RetrofitUtils.getInstances().create().getBalance(mDid).enqueue(new Callback<EntityObject<BucketOrderBean>>() {
            @Override
            public void onResponse(Call<EntityObject<BucketOrderBean>> call, Response<EntityObject<BucketOrderBean>> response) {
                EntityObject<BucketOrderBean> body = response.body();
                if (body.getCode() == 200) {
                    BucketOrderBean result = body.getResult();
                    String balance = result.getBalance();
                    mCanUseYuer.setText("可用余额：￥"+balance);
                }
            }

            @Override
            public void onFailure(Call<EntityObject<BucketOrderBean>> call, Throwable t) {

            }
        });
    }

    private void hint(String text) {
        ToastUitl.showToastCustom(text);
    }

    private void changeState(int type) {
        pay_type = type;
        weChatBtn.setSelected(type == 1 ? true : false);
        aliPayBtn.setSelected(type == 2 ? true : false);
        mYuerPay.setSelected(type == 3 ? true : false);
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
                    EventBus.getDefault().post(new PayFinishEvent());
                    startActivity(new Intent(BucketOrderPayActivity.this, BucketOrderActivity.class));
                    finish();
                } else {
                    hint("请重新支付");
                }
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(PayFinishEvent event) {
        finish();
    }

    @OnClick({R.id.remove, R.id.add, R.id.weChatBtn, R.id.aliPayBtn, R.id.sureBtn, R.id.account,R.id.yuer_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sureBtn:
                if (pay_type == 1 || pay_type == 2){
                    postPay();
                }else {
                    balancePay();
                }

                break;
            case R.id.weChatBtn:
                changeState(1);
                break;
            case R.id.aliPayBtn:
                changeState(2);
                break;
            case R.id.yuer_pay:
                changeState(3);
                break;
            case R.id.remove:
                if (count <= 1) {
                    ToastUitl.showToastCustom("购买数量不能低于1!");
                    return;
                }
                count--;
                changeNum();
                break;
            case R.id.add:
                count++;
                changeNum();
                break;
            case R.id.account:
                EditBucketCountDialog dialog = new EditBucketCountDialog(BucketOrderPayActivity.this, count, new EditBucketCountDialog.EditPurchaseAmountConfirmListener() {
                    @Override
                    public void onEditPurchaseAmountConfirm(int count) {
                        BucketOrderPayActivity.this.count = count;
                        changeNum();
                    }
                });
                dialog.show();
                break;
        }
    }

    private void balancePay() {
        String number = account.getText().toString().trim();
        int num = Integer.parseInt(number);
        String money = moneyTv.getText().toString().trim();
        String substring = money.substring(1, money.length());
        showLoad();
        RetrofitUtils.getInstances().create().bucketPayment(mGid,num,substring).enqueue(new Callback<EntityObject<String>>() {
            @Override
            public void onResponse(Call<EntityObject<String>> call, Response<EntityObject<String>> response) {
                hintLoad();
                EntityObject<String> body = response.body();
                KLog.d(111);
                if (body.getCode() == 200){
                    KLog.d(22);
                    ToastUitl.showToastCustom("支付成功");
                    finish();
                    startActivity(new Intent(BucketOrderPayActivity.this,BucketOrderActivity.class));
//                    BucketPayBean result = body.getResult();
//                    getChannelInfo(result.getBucket_order_sn());
                }else {
                    ToastUitl.showToastCustom(body.getMsg());
                }
            }

            @Override
            public void onFailure(Call<EntityObject<String>> call, Throwable t) {

            }
        });
    }

    private void postPay() {
        KLog.d(moneyTv.getText().toString());
        RetrofitUtils.getInstances().create().buyEmptyBucket(data.getGid(), count, moneyTv.getText().toString(), pay_type == 1 ? "wx" : "alipay").enqueue(new Callback<EntityObject<BucketPayBean>>() {
            @Override
            public void onResponse(Call<EntityObject<BucketPayBean>> call, Response<EntityObject<BucketPayBean>> response) {
                EntityObject<BucketPayBean> body = response.body();
                if (body.getCode() == 200) {
                    BucketPayBean result = body.getResult();
                    getChannelInfo(result.getBucket_order_sn());
                } else {
                    ToastUitl.showToastCustom(body.getMsg());
                }
            }

            @Override
            public void onFailure(Call<EntityObject<BucketPayBean>> call, Throwable t) {

            }
        });
    }

    private void getChannelInfo(String orderNo) {
        showLoad();
        RetrofitUtils.getInstances().create().getPayChannelInfo(orderNo, pay_type == 1 ? "wx" : "alipay").enqueue(new Callback<EntityObject<PayBean>>() {
            @Override
            public void onResponse(Call<EntityObject<PayBean>> call, Response<EntityObject<PayBean>> response) {
                hintLoad();
                EntityObject<PayBean> body = response.body();
                hint(body.getMsg());
                Pingpp.createPayment(BucketOrderPayActivity.this, new Gson().toJson(body.getResult()));
            }

            @Override
            public void onFailure(Call<EntityObject<PayBean>> call, Throwable t) {

            }
        });


    }


    /**
     * 计算金额
     */
    private void changeNum() {
        account.setText(count + "");
        double d = Double.parseDouble(price);
        moneyTv.setText("￥" + CalculateUtils.mul(d, count));
    }


}
