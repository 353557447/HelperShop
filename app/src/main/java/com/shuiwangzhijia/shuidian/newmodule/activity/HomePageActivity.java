package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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

import butterknife.BindView;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;


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
}
