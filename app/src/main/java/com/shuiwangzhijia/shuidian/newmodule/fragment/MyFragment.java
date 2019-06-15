package com.shuiwangzhijia.shuidian.newmodule.fragment;


import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.BaseRecyclerAdapter;
import com.shuiwangzhijia.shuidian.adapter.SmartViewHolder;
import com.shuiwangzhijia.shuidian.base.BaseLazyFragment;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.MyFragmentInfoBean;
import com.shuiwangzhijia.shuidian.bean.ShopInfoData;
import com.shuiwangzhijia.shuidian.bean.TicketBean;
import com.shuiwangzhijia.shuidian.event.CommonEvent;
import com.shuiwangzhijia.shuidian.event.LoginOutEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.activity.DiscountsWaterCouponActivity;
import com.shuiwangzhijia.shuidian.newmodule.activity.GoodsManageNewActivity;
import com.shuiwangzhijia.shuidian.newmodule.activity.MarketingToolsActivity;
import com.shuiwangzhijia.shuidian.newmodule.activity.MyWalletActivity;
import com.shuiwangzhijia.shuidian.newmodule.activity.RechargeCenterActivity;
import com.shuiwangzhijia.shuidian.newmodule.activity.ShopEarningsActivity;
import com.shuiwangzhijia.shuidian.newmodule.activity.SmartEquipActivity;
import com.shuiwangzhijia.shuidian.ui.BucketManageActivity;
import com.shuiwangzhijia.shuidian.ui.CustomerManageActivity;
import com.shuiwangzhijia.shuidian.ui.LoginActivity;
import com.shuiwangzhijia.shuidian.ui.MyCouActivity;
import com.shuiwangzhijia.shuidian.ui.OrderManageActivity;
import com.shuiwangzhijia.shuidian.ui.PurchaseOrderActivity;
import com.shuiwangzhijia.shuidian.ui.SetActivity;
import com.shuiwangzhijia.shuidian.ui.ShopCountActivity;
import com.shuiwangzhijia.shuidian.ui.ShopInfoActivity;
import com.shuiwangzhijia.shuidian.ui.ShopManageActivity;
import com.shuiwangzhijia.shuidian.ui.ShopSpreadActivity;
import com.shuiwangzhijia.shuidian.ui.ShopStudyActivity;
import com.shuiwangzhijia.shuidian.ui.TicketActivity;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.GenerateList;
import com.shuiwangzhijia.shuidian.utils.MyUtils;
import com.shuiwangzhijia.shuidian.view.MyFragmentScrollView;
import com.shuiwangzhijia.shuidian.view.MyLinearLayout;
import com.shuiwangzhijia.shuidian.view.RxTextViewVertical;
import com.google.gson.Gson;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.fragment_my)
public class MyFragment extends BaseLazyFragment implements View.OnTouchListener, MyLinearLayout.OnMyClickListener, MyFragmentScrollView.OnScrollListener {
    @BindView(R.id.setting)
    ImageView mSetting;
    @BindView(R.id.shop_head)
    CircleImageView mShopHead;
    @BindView(R.id.shop_name)
    TextView mShopName;
    @BindView(R.id.total_balance)
    TextView mTotalBalance;
    @BindView(R.id.bottom_ll)
    LinearLayout mBottomLl;
    @BindView(R.id.nest_scroll_view)
    MyFragmentScrollView mNestScrollView;
    @BindView(R.id.my_service_gv)
    GridView mMyServiceGv;
    @BindView(R.id.for_payment)
    MyLinearLayout mForPayment;
    @BindView(R.id.for_distribution)
    MyLinearLayout mForDistribution;
    @BindView(R.id.distributioning)
    MyLinearLayout mDistributioning;
    @BindView(R.id.in_arrears)
    MyLinearLayout mInArrears;
    @BindView(R.id.since_the_lift)
    MyLinearLayout mSinceTheLift;
    @BindView(R.id.title_bar_rl)
    RelativeLayout mTitleBarRl;
    @BindView(R.id.shop_status_switch)
    Switch mShopStatusSwitch;
    @BindView(R.id.on_or_off)
    TextView mOnOrOff;
    @BindView(R.id.recharge_immediately)
    TextView mRechargeImmediately;
    @BindView(R.id.shop_address)
    TextView mShopAddress;
    @BindView(R.id.total_return_money)
    TextView mTotalReturnMoney;
    @BindView(R.id.discount_coupon_counts)
    TextView mDiscountCouponCounts;
    @BindView(R.id.water_coupon_counts)
    TextView mWaterCouponCounts;
    @BindView(R.id.recharge_act_counts)
    TextView mRechargeActCounts;
    @BindView(R.id.recharge_act_one_money)
    TextView mRechargeActOneMoney;
    @BindView(R.id.recharge_act_one_gift)
    TextView mRechargeActOneGift;
    @BindView(R.id.recharge_act_two_money)
    TextView mRechargeActTwoMoney;
    @BindView(R.id.recharge_act_two_gift)
    TextView mRechargeActTwoGift;
    @BindView(R.id.recharge_act_three_money)
    TextView mRechargeActThreeMoney;
    @BindView(R.id.recharge_act_three_gift)
    TextView mRechargeActThreeGift;
    @BindView(R.id.act_rv)
    RecyclerView mActRv;
    @BindView(R.id.total_balance_ll)
    LinearLayout mTotalBalanceLl;
    @BindView(R.id.total_return_money_ll)
    LinearLayout mTotalReturnMoneyLl;
    @BindView(R.id.discount_coupon_counts_ll)
    LinearLayout mDiscountCouponCountsLl;
    @BindView(R.id.new_order)
    MyLinearLayout mNewOrder;
    @BindView(R.id.shipping)
    MyLinearLayout mShipping;
    @BindView(R.id.distribution)
    MyLinearLayout mDistribution;
    @BindView(R.id.completed)
    MyLinearLayout mCompleted;
    @BindView(R.id.coupons)
    MyLinearLayout mCoupons;
    @BindView(R.id.recharge_act_one_rl)
    RelativeLayout mRechargeActOneRl;
    @BindView(R.id.recharge_act_two_rl)
    RelativeLayout mRechargeActTwoRl;
    @BindView(R.id.recharge_act_three_rl)
    RelativeLayout mRechargeActThreeRl;
    @BindView(R.id.shop_basic_info_rl)
    RelativeLayout mShopBasicInfoRl;
    @BindView(R.id.run_text)
    RxTextViewVertical mRunText;
    private SimpleAdapter adapter;
    private ArrayList<Map<String, Object>> dataList;
    private MyFragmentInfoBean.DataBean.ShopBean mShopData;
    private MyFragmentInfoBean.DataBean.RechargeBean mRechargeData;
    private List<MyFragmentInfoBean.DataBean.RechargeBean.ListBean> mRechargeList;
    private boolean mSwitchFlag;
    private List<MyLinearLayout> mNotifyViews;
    private float moveStartX;
    private float moveStartY;
    private boolean isScrollViewCanScrollDown = false;

    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView(View view) {
        initMyServiceGv();
    }


