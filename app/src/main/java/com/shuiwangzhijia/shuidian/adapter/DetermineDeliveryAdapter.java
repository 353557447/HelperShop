package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.OrderShowBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class DetermineDeliveryAdapter extends RecyclerView.Adapter<DetermineDeliveryAdapter.ViewHolder> {
    private final Context mContext;
    private final List<OrderShowBean.GoodsBean> mData;


    public DetermineDeliveryAdapter(Context context, List<OrderShowBean.GoodsBean> goods) {
        this.mContext = context;
        this.mData = goods;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_shuipiao, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mName.setText(mData.get(position).getGname());
        holder.mNumber.setText("x"+mData.get(position).getSnum());
    }

    @Override
    public int getItemCount() {
        return (mData == null) ? 0 : mData.size();
    }

    public List<OrderShowBean.GoodsBean> getData() {
        return mData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.number)
        TextView mNumber;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
