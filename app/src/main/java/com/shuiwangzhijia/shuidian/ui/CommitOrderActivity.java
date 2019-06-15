package com.shuiwangzhijia.shuidian.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.CommitOrderAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.ActiveBean;
import com.shuiwangzhijia.shuidian.bean.AddressBean;
import com.shuiwangzhijia.shuidian.bean.CommitOrderBean;
import com.shuiwangzhijia.shuidian.bean.GoodsBean;
import com.shuiwangzhijia.shuidian.bean.OrderBean;
import com.shuiwangzhijia.shuidian.bean.ShowCouponBean;
import com.shuiwangzhijia.shuidian.bean.TicketBean;
import com.shuiwangzhijia.shuidian.bean.UseTicketBean;
import com.shuiwangzhijia.shuidian.dialog.HintDialog;
import com.shuiwangzhijia.shuidian.event.AddressEvent;
import com.shuiwangzhijia.shuidian.event.CouponsEvent;
import com.shuiwangzhijia.shuidian.event.GoodsEvent;
import com.shuiwangzhijia.shuidian.event.TicketEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.CalculateUtils;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;
import com.shuiwangzhijia.shuidian.view.CustomScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommitOrderActivity extends BaseAct {
    @BindView(R.id.goods_rv)
    RecyclerView mGoodsRv;
    @BindView(R.id.head_title)
    RelativeLayout mTitle;
    @BindView(R.id.custom_scrollview)
    CustomScrollView mCustomScrollview;
    @BindView(R.id.status_bar)
    TextView mStatusBar;
    @BindView(R.id.status)
    TextView mStatus;
    @BindView(R.id.adress)
    TextView mAdress;
    @BindView(R.id.water_ticket)
    RelativeLayout mWaterTicket;
    @BindView(R.id.coupons)
    RelativeLayout mCoupons;
    @BindView(R.id.shop_name)
    TextView mShopName;
    @BindView(R.id.add_goods)
    TextView mAddGoods;
    @BindView(R.id.shop_ll)
    LinearLayout mShopLl;
    @BindView(R.id.one_text)
    TextView mOneText;
    @BindView(R.id.arrow_two)
    ImageView mArrowTwo;
    @BindView(R.id.arrow_three)
    ImageView mArrowThree;
    @BindView(R.id.money)
    TextView mMoney;
    @BindView(R.id.hintBtn)
    TextView mHintBtn;
    @BindView(R.id.payBtn)
    TextView mPayBtn;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.money_full)
    TextView mMoneyFull;
    @BindView(R.id.water_voteText)
    TextView mWaterVoteText;
    @BindView(R.id.cou_text)
    TextView mCouText;
    @BindView(R.id.top_up)
    TextView mTopUp;
    @BindView(R.id.specific_address)
    TextView mSpecificAddress;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.phone)
    TextView mPhone;
    @BindView(R.id.adress_manager)
    RelativeLayout mAdressManager;
    @BindView(R.id.distribution_status)
    TextView mDistributionStatus;
    @BindView(R.id.callBtn)
    ImageView mCallBtn;
    @BindView(R.id.full_reduction_rl)
    RelativeLayout mFullReductionRl;
    private String totalMoney;
    private List<GoodsBean> buyData = new ArrayList<>();
    private List<GoodsBean> newData = new ArrayList<>();
    private List<GoodsBean> newDataTwo = new ArrayList<>();
    private CommitOrderAdapter mCommitOrderAdapter;
    private UseTicketBean useTicketBean;
    private TicketEvent mTicketEvent;
    private double sub;
    private ShowCouponBean mCoupon;
    private List<ActiveBean> mHotCityBeanList;
    private ActiveBean mActiveBean;
    private Double total;
    private double mTicketAmount;
    private int mC_id;
    private String mAmount = "0.0";
    private String mFull = "0.0";
    private AddressBean addressBean;
    private int mAdressId;
    private int mDid;
    private int mDelivery_type;
    //    private int mType;  //1.购物车 2 再来一单
    private int mTickCount;
    private List<TicketBean> mSelectWaterData;
    private boolean isFromWaterTicket;
    private boolean isFromCoupon;
    private int ticketCount;
    //有满减商品的总价
    private Double fullPrice = 0.0;
    //满减是否可用
    private boolean isFullCanUse;
    private int totalTicketNumber = 0;

    public static void startAtc(Context context, List<GoodsBean> buyData) {
        Intent intent = new Intent(context, CommitOrderActivity.class);
        intent.putExtra("data", (Serializable) buyData);
//        intent.putExtra("money", totalPrice);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KLog.d("onCreate");
        getWindow().setSoftInputMode
                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_commit_order);
        ButterKnife.bind(this);
        setNoTitleBar();
        buyData = (List<GoodsBean>) getIntent().getSerializableExtra("data");
