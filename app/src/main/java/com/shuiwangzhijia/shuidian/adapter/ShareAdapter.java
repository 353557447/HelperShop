package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.shuiwangzhijia.shuidian.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ViewHolder> {
    private final Context mContext;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public ShareAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_share, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecyclerViewItemClickListener.onItemClick((int)view.getTag());
            }
        });
        int mipmapId = mContext.getResources().getIdentifier("share_"+position, "drawable", mContext.getPackageName());
        holder.image.setImageResource(mipmapId);
    }

    @Override
    public int getItemCount() {
        return 4;
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
