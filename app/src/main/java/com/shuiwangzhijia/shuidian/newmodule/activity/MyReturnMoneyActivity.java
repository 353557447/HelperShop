package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.BaseFragmentPagerAdapter;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.MyReturnMoneyBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.fragment.MyReturnMoneyFragment;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_my_return_money)
public class MyReturnMoneyActivity extends BaseActivity {
    @BindView(R.id.back_return)
    RelativeLayout mBackReturn;
    @BindView(R.id.tabLayout)
    XTabLayout mTab;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.return_money_policy)
    TextView mReturnMoneyPolicy;
    @BindView(R.id.water_factory_name)
    TextView mWaterFactoryName;
    @BindView(R.id.return_money_balance)
    TextView mReturnMoneyBalance;
    @BindView(R.id.has_withdraw)
    TextView mHasWithdraw;
    @BindView(R.id.has_roll_out)
    TextView mHasRollOut;
    @BindView(R.id.history_return_money)
    TextView mHistoryReturnMoney;
    @BindView(R.id.remain_coupon_counts)
    TextView mRemainCouponCounts;
    @BindView(R.id.total_coupon_counts)
    TextView mTotalCouponCounts;
    private int mDid;
    private MyReturnMoneyBean mMyReturnMoneyBean;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mDid = bundle.getInt("did");
        }
        setBaseBarHide();
        //初始化mTab
        initTab();
        //初始化mVp
        initVp();
        //关联mTab和mVp
        mTab.setupWithViewPager(mVp);
    }

    private void initTab() {
        mTab.setxTabDisplayNum(6);
        mTab.addTab(mTab.newTab().setText("全部"), true);
        mTab.addTab(mTab.newTab().setText("单笔"), false);
        mTab.addTab(mTab.newTab().setText("月度"), false);
        mTab.addTab(mTab.newTab().setText("季度"), false);
        mTab.addTab(mTab.newTab().setText("年度"), false);
        mTab.addTab(mTab.newTab().setText("其它周期"), false);
    }

    private void initVp() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(getFragment(0));
        fragmentList.add(getFragment(1));
        fragmentList.add(getFragment(2));
        fragmentList.add(getFragment(3));
        fragmentList.add(getFragment(4));
        fragmentList.add(getFragment(5));
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("全部");
        titleList.add("单笔");
        titleList.add("月度");
        titleList.add("季度");
        titleList.add("年度");
        titleList.add("其它周期");
        FragmentManager fm = getSupportFragmentManager();
        mVp.setAdapter(new BaseFragmentPagerAdapter(fm, fragmentList, titleList));
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        RetrofitUtils.getInstances().create().getMyReturnMoney(mToken, mDid).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        mMyReturnMoneyBean = mGson.fromJson(datas, MyReturnMoneyBean.class);
                        setData();
                    } else {

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

    private void setData() {
   /*     @BindView(R.id.return_money_balance)
        TextView mReturnMoneyBalance;
        @BindView(R.id.has_withdraw)
        TextView mHasWithdraw;
        @BindView(R.id.has_roll_out)
        TextView mHasRollOut;
        @BindView(R.id.history_return_money)
        TextView mHistoryReturnMoney;
        @BindView(R.id.remain_coupon_counts)
        TextView mRemainCouponCounts;
        @BindView(R.id.total_coupon_counts)
        TextView mTotalCouponCounts;*/

        MyReturnMoneyBean.DataBean data = mMyReturnMoneyBean.getData();
        mWaterFactoryName.setText(data.getSname());
        mReturnMoneyBalance.setText(data.getRebate_amount());
        mHasWithdraw.setText(data.getAlready_amount());
        mHasRollOut.setText(data.getTurn_amount());
        mHistoryReturnMoney.setText(data.getHistory_amount());
        mRemainCouponCounts.setText(data.getNot_s_amount());
        mTotalCouponCounts.setText(data.getAll_s_amount());
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.back_return, R.id.return_money_policy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_return:
                closeActivity();
                break;
            case R.id.return_money_policy:
                Bundle bundle = new Bundle();
                bundle.putInt("did", mDid);
                bundle.putString("waterFactoryName", mMyReturnMoneyBean.getData().getSname());
                skipActivity(ReturnMoneyPolicyActivity.class, bundle);
                break;
        }

    }

    public Fragment getFragment(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("did", mDid);
        MyReturnMoneyFragment fragment = new MyReturnMoneyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
