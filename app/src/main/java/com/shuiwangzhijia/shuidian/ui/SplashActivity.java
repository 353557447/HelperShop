package com.shuiwangzhijia.shuidian.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;

import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.newmodule.activity.HomePageActivity;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;



@FndViewInject(contentViewId = R.layout.activity_splash)
public class SplashActivity extends BaseActivity {
    private static final int sleepTime = 2000;
    private LinearLayout rootLayout;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        setBaseBarHide();
        rootLayout = (LinearLayout) findViewById(R.id.rootLayout);
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(1500);
        rootLayout.startAnimation(animation);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
       // getVersionInfo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                }
                // 如果由信鸽通知调起的 Activity（发送推送的时候，如果要走这个逻辑必须是指定启动界面为：com.cjwsc.activity. HomeActivity ）
                XGPushClickedResult notifiShowedRlt = XGPushManager.onActivityStarted(SplashActivity.this);
                if (notifiShowedRlt != null) {
                    if (!TextUtils.isEmpty(notifiShowedRlt.getCustomContent())) {
                        try {
                            Intent intent = new Intent(SplashActivity.this, HomePageActivity.class);
                            Log.d("push", "主界面接收到推送信息跳转申请" + "status:");
                            JSONObject jsonObject = new JSONObject(notifiShowedRlt.getCustomContent());
                            int status = jsonObject.getInt("status");
                            intent.putExtra("status", status);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (!TextUtils.isEmpty(CommonUtils.getToken())) {
                    toMain();
                } else {
                    toLogin();
                }

            }


        }).start();

    }

   /* private void getVersionInfo() {
        String appVersionName = CommonUtils.getAppVersionName(this);
        RetrofitUtils.getInstances().create().getVersionInfo(0,1,appVersionName).enqueue(new Callback<EntityObject<GetVersionBean>>() {
            @Override
            public void onResponse(Call<EntityObject<GetVersionBean>> call, Response<EntityObject<GetVersionBean>> response) {
                EntityObject<GetVersionBean> body = response.body();
                if (body.getCode() == 200){
                    GetVersionBean result = body.getResult();
                    int online_stauts = result.getOnline_stauts();
                    KLog.d(online_stauts);
                    if (online_stauts == 0){
                        String s = "https://testapi.fndwater.com/";
                        Constant.setUrl(s);
                    }else {
                        String s = "https://api.fndwater.com/";
                        Constant.setUrl(s);
                    }
                }
            }

            @Override
            public void onFailure(Call<EntityObject<GetVersionBean>> call, Throwable t) {

            }
        });
    }*/

    private void toMain() {
        startActivity(new Intent(SplashActivity.this, HomePageActivity.class));
        finish();
    }

    private void toLogin() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }
}
