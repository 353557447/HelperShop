package com.shuiwangzhijia.shuidian.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.bumptech.glide.Glide;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.BaseFmAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.base.Constant;
import com.shuiwangzhijia.shuidian.bean.ShopBean;
import com.shuiwangzhijia.shuidian.event.CommentEvent;
import com.shuiwangzhijia.shuidian.event.LoginOutEvent;
import com.shuiwangzhijia.shuidian.fragment.ShopCommentFragment;
import com.shuiwangzhijia.shuidian.fragment.ShopGoodsFragment;
import com.shuiwangzhijia.shuidian.fragment.ShopInfoFragment;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShopSpreadActivity extends BaseAct {

    @BindView(R.id.tabLayout)
    XTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.shopName)
    TextView shopName;
    @BindView(R.id.shopType)
    TextView shopType;
    @BindView(R.id.startNum)
    RatingBar startNum;
    @BindView(R.id.orderCount)
    TextView orderCount;
    @BindView(R.id.distance)
    TextView distance;
    @BindView(R.id.deliverFee)
    TextView deliverFee;
    @BindView(R.id.freeHint)
    TextView freeHint;
    @BindView(R.id.sendTime)
    TextView sendTime;
    @BindView(R.id.share_shop)
    TextView mShareShop;
    private List<String> titles;
    private ArrayList<Object> pageList;
    private BaseFmAdapter adapter;
    private ShopBean shopBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_spread);
        ButterKnife.bind(this);
        setTitle("店铺推广");
        initData();
    }

    private void initData() {
        showLoad();
        RetrofitUtils.getInstances().create().getShopInfo().enqueue(new Callback<EntityObject<ShopBean>>() {
            @Override
            public void onResponse(Call<EntityObject<ShopBean>> call, Response<EntityObject<ShopBean>> response) {
                hintLoad();
                EntityObject<ShopBean> body = response.body();
                if (body.getCode() == 200) {
                    shopBean = body.getResult();
                    initView();
                } else {
                    if (body.getScode() == -200) {
                        EventBus.getDefault().post(new LoginOutEvent());
                    }
                    hint(body.getMsg());
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ShopBean>> call, Throwable t) {
            }
        });
    }

    private void hint(String text) {
        ToastUitl.showToastCustom(text);
    }

    public void initView() {
        shopName.setText(shopBean.getSname());
        orderCount.setText("销量：" + shopBean.getSailsum());
        deliverFee.setText("配送费:" + isEmpty(shopBean.getAmount()) + "元");
        freeHint.setText("满" + isEmpty(shopBean.getFull_free()) + "元免配送费");
        String effic = shopBean.getEffic();
        if (TextUtils.isEmpty(effic)) {
            sendTime.setVisibility(View.GONE);
        } else {
            sendTime.setText("1小时内:" + effic + "千米");
        }
        startNum.setRating(shopBean.getScore());
        String headPic = CommonUtils.getHeadPic();
        KLog.d(headPic);
        Glide.with(this).load(Constant.SHOP_IMAGE_URL + headPic).placeholder(R.drawable.morenshuidian).into(image);
//        Glide.with(ShopSpreadActivity.this).load(App.SHOP_IMAGE_URL + shopBean.getPicturl()).placeholder(R.color.color_eeeeee).into(image);
        titles = Arrays.asList(new String[]{"商家商品", "商家信息", "评论"});
        pageList = new ArrayList<>();
        pageList.add(new ShopGoodsFragment());
        ShopInfoFragment infoFragment = new ShopInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("phone", shopBean.getPhone());
        bundle.putString("address", shopBean.getAddress());
        bundle.putString("location", shopBean.getLnglat());
        infoFragment.setArguments(bundle);
        pageList.add(infoFragment);
        pageList.add(new ShopCommentFragment());
        adapter = new BaseFmAdapter(getSupportFragmentManager(), pageList, titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private String isEmpty(String text) {
        return TextUtils.isEmpty(text) ? "0" : text;
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(CommentEvent event) {
        titles.set(2, "评论（" + event.size + "）");
        adapter.setTitles(titles);
    }

    @OnClick(R.id.share_shop)
    public void onViewClicked() {
        WaterShareActivity.startAct(this);
    }
}
