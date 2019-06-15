package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.ReturnMoneyRtypeElseBean;
import com.shuiwangzhijia.shuidian.bean.ReturnMoneyRtypeOneBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.ReturnMoneyRecordAdapter;
import com.shuiwangzhijia.shuidian.utils.GenerateList;
import com.shuiwangzhijia.shuidian.view.BaseBar;
import com.shuiwangzhijia.shuidian.view.SpacesItemDecoration;
import com.socks.library.KLog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_return_money_record, title = "返利记录")
public class ReturnMoneyRecordActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Integer> mReturnMoneyDetailsList;
    private ReturnMoneyRecordAdapter mRetrunMoneyRecordAdapter;
    private int mDid;
    private int mRid;
    private List<ReturnMoneyRtypeElseBean> mReturnMoneyRtypeElseList;
    private List<ReturnMoneyRtypeElseBean> mReturnMoneyRtypeElseLoadMoreList;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        mDid = bundle.getInt("did");
        mRid = bundle.getInt("rid");
        mBaseBar.setBarListener(new BaseBar.BarListener() {
            @Override
            public void onLeftClick() {
                closeActivity();
            }

            @Override
            public void onRightClick() {

            }
        });
        initReturnMoneyRv();
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        /*token	是	string	token
        did	是	int	水厂did
        r_id	是	int	模板ID
        start	否	int	默认0页
        limit	否	int	默认10条*/
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getReturnMoneyRecordByTem(mToken, mDid, mRid, mPage, mLimit).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (mSwipeRefreshLayout != null)
                        mSwipeRefreshLayout.setRefreshing(false);
                    if (code == 200) {
                        JSONArray data = object.getJSONArray("data");
                        int rType = data.getJSONObject(0).getInt("r_type");
                        if (rType == 1) {
                            ReturnMoneyRtypeOneBean returnMoneyRtypeOneBean = mGson.fromJson(datas, ReturnMoneyRtypeOneBean.class);
                            List<ReturnMoneyRtypeOneBean.DataBean> returnMoneyRtypeOneList = returnMoneyRtypeOneBean.getData();
                            //单笔返利
                            if (!isLoadMore) {
                                mRetrunMoneyRecordAdapter = new ReturnMoneyRecordAdapter(ReturnMoneyRecordActivity.this, 1);
                                mRetrunMoneyRecordAdapter.setDataRtypeOne(returnMoneyRtypeOneList);
                                mRv.setAdapter(mRetrunMoneyRecordAdapter);
                            } else {
                                mRetrunMoneyRecordAdapter.addDataRtypeOne(returnMoneyRtypeOneList);
                            }
                        } else {
                            //其他
                            if (!isLoadMore) {
                                mRetrunMoneyRecordAdapter = new ReturnMoneyRecordAdapter(ReturnMoneyRecordActivity.this, 2);
                                mReturnMoneyRtypeElseList = new ArrayList<>();
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject jsonObject = data.getJSONObject(i);
                                    ReturnMoneyRtypeElseBean bean = new ReturnMoneyRtypeElseBean();
                                    bean.setRule(jsonObject.getInt("rule"));
                                    bean.setRbasis(jsonObject.getInt("rbasis"));
                                    bean.setR_way(jsonObject.getInt("r_way"));
                                    bean.setRebate_date(jsonObject.getString("rebate_date"));
                                    bean.setStatus(jsonObject.getInt("status"));
                                    bean.setTotal_amount(jsonObject.getString("total_amount"));
                                    bean.setS_count(jsonObject.getInt("s_count"));
                                    bean.setData(jsonObject.getJSONArray("goods"));
                                    bean.setRebate_no(jsonObject.getString("rebate_no"));
                                    mReturnMoneyRtypeElseList.add(bean);
                                }
                                mRetrunMoneyRecordAdapter.setDataRtypeElse(mReturnMoneyRtypeElseList);
                                mRv.setAdapter(mRetrunMoneyRecordAdapter);
                            } else {
                                mReturnMoneyRtypeElseLoadMoreList = new ArrayList<>();
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject jsonObject = data.getJSONObject(i);
                                    ReturnMoneyRtypeElseBean bean = new ReturnMoneyRtypeElseBean();
                                    bean.setRule(jsonObject.getInt("rule"));
                                    bean.setRbasis(jsonObject.getInt("rbasis"));
                                    bean.setR_way(jsonObject.getInt("r_way"));
                                    bean.setRebate_date(jsonObject.getString("rebate_date"));
                                    bean.setStatus(jsonObject.getInt("status"));
                                    bean.setTotal_amount(jsonObject.getString("total_amount"));
                                    bean.setS_count(jsonObject.getInt("s_count"));
                                    bean.setData(jsonObject.getJSONArray("goods"));
                                    bean.setRebate_no(jsonObject.getString("rebate_no"));
                                    mReturnMoneyRtypeElseList.add(bean);
                                }
                               // mReturnMoneyRtypeElseList.addAll(mReturnMoneyRtypeElseLoadMoreList);
                                mRetrunMoneyRecordAdapter.addDataRtypeElse(mReturnMoneyRtypeElseLoadMoreList);
                            }
                        }
                    } else {
                        if (!isLoadMore) {
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

    private void initReturnMoneyRv() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_30adfd));
        mReturnMoneyDetailsList = GenerateList.getList(20);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(manager);
        mRv.addItemDecoration(new SpacesItemDecoration(20, this));

    }

    @Override
    public void onRefresh() {
        mPage = 0;
        isLoadMore = false;
        getData();
    }
}
