package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.event.CommonEvent;
import com.shuiwangzhijia.shuidian.newmodule.fragment.EquipGoodsFragment;
import com.shuiwangzhijia.shuidian.newmodule.fragment.EquipOrderFragment;
import com.shuiwangzhijia.shuidian.newmodule.fragment.MyEquipFragment;
import com.shuiwangzhijia.shuidian.view.BaseBar;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import de.greenrobot.event.EventBus;

@FndViewInject(contentViewId = R.layout.activity_smart_equip,title = "我的设备")
public class SmartEquipActivity extends BaseActivity {
    @BindView(R.id.my_equip_rb)
    RadioButton mMyEquipRb;
    @BindView(R.id.equip_goods_rb)
    RadioButton mEquipGoodsRb;
    @BindView(R.id.equip_order_rb)
    RadioButton mEquipOrderRb;
    @BindView(R.id.rg)
    RadioGroup mRg;
    private MyEquipFragment mMyEquipFragment;
    private EquipOrderFragment mEquipOrderFragment;
    private EquipGoodsFragment mEquipGoodsFragment;
    private ImmersionBar mImmerBar;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        mBaseBar.setRightBtnVisible(false);
        mBaseBar.setRightTv("添加商品");
        mBaseBar.setRightTvColor("#2995F1");
        mBaseBar.setBarListener(new BaseBar.BarListener() {
            @Override
            public void onLeftClick() {
                closeActivity();
            }

            @Override
            public void onRightClick() {
                EventBus.getDefault().post(new CommonEvent("equipGoodsAddGoods"));
            }
        });
        mImmerBar = ImmersionBar.with(this);

        mBaseBar.setBarColor("#425274");
        mBaseBar.setbackIconRes(R.drawable.back);
        mBaseBar.setTitleColor("#ffffff");
        mImmerBar
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(R.color.color_425274)
                .statusBarDarkFont(false)
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
                .init();



        selectedFragment(0);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int buttonId = radioGroup.getCheckedRadioButtonId();
                switch (buttonId) {
                    case R.id.my_equip_rb:
                        mBaseBar.setBarColor("#425274");
                        mBaseBar.setbackIconRes(R.drawable.back);
                        mBaseBar.setTitleColor("#ffffff");
                        mBaseBar.setRightBtnVisible(false);
                        mBaseBar.setTitle("我的设备");
                        mImmerBar
                                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                                .statusBarColor(R.color.color_425274)
                                .statusBarDarkFont(false)
                                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
                                .init();

                        selectedFragment(0);
                        break;
                    case R.id.equip_goods_rb:
                        mBaseBar.setBarColor("#ffffff");
                        mBaseBar.setbackIconRes(R.drawable.black_back);
                        mBaseBar.setTitleColor("#000000");
                        mBaseBar.setRightBtnVisible(true);
                        mBaseBar.setTitle("设备商品");
                        mImmerBar
                                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                                .statusBarColor(R.color.white)
                                .statusBarDarkFont(true)
                                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
                                .init();

                        selectedFragment(1);
                        break;
                    case R.id.equip_order_rb:
                        mBaseBar.setBarColor("#ffffff");
                        mBaseBar.setbackIconRes(R.drawable.black_back);
                        mBaseBar.setTitleColor("#000000");
                        mBaseBar.setRightBtnVisible(false);
                        mBaseBar.setTitle("设备订单");
                        mImmerBar
                                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                                .statusBarColor(R.color.white)
                                .statusBarDarkFont(true)
                                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
                                .init();

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
                if (mMyEquipFragment == null) {
                    mMyEquipFragment = new MyEquipFragment();
                    transaction.add(R.id.container, mMyEquipFragment);
                } else
                    transaction.show(mMyEquipFragment);
                break;
            case 1:
                if (mEquipGoodsFragment == null) {
                    mEquipGoodsFragment = new EquipGoodsFragment();
                    transaction.add(R.id.container, mEquipGoodsFragment);
                } else
                    transaction.show(mEquipGoodsFragment);
                break;
            case 2:
                if (mEquipOrderFragment == null) {
                    mEquipOrderFragment = new EquipOrderFragment();
                    transaction.add(R.id.container, mEquipOrderFragment);
                } else
                    transaction.show(mEquipOrderFragment);
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mMyEquipFragment != null)
            transaction.hide(mMyEquipFragment);
        if (mEquipGoodsFragment != null)
            transaction.hide(mEquipGoodsFragment);
        if (mEquipOrderFragment != null)
            transaction.hide(mEquipOrderFragment);
    }

}
