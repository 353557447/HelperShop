package com.shuiwangzhijia.shuidian.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.Constant;
import com.shuiwangzhijia.shuidian.bean.BucketBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 空桶信息控件
 * created by wangsuli on 2018/10/17.
 */
public class BucketView extends LinearLayout {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.shopName)
    TextView shopName;
    @BindView(R.id.priceTv)
    TextView priceTv;
    @BindView(R.id.numTv)
    TextView numTv;
    @BindView(R.id.payBtn)
    TextView payBtn;
    @BindView(R.id.policyHint)
    TextView policyHint;
    @BindView(R.id.llPolicy)
    LinearLayout llPolicy;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private BucketBean bean;


    public BucketView(Context context) {
        super(context);
    }

    public BucketView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        //加载视图的布局
        LayoutInflater.from(context).inflate(R.layout.bucket_view, this, true);
        ButterKnife.bind(this);
    }

    public void initData(BucketBean data) {
        bean = data;
        //0 表示去购买空桶 1显示数量信息 2支付页面
        switch (data.getType()) {
            case 0:
                payBtn.setVisibility(VISIBLE);
                numTv.setVisibility(GONE);
                priceTv.setText("￥" + data.getEmpty_price());
                break;
            case 1:
                payBtn.setVisibility(GONE);
                numTv.setVisibility(VISIBLE);
                numTv.setText("x" + bean.getGoods().get(0).getNum());
                priceTv.setText("￥" + data.getGoods().get(0).getEmpty_price());
                break;
            case 2:
                payBtn.setVisibility(GONE);
                numTv.setVisibility(GONE);
                priceTv.setText("￥" + data.getEmpty_price());
                break;

        }
        if (data.getType() == 1){
            titleTv.setText(data.getGoods().get(0).getGname()+"空桶");
            shopName.setText(data.getGoods().get(0).getSname());
            Glide.with(context).load(Constant.GOODS_IMAGE_URL + data.getGoods().get(0).getPicturl()).placeholder(R.color.color_eeeeee).into(image);
            setTextStyle(policyHint, "政策说明:", data.getGoods().get(0).getEmpty_policy());
        }else{
            titleTv.setText(data.getGname()+"空桶");
            shopName.setText(data.getSname());
            Glide.with(context).load(Constant.GOODS_IMAGE_URL + data.getPicturl()).placeholder(R.color.color_eeeeee).into(image);
            setTextStyle(policyHint, "政策说明:", data.getEmpty_policy());
            if (data.isPolicyShow()) {
                policyHint.setVisibility(VISIBLE);
            } else {
                policyHint.setVisibility(GONE);
            }
        }
    }

    private void setTextStyle(TextView text, String first, String content) {
        SpannableString spanString = new SpannableString(first + content);
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textBlack)), 0, first.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        text.setText(spanString);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    @OnClick(R.id.payBtn)
    public void onViewClicked() {
        onItemClickListener.onPayClick(bean);
    }


    public interface OnItemClickListener {
        void onPayClick(BucketBean data);
    }
}
