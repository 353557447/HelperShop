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
import com.shuiwangzhijia.shuidian.bean.MyWalletInfoBean;
import com.shuiwangzhijia.shuidian.newmodule.activity.BalanceWalletActivity;
import com.shuiwangzhijia.shuidian.utils.MyUtils;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 地址适配器
 * created by wangsuli on 2018/8/17.
 */
public class MyWalletAdapter extends RecyclerView.Adapter<MyWalletAdapter.ViewHolder> {
    private final Context mContext;

    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private List<MyWalletInfoBean.DataBean.ListBean> data;


    public MyWalletAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_wallet, parent, false);
       /* if(getItemCount()==1){
            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(params);
        }*/
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        MyWalletInfoBean.DataBean.ListBean listBean = data.get(position);
        holder.mFaceValue.setText(listBean.getBalance());
        holder.mWaterFactoryName.setText(listBean.getWater_name());
        holder.mWaterShopName.setText(listBean.getNickname());
        MyUtils.setImg(holder.mWaterShopHead, listBean.getHeader_pic(),"");
        holder.mParticulars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BalanceWalletActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putSerializable("list", (Serializable) data);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerViewItemClickListener != null)
                    onRecyclerViewItemClickListener.onItemClick(position);
            }
        });
    }

    public MyWalletInfoBean.DataBean.ListBean getItem(int position) {
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

    public void setData(List<MyWalletInfoBean.DataBean.ListBean> result) {
        data = result;
    }

    public void refreshData(List<MyWalletInfoBean.DataBean.ListBean> result){
        data.clear();
        data.addAll(result);
        notifyDataSetChanged();
    }

    public void addData(List<MyWalletInfoBean.DataBean.ListBean> result) {
        data.addAll(result);
        notifyDataSetChanged();
    }

    public interface OnRecyclerViewItemClickListener {
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
