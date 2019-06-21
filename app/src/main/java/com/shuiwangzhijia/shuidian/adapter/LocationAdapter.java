package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.LocationBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private final Context mContext;


    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private List<LocationBean> data;


    public LocationAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        LocationBean item = getItem(position);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecyclerViewItemClickListener.onItemClick((Integer) view.getTag());
            }
        });
        holder.check.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        holder.shopName.setText(item.getTitle());
        holder.location.setText(item.getAddress());
    }

    public LocationBean getItem(int position) {
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

    public void setData(List<LocationBean> result) {
        data = result;
        notifyDataSetChanged();
    }

    public void addData(List<LocationBean> result) {
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
        @BindView(R.id.check)
        ImageView check;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
