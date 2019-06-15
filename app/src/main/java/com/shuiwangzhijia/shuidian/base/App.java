package com.shuiwangzhijia.shuidian.base;

import android.app.Application;
import android.content.Context;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.mob.MobSDK;
import com.socks.library.KLog;
import com.shuiwangzhijia.shuidian.network.OkgoRequest;
import com.shuiwangzhijia.shuidian.utils.ShareUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class App extends Application {
    private static App mApp;
    private static Context mApplicationContext;
    public static Application getInstance() {
        return mApp;
    }
    private static IWXAPI iwxapi;

    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this);
        ShareUtils.init(this);
        OkgoRequest.getInstance().initOkgo(this);
        mApp = this;
        KLog.init(true);
        //讯飞
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5b96724d");
        initData();
        ToastUtils.init(this);
        initWeixin();
    }
    private void initData() {
        mApplicationContext = getApplicationContext();
    }

    private void initWeixin() {
        iwxapi = WXAPIFactory.createWXAPI(this, Constant.WXAPPID, true); //初始化微信api
        iwxapi.registerApp(Constant.WXAPPID); //注册appid  appid可以在开发平台获取
    }

    public static IWXAPI getIwxapi() {
        return iwxapi;
    }

    // 获取ApplicationContext
    public static Context getContext() {
        return mApplicationContext;
    }
}
