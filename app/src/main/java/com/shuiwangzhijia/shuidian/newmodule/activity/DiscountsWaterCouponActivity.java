package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.DiscountsWaterCouponBean;
import com.shuiwangzhijia.shuidian.bean.TicketBean;
import com.shuiwangzhijia.shuidian.bean.TicketOrderBean;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.DiscountsWaterCouponAdapter;
import com.shuiwangzhijia.shuidian.ui.TicketPayActivity;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;
import com.shuiwangzhijia.shuidian.view.BaseBar;
import com.shuiwangzhijia.shuidian.view.SpacesItemDecoration;
import com.socks.library.KLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_discounts_water_coupon,title = "优惠水票")
public class DiscountsWaterCouponActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, DiscountsWaterCouponAdapter.OnItemBuyClickListener {
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<DiscountsWaterCouponBean.DataBean> mList;
    private DiscountsWaterCouponAdapter mDiscountsWaterCouponAdapter;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        mBaseBar.setBarListener(new BaseBar.BarListener() {
            @Override
            public void onLeftClick() {
                closeActivity();
            }

            @Override
            public void onRightClick() {

            }
        });
        initRv();
    }

    private void initRv() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_30adfd));
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addItemDecoration(new SpacesItemDecoration(20, this));
        mList = new ArrayList<DiscountsWaterCouponBean.DataBean>();
        mDiscountsWaterCouponAdapter = new DiscountsWaterCouponAdapter(this);
        mDiscountsWaterCouponAdapter.setOnItemBuyClickListener(this);
        mDiscountsWaterCouponAdapter.setData(mList);
        mRv.setAdapter(mDiscountsWaterCouponAdapter);
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getDiscountWaterCouponList(mPage,mLimit).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object =new JSONObject(datas);
                    int code = object.getInt("code");
                    mSwipeRefreshLayout.setRefreshing(false);
                    if(code==200){
                        DiscountsWaterCouponBean discountsWaterCouponBean = mGson.fromJson(datas, DiscountsWaterCouponBean.class);
                        List<DiscountsWaterCouponBean.DataBean> data = discountsWaterCouponBean.getData();
                        if(!isLoadMore) {
                            mList.clear();
                            if(data.isEmpty()){
                                setCentreViewShow(R.drawable.wushuipiao,"暂无水票~");
                            }else{
                                setCentreViewDismiss();
                            }
                        }
                        mList.addAll(data);
                        mDiscountsWaterCouponAdapter.notifyDataSetChanged();
                    }else{
                        if(!isLoadMore)
                            setCentreViewShow(R.drawable.wushuipiao,"暂无水票~");
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
                    if(!mSwipeRefreshLayout.isRefreshing()) {
                        isLoadMore = true;
                        mPage++;
                        getData();
                    }
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
    public void onItemBuyClick(int position) {
        final TicketBean data =   mGson.fromJson(mGson.toJson(mList.get(position)),TicketBean.class) ;
        JSONArray array = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("s_gid", data.getS_gid());
            jsonObject.put("num", data.getSnum());
            array.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RetrofitUtils.getInstances().create().createTicketOrder(CommonUtils.getToken(), "" + data.getSprice(), array.toString()).enqueue(new Callback<EntityObject<TicketOrderBean>>() {
            @Override
            public void onResponse(Call<EntityObject<TicketOrderBean>> call, Response<EntityObject<TicketOrderBean>> response) {
                EntityObject<TicketOrderBean> body = response.body();
                if (body.getCode() == 200) {
//                    ToastUitl.showToastCustom("提交成功!");
                    TicketOrderBean result = body.getResult();
                    result.setData(data);
                    TicketPayActivity.startAtc(DiscountsWaterCouponActivity.this, result);
                } else {
                    ToastUitl.showToastCustom("提交失败!");
                }
            }

            @Override
            public void onFailure(Call<EntityObject<TicketOrderBean>> call, Throwable t) {

            }
        });
    }
}
