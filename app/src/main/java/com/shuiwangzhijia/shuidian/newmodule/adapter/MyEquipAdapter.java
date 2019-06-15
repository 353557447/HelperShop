package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.MyEquipBean;
import com.shuiwangzhijia.shuidian.newmodule.activity.EquipMapActivity;
import com.shuiwangzhijia.shuidian.utils.MyUtils;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyEquipAdapter extends RecyclerView.Adapter<MyEquipAdapter.ViewHolder> {
    private Context mContext;
    private List<MyEquipBean.DataBean.ListBean> data;
    private DecimalFormat mDecimalFormat;

    public MyEquipAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_equip, parent, false);
        mDecimalFormat = new DecimalFormat("######0.00");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MyEquipBean.DataBean.ListBean dataBean = data.get(position);
        MyUtils.setImg(holder.mEquipIv, dataBean.getDevice_pic(),"");
        holder.mEquipName.setText(dataBean.getDname());
        // android:text="售卖中：12"
        //android:text="空闲中：10"
        holder.mSalingCounts.setText("售卖中：" + dataBean.getLattice_ok());
        holder.mLeisureCounts.setText("空闲中：" + dataBean.getLattice_no());
        holder.mTodayEarnings.setText("今日收益：￥"+mDecimalFormat.format(dataBean.getDay_price()));
        holder.mTodaySalesCounts.setText("今日销量："+dataBean.getDay_sales());
        holder.mTotalEarnings.setText("总收益：￥"+mDecimalFormat.format(dataBean.getTotal_price()));
        holder.mTotalSalesCounts.setText("总销量："+dataBean.getTotal_sales());
        //￥20.00
        //   holder.mEquipEarnings.setText("￥" + dataBean.getTotal_price());
        holder.mCheckAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, EquipMapActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("equipImg",dataBean.getDevice_pic());
                bundle.putString("equipName",dataBean.getDname());
                bundle.putString("equipAddress",dataBean.getProvince() + dataBean.getCity() + dataBean.getDistrict() + dataBean.getAddress());
                bundle.putString("longitude",dataBean.getLongitude());
                bundle.putString("latitude",dataBean.getLatitude());
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

        if (position == data.size() - 1) {
            holder.mDevider.setVisibility(View.GONE);
        }
    }


    public void setData(List<MyEquipBean.DataBean.ListBean> data) {
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
        @BindView(R.id.equip_iv)
        ImageView mEquipIv;
        @BindView(R.id.equip_name)
        TextView mEquipName;
        @BindView(R.id.saling_counts)
        TextView mSalingCounts;
        @BindView(R.id.leisure_counts)
        TextView mLeisureCounts;
        /*  @BindView(R.id.equip_earnings)
          TextView mEquipEarnings;*/
        @BindView(R.id.today_earnings)
        TextView mTodayEarnings;
        @BindView(R.id.today_sales_counts)
        TextView mTodaySalesCounts;
        @BindView(R.id.total_earnings)
        TextView mTotalEarnings;
        @BindView(R.id.total_sales_counts)
        TextView mTotalSalesCounts;
        @BindView(R.id.check_address)
        LinearLayout mCheckAddress;
        @BindView(R.id.devider)
        TextView mDevider;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
