package com.shuiwangzhijia.shuidian.bean;

/**
 * created by wangsuli on 2018/8/23.
 */
public class ConsumerDetailBean {

    private int stype;

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public long getUpdate_time() {
        return update_time;
    }

    private long update_time;
    private String amount;
    /**
     * id : 49
     * type : 1
     * order_sn : 2018091432346035
     * update_time : 1537158500
     * name : 采购订单
     */

    private int id;
    private int type;
    private String order_sn;
    private String name;

    public int getStype() {
        return stype;
    }

    public void setStype(int stype) {
        this.stype = stype;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
