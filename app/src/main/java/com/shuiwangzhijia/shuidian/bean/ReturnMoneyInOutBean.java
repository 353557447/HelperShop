package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/12.
 */

public class ReturnMoneyInOutBean {

    /**
     * code : 200.0
     * msg : 查询成功
     * data : [{"type":6,"create_time":1.555033228E9,"amount":"1.00","rebate_amount":"9987.00"},{"type":6,"create_time":1.555033123E9,"amount":"1.00","rebate_amount":"9988.00"},{"type":6,"create_time":1.555032868E9,"amount":"1.00","rebate_amount":"9989.00"},{"type":6,"create_time":1.555032837E9,"amount":"4.80","rebate_amount":"9990.00"},{"type":6,"create_time":1.554984275E9,"amount":"1.20","rebate_amount":"9994.80"},{"type":6,"create_time":1.554984177E9,"amount":"1.00","rebate_amount":"9996.00"},{"type":6,"create_time":1.554984156E9,"amount":"1.00","rebate_amount":"9997.00"},{"type":6,"create_time":1.554983924E9,"amount":"1.00","rebate_amount":"9998.00"}]
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
         * type : 6.0
         * create_time : 1.555033228E9
         * amount : 1.00
         * rebate_amount : 9987.00
         */

        private int type;
        private long create_time;
        private String amount;
        private String rebate_amount;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getRebate_amount() {
            return rebate_amount;
        }

        public void setRebate_amount(String rebate_amount) {
            this.rebate_amount = rebate_amount;
        }
    }
}
