package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.Constant;
import com.shuiwangzhijia.shuidian.view.SquareImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商家评论-图片集适配器
 * created by wangsuli on 2018/8/17.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private final Context mContext;
    private final List<String> data;
    private OnRecyclerViewItemClickListener itemClickListener;


    public ImageAdapter(Context context, List<String> list) {
        mContext = context;
        data = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null)
                    itemClickListener.onItemClick((int) view.getTag());
            }
        });
        Glide.with(mContext).load(Constant.COMMENT_IMAGE_URL+data.get(position)).placeholder(R.color.color_eeeeee).into(holder.image);
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        itemClickListener = listener;
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        SquareImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
