package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/3.
 */

public class BalanceInfoBean {

    /**
     * code : 200.0
     * msg : 获取成功
     * data : {"list":[{"id":7,"c_order_no":"2019040216593089","sid":925,"did":11,"type":1,"amount":"10.00","balance":"10.00","add_time":1.554198672E9},{"id":8,"c_order_no":"2019040216593089","sid":925,"did":11,"type":1,"amount":"10.00","balance":"10.00","add_time":1.554198672E9},{"id":11,"c_order_no":"2019040216593089","sid":925,"did":11,"type":1,"amount":"10.00","balance":"10.00","add_time":1.554198672E9},{"id":13,"c_order_no":"2019040216593089","sid":925,"did":11,"type":3,"amount":"10.00","balance":"10.00","add_time":1.554198672E9},{"id":14,"c_order_no":"2019040216593089","sid":925,"did":11,"type":1,"amount":"10.00","balance":"10.00","add_time":1.554198672E9},{"id":16,"c_order_no":"2019040216593089","sid":925,"did":11,"type":4,"amount":"10.00","balance":"10.00","add_time":1.554198672E9},{"id":18,"c_order_no":"2019040216593089","sid":925,"did":11,"type":1,"amount":"10.00","balance":"10.00","add_time":1.554198672E9},{"id":19,"c_order_no":"2019040216593089","sid":925,"did":11,"type":1,"amount":"10.00","balance":"10.00","add_time":1.554198672E9},{"id":20,"c_order_no":"2019040216593089","sid":925,"did":11,"type":2,"amount":"10.00","balance":"10.00","add_time":1.554198672E9},{"id":21,"c_order_no":"2019040216593089","sid":925,"did":11,"type":1,"amount":"10.00","balance":"10.00","add_time":1.554198672E9}],"total":11}
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
         * list : [{"id":7,"c_order_no":"2019040216593089","sid":925,"did":11,"type":1,"amount":"10.00","balance":"10.00","add_time":1.554198672E9},{"id":8,"c_order_no":"2019040216593089","sid":925,"did":11,"type":1,"amount":"10.00","balance":"10.00","add_time":1.554198672E9},{"id":11,"c_order_no":"2019040216593089","sid":925,"did":11,"type":1,"amount":"10.00","balance":"10.00","add_time":1.554198672E9},{"id":13,"c_order_no":"2019040216593089","sid":925,"did":11,"type":3,"amount":"10.00","balance":"10.00","add_time":1.554198672E9},{"id":14,"c_order_no":"2019040216593089","sid":925,"did":11,"type":1,"amount":"10.00","balance":"10.00","add_time":1.554198672E9},{"id":16,"c_order_no":"2019040216593089","sid":925,"did":11,"type":4,"amount":"10.00","balance":"10.00","add_time":1.554198672E9},{"id":18,"c_order_no":"2019040216593089","sid":925,"did":11,"type":1,"amount":"10.00","balance":"10.00","add_time":1.554198672E9},{"id":19,"c_order_no":"2019040216593089","sid":925,"did":11,"type":1,"amount":"10.00","balance":"10.00","add_time":1.554198672E9},{"id":20,"c_order_no":"2019040216593089","sid":925,"did":11,"type":2,"amount":"10.00","balance":"10.00","add_time":1.554198672E9},{"id":21,"c_order_no":"2019040216593089","sid":925,"did":11,"type":1,"amount":"10.00","balance":"10.00","add_time":1.554198672E9}]
         * total : 11.0
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
             * id : 7.0
             * c_order_no : 2019040216593089
             * sid : 925.0
             * did : 11.0
             * type : 1.0
             * amount : 10.00
             * balance : 10.00
             * add_time : 1.554198672E9
             */

            private int id;
            private String c_order_no;
            private int sid;
            private int did;
            private int type;
            private String amount;
            private String balance;
            private long add_time;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getC_order_no() {
                return c_order_no;
            }

            public void setC_order_no(String c_order_no) {
                this.c_order_no = c_order_no;
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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public long getAdd_time() {
                return add_time;
            }

            public void setAdd_time(long add_time) {
                this.add_time = add_time;
            }
        }
    }
}
