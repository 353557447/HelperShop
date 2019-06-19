package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.bean.UpdateInfo;
import com.shuiwangzhijia.shuidian.dialog.NoticeDialog;
import com.shuiwangzhijia.shuidian.dialog.UpdatingDialog;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.ui.MainActivity;
import com.shuiwangzhijia.shuidian.ui.OrderManageActivity;
import com.shuiwangzhijia.shuidian.ui.TicketActivity;
import com.shuiwangzhijia.shuidian.xinge.XGNotification;
import com.socks.library.KLog;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.event.RedPointEvent;
import com.shuiwangzhijia.shuidian.newmodule.fragment.FeaturedFragment;
import com.shuiwangzhijia.shuidian.newmodule.fragment.MyShoppingCartFragment;
import com.shuiwangzhijia.shuidian.newmodule.fragment.PersonalFragment;
import com.shuiwangzhijia.shuidian.ui.LoginActivity;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@FndViewInject(contentViewId = R.layout.activity_home_page)
public class HomePageActivity extends BaseActivity {
    @BindView(R.id.container)
    FrameLayout mContainer;
    @BindView(R.id.featured_rb)
    RadioButton mFeaturedRb;
    @BindView(R.id.shopping_cart_rb)
    RadioButton mShoppingCartRb;
    @BindView(R.id.my_rb)
    RadioButton mMyRb;
    @BindView(R.id.rg)
    RadioGroup mRg;
    @BindView(R.id.red_point)
    TextView mRedPoint;
    private FeaturedFragment mFeaturedFragment;
    private MyShoppingCartFragment mShoppingCartFragment;
    private PersonalFragment mMyFragment;
    private MsgReceiver mMsgReceiver;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        setBaseBarHide();
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
        selectedFragment(0);
    }

    @Override
    protected void initData() {
        checkVersion();
        initXGPush();
    }

    private void initXGPush() {
        //开启信鸽的日志输出，线上版本不建议调用
        XGPushConfig.enableDebug(this, true);
        String token = XGPushConfig.getToken(this);
        KLog.e("token:"+token);
        //注册数据更新监听器
        mMsgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.shuiwangzhijia.shuidian.Receiver");
        registerReceiver(mMsgReceiver, intentFilter);
        XGPushManager.bindAccount(HomePageActivity.this, CommonUtils.getMobile());
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
   //             startActivity(new Intent(MainActivity.this, CouponActivity.class));
                break;
            case 2:
                startActivity(new Intent(HomePageActivity.this, TicketActivity.class));
                break;
            case 3:
                OrderManageActivity.startAtc(HomePageActivity.this, 0);
                break;
        }
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
                    String versionName = CommonUtils.getAppVersionName(HomePageActivity.this);
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
                        UpdatingDialog updatingDialog = new UpdatingDialog(HomePageActivity.this, result, isUpdate, false,download_type);
                        updatingDialog.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<EntityObject<UpdateInfo>> call, Throwable t) {

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
       if(!CommonUtils.isLogin()) {
           skipActivity(LoginActivity.class);
       }
    }

    @Override
    protected void initEvent() {
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int buttonId = radioGroup.getCheckedRadioButtonId();
                switch (buttonId) {
                    case R.id.featured_rb:
                        selectedFragment(0);
                        break;
                    case R.id.shopping_cart_rb:
                        selectedFragment(1);
                        break;
                    case R.id.my_rb:
                        selectedFragment(2);
                        break;
                }
            }
        });
    }

    private void selectedFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        switch (position) {
            case 0:
                if (mFeaturedFragment == null) {
                    mFeaturedFragment = new FeaturedFragment();
                    transaction.add(R.id.container, mFeaturedFragment);
                } else
                    transaction.show(mFeaturedFragment);
                break;
            case 1:
                if (mShoppingCartFragment == null) {
                    mShoppingCartFragment = new MyShoppingCartFragment();
                    transaction.add(R.id.container, mShoppingCartFragment);
                } else
                    transaction.show(mShoppingCartFragment);
                break;
            case 2:
                if (mMyFragment == null) {
                    mMyFragment = new PersonalFragment();
                    transaction.add(R.id.container, mMyFragment);
                } else
                    transaction.show(mMyFragment);
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mFeaturedFragment != null)
            transaction.hide(mFeaturedFragment);
        if (mShoppingCartFragment != null)
            transaction.hide(mShoppingCartFragment);
        if (mMyFragment != null)
            transaction.hide(mMyFragment);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void redPointCountsChanged(RedPointEvent event) {
        KLog.e("我执行了么");
        int redPointCounts = event.getRedPointCounts();
        if (redPointCounts == 0) {
            mRedPoint.setVisibility(View.GONE);
        } else {
            mRedPoint.setVisibility(View.VISIBLE);
            mRedPoint.setText(redPointCounts + "");
        }
    }

    /**
     * 推送消息
     */
    public class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            XGNotification data = (XGNotification) intent.getSerializableExtra("data");
            try {
                JSONObject jsonObject = new JSONObject(data.getCustom_content());
                String orderSn = jsonObject.getString("order_sn");
                String address = jsonObject.getString("address");
                NoticeDialog noticeDialog = new NoticeDialog(HomePageActivity.this, orderSn, address, new NoticeDialog.OnConfirmClickListener() {
                    @Override
                    public void onConfirm(Dialog dialog) {
                        dialog.dismiss();
                        OrderManageActivity.startAtc(HomePageActivity.this, 0);
                    }
                });
                noticeDialog.show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
