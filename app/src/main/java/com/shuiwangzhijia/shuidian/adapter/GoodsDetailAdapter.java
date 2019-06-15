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


public class GoodsDetailAdapter extends RecyclerView.Adapter<GoodsDetailAdapter.ViewHolder> {
    private final Context mContext;
    private OnSaleClickListener mOnViewItemClickListener;

    public List<GoodsBean> getData() {
        return data;
    }

    private List<GoodsBean> data;

    public void setSale(boolean sale) {
        isSale = sale;
        notifyDataSetChanged();
    }

    private boolean isSale = true;
    private boolean fromShop = false;

    public boolean isSelectGood() {
        return selectGood;
    }

    public void setSelectGood(boolean selectGood) {
        this.selectGood = selectGood;
    }

    private boolean selectGood = false;//水票 选择商品

    public GoodsDetailAdapter(Context context, boolean isSale, boolean fromShop) {
        mContext = context;
        this.isSale = isSale;
        this.fromShop = fromShop;
        data = new ArrayList<>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_goods_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (selectGood) {
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnViewItemClickListener.onSelectClick((int) view.getTag());
                }
            });
        }
        holder.saleBtn.setTag(position);
        holder.saleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnViewItemClickListener.onSelectClick((int) view.getTag());
            }
        });
        if (isSale) {
            holder.saleBtn.setText("下架");
        } else {
            holder.saleBtn.setText("上架");
        }
        GoodsBean goodsBean = getItem(position);
        holder.name.setText(goodsBean.getGname());
        holder.price.setText("￥" + goodsBean.getPprice());
        holder.saleBtn.setVisibility(fromShop ? View.GONE : View.VISIBLE);
        holder.saleBtn.setVisibility(selectGood ? View.GONE : View.VISIBLE);
        Glide.with(mContext).load(Constant.GOODS_IMAGE_URL + goodsBean.getPicturl()).placeholder(R.drawable.wutupian).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    public void setOnSaleClickListener(OnSaleClickListener listener) {
        mOnViewItemClickListener = listener;
    }

    public void setData(List<GoodsBean> list) {
        data = list;
        notifyDataSetChanged();
    }

    public GoodsBean getItem(int position) {
        return data.get(position);
    }

    public void deleteItem(int position) {
        data.remove(position);
        notifyDataSetChanged();
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
        @BindView(R.id.saleBtn)
        TextView saleBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
