package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * created by wangsuli on 2018/10/11.
 */
public class ShopMarketData {
    private List<ShopMarketBean> coupon;

    public List<ShopMarketBean> getCoupon() {
        return coupon;
    }

    public void setCoupon(List<ShopMarketBean> coupon) {
        this.coupon = coupon;
    }

    public List<ShopMarketBean> getWater() {
        return water;
    }

    public void setWater(List<ShopMarketBean> water) {
        this.water = water;
    }

    private List<ShopMarketBean> water;
}
