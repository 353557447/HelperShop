package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.SmartEquipCountersBean;
import com.shuiwangzhijia.shuidian.utils.MyUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EquipDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<SmartEquipCountersBean.DataBean> data;
    private int mScreenWidth;
    private int mCabinetCounts;
    private String mCabinetName;

    public EquipDetailsAdapter(Context context) {
        this.mContext = context;
        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_equip_details, parent, false);
            RelativeLayout rootViewOne = view.findViewById(R.id.root_view_one);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mScreenWidth / 4 - 12);
            rootViewOne.setLayoutParams(lp);
            return new OneViewHolder(view);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_equip_switch, parent, false);
            RelativeLayout rootViewTwo = view.findViewById(R.id.root_view_two);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mScreenWidth / 4 - 12);
            rootViewTwo.setLayoutParams(lp);
            return new TwoViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        int itemViewType = getItemViewType(position);
        SmartEquipCountersBean.DataBean dataBean = data.get(position);
        if (itemViewType == 1) {
            if (dataBean.isChecked())
                ((OneViewHolder) holder).mRootViewOne.setBackgroundResource(R.drawable.equip_details_item_bg);
            else
                ((OneViewHolder) holder).mRootViewOne.setBackgroundColor(Color.parseColor("#ffffff"));
            ((OneViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClick(position);
                }
            });

            String goodsId = dataBean.getGoods_id();
            if (goodsId == null) {
                ((OneViewHolder) holder).mGoodsIv.setImageResource(R.drawable.cabinet_nodata);
                //已清空
                ((OneViewHolder) holder).mOperateGoodsBtn.setText("添加商品");
                ((OneViewHolder) holder).mOperateGoodsBtn.setTextColor(Color.parseColor("#515F85"));
                ((OneViewHolder) holder).mOperateGoodsBtn.setBackgroundResource(R.drawable.c515f85_stroke_bg);
                ((OneViewHolder) holder).mRootRl.setBackgroundColor(Color.parseColor("#40000000"));
            } else {
                //有商品
                MyUtils.setImg(((OneViewHolder) holder).mGoodsIv, dataBean.getPicturl(),"goods/");
                ((OneViewHolder) holder).mOperateGoodsBtn.setText("修改商品");
                ((OneViewHolder) holder).mOperateGoodsBtn.setTextColor(Color.parseColor("#26D1A8"));
                ((OneViewHolder) holder).mOperateGoodsBtn.setBackgroundResource(R.drawable.green_stroke_big_radius_bg);
                ((OneViewHolder) holder).mRootRl.setBackgroundColor(Color.parseColor("#40ffffff"));
            }
            ((OneViewHolder) holder).mOperateGoodsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnModifyClickListener != null) {
                        mOnModifyClickListener.OnModifyClick(position);
                    }
                }
            });

        } else {
            ((TwoViewHolder) holder).mCounterCounts.setText("柜子数量："+mCabinetCounts);
            ((TwoViewHolder) holder).mCurrentCounterName.setText(mCabinetName);

            ((TwoViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnSwitchClickListener != null)
                        mOnSwitchClickListener.OnSwitchClick();
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 1 ? 2 : 1;
    }


    public void setData(List<SmartEquipCountersBean.DataBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    private OnItemClickListener mOnItemClickListener;

    public void setCounterCounts(int size) {
        mCabinetCounts=size;
    }

    public void setCounterName(int cabinetNumber) {
        mCabinetName=cabinetNumber+"号柜子";
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }


    private OnSwitchClickListener mOnSwitchClickListener;

    public interface OnSwitchClickListener {
        void OnSwitchClick();
    }

    public void setOnSwitchClickListener(OnSwitchClickListener listener) {
        mOnSwitchClickListener = listener;
    }


    private OnModifyClickListener mOnModifyClickListener;

    public interface OnModifyClickListener {
        void OnModifyClick(int position);
    }

    public void setOnModifyClickListener(OnModifyClickListener listener) {
        mOnModifyClickListener = listener;
    }

    public class OneViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.equip_index)
        TextView mEquipIndex;
        @BindView(R.id.goods_iv)
        ImageView mGoodsIv;
        @BindView(R.id.operate_goods_btn)
        TextView mOperateGoodsBtn;
        @BindView(R.id.root_view_one)
        RelativeLayout mRootViewOne;
        @BindView(R.id.root_rl)
        RelativeLayout mRootRl;

        public OneViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class TwoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.counter_counts)
        TextView mCounterCounts;
        @BindView(R.id.current_counter_name)
        TextView mCurrentCounterName;
        public TwoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
