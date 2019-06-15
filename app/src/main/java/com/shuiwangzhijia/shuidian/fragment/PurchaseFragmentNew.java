package com.shuiwangzhijia.shuidian.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.androidkun.xtablayout.XTabLayout;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bigkoo.convenientbanner.listener.OnPageChangeListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.BaseFmAdapter;
import com.shuiwangzhijia.shuidian.base.BaseFragment;
import com.shuiwangzhijia.shuidian.base.Constant;
import com.shuiwangzhijia.shuidian.bean.ActivityBean;
import com.shuiwangzhijia.shuidian.bean.BannerBeanNew;
import com.shuiwangzhijia.shuidian.bean.GoodsTypeBean;
import com.shuiwangzhijia.shuidian.bean.IndexBean;
import com.shuiwangzhijia.shuidian.event.CommonEvent;
import com.shuiwangzhijia.shuidian.event.LoginOutEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.activity.MyWalletActivity;
import com.shuiwangzhijia.shuidian.newmodule.activity.RechargeCenterActivity;
import com.shuiwangzhijia.shuidian.ui.CenterActivity;
import com.shuiwangzhijia.shuidian.ui.KeepListActivity;
import com.shuiwangzhijia.shuidian.ui.PurchaseOrderActivity;
import com.shuiwangzhijia.shuidian.ui.WebActivity;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseFragmentNew extends BaseFragment {
    @BindView(R.id.tabLayout)
    XTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.mCb)
    ConvenientBanner mCb;
    @BindView(R.id.recommended)
    LinearLayout recommended;
    @BindView(R.id.rlEmpty)
    RelativeLayout rlEmpty;
    @BindView(R.id.shop)
    LinearLayout mShop;
    @BindView(R.id.good_shop)
    LinearLayout mGoodShop;
    @BindView(R.id.tenants)
    LinearLayout mTenants;
    @BindView(R.id.activity_center)
    RelativeLayout mActivityCenter;
    @BindView(R.id.keep_list)
    RelativeLayout mKeepList;
    @BindView(R.id.mCb_two)
    ConvenientBanner mMCbTwo;
    @BindView(R.id.bg_color)
    ImageView mBgColor;
    @BindView(R.id.mAppBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.image)
    CircleImageView mImage;
    private Unbinder unbinder;
    private ArrayList<IndexBean> images;
    private BaseFmAdapter adapter;
    private ArrayList<Object> pageList = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private List<IndexBean> mIndex;
    private List<ActivityBean> mActivity;
    private String mBackground_color;
    private List<ActivityBean> mImagesTwo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_purchase_new, container, false);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        }
        unbinder = ButterKnife.bind(this, mRootView);
        adapter = new BaseFmAdapter(getChildFragmentManager(), pageList, titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        KLog.e("我执行了么");
        getGoodsType();
        getBannerList();
        initListener();
        String headPic = CommonUtils.getHeadPic();
        KLog.d(headPic);
        Glide.with(mContext).load(Constant.SHOP_IMAGE_URL + headPic).placeholder(R.drawable.morentouxiang).into(mImage);
        return mRootView;
    }

    private void initListener() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float scale = 1.0f - (-verticalOffset) / appBarLayout.getHeight();
                if (mCb.getHeight() + verticalOffset > toolBar.getHeight()) {
//                    toolBar.setAlpha(1);
//                    llSearch.setSelected(false);
                } else if (appBarLayout.getHeight() + verticalOffset < 400) {
//                    toolBar.setAlpha(1);
//                    llSearch.setSelected(true);
                } else {
//                    toolBar.setAlpha(0);
//                    llSearch.setSelected(false);
                }

                toolBar.setBackgroundColor(changeAlpha(getResources().getColor(R.color.color_505E84), Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange()));

                Log.i("appbar", "appBar verticalOffset:" + verticalOffset + " Height:" + appBarLayout.getHeight() + " toolBar Height:" + toolBar.getHeight() + " scale:" + scale);

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

    private void getGoodsType() {
        showLoad();
        RetrofitUtils.getInstances().create().getGoodsType().enqueue(new Callback<EntityObject<ArrayList<GoodsTypeBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<GoodsTypeBean>>> call, Response<EntityObject<ArrayList<GoodsTypeBean>>> response) {
                hintLoad();
                EntityObject<ArrayList<GoodsTypeBean>> body = response.body();
                if (body.getCode() == 200) {
                    ArrayList<GoodsTypeBean> result = body.getResult();
                    if (result == null || result.size() == 0) {
//                        llContent.setVisibility(View.GONE);
                        rlEmpty.setVisibility(View.VISIBLE);
                        return;
                    } else {
//                        llContent.setVisibility(View.VISIBLE);
                        rlEmpty.setVisibility(View.GONE);
                    }
                    titles.clear();
                    pageList.clear();
                    for (GoodsTypeBean bean : result) {
                        titles.add(bean.getT_name());
                        pageList.add(setFragment(bean.getGtype()));
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    if (body.getScode() == -200) {
                        EventBus.getDefault().post(new LoginOutEvent());
                    } else {
//                        llContent.setVisibility(View.GONE);
                        rlEmpty.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<GoodsTypeBean>>> call, Throwable t) {
                Log.i("json", t.getMessage());
//                llContent.setVisibility(View.GONE);
                rlEmpty.setVisibility(View.VISIBLE);
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

    private void getBannerList() {
        RetrofitUtils.getInstances().create().getBannerList().enqueue(new Callback<EntityObject<BannerBeanNew>>() {
            @Override
            public void onResponse(Call<EntityObject<BannerBeanNew>> call, Response<EntityObject<BannerBeanNew>> response) {
                EntityObject<BannerBeanNew> body = response.body();
                try {
                    if (body.getCode() == 200) {
                        BannerBeanNew result = body.getResult();
                        images = (ArrayList<IndexBean>) result.getIndex();
                        mImagesTwo = result.getActivity();
                        mIndex = result.getIndex();
                        mActivity = result.getActivity();
                        if (mIndex == null || mIndex.size() == 0) {
                            return;
                        }
                        if (mActivity == null || mActivity.size() == 0) {
                            mMCbTwo.setVisibility(View.GONE);
                        }
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
                                .setPageIndicator(new int[]{R.drawable.banner_gray, R.drawable.banner_blue}) //设置两个点作为指示器
                                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL); //设置指示器的方向水平  居中
                        mCb.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                //水店端0不跳转,1活动页,2充值中心,3自定义图文,4个人中心,5我的钱包,6跳转外部链接
                                IndexBean bannerBean = images.get(position);
                                KLog.d(bannerBean.getUrl_type());
                                switch (bannerBean.getUrl_type()) {
                                    case 0:
                                        break;
                                    case 1:
                                        CenterActivity.startAct(mContext, 0);
                                        break;
                                    case 2:
                                        skipActivity(RechargeCenterActivity.class);
                                        break;
                                    case 3:
                                        KLog.d(bannerBean.getDetail());
                                        WebActivity.startAct(getContext(), bannerBean.getDetail(), "图文详情", 3);
                                        break;
                                    case 4:
                                        EventBus.getDefault().post(new CommonEvent("go_my"));
                                        break;
                                    case 5:
                                        startActivity(new Intent(getActivity(), MyWalletActivity.class));
                                        break;
                                    case 6:
                                        WebActivity.startAct(getContext(), bannerBean.getUrl_addr(), "详情", 4);
                                        break;

                                }

                            }
                        });

                        mCb.setOnPageChangeListener(new OnPageChangeListener() {
                            @Override
                            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            }

                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            }

                            @Override
                            public void onPageSelected(int index) {
                                mBackground_color = mIndex.get(index).getBackground_color();
                                try {
                                    if (mBackground_color != null)
                                        mBgColor.setBackgroundColor(Color.parseColor(mBackground_color));
                                    else
                                        mBgColor.setBackgroundColor(Color.parseColor("#047AFF"));
                                } catch (Exception e) {

                                }
//                            String background_img = mIndex.get(index).getBackground_img();
//                            Glide.with(mContext).load(Constant.BANNER_IMAGE_URL + background_img).placeholder(R.drawable.wutupian).into(mBgColor);
                            }
                        });

                        mMCbTwo.setPages(new CBViewHolderCreator() {
                            @Override
                            public ImageViewHolderTwo createHolder(View itemView) {
                                return new ImageViewHolderTwo(itemView);
                            }

                            @Override
                            public int getLayoutId() {
                                return R.layout.banner;
                            }

                        }, mActivity)
                                //设置指示器是否可见
                                .setPointViewVisible(true)
                                .setPageIndicator(new int[]{R.drawable.banner_unselected, R.drawable.banner_selected}) //设置两个点作为指示器
                                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL); //设置指示器的方向水平  居中
                        mMCbTwo.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                ActivityBean bannerBean = mImagesTwo.get(position);
                                switch (bannerBean.getUrl_type()) {
                                    case 0:
                                        break;
                                    case 1:
                                        CenterActivity.startAct(mContext, 0);
                                        break;
                                    case 2:
                                        skipActivity(RechargeCenterActivity.class);
                                        break;
                                    case 3:
                                        KLog.d(bannerBean.getDetail());
                                        WebActivity.startAct(getContext(), bannerBean.getDetail(), "图文详情", 3);
                                        break;
                                    case 4:
                                        EventBus.getDefault().post(new CommonEvent("go_my"));
                                        break;
                                    case 5:
                                        startActivity(new Intent(getActivity(), MyWalletActivity.class));
                                        break;
                                    case 6:
                                        WebActivity.startAct(getContext(), bannerBean.getUrl_addr(), "详情", 6);
                                        break;

                                }
                            }
                        });
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<EntityObject<BannerBeanNew>> call, Throwable t) {

            }
        });
    }

    @OnClick({R.id.recommended, R.id.shop, R.id.good_shop, R.id.tenants, R.id.activity_center, R.id.keep_list, R.id.image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image:
                EventBus.getDefault().post(new CommonEvent("go_my"));
                break;
            case R.id.recommended:
//                skipActivity(AddGoodsActivity.class);
//                skipActivity(MyCouActivity.class);
//                skipActivity(TicketActivity.class);
                skipActivity(RechargeCenterActivity.class);
                break;
            case R.id.shop:
                CenterActivity.startAct(mContext, 1);
                break;
            case R.id.good_shop:
                startActivity(new Intent(getActivity(), MyWalletActivity.class));
                break;
            case R.id.tenants:
                PurchaseOrderActivity.statAct(mContext, 0);
                break;
            case R.id.activity_center:
                CenterActivity.startAct(mContext, 0);
                break;
            case R.id.keep_list:
                skipActivity(KeepListActivity.class);
                break;
        }
    }

    public class ImageViewHolder extends Holder<IndexBean> {
        private ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            imageView = itemView.findViewById(R.id.ivPost);
        }

        @Override
        public void updateUI(IndexBean data) {
            KLog.e(Constant.BANNER_IMAGE_URL + data.getPicturl());
            if (imageView == null)
                return;
            if (Util.isOnMainThread() && getActivity() != null) {
                Glide.with(getActivity()).load(Constant.BANNER_IMAGE_URL + data.getPicturl()).placeholder(R.color.textRed).error(R.mipmap.logo).into(imageView);
            }
        }
    }

    public class ImageViewHolderTwo extends Holder<ActivityBean> {
        private ImageView imageView;

        public ImageViewHolderTwo(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            imageView = itemView.findViewById(R.id.ivPost);
        }

        @Override
        public void updateUI(ActivityBean data) {
            if (imageView == null)
                return;
            if (Util.isOnMainThread() && getActivity() != null) {
                Glide.with(getActivity()).load(Constant.BANNER_IMAGE_URL + data.getPicturl()).placeholder(R.color.color_30adfd).error(R.mipmap.logo).into(imageView);
            }
        }
    }

}
