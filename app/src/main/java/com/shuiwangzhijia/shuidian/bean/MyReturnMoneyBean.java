package com.shuiwangzhijia.shuidian.bean;

/**
 * Created by Lijn on 2019/4/12.
 */

public class MyReturnMoneyBean {

    /**
     * code : 200.0
     * msg : 查询成功
     * data : {"sname":"银米科技","rebate_amount":"9986.00","already_amount":"0.00","turn_amount":"13.00","history_amount":"0.00","not_s_amount":"0","all_s_amount":"0"}
     * scode : 0.0
     */

    private int code;
    private String msg;
    private DataBean data;
    private int scode;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getScode() {
        return scode;
    }

    public void setScode(int scode) {
        this.scode = scode;
    }

    public static class DataBean {
        /**
         * sname : 银米科技
         * rebate_amount : 9986.00
         * already_amount : 0.00
         * turn_amount : 13.00
         * history_amount : 0.00
         * not_s_amount : 0
         * all_s_amount : 0
         */

        private String sname;
        private String rebate_amount;
        private String already_amount;
        private String turn_amount;
        private String history_amount;
        private String not_s_amount;
        private String all_s_amount;
        private int all_record;

        public int getAll_record() {
            return all_record;
        }

        public void setAll_record(int all_record) {
            this.all_record = all_record;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public String getRebate_amount() {
            return rebate_amount;
        }

        public void setRebate_amount(String rebate_amount) {
            this.rebate_amount = rebate_amount;
        }

        public String getAlready_amount() {
            return already_amount;
        }

        public void setAlready_amount(String already_amount) {
            this.already_amount = already_amount;
        }

        public String getTurn_amount() {
            return turn_amount;
        }

        public void setTurn_amount(String turn_amount) {
            this.turn_amount = turn_amount;
        }

        public String getHistory_amount() {
            return history_amount;
        }

        public void setHistory_amount(String history_amount) {
            this.history_amount = history_amount;
        }

        public String getNot_s_amount() {
            return not_s_amount;
        }

        public void setNot_s_amount(String not_s_amount) {
            this.not_s_amount = not_s_amount;
        }

        public String getAll_s_amount() {
            return all_s_amount;
        }

        public void setAll_s_amount(String all_s_amount) {
            this.all_s_amount = all_s_amount;
        }
    }
}
