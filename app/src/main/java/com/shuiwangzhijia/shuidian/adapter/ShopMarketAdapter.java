package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.ShopMarketData;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 店铺营销适配器
 * created by wangsuli on 2018/8/17.
 */
public class ShopMarketAdapter extends RecyclerView.Adapter<ShopMarketAdapter.ViewHolder> {
    private final Context mContext;


    private OnViewItemClickListener mOnViewItemClickListener;

    public ShopMarketData getData() {
        return data;
    }

    private ShopMarketData data;

    public ShopMarketAdapter(Context context) {
        mContext = context;
        data = new ShopMarketData();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_shop_market, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        holder.recyclerView.setHasFixedSize(true);
        ShopMarketItemAdapter itemAdapter = new ShopMarketItemAdapter(mContext,false);
        holder.recyclerView.setAdapter(itemAdapter);
        if (position == 0) {
            holder.title.setText("优惠券");
            holder.title.setCompoundDrawables(mContext.getResources().getDrawable(R.drawable.youhuiquan),null,null,null);
            itemAdapter.setData(data.getCoupon());
        } else {
            holder.title.setText("水票");
            holder.title.setCompoundDrawables(mContext.getResources().getDrawable(R.drawable.shuipiao_blue),null,null,null);
            itemAdapter.setData(data.getWater());
        }
        holder.hintBtn.setTag(position);
        holder.hintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnViewItemClickListener.onNoteClick((Integer) view.getTag());
            }
        });
        holder.setBtn.setTag(position);
        holder.setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnViewItemClickListener.onSetClick((Integer) view.getTag());
            }
        });
    }

    public void setData(ShopMarketData result) {
        data = result;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public void setOnItemClickListener(OnViewItemClickListener listener) {
        mOnViewItemClickListener = listener;
    }


    public interface OnViewItemClickListener {
        void onSetClick(int position);

        void onNoteClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.hintBtn)
        ImageView hintBtn;
        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;
        @BindView(R.id.setBtn)
        TextView setBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
