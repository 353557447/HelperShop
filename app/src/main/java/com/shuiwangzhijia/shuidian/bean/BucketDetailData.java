package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * created by wangsuli on 2018/10/23.
 */
public class BucketDetailData {


    /**
     * update_time : 1547450129
     * sname : 0108
     * sum : 3
     * total_price : 0.03
     * bucket_order_sn : 2019011456582405
     * goods : [{"picturl":"20190108/c1360394e7b3705d22dfd2bf21a64d7a.jpg","gname":"3号商品","empty_price":"0.01","empty_policy":"买1送4水票","num":3}]
     */

    private int update_time;
    private String sname;
    private int sum;
    private String total_price;
    private String bucket_order_sn;
    private List<BucketBean> goods;

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getBucket_order_sn() {
        return bucket_order_sn;
    }

    public void setBucket_order_sn(String bucket_order_sn) {
        this.bucket_order_sn = bucket_order_sn;
    }

    public List<BucketBean> getGoods() {
        return goods;
    }

    public void setGoods(List<BucketBean> goods) {
        this.goods = goods;
    }
}
