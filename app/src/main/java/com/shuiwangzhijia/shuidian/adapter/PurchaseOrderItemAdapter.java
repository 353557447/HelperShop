package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.PurchaseListBean;
import com.shuiwangzhijia.shuidian.view.CustomRecycle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PurchaseOrderItemAdapter extends RecyclerView.Adapter<PurchaseOrderItemAdapter.ViewHolder> {
    private final Context mContext;

    private List<PurchaseListBean> data;
    private OnViewItemClickListener mOnViewItemClickListener;

    public PurchaseOrderItemAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
    }

    public List<PurchaseListBean> getData() {
        return data;
    }

    public void setData(List<PurchaseListBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_purchase_order_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final PurchaseListBean item = getItem(position);
        holder.shopName.setText(item.getSname());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        holder.recyclerView.setHasFixedSize(true);
        OrderItemAdapter mOrderAdapter = new OrderItemAdapter(mContext, true);
        mOrderAdapter.setData(item.getList());
        holder.recyclerView.setAdapter(mOrderAdapter);
        holder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnViewItemClickListener!=null){
                    mOnViewItemClickListener.onCallClick(item.getPhone());
                }
            }
        });
    }

    public PurchaseListBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    public interface OnViewItemClickListener{
        void onCallClick(String phone);
    }

    public void setOnItemClickListener(OnViewItemClickListener listener){
        mOnViewItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shopName)
        TextView shopName;
        @BindView(R.id.phone)
        ImageView phone;
        @BindView(R.id.recyclerView)
        CustomRecycle recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
