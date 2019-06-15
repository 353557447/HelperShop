package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/12.
 */

public class ReturnMoneyTemBean {

    /**
     * code : 200.0
     * msg : 查询成功
     * data : [{"r_id":154360,"rname":"啊啊啊啊","rtype":1,"flag":2,"isfailure":2,"already_rebate":"0.00","not_apply_rebate":"0.00","not_deal_rebate":"0.00"}]
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
         * r_id : 154360.0
         * rname : 啊啊啊啊
         * rtype : 1.0
         * flag : 2.0
         * isfailure : 2.0
         * already_rebate : 0.00
         * not_apply_rebate : 0.00
         * not_deal_rebate : 0.00
         */

        private int r_id;
        private String rname;
        private int rtype;
        private int flag;
        private int isfailure;
        private String already_rebate;
        private String not_apply_rebate;
        private String not_deal_rebate;
        private int have_rebate;
        private boolean isChecked;
        private int r_way;

        public int getR_way() {
            return r_way;
        }

        public void setR_way(int r_way) {
            this.r_way = r_way;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public int getHave_rebate() {
            return have_rebate;
        }

        public void setHave_rebate(int have_rebate) {
            this.have_rebate = have_rebate;
        }

        public int getR_id() {
            return r_id;
        }

        public void setR_id(int r_id) {
            this.r_id = r_id;
        }

        public String getRname() {
            return rname;
        }

        public void setRname(String rname) {
            this.rname = rname;
        }

        public int getRtype() {
            return rtype;
        }

        public void setRtype(int rtype) {
            this.rtype = rtype;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getIsfailure() {
            return isfailure;
        }

        public void setIsfailure(int isfailure) {
            this.isfailure = isfailure;
        }

        public String getAlready_rebate() {
            return already_rebate;
        }

        public void setAlready_rebate(String already_rebate) {
            this.already_rebate = already_rebate;
        }

        public String getNot_apply_rebate() {
            return not_apply_rebate;
        }

        public void setNot_apply_rebate(String not_apply_rebate) {
            this.not_apply_rebate = not_apply_rebate;
        }

        public String getNot_deal_rebate() {
            return not_deal_rebate;
        }

        public void setNot_deal_rebate(String not_deal_rebate) {
            this.not_deal_rebate = not_deal_rebate;
        }
    }
}
