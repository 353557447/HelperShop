package com.shuiwangzhijia.shuidian.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.BucketOrderItemAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.BucketBean;
import com.shuiwangzhijia.shuidian.bean.EmptyTongBean;
import com.shuiwangzhijia.shuidian.bean.PayBean;
import com.shuiwangzhijia.shuidian.event.PayFinishEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.DateUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;
import com.google.gson.Gson;
import com.pingplusplus.android.Pingpp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetermineBuyActivity extends BaseAct {
    @BindView(R.id.order_nummber)
    TextView mOrderNummber;
    @BindView(R.id.data)
    TextView mData;
    @BindView(R.id.price)
    TextView mPrice;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.onlineBtn)
    CheckBox mOnlineBtn;
    @BindView(R.id.weChatBtn)
    TextView mWeChatBtn;
    @BindView(R.id.aliPayBtn)
    TextView mAliPayBtn;
    @BindView(R.id.llOnline)
    LinearLayout mLlOnline;
    @BindView(R.id.sureBtn)
    TextView mSureBtn;
    private BucketBean data;
    private int pay_type = 1;//0表示货到付款 1微信 2 支付宝

    public static void startAtc(Context context, BucketBean data) {
        Intent intent = new Intent(context, DetermineBuyActivity.class);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_determine_buy);
        ButterKnife.bind(this);
        setTitle("确定购买");
        data = (BucketBean) getIntent().getSerializableExtra("data");
        initData();
        changeState(1);
    }

    private void changeState(int type) {
        pay_type = type;
        mWeChatBtn.setSelected(type == 1 ? true : false);
        mAliPayBtn.setSelected(type == 2 ? true : false);
    }

    private void initData() {
        List<EmptyTongBean> goods = data.getGoods();
        mOrderNummber.setText("订单号：" + data.getBucket_order_sn());
        mData.setText("下单时间：" + DateUtils.getFormatDateStr(data.getOrder_time() * 1000L));
        mPrice.setText("¥" + data.getTotal_price());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        BucketOrderItemAdapter mOrderAdapter = new BucketOrderItemAdapter(this, goods);
        mOrderAdapter.setData(goods);
        mRecyclerView.setAdapter(mOrderAdapter);
    }

    @OnClick({R.id.weChatBtn, R.id.aliPayBtn, R.id.sureBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.weChatBtn:
                changeState(1);
                break;
            case R.id.aliPayBtn:
                changeState(2);
                break;
            case R.id.sureBtn:
                postPay();
                break;
        }
    }

    private void postPay() {
        getChannelInfo(data.getBucket_order_sn());
    }

    private void getChannelInfo(String orderNo) {
        showLoad();
        RetrofitUtils.getInstances().create().getPayChannelInfo(orderNo, pay_type == 1 ? "wx" : "alipay").enqueue(new Callback<EntityObject<PayBean>>() {
            @Override
            public void onResponse(Call<EntityObject<PayBean>> call, Response<EntityObject<PayBean>> response) {
                hintLoad();
                EntityObject<PayBean> body = response.body();
                hint(body.getMsg());
                Pingpp.createPayment(DetermineBuyActivity.this, new Gson().toJson(body.getResult()));
            }

            @Override
            public void onFailure(Call<EntityObject<PayBean>> call, Throwable t) {

            }
        });
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
                    EventBus.getDefault().post(new PayFinishEvent());
                    startActivity(new Intent(DetermineBuyActivity.this, BucketOrderActivity.class));
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
}
