package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.FlashSaleBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.activity.FlashSaleAddActivity;
import com.shuiwangzhijia.shuidian.utils.ToastUtils;
import com.shuiwangzhijia.shuidian.view.RxDialogSureCancel;
import com.google.gson.Gson;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FlashSaleAdapter extends RecyclerView.Adapter<FlashSaleAdapter.ViewHolder> {
    private Context mContext;
    private List<FlashSaleBean.DataBean> data;
    private final SimpleDateFormat mDateSdf;
    private final SimpleDateFormat mStartEndSdf;

    public FlashSaleAdapter(Context context) {
        this.mContext = context;
        mDateSdf = new SimpleDateFormat("yyyy-MM-dd");
        mStartEndSdf = new SimpleDateFormat("HH:mm");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_flash_sale, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final FlashSaleBean.DataBean dataBean = data.get(position);
        final int flag = dataBean.getFlag();
        final int isExit = dataBean.getIs_exit();
        long dateTime = dataBean.getDate_time();
        String startTime = dataBean.getStart_time();
        String endTime = dataBean.getEnd_time();
        holder.mSession.setText(mDateSdf.format(new Date(dateTime*1000))+" "+startTime+"~"//
        +endTime+"场"
        );
        //flag	string	1进行中 2未开始 3已结束
        //is_exit	int	是否退出 0否 1是
        if(isExit==0){
            if(flag==1){
                holder.mSessionState.setText("进行中");
                holder.mSessionState.setTextColor(Color.parseColor("#FE0233"));
                holder.mQuitAct.setVisibility(View.VISIBLE);
            }else if(flag==2){
                holder.mSessionState.setText("未开始");
                holder.mSessionState.setTextColor(Color.parseColor("#FF9000"));
                holder.mQuitAct.setVisibility(View.VISIBLE);
            }else if(flag==3){
                holder.mSessionState.setText("已结束");
                holder.mSessionState.setTextColor(Color.parseColor("#B3B2B1"));
                holder.mQuitAct.setVisibility(View.GONE);
            }
        }else{
            holder.mQuitAct.setVisibility(View.GONE);
            holder.mSessionState.setText("已退出");
            holder.mSessionState.setTextColor(Color.parseColor("#B3B2B1"));
        }
        List<FlashSaleBean.DataBean.GoodsBean> goods = dataBean.getGoods();
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.mGoodsRv.setLayoutManager(manager);
        FlashSaleGoodsImgAdapter simpleImgAdapter = new FlashSaleGoodsImgAdapter(mContext);
        simpleImgAdapter.setData(goods);
        holder.mGoodsRv.setAdapter(simpleImgAdapter);
        holder.mQuitAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RxDialogSureCancel rxDialogSureCancel=new RxDialogSureCancel(mContext);
                rxDialogSureCancel.getLogoView().setVisibility(View.GONE);
                rxDialogSureCancel.getContentView().setText("确认退出么？退出后该场次将无法再参加");
                rxDialogSureCancel.setCancelListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rxDialogSureCancel.dismiss();
                    }
                });
                rxDialogSureCancel.setSureListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleQuit(position);
                        rxDialogSureCancel.dismiss();
                    }
                });
                rxDialogSureCancel.show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, FlashSaleAddActivity.class);
                Bundle bundle=new Bundle();
                if(isExit==0){
                    holder.mQuitAct.setVisibility(View.VISIBLE);
                    if(flag==1){
                        bundle.putBoolean("isEditable",true);
                    }else if(flag==2){
                        bundle.putBoolean("isEditable",true);
                    }else if(flag==3){
                        bundle.putBoolean("isEditable",false);
                    }
                }else{
                    bundle.putBoolean("isEditable",false);
                }
                bundle.putInt("seckill_id",dataBean.getSeckill_id());
                bundle.putLong("date_time",dataBean.getDate_time());
                bundle.putString("start_time",dataBean.getStart_time());
                bundle.putString("end_time",dataBean.getEnd_time());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    private void handleQuit(int position) {
        final FlashSaleBean.DataBean dataBean = data.get(position);
        RetrofitUtils.getInstances().create().exitAct(dataBean.getDate_time()+"",dataBean.getSeckill_id()).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = new Gson().toJson(response.body());
                    KLog.e(datas);
                    JSONObject object =new JSONObject(datas);
                    int code = object.getInt("code");
                    if(code==200){
                        dataBean.setIs_exit(1);
                        notifyDataSetChanged();
                        ToastUtils.show("活动退出成功");
                    }else{
                        ToastUtils.show("活动退出失败");
                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                }finally {
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                KLog.e(t.getMessage());
            }
        });
    }


    public void setData(List<FlashSaleBean.DataBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.session)
        TextView mSession;
        @BindView(R.id.session_state)
        TextView mSessionState;
        @BindView(R.id.quit_act)
        TextView mQuitAct;
        @BindView(R.id.goods_rv)
        RecyclerView mGoodsRv;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
