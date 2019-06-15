package com.shuiwangzhijia.shuidian.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.GoodsDetailAdapter;
import com.shuiwangzhijia.shuidian.adapter.GoodsTypeAdapter;
import com.shuiwangzhijia.shuidian.base.BaseFragment;
import com.shuiwangzhijia.shuidian.bean.GoodsManageBean;
import com.shuiwangzhijia.shuidian.event.LoginOutEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShopGoodsFragment extends BaseFragment {
    @BindView(R.id.typeRecyclerView)
    RecyclerView typeRecyclerView;
    @BindView(R.id.detailRecyclerView)
    RecyclerView detailRecyclerView;
    @BindView(R.id.llContent)
    LinearLayout llContent;
    @BindView(R.id.rlEmpty)
    RelativeLayout rlEmpty;
    private GoodsTypeAdapter mGoodsTypeAdapter;
    private GoodsDetailAdapter mGoodsDetailAdapter;
    private boolean isSale = false;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_shop_goods, container, false);
            unbinder = ButterKnife.bind(this, mRootView);
            initTypeRecyclerView();
            initDetailRecyclerView();
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            unbinder = ButterKnife.bind(this, mRootView);
        }
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        showLoad();
        getDataList();
    }

    private void getDataList() {
        RetrofitUtils.getInstances().create().getGoodsManageList(isSale ? 0 : 1).enqueue(new Callback<EntityObject<ArrayList<GoodsManageBean>>>() {
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
                } else {
                    if (body.getScode() == -200) {
                        EventBus.getDefault().post(new LoginOutEvent());
                    }
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<GoodsManageBean>>> call, Throwable t) {

            }
        });
    }

    private void initTypeRecyclerView() {
        typeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        typeRecyclerView.setHasFixedSize(true);
        mGoodsTypeAdapter = new GoodsTypeAdapter(getContext());
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
        detailRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        detailRecyclerView.setHasFixedSize(true);
        mGoodsDetailAdapter = new GoodsDetailAdapter(getContext(), isSale, true);
        mGoodsDetailAdapter.setOnSaleClickListener(new GoodsDetailAdapter.OnSaleClickListener() {
            @Override
            public void onSelectClick(int position) {

            }
        });
        detailRecyclerView.setAdapter(mGoodsDetailAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
