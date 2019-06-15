package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.CustomerDetailBean;
import com.shuiwangzhijia.shuidian.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 客户明细适配器
 * created by wangsuli on 2018/8/17.
 */
public class CustomerDetailAdapter extends RecyclerView.Adapter<CustomerDetailAdapter.ViewHolder> {
    private final Context mContext;

    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private List<CustomerDetailBean> data;


    public CustomerDetailAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_customer_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CustomerDetailBean bean = data.get(position);
        holder.orderId.setText("订单号" + bean.getOrder_sn());
        holder.date.setText("下单时间:" + DateUtils.getFormatDateStr(bean.getCreate_time() * 1000));
        holder.num.setText("" + bean.getNum());
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        onRecyclerViewItemClickListener = listener;
    }

    public void setData(List<CustomerDetailBean> result) {
        data = result;
        notifyDataSetChanged();
    }

    public void addData(List<CustomerDetailBean> result) {
        data.addAll(result);
        notifyDataSetChanged();
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.orderId)
        TextView orderId;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.num)
        TextView num;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
