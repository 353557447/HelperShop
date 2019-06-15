package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * created by wangsuli on 2018/8/27.
 */
public class BuyOrderListBean implements Serializable{


    /**
     * order_no : 2018091979553495
     * id : 462
     * pay_status : 0
     * order_time : 1537339105
     * update_time : 1537339105
     * total_price : 8712.05
     * tnum : 104
     * list : [{"order_sn":"2018091962991284","update_time":1537339105,"sname":"水厂123456","phone":"17687654321","total":"99","count_price":"8712.00","status":2,"list":[{"gname":"桶装水","picturl":"20180914/6823d68081a772ed9260f4ae1f6a66c9.jpg","num":99,"price":"88.00"}]}]
     */

    private String order_no;
    private int id;
    private int pay_status;
    private Long order_time;
    private Long update_time;
    private String total_price;
    private int tnum;

    public String getOrder_noo() {
        return order_noo;
    }

    public void setOrder_noo(String order_noo) {
        this.order_noo = order_noo;
    }

    private String order_noo;

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    private int did;

    public String getOut_order() {
        return out_order;
    }

    public void setOut_order(String out_order) {
        this.out_order = out_order;
    }

    private String out_order;

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    private String order_sn;

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

    private int dstutas;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;

    public List<PurchaseListBean> getList() {
        return list;
    }

    public void setList(List<PurchaseListBean> list) {
        this.list = list;
    }

    private List<PurchaseListBean> list;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    public Long getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Long order_time) {
        this.order_time = order_time;
    }

    public Long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Long update_time) {
        this.update_time = update_time;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public int getTnum() {
        return tnum;
    }

    public void setTnum(int tnum) {
        this.tnum = tnum;
    }


}
