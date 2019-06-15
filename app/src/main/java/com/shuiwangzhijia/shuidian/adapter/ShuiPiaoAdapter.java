package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.WaterTicketBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xxc on 2019/2/19.
 */

public class ShuiPiaoAdapter extends RecyclerView.Adapter<ShuiPiaoAdapter.ViewHolder> {
    private final Context mContext;
    private final List<WaterTicketBean> mData;
    private final int type;

    public ShuiPiaoAdapter(Context context, List<WaterTicketBean> water_ticket,int type) {
        this.type = type;
        this.mContext = context;
        this.mData = water_ticket;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_shuipiao, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (type == 1){
            holder.mName.setText(mData.get(position).getS_name());
            holder.mNumber.setText("x"+mData.get(position).getNum());
        }else if (type == 2){
            holder.mName.setText(mData.get(position).getGname());
            holder.mNumber.setText("x"+mData.get(position).getNum());
        }else if (type == 3){
            holder.mName.setText(mData.get(position).getGname());
            holder.mNumber.setText("x"+mData.get(position).getNum());
        }else if (type == 4){
            holder.mName.setText(mData.get(position).getGname());
            holder.mNumber.setText(""+mData.get(position).getNum());
        }else if (type == 5){
            holder.mName.setText(mData.get(position).getS_name());
            holder.mNumber.setText(""+mData.get(position).getNum());
        }
    }

    @Override
    public int getItemCount() {
        return (mData == null) ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.number)
        TextView mNumber;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
