package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.MultipleRMBean;
import com.shuiwangzhijia.shuidian.bean.RMPolicyRuleDetailsOneBean;
import com.shuiwangzhijia.shuidian.bean.RMPolicyRuleDetailsTwoBean;
import com.shuiwangzhijia.shuidian.bean.RangeRMBean;
import com.shuiwangzhijia.shuidian.bean.ReturnMoneyTemBean;
import com.shuiwangzhijia.shuidian.bean.RuleProgressBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.ReturnMoneyProgressAdapter;
import com.shuiwangzhijia.shuidian.newmodule.adapter.ReturnMoneyRuleAdapter;
import com.shuiwangzhijia.shuidian.newmodule.adapter.ReturnMoneyTemplateAdapter;
import com.shuiwangzhijia.shuidian.utils.DateUtils;
import com.shuiwangzhijia.shuidian.view.MyScrollView;
import com.shuiwangzhijia.shuidian.view.SpacesItemDecoration;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_return_money_policy)
public class ReturnMoneyPolicyActivity extends BaseActivity implements ReturnMoneyTemplateAdapter.OnRecyclerViewItemClickListener {
    @BindView(R.id.return_money_template_rv)
    RecyclerView mReturnMoneyTemplateRv;
    @BindView(R.id.back_return)
    RelativeLayout mBackReturn;
    @BindView(R.id.return_money_progress_rv)
    RecyclerView mReturnMoneyProgressRv;
    @BindView(R.id.return_money_policy_rv)
    RecyclerView mReturnMoneyPolicyRv;
    @BindView(R.id.check_return_money_record)
    TextView mCheckReturnMoneyRecord;
    @BindView(R.id.scroll_view)
    MyScrollView mScrollView;
    @BindView(R.id.title_bar_rl)
    RelativeLayout mTitleBarRl;
    @BindView(R.id.status)
    TextView mStatus;
    @BindView(R.id.has_return_money)
    TextView mHasReturnMoney;
    @BindView(R.id.has_return_money_unit)
    TextView mHasReturnMoneyUnit;
    @BindView(R.id.pending_apply)
    TextView mPendingApply;
    @BindView(R.id.pending_apply_unit)
    TextView mPendingApplyUnit;
    @BindView(R.id.applying)
    TextView mApplying;
    @BindView(R.id.applying_unit)
    TextView mApplyingUnit;
    @BindView(R.id.water_factory_name)
    TextView mWaterFactoryName;
    @BindView(R.id.has_return_ll)
    LinearLayout mHasReturnLl;
    @BindView(R.id.pending_apply_ll)
    LinearLayout mPendingApplyLl;
    @BindView(R.id.applying_ll)
    LinearLayout mApplyingLl;
    @BindView(R.id.return_money_progress_ll)
    LinearLayout mReturnMoneyProgressLl;
    @BindView(R.id.return_money_cycle)
    TextView mReturnMoneyCycle;
    @BindView(R.id.return_money_cycle_ll)
    LinearLayout mReturnMoneyCycleLl;
    @BindView(R.id.tem_return_money_details_ll)
    LinearLayout mTemReturnMoneyDetailsLl;
    @BindView(R.id.period_of_validity)
    TextView mPeriodOfValidity;
    @BindView(R.id.rule_r_way)
    TextView mRuleRWay;
    @BindView(R.id.rule_r_basis)
    TextView mRuleRBasis;
    @BindView(R.id.rule_r_rule)
    TextView mRuleRRule;
    @BindView(R.id.anticipated_return)
    TextView mAnticipatedReturn;
    @BindView(R.id.fanlizhengce)
    TextView fanlizhengce;
    @BindView(R.id.root_ll)
    LinearLayout mRootLl;
    @BindView(R.id.back_iv_src)
    ImageView mBackIvSrc;
    private ReturnMoneyTemplateAdapter mReturnMoneyTemplateAdapter;
    private List<ReturnMoneyTemBean.DataBean> mReturnMoneyTemList;
    private ReturnMoneyProgressAdapter mReturnMoneyProgressAdapter;
    private List<Object> mReturnMoneyRuleList;
    private ReturnMoneyRuleAdapter mReturnMoneyRuleAdapter;
    private int mDid;
    private int mSelectedPosition;
    private String mWaterFactoryNameStr;
    private int mRType;
    private int mRWay;
    private int mRule;
    private int mRbasis;
    private long mStartTime;
    private long mEndTime;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        mDid = bundle.getInt("did");
        mWaterFactoryNameStr = bundle.getString("waterFactoryName");
        mWaterFactoryName.setText(mWaterFactoryNameStr);
        setBaseBarHide();
        initReturnMoneyTemplateRv();
        initReturnMoneyProgressRv();
        initReturnMoneyRuleRv();
    }

    private void initReturnMoneyTemplateRv() {
        mReturnMoneyTemList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mReturnMoneyTemplateRv.setLayoutManager(manager);
        mReturnMoneyTemplateRv.addItemDecoration(new SpacesItemDecoration(20, this));
        mReturnMoneyTemplateAdapter = new ReturnMoneyTemplateAdapter(this);
        mReturnMoneyTemplateAdapter.setData(mReturnMoneyTemList);
        mReturnMoneyTemplateAdapter.setOnItemClickListener(this);
        mReturnMoneyTemplateRv.setAdapter(mReturnMoneyTemplateAdapter);
    }

    private void initReturnMoneyProgressRv() {
        mReturnMoneyProgressRv.setLayoutManager(new LinearLayoutManager(this));
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(20, this);
        mReturnMoneyProgressRv.addItemDecoration(spacesItemDecoration);
    }


    private void initReturnMoneyRuleRv() {
        mReturnMoneyPolicyRv.setLayoutManager(new LinearLayoutManager(this));
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(30, this);
        mReturnMoneyPolicyRv.addItemDecoration(spacesItemDecoration);
        mReturnMoneyRuleAdapter = new ReturnMoneyRuleAdapter(this);
        mReturnMoneyRuleList = new ArrayList<>();
        mReturnMoneyRuleAdapter.setData(mReturnMoneyRuleList);
        mReturnMoneyPolicyRv.setAdapter(mReturnMoneyRuleAdapter);
    }

    @Override
    protected void initData() {
        getTemplateList();
    }

    private void getTemplateList() {
        RetrofitUtils.getInstances().create().getReturnMoneyTempList(mToken, mDid).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        ReturnMoneyTemBean returnMoneyTemBean = mGson.fromJson(datas, ReturnMoneyTemBean.class);
                        List<ReturnMoneyTemBean.DataBean> data = returnMoneyTemBean.getData();
                        mReturnMoneyTemList.addAll(data);
                        ReturnMoneyTemBean.DataBean bean = mReturnMoneyTemList.get(0);
                        bean.setChecked(true);
                        //供模版返利政策控制视图用
                        mRType = bean.getRtype();
                        mRWay = bean.getR_way();
                        mReturnMoneyTemplateAdapter.notifyDataSetChanged();
                        setReturnMoneyTemData(0);
                        getTemplatePolicy(0);
                    } else {
                        mRootLl.setVisibility(View.GONE);
                        mBackIvSrc.setImageResource(R.drawable.black_back);
                        mWaterFactoryName.setTextColor(Color.parseColor("#212121"));
                        fanlizhengce.setTextColor(Color.parseColor("#212121"));
                        setCentreViewShow(R.drawable.no_order_data_icon,"暂无返利政策数据~");
                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                } finally {

                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                showErrorToast();
                KLog.e(t.getMessage());
            }
        });
    }

    private void setReturnMoneyTemData(int position) {
        ReturnMoneyTemBean.DataBean dataBean = mReturnMoneyTemList.get(position);
        // isfailure		1 已失效 2生效中
        if (dataBean.getIsfailure() == 1) {
            mStatus.setText("已失效");
            mReturnMoneyProgressLl.setVisibility(View.GONE);
        } else {
            mStatus.setText("生效中");
            mReturnMoneyProgressLl.setVisibility(View.VISIBLE);
        }
        //already_rebate	已返利
        //not_apply_rebate	待申请
        //not_deal_rebate	申请中
        mHasReturnMoney.setText(dataBean.getAlready_rebate());
        mPendingApply.setText(dataBean.getNot_apply_rebate());
        mApplying.setText(dataBean.getNot_deal_rebate());
        //1现金返利 2水票返利
        if (mRWay == 1) {
            mHasReturnMoneyUnit.setText("已返利（元）");
            mPendingApplyUnit.setText("待申请（元）");
            mApplyingUnit.setText("申请中（元）");
        } else {
            mHasReturnMoneyUnit.setText("已返利（张）");
            mPendingApplyUnit.setText("待申请（张）");
            mApplyingUnit.setText("申请中（张）");
        }
    }


    private void getTemplatePolicy(int position) {
        int rId = mReturnMoneyTemList.get(position).getR_id();
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getReturnMoneyTempPolicy(mToken, rId).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                      /*  ReturnMoneyTemBean.DataBean bean = mReturnMoneyTemList.get(mSelectedPosition);
                        mRType = bean.getRtype();
                        mRWay = bean.getR_way();*/
                        JSONObject data = object.getJSONObject("data");
                        JSONObject cycle = data.getJSONObject("cycle");
                        mRule = cycle.getInt("rule");
                        mRbasis = cycle.getInt("rbasis");

                        handleRtypeUi(data);
                    } else {

                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                } finally {
                    dismissLoadingDialog();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                showErrorToast();
                KLog.e(t.getMessage());
                dismissLoadingDialog();
            }
        });
    }

    private void handleRtypeUi(JSONObject data) {
        //返利类型   1单笔返利，2月度返利，3季度返利，4年度返利，5其他返利
        setCommonData(data);
        switch (mRType) {
            case 1:
                setGone(mPendingApplyLl, mApplyingLl, mReturnMoneyProgressLl, mReturnMoneyCycleLl);
                //setSingleData(data);
                break;
            case 2:
                setVisible(mPendingApplyLl, mApplyingLl, mReturnMoneyProgressLl, mReturnMoneyCycleLl);
                setMonthCycle();
                setNewProgress(data);
                break;
            case 3:
                setVisible(mPendingApplyLl, mApplyingLl, mReturnMoneyProgressLl, mReturnMoneyCycleLl);
                setQuarterCycle();
                setNewProgress(data);
                break;
            case 4:
                setVisible(mPendingApplyLl, mApplyingLl, mReturnMoneyProgressLl, mReturnMoneyCycleLl);
                setYearCycle();
                setNewProgress(data);
                break;
            case 5:
                setVisible(mPendingApplyLl, mApplyingLl, mReturnMoneyProgressLl, mReturnMoneyCycleLl);
                setElseCycle();
                setNewProgress(data);
                break;
        }
        mTemReturnMoneyDetailsLl.setVisibility(View.VISIBLE);
    }

    private void setNewProgress(JSONObject data) {
        setNewProgressUiType(data);
    }

    private void setNewProgressUiType(JSONObject data) {
        if (mRule == 1) {
            //倍数返利  环形进度条
            //1按商品 2按订单  rBasis; 判断如果是订单返利则只有一个进度条目 其他是多个进度条目
            List<MultipleRMBean> multipleRMList = new ArrayList<>();
            if (mRbasis == 1) {
                // 1按商品
                try {
                    JSONObject newProgress = data.getJSONObject("new_progress");
                    if (mRWay == 1)
                        //预计可返：￥20.23
                        mAnticipatedReturn.setText("预计可返：￥" + newProgress.getDouble("rebate_amount"));
                    else
                        // 预计可返：20张水票
                        mAnticipatedReturn.setText("预计可返：" + newProgress.getInt("rebate_picket") + "张水票");
                    JSONArray goods = newProgress.getJSONArray("goods");
                    for (int i = 0; i < goods.length(); i++) {
                        MultipleRMBean multipleRMBean = new MultipleRMBean();
                        JSONObject jsonObject = goods.getJSONObject(i);
                        multipleRMBean.setrWay(mRWay);
                        multipleRMBean.setGoodsName(jsonObject.getString("gname"));
                        multipleRMBean.setCompletedCounts(jsonObject.getInt("sum"));
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
                    e.printStackTrace();
                }

            } else {
                // 2按订单
                try {
                    JSONObject newProgress = data.getJSONObject("new_progress");
                    if (mRWay == 1)
                        //预计可返：￥20.23
                        mAnticipatedReturn.setText("预计可返：￥" + newProgress.getDouble("rebate_amount"));
                    else
                        // 预计可返：20张水票
                        mAnticipatedReturn.setText("预计可返：" + newProgress.getInt("rebate_picket") + "张水票");
                    JSONObject goods = newProgress.getJSONObject("goods");
                    MultipleRMBean multipleRMBean = new MultipleRMBean();
                    multipleRMBean.setrWay(mRWay);
                    multipleRMBean.setGoodsName("下单完成数量");
                    multipleRMBean.setCompletedCounts(Integer.parseInt(goods.getString("sum")));
                    int ratio = Integer.parseInt(goods.getString("ratio").replace("%", ""));
                    multipleRMBean.setProgress(ratio);
                    if (mRWay == 1) {
                        //按现金
                        multipleRMBean.setMoneyCondition(goods.getInt("full"));
                        multipleRMBean.setMoney(goods.getString("amount"));
                    } else {
                        //按水票
                        multipleRMBean.setWaterCouponCondition(goods.getInt("full"));
                        multipleRMBean.setWaterCouponName(goods.getString("s_name"));
                        multipleRMBean.setWaterCouponCounts(goods.getInt("snum"));
                    }
                    multipleRMList.add(multipleRMBean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            mReturnMoneyProgressAdapter = new ReturnMoneyProgressAdapter(this, 2);
            mReturnMoneyProgressAdapter.setMultipleRMData(multipleRMList);
            mReturnMoneyProgressRv.setAdapter(mReturnMoneyProgressAdapter);
        } else {
            //范围返利  普通进度
            //1按商品 2按订单  rBasis; 判断如果是订单返利则只有一个进度条目 其他是多个进度条目
            List<RangeRMBean> rangeRMList = new ArrayList<>();
            if (mRbasis == 1) {
                // 1按商品
                try {
                    JSONObject newProgress = data.getJSONObject("new_progress");
                    if (mRWay == 1)
                        //预计可返：￥20.23
                        mAnticipatedReturn.setText("预计可返：￥" + newProgress.getDouble("rebate_amount"));
                    else
                        // 预计可返：20张水票
                        mAnticipatedReturn.setText("预计可返：" + newProgress.getInt("rebate_picket") + "张水票");
                    JSONArray goods = newProgress.getJSONArray("goods");
                    for (int i = 0; i < goods.length(); i++) {
                        JSONObject jsonObject = goods.getJSONObject(i);
                        RangeRMBean rangeRMBean = new RangeRMBean();
                        rangeRMBean.setrWay(mRWay);
                        rangeRMBean.setGoodsName(jsonObject.getString("gname"));
                        rangeRMBean.setCompletedCounts(jsonObject.getInt("sum"));
                        JSONArray ruleDetail = jsonObject.getJSONArray("rule_detail");
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
                    e.printStackTrace();
                }
            } else {
                // 2按订单
                try {
                    JSONObject newProgress = data.getJSONObject("new_progress");
                    if (mRWay == 1)
                        //预计可返：￥20.23
                        mAnticipatedReturn.setText("预计可返：￥" + newProgress.getDouble("rebate_amount"));
                    else
                        // 预计可返：20张水票
                        mAnticipatedReturn.setText("预计可返：" + newProgress.getInt("rebate_picket") + "张水票");
                    JSONObject goods = newProgress.getJSONObject("goods");
                    RangeRMBean rangeRMBean = new RangeRMBean();
                    rangeRMBean.setrWay(mRWay);
                    rangeRMBean.setGoodsName("下单完成数量");
                    rangeRMBean.setCompletedCounts(Integer.parseInt(goods.getString("sum")));
                    JSONArray ruleDetail = goods.getJSONArray("rule_detail");
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
                    e.printStackTrace();
                }
            }

            //上述处理完数据传至adapter

            mReturnMoneyProgressAdapter = new ReturnMoneyProgressAdapter(this, 1);
            mReturnMoneyProgressAdapter.setRangeRMData(rangeRMList);
            mReturnMoneyProgressRv.setAdapter(mReturnMoneyProgressAdapter);
        }
    }

    private void setElseCycle() {
        String startTimeFormated = DateUtils.getFormatDateTime(new Date(mStartTime * 1000), "yyyy年MM月");
        String endTimeFormated = DateUtils.getFormatDateTime(new Date(mEndTime * 1000 - 86400000), "yyyy年MM月");
        mReturnMoneyCycle.setText(startTimeFormated + "~" + endTimeFormated);
    }

    private void setMonthCycle() {
        //2019年3月，2019年4月，2019年5月，2019年6月， 2019年7月
        StringBuilder sb = new StringBuilder();
        String yyyy = DateUtils.getFormatDateTime(new Date(mStartTime * 1000), "yyyy");
        String startTimeFormated = DateUtils.getFormatDateTime(new Date(mStartTime * 1000), "MM");
        String endTimeFormated = DateUtils.getFormatDateTime(new Date(mEndTime * 1000 - 86400000), "MM");
        int startTimeInt = Integer.parseInt(startTimeFormated);
        int endTimeInt = Integer.parseInt(endTimeFormated);
        for (int i = startTimeInt; i <= endTimeInt; i++) {
            sb.append(yyyy + "年" + i + "月  ");
        }
        mReturnMoneyCycle.setText(sb.toString());
    }

    private void setYearCycle() {
        //2019年，2020年，2021年
        StringBuilder sb = new StringBuilder();
        String startTimeFormated = DateUtils.getFormatDateTime(new Date(mStartTime * 1000), "yyyy");
        String endTimeFormated = DateUtils.getFormatDateTime(new Date(mEndTime * 1000 - 86400000), "yyyy");
        int startTimeInt = Integer.parseInt(startTimeFormated);
        int endTimeInt = Integer.parseInt(endTimeFormated);
        for (int i = startTimeInt; i <= endTimeInt; i++) {
            sb.append(i + "年  ");
        }
        mReturnMoneyCycle.setText(sb.toString());
    }

    private void setQuarterCycle() {
        //2019年一季度（1-3月），2019年二季度（4-6月）， 2019年三季度（7-9月），2019年四季度（10-12月）
        StringBuilder sb = new StringBuilder();
        String yyyy = DateUtils.getFormatDateTime(new Date(mStartTime * 1000), "yyyy");
        String startTimeFormated = DateUtils.getFormatDateTime(new Date(mStartTime * 1000), "MM");
        String endTimeFormated = DateUtils.getFormatDateTime(new Date(mEndTime * 1000 - 86400000), "MM");
        int startTimeInt = Integer.parseInt(startTimeFormated);
        int endTimeInt = Integer.parseInt(endTimeFormated);
        boolean[] booleans = new boolean[4];
        for (int i = startTimeInt; i <= endTimeInt; i++) {
            if (1 <= i && i <= 3) {
                booleans[0] = true;
            } else if (4 <= i && i <= 6) {
                booleans[1] = true;
            } else if (7 <= i && i <= 9) {
                booleans[2] = true;
            } else if (10 <= i && i <= 12) {
                booleans[3] = true;
            }
        }
        int trueCounts = 0;
        for (int i = 0; i < booleans.length; i++) {
            if (booleans[i]) {
                if (trueCounts == 2)
                    sb.append("\n");
                switch (i) {
                    case 0:
                        sb.append(yyyy + "年一季度(1-3月)");
                        break;
                    case 1:
                        sb.append(yyyy + "年二季度(4-6月)");
                        break;
                    case 2:
                        sb.append(yyyy + "年三季度(7-9月)");
                        break;
                    case 3:
                        sb.append(yyyy + "年三季度(10-12月)");
                        break;
                }
                sb.append("  ");
                trueCounts++;
            }
        }
        mReturnMoneyCycle.setText(sb.toString());
    }

    private void setCommonData(JSONObject data) {
        //设置所有情况 共同的数据
        try {
            JSONObject cycle = data.getJSONObject("cycle");

            //设置有效期
            mStartTime = cycle.getLong("start_time");
            mEndTime = cycle.getLong("end_time");
            String startTimeFormated = DateUtils.getFormatDateTime(new Date(mStartTime * 1000), "yyyy年MM月dd日");
            //结束时间应减去一天
            String endTimeFormated = DateUtils.getFormatDateTime(new Date(mEndTime * 1000 - 86400000), "yyyy年MM月dd日");
            //android:text="有效期：2019年4月1日~2019年7月31日"
            mPeriodOfValidity.setText("有效期：" + startTimeFormated + "~" + endTimeFormated);
            //设置返利标签
            //返利方式 1现金返利 2水票返利
            if (mRWay == 1) {
                mRuleRWay.setText("现金");
            } else {
                mRuleRWay.setText("水票");
            }
            // 1按商品 2按订单
            if (mRbasis == 1) {
                mRuleRBasis.setText("按商品");
            } else {
                mRuleRBasis.setText("按订单");
            }
            //返利规则 1倍数返利 2范围返利
            if (mRule == 1) {
                mRuleRRule.setText("倍数返利");
            } else {
                mRuleRRule.setText("范围返利");
            }
            //设置返利规则列表
            JSONObject rule = data.getJSONObject("rule");
            JSONArray ruleDetail = rule.getJSONArray("rule_detail");
            if (mRbasis == 1) {
                //按商品
                Type type = new TypeToken<List<RMPolicyRuleDetailsOneBean>>() {
                }.getType();
                List<RMPolicyRuleDetailsOneBean> RMPolicyRuleDetailsBeanList = mGson.fromJson(String.valueOf(ruleDetail), type);
                mReturnMoneyRuleList.clear();
                mReturnMoneyRuleList.addAll(RMPolicyRuleDetailsBeanList);
                mReturnMoneyRuleAdapter.setConfig(mRWay, mRule, mRbasis);
                mReturnMoneyRuleAdapter.notifyDataSetChanged();
            } else {
                //按订单
                Type type = new TypeToken<List<RMPolicyRuleDetailsTwoBean>>() {
                }.getType();
                List<RMPolicyRuleDetailsTwoBean> RMPolicyRuleDetailsBeanList = mGson.fromJson(String.valueOf(ruleDetail), type);
                mReturnMoneyRuleList.clear();
                mReturnMoneyRuleList.addAll(RMPolicyRuleDetailsBeanList);
                mReturnMoneyRuleAdapter.setConfig(mRWay, mRule, mRbasis);
                mReturnMoneyRuleAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

  /*  private void setSingleData(JSONObject data) {
        try {
            JSONObject cycle = data.getJSONObject("cycle");
            JSONObject rule = data.getJSONObject("rule");
            JSONArray ruleDetail = rule.getJSONArray("rule_detail");
            Type type = new TypeToken<List<RMPolicyRuleDetailsBean>>() {
            }.getType();
            List<RMPolicyRuleDetailsBean> RMPolicyRuleDetailsBeanList = mGson.fromJson(String.valueOf(ruleDetail), type);
            mReturnMoneyRuleList.clear();
            mReturnMoneyRuleList.addAll(RMPolicyRuleDetailsBeanList);
            mReturnMoneyRuleAdapter.setConfig(mRWay,mRule);
            mReturnMoneyRuleAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Override
    protected void initEvent() {
        mScrollView.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                if (scrollY < 100 && scrollY > 0) {
                    int tras = scrollY / 5;
                    if (tras > 10) {
                        mTitleBarRl.setBackgroundColor(Color.parseColor("#" + tras + "4F6389"));
                    }
                } else if (scrollY >= 100) {
                    mTitleBarRl.setBackgroundColor(Color.parseColor("#4F6389"));
                } else {
                    mTitleBarRl.setBackgroundColor(Color.parseColor("#00ffffff"));
                }
            }
        });
    }

    @OnClick({R.id.back_return, R.id.check_return_money_record})
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.back_return:
                closeActivity();
                break;
            case R.id.check_return_money_record:
                bundle = new Bundle();
                bundle.putInt("did", mDid);
                int rId = mReturnMoneyTemList.get(mSelectedPosition).getR_id();
                bundle.putInt("rid", rId);
                skipActivity(ReturnMoneyRecordActivity.class, bundle);
                break;

        }
    }

    @Override
    public void onTemplateItemClick(int position) {
        for (int i = 0; i < mReturnMoneyTemList.size(); i++) {
            ReturnMoneyTemBean.DataBean bean = mReturnMoneyTemList.get(i);
            if (i == position) {
                bean.setChecked(true);
            } else {
                bean.setChecked(false);
            }
        }
        mReturnMoneyTemplateAdapter.notifyDataSetChanged();
        mSelectedPosition = position;

        mRType = mReturnMoneyTemList.get(position).getRtype();
        mRWay = mReturnMoneyTemList.get(position).getR_way();


        setReturnMoneyTemData(position);
        getTemplatePolicy(position);
    }

    public void setGone(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(View.GONE);
        }
    }

    public void setVisible(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(View.VISIBLE);
        }
    }
}
