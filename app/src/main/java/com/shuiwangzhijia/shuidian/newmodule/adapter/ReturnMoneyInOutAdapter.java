package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.ReturnMoneyInOutBean;

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
public class ReturnMoneyInOutAdapter extends RecyclerView.Adapter<ReturnMoneyInOutAdapter.ViewHolder> {
    private final Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private List<ReturnMoneyInOutBean.DataBean> data;
    private SimpleDateFormat mSdf;


    public ReturnMoneyInOutAdapter(Context context) {
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

       // 0 提现 1单笔返利，2月度返利，3季度返利，4年度返利，5其他返利 6转出至余额
        ReturnMoneyInOutBean.DataBean listBean = data.get(position);
        int type = listBean.getType();
        /*switch (type) {
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
        }*/

        switch (type){
            case 0:
                holder.mType.setText("提现");
                holder.mIncomeOrExpensesMoney.setText("-￥"+listBean.getAmount());
                break;
            case 1:
                holder.mType.setText("单笔返利");
                holder.mIncomeOrExpensesMoney.setText("+￥"+listBean.getAmount());
                break;
            case 2:
                holder.mType.setText("月度返利");
                holder.mIncomeOrExpensesMoney.setText("+￥"+listBean.getAmount());
                break;
            case 3:
                holder.mType.setText("季度返利");
                holder.mIncomeOrExpensesMoney.setText("+￥"+listBean.getAmount());
                break;
            case 4:
                holder.mType.setText("年度返利");
                holder.mIncomeOrExpensesMoney.setText("+￥"+listBean.getAmount());
                break;
            case 5:
                holder.mType.setText("其他返利");
                holder.mIncomeOrExpensesMoney.setText("+￥"+listBean.getAmount());
                break;
            case 6:
                holder.mType.setText("转出至余额");
                holder.mIncomeOrExpensesMoney.setText("-￥"+listBean.getAmount());
                break;
        }
        holder.mTime.setText(mSdf.format(new Date(listBean.getCreate_time()*1000)));
        holder.mRemainMoney.setText("余额：￥"+listBean.getRebate_amount());

    }

    public ReturnMoneyInOutBean.DataBean getItem(int position) {
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

    public void setData(List<ReturnMoneyInOutBean.DataBean> result) {
        data = result;
    }

    public void addData(List<ReturnMoneyInOutBean.DataBean> result) {
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
