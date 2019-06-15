package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.MyWalletInfoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RMDetailsAdapter extends RecyclerView.Adapter<RMDetailsAdapter.ViewHolder> {
    private final Context mContext;

    private OnItemClickListener mOnItemClickListener;
    private List<MyWalletInfoBean.DataBean.ListBean> data;
    private int checkedIndex;


    public RMDetailsAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rm_details, parent, false);
       /* if(getItemCount()==1){
            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(params);
        }*/
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        MyWalletInfoBean.DataBean.ListBean listBean = data.get(position);
        if(checkedIndex==position){
            holder.mRootRl.setBackgroundResource(R.drawable.item_return_money_tem_selected_bg);
        }else{
            holder.mRootRl.setBackgroundResource(R.drawable.item_return_money_tem_unselect_bg);
        }
        holder.mWaterFactoryName.setText(listBean.getWater_name());
        holder.mFaceValue.setText(listBean.getRebate_amount());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener!=null)
                    mOnItemClickListener.onItemClick(position);
            }
        });
    }
    public void setCheckedIndex(int checkedIndex){
        this.checkedIndex=checkedIndex;
        notifyDataSetChanged();
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
        @BindView(R.id.root_rl)
        RelativeLayout mRootRl;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
