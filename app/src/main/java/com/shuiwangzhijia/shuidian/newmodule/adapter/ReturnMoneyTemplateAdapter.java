package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.ReturnMoneyTemBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 地址适配器
 * created by wangsuli on 2018/8/17.
 */
public class ReturnMoneyTemplateAdapter extends RecyclerView.Adapter<ReturnMoneyTemplateAdapter.ViewHolder> {
    private Context mContext;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private List<ReturnMoneyTemBean.DataBean> data;


    public ReturnMoneyTemplateAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_return_money_template, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ReturnMoneyTemBean.DataBean dataBean = data.get(position);
        if(dataBean.isChecked()){
            holder.mRootRl.setBackgroundResource(R.drawable.item_return_money_tem_selected_bg);
        }else{
            holder.mRootRl.setBackgroundResource(R.drawable.item_return_money_tem_unselect_bg);
        }
        int rtype = dataBean.getRtype();
        // 1单笔返利，2月度返利，3季度返利，4年度返利，5其他周期
        switch (rtype) {
            case 1:
                holder.mCycle.setText("单笔返利");
                break;
            case 2:
                holder.mCycle.setText("月度返利");
                break;
            case 3:
                holder.mCycle.setText("季度返利");
                break;
            case 4:
                holder.mCycle.setText("年度返利");
                break;
            case 5:
                holder.mCycle.setText("其他周期");
                break;
        }
        int haveRebate = dataBean.getHave_rebate();
        if (haveRebate == 1) {
            //有待申请返利
            holder.mTemState.setVisibility(View.VISIBLE);
            String notApplyRebate = dataBean.getNot_apply_rebate();
            if("0".equals(notApplyRebate)||"0.00".equals(notApplyRebate)){
                holder.mTemState.setVisibility(View.GONE);
            }
        } else {
            holder.mTemState.setVisibility(View.GONE);
        }
        if(rtype==1)
            holder.mTemState.setVisibility(View.GONE);
        holder.mTemName.setText(dataBean.getRname());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onRecyclerViewItemClickListener!=null)
                    onRecyclerViewItemClickListener.onTemplateItemClick(position);
            }
        });
    }

    public ReturnMoneyTemBean.DataBean getItem(int position) {
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

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        onRecyclerViewItemClickListener = listener;
    }

    public void setData(List<ReturnMoneyTemBean.DataBean> result) {
        data = result;
    }

    public interface OnRecyclerViewItemClickListener {
        void onTemplateItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.root_rl)
        RelativeLayout mRootRl;
        @BindView(R.id.cycle)
        TextView mCycle;
        @BindView(R.id.tem_name)
        TextView mTemName;
        @BindView(R.id.tem_state)
        TextView mTemState;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
