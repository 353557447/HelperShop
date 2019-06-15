package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.Constant;
import com.shuiwangzhijia.shuidian.bean.ActiveBean;
import com.shuiwangzhijia.shuidian.bean.ShowPlantsBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class OftenBuyAdapter extends RecyclerView.Adapter<OftenBuyAdapter.ViewHolder> {
    private final Context mContext;
    private ArrayList<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> mData;
    private List<ActiveBean> mHotCityBeanList;
    private ActiveBean mActiveBean;
    private OnViewItemClickListener mOnViewItemClickListener;

    public OftenBuyAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_often_buy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Gson gson = new Gson();
        final ShowPlantsBean.HarvestInfoBean.OrderGoodsBean item = getItem(position);
        holder.mGoodsName.setText(item.getGname());
        holder.mPrice.setText("￥" + item.getPprice());
        holder.mCounts.setText(item.getCount()+"");
        Glide.with(mContext).load(Constant.GOODS_IMAGE_URL+item.getPicturl()).placeholder(R.drawable.wutupian).into(holder.mGoodsPic);
        int activity_type = item.getActivity_type();
        String active = item.getActive();
        if (activity_type == 1){
            mHotCityBeanList = gson.fromJson(active, new TypeToken<List<ActiveBean>>() {}.getType());
        }else {
            mActiveBean = gson.fromJson(active, ActiveBean.class);
        }
        if (activity_type == 1 || activity_type == 2){
            holder.mFullReduction.setVisibility(View.VISIBLE);
            if (activity_type == 1){
                if (mHotCityBeanList != null){
                    holder.mFullReduction.setText("满"+mHotCityBeanList.get(0).getFull()+"减"+mHotCityBeanList.get(0).getReduce());
                }
            }else if (activity_type == 2){
                if (mActiveBean != null){
                    holder.mFullReduction.setText("满"+mActiveBean.getFull()+"赠水票");
                }
            }
        }else {
            holder.mFullReduction.setVisibility(View.GONE);
        }
        holder.mSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnViewItemClickListener != null){
                    mOnViewItemClickListener.onCutClick(position);
                }
            }
        });
        holder.mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnViewItemClickListener != null){
                    mOnViewItemClickListener.onAddClick(position);
                }
            }
        });
    }

    public ArrayList<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> getData(){
        return mData;
    }

    @Override
    public int getItemCount() {
        return (mData == null) ? 0 : mData.size();
    }

    public void setData(ArrayList<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnViewItemClickListener listener) {
        mOnViewItemClickListener = listener;
    }

    public interface OnViewItemClickListener {
        void onCutClick(int position);

        void onAddClick(int position);
    }

    public ShowPlantsBean.HarvestInfoBean.OrderGoodsBean getItem(int position) {
        return mData.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_cb)
        CheckBox mGoodsCb;
        @BindView(R.id.goods_pic)
        ImageView mGoodsPic;
        @BindView(R.id.goods_pic_fl)
        FrameLayout mGoodsPicFl;
        @BindView(R.id.goods_name)
        TextView mGoodsName;
        @BindView(R.id.price)
        TextView mPrice;
        @BindView(R.id.subtract)
        RelativeLayout mSubtract;
        @BindView(R.id.counts)
        TextView mCounts;
        @BindView(R.id.add)
        RelativeLayout mAdd;
        @BindView(R.id.root_rl)
        RelativeLayout mRootRl;
        @BindView(R.id.btnDelete)
        Button mBtnDelete;
        @BindView(R.id.full_reduction)
        TextView mFullReduction;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
