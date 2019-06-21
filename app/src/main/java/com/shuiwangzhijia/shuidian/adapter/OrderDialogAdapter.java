package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.StoreListBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;



public class OrderDialogAdapter extends RecyclerView.Adapter<OrderDialogAdapter.ViewHolder> {

    private static final String TAG = "OrderDialogAdapter";
    private final Context mContext;
    private final ArrayList<StoreListBean> mData;
    private int mSelectedPos = 0;
    private OnItemClickListener onRecyclerViewItemClickListener;
    public OrderDialogAdapter(Context context, ArrayList<StoreListBean> data) {
        this.mContext = context;
        this.mData = data;
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).isCheck()){
                mSelectedPos = i;
            }
        }
    }

    private int selectIndex = -1;
    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_dialog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mCangkuCb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return holder.itemView.onTouchEvent(event);
            }
        });
        holder.mCangkuName.setText(mData.get(position).getCname());
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecyclerViewItemClickListener.onItemClick((Integer) view.getTag());
            }
        });
        if (selectIndex == position) {
            holder.mCangkuCb.setChecked(true);
        } else {
            holder.mCangkuCb.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return (mData == null) ? 0 : mData.size();
    }

    public StoreListBean getItem(int position){
        return mData.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onRecyclerViewItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cangku_name)
        TextView mCangkuName;
        @BindView(R.id.cangku_cb)
        CheckBox mCangkuCb;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
