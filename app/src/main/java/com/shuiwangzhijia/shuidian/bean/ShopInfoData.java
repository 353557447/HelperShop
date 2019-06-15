package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;

/**
 * created by wangsuli on 2018/10/15.
 */
public class ShopInfoData implements Serializable{
    /**
     * header_pic :
     * sname : 华能水店2
     * address : 华强北
     * lnglat : 114.092903,22.549591
     * is_perfect : 0
     * account : 13877777777
     */

    private String header_pic;
    private String sname;
    private String province;
    private String city;
    private String dist;
    private String address;
    private String lnglat;
    private int is_perfect;
    private String account;

    public String getProvice() {
        return province;
    }

    public void setProvice(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getHeader_pic() {
        return header_pic;
    }

    public void setHeader_pic(String header_pic) {
        this.header_pic = header_pic;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
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

    public int getIs_perfect() {
        return is_perfect;
    }

    public void setIs_perfect(int is_perfect) {
        this.is_perfect = is_perfect;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
