package com.shuiwangzhijia.shuidian.newmodule.bean;

import java.util.List;

public class FeatureGoodsBean {

    /**
     * data : [{"id":221,"gid":"47273555","did":1706,"gname":"农夫山泉15L桶装水","gsum":989,"is_up":1,"g_number":"","barcode":"","gunit":null,"pprice":"0.01","price":"0.01","least_p":2,"gprice":"1.00","least_g":null,"descrip":"","picturl":"20190326/8ec3ec42edb15db680a37f491eb61fb3.jpg","detail":"","update_time":1556520601,"pid":1706,"sid":1720,"sgrade":null,"sname":"废五金水厂","least_num":0,"amount":"0.00","full_free":"0.00","is_free":0,"rebate_flag":0,"activity_type":0},{"id":242,"gid":"76676264","did":1706,"gname":"小分子空气水","gsum":808,"is_up":1,"g_number":"","barcode":"","gunit":null,"pprice":"0.01","price":"0.01","least_p":1,"gprice":"0.01","least_g":null,"descrip":"","picturl":"20190422/66298df33f9171b79e7ce2310964000e.jpg","detail":"","update_time":1556428258,"pid":1706,"sid":1720,"sgrade":null,"sname":"废五金水厂","least_num":0,"amount":"0.00","full_free":"0.00","is_free":0,"rebate_flag":1,"active":"[{\"full\":\"0.03\",\"reduce\":\"0.01\"}]","activity_type":1}]
     * code : 200
     * msg : success
     * scode : 0
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
         * id : 221
         * gid : 47273555
         * did : 1706
         * gname : 农夫山泉15L桶装水
         * gsum : 989
         * is_up : 1
         * g_number :
         * barcode :
         * gunit : null
         * pprice : 0.01
         * price : 0.01
         * least_p : 2
         * gprice : 1.00
         * least_g : null
         * descrip :
         * picturl : 20190326/8ec3ec42edb15db680a37f491eb61fb3.jpg
         * detail :
         * update_time : 1556520601
         * pid : 1706
         * sid : 1720
         * sgrade : null
         * sname : 废五金水厂
         * least_num : 0
         * amount : 0.00
         * full_free : 0.00
         * is_free : 0
         * rebate_flag : 0
         * activity_type : 0
         * active : [{"full":"0.03","reduce":"0.01"}]
         */

        private int id;
        private String gid;
        private int did;
        private String gname;
        private int gsum;
        private int is_up;
        private String g_number;
        private String barcode;
        private Object gunit;
        private String pprice;
        private String price;
        private int least_p;
        private String gprice;
        private Object least_g;
        private String descrip;
        private String picturl;
        private String detail;
        private int update_time;
        private int pid;
        private int sid;
        private Object sgrade;
        private String sname;
        private int least_num;
        private String amount;
        private String full_free;
        private int is_free;
        private int rebate_flag;
        private int activity_type;
        private String active;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public int getDid() {
            return did;
        }

        public void setDid(int did) {
            this.did = did;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public int getGsum() {
            return gsum;
        }

        public void setGsum(int gsum) {
            this.gsum = gsum;
        }

        public int getIs_up() {
            return is_up;
        }

        public void setIs_up(int is_up) {
            this.is_up = is_up;
        }

        public String getG_number() {
            return g_number;
        }

        public void setG_number(String g_number) {
            this.g_number = g_number;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public Object getGunit() {
            return gunit;
        }

        public void setGunit(Object gunit) {
            this.gunit = gunit;
        }

        public String getPprice() {
            return pprice;
        }

        public void setPprice(String pprice) {
            this.pprice = pprice;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getLeast_p() {
            return least_p;
        }

        public void setLeast_p(int least_p) {
            this.least_p = least_p;
        }

        public String getGprice() {
            return gprice;
        }

        public void setGprice(String gprice) {
            this.gprice = gprice;
        }

        public Object getLeast_g() {
            return least_g;
        }

        public void setLeast_g(Object least_g) {
            this.least_g = least_g;
        }

        public String getDescrip() {
            return descrip;
        }

        public void setDescrip(String descrip) {
            this.descrip = descrip;
        }

        public String getPicturl() {
            return picturl;
        }

        public void setPicturl(String picturl) {
            this.picturl = picturl;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public Object getSgrade() {
            return sgrade;
        }

        public void setSgrade(Object sgrade) {
            this.sgrade = sgrade;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public int getLeast_num() {
            return least_num;
        }

        public void setLeast_num(int least_num) {
            this.least_num = least_num;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getFull_free() {
            return full_free;
        }

        public void setFull_free(String full_free) {
            this.full_free = full_free;
        }

        public int getIs_free() {
            return is_free;
        }

        public void setIs_free(int is_free) {
            this.is_free = is_free;
        }

        public int getRebate_flag() {
            return rebate_flag;
        }

        public void setRebate_flag(int rebate_flag) {
            this.rebate_flag = rebate_flag;
        }

        public int getActivity_type() {
            return activity_type;
        }

        public void setActivity_type(int activity_type) {
            this.activity_type = activity_type;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }
    }
}
