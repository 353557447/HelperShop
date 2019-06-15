package com.shuiwangzhijia.shuidian.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.socks.library.KLog;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.CenterAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.ActivityPlantsBean;
import com.shuiwangzhijia.shuidian.event.CommonEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.activity.RechargeCenterActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CenterActivity extends BaseAct implements CenterAdapter.OnSaleClickListener {

    @BindView(R.id.mRecyclerView)
    RecyclerView mMRecyclerView;
//    @BindView(R.id.swipeRefreshLayout)
//    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.shopcart)
    ImageView mShopcart;
    @BindView(R.id.center_title)
    TextView mTitle;
    private LinearLayoutManager layoutManager;
    private CenterAdapter mCenterAdapter;
    private int type;

    public static void startAct(Context context, int type) {
        Intent intent = new Intent(context, CenterActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);
        type = getIntent().getIntExtra("type", -1);
        ButterKnife.bind(this);
        if (type == 1){
            mTitle.setText("我的水厂");
            mShopcart.setVisibility(View.GONE);
        }else {
            mTitle.setText("活动中心");
            mShopcart.setVisibility(View.VISIBLE);
        }
//        initRecycleView();
        setNoTitleBar();
        getList();
    }


    private void getList() {
        RetrofitUtils.getInstances().create().ShowActivityPlants(type).enqueue(new Callback<EntityObject<ArrayList<ActivityPlantsBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<ActivityPlantsBean>>> call, Response<EntityObject<ArrayList<ActivityPlantsBean>>> response) {
                EntityObject<ArrayList<ActivityPlantsBean>> body = response.body();
                KLog.e(new Gson().toJson(body));
                if (body.getCode() == 200) {
                    ArrayList<ActivityPlantsBean> result = body.getResult();
                    initRecycleView();
                    mCenterAdapter.setData(result);
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<ActivityPlantsBean>>> call, Throwable t) {

            }
        });
    }

    private void initRecycleView() {
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_30adfd));
        layoutManager = new LinearLayoutManager(this);
        mMRecyclerView.setLayoutManager(layoutManager);
        mMRecyclerView.setHasFixedSize(true);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.divider_bg_tran));
        mMRecyclerView.addItemDecoration(divider);
        mCenterAdapter = new CenterAdapter(this,type);
        mCenterAdapter.setOnSaleClickListener(this);
        mMRecyclerView.setAdapter(mCenterAdapter);
        mMRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("test", "StateChanged = " + newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
//        mSwipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                onRefresh();
//            }
//        });
    }

//    @Override
//    public void onRefresh() {
//
//    }

    @OnClick({R.id.back, R.id.shopcart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.shopcart:
                finish();
                skipActivity(MainActivity.class);
                EventBus.getDefault().post(new CommonEvent("goto_shopcart"));
                break;
        }
    }

    @Override
    public void onTopUpClick(int position) {
        skipActivity(RechargeCenterActivity.class);
    }
}
