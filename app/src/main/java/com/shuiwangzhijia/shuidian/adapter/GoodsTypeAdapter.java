package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.GoodsBean;
import com.shuiwangzhijia.shuidian.bean.GoodsManageBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GoodsTypeAdapter extends RecyclerView.Adapter<GoodsTypeAdapter.ViewHolder> {
    private final Context mContext;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    private ArrayList<GoodsManageBean> data;

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
        notifyDataSetChanged();
    }

    private int selectIndex=0;

    public GoodsTypeAdapter(Context context) {
        mContext = context;
        data=new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_goods_type, parent, false);
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
        GoodsManageBean item = getItem(position);
        holder.title.setText(item.getGname());
      //  holder.show.setVisibility(position == selectIndex ? View.VISIBLE : View.GONE);
        holder.title.setTextColor(position == selectIndex ? mContext.getResources().getColor(R.color.color_black) : mContext.getResources().getColor(R.color.color_999999));
        holder.root.setBackgroundColor(position == selectIndex ? mContext.getResources().getColor(R.color.color_ffffff) : mContext.getResources().getColor(R.color.color_f5f5f5));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        onRecyclerViewItemClickListener = listener;
    }

    public void setData(ArrayList<GoodsManageBean> result) {
        data =result;
        notifyDataSetChanged();
    }
    public void setItem(String gtype,ArrayList<GoodsBean> result) {
        for (GoodsManageBean bean : data) {
            if(gtype.equals(bean.getGtype())){
                bean.setList(result);
            }
        }
    }
    public GoodsManageBean getItem(int position) {
        return data.get(position);
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    /*    @BindView(R.id.show)
        View show;*/
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
