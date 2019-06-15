package com.shuiwangzhijia.shuidian.newmodule.bean;

import java.util.List;

public class FeaturedGoodsTypeBean {

    /**
     * data : [{"gtype":"9,17","t_name":"瓶装水"},{"gtype":8,"t_name":"桶装水"}]
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
         * gtype : 9,17
         * t_name : 瓶装水
         */

        private String gtype;
        private String t_name;

        public String getGtype() {
            return gtype;
        }

        public void setGtype(String gtype) {
            this.gtype = gtype;
        }

        public String getT_name() {
            return t_name;
        }

        public void setT_name(String t_name) {
            this.t_name = t_name;
        }
    }
}
