package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/16.
 */

public class FlashSaleSessionBean {

    /**
     * code : 200.0
     * msg : 查询成功
     * data : [{"seckill_id":124586,"time_name":1,"start_time":"16:00:00","end_time":"18:00:00"},{"seckill_id":124587,"time_name":2,"start_time":"18:00:00","end_time":"19:00:00"},{"seckill_id":124617,"time_name":15,"start_time":"18:05:05","end_time":"19:05:05"}]
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
         * seckill_id : 124586.0
         * time_name : 1.0
         * start_time : 16:00:00
         * end_time : 18:00:00
         */

        private int seckill_id;
        private int time_name;
        private String start_time;
        private String end_time;

        public int getSeckill_id() {
            return seckill_id;
        }

        public void setSeckill_id(int seckill_id) {
            this.seckill_id = seckill_id;
        }

        public int getTime_name() {
            return time_name;
        }

        public void setTime_name(int time_name) {
            this.time_name = time_name;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }
    }
}
