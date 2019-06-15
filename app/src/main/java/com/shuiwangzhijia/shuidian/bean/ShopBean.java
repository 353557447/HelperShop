package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;

/**
 * created by wangsuli on 2018/8/22.
 */
public class ShopBean implements Serializable {
    /**
     * sname : 红火水店
     * phone : 18682282668
     * address : 广东省深圳市福田区华发北路22 888号
     * lnglat : 114.0896,22.541576
     * is_business : 1
     * banlance : 99999899.99
     * busi_time : 02:00 02:04
     * is_free : 0
     * amount : 100.00
     * full_free : 200.00
     * effic : 1
     * picturl : 20180606/dca956b71a2eac2197b1bcc7cb12d04e.jpg
     * score : 4
     * sailsum : 90
     */

    private String sname;
    private String phone;
    private String address;
    private String lnglat;
    private int is_business;
    private String banlance;
    private String busi_time;
    private int is_free;
    private String amount;
    private String full_free;
    private String effic;
    private String picturl;
    private String announce;
    private float score;
    private int sailsum;

    public String getHeader_pic() {
        return header_pic;
    }

    public void setHeader_pic(String header_pic) {
        this.header_pic = header_pic;
    }

    private String header_pic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * sid : 9
     * distance : 12
     */

    private int id;//选择水厂的id
    private int sid;
    private String distance;

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getAnnounce() {
        return announce;
    }

    public void setAnnounce(String announce) {
        this.announce = announce;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLnglat() {
        return lnglat;
    }

    public void setLnglat(String lnglat) {
        this.lnglat = lnglat;
    }

    public int getIs_business() {
        return is_business;
    }

    public void setIs_business(int is_business) {
        this.is_business = is_business;
    }

    public String getBanlance() {
        return banlance;
    }

    public void setBanlance(String banlance) {
        this.banlance = banlance;
    }

    public String getBusi_time() {
        return busi_time;
    }

    public void setBusi_time(String busi_time) {
        this.busi_time = busi_time;
    }

    public int getIs_free() {
        return is_free;
    }

    public void setIs_free(int is_free) {
        this.is_free = is_free;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFull_free() {
        return full_free;
    }

    public void setFull_free(String full_free) {
        this.full_free = full_free;
    }

    public String getEffic() {
        return effic;
    }

    public void setEffic(String effic) {
        this.effic = effic;
    }

    public String getPicturl() {
        return picturl;
    }

    public void setPicturl(String picturl) {
        this.picturl = picturl;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getSailsum() {
        return sailsum;
    }

    public void setSailsum(int sailsum) {
        this.sailsum = sailsum;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
