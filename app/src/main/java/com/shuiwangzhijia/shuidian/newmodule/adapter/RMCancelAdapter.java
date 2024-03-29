package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.MyReturnMoneyListBean;
import com.shuiwangzhijia.shuidian.newmodule.activity.SettleAccountsActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RMCancelAdapter extends RecyclerView.Adapter<RMCancelAdapter.ViewHolder> {
    private  SimpleDateFormat mSimpleDateFormat;
    private Context mContext;
    private List<MyReturnMoneyListBean.DataBean.HistoryBean> data;

    public RMCancelAdapter(Context context) {
        this.mContext = context;
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rm_cancel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MyReturnMoneyListBean.DataBean.HistoryBean historyBean = data.get(position);
        // 1单笔返利，2月度返利，3季度返利，4年度返利，5其他返利
        int rtype = historyBean.getRtype();
        switch (rtype) {
            case 1:
                holder.mRmType.setText("单笔返利");
                holder.mRmTypeHint.setText("每笔已完成订单三天后可结算");
                break;
            case 2:
                holder.mRmType.setText("月度返利");
                holder.mRmTypeHint.setText("每月1号后可结算上个月返利");
                break;
            case 3:
                holder.mRmType.setText("季度返利");
                holder.mRmTypeHint.setText("每季度1号后可结算上季度返利");
                break;
            case 4:
                holder.mRmType.setText("年度返利");
                holder.mRmTypeHint.setText("每年1月1号后可结算上年返利");
                break;
            case 5:
                long startTime = historyBean.getStart_time();
                String startTimeStr = mSimpleDateFormat.format(new Date(startTime * 1000));

                long endTime = historyBean.getEnd_time();
                String endTimeStr = mSimpleDateFormat.format(new Date(endTime * 1000));
                holder.mRmType.setText("周期返利");
                holder.mRmTypeHint.setText(String.format("周期时间后可结算(%s~%s)", startTimeStr, endTimeStr));
                break;
        }
        holder.mRMoney.setText(historyBean.getRebate().getAmount() + "");
        holder.mROrder.setText(historyBean.getRebate().getOrder_num() + "");
        holder.mRWaterCoupon.setText(historyBean.getRebate().getSnum() + "");
        int rWay = historyBean.getR_way();
        //1现金返利 2水票返利
        if(rWay==1){
            holder.mAlreadyReturn.setText(String.format("已返利 金额:%s",historyBean.getAlready_rebate()));
        }else{
            holder.mAlreadyReturn.setText(String.format("已返利 水票:%s",historyBean.getAlready_rebate()));
        }
        holder.mLijijiesuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rId = historyBean.getR_id();
                historyBean.getStart_time();

                Intent intent=new Intent(mContext,SettleAccountsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("rId",rId);
                bundle.putInt("rType",historyBean.getRtype());
                bundle.putInt("rWay",historyBean.getR_way());
                bundle.putInt("rRule",historyBean.getRule());
                bundle.putInt("rBasis",historyBean.getRbasis());
                bundle.putSerializable("timeCancel",(Serializable) historyBean.getTime());
                bundle.putSerializable("ruleDetailsCancel",(Serializable) historyBean.getRule_detail());
                bundle.putInt("type",0);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(position);
            }
        });
    }


    public void setData(ArrayList<MyReturnMoneyListBean.DataBean.HistoryBean> data) {
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
        @BindView(R.id.rm_type)
        TextView mRmType;
        @BindView(R.id.rm_type_hint)
        TextView mRmTypeHint;
        @BindView(R.id.r_order)
        TextView mROrder;
        @BindView(R.id.r_money)
        TextView mRMoney;
        @BindView(R.id.r_water_coupon)
        TextView mRWaterCoupon;
        @BindView(R.id.already_return)
        TextView mAlreadyReturn;
        @BindView(R.id.lijijiesuan)
        TextView mLijijiesuan;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
