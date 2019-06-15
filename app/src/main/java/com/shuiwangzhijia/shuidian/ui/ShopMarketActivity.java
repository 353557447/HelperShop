package com.shuiwangzhijia.shuidian.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.ShopMarketAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.ShopMarketData;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 店铺营销
 * created by wangsuli on 2018/10/11.
 */
public class ShopMarketActivity extends BaseAct {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ShopMarketAdapter shopMarketAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_market);
        ButterKnife.bind(this);
        setTitle("店铺营销");
        getData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.divider_bg));
        mRecyclerView.addItemDecoration(divider);
        shopMarketAdapter = new ShopMarketAdapter(this);
        shopMarketAdapter.setOnItemClickListener(new ShopMarketAdapter.OnViewItemClickListener() {
            @Override
            public void onSetClick(int position) {
                if(position==0){
                    startActivity(new Intent(ShopMarketActivity.this,ShopCouponActivity.class));
                }else{
                    startActivity(new Intent(ShopMarketActivity.this,ShopTicketActivity.class));
                }
            }

            @Override
            public void onNoteClick(int position) {

            }
        });
        mRecyclerView.setAdapter(shopMarketAdapter);
    }

    private void getData() {
        RetrofitUtils.getInstances().create().getShopMarketData().enqueue(new Callback<EntityObject<ShopMarketData>>() {
            @Override
            public void onResponse(Call<EntityObject<ShopMarketData>> call, Response<EntityObject<ShopMarketData>> response) {
                EntityObject<ShopMarketData> body = response.body();
                if(body.getCode()==200){
                    shopMarketAdapter.setData(body.getResult());
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ShopMarketData>> call, Throwable t) {

            }
        });
    }
}
