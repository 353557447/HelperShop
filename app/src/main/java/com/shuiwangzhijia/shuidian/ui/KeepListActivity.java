package com.shuiwangzhijia.shuidian.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.BaseFragmentPagerAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.ShowPlantsBean;
import com.shuiwangzhijia.shuidian.event.TitleEvent;
import com.shuiwangzhijia.shuidian.fragment.KeepListFragment;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.DensityUtils;
import com.socks.library.KLog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeepListActivity extends BaseAct {

    @BindView(R.id.my_order_xTablayout)
    XTabLayout mMyOrderXTablayout;
    @BindView(R.id.my_order_vp)
    ViewPager mMyOrderVp;
    @BindView(R.id.status_bar)
    TextView mStatusBar;
    @BindView(R.id.status)
    TextView mStatus;
    @BindView(R.id.adress)
    TextView mAdress;
    @BindView(R.id.head_title)
    RelativeLayout mHeadTitle;
    @BindView(R.id.back)
    ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode
                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_keep_list);
        ButterKnife.bind(this);
        setNoTitleBar();
        //初始化mTab
//        initTab();
//        //初始化mVp
//        initVp();
        //关联mTab和mVp
        initData();
    }

    private void initData() {
        getList();
    }

    public void getList() {
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
                    initTab(result);
                    initVp(result);
                    mMyOrderXTablayout.setupWithViewPager(mMyOrderVp);
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<ShowPlantsBean>>> call, Throwable t) {

            }
        });
    }

    private void initTab(ArrayList<ShowPlantsBean> data) {
        mMyOrderXTablayout.setxTabDisplayNum(data.size());
        for (int i = 0; i < data.size(); i++) {
            if (i == 0) {
                mMyOrderXTablayout.addTab(mMyOrderXTablayout.newTab().setText(data.get(i).getSname()), true);
            } else {
                mMyOrderXTablayout.addTab(mMyOrderXTablayout.newTab().setText(data.get(i).getSname()), false);
            }
        }
        if (data.size() == 1){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtils.dp2px(106), DensityUtils.dp2px(50));
            layoutParams.setMargins(0,DensityUtils.dp2px(53),0,0);
            mMyOrderXTablayout.setLayoutParams(layoutParams);
        }else if (data.size() > 1){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtils.getScreenWidth(this), DensityUtils.dp2px(50));
            layoutParams.setMargins(0,DensityUtils.dp2px(53),0,0);
            mMyOrderXTablayout.setLayoutParams(layoutParams);
        }
    }

    private void initVp(ArrayList<ShowPlantsBean> data) {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        ArrayList<String> titleList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            KLog.d(i);
            fragmentList.add(setFragment(i));
            titleList.add(data.get(i).getSname());
        }
        FragmentManager fm = getSupportFragmentManager();
        mMyOrderVp.setAdapter(new BaseFragmentPagerAdapter(fm, fragmentList, titleList));
    }

    private Fragment setFragment(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        KeepListFragment orderBaseFragment = new KeepListFragment();
        orderBaseFragment.setArguments(bundle);
        return orderBaseFragment;
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void changeTitle(TitleEvent event) {
        String adress = event.getAdress();
        int type = event.getType();
        if ("show_adress".equals(event.getMsg())) {
            mStatusBar.setVisibility(View.GONE);
            mStatus.setVisibility(View.VISIBLE);
            mAdress.setVisibility(View.VISIBLE);
            if (type == 0){
                mStatus.setText("水厂配送");
            }else {
                mStatus.setText("水厂自提");
            }
            mAdress.setText(adress);
        } else if ("show_title".equals(event.getMsg())) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatus.setVisibility(View.GONE);
            mAdress.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
