package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.RechargeRecordBean;
import com.shuiwangzhijia.shuidian.utils.DateUtils;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RechargeRecordAdapter extends RecyclerView.Adapter<RechargeRecordAdapter.ViewHolder> {
    private final Context mContext;

    private OnItemClickListener mOnItemClickListener;
    private List<RechargeRecordBean.DataBean.ListBean> data;


    public RechargeRecordAdapter(Context context) {
        mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recharge_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        RechargeRecordBean.DataBean.ListBean listBean = data.get(position);
        holder.mWaterFactoryName.setText(listBean.getDname());
        holder.mTime.setText(DateUtils.getFormatDateTime(new Date(listBean.getCreate_time()*1000),"yyyy-MM-dd HH:mm:ss"));
        holder.mPayMoney.setText("支付金额：￥"+listBean.getSamount());
        holder.mActualPayMoney.setText(listBean.getAmount());
    }

    public RechargeRecordBean.DataBean.ListBean getItem(int position) {
        return data.get(position);
    }

    public void deleteItem(int position) {
        data.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setData(List<RechargeRecordBean.DataBean.ListBean> result) {
        data = result;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.water_factory_name)
        TextView mWaterFactoryName;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.pay_money)
        TextView mPayMoney;
        @BindView(R.id.actual_pay_money)
        TextView mActualPayMoney;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
