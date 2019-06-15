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
import com.shuiwangzhijia.shuidian.bean.ActiveBean;
import com.shuiwangzhijia.shuidian.bean.GoodsBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 采购适配器
 * created by wangsuli on 2018/8/17.
 */
public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder> {
    public static final int TYPE_ALL = 0;
    public static final int TYPE_ALL_1 = 1;
    public static final int TYPE_ALL_2 = 2;
    public static final int TYPE_ALL_3 = 3;
    private final Context mContext;
    private OnSaleClickListener mOnViewItemClickListener;
    private List<GoodsBean> data;
    private List<ActiveBean> mHotCityBeanList;
    private ActiveBean mActiveBean;

    public void setSale(boolean sale) {
        isSale = sale;
        notifyDataSetChanged();
    }

    private boolean isSale = true;

    public PurchaseAdapter(Context context, boolean isSale) {
        mContext = context;
        this.isSale = isSale;
        data = new ArrayList<>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_purchase, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Gson gson = new Gson();
        holder.addBtn.setTag(position);
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnViewItemClickListener.onSelectClick((int) view.getTag());
            }
        });
        GoodsBean bean = getItem(position);
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
        bean.setCount(1);
        Glide.with(mContext).load(Constant.GOODS_IMAGE_URL + bean.getPicturl()).placeholder(R.drawable.wutupian).into(holder.image);
        holder.title.setText(bean.getGname());
        holder.price.setText("￥" + bean.getPrice());
    }


    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    public void setOnSaleClickListener(OnSaleClickListener listener) {
        mOnViewItemClickListener = listener;
    }

    public void setData(ArrayList<GoodsBean> result) {
        data = result;
        notifyDataSetChanged();
    }

    public GoodsBean getItem(int position) {
        return data.get(position);
    }

    public void addData(ArrayList<GoodsBean> result) {
        data.addAll(result);
        notifyDataSetChanged();
    }

    public interface OnSaleClickListener {
        void onSelectClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.addBtn)
        ImageView addBtn;
        @BindView(R.id.rebate)
        TextView mRebate;
        @BindView(R.id.full_reduction)
        TextView mFullReduction;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
