package com.shuiwangzhijia.shuidian.bean;


import com.google.gson.annotations.SerializedName;


public class PayBean {


    /**
     * appid : wxf8880011c1f9ba3d
     * noncestr : zcbag0jqj6pjv9ixunhjuvb52x0o6exj
     * package : Sign=WXPay
     * partnerid : 1539060071
     * prepayid : wx1300094442255672da2dca721501125500
     * timestamp : 1560355784
     * sign : E46BFA54FA8DB4910D6E9EBCF7986C1D
     */

    private String appid;
    private String noncestr;
    private String packageX;
    private String partnerid;
    private String prepayid;
    private long timestamp;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
