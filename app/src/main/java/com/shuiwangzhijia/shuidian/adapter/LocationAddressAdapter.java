package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.bean.ShopBean;
import com.shuiwangzhijia.shuidian.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 店铺定位地址适配器
 * created by wangsuli on 2018/8/17.
 */
public class LocationAddressAdapter extends RecyclerView.Adapter<LocationAddressAdapter.ViewHolder> {
    private final Context mContext;


    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private List<ShopBean> data;


    public LocationAddressAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_location_address, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ShopBean item = getItem(position);
        holder.shopName.setText(item.getSname());
        holder.location.setText(item.getAddress());
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecyclerViewItemClickListener.onItemClick((Integer) view.getTag());
            }
        });

    }

    public ShopBean getItem(int position) {
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

    public void setData(ArrayList<ShopBean> result) {
        data = result;
        notifyDataSetChanged();
    }

    public void addData(List<ShopBean> result) {
        data.addAll(result);
        notifyDataSetChanged();
    }
    public interface OnRecyclerViewItemClickListener {

        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shopName)
        TextView shopName;
        @BindView(R.id.location)
        TextView location;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
