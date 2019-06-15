package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.MultipleRMBean;
import com.shuiwangzhijia.shuidian.bean.RangeRMBean;
import com.shuiwangzhijia.shuidian.bean.ReturnMoneyRtypeElseBean;
import com.shuiwangzhijia.shuidian.bean.ReturnMoneyRtypeOneBean;
import com.shuiwangzhijia.shuidian.bean.RuleProgressBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUtils;
import com.google.gson.Gson;
import com.socks.library.KLog;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.json.JSONArray;
import org.json.JSONException;
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

public class ReturnMoneyRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private int type;//1.单笔  2.其他可展开
    private List<ReturnMoneyRtypeOneBean.DataBean> rtypeOneList;
    private List<ReturnMoneyRtypeElseBean> rtypeElseList;
    private final SimpleDateFormat mSimpleDateFormat;


    public ReturnMoneyRecordAdapter(Context context, int type) {
        mContext = context;
        this.type = type;
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (type == 1) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_return_money_details, parent, false);
            return new TypeOneViewHolder(view);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_return_money_expandle_details, parent, false);
            return new TypeTwoViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (type == 1) {
            ReturnMoneyRtypeOneBean.DataBean dataBean = rtypeOneList.get(position);
            ((TypeOneViewHolder) holder).mOrderNum.setText("订单编号：" + dataBean.getRebate_no());
            ((TypeOneViewHolder) holder).mTime.setText(mSimpleDateFormat.format(new Date(dataBean.getApply_time() * 1000)));
            int rWay = dataBean.getR_way();
            if (rWay == 1) {
              /*  double totalReturn = 0;
                List<ReturnMoneyRtypeOneBean.DataBean.GoodsBean> goods = dataBean.getGoods();
                for (ReturnMoneyRtypeOneBean.DataBean.GoodsBean goodsBean :
                        goods) {
                    String amount = goodsBean.getAmount();
                    totalReturn += Double.parseDouble(amount);
                }*/
                //现金返利
              //  ((TypeOneViewHolder) holder).mReturnCounts.setText("返利金额：￥" + MyUtils.formatPrice(totalReturn));
                ((TypeOneViewHolder) holder).mReturnCounts.setText("返利金额：￥" + dataBean.getTotal_amount());
            } else {
                //水票返利
             /*   int totalReturn = 0;
                List<ReturnMoneyRtypeOneBean.DataBean.GoodsBean> goods = dataBean.getGoods();
                for (ReturnMoneyRtypeOneBean.DataBean.GoodsBean goodsBean :
                        goods) {
                    int num = goodsBean.getSnum();
                    totalReturn += num;
                }*/
                ((TypeOneViewHolder) holder).mReturnCounts.setText("返利水票：" + dataBean.getS_count());
            }
        } else {
            ReturnMoneyRtypeElseBean returnMoneyRtypeElseBean = rtypeElseList.get(position);
            ((TypeTwoViewHolder) holder).mTime.setText(returnMoneyRtypeElseBean.getRebate_date());
            //1申请返利,2申请中,3已返利
            int status = returnMoneyRtypeElseBean.getStatus();
            switch (status) {
                case 1:
                    ((TypeTwoViewHolder) holder).mApplyRm.setVisibility(View.VISIBLE);
                    ((TypeTwoViewHolder) holder).mApplyRm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            handleApply(position);
                        }
                    });
                    break;
                case 2:
                    ((TypeTwoViewHolder) holder).mApplyRm.setVisibility(View.GONE);
                    ((TypeTwoViewHolder) holder).mStatus.setText("申请中");
                    break;
                case 3:
                    ((TypeTwoViewHolder) holder).mApplyRm.setVisibility(View.GONE);
                    ((TypeTwoViewHolder) holder).mStatus.setText("已返利");
                    break;
            }
            int rWay = returnMoneyRtypeElseBean.getR_way();
            if (rWay == 1) {
                //现金
                // 返利金额：￥123.89
                ((TypeTwoViewHolder) holder).mRmCounts.setText("返利金额：￥" + returnMoneyRtypeElseBean.getTotal_amount());
            } else {
                //水票
                //返利水票：12张
                ((TypeTwoViewHolder) holder).mRmCounts.setText("返利水票：" + returnMoneyRtypeElseBean.getS_count() + "张");
            }
            ((TypeTwoViewHolder) holder).mRv.setLayoutManager(new LinearLayoutManager(mContext));
            // int mRule,int mRbasis,int mRWay,RecyclerView rv,JSONArray goods
            setNewProgressUiType(returnMoneyRtypeElseBean.getRule(), returnMoneyRtypeElseBean.getRbasis()//
                    , rWay, ((TypeTwoViewHolder) holder).mRv, returnMoneyRtypeElseBean.getData());
            ((TypeTwoViewHolder) holder).mExpandToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean expanded = ((TypeTwoViewHolder) holder).mExpandableLayout.isExpanded();
                    if (expanded) {
                        ((TypeTwoViewHolder) holder).mExpandableLayout.collapse();
                        createRotateAnimator(((TypeTwoViewHolder) holder).mArrow, 180f, 0f).start();
                    } else {
                        ((TypeTwoViewHolder) holder).mExpandableLayout.expand();
                        createRotateAnimator(((TypeTwoViewHolder) holder).mArrow, 0f, 180f).start();
                    }
                }
            });

        }
    }

    private void handleApply(int position) {
        final ReturnMoneyRtypeElseBean elseBean = rtypeElseList.get(position);
        RetrofitUtils.getInstances().create().returnMoneyRecordApplyRebate(elseBean.getRebate_no()).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = new Gson().toJson(response.body());
                    KLog.e(datas);
                    JSONObject object =new JSONObject(datas);
                    int code = object.getInt("code");
                    if(code==200){
                        elseBean.setStatus(2);
                        notifyDataSetChanged();
                        ToastUtils.show(object.getString("msg"));
                    }else{
                        ToastUtils.show(object.getString("msg"));
                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                }finally {

                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if (type == 1)
            return (rtypeOneList == null) ? 0 : rtypeOneList.size();
        else
            return (rtypeElseList == null) ? 0 : rtypeElseList.size();
    }

    public void setDataRtypeOne(List<ReturnMoneyRtypeOneBean.DataBean> list) {
        rtypeOneList = list;
    }

    public void addDataRtypeOne(List<ReturnMoneyRtypeOneBean.DataBean> list) {
        rtypeOneList.addAll(list);
        notifyDataSetChanged();
    }

    public void setDataRtypeElse(List<ReturnMoneyRtypeElseBean> list) {
        rtypeElseList = list;
    }

    public void addDataRtypeElse(List<ReturnMoneyRtypeElseBean> list) {
        rtypeElseList.addAll(list);
        notifyDataSetChanged();
    }

    public class TypeOneViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.order_num)
        TextView mOrderNum;
        @BindView(R.id.return_counts)
        TextView mReturnCounts;
        @BindView(R.id.time)
        TextView mTime;

        public TypeOneViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class TypeTwoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.status)
        TextView mStatus;
        @BindView(R.id.apply_rm)
        TextView mApplyRm;
        @BindView(R.id.rm_counts)
        TextView mRmCounts;
        @BindView(R.id.arrow)
        ImageView mArrow;
        @BindView(R.id.expand_toggle)
        LinearLayout mExpandToggle;
        @BindView(R.id.rv)
        RecyclerView mRv;
        @BindView(R.id.expandable_layout)
        ExpandableLayout mExpandableLayout;

        public TypeTwoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        return animator;
    }


    private void setNewProgressUiType(int mRule, int mRbasis, int mRWay, RecyclerView rv, JSONArray goods) {
        if (mRule == 1) {
            //倍数返利  环形进度条
            //1按商品 2按订单  rBasis; 判断如果是订单返利则只有一个进度条目 其他是多个进度条目
            List<MultipleRMBean> multipleRMList = new ArrayList<>();
            if (mRbasis == 1) {
                // 1按商品
                try {
                    for (int i = 0; i < goods.length(); i++) {
                        MultipleRMBean multipleRMBean = new MultipleRMBean();
                        JSONObject jsonObject = goods.getJSONObject(i);
                        multipleRMBean.setrWay(mRWay);
                        multipleRMBean.setGoodsName(jsonObject.getString("gname"));
                        multipleRMBean.setCompletedCounts(jsonObject.getInt("gnum"));
                        int ratio = Integer.parseInt(jsonObject.getString("ratio").replace("%", ""));
                        multipleRMBean.setProgress(ratio);
                        if (mRWay == 1) {
                            //按现金
                            multipleRMBean.setMoneyCondition(jsonObject.getInt("full"));
                            multipleRMBean.setMoney(jsonObject.getString("amount"));
                        } else {
                            //按水票
                            multipleRMBean.setWaterCouponCondition(jsonObject.getInt("full"));
                            multipleRMBean.setWaterCouponName(jsonObject.getString("s_name"));
                            multipleRMBean.setWaterCouponCounts(jsonObject.getInt("snum"));
                        }
                        multipleRMList.add(multipleRMBean);
                    }
                } catch (JSONException e) {
                    KLog.e(e.getMessage());
                }

            } else {
                // 2按订单
                try {
                    JSONObject goodsObject = goods.getJSONObject(0);
                    MultipleRMBean multipleRMBean = new MultipleRMBean();
                    multipleRMBean.setrWay(mRWay);
                    multipleRMBean.setGoodsName("下单完成数量");
                    multipleRMBean.setCompletedCounts(goodsObject.getInt("gnum"));
                    int ratio = Integer.parseInt(goodsObject.getString("ratio").replace("%", ""));
                    multipleRMBean.setProgress(ratio);
                    if (mRWay == 1) {
                        //按现金
                        multipleRMBean.setMoneyCondition(goodsObject.getInt("full"));
                        multipleRMBean.setMoney(goodsObject.getString("amount"));
                    } else {
                        //按水票
                        multipleRMBean.setWaterCouponCondition(goodsObject.getInt("full"));
                        multipleRMBean.setWaterCouponName(goodsObject.getString("s_name"));
                        multipleRMBean.setWaterCouponCounts(goodsObject.getInt("snum"));
                    }
                    multipleRMList.add(multipleRMBean);
                } catch (JSONException e) {
                    KLog.e(e.getMessage());
                }
            }
            ReturnMoneyRecordProgressAdapter returnMoneyRecordProgressAdapter = new ReturnMoneyRecordProgressAdapter(mContext, 2);
            returnMoneyRecordProgressAdapter.setMultipleRMData(multipleRMList);
            rv.setAdapter(returnMoneyRecordProgressAdapter);
        } else {
            //范围返利  普通进度
            //1按商品 2按订单  rBasis; 判断如果是订单返利则只有一个进度条目 其他是多个进度条目
            List<RangeRMBean> rangeRMList = new ArrayList<>();
            if (mRbasis == 1) {
                // 1按商品
                try {
                    for (int i = 0; i < goods.length(); i++) {
                        JSONObject jsonObject = goods.getJSONObject(i);
                        RangeRMBean rangeRMBean = new RangeRMBean();
                        rangeRMBean.setrWay(mRWay);
                        rangeRMBean.setGoodsName(jsonObject.getString("gname"));
                        rangeRMBean.setCompletedCounts(jsonObject.getInt("gnum"));
                        JSONArray ruleDetail = jsonObject.getJSONArray("rule_fw");
                        List<RuleProgressBean> ruleProgressList = new ArrayList<>();
                        for (int j = 0; j < ruleDetail.length(); j++) {
                            RuleProgressBean ruleProgressBean = new RuleProgressBean();
                            ruleProgressBean.setrWay(mRWay);
                            JSONObject jsonObjectSon = ruleDetail.getJSONObject(j);
                            if (mRWay == 1) {
                                //现金
                                ruleProgressBean.setMoneyCondition(jsonObjectSon.getInt("full"));
                                ruleProgressBean.setMoney(jsonObjectSon.getString("amount"));
                            } else {
                                //水票
                                ruleProgressBean.setWaterCouponCondition(jsonObjectSon.getInt("full"));
                                ruleProgressBean.setWaterCouponName(jsonObjectSon.getString("s_name"));
                                ruleProgressBean.setWaterCouponCounts(jsonObjectSon.getInt("snum"));
                            }
                            ruleProgressList.add(ruleProgressBean);
                        }
                        rangeRMBean.setList(ruleProgressList);
                        rangeRMList.add(rangeRMBean);
                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                }
            } else {
                // 2按订单
                try {
                    JSONObject goodsObject = goods.getJSONObject(0);
                    RangeRMBean rangeRMBean = new RangeRMBean();
                    rangeRMBean.setrWay(mRWay);
                    rangeRMBean.setGoodsName("下单完成数量");
                    rangeRMBean.setCompletedCounts(goodsObject.getInt("gnum"));
                    JSONArray ruleDetail = goodsObject.getJSONArray("rule_fw");
                    List<RuleProgressBean> ruleProgressList = new ArrayList<>();
                    for (int i = 0; i < ruleDetail.length(); i++) {
                        RuleProgressBean ruleProgressBean = new RuleProgressBean();
                        ruleProgressBean.setrWay(mRWay);
                        JSONObject jsonObjectSon = ruleDetail.getJSONObject(i);
                        if (mRWay == 1) {
                            //现金
                            ruleProgressBean.setMoneyCondition(jsonObjectSon.getInt("full"));
                            ruleProgressBean.setMoney(jsonObjectSon.getString("amount"));
                        } else {
                            //水票
                            ruleProgressBean.setWaterCouponCondition(jsonObjectSon.getInt("full"));
                            ruleProgressBean.setWaterCouponName(jsonObjectSon.getString("s_name"));
                            ruleProgressBean.setWaterCouponCounts(jsonObjectSon.getInt("snum"));
                        }
                        ruleProgressList.add(ruleProgressBean);
                    }
                    rangeRMBean.setList(ruleProgressList);
                    rangeRMList.add(rangeRMBean);
                } catch (JSONException e) {
                    KLog.e(e.getMessage());
                }
            }

            //上述处理完数据传至adapter

            ReturnMoneyRecordProgressAdapter returnMoneyRecordProgressAdapter = new ReturnMoneyRecordProgressAdapter(mContext, 1);
            returnMoneyRecordProgressAdapter.setRangeRMData(rangeRMList);
            rv.setAdapter(returnMoneyRecordProgressAdapter);
        }
    }
}
