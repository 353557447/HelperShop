package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.SailOrderListBean;
import com.shuiwangzhijia.shuidian.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订单适配器
 * created by wangsuli on 2018/8/17.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private final Context mContext;
    private final int type;

    private OnViewItemClickListener mOnViewItemClickListener;


    public void setData(List<SailOrderListBean> data) {
        this.dataSail = data;
        notifyDataSetChanged();
    }

    public void addData(List<SailOrderListBean> data) {
        this.dataSail.addAll(data);
        notifyDataSetChanged();
    }

    private List<SailOrderListBean> dataSail;


    public OrderAdapter(Context context, int type) {
        mContext = context;
        this.type = type;
        dataSail = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SailOrderListBean item = getItem(position);

        holder.orderId.setText("订单号:" + item.getOrder_no());
        holder.orderDate.setText("下单时间:" + DateUtils.getFormatDateStr((item.getOrder_time() * 1000)));
        holder.shopName.setText(item.getSjname());
        String buk = item.getBuk();
        if (TextUtils.isEmpty(buk) || buk.equals("1")) {
            holder.bucket.setText("有桶");
        } else {
            holder.bucket.setText("无桶");
        }
        holder.shopName.setVisibility(View.GONE);
        holder.llBuyInfo.setVisibility(View.VISIBLE);
        holder.contacts.setText("联系人:" + item.getSjname());
        holder.sendTime.setText("送水时间:" + item.getNeed_time());
        holder.remark.setText("备注:" + (TextUtils.isEmpty(item.getRemark())?"":item.getRemark()));
        setTextStyle(holder.count, "共", "" + item.getTnum(), "件");
        setTextStyle(holder.totalMoney, "合计:", "" + item.getTotal_price(), "元");
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        holder.recyclerView.setHasFixedSize(true);
//            DividerItemDecoration divider = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
//            divider.setDrawable(mContext.getResources().getDrawable(R.drawable.divider_bg_white));
//            holder.recyclerView.addItemDecoration(divider);
        OrderItemAdapter mOrderAdapter = new OrderItemAdapter(mContext, false);
        mOrderAdapter.setData(item.getGoods());
        holder.recyclerView.setAdapter(mOrderAdapter);
        if (type == 0) {
            holder.firstBtn.setText("接单");
            holder.secondBtn.setVisibility(View.INVISIBLE);
        } else if (type == 1) {
            holder.firstBtn.setText("开始配送");
            holder.secondBtn.setVisibility(View.INVISIBLE);
        } else if (type == 2) {
            holder.firstBtn.setText("确认到达");
            holder.secondBtn.setVisibility(View.INVISIBLE);
        } else {
            holder.firstBtn.setVisibility(View.INVISIBLE);
            holder.secondBtn.setVisibility(View.INVISIBLE);
        }
        holder.firstBtn.setTag(position);
        holder.secondBtn.setTag(position);
        holder.secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) view.getTag();


            }
        });
        holder.firstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) view.getTag();
                SailOrderListBean tagObject = getItem(tag);
                if (type == 0) {
                    //接单
                    mOnViewItemClickListener.onReceiptOrderClick(tagObject.getOrder_no());
                } else if (type == 1) {
                    //开始配送
                    mOnViewItemClickListener.onSendClick(tagObject.getOrder_no(), 1);
                } else if (type == 2) {
                    //确认到达
                    mOnViewItemClickListener.onFinishClick(tagObject.getOrder_no(), 2);
                }
            }
        });
        holder.recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return holder.itemView.onTouchEvent(event);
            }
        });

    }

    private void setTextStyle(TextView text, String first, String content, String second) {
        SpannableString spanString = new SpannableString(first + content + second);
        spanString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_A53533)), first.length(), (content + first).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        spanString.setSpan(new AbsoluteSizeSpan(14, true), first.length(), (content + first).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);//字体大小
        text.setText(spanString);
    }

    public SailOrderListBean getItem(int position) {
        return dataSail.get(position);
    }

    @Override
    public int getItemCount() {
        return (dataSail == null) ? 0 : dataSail.size();
    }

    public void setOnItemClickListener(OnViewItemClickListener listener) {
        mOnViewItemClickListener = listener;
    }


    public interface OnViewItemClickListener {

        void onReceiptOrderClick(String orderNo);

        void onSendClick(String orderNo, int status);

        void onFinishClick(String orderNo, int status);

        void onCallClick(String phone);

        void onPayClick(String orderNo, long time, String price);

        void onCancelClick(String orderNo);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.orderId)
        TextView orderId;
        @BindView(R.id.orderDate)
        TextView orderDate;
        @BindView(R.id.shopName)
        TextView shopName;
        @BindView(R.id.contacts)
        TextView contacts;
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.sendTime)
        TextView sendTime;
        @BindView(R.id.remark)
        TextView remark;
        @BindView(R.id.state)
        TextView state;
        @BindView(R.id.bucket)
        TextView bucket;
        @BindView(R.id.llBuyInfo)
        LinearLayout llBuyInfo;
        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;
        @BindView(R.id.count)
        TextView count;
        @BindView(R.id.totalMoney)
        TextView totalMoney;
        @BindView(R.id.secondBtn)
        TextView secondBtn;
        @BindView(R.id.firstBtn)
        TextView firstBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
