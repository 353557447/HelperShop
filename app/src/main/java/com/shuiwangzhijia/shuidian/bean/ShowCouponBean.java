package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xxc on 2019/4/4.
 */

public class ShowCouponBean implements Serializable{

    /**
     * count : 3
     * list : [{"id":45,"c_id":15326460,"cname":"优惠1","ctype":0,"amount":"20.00","full":"50.00"},{"id":48,"c_id":15326457,"cname":"优惠1","ctype":0,"amount":"0.10","full":"0.10"},{"id":55,"c_id":15326457,"cname":"优惠1","ctype":0,"amount":"0.10","full":"0.10"}]
     */

    private int count;
    private List<ListBean> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * id : 45
         * c_id : 15326460
         * cname : 优惠1
         * ctype : 0
         * amount : 20.00
         * full : 50.00
         */

        private int id;
        private int c_id;
        private String cname;
        private int ctype;
        private String amount;
        private String full;
        private long start;

        public String getGids() {
            return gids;
        }

        public void setGids(String gids) {
            this.gids = gids;
        }

        private String gids;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        private String goods_id;

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        private String sname;

        public long getStart() {
            return start;
        }

        public void setStart(long start) {
            this.start = start;
        }

        public long getEnd() {
            return end;
        }

        public void setEnd(long end) {
            this.end = end;
        }

        private long end;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getC_id() {
            return c_id;
        }

        public void setC_id(int c_id) {
            this.c_id = c_id;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public int getCtype() {
            return ctype;
        }

        public void setCtype(int ctype) {
            this.ctype = ctype;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getFull() {
            return full;
        }

        public void setFull(String full) {
            this.full = full;
        }
    }
}
