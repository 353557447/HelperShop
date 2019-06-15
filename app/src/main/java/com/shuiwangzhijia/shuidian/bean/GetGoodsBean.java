package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;

/**
 * Created by xxc on 2019/4/3.
 */

public class GetGoodsBean implements Serializable{

    /**
     * gid : 21250220
     * gname : 幸福
     * pprice : 2.00
     * did : 11
     * active : {"full":"123","links":[{"sid":21250220,"num":"1","name":"幸福水票"},{"sid":60470503,"num":"10","name":"高兴水票"},{"sid":21250220,"num":"20","name":"幸福水票"},{"sid":60470503,"num":"50","name":"高兴水票"}]}
     * activity_type : 2
     */

    private String gid;
    private String gname;
    private String pprice;
    private int did;
    private String active;
    private int activity_type;
    private int count;
    private String picturl;
    private int snum;
    private String price;

    public int getSnum() {
        return snum;
    }

    public void setSnum(int snum) {
        this.snum = snum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public String getPicturl() {
        return picturl;
    }

    public void setPicturl(String picturl) {
        this.picturl = picturl;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public int getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(int activity_type) {
        this.activity_type = activity_type;
    }
}
