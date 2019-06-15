package com.shuiwangzhijia.shuidian.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.Constant;
import com.shuiwangzhijia.shuidian.bean.GoodsBean;
import com.shuiwangzhijia.shuidian.event.GoodsEvent;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;



public class CenterOneAdapter extends RecyclerView.Adapter<CenterOneAdapter.ViewHolder> {
    private final Context mContext;
    private final boolean isTop;

    private List<GoodsBean> mData;

    public CenterOneAdapter(Context context, boolean b) {
        this.mContext = context;
        this.isTop = b;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_center_remove, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final GoodsBean item = getItem(position);
        Glide.with(mContext).load(Constant.GOODS_IMAGE_URL + item.getPicturl()).placeholder(R.drawable.wutupian).into(holder.mImage);
        holder.mGoodsName.setText(item.getGname());
        holder.mGoodsPrice.setText("￥"+item.getPrice());
        holder.mShopcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GoodsBean> shopCart = CommonUtils.getShopCart();
                if (shopCart == null) {
                    shopCart = new ArrayList<>();
                }
                item.setCount(item.getLeast_p());
                GoodsBean old = null;
                for (int i = 0; i < shopCart.size(); i++) {
                    old = shopCart.get(i);
                    if (old.getGid().equals(item.getGid())) {
                        item.setCount(old.getCount() + item.getCount());
                        shopCart.remove(old);
                    }
                }
                shopCart.add(item);
                CommonUtils.saveShopCart(shopCart);
                EventBus.getDefault().post(new GoodsEvent(shopCart.size(), item.getPicturl()));
                ToastUitl.showToastCustom("加入购物车成功");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<GoodsBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public GoodsBean getItem(int position) {
        return mData.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView mImage;
        @BindView(R.id.shopcart)
        ImageView mShopcart;
        @BindView(R.id.goods_name)
        TextView mGoodsName;
        @BindView(R.id.goods_price)
        TextView mGoodsPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
