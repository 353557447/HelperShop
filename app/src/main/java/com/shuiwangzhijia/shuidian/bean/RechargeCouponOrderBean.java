package com.shuiwangzhijia.shuidian.bean;

/**
 * Created by Lijn on 2019/4/4.
 */

public class RechargeCouponOrderBean {

    /**
     * code : 200.0
     * msg : 下单成功
     * data : {"order_no":"2019040416593141","order_time":1.554358076E9,"price":"231321.00"}
     * scode : 200.0
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
         * order_no : 2019040416593141
         * order_time : 1.554358076E9
         * price : 231321.00
         */

        private String order_no;
        private long order_time;
        private String price;

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public long getOrder_time() {
            return order_time;
        }

        public void setOrder_time(long order_time) {
            this.order_time = order_time;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
