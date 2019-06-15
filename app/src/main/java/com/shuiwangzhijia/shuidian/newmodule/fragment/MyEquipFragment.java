package com.shuiwangzhijia.shuidian.newmodule.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseLazyFragment;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.MyEquipBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.activity.EquipDetailsActivity;
import com.shuiwangzhijia.shuidian.newmodule.adapter.MyEquipAdapter;
import com.shuiwangzhijia.shuidian.utils.MyUtils;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@FndViewInject(contentViewId = R.layout.fragment_my_equip)
public class MyEquipFragment extends BaseLazyFragment implements MyEquipAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.history_earnings)
    TextView mHistoryEarnings;
    @BindView(R.id.top_today_earnings)
    TextView mTopTodayEarnings;
    @BindView(R.id.top_sale_counts)
    TextView mTopSaleCounts;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<MyEquipBean.DataBean.ListBean> mList;
    private MyEquipAdapter mMyEquipAdapter;

    public MyEquipFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView(View view) {
        //item_my_equip
        initRv();
    }

    private void initRv() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_30adfd));
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mList = new ArrayList<MyEquipBean.DataBean.ListBean>();
        mMyEquipAdapter = new MyEquipAdapter(mContext);
        mMyEquipAdapter.setData(mList);
        mMyEquipAdapter.setOnItemClickListener(this);
        mRv.setAdapter(mMyEquipAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        getData();
    }

    private void getData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getMySmartEquipList(mToken).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (code == 200) {
                        MyEquipBean myEquipBean = mGson.fromJson(datas, MyEquipBean.class);
                        List<MyEquipBean.DataBean.ListBean> data = myEquipBean.getData().getList();
                        if(data.isEmpty()){
                            setCentreViewShow(R.drawable.no_equipment, "还没有设备哦~");
                        }else{
                            setCentreViewDismiss();
                        }
                        MyEquipBean.DataBean.TotalBean total = myEquipBean.getData().getTotal();
                        mHistoryEarnings.setText("￥" + MyUtils.formatPrice(total.getTotal_price()));
                        mTopTodayEarnings.setText("" + MyUtils.formatPrice(total.getDay_price()));
                        mTopSaleCounts.setText("" + total.getDay_sales());
                        mList.clear();
                        mList.addAll(data);
                        mMyEquipAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                } finally {
                    dismissLoadingDialog();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                showErrorToast();
                KLog.e(t.getMessage());
                dismissLoadingDialog();
            }
        });
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onItemClick(int position) {
        MyEquipBean.DataBean.ListBean dataBean = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("deviceId", dataBean.getId());
        bundle.putString("deviceName", dataBean.getDname());
        skipActivity(EquipDetailsActivity.class, bundle);
    }

    @Override
    public void onRefresh() {
        getData();
    }
}
