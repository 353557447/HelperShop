package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shuiwangzhijia.shuidian.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 地址适配器
 * created by wangsuli on 2018/8/17.
 */
public class ReturnMoneyProgressTypeTwoAdapter extends RecyclerView.Adapter<ReturnMoneyProgressTypeTwoAdapter.ViewHolder> {
    private final Context mContext;
    private List<Integer> data;


    public ReturnMoneyProgressTypeTwoAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_return_money_progress_type_two_son, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

    }

    public Integer getItem(int position) {
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

    public void setData(List<Integer> result) {
        data = result;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
