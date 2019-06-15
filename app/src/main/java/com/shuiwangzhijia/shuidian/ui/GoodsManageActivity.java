package com.shuiwangzhijia.shuidian.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.GoodsDetailAdapter;
import com.shuiwangzhijia.shuidian.adapter.GoodsTypeAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.GoodsBean;
import com.shuiwangzhijia.shuidian.bean.GoodsManageBean;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GoodsManageActivity extends BaseAct {
    @BindView(R.id.typeRecyclerView)
    RecyclerView typeRecyclerView;
    @BindView(R.id.detailRecyclerView)
    RecyclerView detailRecyclerView;
    @BindView(R.id.saleBtn)
    TextView saleBtn;
    @BindView(R.id.downBtn)
    TextView downBtn;
    @BindView(R.id.llContent)
    LinearLayout llContent;
    @BindView(R.id.rlEmpty)
    RelativeLayout rlEmpty;
    @BindView(R.id.emptyHint)
    TextView emptyHint;
    private GoodsTypeAdapter mGoodsTypeAdapter;
    private GoodsDetailAdapter mGoodsDetailAdapter;
    private boolean isSale = true;//1上架，0下架

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_manage);
        ButterKnife.bind(this);
        setTitle("商品管理");
        initTypeRecyclerView();
        initDetailRecyclerView();
        changeState(isSale);
        getDataList();
    }

    private void getDataList() {
        showLoad();
        RetrofitUtils.getInstances().create().getGoodsManageList(isSale ? 1 : 0).enqueue(new Callback<EntityObject<ArrayList<GoodsManageBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<GoodsManageBean>>> call, Response<EntityObject<ArrayList<GoodsManageBean>>> response) {
                hintLoad();
                EntityObject<ArrayList<GoodsManageBean>> body = response.body();
                int code = body.getCode();
                if (code == 200) {
                    ArrayList<GoodsManageBean> result = body.getResult();
                    if (result == null) {
                        rlEmpty.setVisibility(View.VISIBLE);
                        llContent.setVisibility(View.GONE);
                        emptyHint.setText(isSale ? "暂无商品售卖" : "下架商品为空\n" + "若要新增商品，请联系指定水厂");
                        return;
                    }
                    rlEmpty.setVisibility(View.GONE);
                    llContent.setVisibility(View.VISIBLE);
                    mGoodsTypeAdapter.setData(result);
                    mGoodsTypeAdapter.setSelectIndex(0);
                    mGoodsDetailAdapter.setData(result.get(0).getList());
                } else if (code == -300) {
                    rlEmpty.setVisibility(View.VISIBLE);
                    llContent.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<GoodsManageBean>>> call, Throwable t) {

            }
        });
    }

    private void initTypeRecyclerView() {
        typeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        typeRecyclerView.setHasFixedSize(true);
        mGoodsTypeAdapter = new GoodsTypeAdapter(this);
        mGoodsTypeAdapter.setOnItemClickListener(new GoodsTypeAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                GoodsManageBean item = mGoodsTypeAdapter.getItem(position);
                mGoodsDetailAdapter.setData(item.getList());
                mGoodsTypeAdapter.setSelectIndex(position);
            }
        });
        typeRecyclerView.setAdapter(mGoodsTypeAdapter);

    }

    private void initDetailRecyclerView() {
        detailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailRecyclerView.setHasFixedSize(true);
        mGoodsDetailAdapter = new GoodsDetailAdapter(this, isSale, false);
        mGoodsDetailAdapter.setOnSaleClickListener(new GoodsDetailAdapter.OnSaleClickListener() {
            @Override
            public void onSelectClick(final int position) {
                GoodsBean item = mGoodsDetailAdapter.getItem(position);
                RetrofitUtils.getInstances().create().upDownGoods(item.getSid(),item.getGid()).enqueue(new Callback<EntityObject<String>>() {
                    @Override
                    public void onResponse(Call<EntityObject<String>> call, Response<EntityObject<String>> response) {
                        EntityObject<String> body = response.body();
                        if (body.getCode() == 200) {
                            ToastUitl.showToastCustom("提交成功!");
                            mGoodsDetailAdapter.deleteItem(position);
                        } else {
                            ToastUitl.showToastCustom("提交失败!");
                        }
                    }

                    @Override
                    public void onFailure(Call<EntityObject<String>> call, Throwable t) {

                    }
                });


            }
        });
        detailRecyclerView.setAdapter(mGoodsDetailAdapter);
    }


    @OnClick({R.id.saleBtn, R.id.downBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.saleBtn:
                changeState(true);
                getDataList();
                break;
            case R.id.downBtn:
                changeState(false);
                getDataList();
                break;
        }
    }

    private void changeState(boolean flag) {
        isSale = flag;
        if (flag) {
            saleBtn.setBackgroundColor(getResources().getColor(R.color.color_505E84));
            downBtn.setBackgroundColor(getResources().getColor(R.color.color_ffffff));
            saleBtn.setSelected(true);
            downBtn.setSelected(false);
        } else {
            downBtn.setBackgroundColor(getResources().getColor(R.color.color_505E84));
            saleBtn.setBackgroundColor(getResources().getColor(R.color.color_ffffff));
            downBtn.setSelected(true);
            saleBtn.setSelected(false);
        }
        mGoodsDetailAdapter.setSale(flag);
    }
}
