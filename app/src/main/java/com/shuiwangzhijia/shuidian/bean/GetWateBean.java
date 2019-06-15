package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;

/**
 * Created by xxc on 2019/4/22.
 */

public class GetWateBean implements Serializable{

    /**
     * phone : 15575098380
     * province : 湖南省
     * city : 岳阳市
     * dist : 岳阳楼区
     * address : 路口菜市场
     */

    private String phone;
    private String province;
    private String city;
    private String dist;
    private String address;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
