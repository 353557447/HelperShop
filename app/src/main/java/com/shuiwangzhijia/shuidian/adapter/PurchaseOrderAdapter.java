package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.BuyOrderListBean;
import com.shuiwangzhijia.shuidian.utils.DateUtils;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PurchaseOrderAdapter extends RecyclerView.Adapter<PurchaseOrderAdapter.ViewHolder> implements PurchaseOrderItemAdapter.OnViewItemClickListener {
    private final Context mContext;
    private OnViewItemClickListener mOnViewItemClickListener;


    public void setData(List<BuyOrderListBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<BuyOrderListBean> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    private List<BuyOrderListBean> data;


    public PurchaseOrderAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_purchase_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        BuyOrderListBean item = getItem(position);
        holder.orderId.setText("订单号:" + item.getOrder_no());
        TextPaint paint = holder.orderId.getPaint();
        paint.setFakeBoldText(true);
        holder.orderDate.setText("下单时间:" + DateUtils.getFormatDateStr((item.getOrder_time() * 1000)));
        int payStatus = item.getPay_status();
        int delivery_type = item.getDelivery_type();
        int dstutas = item.getDstutas();
        if (delivery_type == 0) {
            holder.mOrdertype.setText("订单类型:" + "配送订单");
            holder.mOrderPickUp.setVisibility(View.GONE);
        } else if (delivery_type == 1) {
            holder.mOrdertype.setText("订单类型:" + "自提订单");
        }
        int firstIndex = -1;
        final int status = item.getStatus();
        //配送
        if (delivery_type == 0) {
            switch (status) {
                case 0:
                    holder.state.setText("待支付");
                    holder.firstBtn.setText("去支付");
                    holder.secondBtn.setText("取消支付");
                    holder.firstBtn.setVisibility(View.VISIBLE);
                    holder.secondBtn.setVisibility(View.VISIBLE);
                    firstIndex = 0;
                    break;
                case 1:
                    firstIndex = 1;
                    holder.state.setText("待配送");
                    holder.firstBtn.setText("联系厂家");
                    holder.firstBtn.setVisibility(View.GONE);
                    holder.secondBtn.setVisibility(View.GONE);
                    break;
                case 2:
                    firstIndex = 1;
                    holder.state.setText("配送中");
                    holder.firstBtn.setText("联系厂家");
                    holder.firstBtn.setVisibility(View.GONE);
                    holder.secondBtn.setVisibility(View.GONE);
                    break;
                case 3:
                    firstIndex = 2;
                    holder.state.setText("已完成");
                    holder.firstBtn.setText("再来一单");
                    holder.firstBtn.setVisibility(View.VISIBLE);
                    holder.secondBtn.setVisibility(View.GONE);
                    break;
                case 4:
                    firstIndex = 0;
                    holder.state.setText("赊账中");
                    holder.firstBtn.setText("去支付");
                    holder.secondBtn.setText("取消支付");
                    holder.firstBtn.setVisibility(View.VISIBLE);
                    holder.secondBtn.setVisibility(View.GONE);
                    break;
                case 5:
                    holder.state.setText("已取消");
                    holder.firstBtn.setVisibility(View.GONE);
                    holder.secondBtn.setVisibility(View.GONE);
                    break;
//            }
            }
        } else if (delivery_type == 1) {
            //自提
            switch (dstutas) {
                case 0:
                    holder.state.setText("待支付");
                    holder.firstBtn.setText("去支付");
                    holder.secondBtn.setText("取消支付");
                    holder.firstBtn.setVisibility(View.VISIBLE);
                    holder.secondBtn.setVisibility(View.VISIBLE);
                    firstIndex = 0;
                    break;
                case 1:
                    //待提货，有提货操作按钮
                    firstIndex = 3;
                    holder.state.setText("待提货");
                    holder.firstBtn.setText("提货操作");
                    holder.secondBtn.setText("取消支付");
                    holder.firstBtn.setVisibility(View.VISIBLE);
                    holder.secondBtn.setVisibility(View.GONE);
                    break;
                case 2:
                    //待提货，没有提货操作按钮
                    holder.state.setText("待提货");
                    holder.firstBtn.setText("提货操作");
                    holder.secondBtn.setText("取消支付");
                    holder.firstBtn.setVisibility(View.GONE);
                    holder.secondBtn.setVisibility(View.GONE);
                    holder.mOrderPickUp.setVisibility(View.VISIBLE);
                    holder.mOrderPickUp.setText("提货单号:"+item.getOut_order());
                    break;
                case 3:
                    firstIndex = 2;
                    holder.state.setText("已完成");
                    holder.firstBtn.setText("再来一单");
                    holder.firstBtn.setVisibility(View.VISIBLE);
                    holder.secondBtn.setVisibility(View.GONE);
                    holder.mOrderPickUp.setVisibility(View.VISIBLE);
                    holder.mOrderPickUp.setText("提货单号:"+item.getOut_order());
                    break;
                case 4:
                    firstIndex = 0;
                    holder.state.setText("赊账中");
                    holder.firstBtn.setText("去支付");
                    holder.secondBtn.setText("取消支付");
                    holder.firstBtn.setVisibility(View.VISIBLE);
                    holder.secondBtn.setVisibility(View.GONE);
                    break;
                case 5:
                    holder.state.setText("已取消");
                    holder.firstBtn.setVisibility(View.GONE);
                    holder.secondBtn.setVisibility(View.GONE);
                    break;
            }
        }

        holder.count.setText("共" + item.getTnum()+ "件合计:");
      //  setTextStyle(holder.count, "共", "" + item.getTnum(), "件");
      //  setTextStyle(holder.totalMoney, "合计:", "" + item.getTotal_price(), "元");
        holder.totalMoney.setText("￥"+item.getTotal_price());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        holder.recyclerView.setHasFixedSize(true);
//            DividerItemDecoration divider = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
//            divider.setDrawable(mContext.getResources().getDrawable(R.drawable.divider_bg_white));
//            holder.recyclerView.addItemDecoration(divider);
        PurchaseOrderItemAdapter mOrderAdapter = new PurchaseOrderItemAdapter(mContext);
        mOrderAdapter.setOnItemClickListener(this);
        mOrderAdapter.setData(item.getList());
        holder.recyclerView.setAdapter(mOrderAdapter);

        holder.firstBtn.setTag(position);
        holder.secondBtn.setTag(position);
        holder.secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) view.getTag();
                mOnViewItemClickListener.onCancelClick(getItem(tag).getOrder_no());

            }
        });
        final int finalFirstIndex = firstIndex;
        holder.firstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) view.getTag();
                BuyOrderListBean bean = getItem(tag);
                KLog.e(bean.getOrder_no());
                KLog.e(bean.getOrder_noo());
                switch (finalFirstIndex) {
                    case 0:
                        mOnViewItemClickListener.onPayClick(bean.getOrder_noo(), bean.getOrder_time(), bean.getTotal_price(), status,bean.getDstutas(),bean.getDid());
                        break;
                    case 1:
                        mOnViewItemClickListener.onCallClick(bean.getList().get(0).getPhone());
                        break;
                    case 2:
                        mOnViewItemClickListener.onMoreOrderClick(tag);
                        break;
                    case 3:
                        mOnViewItemClickListener.onTakeOperation(tag);
                        break;
                }

            }
        });
        holder.recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return holder.itemView.onTouchEvent(event);
            }
        });
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnViewItemClickListener.onDetailClick((int) view.getTag());
            }
        });
    }

    private void setTextStyle(TextView text, String first, String content, String second) {
        SpannableString spanString = new SpannableString(first + content + second);
        spanString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_A53533)), first.length(), (content + first).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        spanString.setSpan(new AbsoluteSizeSpan(14, true), first.length(), (content + first).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);//字体大小
        text.setText(spanString);
    }

    public BuyOrderListBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    public void setOnItemClickListener(OnViewItemClickListener listener) {
        mOnViewItemClickListener = listener;
    }

    @Override
    public void onCallClick(String phone) {
        mOnViewItemClickListener.onCallClick(phone);
    }

    public interface OnViewItemClickListener {

        void onMoreOrderClick(int position);

        void onSendClick(String orderNo, int status);

        void onFinishClick(String orderNo, int status);

        void onCallClick(String phone);

        void onDetailClick(int position);

        void onPayClick(String orderNo, long time, String price, int status , int bstatus,int did);

        void onCancelClick(String orderNo);

        void onTakeOperation(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.orderId)
        TextView orderId;
        @BindView(R.id.orderDate)
        TextView orderDate;
        @BindView(R.id.state)
        TextView state;
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
        @BindView(R.id.ordertype)
        TextView mOrdertype;
        @BindView(R.id.order_pick_up)
        TextView mOrderPickUp;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
