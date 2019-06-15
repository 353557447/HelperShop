package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.DiscountsWaterCouponBean;
import com.shuiwangzhijia.shuidian.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DiscountsWaterCouponAdapter extends RecyclerView.Adapter<DiscountsWaterCouponAdapter.ViewHolder> {
    private Context mContext;
    private List<DiscountsWaterCouponBean.DataBean> data;

    public DiscountsWaterCouponAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_discounts_water_coupon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        DiscountsWaterCouponBean.DataBean dataBean = data.get(position);
        holder.mName.setText(dataBean.getGname());
        holder.mWaterFactoryName.setText(dataBean.getSname());
        holder.mPrice.setText("原价：￥"+dataBean.getPprice());
        holder.mCurrentPrice.setText("￥"+dataBean.getSprice());
         holder.mCouponCounts.setText("共" + dataBean.getSnum() + "张");
        if (dataBean.getStart() > 0 && dataBean.getEnd() > 0) {
             holder.mPeriodOfValidity.setText("有效期限:" + DateUtils.getYMDTime(dataBean.getStart()) + "~" + DateUtils.getYMDTime(dataBean.getEnd()));
        } else {
            holder.mPeriodOfValidity.setText("有效期限:" + "永久有效");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemBuyClickListener != null)
                    mOnItemBuyClickListener.onItemBuyClick(position);
            }
        });
    }


    public void setData(List<DiscountsWaterCouponBean.DataBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    private OnItemBuyClickListener mOnItemBuyClickListener;

    public interface OnItemBuyClickListener {
        void onItemBuyClick(int position);
    }

    public void setOnItemBuyClickListener(OnItemBuyClickListener listener) {
        mOnItemBuyClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.water_factory_name)
        TextView mWaterFactoryName;
        @BindView(R.id.price)
        TextView mPrice;
        @BindView(R.id.current_price)
        TextView mCurrentPrice;
        @BindView(R.id.coupon_counts)
        TextView mCouponCounts;
        @BindView(R.id.period_of_validity)
        TextView mPeriodOfValidity;
        @BindView(R.id.purchase_immediately)
        TextView mPurchaseImmediately;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
