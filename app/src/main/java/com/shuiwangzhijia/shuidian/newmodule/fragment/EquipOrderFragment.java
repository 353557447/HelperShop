package com.shuiwangzhijia.shuidian.newmodule.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseLazyFragment;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.EquipOrderBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.activity.EquipOrderDetailsActivity;
import com.shuiwangzhijia.shuidian.newmodule.adapter.EquipOrderAdapter;
import com.shuiwangzhijia.shuidian.view.SpacesItemDecoration;
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
@FndViewInject(contentViewId = R.layout.fragment_equip_order)
public class EquipOrderFragment extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener, EquipOrderAdapter.OnItemClickListener, EquipOrderAdapter.OnRefundClickListener {
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<EquipOrderBean.DataBean> mList;
    private EquipOrderAdapter mEquipOrderAdapter;

    public EquipOrderFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView(View view) {
        //item_equip_order
        initRv();
    }

    private void initRv() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_30adfd));
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mList = new ArrayList<>();
        mEquipOrderAdapter = new EquipOrderAdapter(mContext);
        mEquipOrderAdapter.setData(mList);
        mEquipOrderAdapter.setOnItemClickListener(this);
        mEquipOrderAdapter.setOnRefundClickListener(this);
        mRv.addItemDecoration(new SpacesItemDecoration(26, mContext));
        mRv.setAdapter(mEquipOrderAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPage=0;
        isLoadMore=false;
        getData();
    }

    private void getData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getEquipOrderList(mToken,mPage,mLimit).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        EquipOrderBean equipOrderBean = mGson.fromJson(datas, EquipOrderBean.class);
                        List<EquipOrderBean.DataBean> data = equipOrderBean.getData();
                        if(!isLoadMore){
                            if(data.isEmpty()){
                                setCentreViewShow(R.drawable.no_order_data_icon,"暂无设备订单~");
                            }else{
                                setCentreViewDismiss();
                            }
                            mSwipeRefreshLayout.setRefreshing(false);
                            mList.clear();
                        }
                        mList.addAll(data);
                        mEquipOrderAdapter.notifyDataSetChanged();
                    } else {
                        if(!isLoadMore){
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
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
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isSlideToBottom(recyclerView)) {
                    isLoadMore = true;
                    mPage++;
                    getData();
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        mPage = 0;
        isLoadMore = false;
        getData();
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle=new Bundle();
        String orderCode = mList.get(position).getOrder_code();
        bundle.putString("orderCode",orderCode);
        skipActivity(EquipOrderDetailsActivity.class,bundle);
    }

    @Override
    public void onRefundClick(int position) {
        handleRefund(position);
    }

    private void handleRefund(int position) {
        EquipOrderBean.DataBean dataBean = mList.get(position);
        String order_code = dataBean.getOrder_code();
        showLoadingDialog();
        RetrofitUtils.getInstances().create().equipOrderRefund(mToken,order_code).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object =new JSONObject(datas);
                    int code = object.getInt("code");
                    if(code==200){
                        mPage = 0;
                        isLoadMore = false;
                        getData();
                        showToast("退款成功");
                    }else{
                        showToast("退款失败");
                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                }finally {
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
}
