package com.shuiwangzhijia.shuidian.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.SelectCouponsAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.ShowCouponBean;
import com.shuiwangzhijia.shuidian.event.CouponsEvent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class SelectCouponsActivity extends BaseAct {

    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.hintBtn)
    TextView mHintBtn;
    @BindView(R.id.payBtn)
    TextView mPayBtn;
    private ShowCouponBean data;
    private SelectCouponsAdapter mSelectCouponsAdapter;

    public static void startAtc(Context context, ShowCouponBean data) {
        Intent intent = new Intent(context, SelectCouponsActivity.class);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_coupons);
        ButterKnife.bind(this);
        setTitle("选择优惠券");
        data = (ShowCouponBean) getIntent().getSerializableExtra("data");
        initRecycleView();
        List<ShowCouponBean.ListBean> list = data.getList();
        mSelectCouponsAdapter.setData(list);
    }

    private void initRecycleView() {
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setHasFixedSize(true);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.divider_bg_tran));
        mRv.addItemDecoration(divider);
        mSelectCouponsAdapter = new SelectCouponsAdapter(this);
        mSelectCouponsAdapter.setOnItemClickListener(new SelectCouponsAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mSelectCouponsAdapter.setSelectIndex(position);
                mHintBtn.setText("已选"+mSelectCouponsAdapter.getItem(position).getSname()+"优惠券");
            }
        });
        mRv.setAdapter(mSelectCouponsAdapter);
    }

    @OnClick(R.id.payBtn)
    public void onViewClicked() {
        int selectIndex = mSelectCouponsAdapter.getSelectIndex();
        if (selectIndex == -1) {
            finish();
            EventBus.getDefault().post(new CouponsEvent(mSelectCouponsAdapter.getItem(0),2));
        } else {
            finish();
            EventBus.getDefault().post(new CouponsEvent(mSelectCouponsAdapter.getItem(selectIndex),1));
        }
    }
}
