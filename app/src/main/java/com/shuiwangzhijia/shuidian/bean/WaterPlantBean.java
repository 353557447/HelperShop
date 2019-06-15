package com.shuiwangzhijia.shuidian.bean;

/**
 * created by wangsuli on 2018/10/19.
 */
public class WaterPlantBean {
    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "WaterPlantBean{" +
                "isOk=" + isOk +
                ", limitNum=" + limitNum +
                ", total=" + total +
                ", sname=" + sname +
                '}';
    }

    private boolean isOk;//是否可以下单
    private int limitNum;//单个厂的起送量
    private int total;//购买的单个厂的总数量

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    private String sname;//厂名称
}
