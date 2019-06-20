package com.shuiwangzhijia.shuidian.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseAct;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BucketManageActivity extends BaseAct {

    @BindView(R.id.buyBtn)
    TextView buyBtn;
    @BindView(R.id.recordBtn)
    TextView recordBtn;
    @BindView(R.id.orderBtn)
    TextView orderBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_manage);
        ButterKnife.bind(this);
        setTitle("空桶管理");
    }



    @OnClick({R.id.buyBtn, R.id.recordBtn, R.id.orderBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buyBtn:
                startActivity(new Intent(this,BucketListActivity.class));
                break;
            case R.id.recordBtn:
                startActivity(new Intent(this,BucketRecordActivity.class));
                break;
            case R.id.orderBtn:
                startActivity(new Intent(this,BucketOrderActivity.class));
                break;
        }
    }
}
