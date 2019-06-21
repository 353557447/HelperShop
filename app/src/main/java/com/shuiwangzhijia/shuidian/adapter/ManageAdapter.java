package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ManageAdapter extends RecyclerView.Adapter<ManageAdapter.ViewHolder> {
    private final Context mContext;
    private final String[] titles;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public ManageAdapter(Context context) {
        mContext = context;
        titles = mContext.getResources().getStringArray(R.array.manageTitle);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_manage, parent, false);
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
        holder.title.setText(titles[position]);
        int mipmapId = -1;
            mipmapId = mContext.getResources().getIdentifier("manage_" + position, "drawable", mContext.getPackageName());
            holder.image.setImageResource(mipmapId);
//            holder.image.setImageResource(R.drawable.jingqingqidai);
//            holder.title.setTextColor(mContext.getResources().getColor(R.color.color_999999));
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        onRecyclerViewItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.title)
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
