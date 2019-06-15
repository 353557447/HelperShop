package com.shuiwangzhijia.shuidian.newmodule.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.google.gson.Gson;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.shuiwangzhijia.shuidian.bean.RechargeCenterInfoBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.activity.MyReturnMoneyNewActivity;
import com.shuiwangzhijia.shuidian.newmodule.activity.MyWalletNewActivity;
import com.shuiwangzhijia.shuidian.newmodule.activity.RechargeCenterNewActivity;
import com.shuiwangzhijia.shuidian.ui.LoginActivity;
import com.shuiwangzhijia.shuidian.utils.CalculateUtils;
import com.shuiwangzhijia.shuidian.utils.MeasureUtil;
import com.socks.library.KLog;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.BaseFmAdapter;
import com.shuiwangzhijia.shuidian.base.BaseLazyFragment;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.GoodsBean;
import com.shuiwangzhijia.shuidian.event.GoodsEvent;
import com.shuiwangzhijia.shuidian.fragment.PurchaseBaseFragment;
import com.shuiwangzhijia.shuidian.network.OkgoRequest;
import com.shuiwangzhijia.shuidian.network.OkgoUrl;
import com.shuiwangzhijia.shuidian.newmodule.activity.MyWalletActivity;
import com.shuiwangzhijia.shuidian.newmodule.activity.RechargeCenterActivity;
import com.shuiwangzhijia.shuidian.newmodule.activity.ReturnMoneyPolicyActivity;
import com.shuiwangzhijia.shuidian.newmodule.adapter.FeaturedContentAdapter;
import com.shuiwangzhijia.shuidian.newmodule.adapter.FeaturedTypeAdapter;
import com.shuiwangzhijia.shuidian.newmodule.bean.BannerBean;
import com.shuiwangzhijia.shuidian.newmodule.bean.FeatureGoodsBean;
import com.shuiwangzhijia.shuidian.newmodule.bean.FeaturedGoodsTypeBean;
import com.shuiwangzhijia.shuidian.ui.CenterActivity;
import com.shuiwangzhijia.shuidian.ui.KeepListActivity;
import com.shuiwangzhijia.shuidian.ui.PurchaseOrderActivity;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.GridDividerItemDecoration;
import com.shuiwangzhijia.shuidian.utils.WaterHelperUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 */
@FndViewInject(contentViewId = R.layout.fragment_featured)
public class FeaturedFragment extends BaseLazyFragment implements FeaturedTypeAdapter.OnGooodsTypeClickListener, FeaturedContentAdapter.OnJoinShoppingCartClickListener {
    @BindView(R.id.bg_color)
    ImageView mBgColor;
    @BindView(R.id.cb)
    ConvenientBanner mCb;
    @BindView(R.id.water_factory_name)
    TextView mWaterFactoryName;
    @BindView(R.id.contact)
    ImageView mContact;
    @BindView(R.id.water_factory_policy)
    LinearLayout mWaterFactoryPolicy;
    @BindView(R.id.regular_list)
    LinearLayout mRegularList;
    @BindView(R.id.my_wallet)
    LinearLayout mMyWallet;
    @BindView(R.id.purchase_order)
    LinearLayout mPurchaseOrder;
    @BindView(R.id.youhuichongzhi)
    ImageView mYouhuichongzhi;
    @BindView(R.id.act_info_tv)
    TextView mActInfoTv;
    @BindView(R.id.recharge_immediately)
    TextView mRechargeImmediately;
    @BindView(R.id.featured_middle_iv)
    ImageView mFeaturedMiddleIv;
    @BindView(R.id.type_rv)
    RecyclerView mTypeRv;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.content_rv)
    RecyclerView mContentRv;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    private List<BannerBean.DataBean.IndexBean> mIndex;
    private List<BannerBean.DataBean.ActivityBean> mActivity;
    private FeaturedTypeAdapter mFeaturedTypeAdapter;
    private List<FeaturedGoodsTypeBean.DataBean> mGoodsTypeList;
    private FeaturedContentAdapter mFeaturedContentAdapter;
    private List<FeatureGoodsBean.DataBean> mFeatureGoodsList;
    private BaseFmAdapter adapter;
    private List<String> titles = new ArrayList<>();
    private ArrayList<Object> pageList = new ArrayList<>();

    public FeaturedFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView(View view) {
        initTypeRv();
    }

    private void initTypeRv() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFeaturedTypeAdapter = new FeaturedTypeAdapter(mContext);
        mGoodsTypeList = new ArrayList<>();
        mFeaturedTypeAdapter.setData(mGoodsTypeList);
        mFeaturedTypeAdapter.setOnItemClickListener(this);
        mTypeRv.addItemDecoration(new GridDividerItemDecoration(20, getResources().getColor(R.color.transparent)));
        mTypeRv.setLayoutManager(manager);
        mTypeRv.setAdapter(mFeaturedTypeAdapter);



        adapter = new BaseFmAdapter(getChildFragmentManager(), pageList, titles);
        mViewpager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewpager);
    }



    @Override
    protected void initData() {
        super.initData();
        getBannerData();
        getRechargeAct();
    }

    @Override
    public void onResume() {
        super.onResume();
        // item_feature_goods
        WaterHelperUtils.updateRedPointCounts();
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        getBannerData();
    }

    private void getBannerData() {
        Map<String, String> params = new HashMap<>();
        OkgoRequest.doPostWithToken(mContext, params, OkgoUrl.GET_BANNER, new StringCallback() {
            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);
                showLoadingDialog();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismissLoadingDialog();
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
            }

            @Override
            public void onSuccess(Response<String> response) {
                String datas = response.body().toString();
                KLog.e(datas);
                try {
                    JSONObject object = new JSONObject(datas);
                    if (object.getInt("code") == 200) {
                        BannerBean bannerBean = mGson.fromJson(datas, BannerBean.class);
                        mIndex = bannerBean.getData().getIndex();
                        mActivity = bannerBean.getData().getActivity();
                        initBanner();
                        getGoodsType();
                    } else {

                    }
                    if("您无权访问".equals(object.getString("msg"))){
                        skipActivity(LoginActivity.class);
                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                }
            }
        });
    }

    private void initBanner() {
        mCb.setPages(new CBViewHolderCreator() {
            @Override
            public ImageViewHolder createHolder(View itemView) {
                return new ImageViewHolder(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.banner;
            }

        }, mIndex)
                //设置指示器是否可见
                .setPointViewVisible(true)
                //设置自动切换（同时设置了切换时间间隔）
                .startTurning(2000)
                .setPageIndicator(new int[]{R.drawable.banner_unselected, R.drawable.banner_selected}) //设置两个点作为指示器
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL); //设置指示器的方向水平  居中
    }

    private void getGoodsType() {
        Map<String, String> params = new HashMap<>();
        OkgoRequest.doPostWithToken(mContext, params, OkgoUrl.GET_GOODS_TYPE, new StringCallback() {
            @Override
            public void onError(Response<String> response) {
                super.onError(response);
            }

            @Override
            public void onSuccess(Response<String> response) {
                String datas = response.body().toString();
                KLog.e(datas);
                try {
                    JSONObject object = new JSONObject(datas);
                    if (object.getInt("code") == 200) {
                        FeaturedGoodsTypeBean featuredGoodsTypeBean = mGson.fromJson(datas, FeaturedGoodsTypeBean.class);
                        List<FeaturedGoodsTypeBean.DataBean> data = featuredGoodsTypeBean.getData();
                        mGoodsTypeList.clear();
                        mGoodsTypeList.addAll(data);
                        mFeaturedTypeAdapter.notifyDataSetChanged();
                        //getGoodsTypeContent(mGoodsTypeList.get(mFeaturedTypeAdapter.getSelectedItem()).getGtype());
                        titles.clear();
                        pageList.clear();
                        for (FeaturedGoodsTypeBean.DataBean bean: mGoodsTypeList) {
                            titles.add(bean.getT_name());
                            pageList.add(setFragment(bean.getGtype()));
                        }
                 /*       titles.add("未知");
                        pageList.add(setFragment("5"));
                        titles.add("未知");
                        pageList.add(setFragment("5"));
                        titles.add("未知");
                        pageList.add(setFragment("5"));*/
                        adapter.notifyDataSetChanged();

                    } else {

                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                }
            }
        });
    }

    private Fragment setFragment(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        PurchaseBaseFragment orderBaseFragment = new PurchaseBaseFragment();
        orderBaseFragment.setArguments(bundle);
        return orderBaseFragment;
    }

    private void getGoodsTypeContent(String gType) {
        Map<String, String> params = new HashMap<>();
        params.put("gtype", gType);
        params.put("start", mPage + "");
        params.put("limit", mLimit + "");
        OkgoRequest.doPostWithToken(mContext, params, OkgoUrl.GET_GOODS_LIST, new StringCallback() {
            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                KLog.e("onError");
            }

            @Override
            public void onSuccess(Response<String> response) {
                String datas = response.body().toString();
                KLog.e(datas);
                try {
                    JSONObject object = new JSONObject(datas);
                    if (object.getInt("code") == 200) {
                        FeatureGoodsBean featureGoodsBean = mGson.fromJson(datas, FeatureGoodsBean.class);
                        List<FeatureGoodsBean.DataBean> data = featureGoodsBean.getData();
                        if (!isLoadMore) {
                            mFeatureGoodsList.clear();
                        }
                        mFeatureGoodsList.addAll(data);
                        mFeaturedContentAdapter.notifyDataSetChanged();
                    } else {

                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                }
            }
        });
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected void initEvent() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float scale = 1.0f - (-verticalOffset) / appBarLayout.getHeight();
                if (mCb.getHeight() + verticalOffset > mToolBar.getHeight()) {
//                    toolBar.setAlpha(1);
//                    llSearch.setSelected(false);
                } else if (appBarLayout.getHeight() + verticalOffset < 400) {
//                    toolBar.setAlpha(1);
//                    llSearch.setSelected(true);
                } else {
//                    toolBar.setAlpha(0);
//                    llSearch.setSelected(false);
                }

                mToolBar.setBackgroundColor(changeAlpha(getResources().getColor(R.color.cFF014E), Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange()));
            }
        });
    }

    /**
     * 根据百分比改变颜色透明度
     */
    public int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

    @Override
    public void onGoodsTypeItemClick(int position) {
        mFeaturedTypeAdapter.setSelectedItem(position);
        mFeaturedTypeAdapter.notifyDataSetChanged();
        isLoadMore = false;
        //getGoodsTypeContent(mGoodsTypeList.get(position).getGtype());
    }

    @Override
    public void onJoinShoppingCart(int position) {
        //添加到购物车
        List<GoodsBean> shopCart = CommonUtils.getShopCart();
        if (shopCart == null) {
            shopCart = new ArrayList<>();
        }
        GoodsBean item = mGson.fromJson(mGson.toJson(mFeatureGoodsList.get(position)), GoodsBean.class);
        item.setCount(item.getLeast_p());
        GoodsBean old = null;
        for (int i = 0; i < shopCart.size(); i++) {
            old = shopCart.get(i);
            if (old.getGid().equals(item.getGid())) {
                item.setCount(old.getCount() + item.getCount());
                shopCart.remove(old);
            }
        }
        shopCart.add(item);
        Gson gson = new Gson();
        KLog.e(gson.toJson(item));
        CommonUtils.saveShopCart(shopCart);
        KLog.e(gson.toJson(shopCart));
        EventBus.getDefault().post(new GoodsEvent(shopCart.size(), item.getPicturl()));
        showToast("加入购物车成功");
        WaterHelperUtils.updateRedPointCounts();
    }


    @OnClick({R.id.water_factory_policy, R.id.regular_list, //
            R.id.my_wallet, R.id.purchase_order, R.id.recharge_immediately,//
            R.id.featured_middle_iv
    })
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.water_factory_policy:
      /*          bundle = new Bundle();
                bundle.putInt("did", Integer.parseInt(CommonUtils.getDid()));
                bundle.putString("waterFactoryName", "九重岩水厂");
                skipActivity(ReturnMoneyPolicyActivity.class, bundle);*/
                skipActivity(MyReturnMoneyNewActivity.class);
                break;
            case R.id.regular_list:
                skipActivity(KeepListActivity.class);
                break;
            case R.id.my_wallet:
                skipActivity(MyWalletNewActivity.class);
                break;
            case R.id.purchase_order:
                PurchaseOrderActivity.statAct(mContext, 0);
                break;
            case R.id.recharge_immediately:
                skipActivity(RechargeCenterNewActivity.class);
                break;
            case R.id.featured_middle_iv:
                CenterActivity.startAct(mContext, 0);
                break;

        }
    }


    private void getRechargeAct() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getRechargeCenterList(CommonUtils.getToken()).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int scode = object.getInt("scode");
                    if (scode == 200) {
                        RechargeCenterInfoBean rechargeCenterInfoBean = mGson.fromJson(datas, RechargeCenterInfoBean.class);
                        List<RechargeCenterInfoBean.DataBean.ListBean> list = rechargeCenterInfoBean.getData().getList();

                        List<RechargeCenterInfoBean.DataBean.ListBean.RechargeBean> recharge = list.get(0).getRecharge();
                        String regulation="";
                        if (recharge != null && recharge.size() != 0) {
                            for (int i = 0; i < recharge.size(); i++) {
                                RechargeCenterInfoBean.DataBean.ListBean.RechargeBean rechargeBean = recharge.get(0);
                                String sailAmount = rechargeBean.getSail_amount();
                                String ramount = rechargeBean.getRamount();

                                double sailAmountInt = Double.parseDouble(sailAmount);
                                double ramountInt = Double.parseDouble(ramount);
                                double gift = CalculateUtils.sub(ramountInt,sailAmountInt);
                                regulation+= "充" + sailAmountInt + "赠" + gift;
                            }
                              mActInfoTv.setText(regulation);
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
                KLog.e(t.getMessage());
                dismissLoadingDialog();
            }
        });
    }




    public class ImageViewHolder extends Holder<BannerBean.DataBean.IndexBean> {
        private ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            imageView = itemView.findViewById(R.id.ivPost);
        }

        @Override
        public void updateUI(BannerBean.DataBean.IndexBean data) {
            KLog.e(OkgoUrl.BASE_URL + "banner/" + data.getPicturl());
            if (imageView == null)
                return;
            if (Util.isOnMainThread()) {
                Glide.with(mContext).load(OkgoUrl.BASE_URL + "banner/" + data.getPicturl()).placeholder(R.color.c30adfd).into(imageView);
            }
        }
    }
}
