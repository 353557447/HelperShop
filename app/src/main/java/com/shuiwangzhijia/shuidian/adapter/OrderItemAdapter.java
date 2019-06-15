package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.Constant;
import com.shuiwangzhijia.shuidian.bean.GoodsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {
    private final Context mContext;
    private List<GoodsBean> data;
    private OnSaleClickListener mOnViewItemClickListener;
    private boolean isOrderManage = false;//来着订单管理 或者  确认订单页面

    public OrderItemAdapter(Context context, boolean isOrderManage) {
        mContext = context;
        this.isOrderManage = isOrderManage;
        data = new ArrayList<>();
    }

    public List<GoodsBean> getData() {
        return data;
    }

    public void setData(List<GoodsBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GoodsBean bean = data.get(position);
        holder.name.setText(bean.getGname());
        holder.price.setText("￥" + bean.getPrice());
        if (isOrderManage) {
            holder.num.setText("x" + bean.getCount());
        } else {
            holder.num.setText("x" + bean.getNum());
        }
        Glide.with(mContext).load(Constant.GOODS_IMAGE_URL + bean.getPicturl()).placeholder(R.drawable.wutupian).into(holder.image);
    }

    public GoodsBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    public void setOnSaleClickListener(OnSaleClickListener listener) {
        mOnViewItemClickListener = listener;
    }

    public interface OnSaleClickListener {
        void onSelectClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.num)
        TextView num;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
