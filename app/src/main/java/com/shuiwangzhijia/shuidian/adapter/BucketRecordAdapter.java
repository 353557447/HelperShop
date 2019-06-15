package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.BucketRecordBean;
import com.shuiwangzhijia.shuidian.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 空桶记录适配器
 * created by wangsuli on 2018/8/17.
 */
public class BucketRecordAdapter extends RecyclerView.Adapter<BucketRecordAdapter.ViewHolder> {
    private final Context mContext;


    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public List<BucketRecordBean> getData() {
        return data;
    }

    public void setData(List<BucketRecordBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<BucketRecordBean> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    private List<BucketRecordBean> data;

    private boolean fromStoreOwe;


    public BucketRecordAdapter(Context context, boolean fromStoreOwe) {
        mContext = context;
        this.fromStoreOwe = fromStoreOwe;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_bucket_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecyclerViewItemClickListener.onItemClick((Integer) view.getTag());
            }
        });
        BucketRecordBean item = getItem(position);
        holder.dateTv.setText(DateUtils.getFormatDateStr(item.getUpdate_time() * 1000));
        if (fromStoreOwe) {
            holder.title.setText(item.getGname());
            holder.type.setVisibility(View.VISIBLE);
            holder.shopName.setVisibility(View.GONE);
            int save = item.getNum();
            if (item.getStatus() == 0 || item.getStatus() == 1 || item.getStatus() ==2 || item.getStatus() == 4) {
                holder.type.setText("存");
                holder.numTv.setText("+" + save);
            } else {
                holder.type.setText("欠");
                holder.numTv.setText("-" + save);
            }

        } else {
            holder.type.setVisibility(View.GONE);
            holder.shopName.setVisibility(View.VISIBLE);
            holder.shopName.setText(item.getSname());
            String flag = "";
            switch (item.getStatus()) {
                case 0:
                    holder.title.setText("购桶");
//                    item.setStatus(6);
                    flag = "+";
                    break;
                case 1:
                    holder.title.setText("回桶");
//                    item.setStatus(2);
                    flag = "+";
                    break;
                case 2:
                    holder.title.setText("押桶");
//                    item.setStatus(1);
                    flag = "+";
                    break;
                case 3:
                    holder.title.setText("退桶");
//                    item.setStatus(3);
                    flag = "-";
                    break;
                case 4:
                    holder.title.setText("换桶");
//                    item.setStatus(5);
                    flag = "+";
                    break;
                case 5:
                    holder.title.setText("订单桶");
//                    item.setStatus(4);
                    flag = "-";
                    break;
            }
            holder.numTv.setText(item.getNum()+"");

        }


    }

    public BucketRecordBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        onRecyclerViewItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.shopName)
        TextView shopName;
        @BindView(R.id.dateTv)
        TextView dateTv;
        @BindView(R.id.type)
        TextView type;
        @BindView(R.id.numTv)
        TextView numTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
