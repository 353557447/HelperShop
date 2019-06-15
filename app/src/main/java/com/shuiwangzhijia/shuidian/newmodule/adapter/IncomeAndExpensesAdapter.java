package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.BalanceInfoBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 地址适配器
 * created by wangsuli on 2018/8/17.
 */
public class IncomeAndExpensesAdapter extends RecyclerView.Adapter<IncomeAndExpensesAdapter.ViewHolder> {
    private final Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private List<BalanceInfoBean.DataBean.ListBean> data;
    private SimpleDateFormat mSdf;


    public IncomeAndExpensesAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
        mSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_income_and_expenses, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //1充值+，2消费支出-，3退款+，4返利金转入+
        BalanceInfoBean.DataBean.ListBean listBean = data.get(position);
        int type = listBean.getType();
        switch (type) {
            case 1:
                holder.mType.setText("充值");
                holder.mIncomeOrExpensesMoney.setText("+￥"+listBean.getAmount());
                break;
            case 2:
                holder.mType.setText("消费支出");
                holder.mIncomeOrExpensesMoney.setText("-￥"+listBean.getAmount());
                break;
            case 3:
                holder.mType.setText("退款");
                holder.mIncomeOrExpensesMoney.setText("+￥"+listBean.getAmount());
                break;
            case 4:
                holder.mType.setText("返利金转入");
                holder.mIncomeOrExpensesMoney.setText("+￥"+listBean.getAmount());
                break;
        }
        holder.mTime.setText(mSdf.format(new Date(listBean.getAdd_time()*1000)));
        holder.mRemainMoney.setText("余额：￥"+listBean.getBalance());

    }

    public BalanceInfoBean.DataBean.ListBean getItem(int position) {
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setData(List<BalanceInfoBean.DataBean.ListBean> result) {
        data = result;
        notifyDataSetChanged();
    }

    public void addData(List<BalanceInfoBean.DataBean.ListBean> result) {
        data.addAll(result);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.type)
        TextView mType;
        @BindView(R.id.income_or_expenses_money)
        TextView mIncomeOrExpensesMoney;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.remain_money)
        TextView mRemainMoney;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
