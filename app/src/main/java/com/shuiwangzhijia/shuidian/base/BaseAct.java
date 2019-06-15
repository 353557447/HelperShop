package com.shuiwangzhijia.shuidian.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.dialog.LoadingDialog;
import com.shuiwangzhijia.shuidian.event.LoginOutEvent;
import com.shuiwangzhijia.shuidian.ui.CenterActivity;
import com.shuiwangzhijia.shuidian.ui.CommitOrderActivity;
import com.shuiwangzhijia.shuidian.ui.KeepListActivity;
import com.shuiwangzhijia.shuidian.ui.LoginActivity;
import com.shuiwangzhijia.shuidian.ui.MainActivity;


import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.DoubleUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.tencent.android.tpush.XGPushManager;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;


public class BaseAct extends AppCompatActivity implements View.OnClickListener {
    public ImageView backIv;
    TextView title;
    TextView rightTv;
    LinearLayout titleBar;
    FrameLayout frameLayout;
    public int PageSize = 10;
    public boolean loading;
    public int page = 0;
    private LoadingDialog mLoadingDialog;
    private View line;
    private ImageView rightIv;
    private ImmersionBar mImmersionBar;
    private InputMethodManager mIms;
    protected Gson mGson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        initBar();
        mGson = new Gson();
        mImmersionBar = ImmersionBar.with(this);
        if (this instanceof MainActivity || this instanceof CenterActivity
                || this instanceof KeepListActivity //
                || this instanceof CommitOrderActivity//
                || this instanceof LoginActivity
                ) {
            mImmersionBar.statusBarColor(R.color.transparent)
                    .statusBarDarkFont(true)
                    .init();
        }else {
            mImmersionBar.fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                    .statusBarColor(R.color.white)
                    .statusBarDarkFont(true)
                    .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                    .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
                    .init();
        }
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(Object messageEvent) {
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(LoginOutEvent event) {
        //解绑
        XGPushManager.delAccount(this, CommonUtils.getMobile());
        //反注册，建议在不需要接收推送的时候调用
        XGPushManager.unregisterPush(this);
        LoginActivity.startActLoginOut(this);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initBar() {
        titleBar = (LinearLayout) findViewById(R.id.titleBar);
        title = (TextView) findViewById(R.id.title);
        rightTv = (TextView) findViewById(R.id.rightTv);
        backIv = (ImageView) findViewById(R.id.backIv);
        rightIv = (ImageView) findViewById(R.id.rightIv);
        line = (View) findViewById(R.id.line);
        backIv.setOnClickListener(this);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
    }

    protected void setTitleBarBgColor(int id) {
        titleBar.setBackgroundColor(getResources().getColor(id));
        line.setBackgroundColor(getResources().getColor(id));
    }

    protected void setRightIvClickListener(int id, View.OnClickListener listener) {
        rightIv.setVisibility(View.VISIBLE);
        rightIv.setImageResource(id);
        rightIv.setOnClickListener(listener);
    }

    protected void setNoTitleBar() {
        titleBar.setVisibility(View.GONE);
    }

    protected void setNobackIv() {
        backIv.setVisibility(View.GONE);
    }

    protected void setTitle(String titleStr) {
        title.setText(titleStr);
    }

    protected void setRightTitle(String titleStr) {
        rightTv.setVisibility(View.VISIBLE);
        rightTv.setText(titleStr);
    }

    protected void setRightClickListener(View.OnClickListener listener) {
        rightTv.setOnClickListener(listener);
    }

    protected void setLeftImageResource(int id) {
        backIv.setImageResource(id);
    }

    @Override
    public void setContentView(int layoutResID) {
        View content = getLayoutInflater().inflate(layoutResID, frameLayout, false);
        frameLayout.removeAllViews();
        frameLayout.addView(content);
    }

    @Override
    public void setContentView(View view) {
        frameLayout.removeAllViews();
        frameLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        frameLayout.removeAllViews();
        frameLayout.addView(view, params);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backIv:
                finish();
                break;
        }
    }

    protected void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.mIms == null) {
            this.mIms = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.mIms != null)) {
            this.mIms.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }

    public void showLoad() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    protected void skipActivity(Class clz) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent(this, clz);
            startActivity(intent);
        }
    }

    protected void skipActivity(Class clz, Bundle bundle) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent(this, clz);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void hintLoad() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }
}