//        newData.addAll(buyData);
//        mType = getIntent().getIntExtra("type", -1);
        KLog.d(buyData.size());
        if (buyData.size() > 0) {
            mShopName.setText(buyData.get(0).getSname());
        }
//        totalMoney = getIntent().getStringExtra("money");
        initRecycleView();
        mTitle.bringToFront();
        mCustomScrollview.setOnScrollListener(new CustomScrollView.OnScrollListener() {
            @Override
            public void onScroll(int l, int t, int oldl, int oldt) {
                if (t >= 300) {
                    mStatusBar.setVisibility(View.GONE);
                    mStatus.setVisibility(View.VISIBLE);
                    mAdress.setVisibility(View.VISIBLE);
                    mStatus.setText(mDistributionStatus.getText());
                    mAdress.setText(mSpecificAddress.getText().toString());
                } else {
                    mStatusBar.setVisibility(View.VISIBLE);
                    mStatus.setVisibility(View.GONE);
                    mAdress.setVisibility(View.GONE);
                }
            }
        });
        initFullReduction();
        KLog.d(mActiveBean == null);
        calculate();
//      getDefaultAddress();
        initListener();
        getList();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (isFromWaterTicket){
//            return;
//        }
//        if (isFromCoupon){
//            return;
//        }
//    }

    private void getDefaultAddress() {
        showLoad();
        RetrofitUtils.getInstances().create().getDefaultAddress().enqueue(new Callback<EntityObject<ArrayList<AddressBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<AddressBean>>> call, Response<EntityObject<ArrayList<AddressBean>>> response) {
                hintLoad();
                EntityObject<ArrayList<AddressBean>> body = response.body();
                if (body.getCode() == 200) {
                    ArrayList<AddressBean> result = body.getResult();
                    if (result == null || result.size() == 0) {
                        mName.setText("请选择收货人地址信息");
                        mPhone.setText("");
                        mSpecificAddress.setText("");
                    } else {
                        addressBean = result.get(0);
                        mAdressId = addressBean.getId();
                        mName.setText(addressBean.getAname());
                        mPhone.setText(addressBean.getSphone());
                        mSpecificAddress.setText(addressBean.getDaddress());
                    }

                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<AddressBean>>> call, Throwable t) {

            }
        });
    }

    private void initListener() {
        mPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromWaterTicket = false;
                isFromCoupon = false;
                if (addressBean == null) {
                    hint("请选择收货人地址信息...");
                    return;
                }

                String ticketdata;
                if (mTicketEvent == null) {
                    ticketdata = "";
                } else {
                    ticketdata = mTicketEvent.data;
                }
                KLog.d(CommonUtils.getToken() + "\n" + mDid + "\n" + total + "\n" + packageDataThree() + "\n" + ticketdata + "\n" + mC_id + "\n" + mAdressId);
                //packageDataTwo()商品数据  ticketdata 水票数据   mc_id 优惠券的id
                showLoad();
                RetrofitUtils.getInstances().create().generateOrderShopS(CommonUtils.getToken(), mDid, total + "", packageDataThree(), ticketdata, mC_id, mAdressId).enqueue(new Callback<EntityObject<OrderBean>>() {
                    @Override
                    public void onResponse(Call<EntityObject<OrderBean>> call, Response<EntityObject<OrderBean>> response) {
                        hintLoad();
                        EntityObject<OrderBean> body = response.body();
                        if (body.getScode() == 700) {
                            updateShopCartInfo();
                            if (mDelivery_type == 0) {
                                PurchaseOrderActivity.statAct(CommitOrderActivity.this, 1);
                            } else {
                                PurchaseOrderActivity.statAct(CommitOrderActivity.this, 5);
                            }
                            finish();
                            return;
                        }
                        if (body.getCode() == 200) {
                            finish();
                            updateShopCartInfo();
                            OrderBean result = body.getResult();
                            OrderPayActivity.startAtc(CommitOrderActivity.this, result, 3);
                        } else {
                            ToastUitl.showToastCustom(body.getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Call<EntityObject<OrderBean>> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void updateShopCartInfo() {
        List<GoodsBean> shopCart = CommonUtils.getShopCart();
        List<GoodsBean> newShopCart = new ArrayList<>();
        List<GoodsBean> buyGoodsList = CommonUtils.getBuyGoodsList();
        if (buyGoodsList != null) {
            GoodsBean buy, item = null;
            for (int j = 0; j < shopCart.size(); j++) {
                buy = shopCart.get(j);
                boolean remove = false;
                for (int i = 0; i < buyGoodsList.size(); i++) {
                    item = buyGoodsList.get(i);
                    if (buy.getGid().equals(item.getGid())) {
                        remove = true;
                    }
                }
                if (!remove) {
                    newShopCart.add(buy);
                }
            }
            CommonUtils.saveBuyGoodsList(null);
            CommonUtils.saveShopCart(newShopCart);
            EventBus.getDefault().post(new GoodsEvent(newShopCart.size(), ""));
        }
    }

    private void calculate() {
        if (buyData == null || buyData.size() == 0) {
            return;
        }
        total = 0.0;
        for (GoodsBean bean : buyData) {
            total = CalculateUtils.add(CalculateUtils.mul(Double.parseDouble(bean.getPrice()), bean.getCount()), total);
        }
        mDid = buyData.get(0).getDid();
        KLog.d(mDid);
        KLog.d(newDataTwo.size() == 0);

        //刚进来的时候判断满减是否可用，如果选择水票了则不用此方法
        if (newDataTwo.size() == 0) {
            for (int i = 0; i < mCommitOrderAdapter.getData().size(); i++) {
                GoodsBean bean = mCommitOrderAdapter.getData().get(i);
                //有满减
                KLog.d(bean.getActivity_type());
                if (bean.getActivity_type() == 1 || bean.getActivity_type() == 2) {
                    KLog.d(bean.getPrice() + "   " + bean.getPprice());
                    if (bean.getPrice() == null) {
                        fullPrice = CalculateUtils.add(CalculateUtils.mul(Double.valueOf(bean.getPprice()), bean.getCount()), fullPrice);
//                        fullPrice = CalculateUtils.mul(Double.valueOf(bean.getPprice()), bean.getCount());
                    } else {
                        fullPrice = CalculateUtils.add(CalculateUtils.mul(Double.valueOf(bean.getPrice()), bean.getCount()), fullPrice);
//                        fullPrice = CalculateUtils.mul(Double.valueOf(bean.getPrice()), bean.getCount());
                    }

                }
            }

            if (mHotCityBeanList != null && mHotCityBeanList.get(0) != null) {
                if (CalculateUtils.sub(fullPrice, Double.parseDouble(mHotCityBeanList.get(0).getFull())) >= 0) {
                    KLog.d("isFullCanUse");
                    mFullReductionRl.setVisibility(View.VISIBLE);
                    isFullCanUse = true;
                } else {
                    isFullCanUse = false;
                    mFullReductionRl.setVisibility(View.GONE);
                }
            }
            if (mActiveBean != null) {
                if (CalculateUtils.sub(fullPrice, Double.parseDouble(mActiveBean.getFull())) >= 0) {
                    initFullReduction();
                    mFullReductionRl.setVisibility(View.VISIBLE);
                } else {
                    mFullReductionRl.setVisibility(View.GONE);
                }
            }
        }

        if (Double.doubleToLongBits(Double.parseDouble(mAmount)) != Double.doubleToLongBits(0.0) && Double.doubleToLongBits(mTicketAmount) != Double.doubleToLongBits(0.0)) {
            KLog.d("有优惠券并且有水票");
            total = CalculateUtils.sub(total, Double.parseDouble(mAmount));
            total = CalculateUtils.sub(total, mTicketAmount);
            if (Double.doubleToLongBits(total) < Double.doubleToLongBits(0.0)) {
                total = 0.0;
            }
            mHintBtn.setText("已优惠￥" + CalculateUtils.add(Double.parseDouble(mAmount), mTicketAmount));
            KLog.d(total);
            mMoney.setText("￥" + total);
            return;
        }

        if (Double.doubleToLongBits(mTicketAmount) != Double.doubleToLongBits(0.0) && mHotCityBeanList != null) {
            KLog.d("有满减和水票");
            total = CalculateUtils.sub(total, mTicketAmount);
            //判满不满足满减条件
            if (isFullCanUse) {
                if (mHotCityBeanList != null && mHotCityBeanList.get(0) != null) {
                    total = CalculateUtils.sub(total, Double.parseDouble(mHotCityBeanList.get(0).getReduce()));
                    mHintBtn.setText("已优惠￥" + CalculateUtils.add(Double.parseDouble(mHotCityBeanList.get(0).getReduce()), mTicketAmount));
                }
            }else {
                if (mHotCityBeanList != null && mHotCityBeanList.get(0) != null) {
                    mHintBtn.setText("已优惠￥" +  mTicketAmount);
                }
            }
            if (mActiveBean!=null){
                mHintBtn.setText("已优惠￥" +  mActiveBean.getFull());
            }
            if (Double.doubleToLongBits(total) < Double.doubleToLongBits(0.0)) {
                total = 0.0;
            }
            KLog.d(total);
            mMoney.setText("￥" + total);
            return;
        }

        //优惠券
        if (Double.doubleToLongBits(Double.parseDouble(mAmount)) == Double.doubleToLongBits(0.0)) {
            KLog.d("没有优惠券");
        } else {
            KLog.d("有优惠券");
            if (Double.doubleToLongBits(total) > Double.doubleToLongBits(Double.parseDouble(mFull))) {
                total = CalculateUtils.sub(total, Double.parseDouble(mAmount));
                if (Double.doubleToLongBits(total) < Double.doubleToLongBits(0.0)) {
                    total = 0.0;
                }
                mHintBtn.setText("已优惠￥" + mAmount);
                KLog.d(total);
                mMoney.setText("￥" + total);
                return;
            }
        }

        //有水票
        if (Double.doubleToLongBits(mTicketAmount) == Double.doubleToLongBits(0.0)) {
            KLog.d("没有水票数据");
            mHintBtn.setText("已优惠￥" + 0.00);
        } else {
            KLog.d("有水票数据");
            total = CalculateUtils.sub(total, mTicketAmount);
            if (Double.doubleToLongBits(total) < Double.doubleToLongBits(0.0)) {
                total = 0.0;
            }
            mHintBtn.setText("已优惠￥" + mTicketAmount);
            mMoney.setText("￥" + total);
            KLog.d(total);
            return;
        }

        //有满减
        if (mHotCityBeanList != null) {
            String reduce = mHotCityBeanList.get(0).getReduce();
            if (isFullCanUse) {
                total = CalculateUtils.sub(total, Double.parseDouble(reduce));
                mHintBtn.setText("已优惠￥" + reduce);
            }
            if (Double.doubleToLongBits(total) < Double.doubleToLongBits(0.0)) {
                total = 0.0;
            }
            KLog.d(total);
            mMoney.setText("￥" + total);
            return;
        }
        mMoney.setText("￥" + total);

    }


    public void initFullReduction() {
        //是否有满减数据
        Gson gson = new Gson();
        for (GoodsBean bean : buyData) {
            String active = bean.getActive();
            int activity_type = bean.getActivity_type();
            KLog.d(activity_type);
            if (activity_type == 1) {
                mHotCityBeanList = gson.fromJson(active, new TypeToken<List<ActiveBean>>() {
                }.getType());
            } else if (activity_type == 2) {
                mActiveBean = gson.fromJson(active, ActiveBean.class);
            }
            if (activity_type == 1 || activity_type == 2) {
                mTopUp.setVisibility(View.VISIBLE);
                mMoneyFull.setVisibility(View.VISIBLE);
                mFullReductionRl.setVisibility(View.VISIBLE);
                if (activity_type == 1) {
                    if (mHotCityBeanList != null) {
                        mOneText.setText("满减");
                        mTopUp.setText("满" + mHotCityBeanList.get(0).getFull() + "减" + mHotCityBeanList.get(0).getReduce());
                        mMoneyFull.setText("— ￥" + mHotCityBeanList.get(0).getReduce());
                        mMoneyFull.setTextColor(getResources().getColor(R.color.color_A53533));
                    }
                } else if (activity_type == 2) {
                    if (mActiveBean != null) {
                        mOneText.setText("满赠");
                        mTopUp.setText("满" + mActiveBean.getFull() + "赠水票");
                        mMoneyFull.setText(mActiveBean.getLinks().get(0).getName() + "x" + mActiveBean.getLinks().get(0).getNum());
                        mMoneyFull.setTextColor(getResources().getColor(R.color.color_A53533));
                    }
                }
            }
        }
    }

    private void setNumStyle(TextView text, String first, String count, String second, String amount, String third) {
        SpannableString spanString = new SpannableString(first + count + second + amount + third);
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_ff3939)), first.length(), (count + first).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_ff3939)), (count + first + second).length(), (count + first + second + amount).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        text.setText(spanString);
    }

    public void getList() {
        KLog.d(packageData());
        RetrofitUtils.getInstances().create().showTicketsUse(packageData()).enqueue(new Callback<EntityObject<UseTicketBean>>() {
            @Override
            public void onResponse(Call<EntityObject<UseTicketBean>> call, Response<EntityObject<UseTicketBean>> response) {
                EntityObject<UseTicketBean> body = response.body();
                if (body.getCode() == 200) {
                    ticketCount = 0;
                    useTicketBean = body.getResult();
                    ArrayList<TicketBean> list = useTicketBean.getList();
                    if (list != null && list.size() > 0) {
                        for (TicketBean bean : list) {
                            if (bean.getCuse() == 1) {
                                ticketCount += bean.getSum();
                            }
                        }
                    }
                    if (ticketCount == 0) {
                        mWaterVoteText.setTextColor(getResources().getColor(R.color.color_ababab));
                        mWaterVoteText.setBackgroundResource(R.color.white);
                        mWaterVoteText.setText("暂无水票可用");
                        return;
                    }
                    mWaterVoteText.setText(ticketCount + "张可以使用");
                    mWaterVoteText.setTextColor(getResources().getColor(R.color.white));
                    mWaterVoteText.setBackgroundColor(Color.parseColor("#FF4D14"));
                }
            }

            @Override
            public void onFailure(Call<EntityObject<UseTicketBean>> call, Throwable t) {

            }
        });

        getCouponList();

        RetrofitUtils.getInstances().create().getShopWateInfo(mDid).enqueue(new Callback<EntityObject<CommitOrderBean>>() {
            @Override
            public void onResponse(Call<EntityObject<CommitOrderBean>> call, Response<EntityObject<CommitOrderBean>> response) {
                EntityObject<CommitOrderBean> body = response.body();
                if (body.getCode() == 200) {
                    CommitOrderBean result = body.getResult();
                    mDelivery_type = result.getDelivery_type();
                    if (mDelivery_type == 0) {
                        mDistributionStatus.setText("水厂配送");
                        mCallBtn.setVisibility(View.GONE);
                        getDefaultAddress();
                    } else {
                        mDistributionStatus.setText("水厂自提");
                        mCallBtn.setVisibility(View.VISIBLE);
                        getZitiInfo();
                        mAdressManager.setEnabled(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<EntityObject<CommitOrderBean>> call, Throwable t) {

            }
        });
    }

    private void getCouponList() {
        String trim = mMoney.getText().toString().trim();
        String totalMoney = trim.substring(1, trim.length());
        KLog.d(totalMoney);
        KLog.d(packageDataTwo());
        RetrofitUtils.getInstances().create().ShowCoupon(mDid, totalMoney, packageDataTwo()).enqueue(new Callback<EntityObject<ShowCouponBean>>() {
            @Override
            public void onResponse(Call<EntityObject<ShowCouponBean>> call, Response<EntityObject<ShowCouponBean>> response) {
                EntityObject<ShowCouponBean> body = response.body();
                if(body==null)
                    return;
                if (body.getCode() == 200) {
                    mCoupon = body.getResult();
                    List<ShowCouponBean.ListBean> list = mCoupon.getList();
                    if (list != null && list.size() > 0) {
                        mCouText.setTextColor(getResources().getColor(R.color.white));
                        mCouText.setText(list.size() + "张可以使用");
                        mCouText.setBackgroundColor(getResources().getColor(R.color.common_red));
                        mCoupons.setEnabled(true);
                    }
                } else {
                    mCouText.setTextColor(getResources().getColor(R.color.color_ababab));
                    mCouText.setText("暂无优惠券可以使用");
                    mCouText.setBackgroundResource(R.color.white);
                    mCoupons.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ShowCouponBean>> call, Throwable t) {

            }
        });
    }

    private void getZitiInfo() {
        RetrofitUtils.getInstances().create().getWate(mDid).enqueue(new Callback<EntityObject<AddressBean>>() {
            @Override
            public void onResponse(Call<EntityObject<AddressBean>> call, Response<EntityObject<AddressBean>> response) {
                EntityObject<AddressBean> body = response.body();
                if (body.getCode() == 200) {
                    addressBean = body.getResult();
                    if (addressBean == null) {
                        mName.setText("请选择收货人地址信息");
                        mPhone.setText("");
                        mSpecificAddress.setText("");
                    } else {
                        mAdressId = addressBean.getAdress_id();
                        mName.setText(addressBean.getAname());
                        mPhone.setText(addressBean.getAphone());
                        mSpecificAddress.setText(addressBean.getAddress());
                        mSpecificAddress.setCompoundDrawables(null, null, null, null);
                    }
                }
            }

            @Override
            public void onFailure(Call<EntityObject<AddressBean>> call, Throwable t) {

            }
        });
    }

    private String packageDataTwo() {
        JSONArray array = new JSONArray();
        JSONObject item = null;
        if (isFromWaterTicket) {
            for (GoodsBean bean : newDataTwo) {
                item = new JSONObject();
                try {
                    item.put("did", bean.getDid());
                    item.put("gid", bean.getGid());
                    item.put("num", bean.getCount());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                array.put(item);
            }
        } else {
            for (GoodsBean bean : buyData) {
                item = new JSONObject();
                try {
                    item.put("did", bean.getDid());
                    item.put("gid", bean.getGid());
                    item.put("num", bean.getCount());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                array.put(item);
            }
        }

        return array.toString();
    }

    private String packageDataThree() {
        JSONArray array = new JSONArray();
        JSONObject item = null;
        for (GoodsBean bean : buyData) {
            item = new JSONObject();
            try {
                item.put("did", bean.getDid());
                item.put("gid", bean.getGid());
                item.put("num", bean.getCount());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(item);
        }
        return array.toString();
    }

    /**
     * 组装数据
     *
     * @return
     */
    private String packageData() {
        JSONArray array = new JSONArray();
        JSONObject item = null;
        for (GoodsBean bean : buyData) {
            item = new JSONObject();
            try {
                item.put("did", bean.getDid());
                item.put("gid", bean.getGid());
                item.put("num", bean.getCount());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(item);
        }
        return array.toString();
    }

    private void initRecycleView() {
        mGoodsRv.setLayoutManager(new LinearLayoutManager(this));
        mGoodsRv.setHasFixedSize(true);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.divider_bg_tran));
        mGoodsRv.addItemDecoration(divider);
        mCommitOrderAdapter = new CommitOrderAdapter(this);
        mCommitOrderAdapter.setData(buyData);
        mGoodsRv.setAdapter(mCommitOrderAdapter);
    }

    @OnClick({R.id.water_ticket, R.id.coupons, R.id.back, R.id.adress_manager, R.id.callBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.callBtn:
                HintDialog hintDialog = new HintDialog(this, "确认拨打电话？", new HintDialog.OnConfirmClickListener() {
                    @Override
                    public void onConfirm(Dialog dialog) {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + addressBean.getAphone());
                        intent.setData(data);
                        startActivity(intent);
                    }
                });
                hintDialog.show();
                break;
            case R.id.adress_manager:
                startActivity(new Intent(CommitOrderActivity.this, AddressManageActivity.class));
                break;
            case R.id.water_ticket:
                if (useTicketBean == null || ticketCount == 0) {
                    hint("没有可以使用的水票");
                    return;
                }
                ArrayList<TicketBean> ticketBeanList = useTicketBean.getList();
                if (ticketBeanList == null || ticketBeanList.size() == 0) {
                    hint("没有优惠可选");
                    return;
                }
                //传商品 便于对比数据
                List<GoodsBean> data = mCommitOrderAdapter.getData();
                for (GoodsBean bean : data) {
                    KLog.d(bean.getCount());
                }
                useTicketBean.setGoodsList(data);
                SelectWaterActivity.startAtc(this, -3, useTicketBean);
                break;
            case R.id.coupons:
                if (mCoupon == null) {
                    hint("没有可以使用的优惠券");
                    return;
                }
                SelectCouponsActivity.startAtc(this, mCoupon);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    //优惠券
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(CouponsEvent event) {
        KLog.d("CouponsEvent");
        isFromCoupon = true;
        ShowCouponBean.ListBean msg = event.getMsg();
        int type = event.getType();
        if (type == 1) {
            mAmount = msg.getAmount();
            mFull = msg.getFull();
            mC_id = msg.getId();
//            String goods_id = msg.getGoods_id();
//            String[] split = goods_id.split(",");
//            List<String> list = Arrays.asList(split);
//            List<GoodsBean> data = mCommitOrderAdapter.getData();
//            for (int i = 0; i < data.size(); i++) {
//                String gid = data.get(i).getGid();
//                int count = data.get(i).getCount();
//                String price = data.get(i).getPrice();
//                for (int j = 0; j < list.size(); j++) {
//                    String s = list.get(j);
//                    if (s.equals(gid)){
//                        double mul = CalculateUtils.mul(Double.parseDouble(price), count);
//                        if (Double.parseDouble(mAmount) > mul){
//                            mAmount = mul+"";
//                        }
//                    }
//                }
//            }
            mCouText.setText("已选" + mAmount + "元优惠券");
            mCouText.setTextColor(getResources().getColor(R.color.color_ababab));
            mCouText.setBackgroundResource(R.color.white);
            mMoneyFull.setVisibility(View.VISIBLE);
            mMoneyFull.setText("活动不与优惠券同享");
            mMoneyFull.setTextColor(getResources().getColor(R.color.color_ababab));
            mTopUp.setVisibility(View.GONE);
            calculate();
        } else {
            mCouText.setText("没有选择优惠券");
            mHintBtn.setText("已优惠￥" + 0.00);
            mCouText.setTextColor(getResources().getColor(R.color.color_ababab));
            mCouText.setBackgroundResource(R.color.white);
            mC_id = 0;
            mAmount = "0.0";
            if (mHotCityBeanList != null) {
                mMoneyFull.setText("— ￥" + mHotCityBeanList.get(0).getReduce());
                mMoneyFull.setTextColor(getResources().getColor(R.color.color_A53533));
            }
            calculate();
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(AddressEvent event) {
        if (event.isDelete) {
            AddressBean data = event.data;
            if (data.getId() == addressBean.getId()) {
                mName.setText("请选择收货人地址信息");
                mPhone.setText("");
                mSpecificAddress.setText("");
            }
            addressBean = null;
            if (data.getDefaultInt() != 0) {
                getDefaultAddress();
            } else {
                getListAddress();
            }

        } else {
            if (event.data != null) {
                addressBean = event.data;
                mName.setText(addressBean.getAname());
                mPhone.setText(addressBean.getSphone());
                mSpecificAddress.setText(addressBean.getDaddress());
            }
        }
    }

    private void getListAddress() {
        RetrofitUtils.getInstances().create().getAddressList().enqueue(new Callback<EntityObject<ArrayList<AddressBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<AddressBean>>> call, Response<EntityObject<ArrayList<AddressBean>>> response) {
                hintLoad();
                EntityObject<ArrayList<AddressBean>> body = response.body();
                if (body.getCode() == 200) {
                    ArrayList<AddressBean> result = body.getResult();
                    if (result == null || result.size() == 0) {
                        mName.setText("请选择收货人地址信息");
                        mPhone.setText("");
                        mSpecificAddress.setText("");
                    } else {
                        addressBean = result.get(0);
                        mName.setText(addressBean.getAname());
                        mPhone.setText(addressBean.getSphone());
                        mSpecificAddress.setText(addressBean.getDaddress());
                    }
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<AddressBean>>> call, Throwable t) {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(TicketEvent event) {
        Map<String, GoodsBean> ticketData = new HashMap<>();
        newData.clear();
        newDataTwo.clear();
        isFromWaterTicket = true;
        fullPrice = 0.0;
        totalTicketNumber = 0;
        mTicketEvent = event;
        mTicketAmount = event.amount;
        mWaterVoteText.setTextColor(getResources().getColor(R.color.color_ababab));
        setNumStyle(mWaterVoteText, "你已选", "" + event.count, "张水票，可抵", "" + event.amount, "元");
        mWaterVoteText.setBackgroundResource(R.color.white);
        Gson gson = new Gson();
        mSelectWaterData = gson.fromJson(event.data, new TypeToken<List<TicketBean>>() {
        }.getType());
        List<GoodsBean> data = mCommitOrderAdapter.getData();

        for (int i = 0; i < data.size(); i++) {
            int count = data.get(i).getCount();
            String gid = data.get(i).getGid();
            int did = data.get(i).getDid();
            int activity_type = data.get(i).getActivity_type();
            String price = data.get(i).getPrice();
            for (int j = 0; j < mSelectWaterData.size(); j++) {
                String gid1 = mSelectWaterData.get(j).getGid();
//                int count1 = mSelectWaterData.get(j).getNum();
                if (gid.equals(gid1)) {
                    for (int k = 0; k < mSelectWaterData.size(); k++) {
                        int num = mSelectWaterData.get(k).getNum();
                        totalTicketNumber+=num;
                    }
                    int newCount = count - totalTicketNumber;
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCount(newCount);
                    goodsBean.setDid(did);
                    goodsBean.setGid(gid);
                    goodsBean.setActivity_type(activity_type);
                    goodsBean.setPrice(price);
                    newData.add(goodsBean);
                }
            }
        }
        newData.addAll(data);
        for (int i = 0; i < newData.size(); i++) {
            GoodsBean bean = newData.get(i);
            String gid = bean.getGid();
            if (!ticketData.containsKey(gid)) {
                ticketData.put(gid, bean);
            }
        }
        Set<String> strings = ticketData.keySet();
        for (String key : strings) {
            GoodsBean bean = ticketData.get(key);
            newDataTwo.add(bean);
        }
        KLog.d(new Gson().toJson(newDataTwo));
        for (int i = 0; i < newDataTwo.size(); i++) {
            GoodsBean bean = newDataTwo.get(i);
            //有满减
            KLog.d(bean.getActivity_type());
            if (bean.getActivity_type() == 1 || bean.getActivity_type() == 2) {
                fullPrice = CalculateUtils.add(CalculateUtils.mul(Double.valueOf(bean.getPrice()), bean.getCount()), fullPrice);
//                fullPrice = CalculateUtils.mul(Double.valueOf(bean.getPrice()), bean.getCount());
            }
        }
        KLog.d(fullPrice);
        if (mHotCityBeanList != null && mHotCityBeanList.get(0) != null) {
            if (CalculateUtils.sub(fullPrice, Double.parseDouble(mHotCityBeanList.get(0).getFull())) >= 0) {
                KLog.d("isFullCanUse");
                isFullCanUse = true;
                if (mHotCityBeanList != null) {
                    mOneText.setText("满减");
                    mTopUp.setText("满" + mHotCityBeanList.get(0).getFull() + "减" + mHotCityBeanList.get(0).getReduce());
                    mMoneyFull.setText("— ￥" + mHotCityBeanList.get(0).getReduce());
                    mMoneyFull.setTextColor(getResources().getColor(R.color.color_A53533));
                }
            } else {
                isFullCanUse = false;
                mMoneyFull.setVisibility(View.VISIBLE);
                mMoneyFull.setText("无可参加活动");
                mMoneyFull.setTextColor(getResources().getColor(R.color.color_ababab));
            }
        }
        if (mActiveBean != null) {
            KLog.d("mActiveBean!=null");
            if (CalculateUtils.sub(fullPrice, Double.parseDouble(mActiveBean.getFull())) >= 0) {
                mMoney.setVisibility(View.VISIBLE);
                mTopUp.setVisibility(View.VISIBLE);
                initFullReduction();
            } else {
                mTopUp.setVisibility(View.GONE);
                mMoneyFull.setVisibility(View.VISIBLE);
                mMoneyFull.setText("无可参加活动");
                mMoneyFull.setTextColor(getResources().getColor(R.color.color_ababab));
            }
        }
        mAmount = "0.0";
        mC_id = 0;
        calculate();
        getCouponList();
    }

    private void hint(String text) {
        ToastUitl.showToastCustom(text);
    }
}
