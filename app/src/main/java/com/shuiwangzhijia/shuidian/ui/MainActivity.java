package com.shuiwangzhijia.shuidian.ui;

import com.shuiwangzhijia.shuidian.base.BaseAct;

/**
 * 主页
 * created by wangsuli on 2018/8/17.
 */
public class MainActivity extends BaseAct {
   /* private FragmentTabHost mainTabHost;
    private int index = 0;
    private MsgReceiver mMsgReceiver;
    private NetworkConnectChangedReceiver mReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setNoTitleBar();
        initHost();
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            for (int i = 0; i < permissions.length; i++) {
                if (checkSelfPermission(permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(permissions, i);
                }
            }

        }
        checkVersion();
        initXGPush();
        fromNoticeMessage(getIntent());
        initNetStatus();
        KLog.d(Constant.url);
    }

    private void initNetStatus() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mReceiver = new NetworkConnectChangedReceiver();
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        fromNoticeMessage(intent);
    }
    private void fromNoticeMessage(Intent intent) {
        int status = intent.getIntExtra("status", -1);
        switch (status) {
            case 1:
//                startActivity(new Intent(MainActivity.this, CouponActivity.class));
                break;
            case 2:
                startActivity(new Intent(MainActivity.this, TicketActivity.class));
                break;
            case 3:
                OrderManageActivity.startAtc(MainActivity.this, 0);
                break;
        }
    }


    private void initXGPush() {
        //开启信鸽的日志输出，线上版本不建议调用
        XGPushConfig.enableDebug(this, true);
        XGPushConfig.getToken(this);
        //注册数据更新监听器
        mMsgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.fnd.waterstore.Receiver");
        registerReceiver(mMsgReceiver, intentFilter);
        XGPushManager.bindAccount(MainActivity.this, CommonUtils.getMobile());
    }

    private void checkVersion() {
        RetrofitUtils.getInstances().create().getAppVersionInfo(0, 1).enqueue(new Callback<EntityObject<UpdateInfo>>() {
            @Override
            public void onResponse(Call<EntityObject<UpdateInfo>> call, Response<EntityObject<UpdateInfo>> response) {
                EntityObject<UpdateInfo> body = response.body();
                if (body.getCode() == 200) {
                    UpdateInfo result = body.getResult();
                    if (result == null) {
                        return;
                    }
                    int download_type = result.getDownload_type();
                    String versionName = CommonUtils.getAppVersionName(MainActivity.this);
                    String version = result.getVersion();
                    int isUpdate = -1;
                    if (CommonUtils.isUpdate(version, versionName)) {
                        //最新版本比当前高 建议更新
                        isUpdate = 1;
                    }
                    if (CommonUtils.isUpdate(version, versionName)) {
                        //支持的最低版本比当前高 强制更新
                        isUpdate = 2;
                    }
                    if (isUpdate != -1) {
                        UpdatingDialog updatingDialog = new UpdatingDialog(MainActivity.this, result, isUpdate, false,download_type);
                        updatingDialog.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<EntityObject<UpdateInfo>> call, Throwable t) {

            }
        });

    }

    *//***
     * 初始化底部导航栏
     *//*
    private void initHost() {
        mainTabHost = (FragmentTabHost) findViewById(R.id.mainTab);
        //调用setup方法 设置view
        mainTabHost.setup(this, getSupportFragmentManager(), R.id.mainView);
        //去除分割线
        mainTabHost.getTabWidget().setDividerDrawable(null);
        //监听事件
        mainTabHost.setOnTabChangedListener(this);
        initTab();
        //默认选中
        mainTabHost.setCurrentTab(index);
    }

    *//**
     * 创建子tab
     *//*
    private void initTab() {
        List<GoodsBean> shopCart = CommonUtils.getShopCart();
        if(shopCart==null){
            shopCart=new ArrayList<>();
        }
        String[] tabs = MainTabDb.getTabsTxt();
        for (int i = 0; i < tabs.length; i++) {
            //新建TabSpec
            TabHost.TabSpec mTabSpec = mainTabHost.newTabSpec(tabs[i]);
            View view = LayoutInflater.from(this).inflate(R.layout.main_tab, null);
            TextView mNum = (TextView) view.findViewById(R.id.num);
            if (i==1){
                mNum.setVisibility(View.VISIBLE);
                mNum.setText(shopCart.size()+"");
            }else {
                mNum.setVisibility(View.GONE);
            }
            ((TextView) view.findViewById(R.id.foot_tv)).setText(tabs[i]);
            ((ImageView) view.findViewById(R.id.foot_iv)).setImageResource(MainTabDb.getTabsImg()[i]);
            mTabSpec.setIndicator(view);
            //加入TabSpec
            mainTabHost.addTab(mTabSpec, MainTabDb.getFramgent()[i], null);
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(GoodsEvent event) {
        KLog.d(event.count);
        TextView numText = (TextView) mainTabHost.getTabWidget().getChildAt(1).findViewById(R.id.num);
        if (event.count >= 10){
            numText.setText("9+");
        }else {
            numText.setText(event.count+"");
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        int indexCurrentTab = MainTabDb.getTabsIndex(tabId);
        //从分割线中获得多少个切换界面
        TabWidget tabw = mainTabHost.getTabWidget();
        for (int i = 0; i < tabw.getChildCount(); i++) {
            View v = tabw.getChildAt(i);
            TextView tv = (TextView) v.findViewById(R.id.foot_tv);
            ImageView iv = (ImageView) v.findViewById(R.id.foot_iv);
            //修改当前的界面按钮颜色图片
            if (i == indexCurrentTab) {
                tv.setTextColor(getResources().getColor(R.color.color_425274));
                iv.setImageResource(MainTabDb.getTabsImgLight()[i]);
            } else {
                tv.setTextColor(getResources().getColor(R.color.color_999999));
                iv.setImageResource(MainTabDb.getTabsImg()[i]);
            }
        }

    }

    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    //如果两次按键时间间隔大于2秒，则不退出
                    ToastUitl.showToastCustom("再按一次退出水的快递-水店端");
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {
                    moveTaskToBack(false);
                    return super.onKeyDown(keyCode, event);

                }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mMsgReceiver != null)
            unregisterReceiver(mMsgReceiver);
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(Object messageEvent) {
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(LoginOutEvent event) {
        LoginActivity.startActLoginOut(this);
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(MainEvent event) {
        mainTabHost.setCurrentTab(1);
    }


    *//**
     * 推送消息
     *//*
    public class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            XGNotification data = (XGNotification) intent.getSerializableExtra("data");
            try {
                JSONObject jsonObject = new JSONObject(data.getCustom_content());
                String orderSn = jsonObject.getString("order_sn");
                String address = jsonObject.getString("address");
                NoticeDialog noticeDialog = new NoticeDialog(MainActivity.this, orderSn, address, new NoticeDialog.OnConfirmClickListener() {
                    @Override
                    public void onConfirm(Dialog dialog) {
                        dialog.dismiss();
                        OrderManageActivity.startAtc(MainActivity.this, 0);
                    }
                });
                noticeDialog.show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void activityCenter(CommonEvent event) {
        KLog.d("0000");
        if ("goto_shopcart".equals(event.getMsg())) {
            mainTabHost.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mainTabHost.setCurrentTab(1);
                }
            },30);
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void getMy(CommonEvent event) {
        if ("go_my".equals(event.getMsg())) {
            mainTabHost.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mainTabHost.setCurrentTab(2);
                }
            },30);
        }
    }*/

}
