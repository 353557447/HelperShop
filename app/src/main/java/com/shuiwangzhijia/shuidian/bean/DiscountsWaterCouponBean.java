package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/5/6.
 */

public class DiscountsWaterCouponBean {

    /**
     * code : 200.0
     * msg : success
     * data : [{"sname":"废五金水厂","s_name":"怡宝15L桶装水水票","gname":"怡宝15L桶装水","gid":8.5346664E7,"s_gid":1.6594907E7,"snum":10,"pprice":"100.00","sprice":"90.00","did":1706},{"sname":"废五金水厂","s_name":"小分子空气水水票","gname":"小分子空气水","gid":7.6676264E7,"s_gid":1.6594934E7,"snum":5,"pprice":"0.01","sprice":"0.01","start":1.5564672E9,"end":1.559232E9,"did":1706}]
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
         * sname : 废五金水厂
         * s_name : 怡宝15L桶装水水票
         * gname : 怡宝15L桶装水
         * gid : 8.5346664E7
         * s_gid : 1.6594907E7
         * snum : 10.0
         * pprice : 100.00
         * sprice : 90.00
         * did : 1706.0
         * start : 1.5564672E9
         * end : 1.559232E9
         */

        private String sname;
        private String s_name;
        private String gname;
        private int gid;
        private int s_gid;
        private int snum;
        private String pprice;
        private String sprice;
        private int did;
        private long start;
        private long end;
        private String picturl;

        public String getPicturl() {
            return picturl;
        }

        public void setPicturl(String picturl) {
            this.picturl = picturl;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public String getS_name() {
            return s_name;
        }

        public void setS_name(String s_name) {
            this.s_name = s_name;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public int getS_gid() {
            return s_gid;
        }

        public void setS_gid(int s_gid) {
            this.s_gid = s_gid;
        }

        public int getSnum() {
            return snum;
        }

        public void setSnum(int snum) {
            this.snum = snum;
        }

        public String getPprice() {
            return pprice;
        }

        public void setPprice(String pprice) {
            this.pprice = pprice;
        }

        public String getSprice() {
            return sprice;
        }

        public void setSprice(String sprice) {
            this.sprice = sprice;
        }

        public int getDid() {
            return did;
        }

        public void setDid(int did) {
            this.did = did;
        }

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
    }
}
