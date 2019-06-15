package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;

/**
 * created by wangsuli on 2018/9/27.
 */
public class TicketOrderBean implements Serializable{
    private TicketBean data;

    /**
     * order_no : 201809292750934
     * tprice : 50
     * tnum : 30
     * sid : 16
     * pay_status : 0
     * create_time : 1538192450
     * update_time : 1538192450
     */

    private String order_no;
    private double tprice;
    private int tnum;
    private int sid;
    private int pay_status;
    private int create_time;
    private int update_time;

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    private int did;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public double getTprice() {
        return tprice;
    }

    public void setTprice(double tprice) {
        this.tprice = tprice;
    }

    public int getTnum() {
        return tnum;
    }

    public void setTnum(int tnum) {
        this.tnum = tnum;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    public TicketBean getData() {
        return data;
    }

    public void setData(TicketBean data) {
        this.data = data;
    }
}
