package com.shuiwangzhijia.shuidian.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.OrderAdapter;
import com.shuiwangzhijia.shuidian.base.BaseFragment;
import com.shuiwangzhijia.shuidian.bean.OrderBean;
import com.shuiwangzhijia.shuidian.bean.SailOrderListBean;
import com.shuiwangzhijia.shuidian.dialog.HintDialog;
import com.shuiwangzhijia.shuidian.event.LoginOutEvent;
import com.shuiwangzhijia.shuidian.event.OrderListFlashEvent;
import com.shuiwangzhijia.shuidian.event.PayFinishEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.ui.OrderPayActivity;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;
import com.google.gson.Gson;
import com.socks.library.KLog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderBaseFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OrderAdapter.OnViewItemClickListener {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rlEmpty)
    RelativeLayout rlEmpty;
    private Unbinder unbinder;
    private OrderAdapter mOrderAdapter;
    private int type;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x101:
                    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                        completeSwipeRefresh();
                    }

                    break;
            }
        }
    };
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        type = getArguments().getInt("type");
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_order_base, container, false);
            unbinder = ButterKnife.bind(this, mRootView);
            initRecyclerView();
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


    private void getList() {
        RetrofitUtils.getInstances().create().getSailOrderList(type, page, pageSize).enqueue(new Callback<EntityObject<ArrayList<SailOrderListBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<SailOrderListBean>>> call, Response<EntityObject<ArrayList<SailOrderListBean>>> response) {
                KLog.e(new Gson().toJson(response.body().getResult()));
                completeSwipeRefresh();
                hintLoad();
                EntityObject<ArrayList<SailOrderListBean>> body = response.body();
                if (body.getCode() == 200) {
                    ArrayList<SailOrderListBean> result = body.getResult();
                    if (result == null || result.size() == 0) {
                        rlEmpty.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setVisibility(View.GONE);
                        return;
                    } else {
                        rlEmpty.setVisibility(View.GONE);
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                        if (page == 0) {
                            mOrderAdapter.setData(result);
                        } else {
                            mOrderAdapter.addData(result);
                        }
                        loading = result.size() < pageSize ? false : true;
                    }
                } else {
                    if (page == 0) {
                        rlEmpty.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setVisibility(View.GONE);
                    }
                    if (body.getScode() == -200) {
                        EventBus.getDefault().post(new LoginOutEvent());
                    }
                }

            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<SailOrderListBean>>> call, Throwable t) {
                Log.i("json 异常", t.getMessage());
            }
        });
    }

    private void initRecyclerView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.color_30adfd));
        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(getActivity().getResources().getDrawable(R.drawable.divider_bg));
        mRecyclerView.addItemDecoration(divider);
        mOrderAdapter = new OrderAdapter(getActivity(), type);
        mOrderAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mOrderAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("test", "StateChanged = " + newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("test", "onScrolled");
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (dy > 0 && lastVisibleItemPosition + 1 == layoutManager.getItemCount()) {
                    if (loading) {
                        loading = false;
                        new LoadDataThread().start();
                    }
                }
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        });
    }

    @Override
    public void onRefresh() {
        page = -1;
        showLoad();
        new LoadDataThread().start();
    }

    private void completeSwipeRefresh() {
        if (swipeRefreshLayout == null)
            return;
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onReceiptOrderClick(final String orderNo) {
        HintDialog hintDialog = new HintDialog(getContext(), "确认接单？", new HintDialog.OnConfirmClickListener() {
            @Override
            public void onConfirm(Dialog dialog) {
                dialog.dismiss();
                RetrofitUtils.getInstances().create().receiptOrder(orderNo).enqueue(new Callback<EntityObject<Object>>() {
                    @Override
                    public void onResponse(Call<EntityObject<Object>> call, Response<EntityObject<Object>> response) {
                        EntityObject<Object> body = response.body();
                        if (body.getCode() == 200) {
                            hint("接单成功");
                            onRefresh();
                        } else {
                            hint("接单失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<EntityObject<Object>> call, Throwable t) {

                    }
                });
            }
        });
        hintDialog.show();

    }

    @Override
    public void onSendClick(final String orderNo, final int status) {

        HintDialog hintDialog = new HintDialog(getContext(), "确认开始配送？", new HintDialog.OnConfirmClickListener() {
            @Override
            public void onConfirm(Dialog dialog) {
                dialog.dismiss();
                RetrofitUtils.getInstances().create().sendOrder(orderNo, status).enqueue(new Callback<EntityObject<Object>>() {
                    @Override
                    public void onResponse(Call<EntityObject<Object>> call, Response<EntityObject<Object>> response) {
                        EntityObject<Object> body = response.body();
                        if (body.getCode() == 200) {
                            hint("开始配送成功");
                            onRefresh();
                        } else {
                            hint("开始配送失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<EntityObject<Object>> call, Throwable t) {

                    }
                });
            }
        });
        hintDialog.show();


    }

    @Override
    public void onFinishClick(final String orderNo, final int status) {
        HintDialog hintDialog = new HintDialog(getContext(), "提交确认到达？", new HintDialog.OnConfirmClickListener() {
            @Override
            public void onConfirm(Dialog dialog) {
                dialog.dismiss();
                RetrofitUtils.getInstances().create().sendOrder(orderNo, status).enqueue(new Callback<EntityObject<Object>>() {
                    @Override
                    public void onResponse(Call<EntityObject<Object>> call, Response<EntityObject<Object>> response) {
                        EntityObject<Object> body = response.body();
                        if (body.getCode() == 200) {
                            hint("确认到达成功");
                            onRefresh();
                        } else {
                            hint("确认到达失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<EntityObject<Object>> call, Throwable t) {

                    }
                });
            }
        });
        hintDialog.show();


    }

    @Override
    public void onCallClick(final String phone) {
        HintDialog hintDialog = new HintDialog(getContext(), "确认拨打电话？", new HintDialog.OnConfirmClickListener() {
            @Override
            public void onConfirm(Dialog dialog) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + phone);
                intent.setData(data);
                startActivity(intent);
            }
        });
        hintDialog.show();


    }

    @Override
    public void onPayClick(final String orderNo, final long time, final String price) {
        HintDialog hintDialog = new HintDialog(getContext(), "确认去支付？", new HintDialog.OnConfirmClickListener() {
            @Override
            public void onConfirm(Dialog dialog) {
                dialog.dismiss();
                OrderBean data = new OrderBean();
                data.setOrder_no(orderNo);
                data.setOrderTime(time);
                data.setAmount(Double.parseDouble(price));
                OrderPayActivity.startAtc(getContext(), data,1);

            }
        });
        hintDialog.show();


    }

    @Override
    public void onCancelClick(final String orderNo) {
        HintDialog hintDialog = new HintDialog(getContext(), "确认取消订单？", new HintDialog.OnConfirmClickListener() {
            @Override
            public void onConfirm(Dialog dialog) {
                dialog.dismiss();
                RetrofitUtils.getInstances().create().cancelOrder(orderNo).enqueue(new Callback<EntityObject<Object>>() {
                    @Override
                    public void onResponse(Call<EntityObject<Object>> call, Response<EntityObject<Object>> response) {
                        EntityObject<Object> body = response.body();
                        if (body.getCode() == 200) {
                            hint("取消订单成功");
                            onRefresh();
                        } else {
                            hint("取消订单失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<EntityObject<Object>> call, Throwable t) {

                    }
                });
            }
        });
        hintDialog.show();


    }

    /**
     * 模拟加载数据的线程
     */
    class LoadDataThread extends Thread {
        @Override
        public void run() {
            page++;
            getList();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0x101);//通过handler发送一个更新数据的标记
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(OrderListFlashEvent event) {
        if (!event.isFlash) {
            type = event.type;
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(PayFinishEvent event) {
        onRefresh();
    }

    private void hint(String text) {
        ToastUitl.showToastCustom(text);
    }
}
