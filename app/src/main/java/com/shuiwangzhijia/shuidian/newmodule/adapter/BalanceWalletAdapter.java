package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.MyWalletInfoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 地址适配器
 * created by wangsuli on 2018/8/17.
 */
public class BalanceWalletAdapter extends RecyclerView.Adapter<BalanceWalletAdapter.ViewHolder> {
    private final Context mContext;

    private OnItemClickListener mOnItemClickListener;
    private List<MyWalletInfoBean.DataBean.ListBean> data;


    public BalanceWalletAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_touch_balance, parent, false);
       /* if(getItemCount()==1){
            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(params);
        }*/
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        MyWalletInfoBean.DataBean.ListBean listBean = data.get(position);
        holder.mWaterFactoryName.setText(listBean.getWater_name());
        holder.mFaceValue.setText(listBean.getBalance());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener!=null)
                    mOnItemClickListener.onItemClick(position);
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setData(List<MyWalletInfoBean.DataBean.ListBean> result) {
        data = result;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.water_factory_name)
        TextView mWaterFactoryName;
        @BindView(R.id.face_value)
        TextView mFaceValue;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
