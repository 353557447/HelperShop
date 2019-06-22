package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.FlashSaleSessionBean;
import com.shuiwangzhijia.shuidian.bean.MyReturnMoneyListBean;
import com.shuiwangzhijia.shuidian.bean.RMCheckOutOrderListBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.RmUnderwayAdapter;
import com.shuiwangzhijia.shuidian.newmodule.adapter.SettleAccountsAdapter;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.MeasureUtil;
import com.shuiwangzhijia.shuidian.view.MyScrollView;
import com.shuiwangzhijia.shuidian.view.RSelectTimeDialog;
import com.shuiwangzhijia.shuidian.view.SelectTimeDialog;
import com.shuiwangzhijia.shuidian.view.SpacesItemDecoration;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_settle_accounts)
public class SettleAccountsActivity extends BaseActivity implements MyScrollView.OnScrollListener {
    @BindView(R.id.r_type)
    TextView mRType;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.rule_details)
    TextView mRuleDetails;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.nest_scroll_view)
    MyScrollView mNestScrollView;
    @BindView(R.id.hintBtn)
    TextView mHintBtn;
    @BindView(R.id.money)
    TextView mMoney;
    @BindView(R.id.payBtn)
    TextView mPayBtn;
    @BindView(R.id.back_return)
    RelativeLayout mBackReturn;
    @BindView(R.id.title_bar_rl)
    RelativeLayout mTitleBarRl;
    private int mRId;
    private int mReturnType;
    private List<MyReturnMoneyListBean.DataBean.HistoryBean.TimeBean> mTimeCancel;
    private List<MyReturnMoneyListBean.DataBean.ConductBean.TimeBean> mTimeUnderway;
    private int mType;
    private List<RMCheckOutOrderListBean.DataBean.ListBean> mList;
    private SettleAccountsAdapter mSettleAccountsAdapter;
    private int mRWay;
    private int mRRule;
    private int mRBasis;
    private MyReturnMoneyListBean.DataBean.ConductBean.RuleDetailBeanX mRuleDetailsUnderWay;
    private MyReturnMoneyListBean.DataBean.HistoryBean.RuleDetailBean mRuleDetailsCancel;
    private SimpleDateFormat mSimpleDateFormat;
    private long checkOutStartTime;
    private long checkOutEndTime;
    private RMCheckOutOrderListBean mRmCheckOutOrderListBean;


    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM");
        //返利类型   1单笔返利，2月度返利，3季度返利，4年度返利，5其他返利
        Bundle bundle = getIntent().getExtras();
        mRId = bundle.getInt("rId");
        mReturnType = bundle.getInt("rType");
        mRWay = bundle.getInt("rWay");
        mRRule = bundle.getInt("rRule");
        mRBasis = bundle.getInt("rBasis");
        mRuleDetailsUnderWay = (MyReturnMoneyListBean.DataBean.ConductBean.RuleDetailBeanX) bundle.getSerializable("ruleDetailsUnderWay");
        mRuleDetailsCancel = (MyReturnMoneyListBean.DataBean.HistoryBean.RuleDetailBean) bundle.getSerializable("ruleDetailsCancel");
        mTimeCancel = (List<MyReturnMoneyListBean.DataBean.HistoryBean.TimeBean>) bundle.getSerializable("timeCancel");
        mTimeUnderway = (List<MyReturnMoneyListBean.DataBean.ConductBean.TimeBean>) bundle.getSerializable("timeUnderway");
        //1进行中  0取消
        mType = bundle.getInt("type");
        setBaseBarHide();
        setData();
        initRv();
    }

    private void initRv() {
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mSettleAccountsAdapter = new SettleAccountsAdapter(this);
        mList = new ArrayList<RMCheckOutOrderListBean.DataBean.ListBean>();
        mSettleAccountsAdapter.setData(mList);
        mRv.addItemDecoration(new SpacesItemDecoration(this, MeasureUtil.dip2px(this, 12)));
        mRv.setAdapter(mSettleAccountsAdapter);
    }

    private void setData() {
        switch (mReturnType) {
            case 1:
                mRType.setText("单笔返利");
                break;
            case 2:
                mRType.setText("月度返利");
                break;
            case 3:
                mRType.setText("季度返利");
                break;
            case 4:
                mRType.setText("年度返利");
                break;
            case 5:
                mRType.setText("周期返利");

                break;
        }
        StringBuilder sb = new StringBuilder();
        // 1按商品 2按订单
        if (mRBasis == 1) {
            sb.append("按商品");
        } else {
            sb.append("按订单");
        }
        //返利规则 1倍数返利 2范围返利
        if (mRRule == 1) {
            sb.append("倍数返利");
        } else {
            sb.append("范围返利");
        }

        if (mRWay == 1) {
            //  mRuleRWay.setText("现金");
            if (mType == 1) {
                List<MyReturnMoneyListBean.DataBean.ConductBean.RuleDetailBeanX.DetailBeanX> detail = mRuleDetailsUnderWay.getDetail();
                for (int i = 0; i < detail.size(); i++) {
                    sb.append(" [");
                    MyReturnMoneyListBean.DataBean.ConductBean.RuleDetailBeanX.DetailBeanX detailBeanX = detail.get(i);
                    int full = detailBeanX.getFull();
                    String amount = detailBeanX.getAmount();
                    if (mRRule == 1) {
                        sb.append("每满" + full + "桶" + ",返" + amount + "元");
                    } else {
                        sb.append("满" + full + "桶" + ",返" + amount + "元");
                    }
                    sb.append("] ");
                }
            } else {
                List<MyReturnMoneyListBean.DataBean.HistoryBean.RuleDetailBean.DetailBean> detail = mRuleDetailsCancel.getDetail();
                for (int i = 0; i < detail.size(); i++) {
                    sb.append(" [");
                    MyReturnMoneyListBean.DataBean.HistoryBean.RuleDetailBean.DetailBean detailBean = detail.get(i);
                    int full = detailBean.getFull();
                    String amount = detailBean.getAmount();
                    if (mRRule == 1) {
                        sb.append("每满" + full + "桶" + ",返" + amount + "元");
                    } else {
                        sb.append("满" + full + "桶" + ",返" + amount + "元");
                    }
                    sb.append("] ");
                }
            }
        } else {
            // mRuleRWay.setText("水票");
            if (mType == 1) {
                List<MyReturnMoneyListBean.DataBean.ConductBean.RuleDetailBeanX.DetailBeanX> detail = mRuleDetailsUnderWay.getDetail();
                for (int i = 0; i < detail.size(); i++) {
                    sb.append(" [");
                    MyReturnMoneyListBean.DataBean.ConductBean.RuleDetailBeanX.DetailBeanX detailBeanX = detail.get(i);
                    int full = detailBeanX.getFull();
                    String sName = detailBeanX.getS_name();
                    int sNum = detailBeanX.getSnum();
                    if (mRRule == 1) {
                        sb.append("每满" + full + "桶" + ",返" + sName + sNum + "张");
                    } else {
                        sb.append("满" + full + "桶" + ",返" + sName + sNum + "张");
                    }
                    sb.append("] ");
                }

            } else {
                List<MyReturnMoneyListBean.DataBean.HistoryBean.RuleDetailBean.DetailBean> detail = mRuleDetailsCancel.getDetail();
                for (int i = 0; i < detail.size(); i++) {
                    sb.append(" [");
                    MyReturnMoneyListBean.DataBean.HistoryBean.RuleDetailBean.DetailBean detailBean = detail.get(i);
                    int full = detailBean.getFull();
                    String sName = detailBean.getS_name();
                    int sNum = detailBean.getSnum();
                    if (mRRule == 1) {
                        sb.append("每满" + full + "桶" + ",返" + sName + sNum + "张");
                    } else {
                        sb.append("满" + full + "桶" + ",返" + sName + sNum + "张");
                    }
                    sb.append("] ");
                }


            }
        }
        mRuleDetails.setText(sb.toString());
    }

    @Override
    protected void initData() {
        if (mType == 1) {
            MyReturnMoneyListBean.DataBean.ConductBean.TimeBean timeBean = mTimeUnderway.get(0);
            switch (mReturnType) {
                //单笔 其他周期
                case 1:
                case 5:
                    List<Long> timeArray = timeBean.getTime_array();
                    mTime.setText(mSimpleDateFormat.format(new Date(timeArray.get(0) * 1000)));
                    getData(timeArray.get(0), timeArray.get(1));
                    checkOutStartTime = timeArray.get(0);
                    checkOutEndTime = timeArray.get(1);
                    break;
                default:
                    mTime.setText(mSimpleDateFormat.format(new Date(timeBean.getTimeStamp() * 1000)));
                    getData(timeBean.getTimeStamp(), timeBean.getEnd_time());
                    checkOutStartTime = timeBean.getTimeStamp();
                    checkOutEndTime = timeBean.getEnd_time();
                    break;
            }

        } else {
            MyReturnMoneyListBean.DataBean.HistoryBean.TimeBean timeBeanCancel = mTimeCancel.get(0);
            switch (mReturnType) {
                case 1:
                case 5:
                    List<Long> timeArray = timeBeanCancel.getTime_array();
                    mTime.setText(mSimpleDateFormat.format(new Date(timeArray.get(0) * 1000)));
                    getData(timeArray.get(0), timeArray.get(1));
                    checkOutStartTime = timeArray.get(0);
                    checkOutEndTime = timeArray.get(1);
                    break;
                default:
                    mTime.setText(mSimpleDateFormat.format(new Date(timeBeanCancel.getTimeStamp() * 1000)));
                    getData(timeBeanCancel.getTimeStamp(), timeBeanCancel.getEnd_time());
                    checkOutStartTime = timeBeanCancel.getTimeStamp();
                    checkOutEndTime = timeBeanCancel.getEnd_time();
                    break;
            }

        }

    }

    private void getData(long startTime, long endTime) {
      /*  did	是	int	水厂id
        r_id	是	int	水厂id
        start_time	是	int	周期开始时间
        end_time	是	int	周期结束时间*/

        showLoadingDialog();
        RetrofitUtils.getInstances().create().getMyReturnMoneyCheckOutList(CommonUtils.getDid(), mRId, startTime, endTime).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        mRmCheckOutOrderListBean = mGson.fromJson(datas, RMCheckOutOrderListBean.class);
                        List<RMCheckOutOrderListBean.DataBean.ListBean> list = mRmCheckOutOrderListBean.getData().getList();
                        if (!list.isEmpty()) {
                            setCentreViewDismiss();
                            mList.clear();
                            mList.addAll(list);
                            mSettleAccountsAdapter.notifyDataSetChanged();
                        } else {
                            mList.clear();
                            mSettleAccountsAdapter.notifyDataSetChanged();
                            setCentreViewShow(R.drawable.wudingdan, "暂无结算返利订单~");
                        }
                        RMCheckOutOrderListBean.DataBean.TatolBean tatol = mRmCheckOutOrderListBean.getData().getTatol();
                        mHintBtn.setText("共"+tatol.getGoods_num()+"桶，返利");
                        if(mRWay==1){
                            //现金
                            mMoney.setText("￥"+tatol.getAmount());
                        }else{
                            //水票
                            mMoney.setText("水票"+tatol.getSnum()+"张");
                        }
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

    @Override
    protected void initEvent() {
        mNestScrollView.setOnScrollListener(this);
    }

    @OnClick({R.id.time, R.id.payBtn, R.id.back_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.time:
                //  RMCheckOutOrderListBean
                if (mReturnType != 1 && mReturnType != 5 && mReturnType != 4)
                    initTimePickView();
                break;
            case R.id.payBtn:
                handlePay();
                break;
            case R.id.back_return:
                closeActivity();
                break;
        }
    }

    private void handlePay() {
        /*token	是	string	token
        did	是	int	水厂id
        r_id	是	int	水厂id
        start_time	是	int	周期开始时间
        end_time	是	int	周期结束时间
        order_arr	是	int	订单数组
        total_rebate	是	int	总返利总额*/
        List<String> orderList = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            orderList.add(mList.get(i).getOrder_sn());
        }
        RMCheckOutOrderListBean.DataBean.TatolBean tatolBean = mRmCheckOutOrderListBean.getData().getTatol();
        String totalRebate;
        if (mRWay == 1) {
            totalRebate = tatolBean.getAmount() + "";
        } else {
            totalRebate = tatolBean.getSnum() + "";
        }
        showLoadingDialog();
        RetrofitUtils.getInstances().create().returnMoneyCheckOut(CommonUtils.getDid(), mRId, checkOutStartTime, checkOutEndTime, orderList, totalRebate).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        showToast("结算成功");
                        getData(checkOutStartTime,checkOutEndTime);
                    } else {
                        showToast(object.getString("msg"));
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


    private void initTimePickView() {
        RSelectTimeDialog dialog = new RSelectTimeDialog(this, mType, mReturnType, mTimeUnderway, mTimeCancel, new RSelectTimeDialog.OnItemClickListener() {
            @Override
            public void onSureClick(RSelectTimeDialog dialog, int selectTimePosition) {
                if (mType == 1) {
                    MyReturnMoneyListBean.DataBean.ConductBean.TimeBean timeBean = mTimeUnderway.get(selectTimePosition);
                    mTime.setText(mSimpleDateFormat.format(new Date(timeBean.getTimeStamp() * 1000)));
                    getData(timeBean.getTimeStamp(), timeBean.getEnd_time());
                    checkOutStartTime = timeBean.getTimeStamp();
                    checkOutEndTime = timeBean.getEnd_time();
                } else {
                    MyReturnMoneyListBean.DataBean.HistoryBean.TimeBean timeBeanCancel = mTimeCancel.get(0);
                    mTime.setText(mSimpleDateFormat.format(new Date(timeBeanCancel.getTimeStamp() * 1000)));
                    getData(timeBeanCancel.getTimeStamp(), timeBeanCancel.getEnd_time());
                    checkOutStartTime = timeBeanCancel.getTimeStamp();
                    checkOutEndTime = timeBeanCancel.getEnd_time();
                }
                dialog.dismiss();
            }
        });
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onScroll(int scrollY) {
        if (scrollY < 500 && scrollY > 0) {
            int tras = scrollY / 5;
            if (tras > 10) {
                mTitleBarRl.setBackgroundColor(Color.parseColor("#" + tras + "FF014E"));
            }
        } else if (scrollY >= 500) {
            mTitleBarRl.setBackgroundColor(Color.parseColor("#FF014E"));
        } else {
            mTitleBarRl.setBackgroundColor(Color.parseColor("#00ffffff"));
        }
    }
}
