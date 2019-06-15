package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.CustomerBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 购物车适配器
 * created by wangsuli on 2018/8/17.
 */
public class CustomerManageAdapter extends RecyclerView.Adapter<CustomerManageAdapter.ViewHolder> {
    private final Context mContext;

    private OnViewItemClickListener mOnViewItemClickListener;
    private ArrayList<CustomerBean> data;

    public CustomerManageAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_customer_manage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnViewItemClickListener.onItemClick((Integer) view.getTag());
            }
        });
        CustomerBean bean = data.get(position);
        Glide.with(mContext).load(bean.getHeader_pic()).placeholder(R.drawable.morentouxiang).into(holder.avatar);
        holder.name.setText(bean.getPhone());
        holder.num.setText(bean.getTnum());

    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    public void setOnItemClickListener(OnViewItemClickListener listener) {
        mOnViewItemClickListener = listener;
    }

    public void setData(ArrayList<CustomerBean> result) {
        data = result;
        notifyDataSetChanged();
    }

    public void addData(ArrayList<CustomerBean> result) {
        data.addAll(result);
        notifyDataSetChanged();
    }

    public CustomerBean getItem(int position) {
        return data.get(position);
    }

    public interface OnViewItemClickListener {
        void onItemClick(int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.num)
        TextView num;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
