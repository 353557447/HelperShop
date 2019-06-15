package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.RuleProgressBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReturnMoneyProgressTypeOneAdapter extends RecyclerView.Adapter<ReturnMoneyProgressTypeOneAdapter.ViewHolder> {
    private final Context mContext;
    private List<RuleProgressBean> data;
    private int progressPosition;


    public void setProgressPosition(int progressPosition) {
        this.progressPosition = progressPosition;
    }

    public ReturnMoneyProgressTypeOneAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_return_money_progress_type_one_son, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        RuleProgressBean ruleProgressBean = data.get(position);
        int rWay = ruleProgressBean.getrWay();
        //返利方式 1现金返利 2水票返利
        if (rWay == 1) {
            //满50桶，返0.10元
            holder.mContent.setText("满" + ruleProgressBean.getMoneyCondition() + "桶，返" + ruleProgressBean.getMoney() + "元");
        } else {
            //满50桶，返xxx水票x张
            holder.mContent.setText("满" + ruleProgressBean.getWaterCouponCondition() + "桶，返"//
                    + ruleProgressBean.getWaterCouponName() + ruleProgressBean.getWaterCouponCounts() + "张");
        }
        if (position == 0) {
            holder.mLineOne.setVisibility(View.INVISIBLE);
        } else if (position == data.size() - 1) {
            holder.mLineTwo.setVisibility(View.INVISIBLE);
        }

        if(position==progressPosition){
            holder.mLittleCircle.setImageResource(R.drawable.little_circle_blue);
        }else{
            holder.mLittleCircle.setImageResource(R.drawable.little_circle_gray);
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

    public void setData(List<RuleProgressBean> result) {
        data = result;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.line_one)
        TextView mLineOne;
        @BindView(R.id.line_two)
        TextView mLineTwo;
        @BindView(R.id.content)
        TextView mContent;
        @BindView(R.id.little_circle)
        ImageView mLittleCircle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
