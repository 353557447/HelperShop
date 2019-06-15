package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.EquipOrderBean;
import com.shuiwangzhijia.shuidian.utils.DateUtils;
import com.shuiwangzhijia.shuidian.utils.MyUtils;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EquipOrderAdapter extends RecyclerView.Adapter<EquipOrderAdapter.ViewHolder> {
    private Context mContext;
    private List<EquipOrderBean.DataBean> data;

    public EquipOrderAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_equip_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        EquipOrderBean.DataBean dataBean = data.get(position);
        EquipOrderBean.DataBean.GoodsBean goodsBean = dataBean.getGoods().get(0);
        holder.mEquipName.setText(dataBean.getDname());
       /* pay_status	string	支付状态(0:未支付，1已支付)
        refund_status	string	退款状态(0:未退款，1已退款)*/
       //只有兩種狀態 已完成  已退款
        int refundStatus = dataBean.getRefund_status();
        int payStatus = dataBean.getPay_status();
        if (refundStatus == 0) {
            holder.mState.setText("已完成");
            holder.mBottomRl.setVisibility(View.VISIBLE);
            long orderTime = dataBean.getOrder_time() * 1000;
            long currentTime = System.currentTimeMillis();
            long distanceTime = currentTime - orderTime;
            if(distanceTime>=3*24*60*60*1000){
                holder.mRightBtn.setTextColor(Color.parseColor("#9d9d9d"));
                holder.mRightBtn.setBackgroundResource(R.drawable.gray_stroke_big_radius_bg);
            }
          /*  if (payStatus == 0) {
                holder.mBottomRl.setVisibility(View.GONE);
            } else {
                holder.mState.setText("已完成");
                holder.mBottomRl.setVisibility(View.VISIBLE);
            }*/
        } else {
            holder.mState.setText("已退款");
            holder.mBottomRl.setVisibility(View.GONE);
        }
        MyUtils.setImg(holder.mGoodsIv, goodsBean.getGood_pic(),"goods/");
        holder.mGoodsName.setText(goodsBean.getGname());
        holder.mGoodsCounts.setText("x" + goodsBean.getNum());
        holder.mGoodsPrice.setText("￥" + goodsBean.getPrice());
        holder.mTotalPrice.setText("￥" + dataBean.getAmount_price());
        holder.mOrderTime.setText(DateUtils.getFormatDateTime(new Date(dataBean.getOrder_time()*1000), "yyyy-MM-dd HH:mm:ss"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener!=null)
                    mOnItemClickListener.onItemClick(position);
            }
        });
    }


    public void setData(List<EquipOrderBean.DataBean> data) {
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

    private OnRefundClickListener mOnRefundClickListener;

    public interface OnRefundClickListener {
        void onRefundClick(int position);
    }

    public void setOnRefundClickListener(OnRefundClickListener listener) {
        mOnRefundClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.equip_name)
        TextView mEquipName;
        @BindView(R.id.state)
        TextView mState;
        @BindView(R.id.goods_iv)
        ImageView mGoodsIv;
        @BindView(R.id.goods_name)
        TextView mGoodsName;
        @BindView(R.id.goods_price)
        TextView mGoodsPrice;
        @BindView(R.id.goods_counts)
        TextView mGoodsCounts;
        @BindView(R.id.total_price)
        TextView mTotalPrice;
        @BindView(R.id.right_btn)
        TextView mRightBtn;
        @BindView(R.id.bottom_rl)
        RelativeLayout mBottomRl;
        @BindView(R.id.order_time)
        TextView mOrderTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
