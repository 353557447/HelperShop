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
import com.shuiwangzhijia.shuidian.dialog.EditAmountDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xxc on 2019/4/1.
 */

public class KeepListAdapter extends RecyclerView.Adapter<KeepListAdapter.ViewHolder> {
    private final Context mContext;
    private OnViewItemClickListener mOnViewItemClickListener;
    private List<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> mData;
    private List<ActiveBean> mHotCityBeanList;
    private ActiveBean mActiveBean;
    public KeepListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_keep, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Gson gson = new Gson();
        final ShowPlantsBean.HarvestInfoBean.OrderGoodsBean item = getItem(position);
        Glide.with(mContext).load(Constant.GOODS_IMAGE_URL+item.getPicturl()).placeholder(R.drawable.wutupian).into(holder.mGoodsPic);
        if (item.getPrice() == null){
            holder.mPrice.setText("￥"+item.getPprice());
        }else {
            holder.mPrice.setText("￥"+item.getPrice());
        }
        holder.mGoodsName.setText(item.getGname());
        holder.mCounts.setText(item.getCount()+"");
        holder.mGoodsCb.setChecked(item.isCheck());
        String active = item.getActive();
        int activity_type = item.getActivity_type();
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
//                    holder.mFullReduction.setText("满"+mActiveBean.getFull()+"赠"+mActiveBean.getLinks().get(0).getName());
                    holder.mFullReduction.setText("满"+mActiveBean.getFull()+"赠水票");
                }
            }
        }else {
            holder.mFullReduction.setVisibility(View.GONE);
        }
        holder.mSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnViewItemClickListener.onSubtractClick(position);
            }
        });

        holder.mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnViewItemClickListener.onAddGoodsClick(position);
            }
        });

        holder.mGoodsCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnViewItemClickListener.onSelectClick(position);
            }
        });

        holder.mCounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int num = mData.get(position).getCount();
                EditAmountDialog dialog = new EditAmountDialog(mContext, num, new EditAmountDialog.EditPurchaseAmountConfirmListener() {
                    @Override
                    public void onEditPurchaseAmountConfirm(int count) {
                        if (mOnViewItemClickListener != null) {
                            mOnViewItemClickListener.doEditClick(position, count);
                        }
                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (mData == null) ?0:mData.size();
    }

    public void setData(List<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public ShowPlantsBean.HarvestInfoBean.OrderGoodsBean getItem(int position) {
        return mData.get(position);
    }

    public interface OnViewItemClickListener {
        void onSelectClick(int position);
        void onAddGoodsClick(int position);
        void onSubtractClick(int position);
        void doEditClick(int position, int count);
    }

    public void setOnItemClickListener(OnViewItemClickListener listener) {
        mOnViewItemClickListener = listener;
    }

    public List<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> getData(){
        return mData;
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
        @BindView(R.id.rebate)
        TextView mRebate;
        @BindView(R.id.full_reduction)
        TextView mFullReduction;
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
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
