package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品类型适配器
 * created by wangsuli on 2018/8/17.
 */
public class GoodsTypeAdapter extends RecyclerView.Adapter<GoodsTypeAdapter.ViewHolder> {
    private final Context mContext;
    private List<String> mList;
    private int selectIndex=0;

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
        notifyDataSetChanged();
    }

    public void setData(List<String> list){
        mList=list;
    }

    public GoodsTypeAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_add_equip_goods_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(mList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnTypeItemClickListener!=null)
                    mOnTypeItemClickListener.onTypeItemClick(position);
            }
        });
        holder.root.setBackgroundColor(position == selectIndex ? mContext.getResources().getColor(R.color.white) : mContext.getResources().getColor(R.color.color_f5f5f5));
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }
    private OnTypeItemClickListener mOnTypeItemClickListener;

    public void setOnTypeItemClickListener(OnTypeItemClickListener listener) {
        mOnTypeItemClickListener = listener;
    }

    public interface OnTypeItemClickListener {
        void onTypeItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.root)
        LinearLayout root;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
