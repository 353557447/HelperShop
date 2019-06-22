package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.SessionGoodsBean;
import com.shuiwangzhijia.shuidian.utils.MyUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FlashSaleAddAdapter extends RecyclerView.Adapter<FlashSaleAddAdapter.ViewHolder> {
    private final Context mContext;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private List<SessionGoodsBean.DataBean> data;
    private Bundle mBundle;


    public FlashSaleAddAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_flash_sale_add, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        SessionGoodsBean.DataBean dataBean = data.get(position);
        MyUtils.setImg(holder.mGoodsImg, dataBean.getPicturl(), "goods/");
        holder.mGoodsName.setText(dataBean.getGname());
        holder.mGoodsPrice.setText("￥" + dataBean.getSeckill_price());
        holder.mReferencePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.mReferencePrice.setText("￥" + dataBean.getPprice());
        holder.mRepertoryCounts.setText("库存:" + dataBean.getSeckill_stock());
        holder.mSaleLimitCounts.setText("限购:" + dataBean.getSeckill_restrictions());
        holder.mSaleCounts.setText("销量:" + dataBean.getSeckill_sales_volume());

        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemDeleteEditListener != null)
                    mOnItemDeleteEditListener.onItemDelete(position);
            }
        });
        holder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemDeleteEditListener != null)
                    mOnItemDeleteEditListener.onItemEdit(position);
            }
        });

        if (mBundle != null) {
            boolean isEditable = mBundle.getBoolean("isEditable");
            if (!isEditable) {
                holder.mEdit.setVisibility(View.GONE);
                holder.mDelete.setVisibility(View.GONE);
            }
        }
    }

    public void deleteItem(int position) {
        data.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        onRecyclerViewItemClickListener = listener;
    }

    private OnItemDeleteEditListener mOnItemDeleteEditListener;

    public void setOnItemDeleteEditListener(OnItemDeleteEditListener listener) {
        mOnItemDeleteEditListener = listener;
    }

    public void setBuddle(Bundle bundle) {
        mBundle = bundle;
    }


    public interface OnItemDeleteEditListener {
        void onItemDelete(int position);

        void onItemEdit(int position);
    }

    public void setData(List<SessionGoodsBean.DataBean> result) {
        data = result;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_img)
        ImageView mGoodsImg;
        @BindView(R.id.goods_name)
        TextView mGoodsName;
        @BindView(R.id.goods_price)
        TextView mGoodsPrice;
        @BindView(R.id.reference_price)
        TextView mReferencePrice;
        @BindView(R.id.repertory_counts)
        TextView mRepertoryCounts;
        @BindView(R.id.sale_limit_counts)
        TextView mSaleLimitCounts;
        @BindView(R.id.sale_counts)
        TextView mSaleCounts;
        @BindView(R.id.delete)
        TextView mDelete;
        @BindView(R.id.edit)
        TextView mEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
