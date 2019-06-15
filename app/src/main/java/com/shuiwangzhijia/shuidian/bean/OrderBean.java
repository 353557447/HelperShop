package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;

/**
 * created by wangsuli on 2018/8/24.
 */
public class OrderBean implements Serializable {

    /**
     * order_no : 2018082451725260
     * total_price : 18
     * tnum : 1
     * user_id : 33
     * pay_status : 0
     * buk : null
     * remark : chdhd
     * addr : 收货地址:哼哼唧唧叫1
     * order_type : 1
     * create_time : 2018-08-24 16:03:07
     * update_time : 2018-08-24 16:03:07
     * id : 61
     */

    private String order_no;
    private Double total_price;
    private int tnum;
    private int user_id;
    private int pay_status;
    private Object buk;
    private String remark;
    private String addr;
    private int order_type;
    private String create_time;
    private String update_time;

    public String getOrder_noo() {
        return order_noo;
    }

    public void setOrder_noo(String order_noo) {
        this.order_noo = order_noo;
    }

    private String order_noo;
    /**
     * uid : 33
     * tamount : 2400
     * amount : 2400
     * tsum : 100
     * sum : 100
     * buk : null
     */

    private int uid;
    private Double tamount;
    private Double amount;
    private int tsum;
    private int sum;
    private int is_suc;
    private int dstutas;

    public int getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(int delivery_type) {
        this.delivery_type = delivery_type;
    }

    private int delivery_type;

    public int getDstutas() {
        return dstutas;
    }

    public void setDstutas(int dstutas) {
        this.dstutas = dstutas;
    }

    /**
     * price : 0
     * order_sn : 2019031316586833
     * pay : 测试六号
     * sail : null
     * pay_type : 换桶支付
     */

    private String price;
    private String order_sn;
    private String pay;
    private Object sail;
    private String pay_type;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    private long orderTime;//自曾下单时间字段
    private String id;

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    int did;
    String gid;
    int num;
    private String money;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }


    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }

    public int getTnum() {
        return tnum;
    }

    public void setTnum(int tnum) {
        this.tnum = tnum;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    public Object getBuk() {
        return buk;
    }

    public void setBuk(Object buk) {
        this.buk = buk;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getOrder_type() {
        return order_type;
    }

    public void setOrder_type(int order_type) {
        this.order_type = order_type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Double getTamount() {
        return tamount;
    }

    public void setTamount(Double tamount) {
        this.tamount = tamount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getTsum() {
        return tsum;
    }

    public void setTsum(int tsum) {
        this.tsum = tsum;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getIs_suc() {
        return is_suc;
    }

    public void setIs_suc(int is_suc) {
        this.is_suc = is_suc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
