package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.RechargeCenterInfoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 地址适配器
 * created by wangsuli on 2018/8/17.
 */
public class RechargeCenterCouponAdapter extends RecyclerView.Adapter<RechargeCenterCouponAdapter.ViewHolder> {
    private final Context mContext;
    private OnCouponItemClickListener mOnItemClickListener;
    private List<RechargeCenterInfoBean.DataBean.ListBean.RechargeBean> data;


    public RechargeCenterCouponAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recharge_center_coupon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        RechargeCenterInfoBean.DataBean.ListBean.RechargeBean rechargeBean = data.get(position);
        holder.mWaterFactoryName.setText(rechargeBean.getDname());
        String sailAmount = rechargeBean.getSail_amount();
        String ramount = rechargeBean.getRamount();
        double sailAmountInt = Double.parseDouble(sailAmount);
        double ramountInt = Double.parseDouble(ramount);
        double gift = ramountInt - sailAmountInt;
        holder.mFaceValue.setText(sailAmount);
        String regulation="充" + sailAmountInt + "赠" + gift;
        holder.mRegulation.setText(regulation);
        rechargeBean.setRegulation(regulation);
        if (rechargeBean.isChecked()) {
            holder.mRootRl.setBackgroundResource(R.drawable.recharge_coupon_selected);
        } else {
            holder.mRootRl.setBackgroundResource(R.drawable.item_touch_balance_bg);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener!=null)
                    mOnItemClickListener.onCouponItemClick(position);
            }
        });
    }

    public RechargeCenterInfoBean.DataBean.ListBean.RechargeBean getItem(int position) {
        return data.get(position);
    }

    public void deleteItem(int position) {
        data.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    public void setOnItemClickListener(OnCouponItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setData(List<RechargeCenterInfoBean.DataBean.ListBean.RechargeBean> result) {
        data = result;
        notifyDataSetChanged();
    }

    public void refreshData(List<RechargeCenterInfoBean.DataBean.ListBean.RechargeBean> result) {
        data.clear();
        data.addAll(result);
        notifyDataSetChanged();
    }

    public interface OnCouponItemClickListener {
        void onCouponItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.water_factory_name)
        TextView mWaterFactoryName;
        @BindView(R.id.face_value)
        TextView mFaceValue;
        @BindView(R.id.regulation)
        TextView mRegulation;
        @BindView(R.id.root_rl)
        RelativeLayout mRootRl;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
