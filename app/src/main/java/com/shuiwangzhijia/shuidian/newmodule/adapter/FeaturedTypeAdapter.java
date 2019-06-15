package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.newmodule.bean.FeaturedGoodsTypeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class FeaturedTypeAdapter extends RecyclerView.Adapter<FeaturedTypeAdapter.ViewHolder> {
    private Context mContext;
    private List<FeaturedGoodsTypeBean.DataBean> data;
    private int selectedItem = 0;

    public FeaturedTypeAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_feature_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTypeName.setText(data.get(position).getT_name());
        if (position == selectedItem) {
            holder.mTypeName.setBackgroundResource(R.drawable.red_solid_big_radius_bg);
            holder.mTypeName.setTextColor(Color.parseColor("#ffffff"));
        }else{
            holder.mTypeName.setBackgroundResource(R.drawable.gray_solid_big_radius_bg);
            holder.mTypeName.setTextColor(Color.parseColor("#212121"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnGooodsTypeClickListener != null)
                    mOnGooodsTypeClickListener.onGoodsTypeItemClick(position);
            }
        });
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }
    public int getSelectedItem(){
        return selectedItem;
    }

    public void setData(List<FeaturedGoodsTypeBean.DataBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    private OnGooodsTypeClickListener mOnGooodsTypeClickListener;

    public interface OnGooodsTypeClickListener {
        void onGoodsTypeItemClick(int position);
    }

    public void setOnItemClickListener(OnGooodsTypeClickListener listener) {
        mOnGooodsTypeClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.type_name)
        TextView mTypeName;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
