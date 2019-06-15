package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.ActiveBean;
import com.shuiwangzhijia.shuidian.bean.ActivityPlantsBean;
import com.shuiwangzhijia.shuidian.bean.GoodsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xcc on 2019/4/1.
 */

public class CenterAdapter extends RecyclerView.Adapter<CenterAdapter.ViewHolder> {

    private Context mContext;
    private int type;
    private DividerItemDecoration mDivider;
    private int flag;
    private ArrayList<ActivityPlantsBean> mData;
    private OnSaleClickListener mOnViewItemClickListener;
    private List<ActiveBean> mHotCityBeanList;
    private ActiveBean mActiveBean;

    public CenterAdapter(Context context, int type) {
        this.mContext = context;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_center, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Gson gson = new Gson();
        ActivityPlantsBean item = getItem(position);

        List<GoodsBean> rebate = item.getRebate();
        List<GoodsBean> full_giving = item.getFull_giving();
        List<GoodsBean> full_minus = item.getFull_minus();
        if (type != 1) {
            if ((rebate == null || rebate.size() == 0) && (full_giving == null || full_giving.size() == 0) && (full_minus == null || full_minus.size() == 0)) {
                holder.itemView.setVisibility(View.GONE);
            }
        }

        int activity_type = item.getActivity_type();
        String ruler = item.getRuler();
        if (activity_type == 1) {
            mHotCityBeanList = gson.fromJson(ruler, new TypeToken<List<ActiveBean>>() {
            }.getType());
        } else {
            mActiveBean = gson.fromJson(ruler, ActiveBean.class);
        }
        if (activity_type == 1 || activity_type == 2) {
            holder.mFullReduction.setVisibility(View.VISIBLE);
            if (activity_type == 1) {
                if (mHotCityBeanList != null) {
                    holder.mFullReduction.setText("满" + mHotCityBeanList.get(0).getFull() + "减" + mHotCityBeanList.get(0).getReduce());
                }
            } else if (activity_type == 2) {
                if (mActiveBean != null) {
                    holder.mFullReduction.setText("满" + mActiveBean.getFull() + "赠水票");
                }
            }
        } else {
            holder.mFullReduction.setVisibility(View.GONE);
        }

        if (rebate == null || rebate.size() == 0) {
            holder.mFanliLL.setVisibility(View.GONE);
        } else {
            holder.mRvTwo.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            holder.mRvTwo.setHasFixedSize(true);
            mDivider = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
            mDivider.setDrawable(mContext.getResources().getDrawable(R.drawable.divider_bg_tran_big));
            holder.mRvTwo.addItemDecoration(mDivider);
            CenterOneAdapter centerTwoAdapter = new CenterOneAdapter(mContext, false);
            holder.mRvTwo.setAdapter(centerTwoAdapter);
            centerTwoAdapter.setData(rebate);
        }

        if (activity_type == 2) {
            holder.mManJian.setText("满赠");
            holder.mRvOne.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            holder.mRvOne.setHasFixedSize(true);
            mDivider = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
            mDivider.setDrawable(mContext.getResources().getDrawable(R.drawable.divider_bg_tran_big));
            holder.mRvOne.addItemDecoration(mDivider);
            CenterOneAdapter centerOneAdapter = new CenterOneAdapter(mContext, true);
            holder.mRvOne.setAdapter(centerOneAdapter);
            centerOneAdapter.setData(full_giving);
        } else if (activity_type == 1) {
            holder.mManJian.setText("满减");
            holder.mRvOne.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            holder.mRvOne.setHasFixedSize(true);
            mDivider = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
            mDivider.setDrawable(mContext.getResources().getDrawable(R.drawable.divider_bg_tran_big));
            holder.mRvOne.addItemDecoration(mDivider);
            CenterOneAdapter centerOneAdapter = new CenterOneAdapter(mContext, true);
            holder.mRvOne.setAdapter(centerOneAdapter);
            centerOneAdapter.setData(full_minus);
        } else {
            holder.mManJianll.setVisibility(View.GONE);
        }
        holder.mShopName.setText(item.getSname());
        String recharge = item.getRecharge();
        if (recharge == null || recharge.equals("")) {
            holder.mTopUpText.setVisibility(View.GONE);
            holder.mChongzhiBtn.setVisibility(View.GONE);
        } else {
            holder.mChongzhiBtn.setVisibility(View.VISIBLE);
            holder.mTopUpText.setVisibility(View.VISIBLE);
            holder.mTopUpText.setText(recharge);
        }
        holder.mFoldPreferential.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (flag == 0) {
                    holder.mFoldPreferential.setText("收起优惠");
                    holder.mFoldPreferential.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, mContext.getResources().getDrawable(R.drawable.arrow_up, null), null);
                    holder.mRvOne.setVisibility(View.VISIBLE);
                    holder.mRvTwo.setVisibility(View.VISIBLE);
                    flag = 1;
                } else if (flag == 1) {
                    holder.mFoldPreferential.setText("展开优惠");
                    holder.mFoldPreferential.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, mContext.getResources().getDrawable(R.drawable.arrow_down, null), null);
                    holder.mRvOne.setVisibility(View.GONE);
                    holder.mRvTwo.setVisibility(View.GONE);
                    flag = 0;
                }

            }
        });
        if (type == 1) {
            holder.mFoldPreferential.setVisibility(View.GONE);
            holder.mTopUp.setVisibility(View.GONE);
        } else {
            holder.mFoldPreferential.setVisibility(View.VISIBLE);
            holder.mTopUp.setVisibility(View.VISIBLE);
        }
        holder.mTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnViewItemClickListener != null) {
                    mOnViewItemClickListener.onTopUpClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(ArrayList<ActivityPlantsBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public ActivityPlantsBean getItem(int position) {
        if (mData == null) {
            KLog.d("mData == null");
        }
        return mData.get(position);
    }

    public interface OnSaleClickListener {
        void onTopUpClick(int position);
    }

    public void setOnSaleClickListener(OnSaleClickListener listener) {
        mOnViewItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rv_one)
        RecyclerView mRvOne;
        @BindView(R.id.rv_two)
        RecyclerView mRvTwo;
        @BindView(R.id.top_up)
        TextView mTopUp;
        @BindView(R.id.shop_name)
        TextView mShopName;
        @BindView(R.id.fold_preferential)
        TextView mFoldPreferential;
        @BindView(R.id.full_reduction)
        TextView mFullReduction;
        @BindView(R.id.rebate)
        TextView mRebate;
        @BindView(R.id.top_up_text)
        TextView mTopUpText;
        @BindView(R.id.manjian)
        TextView mManJian;
        @BindView(R.id.manjian_ll)
        LinearLayout mManJianll;
        @BindView(R.id.fanli_ll)
        LinearLayout mFanliLL;
        @BindView(R.id.chongzhi_btn)
        TextView mChongzhiBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
