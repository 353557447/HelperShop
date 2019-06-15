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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 选择商品
 * created by wangsuli on 2018/8/20.
 */
public class SelectGoodsActivity extends BaseAct {
    @BindView(R.id.typeRecyclerView)
    RecyclerView typeRecyclerView;
    @BindView(R.id.detailRecyclerView)
    RecyclerView detailRecyclerView;
    @BindView(R.id.llContent)
    LinearLayout llContent;
    @BindView(R.id.rlEmpty)
    RelativeLayout rlEmpty;
    @BindView(R.id.emptyHint)
    TextView emptyHint;
    private GoodsTypeAdapter mGoodsTypeAdapter;
    private GoodsDetailAdapter mGoodsDetailAdapter;
    private String gtype;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_goods);
        ButterKnife.bind(this);
        setTitle("选择商品");
        PageSize = 50;
        initTypeRecyclerView();
        initDetailRecyclerView();
        getTypeList();
    }

    private void getTypeList() {
        RetrofitUtils.getInstances().create().getSelectGoodsType().enqueue(new Callback<EntityObject<ArrayList<GoodsManageBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<GoodsManageBean>>> call, Response<EntityObject<ArrayList<GoodsManageBean>>> response) {
                hintLoad();
                EntityObject<ArrayList<GoodsManageBean>> body = response.body();
                int code = body.getCode();
                if (code == 200) {
                    ArrayList<GoodsManageBean> result = body.getResult();
                    if (result == null || result.size() == 0) {
                        rlEmpty.setVisibility(View.VISIBLE);
                        llContent.setVisibility(View.GONE);
                        return;
                    }
                    rlEmpty.setVisibility(View.GONE);
                    llContent.setVisibility(View.VISIBLE);
                    GoodsManageBean item0 = new GoodsManageBean();
                    item0.setGname("全部");
                    item0.setGtype("0");
                    result.add(0, item0);
                    mGoodsTypeAdapter.setData(result);
                    mGoodsTypeAdapter.setSelectIndex(0);
                    gtype = item0.getGtype();
                    getDataList();
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

    private void getDataList() {
        showLoad();
        RetrofitUtils.getInstances().create().getSelectGoodsList(gtype, page, PageSize).enqueue(new Callback<EntityObject<ArrayList<GoodsBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<GoodsBean>>> call, Response<EntityObject<ArrayList<GoodsBean>>> response) {
                hintLoad();
                EntityObject<ArrayList<GoodsBean>> body = response.body();
                int code = body.getCode();
                if (code == 200) {
                    ArrayList<GoodsBean> result = body.getResult();
                    if (result == null) {
                        rlEmpty.setVisibility(View.VISIBLE);
                        llContent.setVisibility(View.GONE);
                        return;
                    }
                    rlEmpty.setVisibility(View.GONE);
                    llContent.setVisibility(View.VISIBLE);
                    mGoodsTypeAdapter.setItem(gtype, result);
                    mGoodsDetailAdapter.setData(result);
                } else if (code == -300) {
                    rlEmpty.setVisibility(View.VISIBLE);
                    llContent.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<GoodsBean>>> call, Throwable t) {

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
                List<GoodsBean> list = item.getList();
                if (list == null || list.size() == 0) {
                    gtype = item.getGtype();
                    page = 0;
                    getDataList();
                } else {
                    mGoodsDetailAdapter.setData(list);
                }
                mGoodsTypeAdapter.setSelectIndex(position);
            }
        });
        typeRecyclerView.setAdapter(mGoodsTypeAdapter);

    }

    private void initDetailRecyclerView() {
        detailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailRecyclerView.setHasFixedSize(true);
        mGoodsDetailAdapter = new GoodsDetailAdapter(this, false, true);
        mGoodsDetailAdapter.setSelectGood(true);
        mGoodsDetailAdapter.setOnSaleClickListener(new GoodsDetailAdapter.OnSaleClickListener() {
            @Override
            public void onSelectClick(final int position) {
                GoodsBean item = mGoodsDetailAdapter.getItem(position);
                EventBus.getDefault().post(item);
                finish();

            }
        });
        detailRecyclerView.setAdapter(mGoodsDetailAdapter);
    }


}
