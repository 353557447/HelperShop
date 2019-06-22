package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.RMCheckOutOrderListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SettleAccountsSonAdapter extends RecyclerView.Adapter<SettleAccountsSonAdapter.ViewHolder> {
    private Context mContext;
    private List<RMCheckOutOrderListBean.DataBean.ListBean.GoodsBean> data;

    public SettleAccountsSonAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_settle_accounts_son, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        RMCheckOutOrderListBean.DataBean.ListBean.GoodsBean goodsBean = data.get(position);
        holder.mGoodsName.setText(goodsBean.getGname());
        holder.mGoodsCounts.setText("x"+goodsBean.getSnum());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(position);
            }
        });
    }


    public void setData(List<RMCheckOutOrderListBean.DataBean.ListBean.GoodsBean> data) {
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
        @BindView(R.id.goods_name)
        TextView mGoodsName;
        @BindView(R.id.goods_counts)
        TextView mGoodsCounts;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
