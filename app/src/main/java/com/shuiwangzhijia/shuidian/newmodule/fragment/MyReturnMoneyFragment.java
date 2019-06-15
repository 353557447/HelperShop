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
import com.shuiwangzhijia.shuidian.bean.MyReturnMoneyRecordBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.MyReturnMoneyAdapter;
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
@FndViewInject(contentViewId = R.layout.fragment_my_return_money)
public class MyReturnMoneyFragment extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private int mType;
    private MyReturnMoneyAdapter mMyReturnMoneyAdapter;
    private int mDid;
    private List<MyReturnMoneyRecordBean.DataBean> mList;

    public MyReturnMoneyFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView(View view) {
        Bundle bundle = getArguments();
        mType = bundle.getInt("type");
        mDid = bundle.getInt("did");
        initRv();
    }


    private void initRv() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_30adfd));
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mList = new ArrayList<>();
        mMyReturnMoneyAdapter = new MyReturnMoneyAdapter(mContext);
        mMyReturnMoneyAdapter.setData(mList);
        mRv.setAdapter(mMyReturnMoneyAdapter);
    }

    @Override
    protected void lazyLoadData() {
        getData();
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

    private void getData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getMyReturnMoneyRecord(mToken, mDid, mType, mPage, mLimit).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        setCentreViewDismiss();
                        MyReturnMoneyRecordBean myReturnMoneyRecordBean = mGson.fromJson(datas, MyReturnMoneyRecordBean.class);
                        List<MyReturnMoneyRecordBean.DataBean> data = myReturnMoneyRecordBean.getData();
                        if(!isLoadMore){
                            mList.clear();
                            mList.addAll(data);
                            mSwipeRefreshLayout.setRefreshing(false);
                        }else{
                            mList.addAll(data);
                        }
                        mMyReturnMoneyAdapter.notifyDataSetChanged();
                    } else {
                        if(!isLoadMore) {
                            mSwipeRefreshLayout.setRefreshing(false);
                            setCentreViewShow(R.drawable.no_consume_data, "暂无返利记录");
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
    public void onRefresh() {
        mPage = 0;
        isLoadMore = false;
        getData();
    }
}
