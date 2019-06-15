package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.FlashSaleBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.FlashSaleAdapter;
import com.shuiwangzhijia.shuidian.utils.MeasureUtil;
import com.shuiwangzhijia.shuidian.view.BaseBar;
import com.socks.library.KLog;
import com.shuiwangzhijia.shuidian.view.SpacesItemDecoration;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_flash_sale, title = "限时抢购")
public class FlashSaleActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.add_act)
    Button mAddAct;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<FlashSaleBean.DataBean> mList;
    private FlashSaleAdapter mFlashSaleAdapter;

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

    @Override
    protected void initData() {
      //  getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPage = 0;
        isLoadMore = false;
        getData();
    }

    private void getData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getFlashSaleList(mToken,mPage+"",mLimit+"").enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object =new JSONObject(datas);
                    int code = object.getInt("code");
                    if(code==200){
                        setCentreViewDismiss();
                        FlashSaleBean flashSaleBean = mGson.fromJson(datas, FlashSaleBean.class);
                        List<FlashSaleBean.DataBean> data = flashSaleBean.getData();
                        if(!isLoadMore){
                            mSwipeRefreshLayout.setRefreshing(false);
                            mList.clear();
                        }
                        mList.addAll(data);
                        mFlashSaleAdapter.notifyDataSetChanged();
                    }else{
                        if(!isLoadMore) {
                            setCentreViewShow(R.drawable.no_flash_sale_data, "还没有参加限时抢购活动");
                        }
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
                    isLoadMore = true;
                    mPage++;
                    getData();
                }
            }
        });
    }


    private void initRv() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_30adfd));
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addItemDecoration(new SpacesItemDecoration(this,MeasureUtil.dip2px(this,12)));
        mList = new ArrayList<>();
        mFlashSaleAdapter = new FlashSaleAdapter(this);
        mFlashSaleAdapter.setData(mList);
        mRv.setAdapter(mFlashSaleAdapter);
    }

    @OnClick({R.id.add_act})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_act:
                skipActivity(FlashSaleAddActivity.class);
                break;
        }
    }

    @Override
    public void onRefresh() {
        mPage = 0;
        isLoadMore = false;
        getData();
    }
}
