package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;

/**
 * Created by xxc on 2019/1/14.
 */

public class EmptyTongBean implements Serializable{

    /**
     * gid : 31337637
     * num : 1
     * empty_price : 0.01
     * picturl : 20190108/c1360394e7b3705d22dfd2bf21a64d7a.jpg
     * empty_policy : 买1送4水票
     * gname : 3号商品
     * sname : 0108
     */

    private int gid;
    private int num;
    private String empty_price;
    private String picturl;
    private String empty_policy;
    private String gname;
    private String sname;

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getEmpty_price() {
        return empty_price;
    }

    public void setEmpty_price(String empty_price) {
        this.empty_price = empty_price;
    }

    public String getPicturl() {
        return picturl;
    }

    public void setPicturl(String picturl) {
        this.picturl = picturl;
    }

    public String getEmpty_policy() {
        return empty_policy;
    }

    public void setEmpty_policy(String empty_policy) {
        this.empty_policy = empty_policy;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
}
