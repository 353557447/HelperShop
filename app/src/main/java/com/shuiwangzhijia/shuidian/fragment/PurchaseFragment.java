package com.shuiwangzhijia.shuidian.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.socks.library.KLog;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.BaseFmAdapter;
import com.shuiwangzhijia.shuidian.base.BaseFragment;
import com.shuiwangzhijia.shuidian.base.Constant;
import com.shuiwangzhijia.shuidian.bean.BannerBean;
import com.shuiwangzhijia.shuidian.bean.GoodsBean;
import com.shuiwangzhijia.shuidian.bean.GoodsTypeBean;
import com.shuiwangzhijia.shuidian.event.GoodsEvent;
import com.shuiwangzhijia.shuidian.event.LoginOutEvent;
import com.shuiwangzhijia.shuidian.event.MainEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.DensityUtils;
import com.shuiwangzhijia.shuidian.utils.GlideCircleTransform;
import com.shuiwangzhijia.shuidian.utils.PreferenceUtils;
import com.shuiwangzhijia.shuidian.view.RxFakeAddImageView;
import com.shuiwangzhijia.shuidian.view.RxPointFTypeEvaluator;

import java.util.ArrayList;
import java.util.List;

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


/**
 * 采购页面
 * created by wangsuli on 2018/8/17.
 */
public class PurchaseFragment extends BaseFragment {
    @BindView(R.id.mCb)
    ConvenientBanner mCb;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.rlNum)
    RelativeLayout rlNum;
    @BindView(R.id.shopping_cart)
    ImageView shoppingCart;
    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;
    @BindView(R.id.titleBar)
    TextView titleBar;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.rlEmpty)
    RelativeLayout rlEmpty;
    @BindView(R.id.llContent)
    LinearLayout llContent;
    private Unbinder unbinder;
    private List<String> titles = new ArrayList<>();
    private ArrayList<Object> pageList = new ArrayList<>();
    private BaseFmAdapter adapter;
    private ArrayList<BannerBean> images;
    private boolean flag = false;
    private int mMove_bigX;
    private int mMove_bigY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_purchase, container, false);
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
        getGoodsType();
//        getBannerList();
        List<GoodsBean> shopCart = CommonUtils.getShopCart();
        num.setText(shopCart == null ? "0" : shopCart.size() + "");
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initListener();
    }

    private void initListener() {
        final int width = PreferenceUtils.getInt("shopcart_root_width",0);
        final int height = PreferenceUtils.getInt("shopcart_root_height",0);
        KLog.d("\n"+width+"\n"+height);
        rlNum.setOnTouchListener(new View.OnTouchListener() {
            private int startY;
            private int startX;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        KLog.d("打印操作：按下了");
                        //获取当前按下的坐标
                        startX = (int) motionEvent.getRawX();
                        startY = (int) motionEvent.getRawY();
                        view.bringToFront();
                        flag = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //获取移动后的坐标
                        int moveX = (int) motionEvent.getRawX();
                        int moveY = (int) motionEvent.getRawY();
                        //拿到手指移动距离的大小
                        mMove_bigX = moveX - startX;
                        mMove_bigY = moveY - startY;

                        //拿到当前控件未移动的坐标
                        int left = rlNum.getLeft();
                        int top = rlNum.getTop();
                        left += mMove_bigX;
                        top += mMove_bigY;
                        int right = left + rlNum.getWidth();
                        int bottom = top + rlNum.getHeight();
                        if (left < 0) {
                            left = 0;
                            right = left + rlNum.getWidth();
                        } else if (right > width) {
                            right = width;
                            left = right - rlNum.getWidth();
                        }
                        if (top < 0) {
                            top = 0;
                            bottom = top + rlNum.getHeight();
                        } else if (bottom > height) {
                            bottom = height;
                            top = bottom - rlNum.getHeight();
                        }
                        setRelativeViewLocation(rlNum, left, top, right, bottom);
                        startX = moveX;
                        startY = moveY;
                        //解决有的手机点击购物车按钮没有反应的问题
                        int xDistance = Math.abs(mMove_bigX);
                        int yDistance = Math.abs(mMove_bigY);
                        if (xDistance>5 || yDistance>5){
                            flag = true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        KLog.d("打印操作：抬起了");
                        break;
                }
                return false;
            }
        });
    }

    private void setRelativeViewLocation(View view, int left, int top, int right, int bottom) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(right - left, bottom - top);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        params.setMargins(left, top, 0, 0);
        view.setLayoutParams(params);
    }

