package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.RechargeCenterInfoBean;
import com.shuiwangzhijia.shuidian.newmodule.activity.BalanceRechargeActivity;
import com.shuiwangzhijia.shuidian.utils.MyUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 地址适配器
 * created by wangsuli on 2018/8/17.
 */
public class RechargeCenterAdapter extends RecyclerView.Adapter<RechargeCenterAdapter.ViewHolder> {
    private final Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private List<RechargeCenterInfoBean.DataBean.ListBean> data;


    public RechargeCenterAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recharge_center, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final RechargeCenterInfoBean.DataBean.ListBean listBean = data.get(position);
        holder.mWaterFactoryName.setText(listBean.getWater_name());
        holder.mWaterShopName.setText(listBean.getNickname());
        holder.mFaceValue.setText(listBean.getBalance());
        MyUtils.setImg(holder.mWaterShopHead,listBean.getHeader_pic(),"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
        holder.mParticulars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BalanceRechargeActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("position",position);
                bundle.putSerializable("list",(Serializable) data);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    public RechargeCenterInfoBean.DataBean.ListBean getItem(int position) {
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setData(List<RechargeCenterInfoBean.DataBean.ListBean> result) {
        data = result;
        notifyDataSetChanged();
    }

    public void addData(List<RechargeCenterInfoBean.DataBean.ListBean> result) {
        data = result;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.water_factory_name)
        TextView mWaterFactoryName;
        @BindView(R.id.water_shop_name)
        TextView mWaterShopName;
        @BindView(R.id.water_shop_head)
        CircleImageView mWaterShopHead;
        @BindView(R.id.face_value)
        TextView mFaceValue;
        @BindView(R.id.particulars)
        TextView mParticulars;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
