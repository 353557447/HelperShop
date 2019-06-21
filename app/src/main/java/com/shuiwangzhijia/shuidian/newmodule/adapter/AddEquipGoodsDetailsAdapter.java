package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.AddEquipGoodsBean;
import com.shuiwangzhijia.shuidian.utils.MyUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;




public class AddEquipGoodsDetailsAdapter extends RecyclerView.Adapter<AddEquipGoodsDetailsAdapter.ViewHolder> {
    private Context mContext;
    private List<AddEquipGoodsBean.DataBean.GoodsBean> mList;

    public AddEquipGoodsDetailsAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_add_equip_goods_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        AddEquipGoodsBean.DataBean.GoodsBean goodsBean = mList.get(position);
        MyUtils.setImg(holder.mGoodsIv, goodsBean.getPicturl(),"goods/");
        holder.mGoodsName.setText(goodsBean.getGname());
        holder.mReferencePrice.setText("参考价：￥" + goodsBean.getPprice());
        // 0下架，1上架
        int isUp = goodsBean.getIs_up();
        if (isUp == 0) {
            holder.mAdd.setText("添加");
            holder.mAdd.setTextColor(Color.parseColor("#515F85"));
            holder.mAdd.setBackgroundResource(R.drawable.c515f85_stroke_bg);
            holder.mAdd.setEnabled(true);
        } else {
            holder.mAdd.setText("已添加");
            holder.mAdd.setTextColor(Color.parseColor("#BCBCBC"));
            holder.mAdd.setBackgroundResource(R.drawable.gray_stroke_big_radius_bg);
            holder.mAdd.setEnabled(false);
        }
        holder.mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDetailsItemAddClickListener != null)
                    mOnDetailsItemAddClickListener.onDetailsItemAddClick(position);
            }
        });
    }

    private OnDetailsItemAddClickListener mOnDetailsItemAddClickListener;

    public void setOnDetailsItemClickListener(OnDetailsItemAddClickListener listener) {
        mOnDetailsItemAddClickListener = listener;
    }

    public interface OnDetailsItemAddClickListener {
        void onDetailsItemAddClick(int position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setData(List<AddEquipGoodsBean.DataBean.GoodsBean> list) {
        mList = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_iv)
        ImageView mGoodsIv;
        @BindView(R.id.goods_name)
        TextView mGoodsName;
        @BindView(R.id.reference_price)
        TextView mReferencePrice;
        @BindView(R.id.add)
        TextView mAdd;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
