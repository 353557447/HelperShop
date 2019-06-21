package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.Constant;
import com.shuiwangzhijia.shuidian.bean.EmptyTongBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BucketOrderItemAdapter extends RecyclerView.Adapter<BucketOrderItemAdapter.ViewHolder> {
    private final Context mContext;
    private List<EmptyTongBean> mData;

    public BucketOrderItemAdapter(Context context, List<EmptyTongBean> goods) {
        this.mContext = context;
        this.mData = goods;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.bucket_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EmptyTongBean item = mData.get(position);
        holder.mTitleTv.setText(item.getGname()+"空桶");
        holder.mShopName.setText(item.getSname());
        Glide.with(mContext).load(Constant.GOODS_IMAGE_URL + item.getPicturl()).placeholder(R.color.color_eeeeee).into(holder.mImage);
        setTextStyle(holder.mPolicyHint, "政策说明:", item.getEmpty_policy());
        holder.mPriceTv.setText(item.getEmpty_price());
        holder.mNumTv.setText("x"+item.getNum());
//        if (item.isPolicyShow()) {
//            policyHint.setVisibility(VISIBLE);
//        } else {
//            policyHint.setVisibility(GONE);
//        }
    }

    public EmptyTongBean getItem(int position){
        return mData.get(position);
    }

    private void setTextStyle(TextView text, String first, String content) {
        SpannableString spanString = new SpannableString(first + content);
        spanString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_db2520)), 0, first.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        text.setText(spanString);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<EmptyTongBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView mImage;
        @BindView(R.id.titleTv)
        TextView mTitleTv;
        @BindView(R.id.shopName)
        TextView mShopName;
        @BindView(R.id.priceTv)
        TextView mPriceTv;
        @BindView(R.id.numTv)
        TextView mNumTv;
        @BindView(R.id.payBtn)
        TextView mPayBtn;
        @BindView(R.id.policyHint)
        TextView mPolicyHint;
        @BindView(R.id.llPolicy)
        RelativeLayout mLlPolicy;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
