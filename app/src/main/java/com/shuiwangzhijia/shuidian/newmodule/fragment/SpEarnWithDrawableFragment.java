package com.shuiwangzhijia.shuidian.newmodule.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseLazyFragment;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.ShopEarnApplyWdBean;
import com.shuiwangzhijia.shuidian.event.CommonEvent;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.SpEarnWDableAdapter;
import com.shuiwangzhijia.shuidian.ui.CashActivity;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@FndViewInject(contentViewId = R.layout.fragment_sp_earn_with_drawable)
public class SpEarnWithDrawableFragment extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener, SpEarnWDableAdapter.OnItemClickListener {
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.selected_iv)
    ImageView mSelectedIv;
    @BindView(R.id.all_selected)
    LinearLayout mAllSelected;
    @BindView(R.id.apply_immediately)
    TextView mApplyImmediately;
    @BindView(R.id.total_price)
    TextView mTotalPrice;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private SpEarnWDableAdapter mSpEarnWDableAdapter;
    private List<ShopEarnApplyWdBean.DataBean> mList;
    private boolean isAllSelected;

    public SpEarnWithDrawableFragment() {

    }

    @Override
    protected void initView(View view) {
        initRv();
    }

    private void initRv() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_30adfd));
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mSpEarnWDableAdapter = new SpEarnWDableAdapter(mContext);
        mList = new ArrayList<>();
        mSpEarnWDableAdapter.setData(mList);
        mSpEarnWDableAdapter.setOnItemClickListener(this);
        mRv.setAdapter(mSpEarnWDableAdapter);
    }

    @Override
    protected void lazyLoadData() {
        getData();
    }

    private void getData() {
        showLoadingDialog();
        KLog.e(mToken);
        RetrofitUtils.getInstances().create().shopEarnApplyWDList(mToken, mPage, mLimit, 1).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (code == 200) {
                        ShopEarnApplyWdBean shopEarnApplyWdBean = mGson.fromJson(datas, ShopEarnApplyWdBean.class);
                        List<ShopEarnApplyWdBean.DataBean> list = shopEarnApplyWdBean.getData();
                        if (!isLoadMore) {
                            mList.clear();
                            if (list.size() == 0)
                                setCentreViewShow(R.drawable.no_order_data_icon, "还没有可提现收益~");
                        }
                        mList.addAll(list);
                        mSpEarnWDableAdapter.notifyDataSetChanged();
                    } else {
                        if (!isLoadMore)
                            setCentreViewShow(R.drawable.no_order_data_icon, "还没有可提现收益~");
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

    @OnClick({R.id.all_selected, R.id.apply_immediately})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all_selected:
                for (int i = 0; i < mList.size(); i++) {
                    mList.get(i).setChecked(!isAllSelected);
                }
                isAllSelected = !isAllSelected;
                mSpEarnWDableAdapter.notifyDataSetChanged();
                break;
            case R.id.apply_immediately:
                Bundle bundle=new Bundle();
                bundle.putString("money",totalPriceResult+"");
                ArrayList<String> arrayList=new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {
                    arrayList.add( mList.get(i).getOrder_code());
                }
                String[] orderSn = arrayList.toArray(new String[arrayList.size()]);
                bundle.putStringArray("order_sn",orderSn);
                skipActivity(CashActivity.class,bundle);
                break;
        }
    }

    @Override
    public void onRefresh() {
        mPage = 0;
        isLoadMore = false;
        getData();
    }

    @Override
    public void onItemClick(int position) {
        ShopEarnApplyWdBean.DataBean dataBean = mList.get(position);
        dataBean.setChecked(!dataBean.isChecked());
        mSpEarnWDableAdapter.notifyDataSetChanged();
    }
    private double totalPriceResult;
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void spEarnWithDrawableTotalMoneyChanged(CommonEvent commonEvent) {
        if ("spEarnWithDrawableTotalMoneyChanged".equals(commonEvent.getMsg())) {
            double totalPrice = 0.00;
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).isChecked()) {
                    ShopEarnApplyWdBean.DataBean dataBean = mList.get(i);
                    String amountPrice = dataBean.getAmount_price();
                    double v = Double.parseDouble(amountPrice);
                    totalPrice += v;
                }
            }
            totalPriceResult=totalPrice;
            mTotalPrice.setText(totalPrice + "");
        }
    }
}
