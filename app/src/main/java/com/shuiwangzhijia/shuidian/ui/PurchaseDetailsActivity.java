package com.shuiwangzhijia.shuidian.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.PurchaseOrderItemAdapter;
import com.shuiwangzhijia.shuidian.adapter.ShuiPiaoAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.BuyOrderListBean;
import com.shuiwangzhijia.shuidian.bean.ChangeBucketBean;
import com.shuiwangzhijia.shuidian.bean.ChangeModeBean;
import com.shuiwangzhijia.shuidian.bean.PurchaseDetailsBean;
import com.shuiwangzhijia.shuidian.bean.WaterTicketBean;
import com.shuiwangzhijia.shuidian.dialog.HintDialog;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.DateUtils;
import com.socks.library.KLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseDetailsActivity extends BaseAct implements PurchaseOrderItemAdapter.OnViewItemClickListener {
    @BindView(R.id.orderId)
    TextView mOrderId;
    @BindView(R.id.state)
    TextView mState;
    @BindView(R.id.orderDate)
    TextView mOrderDate;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.count)
    TextView mCount;
    @BindView(R.id.totalMoney)
    TextView mTotalMoney;
    @BindView(R.id.shuipiao)
    RecyclerView mShuipiao;
    @BindView(R.id.huitong)
    RecyclerView mHuitong;
    @BindView(R.id.tuishui)
    RecyclerView mTuishui;
    @BindView(R.id.zatong_number)
    TextView mZatongNumber;
    @BindView(R.id.ziyingtong)
    RecyclerView mZiyingtong;
    @BindView(R.id.money)
    TextView mMoney;
    @BindView(R.id.status)
    TextView mStatus;
    @BindView(R.id.shuipiao_bottom)
    RecyclerView mShuipiaoBottom;
    @BindView(R.id.way)
    TextView mWay;
    @BindView(R.id.shuipiao_status)
    TextView mShuipiaoStatus;
    @BindView(R.id.huitong_status)
    TextView mHuitongStatus;
    @BindView(R.id.tuishui_status)
    TextView mTuishuiStatus;
    @BindView(R.id.zatong_status)
    TextView mZatongStatus;
    @BindView(R.id.root_zatong)
    LinearLayout mRootZatong;
    @BindView(R.id.potong_number)
    TextView mPotongNumber;
    @BindView(R.id.way_trade)
    TextView mWayTrade;
    private BuyOrderListBean data;

    public static void startAtc(Context context, BuyOrderListBean data) {
        Intent intent = new Intent(context, PurchaseDetailsActivity.class);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_details);
        ButterKnife.bind(this);
        setTitle("采购详情");
        data = (BuyOrderListBean) getIntent().getSerializableExtra("data");
        initView();
        getList();
    }

    private void getList() {
        showLoad();
        RetrofitUtils.getInstances().create().getOrderDetail(data.getOrder_no()).enqueue(new Callback<EntityObject<PurchaseDetailsBean>>() {
            @Override
            public void onResponse(Call<EntityObject<PurchaseDetailsBean>> call, Response<EntityObject<PurchaseDetailsBean>> response) {
                hintLoad();
                EntityObject<PurchaseDetailsBean> body = response.body();
                if (body.getCode() == 200) {
                    PurchaseDetailsBean result = body.getResult();
                    List<WaterTicketBean> water_ticket = result.getWater_ticket(); //水票
                    List<WaterTicketBean> recycler_water = result.getRecycler_water();//回桶
                    List<WaterTicketBean> refundwt_water = result.getRefundwt_water();//退水
                    if (water_ticket == null || water_ticket.size() == 0) {
                        mShuipiaoStatus.setVisibility(View.VISIBLE);
                    } else {
                        initWaterVoteRecycle(water_ticket);
                    }
                    if (recycler_water == null || recycler_water.size() == 0) {
                        mHuitongStatus.setVisibility(View.VISIBLE);
                    } else {
                        initRecyclerRv(recycler_water);
                    }
                    if (refundwt_water == null || refundwt_water.size() == 0) {
                        mTuishuiStatus.setVisibility(View.VISIBLE);
                    } else {
                        initRefundwtRv(refundwt_water);
                    }

                    //换杂桶情况
                    ChangeBucketBean change_bucket = result.getChange_bucket();
                    if (change_bucket != null) {
                        ChangeBucketBean.BucketBean bucket = change_bucket.getBucket();
                        //交易方式0补偿金额  1补偿水票
//                        int change_way = bucket.getChange_way();
//                        int pay_status = bucket.getPay_status();
//                        if (change_way == 0) {
//                            mWay.setText("支付金额");
//                            mMoney.setText("补金额： " + bucket.getTotal_price());
//                            mShuipiaoBottom.setVisibility(View.GONE);
//                        } else {
//                            mWay.setText("送水票");
//                            mMoney.setText("赠水票");
//                            mShuipiaoBottom.setVisibility(View.VISIBLE);
//                        }
//                        if (pay_status == 0) {
//                            mStatus.setText("未交易");
//                        } else {
//                            mStatus.setText("已交易");
//                        }
                        mZatongNumber.setText(bucket.getMix_num() + "");
                        mPotongNumber.setText(bucket.getPo_num() + "");
                        List<WaterTicketBean> goods = change_bucket.getGoods();
                        List<WaterTicketBean> waterTicket = change_bucket.getWater_ticket();
                        initZiyingRv(goods);
                        initShuiPiaoBottom(waterTicket);
                    } else {
                        mRootZatong.setVisibility(View.GONE);
                        mZatongStatus.setVisibility(View.VISIBLE);
                    }

                    //交易方式：
                    ChangeModeBean change_mode = result.getChange_mode();
                    if (change_mode != null) {
                        int change_way = change_mode.getChange_way();
                        int pay_status = change_mode.getPay_status();
                        if (change_way == 0) {
                            mWay.setText("支付金额");
                            mMoney.setText("补金额： " + change_mode.getTotal_price());
                            mShuipiaoBottom.setVisibility(View.GONE);
                        } else {
                            mWay.setText("送水票");
                            mMoney.setText("赠水票");
                            mShuipiaoBottom.setVisibility(View.VISIBLE);
                        }
                        if (pay_status == 0) {
                            mStatus.setText("未交易");
                        } else {
                            mStatus.setText("已交易");
                        }
                    }else {
                        mWayTrade.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<EntityObject<PurchaseDetailsBean>> call, Throwable t) {

            }
        });
    }

    private void initShuiPiaoBottom(List<WaterTicketBean> waterTicket) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mShuipiaoBottom.setLayoutManager(manager);
        ShuiPiaoAdapter adapter = new ShuiPiaoAdapter(this, waterTicket, 5);
        mShuipiaoBottom.setAdapter(adapter);
    }

    //换自营桶数量
    private void initZiyingRv(List<WaterTicketBean> goods) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mZiyingtong.setLayoutManager(manager);
        ShuiPiaoAdapter adapter = new ShuiPiaoAdapter(this, goods, 4);
        mZiyingtong.setAdapter(adapter);
    }

    private void initRefundwtRv(List<WaterTicketBean> refundwt_water) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mTuishui.setLayoutManager(manager);
        ShuiPiaoAdapter adapter = new ShuiPiaoAdapter(this, refundwt_water, 3);
        mTuishui.setAdapter(adapter);
    }

    //回桶recycleview
    private void initRecyclerRv(List<WaterTicketBean> recycler_water) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mHuitong.setLayoutManager(manager);
        ShuiPiaoAdapter adapter = new ShuiPiaoAdapter(this, recycler_water, 2);
        mHuitong.setAdapter(adapter);
    }

    //水票recycleview
    private void initWaterVoteRecycle(List<WaterTicketBean> water_ticket) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mShuipiao.setLayoutManager(manager);
        ShuiPiaoAdapter adapter = new ShuiPiaoAdapter(this, water_ticket, 1);
        mShuipiao.setAdapter(adapter);
    }

    private void initView() {
        mOrderId.setText("订单号：" + data.getOrder_no());
        TextPaint paint = mOrderId.getPaint();
        paint.setFakeBoldText(true);
        mOrderDate.setText("下单时间：" + DateUtils.getFormatDateStr(data.getOrder_time() * 1000L));
        int delivery_type = data.getDelivery_type();
        int dstutas = data.getDstutas();
        KLog.d(dstutas);
        if (delivery_type == 1){
            switch (dstutas) {
                case 0:
                    mState.setText("待支付");
                    break;
                case 1:
                    mState.setText("待提货");
                    break;
                case 2:
                    mState.setText("待提货");
                    break;
                case 3:
                    mState.setText("已完成");
                    break;
                case 4:
                    mState.setText("赊账中");
                    break;
                case 5:
                    mState.setText("已取消");
                    break;
            }
        }
//        int status = data.getStatus();

        setTextStyle(mCount, "共", "" + data.getTnum(), "桶");
        setTextStyle(mTotalMoney, "实际支付:", "￥" + data.getTotal_price(), "");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        PurchaseOrderItemAdapter mOrderAdapter = new PurchaseOrderItemAdapter(this);
        mOrderAdapter.setOnItemClickListener(this);
        mOrderAdapter.setData(data.getList());
        mRecyclerView.setAdapter(mOrderAdapter);
    }

    private void setTextStyle(TextView text, String first, String content, String second) {
        SpannableString spanString = new SpannableString(first + content + second);
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_ff4444)), first.length(), (content + first).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        spanString.setSpan(new AbsoluteSizeSpan(14, true), first.length(), (content + first).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);//字体大小
        text.setText(spanString);
    }

    @Override
    public void onCallClick(final String phone) {
        HintDialog hintDialog = new HintDialog(this, "确认拨打电话？", new HintDialog.OnConfirmClickListener() {
            @Override
            public void onConfirm(Dialog dialog) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + phone);
                intent.setData(data);
                startActivity(intent);
            }
        });
        hintDialog.show();
    }
}
