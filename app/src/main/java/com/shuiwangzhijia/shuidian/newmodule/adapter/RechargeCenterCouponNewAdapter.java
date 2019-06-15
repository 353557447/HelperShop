package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.RechargeCenterInfoBean;
import com.shuiwangzhijia.shuidian.bean.RechargeCouponOrderBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.activity.RechargeCouponPayActivity;
import com.shuiwangzhijia.shuidian.utils.CalculateUtils;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUtils;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RechargeCenterCouponNewAdapter extends RecyclerView.Adapter<RechargeCenterCouponNewAdapter.ViewHolder> {
    private final Context mContext;
    private OnCouponItemClickListener mOnItemClickListener;
    private List<RechargeCenterInfoBean.DataBean.ListBean.RechargeBean> data;
    private final SimpleDateFormat mSimpleDateFormat;


    public RechargeCenterCouponNewAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recharge_center_coupon_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final RechargeCenterInfoBean.DataBean.ListBean.RechargeBean rechargeBean = data.get(position);
        holder.mWaterFactoryName.setText(rechargeBean.getDname());
        String sailAmount = rechargeBean.getSail_amount();
        String ramount = rechargeBean.getRamount();
        double sailAmountInt = Double.parseDouble(sailAmount);
        double ramountInt = Double.parseDouble(ramount);
        double gift = CalculateUtils.sub(ramountInt,sailAmountInt);
        if (rechargeBean.getAging() == 0) {
            //永久有效
            holder.mTime.setText("永久有效");
        } else {
            //有期限
            holder.mTime.setText(mSimpleDateFormat.format(new Date(rechargeBean.getStart_time() * 1000)) + "~"//
                    + mSimpleDateFormat.format(new Date(rechargeBean.getEnd_time() * 1000))
            );
        }
        holder.mFaceValue.setText(sailAmount);
        String regulation = "充" + sailAmountInt + "赠" + gift;
        holder.mRegulation.setText(regulation);
        rechargeBean.setRegulation(regulation);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onCouponItemClick(position);
            }
        });
        holder.mRechargeLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRecharge(rechargeBean.getR_id(),rechargeBean.getSail_amount());
            }
        });
    }

    public RechargeCenterInfoBean.DataBean.ListBean.RechargeBean getItem(int position) {
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

    public void setOnItemClickListener(OnCouponItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setData(List<RechargeCenterInfoBean.DataBean.ListBean.RechargeBean> result) {
        data = result;
        notifyDataSetChanged();
    }

    public void refreshData(List<RechargeCenterInfoBean.DataBean.ListBean.RechargeBean> result) {
        data.clear();
        data.addAll(result);
        notifyDataSetChanged();
    }

    public interface OnCouponItemClickListener {
        void onCouponItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.water_factory_name)
        TextView mWaterFactoryName;
        @BindView(R.id.face_value)
        TextView mFaceValue;
        @BindView(R.id.regulation)
        TextView mRegulation;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.root_rl)
        RelativeLayout mRootRl;
        @BindView(R.id.recharge_ll)
        LinearLayout mRechargeLl;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void handleRecharge(int selectedCouponRid,String selectedCouponPrice){
        RetrofitUtils.getInstances().create().rechargeConfirmOrder(CommonUtils.getToken(), selectedCouponRid, selectedCouponPrice).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = new Gson().toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int scode = object.getInt("scode");
                    if (scode == 200) {
                        RechargeCouponOrderBean rechargeCouponOrderBean = new Gson().fromJson(datas, RechargeCouponOrderBean.class);
                        RechargeCouponOrderBean.DataBean data = rechargeCouponOrderBean.getData();
                        Bundle bundle = new Bundle();
                        bundle.putString("orderNum", data.getOrder_no());
                        bundle.putLong("createTime", data.getOrder_time());
                        bundle.putString("price", data.getPrice());
                        Intent intent=new Intent(mContext, RechargeCouponPayActivity.class);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);

                    } else {
                        ToastUtils.show("充值券下单失败");
                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                } finally {

                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                KLog.e(t.getMessage());
            }
        });
    }


}