//    private void getBannerList() {
//        RetrofitUtils.getInstances().create().getBannerList().enqueue(new Callback<EntityObject<ArrayList<BannerBean>>>() {
//            @Override
//            public void onResponse(Call<EntityObject<ArrayList<BannerBean>>> call, Response<EntityObject<ArrayList<BannerBean>>> response) {
//                EntityObject<ArrayList<BannerBean>> body = response.body();
//                if (body.getCode() == 200) {
//                    images = body.getResult();
//                    if (images == null || images.size() == 0) {
//                        return;
//                    }
//                    mCb.setPages(new CBViewHolderCreator() {
//                        @Override
//                        public ImageViewHolder createHolder(View itemView) {
//                            return new ImageViewHolder(itemView);
//                        }
//
//                        @Override
//                        public int getLayoutId() {
//                            return R.layout.banner;
//                        }
//
//                    }, images)
//                            //设置指示器是否可见
//                            .setPointViewVisible(true)
//                            //设置自动切换（同时设置了切换时间间隔）
//                            .startTurning(2000)
//                            .setPageIndicator(new int[]{R.drawable.banner_unselected, R.drawable.banner_selected}) //设置两个点作为指示器
//                            .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL); //设置指示器的方向水平  居中
//                    mCb.setOnItemClickListener(new OnItemClickListener() {
//                        @Override
//                        public void onItemClick(int position) {
//                            BannerBean bannerBean = images.get(position);
//                            switch (bannerBean.getUrl_type()) {
//                                case 0:
//                                    break;
//                                case 1:
//                                    break;
//                                case 2:
//                                    break;
//                                case 3:
//                                    WebActivity.startAct(getContext(), bannerBean.getUrl_addr(), "图文详情", 3);
//                                    break;
//                                case 4:
//                                    WebActivity.startAct(getContext(), bannerBean.getUrl_addr(), "详情", 4);
//                                    break;
//
//                            }
//
//                        }
//                    });
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<EntityObject<ArrayList<BannerBean>>> call, Throwable t) {
//
//            }
//        });
//    }


    public class ImageViewHolder extends Holder<BannerBean> {
        private ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            imageView = itemView.findViewById(R.id.ivPost);
        }

        @Override
        public void updateUI(BannerBean data) {
            if (imageView == null)
                return;
            if (Util.isOnMainThread() && getActivity() != null) {
                Glide.with(getActivity()).load(Constant.BANNER_IMAGE_URL + data.getPicturl()).placeholder(R.color.color_30adfd).error(R.mipmap.logo).into(imageView);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (Util.isOnMainThread()) {
            Glide.with(this).pauseRequests();
        }
    }

    private void getGoodsType() {
        showLoad();
        RetrofitUtils.getInstances().create().getGoodsType().enqueue(new Callback<EntityObject<ArrayList<GoodsTypeBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<GoodsTypeBean>>> call, Response<EntityObject<ArrayList<GoodsTypeBean>>> response) {
                KLog.d(111);
                hintLoad();
                EntityObject<ArrayList<GoodsTypeBean>> body = response.body();
                if (body.getCode() == 200) {
                    ArrayList<GoodsTypeBean> result = body.getResult();
                    if (result == null || result.size() == 0) {
                        llContent.setVisibility(View.GONE);
                        rlEmpty.setVisibility(View.VISIBLE);
                        return;
                    } else {
                        llContent.setVisibility(View.VISIBLE);
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
                        llContent.setVisibility(View.GONE);
                        rlEmpty.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<GoodsTypeBean>>> call, Throwable t) {
                Log.i("json", t.getMessage());
                llContent.setVisibility(View.GONE);
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


    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(GoodsEvent event) {
        num.setText(event.count + "");
        addShopCartAnimition(event.url);
    }

    private void addShopCartAnimition(String url) {
        int[] cartLocation = new int[2];
        shoppingCart.getLocationInWindow(cartLocation);
        PointF startP = new PointF();
        PointF endP = new PointF();
        PointF controlP = new PointF();
        startP.x = DensityUtils.getScreenWidth(getContext()) / 2;
        startP.y = DensityUtils.getScreenHeight(getContext()) / 4;
        endP.x = cartLocation[0];
        endP.y = cartLocation[1];
        controlP.x = endP.x;
        controlP.y = startP.y;
        final RxFakeAddImageView rxFakeAddImageView = new RxFakeAddImageView(getContext());
        mainLayout.addView(rxFakeAddImageView);
        int width = getResources().getDimensionPixelSize(R.dimen.dp_70);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, width);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);//addRule参数对应RelativeLayout XML布局的属性
        layoutParams.setMargins(0, (int) startP.y, 0, 0);
        rxFakeAddImageView.setLayoutParams(layoutParams);
        Glide.with(mContext).load(Constant.GOODS_IMAGE_URL + url).bitmapTransform(new GlideCircleTransform(getActivity())).into(rxFakeAddImageView);
        rxFakeAddImageView.setVisibility(View.VISIBLE);
        ObjectAnimator addAnimator = ObjectAnimator.ofObject(rxFakeAddImageView, "mPointF", new RxPointFTypeEvaluator(controlP), startP, endP);
        addAnimator.setInterpolator(new LinearInterpolator());
        addAnimator.setDuration(250);
        /*缩放动画*/
        ObjectAnimator scaleAnimatorX = new ObjectAnimator().ofFloat(rxFakeAddImageView, "scaleX", 1f, 3f, 2f);
        ObjectAnimator scaleAnimatorY = new ObjectAnimator().ofFloat(rxFakeAddImageView, "scaleY", 1f, 3f, 2f);
        scaleAnimatorX.setInterpolator(new AccelerateInterpolator());
        scaleAnimatorY.setInterpolator(new AccelerateInterpolator());
        scaleAnimatorX.setDuration(350);
        scaleAnimatorY.setDuration(350);
        /*缩放动画*/
        ObjectAnimator scaleAnimatorX1 = new ObjectAnimator().ofFloat(rxFakeAddImageView, "scaleX", 2f, 0.8f, 0.1f);
        ObjectAnimator scaleAnimatorY1 = new ObjectAnimator().ofFloat(rxFakeAddImageView, "scaleY", 2f, 0.8f, 0.1f);
        scaleAnimatorX1.setInterpolator(new AccelerateInterpolator());
        scaleAnimatorY1.setInterpolator(new AccelerateInterpolator());
        scaleAnimatorX1.setDuration(250);
        scaleAnimatorY1.setDuration(250);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleAnimatorX).with(scaleAnimatorY);
        animatorSet.play(addAnimator).with(scaleAnimatorX1).with(scaleAnimatorY1).after(500);
        animatorSet.start();
        addAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                rxFakeAddImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                rxFakeAddImageView.setVisibility(View.GONE);
                mainLayout.removeView(rxFakeAddImageView);
                AnimatorSet animatorSet2 = new AnimatorSet();
                ObjectAnimator scaleAnimatorX1 = new ObjectAnimator().ofFloat(shoppingCart, "scaleX", 0.5f, 1f);
                ObjectAnimator scaleAnimatorY2 = new ObjectAnimator().ofFloat(shoppingCart, "scaleY", 0.5f, 1f);
                scaleAnimatorX1.setInterpolator(new AccelerateInterpolator());
                scaleAnimatorY2.setInterpolator(new AccelerateInterpolator());
                animatorSet2.play(scaleAnimatorX1).with(scaleAnimatorY2);
                animatorSet2.setDuration(300);
                animatorSet2.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @OnClick(R.id.rlNum)
    public void onViewClicked() {
        KLog.d("rlNum onclick");
        if (flag) {
            return;
        }
        EventBus.getDefault().post(new MainEvent());
    }
}
