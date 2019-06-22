package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.MycouBean;
import com.shuiwangzhijia.shuidian.utils.DateUtils;
import com.shuiwangzhijia.shuidian.utils.MyUtils;
import com.socks.library.KLog;
import com.tencent.tencentmap.mapsdk.maps.model.HeatOverlay;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MycouAdapter extends RecyclerView.Adapter<MycouAdapter.ViewHolder> {
    private Context mContext;
    private List<MycouBean.ListBean> mData;
    private int mType;

    public MycouAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_cou, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MycouBean.ListBean bean = getItem(position);
        int ctype = bean.getCtype();
        if (ctype == 1) {
            holder.mRemark.setText("下单抵扣");
        } else if (ctype == 0) {
            holder.mRemark.setText("满" + bean.getFull() + "元可用");
        }
        holder.mMoney.setText(bean.getAmount());
        holder.mTicketname.setText(bean.getCname());
        holder.mShopname.setText(bean.getDname() + " · " + bean.getCname());
        if (bean.getStart_time() > 0 && bean.getEnd_time() > 0) {
            holder.mTime.setText("有效期限:" + DateUtils.getYMDTime(bean.getStart_time()) + "~" + DateUtils.getYMDTime(bean.getEnd_time()));
        } else {
            holder.mTime.setText("有效期限:" + "永久有效");
        }
        KLog.d(mType);
        int coupon_type = bean.getCoupon_type();
        switch (coupon_type) {
            case 1:
                holder.goodsIv.setImageResource(R.drawable.quanbu_keshiyong);
                break;
            case 2:
                holder.goodsIv.setImageResource(R.drawable.quanbu_keshiyong);
                break;
            case 3:
                MyUtils.setImg(holder.goodsIv, bean.getPicturl(), "goods/");
                break;
        }
        if (mType == 3) {
            //holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.gray_solid_five_radius_bg));
            // holder.mImage.setVisibility(View.GONE);
            holder.mEndRl.setBackgroundResource(R.drawable.my_coupon_bg_endthree);
            holder.mWenziHint.setVisibility(View.GONE);
            holder.mTupianHint.setImageResource(R.drawable.yishixiao_wenzi);
            holder.mTupianHint.setVisibility(View.VISIBLE);
        } else if (mType == 2) {
            //holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.white_solid_five_radius_bg));
            // holder.mImage.setVisibility(View.VISIBLE);
            holder.mEndRl.setBackgroundResource(R.drawable.my_coupon_bg_endtwo);
            holder.mWenziHint.setVisibility(View.GONE);
            holder.mTupianHint.setImageResource(R.drawable.yishiyong_wenzi);
            holder.mTupianHint.setVisibility(View.VISIBLE);
        } else if (mType == 1) {
            // holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.white_solid_five_radius_bg));
            // holder.mImage.setVisibility(View.GONE);
            holder.mEndRl.setBackgroundResource(R.drawable.my_coupon_bg_endone);
            holder.mWenziHint.setVisibility(View.VISIBLE);
            holder.mTupianHint.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return (mData == null) ? 0 : mData.size();
    }

    public MycouBean.ListBean getItem(int position) {
        return mData.get(position);
    }

    public void setData(List<MycouBean.ListBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void addData(List<MycouBean.ListBean> result) {
        mData.addAll(result);
        notifyDataSetChanged();
    }

    public void setType(int type) {
        this.mType = type;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.money)
        TextView mMoney;
        @BindView(R.id.remark)
        TextView mRemark;
        @BindView(R.id.ticketname)
        TextView mTicketname;
        @BindView(R.id.shopname)
        TextView mShopname;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.wenzi_hint)
        TextView mWenziHint;
        @BindView(R.id.image)
        ImageView mImage;
        @BindView(R.id.tupian_hint)
        ImageView mTupianHint;
        @BindView(R.id.goods_iv)
        ImageView goodsIv;
        @BindView(R.id.end_rl)
        RelativeLayout mEndRl;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
