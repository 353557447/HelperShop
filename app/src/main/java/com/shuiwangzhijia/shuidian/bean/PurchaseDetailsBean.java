package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xxc on 2019/2/19.
 */

public class PurchaseDetailsBean implements Serializable{

    /**
     * order_no : 2019021516585567
     * create_time : 1550194301
     * sum : 0
     * order_sn : 2019021516585568
     * pay_statuses : 1
     * phone : 15818777148
     * pay_style : 1
     * did : 1660
     * amount : 0.01
     * order_noo : 2019021516585568
     * Waterworks_goods : [{"waterworks":"XM1水厂","goods":[{"snum":1,"gname":"三号商品13","picturl":"20190124/14bb54c55fcb40e4c7d44624b8ee11c7.jpg","price":"0.01"}]}]
     * water_ticket : []
     * refundwt_water : []
     * recycler_water : []
     * change_bucket : null
     */

    private String order_no;
    private int create_time;
    private int sum;
    private String order_sn;
    private int pay_statuses;
    private String phone;
    private int pay_style;
    private int did;
    private String amount;
    private String order_noo;
    private ChangeBucketBean change_bucket;       //换杂桶情况

    public ChangeModeBean getChange_mode() {
        return change_mode;
    }

    public void setChange_mode(ChangeModeBean change_mode) {
        this.change_mode = change_mode;
    }

    private ChangeModeBean change_mode;
    private List<WaterworksGoodsBean> Waterworks_goods;
    private List<WaterTicketBean> water_ticket;       //水票
    private List<WaterTicketBean> refundwt_water;     //退水
    private List<WaterTicketBean> recycler_water;     //回桶

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getPay_statuses() {
        return pay_statuses;
    }

    public void setPay_statuses(int pay_statuses) {
        this.pay_statuses = pay_statuses;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPay_style() {
        return pay_style;
    }

    public void setPay_style(int pay_style) {
        this.pay_style = pay_style;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrder_noo() {
        return order_noo;
    }

    public void setOrder_noo(String order_noo) {
        this.order_noo = order_noo;
    }

    public ChangeBucketBean getChange_bucket() {
        return change_bucket;
    }

    public void setChange_bucket(ChangeBucketBean change_bucket) {
        this.change_bucket = change_bucket;
    }

    public List<WaterworksGoodsBean> getWaterworks_goods() {
        return Waterworks_goods;
    }

    public void setWaterworks_goods(List<WaterworksGoodsBean> Waterworks_goods) {
        this.Waterworks_goods = Waterworks_goods;
    }

    public List<WaterTicketBean> getWater_ticket() {
        return water_ticket;
    }

    public void setWater_ticket(List<WaterTicketBean> water_ticket) {
        this.water_ticket = water_ticket;
    }

    public List<WaterTicketBean> getRefundwt_water() {
        return refundwt_water;
    }

    public void setRefundwt_water(List<WaterTicketBean> refundwt_water) {
        this.refundwt_water = refundwt_water;
    }

    public List<WaterTicketBean> getRecycler_water() {
        return recycler_water;
    }

    public void setRecycler_water(List<WaterTicketBean> recycler_water) {
        this.recycler_water = recycler_water;
    }

    public static class WaterworksGoodsBean {
        /**
         * waterworks : XM1水厂
         * goods : [{"snum":1,"gname":"三号商品13","picturl":"20190124/14bb54c55fcb40e4c7d44624b8ee11c7.jpg","price":"0.01"}]
         */

        private String waterworks;
        private List<GoodsBean> goods;

        public String getWaterworks() {
            return waterworks;
        }

        public void setWaterworks(String waterworks) {
            this.waterworks = waterworks;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * snum : 1
             * gname : 三号商品13
             * picturl : 20190124/14bb54c55fcb40e4c7d44624b8ee11c7.jpg
             * price : 0.01
             */

            private int snum;
            private String gname;
            private String picturl;
            private String price;

            public int getSnum() {
                return snum;
            }

            public void setSnum(int snum) {
                this.snum = snum;
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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
