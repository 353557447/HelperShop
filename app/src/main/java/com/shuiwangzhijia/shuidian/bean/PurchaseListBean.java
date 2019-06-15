package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * created by wangsuli on 2018/9/20.
 */
public class PurchaseListBean implements Serializable{
    /**
     * order_sn : 2018091962991284
     * update_time : 1537339105
     * sname : 水厂123456
     * phone : 17687654321
     * total : 99
     * count_price : 8712.00
     * status : 2
     * list : [{"gname":"桶装水","picturl":"20180914/6823d68081a772ed9260f4ae1f6a66c9.jpg","num":99,"price":"88.00"}]
     */

    private String order_sn;

    public long getOrder_time() {
        return order_time;
    }

    public void setOrder_time(long order_time) {
        this.order_time = order_time;
    }

    private long order_time;
    private String sname;
    private String phone;
    private int total;
    private String count_price;
    private int status;
    private List<GoodsBean> list;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    private String order_no;

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }



    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getCount_price() {
        return count_price;
    }

    public void setCount_price(String count_price) {
        this.count_price = count_price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<GoodsBean> getList() {
        return list;
    }

    public void setList(List<GoodsBean> list) {
        this.list = list;
    }


}
