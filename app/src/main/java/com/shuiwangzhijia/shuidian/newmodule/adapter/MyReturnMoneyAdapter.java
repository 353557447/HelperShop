package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.MyReturnMoneyRecordBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 地址适配器
 * created by wangsuli on 2018/8/17.
 */
public class MyReturnMoneyAdapter extends RecyclerView.Adapter<MyReturnMoneyAdapter.ViewHolder> {
    private final Context mContext;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private List<MyReturnMoneyRecordBean.DataBean> data;
    private final SimpleDateFormat mSimpleDateFormat;


    public MyReturnMoneyAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_return_money, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        MyReturnMoneyRecordBean.DataBean dataBean = data.get(position);
        //返利方式 1现金返利 2水票返利

        switch (dataBean.getR_way()) {
            case 1:
                holder.mReturnMoneyType.setText("现金返利");
                holder.mMoney.setText("+￥"+dataBean.getTotal_amount());
                break;
            case 2:
                holder.mReturnMoneyType.setText("水票返利");
                holder.mMoney.setText("+"+dataBean.getS_count()+"张");
                break;
        }
        //返利类型1单笔返利，2月度返利，3季度返利，4年度返利，5其他返利
        GradientDrawable drawable = (GradientDrawable) holder.mReturnMoneyTimeType.getBackground();
        switch (dataBean.getR_type()) {
            case 1:
                holder.mReturnMoneyTimeType.setText("单笔");
                drawable.setColor(Color.parseColor("#5394EE"));
                break;
            case 2:
                holder.mReturnMoneyTimeType.setText("月度");
                drawable.setColor(Color.parseColor("#FFAF62"));
                break;
            case 3:
                holder.mReturnMoneyTimeType.setText("季度");
                drawable.setColor(Color.parseColor("#59B97A"));
                break;
            case 4:
                holder.mReturnMoneyTimeType.setText("年度");
                drawable.setColor(Color.parseColor("#F46C70"));
                break;
            case 5:
                holder.mReturnMoneyTimeType.setText("其他周期");
                drawable.setColor(Color.parseColor("#997AE2"));
                break;
        }
        holder.mModeName.setText(dataBean.getRname());
        holder.mTime.setText(mSimpleDateFormat.format(new Date(dataBean.getSettlement_time() * 1000)));

    }

    public MyReturnMoneyRecordBean.DataBean getItem(int position) {
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

    public void setData(List<MyReturnMoneyRecordBean.DataBean> result) {
        data = result;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.return_money_time_type)
        TextView mReturnMoneyTimeType;
        @BindView(R.id.return_money_type)
        TextView mReturnMoneyType;
        @BindView(R.id.mode_name)
        TextView mModeName;
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
