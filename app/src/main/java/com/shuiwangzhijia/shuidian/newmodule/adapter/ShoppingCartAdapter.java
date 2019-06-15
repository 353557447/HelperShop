package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.ActiveBean;
import com.shuiwangzhijia.shuidian.bean.GoodsBean;
import com.shuiwangzhijia.shuidian.utils.MyUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {
    private Context mContext;
    private List<GoodsBean> data;
    private final Gson mGson;
    private List<ActiveBean> mHotCityBeanList;
    private ActiveBean mActiveBean;

    public ShoppingCartAdapter(Context context) {
        this.mContext = context;
        mGson = new Gson();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_shopping_cart_goods, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final GoodsBean dataBean = data.get(position);
        int activityType = dataBean.getActivity_type();
        MyUtils.setImg(holder.mGoodsIv, dataBean.getPicturl(), "goods/");
        holder.mGoodsName.setText(dataBean.getGname());
        holder.mGoodsPrice.setText("￥" + dataBean.getPrice());
        holder.mGoodsCounts.setText(dataBean.getCount()+"");
        if(dataBean.isCheck()){
            holder.mGoodsCb.setChecked(true);
        }else{
            holder.mGoodsCb.setChecked(false);
        }
        String active = dataBean.getActive();
        int rebateFlag = dataBean.getRebate_flag();
        if (rebateFlag == 0) {
            holder.mReturnMoney.setVisibility(View.GONE);
        } else {
            holder.mReturnMoney.setVisibility(View.VISIBLE);
        }
        if (activityType == 1) {
            mHotCityBeanList = mGson.fromJson(active, new TypeToken<List<ActiveBean>>() {
            }.getType());
        } else {
            mActiveBean = mGson.fromJson(active, ActiveBean.class);
        }
        if (activityType == 1 || activityType == 2) {
            holder.mFullDiscounts.setVisibility(View.VISIBLE);
            if (activityType == 1) {
                if (mHotCityBeanList != null) {
                    holder.mFullDiscounts.setText("满" + mHotCityBeanList.get(0).getFull() + "减" + mHotCityBeanList.get(0).getReduce());
                }
            } else if (activityType == 2) {
                if (mActiveBean != null) {
//                    holder.mFullReduction.setText("满"+mActiveBean.getFull()+"赠"+mActiveBean.getLinks().get(0).getName());
                    holder.mFullDiscounts.setText("满" + mActiveBean.getFull() + "赠水票");
                }
            }
        } else {
            holder.mFullDiscounts.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(position);
            }
        });
        holder.mGoodsSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemSubClickListener!=null){
                    mOnItemSubClickListener.onItemSubClick(position);
                }
            }
        });
        holder.mGoodsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemAddClickListener!=null){
                    mOnItemAddClickListener.onItemAddClick(position);
                }
            }
        });
    }


    public void setData(List<GoodsBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }


    private OnItemSubClickListener mOnItemSubClickListener;

    public interface OnItemSubClickListener {
        void onItemSubClick(int position);
    }

    public void setOnItemSubClickListener(OnItemSubClickListener listener) {
        mOnItemSubClickListener = listener;
    }


    private OnItemAddClickListener mOnItemAddClickListener;

    public interface OnItemAddClickListener {
        void onItemAddClick(int position);
    }

    public void setOnItemAddClickListener(OnItemAddClickListener listener) {
        mOnItemAddClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_cb)
        CheckBox mGoodsCb;
        @BindView(R.id.goods_iv)
        ImageView mGoodsIv;
        @BindView(R.id.goods_name)
        TextView mGoodsName;
        @BindView(R.id.return_money)
        TextView mReturnMoney;
        @BindView(R.id.full_discounts)
        TextView mFullDiscounts;
        @BindView(R.id.goods_price)
        TextView mGoodsPrice;
        @BindView(R.id.goods_subtract)
        RelativeLayout mGoodsSubtract;
        @BindView(R.id.goods_counts)
        TextView mGoodsCounts;
        @BindView(R.id.goods_add)
        RelativeLayout mGoodsAdd;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
