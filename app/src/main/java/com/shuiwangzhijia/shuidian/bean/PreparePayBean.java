package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;

/**
 * created by wangsuli on 2018/9/11.
 */
public class PreparePayBean implements Serializable {

    /**
     * price : 0
     * order_no : 2019031316586862
     * order_sn : 2019031316586833
     * pay : 测试六号
     * sail : null
     * pay_type : 换桶支付
     */

    private String price;
    private String order_no;
    private String order_sn;
    private String pay;
    private Object sail;
    private String pay_type;

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    private long create_time;
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public Object getSail() {
        return sail;
    }

    public void setSail(Object sail) {
        this.sail = sail;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }
}
