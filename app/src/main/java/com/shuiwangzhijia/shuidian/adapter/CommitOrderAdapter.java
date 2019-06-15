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
import com.shuiwangzhijia.shuidian.bean.GoodsBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;

import java.util.List;


public class CommitOrderAdapter extends RecyclerView.Adapter<CommitOrderAdapter.ViewHolder> {
    private final Context mContext;
    private List<GoodsBean> mData;
    private List<ActiveBean> mHotCityBeanList;
    private ActiveBean mActiveBean;
    public CommitOrderAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.itme_commit_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Gson gson = new Gson();
        GoodsBean bean = mData.get(position);
        holder.mGoodsName.setText(bean.getGname());
        holder.mPrice.setText("￥" + bean.getPrice());
//        if (type == 2){
//            holder.mNumber.setText("x" + bean.getNum());
//        }else {
            holder.mNumber.setText("x" + bean.getCount());
//        }
        Glide.with(mContext).load(Constant.GOODS_IMAGE_URL + bean.getPicturl()).placeholder(R.drawable.wutupian).into(holder.mGoodsPic);
        KLog.e(Constant.GOODS_IMAGE_URL + bean.getPicturl());
        int activity_type = bean.getActivity_type();
        String active = bean.getActive();
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
    }

    @Override
    public int getItemCount() {
        return (mData == null) ? 0 : mData.size();
    }

    public void setData(List<GoodsBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public List<GoodsBean> getData(){
        return mData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox mGoodsCb;
        ImageView mGoodsPic;
        FrameLayout mGoodsPicFl;
        TextView mGoodsName;
        TextView mPrice;
        RelativeLayout mRootRl;
        Button mBtnDelete;
        TextView mNumber;
        TextView mFullReduction;
        public ViewHolder(View itemView) {
            super(itemView);
            mGoodsCb = itemView.findViewById(R.id.goods_cb);
            mGoodsPic = itemView.findViewById(R.id.goods_pic);
            mGoodsPicFl = itemView.findViewById(R.id.goods_pic_fl);
            mGoodsName = itemView.findViewById(R.id.goods_name);
            mPrice = itemView.findViewById(R.id.price);
            mRootRl = itemView.findViewById(R.id.root_rl);
            mBtnDelete = itemView.findViewById(R.id.btnDelete);
            mNumber = itemView.findViewById(R.id.number);
            mFullReduction = itemView.findViewById(R.id.full_reduction);
        }
    }
}
