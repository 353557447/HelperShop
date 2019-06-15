package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.MultipleRMBean;
import com.shuiwangzhijia.shuidian.bean.RangeRMBean;
import com.shuiwangzhijia.shuidian.bean.RuleProgressBean;
import com.shuiwangzhijia.shuidian.view.MyCircleProgress;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lijn on 2019/4/10.
 */

public class ReturnMoneyProgressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private int type;//type 1 普通 type 2 环形进度条
    private final LayoutInflater mLayoutInflater;
    private List<RangeRMBean> mRangeRMList;
    private List<MultipleRMBean> mMultipleRMList;

    public ReturnMoneyProgressAdapter(Context context, int type) {
        this.mContext = context;
        this.type = type;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (type == 1) {
            //普通
            view = mLayoutInflater.inflate(R.layout.item_return_money_progress_type_one, parent, false);
            return new TypeOneViewHolder(view);
        } else {
            //环形进度条
            view = mLayoutInflater.inflate(R.layout.item_return_money_progress_type_two, parent, false);
            return new TypeTwoViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (type == 1) {
            RangeRMBean rangeRMBean = mRangeRMList.get(position);
            ((TypeOneViewHolder) holder).mGoodsName.setText(rangeRMBean.getGoodsName());
            int completedCounts = rangeRMBean.getCompletedCounts();
            int rWay = rangeRMBean.getrWay();
            int progressPosition = getProgressPosition(rWay, completedCounts, rangeRMBean.getList());
            ((TypeOneViewHolder) holder).mGoodsCompletedCounts.setText("已完成" + completedCounts + "桶");
            ((TypeOneViewHolder) holder).mRv.setLayoutManager(new LinearLayoutManager(mContext));
            ReturnMoneyProgressTypeOneAdapter returnMoneyProgressTypeOneAdapter = new ReturnMoneyProgressTypeOneAdapter(mContext);
            returnMoneyProgressTypeOneAdapter.setData(rangeRMBean.getList());
            returnMoneyProgressTypeOneAdapter.setProgressPosition(progressPosition);
            ((TypeOneViewHolder) holder).mRv.setAdapter(returnMoneyProgressTypeOneAdapter);
        } else {
            MultipleRMBean multipleRMBean = mMultipleRMList.get(position);
            ((TypeTwoViewHolder) holder).mMyCircleProgress.setProgress(multipleRMBean.getProgress());
            ((TypeTwoViewHolder) holder).mGoodsName.setText(multipleRMBean.getGoodsName());
            int rWay = multipleRMBean.getrWay();
            if (rWay == 1) {
                //现金
                //每满100桶，返0.10元
                ((TypeTwoViewHolder) holder).mRuleContent.setText("每满" + multipleRMBean.getMoneyCondition() + "桶，返" + multipleRMBean.getMoney() + "元");
            } else {
                //水票
                ((TypeTwoViewHolder) holder).mRuleContent.setText("每满" + multipleRMBean.getWaterCouponCondition() + "桶，返"//
                        + multipleRMBean.getWaterCouponName() + multipleRMBean.getWaterCouponCounts() + "张");
            }
            //已完成170桶
            ((TypeTwoViewHolder) holder).mGoodsCompletedCounts.setText("已完成" + multipleRMBean.getCompletedCounts() + "桶");
        }
    }

    private int getProgressPosition(int rWay, int completedCounts, List<RuleProgressBean> list) {
        //返利方式 1现金返利 2水票返利
        int progressPosition = -1;
        if (rWay == 1) {
            for (int i = 0; i < list.size(); i++) {
                int moneyCondition = list.get(i).getMoneyCondition();
                if (completedCounts >= moneyCondition) {
                    progressPosition = i;
                }
            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                int waterCouponCondition = list.get(i).getWaterCouponCondition();
                if (completedCounts >= waterCouponCondition) {
                    progressPosition = i;
                }
            }
        }
        return progressPosition;
    }

    @Override
    public int getItemCount() {
        if (type == 1) {
            //范围返利  普通进度
            return mRangeRMList == null ? 0 : mRangeRMList.size();
        } else {
            //倍数返利  环形进度条
            return mMultipleRMList == null ? 0 : mMultipleRMList.size();
        }

    }

    public void setMultipleRMData(List<MultipleRMBean> list) {
        mMultipleRMList = list;
    }

    public void setRangeRMData(List<RangeRMBean> list) {
        mRangeRMList = list;
    }


    public class TypeOneViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_name)
        TextView mGoodsName;
        @BindView(R.id.goods_completed_counts)
        TextView mGoodsCompletedCounts;
        @BindView(R.id.rv)
        RecyclerView mRv;

        public TypeOneViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class TypeTwoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.my_circle_progress)
        MyCircleProgress mMyCircleProgress;
        @BindView(R.id.goods_name)
        TextView mGoodsName;
        @BindView(R.id.rule_content)
        TextView mRuleContent;
        @BindView(R.id.goods_completed_counts)
        TextView mGoodsCompletedCounts;

        public TypeTwoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
