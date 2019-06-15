package com.shuiwangzhijia.shuidian.bean;


/**
 * created by wangsuli on 2018/10/23.
 */
public class BucketPayBean {

    /**
     * order_no : 2018102349017417
     * sid : 16
     * did : 20
     * price : 40
     * num : 1
     * pay_way : wx
     * create_time : 1540266473
     * update_time : 1540266473
     */

    private String order_no;
    private int sid;
    private int did;
    /**
     * bucket_order_sn : 2019011456582395
     * gid : 31337637
     * empty_policy : 买1送4水票
     * update_time : 1547445018
     * cost_price : ￥0.04
     */

    private String bucket_order_sn;
    private String gid;
    private String empty_policy;
    private String cost_price;

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public long getCreate_time() {
        return create_time;
    }

    public long getUpdate_time() {
        return update_time;
    }

    private double price;
    private String num;
    private String pay_way;

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    private long create_time;
    private long update_time;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }


    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPay_way() {
        return pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }


    public String getBucket_order_sn() {
        return bucket_order_sn;
    }

    public void setBucket_order_sn(String bucket_order_sn) {
        this.bucket_order_sn = bucket_order_sn;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getEmpty_policy() {
        return empty_policy;
    }

    public void setEmpty_policy(String empty_policy) {
        this.empty_policy = empty_policy;
    }

    public String getCost_price() {
        return cost_price;
    }

    public void setCost_price(String cost_price) {
        this.cost_price = cost_price;
    }
}