    @Override
    protected void initData() {
        super.initData();
        initShopActRv();
        initNotifyView();
        initRunText();
    }

    @Override
    public void onResume() {
        super.onResume();
        getShopInfoData();
        getTicketInfo();
    }

    private boolean isTextRun;

    private void getTicketInfo() {
        RetrofitUtils.getInstances().create().getDiscountTicketList(0, 10).enqueue(new Callback<EntityObject<ArrayList<TicketBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<TicketBean>>> call, Response<EntityObject<ArrayList<TicketBean>>> response) {
                EntityObject<ArrayList<TicketBean>> body = response.body();
                KLog.e(new Gson().toJson(body));
                if (body.getCode() == 200) {
                    ArrayList<TicketBean> result = body.getResult();
                    if (result == null || result.size() == 0) {
                        mRunText.setVisibility(View.GONE);
                        return;
                    } else {
                        mRunText.setVisibility(View.VISIBLE);
                        ArrayList<String> runTextContentList = new ArrayList<>();
                        for (TicketBean bean : result) {
                            runTextContentList.add(bean.getSnum() + "张水票，仅售" + bean.getSprice() + "元！");
                        }
                        mRunText.setTextList(runTextContentList);
                        if (!isTextRun) {
                            mRunText.startAutoScroll();
                            isTextRun = true;
                        }
                    }
                }else{
                    mRunText.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<TicketBean>>> call, Throwable t) {
                Log.i("json", t.getMessage());
            }
        });
    }

    private void initRunText() {
        mRunText.setText(12, 1, 0xff5C5D07);//设置属性
        mRunText.setTextStillTime(3000);//设置停留时长间隔
        mRunText.setAnimTime(300);//设置进入和退出的时间间隔
        mRunText.setOnItemClickListener(new RxTextViewVertical.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }


    private void getShopInfoData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getMyFragmentInfo(CommonUtils.getToken()).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        MyFragmentInfoBean myFragmentInfoBean = mGson.fromJson(datas, MyFragmentInfoBean.class);
                        mShopData = myFragmentInfoBean.getData().getShop();
                        mRechargeData = myFragmentInfoBean.getData().getRecharge();
                        mRechargeList = mRechargeData.getList();
                        //初始化店铺营业和休息状态
                        int isBusiness = mShopData.getIs_business();
                        if (isBusiness == 1) {
                            mSwitchFlag = true;
                            mShopStatusSwitch.setChecked(true);
                            mOnOrOff.setText("营业中");
                        } else {
                            mSwitchFlag = false;
                            mShopStatusSwitch.setChecked(false);
                            mOnOrOff.setText("已打烊");
                        }

                        //设置水店信息
                        mShopName.setText(mShopData.getSname());
                        mShopAddress.setText(mShopData.getAddress());
                        // mShopAddress.setText(mShopData.getProvince() + mShopData.getCity() + mShopData.getDist() + mShopData.getAddress());
                        setImg(mShopHead, mShopData.getHeader_pic(), "wtshop/");
                        mTotalBalance.setText(mShopData.getTotal_balance());
                        mTotalReturnMoney.setText(mShopData.getTotal_rebate_amount());
                        mDiscountCouponCounts.setText(mShopData.getTotal_coupon() + "");
                        mWaterCouponCounts.setText(mShopData.getTotal_mypicket());


                        mRechargeActCounts.setText("共有" + mRechargeData.getTotal() + "个");
                        int size = mRechargeList.size();
                        switch (size) {
                            case 1:
                                mRechargeActOneRl.setVisibility(View.VISIBLE);
                                mRechargeActTwoRl.setVisibility(View.INVISIBLE);
                                mRechargeActThreeRl.setVisibility(View.INVISIBLE);
                                break;
                            case 2:
                                mRechargeActOneRl.setVisibility(View.VISIBLE);
                                mRechargeActTwoRl.setVisibility(View.VISIBLE);
                                mRechargeActThreeRl.setVisibility(View.INVISIBLE);
                                break;
                            case 3:
                                mRechargeActOneRl.setVisibility(View.VISIBLE);
                                mRechargeActTwoRl.setVisibility(View.VISIBLE);
                                mRechargeActThreeRl.setVisibility(View.VISIBLE);
                                break;
                        }
                        MyFragmentInfoBean.DataBean.RechargeBean.ListBean listBeanOne = mRechargeList.get(0);
                        if (listBeanOne != null) {
                            String sailSmount = listBeanOne.getSail_amount();
                            String ramount = listBeanOne.getRamount();
                            double sailSmountD = Double.parseDouble(sailSmount);
                            double ramountD = Double.parseDouble(ramount);
                            mRechargeActOneMoney.setText("￥" + sailSmount);
                            mRechargeActOneGift.setText("赠送" + MyUtils.formatPrice(ramountD - sailSmountD));
                        }
                        MyFragmentInfoBean.DataBean.RechargeBean.ListBean listBeanTwo = mRechargeList.get(1);
                        if (listBeanTwo != null) {
                            String sailSmount = listBeanTwo.getSail_amount();
                            String ramount = listBeanTwo.getRamount();
                            double sailSmountD = Double.parseDouble(sailSmount);
                            double ramountD = Double.parseDouble(ramount);
                            mRechargeActTwoMoney.setText("￥" + sailSmount);
                            mRechargeActTwoGift.setText("赠送" + MyUtils.formatPrice(ramountD - sailSmountD));
                        }
                        MyFragmentInfoBean.DataBean.RechargeBean.ListBean listBeanThree = mRechargeList.get(2);
                        if (listBeanThree != null) {
                            String sailSmount = listBeanThree.getSail_amount();
                            String ramount = listBeanThree.getRamount();
                            double sailSmountD = Double.parseDouble(sailSmount);
                            double ramountD = Double.parseDouble(ramount);
                            mRechargeActThreeMoney.setText("￥" + sailSmount);
                            mRechargeActThreeGift.setText("赠送" + MyUtils.formatPrice(ramountD - sailSmountD));
                        }
                    } else if (code == -200) {
                        LoginActivity.startActLoginOut(mContext);
                        getActivity().finish();
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
                KLog.e(t.getMessage());
                dismissLoadingDialog();
            }
        });
    }


    private void initShopActRv() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mActRv.setLayoutManager(manager);
        List<Integer> list = GenerateList.getList(10);
        BaseRecyclerAdapter<Integer> baseRecyclerAdapter = new BaseRecyclerAdapter<Integer>(list, R.layout.item_simple_54_imageview) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, Integer model, int position) {

            }
        };
        mActRv.setAdapter(baseRecyclerAdapter);
    }


    private void initNotifyView() {
        mNotifyViews = new ArrayList();
        mNotifyViews.add(mCoupons);
        mNotifyViews.add(mNewOrder);
        mNotifyViews.add(mShipping);
        mNotifyViews.add(mDistribution);
        mNotifyViews.add(mCompleted);
        mNotifyViews.add(mForPayment);
        mNotifyViews.add(mForDistribution);
        mNotifyViews.add(mDistributioning);
        mNotifyViews.add(mInArrears);
        mNotifyViews.add(mSinceTheLift);
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected void initEvent() {
        mNestScrollView.setOnScrollListener(this);
        //拖动效果
        mBottomLl.setOnTouchListener(this);

        mCoupons.setOnMyClickListener(this);
        mCompleted.setOnMyClickListener(this);
        mDistribution.setOnMyClickListener(this);
        mShipping.setOnMyClickListener(this);
        mNewOrder.setOnMyClickListener(this);
        mForPayment.setOnMyClickListener(this);
        mForDistribution.setOnMyClickListener(this);
        mDistributioning.setOnMyClickListener(this);
        mInArrears.setOnMyClickListener(this);
        mSinceTheLift.setOnMyClickListener(this);
    }


    private void initMyServiceGv() {
        //图标
        int icon[] = {R.drawable.my_shop_manage, R.drawable.my_goods_manage,//
                R.drawable.my_wallet_icon, R.drawable.my_client_manage,//
                R.drawable.my_statistics_icon, R.drawable.my_shop_study,//
                R.drawable.my_generalize, R.drawable.my_shop_marketing,//
                R.drawable.my_empty_manage, R.drawable.my_moneymaking_proposition,//
                R.drawable.my_shop_earnings, R.drawable.my_smart_equip_icon
        };
        //图标下的文字
        String name[] = {"店铺管理", "商品管理", "我的钱包", "客户管理", "店铺统计",//
                "店铺学习", "店铺推广", "店铺营销", "空桶管理", "生意经", "店铺收益", "智能设备"};
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icon[i]);
            map.put("text", name[i]);
            dataList.add(map);
        }
        String[] from = {"img", "text"};
        int[] to = {R.id.img, R.id.text};
        adapter = new SimpleAdapter(getActivity(), dataList, R.layout.item_my_service, from, to);
        mMyServiceGv.setAdapter(adapter);
        mMyServiceGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle;
                switch (i) {
                    case 0:
                        skipActivity(ShopManageActivity.class);
                        break;
                    case 1:
                        skipActivity(GoodsManageNewActivity.class);
                        break;
                    case 2:
                        skipActivity(MyWalletActivity.class);
                        break;
                    case 3:
                        skipActivity(CustomerManageActivity.class);
                        break;
                    case 4:
                        skipActivity(ShopCountActivity.class);
                        break;
                    case 5:
                        skipActivity(ShopStudyActivity.class);
                        break;
                    case 6:
                        skipActivity(ShopSpreadActivity.class);
                        break;
                    case 7:
                        //skipActivity(ShopMarketActivity.class);
                        skipActivity(MarketingToolsActivity.class);
                        break;
                    case 8:
//                      ToastUitl.showToastCustom("正在开发中，敬请期待...");
                        skipActivity(BucketManageActivity.class);
                        break;
                    case 9:
                        showToast("敬请期待");
                        break;
                    case 10:
                        skipActivity(ShopEarningsActivity.class);
                        break;
                    case 11:
                        skipActivity(SmartEquipActivity.class);
                        break;
                }
            }
        });
    }

    @Override
    public void onScroll(int scrollY) {
        if (scrollY == 0) {
            isScrollViewCanScrollDown = false;
        } else {
            isScrollViewCanScrollDown = true;
        }

        if (scrollY < 500 && scrollY > 0) {
            int tras = scrollY / 5;
            if (tras > 10) {
                mTitleBarRl.setBackgroundColor(Color.parseColor("#" + tras + "4F6389"));
            }
        } else if (scrollY >= 500) {
            mTitleBarRl.setBackgroundColor(Color.parseColor("#4F6389"));
        } else {
            mTitleBarRl.setBackgroundColor(Color.parseColor("#00ffffff"));
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBottomLl.getLayoutParams();
        int top = 0;
        if (mBottomLl != null)
            top = mBottomLl.getTop();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moveStartY = event.getRawY();
                moveStartX = event.getRawX();
                if (isScrollViewCanScrollDown) {
                    mNestScrollView.setIntercept(true);
                } else {
                    mNestScrollView.setIntercept(false);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float y = event.getRawY();
                float x = event.getRawX();
                float distanceY = y - moveStartY;
                float distanceX = x - moveStartX;
                boolean isVerticalScroll = Math.abs(distanceX) < Math.abs(distanceY);
                if (distanceY < 0) {
                    //上滑
                    mNestScrollView.setIntercept(true);
                } else {
                    //下滑
                    mNestScrollView.setIntercept(false);
                    if (isVerticalScroll) {
                        if (top < 160) {
                            layoutParams.topMargin = (int) (top + distanceY);
                            mBottomLl.setLayoutParams(layoutParams);
                            mBottomLl.requestLayout();
                        }
                    } else {
                        //横向滑动 不做任何处理
                    }
                }
                moveStartY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                notifyAllMyLinearLayout(event.getRawY());
                ValueAnimator animator = ValueAnimator.ofInt(top, 0);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animatedValue = (int) animation.getAnimatedValue();
                        if (layoutParams != null && mBottomLl != null) {
                            layoutParams.topMargin = animatedValue;
                            mBottomLl.setLayoutParams(layoutParams);
                            mBottomLl.requestLayout();
                        }
                    }
                });
                animator.setDuration(300);
                animator.start();
                break;
        }
        if (isScrollViewCanScrollDown) {
            return false;
        } else {
            return true;
        }
    }

    private void notifyAllMyLinearLayout(float rawY) {
        for (MyLinearLayout myLinearLayout :
                mNotifyViews) {
            myLinearLayout.myPerformClick(rawY);
        }
    }

    @OnClick({R.id.shop_status_switch, R.id.recharge_immediately, R.id.setting, R.id.total_balance_ll//
            , R.id.total_return_money_ll, R.id.discount_coupon_counts_ll, R.id.water_coupon_counts_ll//
            , R.id.shop_basic_info_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting:
                skipActivity(SetActivity.class);
                break;
            case R.id.shop_status_switch:
                getShopState();
                break;
            case R.id.recharge_immediately:
                skipActivity(RechargeCenterActivity.class);
                break;
            case R.id.total_balance_ll:
            case R.id.total_return_money_ll:
                skipActivity(MyWalletActivity.class);
                break;
            case R.id.discount_coupon_counts_ll:
                skipActivity(MyCouActivity.class);
                break;
            case R.id.water_coupon_counts_ll:
                skipActivity(TicketActivity.class);
                break;
            case R.id.shop_basic_info_rl:
                ShopInfoData shopInfoData = new ShopInfoData();
                shopInfoData.setHeader_pic(mShopData.getHeader_pic());
                shopInfoData.setSname(mShopData.getSname());
                shopInfoData.setProvice(mShopData.getProvince());
                shopInfoData.setCity(mShopData.getCity());
                shopInfoData.setDist(mShopData.getDist());
                shopInfoData.setAddress(mShopData.getAddress());
                shopInfoData.setLnglat(mShopData.getLnglat());
                shopInfoData.setIs_perfect(mShopData.getIs_perfect());
                shopInfoData.setAccount(mShopData.getAccount());
                ShopInfoActivity.startAtc(getActivity(), shopInfoData);
                break;

        }
    }

    private void getShopState() {
        RetrofitUtils.getInstances().create().getShopState().enqueue(new Callback<EntityObject<Object>>() {
            @Override
            public void onResponse(Call<EntityObject<Object>> call, Response<EntityObject<Object>> response) {
                EntityObject<Object> body = response.body();
                if (body.getCode() == 200) {
                    mSwitchFlag = !mSwitchFlag;
                    if (mSwitchFlag) {
                        mOnOrOff.setText("营业中");
                    } else {
                        mOnOrOff.setText("已打烊");
                    }
                    mShopStatusSwitch.setChecked(mSwitchFlag);
                } else {
                    if (body.getScode() == -200) {
                        EventBus.getDefault().post(new LoginOutEvent());
                    }
                }
            }

            @Override
            public void onFailure(Call<EntityObject<Object>> call, Throwable t) {

            }
        });
    }

    @Override
    public void OnMyClick(View view) {
        switch (view.getId()) {
            case R.id.coupons:
                skipActivity(DiscountsWaterCouponActivity.class);
                //DiscountTicketActivity.startAtc(mContext, -1, null);
                break;
            case R.id.completed:
                OrderManageActivity.startAtc(mContext, 3);
                break;
            case R.id.distribution:
                OrderManageActivity.startAtc(mContext, 2);
                break;
            case R.id.shipping:
                OrderManageActivity.startAtc(mContext, 1);
                break;
            case R.id.new_order:
                OrderManageActivity.startAtc(mContext, 0);
                break;
            case R.id.for_payment:
                PurchaseOrderActivity.statAct(mContext, 0);
                break;
            case R.id.for_distribution:
                PurchaseOrderActivity.statAct(mContext, 1);
                break;
            case R.id.distributioning:
                PurchaseOrderActivity.statAct(mContext, 2);
                break;
            case R.id.in_arrears:
                PurchaseOrderActivity.statAct(mContext, 4);
                break;
            case R.id.since_the_lift:
                PurchaseOrderActivity.statAct(mContext, 5);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onScrollUpCancelYChanged(CommonEvent event) {
        if ("up_cancel_has_changed".equals(event.getMsg())) {
            if (isScrollViewCanScrollDown)
                notifyAllMyLinearLayout(mNestScrollView.mRawY);
        }
    }
}
