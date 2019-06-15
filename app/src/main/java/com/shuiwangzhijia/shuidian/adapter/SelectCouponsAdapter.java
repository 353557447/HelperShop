package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.ShowCouponBean;
import com.shuiwangzhijia.shuidian.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class SelectCouponsAdapter extends RecyclerView.Adapter<SelectCouponsAdapter.ViewHolder> {
    private final Context mContext;
    private List<ShowCouponBean.ListBean> mData;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public SelectCouponsAdapter(Context context) {
        this.mContext = context;
    }

    private int selectIndex = -1;

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_select_coupons, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShowCouponBean.ListBean bean = getItem(position);
        int ctype = bean.getCtype();
        if (ctype == 1){
            holder.mRemark.setText("下单抵扣");
        }else {
            holder.mRemark.setText("满"+bean.getFull()+"可用");
        }
        holder.mMoney.setText(bean.getAmount()+"");
        holder.mTicketname.setText(bean.getCname());
        holder.mShopname.setText(bean.getSname()+" · "+bean.getGids());
        if (bean.getStart() > 0 && bean.getEnd() > 0) {
            holder.mTime.setText("有效期限:" + DateUtils.getYMDTime(bean.getStart()) + "~" + DateUtils.getYMDTime(bean.getEnd()));
        } else {
            holder.mTime.setText("有效期限:" + "永久有效");
        }

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecyclerViewItemClickListener.onItemClick((int) view.getTag());
            }
        });
        if (selectIndex == position) {
            holder.mCb.setSelected(true);
        } else {
            holder.mCb.setSelected(false);
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        onRecyclerViewItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return (mData == null) ? 0 : mData.size();
    }

    public void setData(List<ShowCouponBean.ListBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public ShowCouponBean.ListBean getItem(int position) {
        return mData.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.money)
        TextView mMoney;
        @BindView(R.id.remark)
        TextView mRemark;
        @BindView(R.id.ticketname)
        TextView mTicketname;
        @BindView(R.id.shopname)
        TextView mShopname;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.cb)
        ImageView mCb;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
