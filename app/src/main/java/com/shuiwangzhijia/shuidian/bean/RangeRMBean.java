package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * 范围返利模型
 */

public class RangeRMBean {
    //商品名称
    private String goodsName;
    /*   //返利规则 1倍数返利 2范围返利
       private int rule;*/

    //已经完成多少
    private int completedCounts;
    //返利方式
    private int rWay;

    public int getrWay() {
        return rWay;
    }

    public void setrWay(int rWay) {
        this.rWay = rWay;
    }

    private List<RuleProgressBean> list;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getCompletedCounts() {
        return completedCounts;
    }

    public void setCompletedCounts(int completedCounts) {
        this.completedCounts = completedCounts;
    }

    public List<RuleProgressBean> getList() {
        return list;
    }

    public void setList(List<RuleProgressBean> list) {
        this.list = list;
    }
}
