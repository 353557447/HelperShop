package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.ui.ShopCouponActivity;
import com.shuiwangzhijia.shuidian.ui.ShopTicketActivity;
import com.shuiwangzhijia.shuidian.view.BaseBar;

import butterknife.BindView;
import butterknife.OnClick;

@FndViewInject(contentViewId = R.layout.activity_marketing_tools, title = "营销工具")
public class MarketingToolsActivity extends BaseActivity {

    @BindView(R.id.discount_coupon)
    LinearLayout mDiscountCoupon;
    @BindView(R.id.water_coupon)
    LinearLayout mWaterCoupon;
    @BindView(R.id.flash_sale)
    LinearLayout mFlashSale;

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
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }
    @OnClick({R.id.discount_coupon, R.id.water_coupon, R.id.flash_sale})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.discount_coupon:
                skipActivity(ShopCouponActivity.class);
                break;
            case R.id.water_coupon:
                skipActivity(ShopTicketActivity.class);
                break;
            case R.id.flash_sale:
                skipActivity(FlashSaleActivity.class);
                break;
        }
    }
}
