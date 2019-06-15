package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
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
import com.shuiwangzhijia.shuidian.bean.EmptyTongBean;
import com.shuiwangzhijia.shuidian.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 空桶订单适配器
 * created by wangsuli on 2018/8/17.
 */
public class BucketOrderAdapterNew extends RecyclerView.Adapter<BucketOrderAdapterNew.ViewHolder> {
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


    public BucketOrderAdapterNew(Context context, int type) {
        mContext = context;
        this.type = type;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_bucket_order_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        BucketBean item = getItem(position);
        item.setType(type);
        switch (type){
            case 0:
                item.setPolicyShow(true);
//                holder.llOrder.setVisibility(View.GONE);
//                holder.llTotal.setVisibility(View.GONE);
                holder.state.setVisibility(View.VISIBLE);
                break;
            case 1:
                item.setPolicyShow(item.getOrder_type() == 0);
                holder.state.setVisibility(View.GONE);
                break;
        }
        if (item.getPay_status() == 0 && type == 0){
            holder.state.setText("未支付");
            holder.state.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.payBtn.setVisibility(View.VISIBLE);
        }else{
            holder.state.setText("已支付，待确认");
            holder.payBtn.setVisibility(View.GONE);
        }

        List<EmptyTongBean> goods = item.getGoods();
        holder.rv.setLayoutManager(new LinearLayoutManager(mContext));
        holder.rv.setHasFixedSize(true);
        BucketOrderItemAdapter mOrderAdapter = new BucketOrderItemAdapter(mContext, goods);
        mOrderAdapter.setData(item.getGoods());
        holder.rv.setAdapter(mOrderAdapter);

        holder.payBtn.setTag(position);
        holder.payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) view.getTag();
                mOnViewItemClickListener.onPayNow(tag);
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
        void onPayNow(int position);
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
        @BindView(R.id.count)
        TextView count;
        @BindView(R.id.totalMoney)
        TextView totalMoney;
        @BindView(R.id.llTotal)
        LinearLayout llTotal;
        @BindView(R.id.payBtn)
        TextView payBtn;
        @BindView(R.id.rv)
        RecyclerView rv;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
