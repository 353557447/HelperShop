package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/17.
 */

public class SmartEquipDataBean {

    /**
     * code : 200.0
     * msg : success
     * data : [{"id":17,"device_id":5,"cabinet_number":1,"add_time":1.555467816E9},{"id":18,"device_id":5,"cabinet_number":2,"add_time":1.555467816E9},{"id":19,"device_id":5,"cabinet_number":3,"add_time":1.555467816E9},{"id":20,"device_id":5,"cabinet_number":4,"add_time":1.555467816E9}]
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
         * id : 17.0
         * device_id : 5.0
         * cabinet_number : 1.0
         * add_time : 1.555467816E9
         */

        private int id;
        private int device_id;
        private int cabinet_number;
        private long add_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDevice_id() {
            return device_id;
        }

        public void setDevice_id(int device_id) {
            this.device_id = device_id;
        }

        public int getCabinet_number() {
            return cabinet_number;
        }

        public void setCabinet_number(int cabinet_number) {
            this.cabinet_number = cabinet_number;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }
    }
}
