package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.Constant;
import com.shuiwangzhijia.shuidian.bean.ActiveBean;
import com.shuiwangzhijia.shuidian.bean.GoodsBean;
import com.shuiwangzhijia.shuidian.dialog.EditAmountDialog;
import com.shuiwangzhijia.shuidian.event.CommonEvent;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by xxc on 2019/4/2.
 */

public class ShopcartItemAdapter extends RecyclerView.Adapter<ShopcartItemAdapter.ViewHolder> {
    private final Context mContext;
    private List<GoodsBean> mData;
    private List<ActiveBean> mHotCityBeanList;
    private ActiveBean mActiveBean;

    public ShopcartItemAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_shop_cart_internal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Gson gson = new Gson();
        final GoodsBean bean = mData.get(position);
        Glide.with(mContext).load(Constant.GOODS_IMAGE_URL + bean.getPicturl()).placeholder(R.drawable.wutupian).into(holder.mGoodsPic);
        holder.mGoodsName.setText(bean.getGname());
        holder.mPrice.setText("￥" + bean.getPrice());
        holder.mCounts.setText(bean.getCount() + "");
        KLog.e(bean.getGname()+":"+bean.isCheck());
        holder.mGoodsCb.setChecked(bean.isCheck());
        int activity_type = bean.getActivity_type();
        String active = bean.getActive();
        try {
            if (activity_type == 1) {
                mHotCityBeanList = gson.fromJson(active, new TypeToken<List<ActiveBean>>() {
                }.getType());
            } else {
                mActiveBean = gson.fromJson(active, ActiveBean.class);
            }
        }catch (Exception e){

        }
        if (activity_type == 1 || activity_type == 2) {
            holder.mFullReduction.setVisibility(View.VISIBLE);
            if (activity_type == 1) {
                if (mHotCityBeanList != null) {
                    holder.mFullReduction.setText("满" + mHotCityBeanList.get(0).getFull() + "减" + mHotCityBeanList.get(0).getReduce());
                }
            } else if (activity_type == 2) {
                if (mActiveBean != null) {
//                    holder.mFullReduction.setText("满"+mActiveBean.getFull()+"赠"+mActiveBean.getLinks().get(0).getName());
                    holder.mFullReduction.setText("满" + mActiveBean.getFull() + "赠水票");
                }
            }
        } else {
            holder.mFullReduction.setVisibility(View.GONE);
        }
//        KLog.e("重新进入后:"+bean.isCheck());
        holder.mRootRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.setCheck(!bean.isCheck());
                KLog.d(bean.isCheck());
                holder.mGoodsCb.setChecked(bean.isCheck());
                // eventbus通知商品总价发生变化
                Intent intent = new Intent("show_surface_view");
                intent.putExtra("did",bean.getDid());
                mContext.sendBroadcast(intent);
//                EventBus.getDefault().post(new ShoppingcartEvent(bean.getDid()));
//                EventBus.getDefault().post(new CommonEvent("shoppingCartTotalPriceChanged"));
            }
        });


        holder.mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counts = Integer.parseInt(holder.mCounts.getText().toString().trim());
                holder.mCounts.setText(++counts + "");
                bean.setCount(counts);
                EventBus.getDefault().post(new CommonEvent("shoppingCartTotalPriceChanged"));
            }
        });
        holder.mSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counts = Integer.parseInt(holder.mCounts.getText().toString().trim());
                if (counts == 1) {
                    // java.lang.RuntimeException: This Toast was not created with Toast.makeText()
//                    ToastUitl.showLong("购物车商品数量不能为0");
                    ToastUitl.showToastCustom("购物车商品数量不能为0");
                    return;
                }
                holder.mCounts.setText(--counts + "");
                bean.setCount(counts);
                EventBus.getDefault().post(new CommonEvent("shoppingCartTotalPriceChanged"));
            }
        });

        holder.mCounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counts = Integer.parseInt(holder.mCounts.getText().toString().trim());
                EditAmountDialog mEditPurchaseAmountDialog = new EditAmountDialog(mContext, counts, new EditAmountDialog.EditPurchaseAmountConfirmListener() {
                    @Override
                    public void onEditPurchaseAmountConfirm(int count) {
                        KLog.d(count);
                        holder.mCounts.setText(count + "");
                        bean.setCount(count);
                        EventBus.getDefault().post(new CommonEvent("shoppingCartTotalPriceChanged"));
                    }
                });
                mEditPurchaseAmountDialog.show();
            }
        });

        holder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);
                EventBus.getDefault().post(new CommonEvent("shoppingCartTotalPriceChanged"));
            }
        });

    }

    public void deleteItem(int position) {
        mData.remove(position);
        notifyDataSetChanged();
        EventBus.getDefault().post(new CommonEvent("calculate_shopcart_number"));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    public void setData(List<GoodsBean> data) {
        mData = data;
       // notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mGoodsName;
        TextView mCounts;
        CheckBox mGoodsCb;
        ImageView mGoodsPic;
        RelativeLayout mRootRl;
        TextView mPrice;
        TextView mSubtract;
        TextView mAdd;
        Button mBtnDelete;
        TextView mFullReduction;

        public ViewHolder(View itemView) {
            super(itemView);
            mGoodsName = itemView.findViewById(R.id.goods_name);
            mCounts = itemView.findViewById(R.id.counts);
            mGoodsCb = itemView.findViewById(R.id.goods_cb);
            mRootRl = itemView.findViewById(R.id.root_rl);
            mGoodsPic = itemView.findViewById(R.id.goods_pic);
            mPrice = itemView.findViewById(R.id.price);
            mSubtract = itemView.findViewById(R.id.subtract);
            mAdd = itemView.findViewById(R.id.add);
            mBtnDelete = itemView.findViewById(R.id.btnDelete);
            mFullReduction = itemView.findViewById(R.id.full_reduction);
        }
    }
}
