package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.ShopEarnApplyWdBean;
import com.shuiwangzhijia.shuidian.event.CommonEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


public class SpEarnWDableAdapter extends RecyclerView.Adapter<SpEarnWDableAdapter.ViewHolder> {
    private Context mContext;
    private List<ShopEarnApplyWdBean.DataBean> data;
    private final SimpleDateFormat mSimpleDateFormat;

    public SpEarnWDableAdapter(Context context) {
        this.mContext = context;
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sp_earn_wdable, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ShopEarnApplyWdBean.DataBean dataBean = data.get(position);
        holder.mOrderNum.setText("订单编号："+dataBean.getOrder_code());
       // 1零售订单，2设备订单，3水票订单
        int orderType = dataBean.getOrder_type();
        switch (orderType){
            case 1:
                holder.mOrderType.setText("零售订单");
                break;
            case 2:
                holder.mOrderType.setText("设备订单");
                break;
            case 3:
                holder.mOrderType.setText("水票订单");
                break;
        }
        holder.mTime.setText("入账时间："+mSimpleDateFormat.format(new Date(dataBean.getPay_time()*1000)));
        holder.mMoney.setText("￥"+dataBean.getAmount_price());
        if(dataBean.isChecked()){
            holder.mSelectSwitch.setImageResource(R.drawable.green_selected_icon);
        }else{
            holder.mSelectSwitch.setImageResource(R.drawable.green_unselected_icon);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                    EventBus.getDefault().post(new CommonEvent("spEarnWithDrawableTotalMoneyChanged"));
                }
            }
        });
    }


    public void setData(List<ShopEarnApplyWdBean.DataBean> data) {
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
        @BindView(R.id.select_switch)
        ImageView mSelectSwitch;
        @BindView(R.id.order_num)
        TextView mOrderNum;
        @BindView(R.id.order_type)
        TextView mOrderType;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.money)
        TextView mMoney;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
