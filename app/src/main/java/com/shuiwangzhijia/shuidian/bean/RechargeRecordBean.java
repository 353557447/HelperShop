package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/3.
 */

public class RechargeRecordBean {


    /**
     * code : 200.0
     * msg : 获取成功
     * data : {"list":[{"create_time":1.554198112E9,"amount":"10.00","samount":"10.00","dname":"岳阳天晶水厂"},{"create_time":1.554198112E9,"amount":"10.00","samount":"10.00","dname":"岳阳天晶水厂"},{"create_time":1.554198112E9,"amount":"10.00","samount":"10.00","dname":"岳阳天晶水厂"},{"create_time":1.554198112E9,"amount":"10.00","samount":"10.00","dname":"岳阳天晶水厂"}],"total":4}
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
         * list : [{"create_time":1.554198112E9,"amount":"10.00","samount":"10.00","dname":"岳阳天晶水厂"},{"create_time":1.554198112E9,"amount":"10.00","samount":"10.00","dname":"岳阳天晶水厂"},{"create_time":1.554198112E9,"amount":"10.00","samount":"10.00","dname":"岳阳天晶水厂"},{"create_time":1.554198112E9,"amount":"10.00","samount":"10.00","dname":"岳阳天晶水厂"}]
         * total : 4.0
         */

        private int total;
        private List<ListBean> list;

        public double getTotal() {
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
             * create_time : 1.554198112E9
             * amount : 10.00
             * samount : 10.00
             * dname : 岳阳天晶水厂
             */

            private long create_time;
            private String amount;
            private String samount;
            private String dname;

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

            public String getSamount() {
                return samount;
            }

            public void setSamount(String samount) {
                this.samount = samount;
            }

            public String getDname() {
                return dname;
            }

            public void setDname(String dname) {
                this.dname = dname;
            }
        }
    }
}
