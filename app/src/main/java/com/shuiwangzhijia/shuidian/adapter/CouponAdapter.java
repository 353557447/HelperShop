package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.CouponBean;
import com.shuiwangzhijia.shuidian.view.CouponView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 优惠券适配器
 * created by wangsuli on 2018/8/17.
 */
public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {
    private final Context mContext;


    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private List<CouponBean> data;
    private int type;
    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    private boolean isShow = true;//默认显示 可以弹窗
    public CouponAdapter(Context context, int type) {
        mContext = context;
        this.type = type;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_coupon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CouponBean bean = getItem(position);
        bean.setShow(isShow);
        bean.setState(type);
        bean.setPosition(position);
        holder.couponView.setData(bean);
        holder.couponView.setOnItemClickListener(new CouponView.OnItemClickListener() {
            @Override
            public void onTurnClick(int position) {
                onRecyclerViewItemClickListener.onTurnClick(position);
            }

            @Override
            public void onOpenCloseClick(int position) {
                onRecyclerViewItemClickListener.onOpenCloseClick(position);
            }
        });
    }

    public CouponBean getItem(int position) {
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

    public void setData(List<CouponBean> result) {
        data = result;
        notifyDataSetChanged();
    }

    public void addData(List<CouponBean> result) {
        data.addAll(result);
        notifyDataSetChanged();
    }

    public interface OnRecyclerViewItemClickListener {
        void onTurnClick(int position);

        void onOpenCloseClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.couponView)
        CouponView couponView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
