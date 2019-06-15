package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.FlashSaleAddGoodsBean;
import com.shuiwangzhijia.shuidian.utils.MyUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xxc on 2019/4/2.
 */

public class FlashSaleAddGoodsAdapter extends RecyclerView.Adapter<FlashSaleAddGoodsAdapter.ViewHolder> {
    private Context mContext;
    private List<FlashSaleAddGoodsBean.DataBean> data;

    public FlashSaleAddGoodsAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_add_goods, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final FlashSaleAddGoodsBean.DataBean dataBean = data.get(position);
        holder.mGoodsName.setText(dataBean.getGname());
        holder.mGoodsPrice.setText("￥"+dataBean.getPprice());
        MyUtils.setImg(holder.mGoodsImg,dataBean.getPicturl(),"goods/");
        holder.mGoodsCb.setChecked(dataBean.isCheck());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(mOnItemClickListener!=null)
                  mOnItemClickListener.onItemClick(position);
            }
        });
    }


    public void setData(List<FlashSaleAddGoodsBean.DataBean> data) {
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
        @BindView(R.id.goods_cb)
        CheckBox mGoodsCb;
        @BindView(R.id.goods_img)
        ImageView mGoodsImg;
        @BindView(R.id.goods_name)
        TextView mGoodsName;
        @BindView(R.id.goods_price)
        TextView mGoodsPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
