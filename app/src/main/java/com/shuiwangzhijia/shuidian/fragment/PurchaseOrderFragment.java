package com.shuiwangzhijia.shuidian.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.socks.library.KLog;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.PurchaseOrderAdapter;
import com.shuiwangzhijia.shuidian.base.BaseFragment;
import com.shuiwangzhijia.shuidian.bean.BuyOrderListBean;
import com.shuiwangzhijia.shuidian.bean.GoodsBean;
import com.shuiwangzhijia.shuidian.bean.OrderBean;
import com.shuiwangzhijia.shuidian.bean.PurchaseListBean;
import com.shuiwangzhijia.shuidian.dialog.HintDialog;
import com.shuiwangzhijia.shuidian.event.LoginOutEvent;
import com.shuiwangzhijia.shuidian.event.OrderListFlashEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.ui.CommitOrderActivity;
import com.shuiwangzhijia.shuidian.ui.OperationOrderActivityNew;
import com.shuiwangzhijia.shuidian.ui.OrderPayActivity;
import com.shuiwangzhijia.shuidian.ui.PurchaseDetailsActivity;
import com.shuiwangzhijia.shuidian.utils.MeasureUtil;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;
import com.shuiwangzhijia.shuidian.view.SpacesItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PurchaseOrderFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, PurchaseOrderAdapter.OnViewItemClickListener {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.rlEmpty)
    RelativeLayout rlEmpty;
    private Unbinder unbinder;
    private int type;
    private int PageSize;
    private LinearLayoutManager layoutManager;
    private PurchaseOrderAdapter mOrderAdapter;
    private boolean isInit;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_purchase_order, container, false);
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
        PageSize = 5;
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        isInit = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            type = getArguments().getInt("type", 0);
            onRefresh();
            KLog.d(type);
        }
    }


    private void initRecyclerView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_30adfd));
        layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
      /*  DividerItemDecoration divider = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.divider_bg));*/
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(getActivity(), MeasureUtil.dip2px(getActivity(),10)));
        mOrderAdapter = new PurchaseOrderAdapter(mContext);
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
                        showLoad();
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

    private void completeSwipeRefresh() {
        if (swipeRefreshLayout == null)
            return;
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        if (isInit){
            showLoad();
        }
        page = -1;
        new LoadDataThread().start();
    }

    @Override
    public void onMoreOrderClick(final int position) {
        HintDialog hintDialog = new HintDialog(mContext, "确认再来一单？", new HintDialog.OnConfirmClickListener() {
            @Override
            public void onConfirm(Dialog dialog) {
                dialog.dismiss();
                BuyOrderListBean item = mOrderAdapter.getItem(position);
                PurchaseListBean purchaseListBean = item.getList().get(0);
                List<GoodsBean> data = purchaseListBean.getList();
                CommitOrderActivity.startAtc(mContext,data);
//                List<GoodsBean> list = purchaseListBean.getList();
//                RetrofitUtils.getInstances().create().anotherOrder(CommonUtils.getToken(), item.getTotal_price(), list.get(0).getAddr(), packageData(list), "", "", 0).enqueue(new Callback<EntityObject<OrderBean>>() {
//                    @Override
//                    public void onResponse(Call<EntityObject<OrderBean>> call, Response<EntityObject<OrderBean>> response) {
//                        hintLoad();
//                        EntityObject<OrderBean> body = response.body();
//                        if (body.getCode() == 200) {
//                            OrderBean result = body.getResult();
//                            OrderPayActivity.startAtc(mContext, result, 1);
//                        } else {
//                            hint("提交失败");
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<EntityObject<OrderBean>> call, Throwable t) {
//
//                    }
//                });
            }
        });
        hintDialog.show();

    }

    private String packageData(List<GoodsBean> data) {
        if (data == null || data.size() == 0) {
            return "";
        }
        JSONArray array = new JSONArray();
        JSONObject item = null;
        for (GoodsBean bean : data) {
            item = new JSONObject();
            try {
                item.put("did", bean.getDid());
                item.put("gid", bean.getGid());
                item.put("num", bean.getNum());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(item);
        }
        Log.i("json", array.toString());
        return array.toString();
    }

    @Override
    public void onSendClick(final String orderNo, final int status) {
        HintDialog hintDialog = new HintDialog(mContext, "确认开始配送？", new HintDialog.OnConfirmClickListener() {
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
        HintDialog hintDialog = new HintDialog(mContext, "提交确认到达？", new HintDialog.OnConfirmClickListener() {
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
        HintDialog hintDialog = new HintDialog(mContext, "确认拨打电话？", new HintDialog.OnConfirmClickListener() {
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
    public void onDetailClick(int position) {
        PurchaseDetailsActivity.startAtc(mContext, mOrderAdapter.getItem(position));
    }

    @Override
    public void onPayClick(final String orderNo, final long time, final String price, final int status, final int bstatus, final int did) {
        HintDialog hintDialog = new HintDialog(mContext, "确认去支付？", new HintDialog.OnConfirmClickListener() {
            @Override
            public void onConfirm(Dialog dialog) {
                dialog.dismiss();
                OrderBean data = new OrderBean();
                data.setOrder_noo(orderNo);
                data.setOrder_no(orderNo);
                data.setOrderTime(time);
                data.setStatus(status);
                data.setDstutas(bstatus);
                data.setDid(did);
                data.setAmount(Double.parseDouble(price));
                OrderPayActivity.startAtc(getActivity(), data, 1);
            }
        });
        hintDialog.show();
    }

    @Override
    public void onCancelClick(final String orderNo) {
        HintDialog hintDialog = new HintDialog(mContext, "确认取消订单？", new HintDialog.OnConfirmClickListener() {
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

    @Override
    public void onTakeOperation(int position) {
        Intent intent = new Intent(mContext, OperationOrderActivityNew.class);
        intent.putExtra("orderData", mOrderAdapter.getItem(position));
        startActivity(intent);
    }


    private void getList() {
        RetrofitUtils.getInstances().create().getPurchaseOrderList(type,page, PageSize).enqueue(new Callback<EntityObject<ArrayList<BuyOrderListBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<BuyOrderListBean>>> call, Response<EntityObject<ArrayList<BuyOrderListBean>>> response) {
                completeSwipeRefresh();
                hintLoad();
                EntityObject<ArrayList<BuyOrderListBean>> body = response.body();
                if (body.getCode() == 200) {
                    ArrayList<BuyOrderListBean> result = body.getResult();
                    if (result == null || result.size() == 0) {
                        if (page == 0) {
                            rlEmpty.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setVisibility(View.GONE);
                        }
                        return;
                    } else {
                        rlEmpty.setVisibility(View.GONE);
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                        ArrayList<BuyOrderListBean> data = new ArrayList<>();
//                        for (BuyOrderListBean bean : result) {
//                            int payStatus = bean.getPay_status();
//                            if (payStatus == 1) {
//                                BuyOrderListBean mBuyOrderListBean = null;
//                                List<PurchaseListBean> mdata;
//                                List<PurchaseListBean> list = bean.getList();
//                                for (PurchaseListBean listBean : list) {
//                                    mBuyOrderListBean = new BuyOrderListBean();
//                                    mdata = new ArrayList<>();
//                                    mdata.add(listBean);
//                                    mBuyOrderListBean.setList(mdata);
//                                    mBuyOrderListBean.setOrder_no(bean.getOrder_no());
//                                    mBuyOrderListBean.setOrder_time(listBean.getOrder_time());
//                                    mBuyOrderListBean.setUpdate_time(listBean.getOrder_time());
//                                    mBuyOrderListBean.setPay_status(1);
//                                    mBuyOrderListBean.setTotal_price(listBean.getCount_price());
//                                    mBuyOrderListBean.setTnum(listBean.getTotal());
//                                    data.add(mBuyOrderListBean);
//                                }
//                            } else {
//                                data.add(bean);
//                            }
//                        }
                        if (page == 0) {
                            mOrderAdapter.setData(result);
                        } else {
                            mOrderAdapter.addData(result);
                        }
                        loading = result.size() < PageSize ? false : true;
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
            public void onFailure(Call<EntityObject<ArrayList<BuyOrderListBean>>> call, Throwable t) {
                hintLoad();
            }
        });
    }

    /**
     * 模拟加载数据的线程
     */
    class LoadDataThread extends Thread {
        @Override
        public void run() {
            page = page + 1;
            getList();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0x101);//通过handler发送一个更新数据的标记
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(OrderListFlashEvent event) {
        if (event.isFlash) {
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
        }

    }

    private void hint(String text) {
        ToastUitl.showToastCustom(text);
    }
}
