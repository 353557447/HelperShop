package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.TicketBean;
import com.shuiwangzhijia.shuidian.utils.DateUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xxc on 2019/4/2.
 */

public class SelectWaterAdapter extends RecyclerView.Adapter<SelectWaterAdapter.ViewHolder> {
    private final Context mContext;
    private ArrayList<TicketBean> mData;
    private OnViewItemClickListener mOnViewItemClickListener;

    public SelectWaterAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_select_water, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        TicketBean bean = getItem(position);
        holder.mName.setText(bean.getS_name());
        holder.mRemark.setText(bean.getGname());
        holder.mCounts.setText(bean.getCount()+"");
        if (bean.getCuse() == 1){
            holder.setVisibility(true);
        }else {
            holder.setVisibility(false);
        }
        if (bean.getStart() > 0 && bean.getEnd() > 0) {
            holder.mTime.setText("有效期限:" + DateUtils.getYMDTime(bean.getStart()) + "~" + DateUtils.getYMDTime(bean.getEnd()));
        } else {
            holder.mTime.setText("有效期限:" + "永久有效");

        }
        setNumStyle(holder.mNumber,"",bean.getSum()+"","张");
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

    }


    private void setNumStyle(TextView text, String first, String content, String second) {
        SpannableString spanString = new SpannableString(first + content + second);
        spanString.setSpan(new AbsoluteSizeSpan(27, true), first.length(), (content + first).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);//字体大小
        spanString.setSpan(new StyleSpan(Typeface.BOLD), first.length(), (content + first).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);//字体加粗
        text.setText(spanString);
    }

    @Override
    public int getItemCount() {
        return (mData == null) ? 0 : mData.size();
    }

    public void setData(ArrayList<TicketBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public ArrayList<TicketBean> getData() {
        return mData;
    }

    public interface OnViewItemClickListener {
        void onAddGoodsClick(int position);
        void onSubtractClick(int position);
    }

    public void setOnItemClickListener(OnViewItemClickListener listener) {
        mOnViewItemClickListener = listener;
    }

    public TicketBean getItem(int position) {
        return mData.get(position);
    }

    public void addData(ArrayList<TicketBean> result) {
        mData.addAll(result);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.remark)
        TextView mRemark;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.number)
        TextView mNumber;
        @BindView(R.id.subtract)
        TextView mSubtract;
        @BindView(R.id.counts)
        TextView mCounts;
        @BindView(R.id.add)
        TextView mAdd;
        public void setVisibility(boolean isVisible){
            RecyclerView.LayoutParams param = (RecyclerView.LayoutParams)itemView.getLayoutParams();
            if (isVisible){
                param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                param.width = LinearLayout.LayoutParams.MATCH_PARENT;
                itemView.setVisibility(View.VISIBLE);
            }else{
                itemView.setVisibility(View.GONE);
                param.height = 0;
                param.width = 0;
            }
            itemView.setLayoutParams(param);
        }
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
