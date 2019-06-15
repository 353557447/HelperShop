package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * created by wangsuli on 2018/8/27.
 */
public class SailOrderListBean {


    /**
     * order_no : 2018081619954830
     * update_time : 1534389179
     * buk : 1
     * distribution_status : 0
     * is_receive : 1
     * need_time : 3
     * tnum : 10
     * total_price : 631.00
     * remark : null
     * sjname : 123
     * phone : 18676712455
     * addr : 广东省深圳市福田区福民路123号
     * goods : [{"gname":"福能达空气水 瓶装水 550ml*24箱装","picturl":"20180514/d6c499edc7d9e431b3d81286fd39879d.jpg","num":3,"price":"18.00"},{"gname":"滴答桶装水(测试) ","picturl":"20180622/e669545c0aab2799cde2bf9f72883401.jpg","num":1,"price":"18.00"},{"gname":"滴答山泉 桶装饮用水 16.8L/桶","picturl":"20180610/a77307610bccab1698bec1b31f677e1d.jpg","num":2,"price":"50.00"},{"gname":"云峰山泉 桶装饮用水 16.8L/桶","picturl":"","num":4,"price":"545.00"}]
     */

    private String order_no;
    private long update_time;

    public long getOrder_time() {
        return order_time;
    }

    public void setOrder_time(long order_time) {
        this.order_time = order_time;
    }

    private long order_time;
    private String buk;
    private int distribution_status;
    private int is_receive;
    private String need_time;
    private String tnum;

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    private int order_status;
    private String total_price;
    private String remark;
    private String sjname;
    private String phone;
    private String addr;
    private List<GoodsBean> goods;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public String getBuk() {
        return buk;
    }

    public void setBuk(String buk) {
        this.buk = buk;
    }

    public int getDistribution_status() {
        return distribution_status;
    }

    public void setDistribution_status(int distribution_status) {
        this.distribution_status = distribution_status;
    }

    public int getIs_receive() {
        return is_receive;
    }

    public void setIs_receive(int is_receive) {
        this.is_receive = is_receive;
    }

    public String getNeed_time() {
        return need_time;
    }

    public void setNeed_time(String need_time) {
        this.need_time = need_time;
    }

    public String getTnum() {
        return tnum;
    }

    public void setTnum(String tnum) {
        this.tnum = tnum;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSjname() {
        return sjname;
    }

    public void setSjname(String sjname) {
        this.sjname = sjname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }


}
