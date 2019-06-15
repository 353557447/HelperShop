package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by xxc on 2018/12/29.
 */

public class ShareShopBean {

    /**
     * header_pic : null
     * did : 4
     * sname : 车公庙水店
     * d_header_pic :
     * address : 深圳市福田区招商银行大厦
     * wxcode : uploads/WeChat/20181229/sRAaXD2E1546061498.png
     * picturl_arr : ["20180921/841118c5940a1232abe623fd49f2d3ef.jpg","20180921/045eb02782df0bdbfa29afc2892b27e7.jpg","20180921/8379d78ad6feb88665b46a56705041b6.jpg","20180921/bf28394ace53b728260bf18dfc3ce4a5.jpg","20180921/400203843c3c10bdc268463004bb09c9.jpg","20180921/a260d215b1f1dfa585823266a7281e3f.jpg"]
     */

    private Object header_pic;
    private int did;
    private String sname;
    private String d_header_pic;
    private String address;
    private String wxcode;
    private List<String> picturl_arr;

    public Object getHeader_pic() {
        return header_pic;
    }

    public void setHeader_pic(Object header_pic) {
        this.header_pic = header_pic;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getD_header_pic() {
        return d_header_pic;
    }

    public void setD_header_pic(String d_header_pic) {
        this.d_header_pic = d_header_pic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWxcode() {
        return wxcode;
    }

    public void setWxcode(String wxcode) {
        this.wxcode = wxcode;
    }

    public List<String> getPicturl_arr() {
        return picturl_arr;
    }

    public void setPicturl_arr(List<String> picturl_arr) {
        this.picturl_arr = picturl_arr;
    }
}
