package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.SpEarnWdRecordBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SpEarnWDRecordAdapter extends RecyclerView.Adapter<SpEarnWDRecordAdapter.ViewHolder> {
    private Context mContext;
    private List<SpEarnWdRecordBean.DataBean> data;
    private final SimpleDateFormat mSimpleDateFormat;

    public SpEarnWDRecordAdapter(Context context) {
        this.mContext = context;
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sp_earn_wd_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        SpEarnWdRecordBean.DataBean dataBean = data.get(position);
        holder.mOrderNum.setText("提现单号：" + dataBean.getForward_code());
        holder.mTime.setText("申请时间：" + mSimpleDateFormat.format(new Date(dataBean.getAdd_time() * 1000)));
        holder.mMoney.setText("￥" + dataBean.getAmount());
        int status = dataBean.getStatus();
        // 0申请中，1提现中，2已完成，3提现失败
        switch (status) {
            case 0:
                holder.mOrderStatus.setText("申请中");
                holder.mReasonLl.setVisibility(View.GONE);
                break;
            case 1:
                holder.mOrderStatus.setText("提现中");
                holder.mReasonLl.setVisibility(View.GONE);
                break;
            case 2:
                holder.mOrderStatus.setText("已完成");
                holder.mReasonLl.setVisibility(View.GONE);
                break;
            case 3:
                holder.mOrderStatus.setText("提现失败");
                holder.mReason.setText("原因：" + dataBean.getRefuse());
                holder.mReasonLl.setVisibility(View.VISIBLE);
                break;
            case 4:
                holder.mOrderStatus.setText("提现中");
                holder.mReasonLl.setVisibility(View.GONE);
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(position);
            }
        });
    }


    public void setData(List<SpEarnWdRecordBean.DataBean> data) {
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
        @BindView(R.id.order_status)
        TextView mOrderStatus;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.money)
        TextView mMoney;
        @BindView(R.id.reason)
        TextView mReason;
        @BindView(R.id.reason_ll)
        LinearLayout mReasonLl;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
