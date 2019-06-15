package com.shuiwangzhijia.shuidian.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.KeepListAdapter;
import com.shuiwangzhijia.shuidian.base.BaseFragment;
import com.shuiwangzhijia.shuidian.bean.ActiveBean;
import com.shuiwangzhijia.shuidian.bean.AddressBean;
import com.shuiwangzhijia.shuidian.bean.OrderBean;
import com.shuiwangzhijia.shuidian.bean.ShowCouponBean;
import com.shuiwangzhijia.shuidian.bean.ShowPlantsBean;
import com.shuiwangzhijia.shuidian.bean.TicketBean;
import com.shuiwangzhijia.shuidian.bean.UseTicketBean;
import com.shuiwangzhijia.shuidian.dialog.HintDialog;
import com.shuiwangzhijia.shuidian.dialog.OrderlDialog;
import com.shuiwangzhijia.shuidian.event.AddressEvent;
import com.shuiwangzhijia.shuidian.event.CouponsEvent;
import com.shuiwangzhijia.shuidian.event.TicketEvent;
import com.shuiwangzhijia.shuidian.event.TitleEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.ui.AddressManageActivity;
import com.shuiwangzhijia.shuidian.ui.OrderPayActivity;
import com.shuiwangzhijia.shuidian.ui.PurchaseOrderActivity;
import com.shuiwangzhijia.shuidian.ui.SelectCouponsActivity;
import com.shuiwangzhijia.shuidian.ui.SelectWaterActivity;
import com.shuiwangzhijia.shuidian.utils.CalculateUtils;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;
import com.shuiwangzhijia.shuidian.view.CustomScrollView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class KeepListFragment extends BaseFragment implements KeepListAdapter.OnViewItemClickListener {
    protected View mRootView;
    Unbinder unbinder;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.custom_scrollview)
    CustomScrollView mCustomScrollview;
    @BindView(R.id.status)
    TextView mStatus;
    @BindView(R.id.adress)
    TextView mAdress;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.phone)
    TextView mPhone;
    @BindView(R.id.shop_cb)
    CheckBox mShopCb;
    @BindView(R.id.shop_name)
    TextView mShopName;
    @BindView(R.id.water_vote_text)
    TextView mWaterVoteText;
    @BindView(R.id.cou_text)
    TextView mCouText;
    @BindView(R.id.add_goods)
    ImageView mAddGoods;
    @BindView(R.id.coupons)
    RelativeLayout mCoupons;
    @BindView(R.id.water_vote)
    RelativeLayout mWaterVote;
    @BindView(R.id.hintBtn)
    TextView mHintBtn;
    @BindView(R.id.one_text)
    TextView mOneText;
    @BindView(R.id.top_up)
    TextView mTopUp;
    @BindView(R.id.money)
    TextView mMoney;
    @BindView(R.id.payBtn)
    TextView mPayBtn;
    @BindView(R.id.phonebtn)
    ImageView mPhoneBtn;
    @BindView(R.id.adress_manager)
    RelativeLayout mAdressManager;
    private OrderlDialog mOrderlDialog;
    private AddressBean addressBean;
    private int type;
    private KeepListAdapter mKeepListAdapter;
    private int mDid;
    private ArrayList<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> mResult;
    private String mSname;
    private UseTicketBean useTicketBean;
    private ShowCouponBean mCoupon;
    private Double total;
    //有满减商品的总价
    private Double fullPrice = 0.0;
    //同一个商品有多个水票，计算这个商品总共用了多少张水票
    private int totalTicketNumber = 0;
    //满减是否可用
    private boolean isFullCanUse;
    private int number;
    private List<ActiveBean> mHotCityBeanList;
    private ActiveBean mActiveBean;
    private List<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> buyData = new ArrayList<>();
    private List<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> newData = new ArrayList<>();
    private List<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> newDataTwo = new ArrayList<>();
    private boolean isPay = false;
    private String mAmount = "0.0";
    private String mFull = "0.0";
    private TicketEvent mTicketEvent;
    private int mC_id;
    private int mAddress_id;
    private String mPhone1;
    private List<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> mOrder_goods;
    private double mTicketAmount;
    private int mDelivery_type;
    private String mAddress;
    private int mTicketCount;
    private boolean isFromWaterTicket;
    private boolean isFromCoupon;
    private List<TicketBean> mSelectWaterData;
    private int ticketNumber;
    private String mAname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        type = getArguments().getInt("type", 0);
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_keep_list, container, false);
            unbinder = ButterKnife.bind(this, mRootView);
            initData();
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            unbinder = ButterKnife.bind(this, mRootView);
        }
        initListener();
        return mRootView;
    }

    public void initCouponData() {
        mWaterVote.setClickable(false);
        mCoupons.setClickable(false);
        mWaterVoteText.setTextColor(getResources().getColor(R.color.color_ababab));
        mWaterVoteText.setBackgroundResource(R.color.white);
        mWaterVoteText.setText("请确认商品");
        mCouText.setTextColor(getResources().getColor(R.color.color_ababab));
        mCouText.setBackgroundResource(R.color.white);
        mCouText.setText("请确认商品");
        mMoney.setTextColor(getResources().getColor(R.color.color_ababab));
        mMoney.setBackgroundResource(R.color.white);
        mMoney.setText("请确认商品");
        mTopUp.setVisibility(View.GONE);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if (isFromWaterTicket) {
//            return;
//        }
//        if (isFromCoupon) {
//            return;
//        }
//    }

    private void initData() {
        showLoad();
        RetrofitUtils.getInstances().create().ShowPlants().enqueue(new Callback<EntityObject<ArrayList<ShowPlantsBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<ShowPlantsBean>>> call, Response<EntityObject<ArrayList<ShowPlantsBean>>> response) {
                hintLoad();
                EntityObject<ArrayList<ShowPlantsBean>> body = response.body();
                if (body.getCode() == 200) {
                    ArrayList<ShowPlantsBean> result = body.getResult();
                    if (result == null || result.size() == 0) {
                        return;
                    }
                    ShowPlantsBean showPlantsBean = result.get(type);
                    mDelivery_type = showPlantsBean.getDelivery_type();
                    mDid = showPlantsBean.getDid();
                    mOrder_goods = showPlantsBean.getHarvest_info().getOrder_goods();
                    mSname = showPlantsBean.getSname();
                    mShopName.setText(mSname);
                    if (mDelivery_type == 0) {
                        mStatus.setText("水厂配送");
                        mPhoneBtn.setVisibility(View.GONE);
                        mPhone1 = showPlantsBean.getHarvest_info().getPhone();
                        mAddress = showPlantsBean.getHarvest_info().getAddress();
                        mAddress_id = showPlantsBean.getHarvest_info().getAddress_id();
                        mAname = showPlantsBean.getHarvest_info().getAname();
                        mName.setText(mAname);
                        mPhone.setText(mPhone1);
                        mAdress.setText(mAddress);
                        mAdressManager.setEnabled(true);
                    } else if (mDelivery_type == 1) {
                        mStatus.setText("水厂自提");
                        mPhoneBtn.setVisibility(View.VISIBLE);
                        mAdressManager.setEnabled(false);
                        getZitiInfo();
                    }
                    initRecycleView();
                    if (mOrder_goods != null) {
                        mKeepListAdapter.setData(mOrder_goods);
                    }
                    if (mKeepListAdapter.getData() != null) {
                        mShopCb.setChecked(true);
                        for (ShowPlantsBean.HarvestInfoBean.OrderGoodsBean bean : mKeepListAdapter.getData()) {
                            bean.setCheck(mShopCb.isChecked());
                            mKeepListAdapter.notifyDataSetChanged();
                        }
                    }
                    isFullCanUse();
                    initFullReduction();
                    calculate();
                    getAddGoods();
                    getTicketWater();
                    getcoupons();
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<ShowPlantsBean>>> call, Throwable t) {

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
                    String cname = addressBean.getCname();
                    if (addressBean == null) {
                        mName.setText("请选择收货人地址信息");
                        mPhone.setText("");
                        mAdress.setText("");
                    } else {
                        mAddress_id = addressBean.getAdress_id();
                        mName.setText(addressBean.getAname());
                        mPhone.setText(addressBean.getAphone());
                        mAdress.setText(addressBean.getAddress() + "(" + cname + ")");
                        mAdress.setCompoundDrawables(null, null, null, null);
                    }
                }
            }

            @Override
            public void onFailure(Call<EntityObject<AddressBean>> call, Throwable t) {

            }
        });
    }

    public void initFullReduction() {
        //是否有满减数据
        Gson gson = new Gson();
        if (mOrder_goods != null) {
            for (ShowPlantsBean.HarvestInfoBean.OrderGoodsBean bean : mOrder_goods) {
                String active = bean.getActive();
                int activity_type = bean.getActivity_type();
                KLog.d(activity_type);
                if (activity_type == 1) {
                    mHotCityBeanList = gson.fromJson(active, new TypeToken<List<ActiveBean>>() {
                    }.getType());
                } else if (activity_type == 2) {
                    mActiveBean = gson.fromJson(active, ActiveBean.class);
                }
            }
            if (mHotCityBeanList != null && mHotCityBeanList.get(0) != null) {
                KLog.d(fullPrice + "   " + Double.parseDouble(mHotCityBeanList.get(0).getFull()));
                if (CalculateUtils.sub(fullPrice, Double.parseDouble(mHotCityBeanList.get(0).getFull())) >= 0) {
                    isFullCanUse = true;
                    mMoney.setVisibility(View.VISIBLE);
                    mTopUp.setVisibility(View.VISIBLE);
                    initFullReductionNew();
                } else {
                    isFullCanUse = false;
                    mMoney.setText("无可参加活动");
                    mMoney.setTextColor(mContext.getResources().getColor(R.color.color_ababab));
                    mTopUp.setVisibility(View.GONE);
                }
            }
            if (mActiveBean != null) {
                if (CalculateUtils.sub(fullPrice, Double.parseDouble(mActiveBean.getFull())) >= 0) {
                    mMoney.setVisibility(View.VISIBLE);
                    mTopUp.setVisibility(View.VISIBLE);
                    initFullReductionNew();
                } else {
                    mMoney.setText("无可参加活动");
                    mMoney.setTextColor(mContext.getResources().getColor(R.color.color_ababab));
                    mTopUp.setVisibility(View.GONE);
                }
            }
        }
    }

    //添加商品的时候得到最新的满减的数据
    public void initFullReductionNew() {
        //是否有满减数据
        Gson gson = new Gson();
        if (mKeepListAdapter.getData() != null) {
            for (ShowPlantsBean.HarvestInfoBean.OrderGoodsBean bean : mKeepListAdapter.getData()) {
                String active = bean.getActive();
                int activity_type = bean.getActivity_type();
                KLog.d(activity_type);
                if (activity_type == 1) {
                    mHotCityBeanList = gson.fromJson(active, new TypeToken<List<ActiveBean>>() {
                    }.getType());
                } else if (activity_type == 2) {
                    mActiveBean = gson.fromJson(active, ActiveBean.class);
                }
                if (bean.isCheck()) {
                    if (activity_type == 1 || activity_type == 2) {
                        mTopUp.setVisibility(View.VISIBLE);
                        mMoney.setVisibility(View.VISIBLE);
                        if (activity_type == 1) {
                            KLog.d(mHotCityBeanList != null);
                            if (mHotCityBeanList != null) {
                                mOneText.setText("满减");
                                mTopUp.setText("满" + mHotCityBeanList.get(0).getFull() + "减" + mHotCityBeanList.get(0).getReduce());
                                mMoney.setText("— ￥" + mHotCityBeanList.get(0).getReduce());
                                mMoney.setTextColor(getResources().getColor(R.color.color_A53533));
                                break;
                            }
                        } else if (activity_type == 2) {
                            if (mActiveBean != null) {
                                mOneText.setText("满赠");
                                mTopUp.setText("满" + mActiveBean.getFull() + "赠水票");
                                mMoney.setText(mActiveBean.getLinks().get(0).getName() + "x" + mActiveBean.getLinks().get(0).getNum());
                                mMoney.setTextColor(getResources().getColor(R.color.color_A53533));
                                break;
                            }
                        }
                    } else {
                        KLog.d(4444);
                        mTopUp.setVisibility(View.GONE);
                        mMoney.setText("无可参加活动");
                        mMoney.setTextColor(mContext.getResources().getColor(R.color.color_ababab));
                    }
                }
            }
        }
    }

    /**
     * 组装数据
     *
     * @return
     */
    private String packageData() {
        JSONArray array = new JSONArray();
        JSONObject item = null;
        if (buyData != null) {
            for (ShowPlantsBean.HarvestInfoBean.OrderGoodsBean bean : buyData) {
                if (bean.isCheck()){
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
        }
        return array.toString();
    }

    private String packageDataTwo() {
        JSONArray array = new JSONArray();
        JSONObject item = null;
        if (isFromWaterTicket) {
            if (newDataTwo == null) {
                return "";
            }
            for (ShowPlantsBean.HarvestInfoBean.OrderGoodsBean bean : newDataTwo) {
                if (bean.isCheck()){
                    item = new JSONObject();
                    try {
                        item.put("gid", bean.getGid());
                        item.put("num", bean.getCount());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    array.put(item);
                }
            }
        } else {
            if (buyData != null) {
                for (ShowPlantsBean.HarvestInfoBean.OrderGoodsBean bean : buyData) {
                    if (bean.isCheck()){
                        item = new JSONObject();
                        try {
                            item.put("gid", bean.getGid());
                            item.put("num", bean.getCount());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        array.put(item);
                    }
                }
            }
        }
        return array.toString();
    }

    private String packageDataThree() {
        JSONArray array = new JSONArray();
        JSONObject item = null;
        if (buyData != null) {
            for (ShowPlantsBean.HarvestInfoBean.OrderGoodsBean bean : buyData) {
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


    public void getAddGoods() {
        RetrofitUtils.getInstances().create().getGoods(mDid).enqueue(new Callback<EntityObject<ArrayList<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean>>> call, Response<EntityObject<ArrayList<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean>>> response) {
                EntityObject<ArrayList<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean>> body = response.body();
                if (body.getCode() == 200) {
                    mResult = body.getResult();
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean>>> call, Throwable t) {

            }
        });
    }

    public void getTicketWater() {
        KLog.d(packageData());
        RetrofitUtils.getInstances().create().showTicketsUse(packageData()).enqueue(new Callback<EntityObject<UseTicketBean>>() {
            @Override
            public void onResponse(Call<EntityObject<UseTicketBean>> call, Response<EntityObject<UseTicketBean>> response) {
                EntityObject<UseTicketBean> body = response.body();
                if (body.getCode() == 200) {
                    ticketNumber = 0;
                    useTicketBean = body.getResult();
                    ArrayList<TicketBean> list = useTicketBean.getList();
                    if (list != null && list.size() > 0) {
                        for (TicketBean bean : list) {
                            if (bean.getCuse() == 1) {
                                ticketNumber += bean.getSum();
                            }
                        }
                    }
                    if (KeepListFragment.this.isAdded()) {
                        if (ticketNumber == 0) {
                            mWaterVoteText.setTextColor(getResources().getColor(R.color.color_ababab));
                            mWaterVoteText.setBackgroundResource(R.color.white);
                            mWaterVoteText.setText("暂无水票可用");
                            return;
                        }
                        mWaterVoteText.setText(ticketNumber + "张可以使用");
                        mWaterVoteText.setTextColor(getResources().getColor(R.color.white));
                        mWaterVoteText.setBackgroundResource(R.drawable.cou_bg);
                    }
                }
            }

            @Override
            public void onFailure(Call<EntityObject<UseTicketBean>> call, Throwable t) {

            }
        });
    }

    public void getcoupons() {
        RetrofitUtils.getInstances().create().ShowCoupon(mDid, total + "", packageDataTwo()).enqueue(new Callback<EntityObject<ShowCouponBean>>() {
            @Override
            public void onResponse(Call<EntityObject<ShowCouponBean>> call, Response<EntityObject<ShowCouponBean>> response) {
                EntityObject<ShowCouponBean> body = response.body();
                if (body.getCode() == 200) {
                    mCoupon = body.getResult();
                    List<ShowCouponBean.ListBean> list = mCoupon.getList();
                    if (list != null && list.size() > 0) {
                        mCouText.setTextColor(mContext.getResources().getColor(R.color.white));
                        mCouText.setText(list.size() + "张可以使用");
                        mCouText.setBackgroundResource(R.drawable.shuipiao_bg);
                        mCoupons.setClickable(true);
                    }
                } else {
                    mCouText.setTextColor(mContext.getResources().getColor(R.color.color_ababab));
                    mCouText.setText("暂无优惠券可以使用");
                    mCouText.setBackgroundResource(R.color.white);
                    mCoupons.setClickable(false);
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ShowCouponBean>> call, Throwable t) {

            }
        });
    }

    private void initListener() {
        mCustomScrollview.setOnScrollListener(new CustomScrollView.OnScrollListener() {
            @Override
            public void onScroll(int l, int t, int oldl, int oldt) {
                if (t >= 300) {
                    EventBus.getDefault().post(new TitleEvent("show_adress", mAddress, mDelivery_type));
                } else {
                    EventBus.getDefault().post(new TitleEvent("show_title", mAddress, mDelivery_type));
                }
            }
        });
    }

    private void initRecycleView() {
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRv.setHasFixedSize(true);
        if (mRv.getItemDecorationCount() == 0) {
            DividerItemDecoration divider = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
            divider.setDrawable(getResources().getDrawable(R.drawable.divider_bg_tran));
            mRv.addItemDecoration(divider);
        }
        mKeepListAdapter = new KeepListAdapter(mContext);
        mKeepListAdapter.setOnItemClickListener(this);
        mRv.setAdapter(mKeepListAdapter);
    }

    @Override
    public void onSelectClick(int position) {
        isFromWaterTicket = false;
        buyData.clear();
        Gson gson = new Gson();
        ShowPlantsBean.HarvestInfoBean.OrderGoodsBean item = mKeepListAdapter.getItem(position);
        boolean check = item.isCheck();
        item.setCheck(!check);
        mKeepListAdapter.notifyDataSetChanged();
        for (int i = 0; i < mKeepListAdapter.getData().size(); i++) {
            ShowPlantsBean.HarvestInfoBean.OrderGoodsBean bean = mKeepListAdapter.getData().get(i);
            if (bean.isCheck()) {
                buyData.add(bean);
            }
        }
        if (buyData.size() == 0) {
            initCouponData();
        } else {
            isFullCanUse();
            calFullRecutionPrice();
            mWaterVote.setClickable(true);
            mCoupons.setClickable(true);
            getTicketWater();
            getcoupons();
        }
    }

    @Override
    public void onAddGoodsClick(int position) {
        isFromWaterTicket = false;
        ShowPlantsBean.HarvestInfoBean.OrderGoodsBean goodsBean = mKeepListAdapter.getItem(position);
        int count = goodsBean.getCount();
        count++;
        goodsBean.setCount(count);
        mKeepListAdapter.notifyDataSetChanged();
        isFullCanUse();
        calFullRecutionPrice();
        getTicketWater();
        getcoupons();
    }

    @Override
    public void onSubtractClick(int position) {
        isFromWaterTicket = false;
        ShowPlantsBean.HarvestInfoBean.OrderGoodsBean goodsBean = mKeepListAdapter.getItem(position);
        int count = goodsBean.getCount();
        if (count <= 1) {
            mKeepListAdapter.getData().remove(position);
            count--;
            goodsBean.setCount(count);
            mKeepListAdapter.notifyDataSetChanged();
            isFullCanUse();
            calFullRecutionPrice();
            getTicketWater();
            getcoupons();
            return;
        } else {
            count--;
            goodsBean.setCount(count);
            mKeepListAdapter.notifyDataSetChanged();
        }
        isFullCanUse();
        calFullRecutionPrice();
        getTicketWater();
        getcoupons();
    }

    @Override
    public void doEditClick(int position, int count) {
        ShowPlantsBean.HarvestInfoBean.OrderGoodsBean item = mKeepListAdapter.getItem(position);
        item.setCount(count);
        mKeepListAdapter.notifyDataSetChanged();
        isFullCanUse();
        calFullRecutionPrice();
        getcoupons();
        getTicketWater();
    }

    @OnClick({R.id.add_goods, R.id.coupons, R.id.water_vote, R.id.payBtn, R.id.phonebtn, R.id.adress_manager, R.id.shop_cb})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.shop_cb:
                List<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> data = mKeepListAdapter.getData();
                if (data == null) {
                    return;
                }
                for (ShowPlantsBean.HarvestInfoBean.OrderGoodsBean bean : data) {
                    bean.setCheck(mShopCb.isChecked());
                }
                mKeepListAdapter.notifyDataSetChanged();
                KLog.d(mShopCb.isChecked());
                if (!mShopCb.isChecked()) {
                    initCouponData();
                } else {
                    mWaterVote.setClickable(true);
                    mCoupons.setClickable(true);
//                    initFullReduction();
                    isFullCanUse();
                    calFullRecutionPrice();
                    getTicketWater();
                    getcoupons();
                }
                break;
            case R.id.adress_manager:
                startActivity(new Intent(mContext, AddressManageActivity.class));
                break;
            case R.id.phonebtn:
                HintDialog hintDialog = new HintDialog(mContext, "确认拨打电话？", new HintDialog.OnConfirmClickListener() {
                    @Override
                    public void onConfirm(Dialog dialog) {
                        dialog.dismiss();
                        if (mDelivery_type == 0) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + mPhone1);
                            intent.setData(data);
                            startActivity(intent);
                        } else if (mDelivery_type == 1) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + addressBean.getAphone());
                            intent.setData(data);
                            startActivity(intent);
                        }

                    }
                });
                hintDialog.show();
                break;
            case R.id.payBtn:
                isFromCoupon = false;
                isFromWaterTicket = false;
                buyData.clear();
                int number = 0;
                for (int i = 0; i < mKeepListAdapter.getData().size(); i++) {
                    ShowPlantsBean.HarvestInfoBean.OrderGoodsBean bean = mKeepListAdapter.getData().get(i);
                    if (bean.isCheck()) {
                        buyData.add(bean);
                        number += bean.getCount();
                    }
                }
                if (buyData.size() == 0) {
                    ToastUitl.showToastCustom("请选择购买的商品！");
                    return;
                }
                if (number == 0) {
                    ToastUitl.showToastCustom("购买数量不能为0！");
                    return;
                }
                String ticketdata;
                if (mTicketEvent == null) {
                    ticketdata = "";
                } else {
                    ticketdata = mTicketEvent.data;
                }
                KLog.d(CommonUtils.getToken() + "\n" + mDid + "\n" + total + "\n" + packageDataThree() + "\n" + ticketdata + "\n" + mC_id + "\n" + mAddress_id);
                RetrofitUtils.getInstances().create().generateOrderShopS(CommonUtils.getToken(), mDid, total + "", packageDataThree(), ticketdata, mC_id, mAddress_id).enqueue(new Callback<EntityObject<OrderBean>>() {
                    @Override
                    public void onResponse(Call<EntityObject<OrderBean>> call, Response<EntityObject<OrderBean>> response) {
                        EntityObject<OrderBean> body = response.body();
                        if (body.getScode() == 700) {
                            PurchaseOrderActivity.statAct(mContext, 1);
                            return;
                        }
                        if (body.getCode() == 200) {
                            getActivity().finish();
                            OrderBean result = body.getResult();
                            OrderPayActivity.startAtc(mContext, result, 3);
                        } else {
                            ToastUitl.showToastCustom(body.getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Call<EntityObject<OrderBean>> call, Throwable t) {

                    }
                });
                break;
            case R.id.add_goods:
                if (mResult == null || mResult.size() == 0) {
                    ToastUitl.showToastCustom("水厂暂未上架商品");
                    return;
                }
                mOrderlDialog = new OrderlDialog(mContext, mResult, mSname, new OrderlDialog.OnConfirmClickListener() {
                    @Override
                    public void onConfirm(List<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> data) {
                        mOrderlDialog.dismiss();
                        List<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> mGidList = new ArrayList<>();
                        Map<String, ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> ticketData = new HashMap<>();
                        if (mOrder_goods == null) {
                            mOrder_goods = new ArrayList<>();
                        }
                        data.addAll(mOrder_goods);
                        for (int i = 0; i < data.size(); i++) {
                            ShowPlantsBean.HarvestInfoBean.OrderGoodsBean bean = data.get(i);
                            String gid = bean.getGid();
                            if (!ticketData.containsKey(gid)) {
                                ticketData.put(gid, bean);
                            }
                        }
                        Set<String> strings = ticketData.keySet();
                        for (String key : strings) {
                            ShowPlantsBean.HarvestInfoBean.OrderGoodsBean bean = ticketData.get(key);
                            mGidList.add(bean);
                        }
                        mKeepListAdapter.setData(mGidList);
                        initFullReductionNew();
                        isFullCanUse();
                        calFullRecutionPrice();
                    }
                });
                if (!mOrderlDialog.isShowing()) {
                    mOrderlDialog.show();
                }
                break;
            case R.id.coupons:
                if (mCoupon == null) {
                    hint("没有可以使用的优惠券");
                    return;
                }
                SelectCouponsActivity.startAtc(mContext, mCoupon);
                break;
            case R.id.water_vote:
                if (useTicketBean == null || ticketNumber == 0) {
                    hint("没有可以使用的水票");
                    return;
                }
                ArrayList<TicketBean> ticketBeanList = useTicketBean.getList();
                if (ticketBeanList == null || ticketBeanList.size() == 0) {
                    hint("没有优惠可选");
                    return;
                }
                //传商品 便于对比数据
                useTicketBean.setGoodsListNew(mKeepListAdapter.getData());
                SelectWaterActivity.startAtc(mContext, -2, useTicketBean);
                break;

        }

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(AddressEvent event) {
        if (event.isDelete) {
            AddressBean data = event.data;
            if (data.getId() == addressBean.getId()) {
                mName.setText("请选择收货人地址信息");
                mPhone.setText("");
                mAdress.setText("");
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
                mAdress.setText(addressBean.getDaddress());
            }
        }
    }

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
                        mAdress.setText("");
                    } else {
                        addressBean = result.get(0);
                        mAddress_id = addressBean.getId();
                        mName.setText(addressBean.getAname());
                        mPhone.setText(addressBean.getSphone());
                        mAdress.setText(addressBean.getDaddress());
                    }

                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<AddressBean>>> call, Throwable t) {

            }
        });
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
                        mAdress.setText("");
                    } else {
                        addressBean = result.get(0);
                        mName.setText(addressBean.getAname());
                        mPhone.setText(addressBean.getSphone());
                        mAdress.setText(addressBean.getDaddress());
                    }
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<AddressBean>>> call, Throwable t) {

            }
        });
    }

    private void calculate() {
        buyData.clear();
        List<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> data = mKeepListAdapter.getData();
        total = 0.0;
        number = 0;
        boolean isAll = true;
        if (data == null) {
            return;
        }
        for (ShowPlantsBean.HarvestInfoBean.OrderGoodsBean bean : data) {
            if (bean.isCheck()) {
                isPay = true;
                if (bean.getPrice() == null) {
                    total = CalculateUtils.add(CalculateUtils.mul(Double.parseDouble(bean.getPprice()), bean.getCount()), total);
                } else {
                    total = CalculateUtils.add(CalculateUtils.mul(Double.parseDouble(bean.getPrice()), bean.getCount()), total);
                }
                number += bean.getCount();
                buyData.add(bean);
            } else {
                isAll = false;
            }
        }
        mShopCb.setChecked(isAll);

        //刚进入常购清单判断是否有满减
        if (newDataTwo.size() == 0) {
            isFullCanUse();
        }

        if (Double.doubleToLongBits(Double.parseDouble(mAmount)) != Double.doubleToLongBits(0.0) && Double.doubleToLongBits(mTicketAmount) != Double.doubleToLongBits(0.0)) {
            KLog.d("有优惠券并且有水票");
            total = CalculateUtils.sub(total, Double.parseDouble(mAmount));
            total = CalculateUtils.sub(total, mTicketAmount);
            if (Double.doubleToLongBits(total) < Double.doubleToLongBits(0.0)) {
                total = 0.0;
            }
            setTextStyle(mHintBtn, "共" + number + "件,合计", "  ￥" + total + "", "");
            KLog.d(total);
            return;
        }

        if (Double.doubleToLongBits(mTicketAmount) != Double.doubleToLongBits(0.0) && mHotCityBeanList != null) {
            KLog.d("有满减和水票");
            total = CalculateUtils.sub(total, mTicketAmount);
            //判断满不满足满减条件

            if (isFullCanUse) {
                if (mHotCityBeanList != null && mHotCityBeanList.get(0) != null) {
                    total = CalculateUtils.sub(total, Double.parseDouble(mHotCityBeanList.get(0).getReduce()));
                }
            }
            if (Double.doubleToLongBits(total) < Double.doubleToLongBits(0.0)) {
                total = 0.0;
            }
            setTextStyle(mHintBtn, "共" + number + "件,合计", "  ￥" + total + "", "");
            KLog.d(total);
            return;
        }

        //优惠券
        if (Double.doubleToLongBits(Double.parseDouble(mAmount)) == Double.doubleToLongBits(0.0)) {
            KLog.d("没有优惠券");
        } else {
            KLog.d("有优惠券");
            if (Double.doubleToLongBits(total) >= Double.doubleToLongBits(Double.parseDouble(mFull))) {
                total = CalculateUtils.sub(total, Double.parseDouble(mAmount));
                if (Double.doubleToLongBits(total) < Double.doubleToLongBits(0.0)) {
                    total = 0.0;
                }
                setTextStyle(mHintBtn, "共" + number + "件,合计", "  ￥" + total + "", "");
                KLog.d(total);
                return;
            }
        }
        //水票
        if (Double.doubleToLongBits(mTicketAmount) == Double.doubleToLongBits(0.0)) {
            KLog.d("没有水票数据");
        } else {
            KLog.d("有水票数据");
            total = CalculateUtils.sub(total, mTicketAmount);
            if (Double.doubleToLongBits(total) < Double.doubleToLongBits(0.0)) {
                total = 0.0;
            }
            setTextStyle(mHintBtn, "共" + number + "件,合计", "  ￥" + total + "", "");
            KLog.d(total);
            return;
        }

        //有满减
        if (mHotCityBeanList != null) {
            String reduce = mHotCityBeanList.get(0).getReduce();
            KLog.d(isFullCanUse);
            if (isFullCanUse) {
                total = CalculateUtils.sub(total, Double.parseDouble(reduce));
            }
            if (Double.doubleToLongBits(total) < Double.doubleToLongBits(0.0)) {
                total = 0.0;
            }
            setTextStyle(mHintBtn, "共" + number + "件,合计", "  ￥" + total + "", "");
            KLog.d(total);
            return;
        }
        setTextStyle(mHintBtn, "共" + number + "件,合计", "  ￥" + total + "", "");
        KLog.d(total);
    }

    private void isFullCanUse() {
        fullPrice = 0.0;
        if(mKeepListAdapter.getData() == null){
            return;
        }
        for (int i = 0; i < mKeepListAdapter.getData().size(); i++) {
            ShowPlantsBean.HarvestInfoBean.OrderGoodsBean bean = mKeepListAdapter.getData().get(i);
            if (bean.isCheck()) {
                if (bean.getActivity_type() == 1 || bean.getActivity_type() == 2) {
                    if (bean.getPrice() == null) {
                        fullPrice = CalculateUtils.add(CalculateUtils.mul(Double.valueOf(bean.getPprice()), bean.getCount()), fullPrice);
//                        fullPrice = CalculateUtils.mul(Double.valueOf(bean.getPprice()), bean.getCount());
                    } else {
                        fullPrice = CalculateUtils.add(CalculateUtils.mul(Double.valueOf(bean.getPrice()), bean.getCount()), fullPrice);
//                        fullPrice = CalculateUtils.mul(Double.valueOf(bean.getPrice()), bean.getCount());
                    }
                }
            }
        }
        KLog.d(fullPrice);
        if (mHotCityBeanList != null && mHotCityBeanList.get(0) != null) {
            if (CalculateUtils.sub(fullPrice, Double.parseDouble(mHotCityBeanList.get(0).getFull())) >= 0) {
                isFullCanUse = true;
            } else {
                isFullCanUse = false;
            }
        } else {
            isFullCanUse = false;
        }
    }

    //计算价格（商品的总价减去满减的价格）
    public void calFullRecutionPrice() {
        total = 0.0;
        number = 0;
        boolean isAll = true;
        buyData.clear();
        for (int i = 0; i < mKeepListAdapter.getData().size(); i++) {
            ShowPlantsBean.HarvestInfoBean.OrderGoodsBean bean = mKeepListAdapter.getData().get(i);
            if (bean.isCheck()) {
                if (bean.getPrice() == null) {
                    total = CalculateUtils.add(CalculateUtils.mul(Double.parseDouble(bean.getPprice()), bean.getCount()), total);
                } else {
                    total = CalculateUtils.add(CalculateUtils.mul(Double.parseDouble(bean.getPrice()), bean.getCount()), total);
                }
                number += bean.getCount();
                buyData.add(bean);
            } else {
                isAll = false;
            }
        }
        KLog.d(mHotCityBeanList != null);
        if (mHotCityBeanList != null && mHotCityBeanList.get(0) != null) {
            KLog.d(fullPrice + "   " + Double.parseDouble(mHotCityBeanList.get(0).getFull()));
            if (CalculateUtils.sub(fullPrice, Double.parseDouble(mHotCityBeanList.get(0).getFull())) >= 0) {
                isFullCanUse = true;
                mMoney.setVisibility(View.VISIBLE);
                mTopUp.setVisibility(View.VISIBLE);
                initFullReductionNew();
            } else {
                isFullCanUse = false;
                mMoney.setText("无可参加活动");
                mMoney.setTextColor(mContext.getResources().getColor(R.color.color_ababab));
                mTopUp.setVisibility(View.GONE);
            }
        }
        if (mActiveBean != null) {
            if (CalculateUtils.sub(fullPrice, Double.parseDouble(mActiveBean.getFull())) >= 0) {
                mMoney.setVisibility(View.VISIBLE);
                mTopUp.setVisibility(View.VISIBLE);
                initFullReductionNew();
            } else {
                mMoney.setText("无可参加活动");
                mMoney.setTextColor(mContext.getResources().getColor(R.color.color_ababab));
                mTopUp.setVisibility(View.GONE);
            }
        }
        mShopCb.setChecked(isAll);
        KLog.d(total);
        KLog.d(isFullCanUse);
        if (mHotCityBeanList != null) {
            String reduce = mHotCityBeanList.get(0).getReduce();
            if (isFullCanUse) {
                total = CalculateUtils.sub(total, Double.parseDouble(reduce));
            }
            if (Double.doubleToLongBits(total) < Double.doubleToLongBits(0.0)) {
                total = 0.0;
            }
            KLog.d(total);
            setTextStyle(mHintBtn, "共" + number + "件,合计", "  ￥" + total + "", "");
        } else {
            setTextStyle(mHintBtn, "共" + number + "件,合计", "  ￥" + total + "", "");
        }
        if (mTicketEvent!=null){
            mTicketEvent = null;
        }
        mAmount = "0.00";
        mTicketAmount = 0.00;
        if (mC_id!=0){
            mC_id = 0;
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
            KLog.d(type);
            mAmount = msg.getAmount();
            mFull = msg.getFull();
            mC_id = msg.getId();
//            String goods_id = msg.getGoods_id();
//            String[] split = goods_id.split(",");
//            List<String> list = Arrays.asList(split);
//            KLog.d(list.size());
//            List<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> data = mKeepListAdapter.getData();
//            for (int i = 0; i < data.size(); i++) {
//                String gid = data.get(i).getGid();
//                int count = data.get(i).getCount();
//                String price = data.get(i).getPrice();
//                for (int j = 0; j < list.size(); j++) {
//                    String s = list.get(j);
//                    if (s.equals(gid)) {
//                        double mul = CalculateUtils.mul(Double.parseDouble(price), count);
//                        //如果优惠券的价格大于指定商品的价格，那么优惠的钱就是商品的钱
//                        if (Double.parseDouble(mAmount) > mul) {
//                            mAmount = mul + "";
//                        }
//                    }
//                }
//            }
            mCouText.setText("已选" + mAmount + "元优惠券");
            mCouText.setTextColor(getResources().getColor(R.color.color_ababab));
            mCouText.setBackgroundResource(R.color.white);
            mMoney.setVisibility(View.VISIBLE);
            mMoney.setText("活动不与优惠券同享");
            mMoney.setTextColor(getResources().getColor(R.color.color_ababab));
            mTopUp.setVisibility(View.GONE);
            calculate();
        } else {
            if (mCoupon.getList() == null) {
                return;
            }
            List<ShowCouponBean.ListBean> list = mCoupon.getList();
            if (list != null && list.size() > 0) {
                mCouText.setTextColor(mContext.getResources().getColor(R.color.white));
                mCouText.setText(list.size() + "张可以使用");
                mCouText.setBackgroundResource(R.drawable.shuipiao_bg);
            }
            mC_id = 0;
            mAmount = "0.0";
            if (mHotCityBeanList != null) {
                mMoney.setText("— ￥" + mHotCityBeanList.get(0).getReduce());
                mMoney.setTextColor(getResources().getColor(R.color.color_A53533));
            }
            calculate();
        }
    }

    //水票
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(TicketEvent event) {
        KLog.d("TicketEvent");
        Map<String, ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> ticketData = new HashMap<>();
        newData.clear();
        newDataTwo.clear();
        fullPrice = 0.0;
        totalTicketNumber = 0;
        isFromWaterTicket = true;
        mTicketEvent = event;
        mTicketAmount = event.amount;
        mWaterVoteText.setTextColor(getResources().getColor(R.color.color_ababab));
        setNumStyle(mWaterVoteText, "你已选", "" + event.count, "张水票，可抵", "" + event.amount, "元");
        mWaterVoteText.setBackgroundResource(R.color.white);
        Gson gson = new Gson();
        mSelectWaterData = gson.fromJson(event.data, new TypeToken<List<TicketBean>>() {
        }.getType());
        KLog.d(event.data);
        List<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> data = mKeepListAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            int count = data.get(i).getCount();
            String gid = data.get(i).getGid();
            int did = data.get(i).getDid();
            int activity_type = data.get(i).getActivity_type();
            String pprice = data.get(i).getPprice();
            String price = data.get(i).getPrice();
            for (int j = 0; j < mSelectWaterData.size(); j++) {
                String gid1 = mSelectWaterData.get(j).getGid();
//                int count1 = mSelectWaterData.get(j).getNum();
                if (gid.equals(gid1)) {
                    for (int k = 0; k < mSelectWaterData.size(); k++) {
                        int num = mSelectWaterData.get(k).getNum();
                        totalTicketNumber += num;
                    }
                    int newCount = count - totalTicketNumber;
                    ShowPlantsBean.HarvestInfoBean.OrderGoodsBean goodsBean = new ShowPlantsBean.HarvestInfoBean.OrderGoodsBean();
                    goodsBean.setCount(newCount);
                    goodsBean.setDid(did);
                    goodsBean.setGid(gid);
                    goodsBean.setActivity_type(activity_type);
                    goodsBean.setPprice(pprice);
                    goodsBean.setPrice(price);
                    newData.add(goodsBean);
                }
            }
        }
        newData.addAll(data);
        for (int i = 0; i < newData.size(); i++) {
            ShowPlantsBean.HarvestInfoBean.OrderGoodsBean bean = newData.get(i);
            String gid = bean.getGid();
            if (!ticketData.containsKey(gid)) {
                ticketData.put(gid, bean);
            }
        }
        Set<String> strings = ticketData.keySet();
        for (String key : strings) {
            ShowPlantsBean.HarvestInfoBean.OrderGoodsBean bean = ticketData.get(key);
            newDataTwo.add(bean);
        }
        KLog.d(new Gson().toJson(newDataTwo));

        for (int i = 0; i < newDataTwo.size(); i++) {
            ShowPlantsBean.HarvestInfoBean.OrderGoodsBean bean = newDataTwo.get(i);
            if (bean.isCheck()){
                if (bean.getActivity_type() == 1 || bean.getActivity_type() == 2) {
                    if (bean.getPrice() == null) {
                        fullPrice = CalculateUtils.add(CalculateUtils.mul(Double.valueOf(bean.getPprice()), bean.getCount()), fullPrice);
//                    fullPrice = CalculateUtils.mul(Double.valueOf(bean.getPprice()), bean.getCount());
                    } else {
                        fullPrice = CalculateUtils.add(CalculateUtils.mul(Double.valueOf(bean.getPrice()), bean.getCount()), fullPrice);
//                    fullPrice = CalculateUtils.mul(Double.valueOf(bean.getPrice()), bean.getCount());
                    }
                }
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
                    mMoney.setText("— ￥" + mHotCityBeanList.get(0).getReduce());
                    mMoney.setTextColor(getResources().getColor(R.color.common_red));
                }
            } else {
                isFullCanUse = false;
                mTopUp.setVisibility(View.GONE);
                mMoney.setVisibility(View.VISIBLE);
                mMoney.setText("无可参加活动");
                mMoney.setTextColor(getResources().getColor(R.color.color_ababab));
            }
        }
        if (mActiveBean != null) {
            KLog.d("mActiveBean!=null");
            if (CalculateUtils.sub(fullPrice, Double.parseDouble(mActiveBean.getFull())) >= 0) {
                mMoney.setVisibility(View.VISIBLE);
                mTopUp.setVisibility(View.VISIBLE);
                initFullReductionNew();
            } else {
                mMoney.setText("无可参加活动");
                mMoney.setTextColor(mContext.getResources().getColor(R.color.color_ababab));
                mTopUp.setVisibility(View.GONE);
            }
        }
        mAmount = "0.0";
        mC_id = 0;
        if (event.count == 0) {
            isFullCanUse();
            calFullRecutionPrice();
        } else {
            calculate();
        }
        getcoupons();
    }

    private void setNumStyle(TextView text, String first, String count, String second, String amount, String third) {
        SpannableString spanString = new SpannableString(first + count + second + amount + third);
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.common_red)), first.length(), (count + first).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.common_red)), (count + first + second).length(), (count + first + second + amount).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        text.setText(spanString);
    }

    private void setTextStyle(TextView text, String first, String content, String second) {
        SpannableString spanString = new SpannableString(first + content + second);
        spanString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.common_red)), first.length(), (content + first).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        spanString.setSpan(new AbsoluteSizeSpan(16, true), first.length(), (content + first).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);//字体大小
        text.setText(spanString);
    }

    private void hint(String text) {
        ToastUitl.showToastCustom(text);
    }
}
