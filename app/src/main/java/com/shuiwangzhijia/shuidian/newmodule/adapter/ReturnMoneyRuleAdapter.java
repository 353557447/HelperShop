package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.RMPolicyRuleDetailsOneBean;
import com.shuiwangzhijia.shuidian.bean.RMPolicyRuleDetailsTwoBean;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReturnMoneyRuleAdapter extends RecyclerView.Adapter<ReturnMoneyRuleAdapter.ViewHolder> {
    private final Context mContext;
    private List<Object> data;
    private int mRWay;
    private int mRule;
    private int mRbasis;
    private String mOrderReturnStr;


    public ReturnMoneyRuleAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_return_money_rule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if(mRbasis==1){
            RMPolicyRuleDetailsOneBean rmPolicyRuleDetailsBean = (RMPolicyRuleDetailsOneBean) data.get(position);
            String gname = rmPolicyRuleDetailsBean.getRule().get(0).getGname();
            holder.mName.setText(gname);
            List<RMPolicyRuleDetailsOneBean.RuleBean> rule = rmPolicyRuleDetailsBean.getRule();
            StringBuilder sb = new StringBuilder();
            //返利方式 1现金返利 2水票返利
            //返利规则 1倍数返利 2范围返利
            //每满200桶，返xxxx水票x张
            for (int i = 0; i < rule.size(); i++) {
                RMPolicyRuleDetailsOneBean.RuleBean ruleBean = rule.get(i);
                if (mRWay == 1) {
                    //每满100桶，返0.10元
                    // 1现金返利
                    sb.append("每满" + ruleBean.getFull() + "桶,返" + ruleBean.getAmount() + "元");
                } else {
                    //满200桶，返xxxx水票x张
                    //2水票返利
                   // sb.append("每满" + ruleBean.getFull() + "桶,返" + ruleBean.getS_name() + "水票" + ruleBean.getSnum() + "张");
                    sb.append("每满" + ruleBean.getFull() + "桶,返" + ruleBean.getS_name() + ruleBean.getSnum() + "张");
                }
                if (i != rule.size() - 1)
                    sb.append("\n");
            }
            String str = sb.toString();
            if(mRule==2){
                str = str.replace("每满", "满");
            }
            holder.mRuleContent.setText(str);
        }else{
            holder.mName.setText("订单总下单数量");
            holder.mRuleContent.setText(mOrderReturnStr);
        }

    }

    @Override
    public int getItemCount() {
        if(mRbasis==2){
            return (data == null) ? 0 : 1;
        }
        return (data == null) ? 0 : data.size();
    }

    public void setData(List<Object> result) {
        data = result;
    }

    public void setConfig(int rWay, int rule,int rbasis) {
        this.mRWay = rWay;
        this.mRule = rule;
        this.mRbasis=rbasis;
        if(rbasis==2){
            //订单返利
            StringBuilder sb = new StringBuilder();
            KLog.e("订单返利："+data.size());
            //单独处理数据
            for (int i = 0; i < data.size(); i++) {
                RMPolicyRuleDetailsTwoBean rMPolicyRuleDetailsTwoBean = (RMPolicyRuleDetailsTwoBean) data.get(i);
                if (mRWay == 1) {
                    //每满100桶，返0.10元
                    // 1现金返利
                    sb.append("每满" + rMPolicyRuleDetailsTwoBean.getFull() + "桶,返" + rMPolicyRuleDetailsTwoBean.getAmount() + "元");
                } else {
                    //满200桶，返xxxx水票x张
                    //2水票返利
                   // sb.append("每满" + rMPolicyRuleDetailsTwoBean.getFull() + "桶,返" + rMPolicyRuleDetailsTwoBean.getS_name() + "水票" + rMPolicyRuleDetailsTwoBean.getSnum() + "张");
                    sb.append("每满" + rMPolicyRuleDetailsTwoBean.getFull() + "桶,返" + rMPolicyRuleDetailsTwoBean.getS_name() + rMPolicyRuleDetailsTwoBean.getSnum() + "张");
                }
                if (i != data.size() - 1)
                    sb.append("\n");
            }
            mOrderReturnStr = sb.toString();
            if(mRule==2){
                mOrderReturnStr = mOrderReturnStr.replace("每满", "满");
            }

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.rule_content)
        TextView mRuleContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
