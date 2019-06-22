package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.RMCheckOutOrderListBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SettleAccountsAdapter extends RecyclerView.Adapter<SettleAccountsAdapter.ViewHolder> {
    private Context mContext;
    private List<RMCheckOutOrderListBean.DataBean.ListBean> data;
    private final SimpleDateFormat mSimpleDateFormat;

    public SettleAccountsAdapter(Context context) {
        this.mContext = context;
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_settle_accounts, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        RMCheckOutOrderListBean.DataBean.ListBean listBean = data.get(position);
        List<RMCheckOutOrderListBean.DataBean.ListBean.GoodsBean> goods = listBean.getGoods();
        holder.mOrderNum.setText("订单号："+listBean.getOrder_sn());
        holder.mOrderTime.setText("下单时间："+mSimpleDateFormat.format(new Date(listBean.getCreate_time()*1000)));
        holder.mTotalCounts.setText("共"+listBean.getGoods_num()+"桶");
        holder.mRv.setLayoutManager(new LinearLayoutManager(mContext));
        SettleAccountsSonAdapter settleAccountsSonAdapter = new SettleAccountsSonAdapter(mContext);
        settleAccountsSonAdapter.setData(goods);
        holder.mRv.setAdapter(settleAccountsSonAdapter);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(position);
            }
        });
    }


    public void setData(List<RMCheckOutOrderListBean.DataBean.ListBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.order_num)
        TextView mOrderNum;
        @BindView(R.id.order_time)
        TextView mOrderTime;
        @BindView(R.id.total_counts)
        TextView mTotalCounts;
        @BindView(R.id.rv)
        RecyclerView mRv;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
