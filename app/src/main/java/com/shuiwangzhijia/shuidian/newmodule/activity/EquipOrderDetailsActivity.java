package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.EquipOrderDetailsBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.DateUtils;
import com.shuiwangzhijia.shuidian.view.BaseBar;
import com.socks.library.KLog;
import com.yanzhenjie.permission.AndPermission;

import org.json.JSONObject;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_equip_order_details, title = "订单详情")
public class EquipOrderDetailsActivity extends BaseActivity {
    @BindView(R.id.refund)
    TextView mRefund;
    @BindView(R.id.equip_name)
    TextView mEquipName;
    @BindView(R.id.contact_merchant)
    TextView mContactMerchant;
    @BindView(R.id.goods_iv)
    ImageView mGoodsIv;
    @BindView(R.id.goods_name)
    TextView mGoodsName;
    @BindView(R.id.goods_price)
    TextView mGoodsPrice;
    @BindView(R.id.goods_counts)
    TextView mGoodsCounts;
    @BindView(R.id.goods_time)
    TextView mGoodsTime;
    @BindView(R.id.goods_total_price)
    TextView mGoodsTotalPrice;
    @BindView(R.id.order_user)
    TextView mOrderUser;
    @BindView(R.id.order_code)
    TextView mOrderCodeTv;
    @BindView(R.id.discount_price)
    TextView mDiscountPrice;
    @BindView(R.id.discount_coupon_price)
    TextView mDiscountCouponPrice;
    @BindView(R.id.equip_id)
    TextView mEquipId;
    @BindView(R.id.take_out_channel)
    TextView mTakeOutChannel;
    @BindView(R.id.order_time)
    TextView mOrderTime;
    @BindView(R.id.pay_method)
    TextView mPayMethod;
    @BindView(R.id.order_state_simple)
    TextView mOrderStateSimple;
    @BindView(R.id.order_state)
    TextView mOrderState;
    private String mOrderCode;
    private EquipOrderDetailsBean.DataBean mData;
    private boolean hasCallPermissions;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        mOrderCode = bundle.getString("orderCode");
        mBaseBar.setBarListener(new BaseBar.BarListener() {
            @Override
            public void onLeftClick() {
                closeActivity();
            }

            @Override
            public void onRightClick() {

            }
        });

    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().equipOrderDetails(mToken, mOrderCode).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        EquipOrderDetailsBean equipOrderDetailsBean = mGson.fromJson(datas, EquipOrderDetailsBean.class);
                        mData = equipOrderDetailsBean.getData();
                        setData();
                    } else {

                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                } finally {
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

    private boolean isOverThreeDays;

    private void setData() {
        int refundStatus = mData.getRefund_status();
        int payStatus = mData.getPay_status();
        if (refundStatus == 0) {
            mOrderStateSimple.setText("已完成");
            mOrderState.setText("订单已完成");
            mRefund.setVisibility(View.VISIBLE);
            long orderTime = mData.getOrder_time() * 1000;
            long currentTime = System.currentTimeMillis();
            long distanceTime = currentTime - orderTime;
            if (distanceTime >= 3 * 24 * 60 * 60 * 1000) {
                isOverThreeDays = true;
                mRefund.setTextColor(Color.parseColor("#9d9d9d"));
                mRefund.setBackgroundResource(R.drawable.gray_stroke_big_radius_bg);
            }
        } else {
            mOrderStateSimple.setText("已退款");
            mOrderState.setText("订单已退款");
            mRefund.setVisibility(View.GONE);
        }
        EquipOrderDetailsBean.DataBean.GoodsBean goodsBean = mData.getGoods().get(0);
        mEquipName.setText(mData.getDname());
        setImg(mGoodsIv, goodsBean.getGood_pic(), "goods/");
        mGoodsName.setText(goodsBean.getGname());
        mGoodsPrice.setText("￥" + goodsBean.getPrice());
        mGoodsCounts.setText("x" + goodsBean.getNum());
        long orderTime = mData.getOrder_time();
        String formatDateTime = DateUtils.getFormatDateTime(new Date(orderTime * 1000), "yyyy-MM-dd HH:mm:ss");
        mGoodsTime.setText(formatDateTime.split(" ")[0]);
        mGoodsTotalPrice.setText("共" + goodsBean.getNum() + "件商品合计：￥" + mData.getAmount_price());
        mOrderUser.setText(mData.getUser_name());
        mOrderCodeTv.setText(mData.getOrder_code());
        mDiscountPrice.setText("-￥" + mData.getDiscount_price());
        mDiscountCouponPrice.setText("-￥" + mData.getPicket_amount());
        mEquipId.setText(mData.getEquipment_number());
        mTakeOutChannel.setText(mData.getCabinet_number() + "号柜-" + mData.getLattice_number() + "柜位");
        mOrderTime.setText(formatDateTime);
        int payMethod = mData.getPay_method();
        //支付方式（0:默认 1：支付宝，2：微信）
        if (payMethod == 0) {
            mPayMethod.setText("暂无");
        } else if (payMethod == 1) {
            mPayMethod.setText("支付宝");
        } else {
            mPayMethod.setText("微信");
        }

    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.refund, R.id.contact_merchant})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.refund:
                if (isOverThreeDays) {
                    showToast("该订单已超过三天，不能退款~");
                    return;
                }
                mRxDialogSureCancel.setContent("您确认要退款么？");
                mRxDialogSureCancel.setSureListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRxDialogSureCancel.dismiss();
                        handleRefund();
                    }
                });
                mRxDialogSureCancel.show();
                break;
            case R.id.contact_merchant:
                handleCall();
                break;
        }
    }

    private void handleRefund() {
        String order_code = mData.getOrder_code();
        showLoadingDialog();
        RetrofitUtils.getInstances().create().equipOrderRefund(mToken, order_code).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    showToast(object.getString("msg"));
                    if (code == 200) {
                        getData();
                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                } finally {
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

    private void handleCall() {
        mRxDialogSureCancel.getTitleView().setText("确认拨打");
        //  final String phoneNum = mCustomerServiceTeleNum.getText().toString().trim();
        mRxDialogSureCancel.getContentView().setText("13066807122");
        //校验权限
        AndPermission.with(this)
                .requestCode(200)
                .permission(Manifest.permission.CALL_PHONE
                ).start();
        int hasCallPhonePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (hasCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
            hasCallPermissions = false;
        } else {
            hasCallPermissions = true;
        }
        mRxDialogSureCancel.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasCallPermissions) {
                    mRxDialogSureCancel.dismiss();
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "13066807122"));
                    startActivity(intent);
                } else {
                    showToast("暂无权限");
                }
            }
        });
        mRxDialogSureCancel.show();

    }
}
