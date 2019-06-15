package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.BucketBean;
import com.shuiwangzhijia.shuidian.utils.DateUtils;
import com.shuiwangzhijia.shuidian.view.BucketView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BucketOrderAdapter extends RecyclerView.Adapter<BucketOrderAdapter.ViewHolder> {
    private final Context mContext;
    private final int type;//0 表示去购买空桶 1显示数量信息 2 修改购买数量


    private OnViewItemClickListener mOnViewItemClickListener;


    public void setData(List<BucketBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<BucketBean> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    private List<BucketBean> data;


    public BucketOrderAdapter(Context context, int type) {
        mContext = context;
        this.type = type;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_bucket_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        BucketBean item = getItem(position);
        item.setType(type);
        switch (type){
            case 0:
                item.setPolicyShow(true);
                holder.llOrder.setVisibility(View.GONE);
                holder.llTotal.setVisibility(View.GONE);
                break;
            case 1:
                item.setPolicyShow(item.getOrder_type() == 0);
                break;
        }
        holder.bucketView.initData(item);
        holder.bucketView.setOnItemClickListener(new BucketView.OnItemClickListener() {
            @Override
            public void onPayClick(BucketBean data) {
                mOnViewItemClickListener.onPayClick(data);
            }
        });
        holder.orderId.setText("订单号:" + item.getBucket_order_sn());
        holder.orderDate.setText("下单时间:" + DateUtils.getFormatDateStr((item.getOrder_time() * 1000L)));
        setTextStyle(holder.count, "共", "" + item.getSum(), "件");
        setTextStyle(holder.totalMoney, "合计:", "" + item.getTotal_price(), "元");
    }

    private void setTextStyle(TextView text, String first, String content, String second) {
        SpannableString spanString = new SpannableString(first + content + second);
        spanString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_db2520)), first.length(), (content + first).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        spanString.setSpan(new AbsoluteSizeSpan(16, true), first.length(), (content + first).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);//字体大小
        text.setText(spanString);
    }

    public BucketBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    public void setOnItemClickListener(OnViewItemClickListener listener) {
        mOnViewItemClickListener = listener;
    }


    public interface OnViewItemClickListener {
        void onPayClick(BucketBean data);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.orderId)
        TextView orderId;
        @BindView(R.id.state)
        TextView state;
        @BindView(R.id.llOrder)
        LinearLayout llOrder;
        @BindView(R.id.orderDate)
        TextView orderDate;
        @BindView(R.id.bucketView)
        BucketView bucketView;
        @BindView(R.id.count)
        TextView count;
        @BindView(R.id.totalMoney)
        TextView totalMoney;
        @BindView(R.id.llTotal)
        LinearLayout llTotal;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
