package com.shuiwangzhijia.shuidian.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.TicketCountBean;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 我的钱包
 * created by wangsuli on 2018/9/26.
 */
public class WalletActivity extends BaseAct {
    @BindView(R.id.unit)
    TextView unit;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.llCash)
    LinearLayout llCash;
    @BindView(R.id.llDetail)
    LinearLayout llDetail;
    @BindView(R.id.ticket)
    TextView ticket;
    @BindView(R.id.llTicket)
    LinearLayout llTicket;
    private String moneyStr;
    private TicketCountBean mTicketCountBean;

    public static void startAtc(Context context, String total) {
        Intent intent = new Intent(context, WalletActivity.class);
        intent.putExtra("money", total);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);
        setTitle("我的钱包");
        setTitleBarBgColor(R.color.color_30adfd);
        moneyStr = getIntent().getStringExtra("money");
        money.setText(moneyStr);
        getCount();
    }

    private void getCount() {
        RetrofitUtils.getInstances().create().showMyTicketsCount().enqueue(new Callback<EntityObject<TicketCountBean>>() {
            @Override
            public void onResponse(Call<EntityObject<TicketCountBean>> call, Response<EntityObject<TicketCountBean>> response) {
                EntityObject<TicketCountBean> body = response.body();
                if(body.getCode()==200){
                    mTicketCountBean = body.getResult();
                    ticket.setText("可使用水票"+mTicketCountBean.getUse()+"张");
                }
            }

            @Override
            public void onFailure(Call<EntityObject<TicketCountBean>> call, Throwable t) {

            }
        });
    }

    @OnClick({R.id.llCash, R.id.llDetail, R.id.llTicket})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llCash:
               // CashActivity.startAtc(this, moneyStr);
                break;
            case R.id.llDetail:
               // startActivity(new Intent(this, ConsumerDetailActivity.class));
                break;
            case R.id.llTicket:
                if(mTicketCountBean==null){
                    ToastUitl.showToastCustom("正加载数据...");
                    return;
                }
                TicketActivity.startAtc(this,mTicketCountBean);
                break;
        }
    }
}
