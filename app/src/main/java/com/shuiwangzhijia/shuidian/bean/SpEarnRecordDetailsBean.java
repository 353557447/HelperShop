package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/22.
 */

public class SpEarnRecordDetailsBean {

    /**
     * code : 200.0
     * msg : ok
     * data : [{"id":47,"sid":673,"order_code":"2019041716593586","order_type":1,"amount_price":"7.00","refund_status":0,"order_time":1.555501819E9,"pay_time":1.550554491E9,"is_capital":0,"pay_method":2,"pay_channels":4},{"id":48,"sid":673,"order_code":"2019041716593587","order_type":2,"amount_price":"4.00","refund_status":0,"order_time":1.555501825E9,"pay_time":1.555554491E9,"is_capital":0,"pay_method":2,"pay_channels":4},{"id":49,"sid":673,"order_code":"2019041816593588","order_type":3,"amount_price":"19.00","refund_status":0,"order_time":1.555550108E9,"pay_time":1.555554491E9,"is_capital":0,"pay_method":2,"pay_channels":4},{"id":50,"sid":673,"order_code":"2019041816593589","order_type":1,"amount_price":"19.00","refund_status":0,"order_time":1.555550317E9,"pay_time":1.555554491E9,"is_capital":0,"pay_method":2,"pay_channels":5}]
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
         * id : 47.0
         * sid : 673.0
         * order_code : 2019041716593586
         * order_type : 1.0
         * amount_price : 7.00
         * refund_status : 0.0
         * order_time : 1.555501819E9
         * pay_time : 1.550554491E9
         * is_capital : 0.0
         * pay_method : 2.0
         * pay_channels : 4.0
         */

        private int id;
        private int sid;
        private String order_code;
        private int order_type;
        private String amount_price;
        private int refund_status;
        private long order_time;
        private long pay_time;
        private int is_capital;
        private int pay_method;
        private int pay_channels;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public int getOrder_type() {
            return order_type;
        }

        public void setOrder_type(int order_type) {
            this.order_type = order_type;
        }

        public String getAmount_price() {
            return amount_price;
        }

        public void setAmount_price(String amount_price) {
            this.amount_price = amount_price;
        }

        public int getRefund_status() {
            return refund_status;
        }

        public void setRefund_status(int refund_status) {
            this.refund_status = refund_status;
        }

        public long getOrder_time() {
            return order_time;
        }

        public void setOrder_time(long order_time) {
            this.order_time = order_time;
        }

        public long getPay_time() {
            return pay_time;
        }

        public void setPay_time(long pay_time) {
            this.pay_time = pay_time;
        }

        public int getIs_capital() {
            return is_capital;
        }

        public void setIs_capital(int is_capital) {
            this.is_capital = is_capital;
        }

        public int getPay_method() {
            return pay_method;
        }

        public void setPay_method(int pay_method) {
            this.pay_method = pay_method;
        }

        public int getPay_channels() {
            return pay_channels;
        }

        public void setPay_channels(int pay_channels) {
            this.pay_channels = pay_channels;
        }
    }
}
