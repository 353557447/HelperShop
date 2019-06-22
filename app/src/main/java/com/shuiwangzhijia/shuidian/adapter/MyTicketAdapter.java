package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.DiscountsWaterCouponBean;
import com.shuiwangzhijia.shuidian.bean.TicketBean;
import com.shuiwangzhijia.shuidian.utils.CalculateUtils;
import com.shuiwangzhijia.shuidian.utils.DateUtils;
import com.shuiwangzhijia.shuidian.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyTicketAdapter extends RecyclerView.Adapter<MyTicketAdapter.ViewHolder> {
    private final Context mContext;

    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private List<TicketBean> data;
    private int type;

    public MyTicketAdapter(Context context, int type) {
        mContext = context;
        this.type = type;
        data = new ArrayList<>();
    }

    @Override
    public MyTicketAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
            view = LayoutInflater.from(mContext).inflate(R.layout.item_my_water_ticket_new, parent, false);
            return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(MyTicketAdapter.ViewHolder holder, int position) {
        TicketBean dataBean = getItem(position);
        holder.mName.setText(dataBean.getGname());
        MyUtils.setImg(holder.mGoodsIv,dataBean.getPicturl(),"goods/");
        holder.mWaterFactoryName.setText(dataBean.getSname());
        holder.mPrice.setText(""+dataBean.getPprice());
        holder.mCurrentPrice.setText("￥"+dataBean.getSprice());
        holder.mRemark.setText("节省"+ CalculateUtils.sub(dataBean.getPprice(),Double.parseDouble(dataBean.getSprice()))+"元");
        holder.mCouponCounts.setText(dataBean.getSum() + "张");
        if (dataBean.getStart() > 0 && dataBean.getEnd() > 0) {
            holder.mPeriodOfValidity.setText("有效期限:" + DateUtils.getYMDTime(dataBean.getStart()) + "~" + DateUtils.getYMDTime(dataBean.getEnd()));
        } else {
            holder.mPeriodOfValidity.setText("有效期限:" + "永久有效");
        }
    }


    private void setNumStyle(TextView text, String first, String content, String second) {
        SpannableString spanString = new SpannableString(first + content + second);
        spanString.setSpan(new AbsoluteSizeSpan(18, true), first.length(), (content + first).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);//字体大小
        spanString.setSpan(new StyleSpan(Typeface.BOLD), first.length(), (content + first).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);//字体加粗
        text.setText(spanString);
    }

    public TicketBean getItem(int position) {
        return data.get(position);
    }

    public void deleteItem(int position) {
        data.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        onRecyclerViewItemClickListener = listener;
    }

    public void setData(ArrayList<TicketBean> result) {
        for (TicketBean bean : result) {
            bean.setCheck(bean.getUse() == 1 ? true : false);
        }
        data = result;
        notifyDataSetChanged();
    }

    public void addData(ArrayList<TicketBean> result) {
        data.addAll(result);
        notifyDataSetChanged();
    }

    public interface OnRecyclerViewItemClickListener {
        void onSelectClick(int position);

        void onPayClick(int position);

        void onUpDownClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.goods_iv)
        ImageView mGoodsIv;
        @BindView(R.id.water_factory_name)
        TextView mWaterFactoryName;
        @BindView(R.id.price)
        TextView mPrice;
        @BindView(R.id.remark)
        TextView mRemark;
        @BindView(R.id.current_price)
        TextView mCurrentPrice;
        @BindView(R.id.coupon_counts)
        TextView mCouponCounts;
        @BindView(R.id.period_of_validity)
        TextView mPeriodOfValidity;
        @BindView(R.id.purchase_immediately)
        TextView mPurchaseImmediately;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
