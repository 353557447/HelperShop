package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.ShopBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品类型适配器
 * created by wangsuli on 2018/8/17.
 */
public class WaterPlantAdapter extends RecyclerView.Adapter<WaterPlantAdapter.ViewHolder> {
    private final Context mContext;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;


    public WaterPlantAdapter(Context context) {
        mContext = context;
        data=new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_water_plant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecyclerViewItemClickListener.onItemClick((int) view.getTag());
            }
        });
        ShopBean item = getItem(position);
        holder.title.setText(item.getSname());
    }


    public void setData(List<ShopBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<ShopBean> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    private List<ShopBean> data;
    public ShopBean getItem(int position) {
        return data.get(position);
    }
    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        onRecyclerViewItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
