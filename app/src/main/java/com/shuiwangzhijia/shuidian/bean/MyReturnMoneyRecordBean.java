package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/11.
 */

public class MyReturnMoneyRecordBean {

    /**
     * code : 200
     * msg : 查询成功
     * data : [{"settlement_time":null,"rname":"模板B","total_amount":"0.00","r_type":3,"r_way":2},{"settlement_time":1554900452,"rname":"模板B","total_amount":"0.00","r_type":3,"r_way":2},{"settlement_time":1554900542,"rname":"模板B","total_amount":"0.00","r_type":2,"r_way":2}]
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
         * settlement_time : null
         * rname : 模板B
         * total_amount : 0.00
         * r_type : 3
         * r_way : 2
         */

        private long settlement_time;
        private String rname;
        private String total_amount;
        private int r_type;
        private int r_way;
        private int s_count;

        public int getS_count() {
            return s_count;
        }

        public void setS_count(int s_count) {
            this.s_count = s_count;
        }

        public long getSettlement_time() {
            return settlement_time;
        }

        public void setSettlement_time(long settlement_time) {
            this.settlement_time = settlement_time;
        }

        public String getRname() {
            return rname;
        }

        public void setRname(String rname) {
            this.rname = rname;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public int getR_type() {
            return r_type;
        }

        public void setR_type(int r_type) {
            this.r_type = r_type;
        }

        public int getR_way() {
            return r_way;
        }

        public void setR_way(int r_way) {
            this.r_way = r_way;
        }
    }
}
