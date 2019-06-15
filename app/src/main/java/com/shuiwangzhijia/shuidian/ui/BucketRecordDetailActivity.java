package com.shuiwangzhijia.shuidian.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.BucketBean;
import com.shuiwangzhijia.shuidian.bean.EmptyTongBean;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.DateUtils;
import com.shuiwangzhijia.shuidian.view.BucketView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 空桶记录详情页面
 * created by wangsuli on 2018/10/17.
 */
public class BucketRecordDetailActivity extends BaseAct {

    @BindView(R.id.recordName)
    TextView recordName;
    @BindView(R.id.dateTv)
    TextView dateTv;
    @BindView(R.id.waterProdName)
    TextView waterProdName;
    @BindView(R.id.bucketNumTv)
    TextView bucketNumTv;
    @BindView(R.id.returnMoneyTv)
    TextView returnMoneyTv;
    @BindView(R.id.bucketView)
    BucketView bucketView;
    private int orderNo;
    private String payTitle;
    private BucketBean bucketDetailData;
    private int type,status;

    public static void startAtc(Context context, int orderNo, int type,int status) {
        Intent intent = new Intent(context, BucketRecordDetailActivity.class);
        intent.putExtra("orderNo", orderNo);
        intent.putExtra("type", type);
        intent.putExtra("status", status);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_record_detail);
        ButterKnife.bind(this);
        setTitle("记录详情");
        orderNo = getIntent().getIntExtra("orderNo",-1);
        type = getIntent().getIntExtra("type", -1);
        status = getIntent().getIntExtra("status", -1);
        String name = "";
        switch (status) {
            case 0:
                name = "购桶";
                payTitle = "支付金额";
                break;
            case 1:
                name = "回桶";
                returnMoneyTv.setVisibility(View.GONE);
                break;
            case 2:
                name = "押桶";
                payTitle = "支付金额";
                break;
            case 3:
                name = "退桶";
                payTitle = "支付金额";
                break;
            case 4:
                name = "换桶";
                payTitle = "支付金额";
                break;
            case 5:
                name = "订单桶";
                payTitle = "返还金额";
                returnMoneyTv.setVisibility(View.GONE);
                break;
        }
        setTextStyle(recordName, "记录名称:   ", name);
        getData();
    }

    private void getData() {
        showLoad();
        RetrofitUtils.getInstances().create().getEmptyBucketDetail(orderNo,status).enqueue(new Callback<EntityObject<BucketBean>>() {
            @Override
            public void onResponse(Call<EntityObject<BucketBean>> call, Response<EntityObject<BucketBean>> response) {
                hintLoad();
                EntityObject<BucketBean> body = response.body();
                if (body.getCode() == 200) {
                    bucketDetailData = body.getResult();

                    setTextStyle(dateTv, "操作时间:   ", DateUtils.getFormatDateStr(Integer.parseInt(bucketDetailData.getUpdate_time())*1000L));
                    setTextStyle(waterProdName, "水厂名称:   ", bucketDetailData.getSname());
                    setTextStyle(bucketNumTv, "空桶数量:   ", bucketDetailData.getSum() + "");
                    setTextStyle(returnMoneyTv, payTitle + ":   ", "￥" + bucketDetailData.getTotal_price());
                    List<EmptyTongBean> list = bucketDetailData.getGoods();
                    if(list==null||list.size()==0){
                        return;
                    }
//                    BucketBean bucketBean = list.get(0);
                    bucketDetailData.setType(1);
                    bucketDetailData.setPolicyShow(type==0||type==2);
                    bucketView.initData(bucketDetailData);
                }
            }

            @Override
            public void onFailure(Call<EntityObject<BucketBean>> call, Throwable t) {

            }
        });
    }

    private void setTextStyle(TextView text, String first, String content) {
        SpannableString spanString = new SpannableString(first + content);
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_999999)), 0, first.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_333333)), first.length(), (content + first).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        text.setText(spanString);
    }
}
