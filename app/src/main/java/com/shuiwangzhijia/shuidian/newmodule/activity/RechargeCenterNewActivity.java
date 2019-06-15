package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.RechargeCenterInfoBean;
import com.shuiwangzhijia.shuidian.http.FndUtils;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.RechargeCenterCouponNewAdapter;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.MeasureUtil;
import com.shuiwangzhijia.shuidian.view.BaseBar;
import com.shuiwangzhijia.shuidian.view.SpacesItemDecoration;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_recharge_center_new, title = "优惠充值")
public class RechargeCenterNewActivity extends BaseActivity {
    @BindView(R.id.rv)
    RecyclerView mRv;
    private RechargeCenterCouponNewAdapter mRechargeCenterCouponNewAdapter;
    private ArrayList<RechargeCenterInfoBean.DataBean.ListBean.RechargeBean> mRechargeCouponList;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        mBaseBar.setBarListener(new BaseBar.BarListener() {
            @Override
            public void onLeftClick() {
                closeActivity();
            }

            @Override
            public void onRightClick() {

            }
        });
        initRv();
    }

    private void initRv() {
        mRechargeCouponList = new ArrayList<>();
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addItemDecoration(new SpacesItemDecoration(this, MeasureUtil.dip2px(this,12)));
        mRechargeCenterCouponNewAdapter = new RechargeCenterCouponNewAdapter(this);
        mRechargeCenterCouponNewAdapter.setData(mRechargeCouponList);
        mRv.setAdapter(mRechargeCenterCouponNewAdapter);
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getRechargeCenterList(CommonUtils.getToken()).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int scode = object.getInt("scode");
                    if (scode == 200) {
                        RechargeCenterInfoBean rechargeCenterInfoBean = mGson.fromJson(datas, RechargeCenterInfoBean.class);
                        List<RechargeCenterInfoBean.DataBean.ListBean> list = rechargeCenterInfoBean.getData().getList();

                        List<RechargeCenterInfoBean.DataBean.ListBean.RechargeBean> recharge = list.get(0).getRecharge();
                        if (recharge != null && recharge.size() != 0) {
                            RechargeCenterInfoBean.DataBean.ListBean.RechargeBean rechargeBean = recharge.get(0);
                            mRechargeCouponList.addAll(recharge);
                            mRechargeCenterCouponNewAdapter.notifyDataSetChanged();
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

    @Override
    protected void initEvent() {

    }
}
