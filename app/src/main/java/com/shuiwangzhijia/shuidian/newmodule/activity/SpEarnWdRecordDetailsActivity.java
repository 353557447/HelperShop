package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.SpEarnRecordDetailsBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.SpEarnWdRecordDetailsAdapter;
import com.shuiwangzhijia.shuidian.view.BaseBar;
import com.shuiwangzhijia.shuidian.view.SpacesItemDecoration;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_sp_earn_wd_record_details, title = "提现明细")
public class SpEarnWdRecordDetailsActivity extends BaseActivity {
    @BindView(R.id.rv)
    RecyclerView mRv;
    private SpEarnWdRecordDetailsAdapter mSpEarnWdRecordDetailsAdapter;
    private List<SpEarnRecordDetailsBean.DataBean> mList;
    private String mForwardCode;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            mForwardCode = bundle.getString("forwardCode");
        }
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
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addItemDecoration(new SpacesItemDecoration(20, this));
        mSpEarnWdRecordDetailsAdapter = new SpEarnWdRecordDetailsAdapter(this);
        mList = new ArrayList<>();
        mSpEarnWdRecordDetailsAdapter.setData(mList);
        mRv.setAdapter(mSpEarnWdRecordDetailsAdapter);
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getShopEarnWdRecordDetails(mToken,mForwardCode).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object =new JSONObject(datas);
                    int code = object.getInt("code");
                    if(code==200){
                        SpEarnRecordDetailsBean spEarnRecordDetailsBean = mGson.fromJson(datas, SpEarnRecordDetailsBean.class);
                        List<SpEarnRecordDetailsBean.DataBean> data = spEarnRecordDetailsBean.getData();
                        if(data.isEmpty())
                            setCentreViewShow(R.drawable.no_order_data_icon, "还没有提现明细~");
                        mList.addAll(data);
                        mSpEarnWdRecordDetailsAdapter.notifyDataSetChanged();
                    }else{

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

    }
}
