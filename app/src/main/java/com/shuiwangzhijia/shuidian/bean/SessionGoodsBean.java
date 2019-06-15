package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/16.
 */

public class SessionGoodsBean {

    /**
     * code : 200.0
     * msg : 查询成功
     * data : [{"id":3,"seckill_sales_volume":0,"seckill_stock":10,"seckill_restrictions":3,"seckill_price":"0.98","gid":"95392132","gname":"景田纯净水18.9L","pprice":"19.00","picturl":"20180921/83370704d0e11bbd014f1c6a084d84dd.jpg"}]
     * scode : 0.0
     */

    private int code;
    private String msg;
    private int scode;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getScode() {
        return scode;
    }

    public void setScode(int scode) {
        this.scode = scode;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3.0
         * seckill_sales_volume : 0.0
         * seckill_stock : 10.0
         * seckill_restrictions : 3.0
         * seckill_price : 0.98
         * gid : 95392132
         * gname : 景田纯净水18.9L
         * pprice : 19.00
         * picturl : 20180921/83370704d0e11bbd014f1c6a084d84dd.jpg
         */

        private int id;
        private int seckill_sales_volume;
        private int seckill_stock;
        private int seckill_restrictions;
        private String seckill_price;
        private String gid;
        private String gname;
        private String pprice;
        private String picturl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSeckill_sales_volume() {
            return seckill_sales_volume;
        }

        public void setSeckill_sales_volume(int seckill_sales_volume) {
            this.seckill_sales_volume = seckill_sales_volume;
        }

        public int getSeckill_stock() {
            return seckill_stock;
        }

        public void setSeckill_stock(int seckill_stock) {
            this.seckill_stock = seckill_stock;
        }

        public int getSeckill_restrictions() {
            return seckill_restrictions;
        }

        public void setSeckill_restrictions(int seckill_restrictions) {
            this.seckill_restrictions = seckill_restrictions;
        }

        public String getSeckill_price() {
            return seckill_price;
        }

        public void setSeckill_price(String seckill_price) {
            this.seckill_price = seckill_price;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public String getPprice() {
            return pprice;
        }

        public void setPprice(String pprice) {
            this.pprice = pprice;
        }

        public String getPicturl() {
            return picturl;
        }

        public void setPicturl(String picturl) {
            this.picturl = picturl;
        }
    }
}
