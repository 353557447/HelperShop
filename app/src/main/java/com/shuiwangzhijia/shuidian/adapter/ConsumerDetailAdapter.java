package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.ConsumerDetailBean;
import com.shuiwangzhijia.shuidian.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ConsumerDetailAdapter extends RecyclerView.Adapter<ConsumerDetailAdapter.ViewHolder> {
    private final Context mContext;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private List<ConsumerDetailBean> data;


    public ConsumerDetailAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cosumer_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ConsumerDetailBean bean = data.get(position);
        holder.info.setText("" + bean.getName());
       // holder.date.setText("交易时间:" + DateUtils.getFormatDateStr(bean.getUpdate_time()*1000));
        holder.date.setText(DateUtils.getFormatDateStr(bean.getUpdate_time()*1000));
        if(bean.getStype()==0||bean.getType()==4){
            holder.money.setText("-" + bean.getAmount());
        }else{
            holder.money.setText("+" + bean.getAmount());
        }
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        onRecyclerViewItemClickListener = listener;
    }

    public void setData(List<ConsumerDetailBean> result) {
        data = result;
        notifyDataSetChanged();
    }

    public void addData(List<ConsumerDetailBean> result) {
        data.addAll(result);
        notifyDataSetChanged();
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.info)
        TextView info;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.money)
        TextView money;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
