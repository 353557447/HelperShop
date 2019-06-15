package com.shuiwangzhijia.shuidian.bean;

/**
 * Created by xxc on 2019/4/8.
 */

public class GenerateOrderBean {

    /**
     * order_no : 2019040416593181
     * orderTime : 1554365193
     * amount : 0.09
     * is_suc : 0
     */

    private String order_no;
    private int orderTime;
    private String amount;
    private int is_suc;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(int orderTime) {
        this.orderTime = orderTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getIs_suc() {
        return is_suc;
    }

    public void setIs_suc(int is_suc) {
        this.is_suc = is_suc;
    }
}
