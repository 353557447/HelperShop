package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.SaveOweBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;



public class SaveOweAdapter extends RecyclerView.Adapter<SaveOweAdapter.ViewHolder> {
    private final ArrayList<SaveOweBean> mData;
    private final Context mContext;

    public SaveOweAdapter(ArrayList<SaveOweBean> result, Context context) {
        this.mData = result;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_save_owe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mName.setText(mData.get(position).getGname());
        int save_owe = mData.get(position).getSave_owe();
        String saveOwe = save_owe+"";
        if (save_owe < 0){
            String s = saveOwe.replace("-","");
            holder.mNumber.setText(s+"(欠)");
            setTextStyle(holder.mNumber,s+"(","欠",")");
        }else {
            String s = saveOwe.replace("+","");
            holder.mNumber.setText(s+"(存)");
        }
    }

    private void setTextStyle(TextView text, String first, String content, String second) {
        SpannableString spanString = new SpannableString(first + content + second);
        spanString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_ff6600)), first.length(), (content + first).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        spanString.setSpan(new AbsoluteSizeSpan(14, true), first.length(), (content + first).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);//字体大小
        text.setText(spanString);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.number)
        TextView mNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
