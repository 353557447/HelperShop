package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xxc on 2019/4/4.
 */

public class MycouBean implements Serializable{

    /**
     * list : [{"id":45,"c_id":15326460,"full":"50.00","amount":"20.00","sid":576,"did":11,"is_use":0,"aging":0,"gids":"21250220,54754261","start_time":0,"end_time":0,"cname":"优惠1","dname":"银米科技"},{"id":48,"c_id":15326457,"full":"0.10","amount":"0.10","sid":576,"did":11,"is_use":0,"aging":0,"gids":"21250220,54754261","start_time":0,"end_time":0,"cname":"优惠1","dname":"银米科技"},{"id":55,"c_id":15326457,"full":"0.10","amount":"0.10","sid":576,"did":11,"is_use":0,"aging":0,"gids":"21250220,54754261","start_time":0,"end_time":0,"cname":"优惠1","dname":"银米科技"}]
     * total : 3
     */

    private int total;
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 45
         * c_id : 15326460
         * full : 50.00
         * amount : 20.00
         * sid : 576
         * did : 11
         * is_use : 0
         * aging : 0
         * gids : 21250220,54754261
         * start_time : 0
         * end_time : 0
         * cname : 优惠1
         * dname : 银米科技
         */

        private int id;
        private int c_id;
        private String full;
        private String amount;
        private int sid;
        private int did;
        private int is_use;
        private int aging;
        private String gids;
        private int start_time;
        private int end_time;
        private String cname;
        private String dname;
        private int ctype;
        private int coupon_type;
        private String picturl;

        public int getCoupon_type() {
            return coupon_type;
        }

        public void setCoupon_type(int coupon_type) {
            this.coupon_type = coupon_type;
        }

        public String getPicturl() {
            return picturl;
        }

        public void setPicturl(String picturl) {
            this.picturl = picturl;
        }

        public int getCtype() {

            return ctype;
        }

        public void setCtype(int ctype) {
            this.ctype = ctype;
        }

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

        public String getFull() {
            return full;
        }

        public void setFull(String full) {
            this.full = full;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public int getDid() {
            return did;
        }

        public void setDid(int did) {
            this.did = did;
        }

        public int getIs_use() {
            return is_use;
        }

        public void setIs_use(int is_use) {
            this.is_use = is_use;
        }

        public int getAging() {
            return aging;
        }

        public void setAging(int aging) {
            this.aging = aging;
        }

        public String getGids() {
            return gids;
        }

        public void setGids(String gids) {
            this.gids = gids;
        }

        public int getStart_time() {
            return start_time;
        }

        public void setStart_time(int start_time) {
            this.start_time = start_time;
        }

        public int getEnd_time() {
            return end_time;
        }

        public void setEnd_time(int end_time) {
            this.end_time = end_time;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getDname() {
            return dname;
        }

        public void setDname(String dname) {
            this.dname = dname;
        }
    }
}
