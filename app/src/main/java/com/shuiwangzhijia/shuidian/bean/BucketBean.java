package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * created by wangsuli on 2018/10/17.
 */
public class BucketBean implements Serializable {
    /**
     * did : 20
     * bname : 怡宝纯净水8.9L 空桶
     * bid : 11111111
     * gid : 72060555
     * b_picturl : 20180926/74b5154cf9723e8eca21f74c27fe7c0c.jpg
     * b_price : 40.00
     * bsum : 1000
     * policy : 送2张水票
     * descrip : 测试
     * b_detail : 21312312
     * sname : 什么水厂
     */

    private int did;
    /**
     * gname : 3号商品
     * gid : 31337637
     * picturl : 20190108/c1360394e7b3705d22dfd2bf21a64d7a.jpg
     * gprice : 0.01
     * detail :
     * empty_price : 0.01
     * empty_is_up : 1
     * empty_policy : 买1送4水票
     */

    private String gname;
    private String picturl;
    private String gprice;
    private String detail;
    private String empty_price;
    private int empty_is_up;
    private String empty_policy;

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    private String update_time;
    /**
     * bucket_order_sn : 2019011456582396
     * order_time : 1547445221
     * sum : 1
     * pay_status : 1
     * goods : [{"gid":31337637,"num":1,"empty_price":"0.01","picturl":"20190108/c1360394e7b3705d22dfd2bf21a64d7a.jpg","empty_policy":"买1送4水票","gname":"3号商品","sname":"0108"}]
     */

    private String bucket_order_sn;
    private int order_time;
    private int sum;
    private int pay_status;
    private List<EmptyTongBean> goods;

    public int getOrder_type() {
        return order_type;
    }

    public void setOrder_type(int order_type) {
        this.order_type = order_type;
    }

    private int order_type;
    private String bname;
    private int bid;
    private int gid;
    private String b_picturl;
    private double b_price;
    private int bsum;
    private String policy;
    private boolean policyShow;
    private String descrip;
    private String b_detail;
    private String sname;
    /**
     * order_no : 2018102349458737
     * create_time : 1540264306
     * total_price : 80.00
     * tnum : 2
     * num : 2
     * price : 40.00
     */

    private String order_no;
    private long create_time;
    private String total_price;
    private int tnum;
    private int num;
    private double price;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getB_picturl() {
        return b_picturl;
    }

    public void setB_picturl(String b_picturl) {
        this.b_picturl = b_picturl;
    }

    public double getB_price() {
        return b_price;
    }

    public void setB_price(double b_price) {
        this.b_price = b_price;
    }

    public int getBsum() {
        return bsum;
    }

    public void setBsum(int bsum) {
        this.bsum = bsum;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getB_detail() {
        return b_detail;
    }

    public void setB_detail(String b_detail) {
        this.b_detail = b_detail;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isPolicyShow() {
        return policyShow;
    }

    public void setPolicyShow(boolean policyShow) {
        this.policyShow = policyShow;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getPicturl() {
        return picturl;
    }

    public void setPicturl(String picturl) {
        this.picturl = picturl;
    }

    public String getGprice() {
        return gprice;
    }

    public void setGprice(String gprice) {
        this.gprice = gprice;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getEmpty_price() {
        return empty_price;
    }

    public void setEmpty_price(String empty_price) {
        this.empty_price = empty_price;
    }

    public int getEmpty_is_up() {
        return empty_is_up;
    }

    public void setEmpty_is_up(int empty_is_up) {
        this.empty_is_up = empty_is_up;
    }

    public String getEmpty_policy() {
        return empty_policy;
    }

    public void setEmpty_policy(String empty_policy) {
        this.empty_policy = empty_policy;
    }

    public String getBucket_order_sn() {
        return bucket_order_sn;
    }

    public void setBucket_order_sn(String bucket_order_sn) {
        this.bucket_order_sn = bucket_order_sn;
    }

    public int getOrder_time() {
        return order_time;
    }

    public void setOrder_time(int order_time) {
        this.order_time = order_time;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    public List<EmptyTongBean> getGoods() {
        return goods;
    }

    public void setGoods(List<EmptyTongBean> goods) {
        this.goods = goods;
    }
}
