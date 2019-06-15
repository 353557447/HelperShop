package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.GMNewBean;
import com.shuiwangzhijia.shuidian.utils.MyUtils;
import com.socks.library.KLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by xxc on 2019/3/29.
 */

public class GmGoodsDetailsAdapter extends RecyclerView.Adapter<GmGoodsDetailsAdapter.ViewHolder> {
    private Context mContext;
    private List<GMNewBean.DataBean.ListBean> mList;

    public GmGoodsDetailsAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gm_goods_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        GMNewBean.DataBean.ListBean goodsBean = mList.get(position);
        MyUtils.setImg(holder.mGoodsIv, goodsBean.getPicturl(),"goods/");
        holder.mGoodsName.setText(goodsBean.getGname());
        holder.mReferencePrice.setText("参考价：￥" + goodsBean.getPprice());
        holder.mGoodsPrice.setText("售价：￥" + goodsBean.getPrice());
        holder.mGoodsDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDetailsItemOperationClickListener != null)
                    mOnDetailsItemOperationClickListener.onDetailsItemDeleteClick(position);
            }
        });
        holder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDetailsItemOperationClickListener != null)
                    mOnDetailsItemOperationClickListener.onDetailsEditItemClick(position);
            }
        });
    }

    private OnDetailsItemOperationClickListener mOnDetailsItemOperationClickListener;

    public void setOnDetailsItemOperationClickListener(OnDetailsItemOperationClickListener listener) {
        mOnDetailsItemOperationClickListener = listener;
    }

    public interface OnDetailsItemOperationClickListener {
        void onDetailsItemDeleteClick(int position);

        void onDetailsEditItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setData(List<GMNewBean.DataBean.ListBean> list) {
        mList = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_iv)
        ImageView mGoodsIv;
        @BindView(R.id.goods_name)
        TextView mGoodsName;
        @BindView(R.id.goods_price)
        TextView mGoodsPrice;
        @BindView(R.id.goods_down)
        TextView mGoodsDown;
        @BindView(R.id.edit)
        ImageView mEdit;
        @BindView(R.id.reference_price)
        TextView mReferencePrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
