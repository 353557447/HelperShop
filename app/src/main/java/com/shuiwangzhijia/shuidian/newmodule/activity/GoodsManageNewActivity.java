package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.newmodule.fragment.GmInTheSaleFragment;
import com.shuiwangzhijia.shuidian.newmodule.fragment.GmLibraryOfGoodsFragment;
import com.shuiwangzhijia.shuidian.view.BaseBar;

import butterknife.BindView;

@FndViewInject(contentViewId = R.layout.activity_goods_manage_new, title = "商品管理")
public class GoodsManageNewActivity extends BaseActivity {
    @BindView(R.id.container)
    FrameLayout mContainer;
    @BindView(R.id.in_the_sale)
    RadioButton mInTheSale;
    @BindView(R.id.library_of_goods)
    RadioButton mLibraryOfGoods;
    @BindView(R.id.rg)
    RadioGroup mRg;
    private GmInTheSaleFragment mGmInTheSaleFragment;
    private GmLibraryOfGoodsFragment mGmLibraryOfGoodsFragment;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        mBaseBar.setBarListener(new BaseBar.BarListener() {
            @Override
            public void onLeftClick() {
                closeActivity();
            }

            @Override
            public void onRightClick() {

            }
        });
        selectedFragment(0);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int buttonId = group.getCheckedRadioButtonId();
                switch (buttonId){
                    case R.id.in_the_sale:
                        selectedFragment(0);
                        break;
                    case R.id.library_of_goods:
                        selectedFragment(1);
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
                if (mGmInTheSaleFragment == null) {
                    mGmInTheSaleFragment = new GmInTheSaleFragment();
                    transaction.add(R.id.container, mGmInTheSaleFragment);
                } else
                    transaction.show(mGmInTheSaleFragment);
                break;
            case 1:
                if (mGmLibraryOfGoodsFragment == null) {
                    mGmLibraryOfGoodsFragment = new GmLibraryOfGoodsFragment();
                    transaction.add(R.id.container, mGmLibraryOfGoodsFragment);
                } else
                    transaction.show(mGmLibraryOfGoodsFragment);
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mGmInTheSaleFragment != null)
            transaction.hide(mGmInTheSaleFragment);
        if (mGmLibraryOfGoodsFragment != null)
            transaction.hide(mGmLibraryOfGoodsFragment);
    }
}
