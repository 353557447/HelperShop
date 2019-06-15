package com.shuiwangzhijia.shuidian.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.tencent.android.tpush.XGPushManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SetActivity extends BaseAct {
    @BindView(R.id.addressBtn)
    TextView addressBtn;
    @BindView(R.id.loginOutBtn)
    TextView loginOutBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
        setTitle("设置");
    }

    @OnClick({R.id.addressBtn, R.id.aboutBtn, R.id.loginOutBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addressBtn:
                startActivity(new Intent(SetActivity.this, AddressManageActivity.class));
                break;
            case R.id.aboutBtn:
                startActivity(new Intent(SetActivity.this, AboutActivity.class));
                break;
            case R.id.loginOutBtn:
                CommonUtils.putToken("");
                CommonUtils.putDid("");
                CommonUtils.putLogin(false);
                //解绑
                XGPushManager.delAccount(this, CommonUtils.getMobile());
                //反注册，建议在不需要接收推送的时候调用
                XGPushManager.unregisterPush(this);
                startActivity(new Intent(SetActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }
}
