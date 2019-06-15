package com.shuiwangzhijia.shuidian.bean;

/**
 * 倍数返利模型
 */

public class MultipleRMBean {
    //进度环形圈数据
    private int progress;
    //商品名称
    private String goodsName;
    //返利方式 1现金返利 2水票返利
    private int rWay;
    //返水票条件(多少桶)
    private int waterCouponCondition;
    //水票名称
    private String waterCouponName;
    //返利水票数量
    private int waterCouponCounts;
    //返现金条件(多少桶)
    private int moneyCondition;
    //返现金金额
    private String money;
    //已经完成多少
    private int completedCounts;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getrWay() {
        return rWay;
    }

    public void setrWay(int rWay) {
        this.rWay = rWay;
    }

    public int getWaterCouponCondition() {
        return waterCouponCondition;
    }

    public void setWaterCouponCondition(int waterCouponCondition) {
        this.waterCouponCondition = waterCouponCondition;
    }

    public String getWaterCouponName() {
        return waterCouponName;
    }

    public void setWaterCouponName(String waterCouponName) {
        this.waterCouponName = waterCouponName;
    }

    public int getWaterCouponCounts() {
        return waterCouponCounts;
    }

    public void setWaterCouponCounts(int waterCouponCounts) {
        this.waterCouponCounts = waterCouponCounts;
    }

    public int getMoneyCondition() {
        return moneyCondition;
    }

    public void setMoneyCondition(int moneyCondition) {
        this.moneyCondition = moneyCondition;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getCompletedCounts() {
        return completedCounts;
    }

    public void setCompletedCounts(int completedCounts) {
        this.completedCounts = completedCounts;
    }
}
