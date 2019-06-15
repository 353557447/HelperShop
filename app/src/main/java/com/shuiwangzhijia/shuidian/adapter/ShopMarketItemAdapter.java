package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.ShopMarketBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 营销内部 和 空桶管理统计数据 item适配器
 * created by wangsuli on 2018/8/17.
 */
public class ShopMarketItemAdapter extends RecyclerView.Adapter<ShopMarketItemAdapter.ViewHolder> {
    private final Context mContext;
    private boolean ticketMangeFlag = false;

    public List<ShopMarketBean> getData() {
        return data;
    }

    public void setData(List<ShopMarketBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    private List<ShopMarketBean> data;


    public ShopMarketItemAdapter(Context context, boolean ticketMangeFlag) {
        mContext = context;
        this.ticketMangeFlag = ticketMangeFlag;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_shop_market_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setBackgroundResource(ticketMangeFlag ? R.color.color_f5f5f5 : R.color.color_ffffff);
        ShopMarketBean item = getItem(position);
        holder.title.setText(item.getTitle());
        holder.num.setText(item.getNum());
    }

    public ShopMarketBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.num)
        TextView num;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
