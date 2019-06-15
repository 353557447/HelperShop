package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/22.
 */

public class SpEarnWdRecordBean {

    /**
     * code : 200.0
     * msg : ok
     * data : [{"forward_code":"123456789","amount":"0.22","remark":"","status":2},{"forward_code":"123456789","amount":"0.22","remark":"","status":2},{"forward_code":"123456789","amount":"0.22","remark":"","status":0},{"forward_code":"123456789","amount":"0.22","remark":"","status":1},{"forward_code":"123456789","amount":"0.22","remark":"","status":2},{"forward_code":"123456789","amount":"0.22","remark":"","status":3},{"forward_code":"123456789","amount":"0.22","remark":"","status":4}]
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
         * forward_code : 123456789
         * amount : 0.22
         * remark :
         * status : 2.0
         */

        private String forward_code;
        private String amount;
        private String remark;
        private int status;
        private String refuse;
        private long add_time;

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public String getRefuse() {
            return refuse;
        }

        public void setRefuse(String refuse) {
            this.refuse = refuse;
        }

        public String getForward_code() {
            return forward_code;
        }

        public void setForward_code(String forward_code) {
            this.forward_code = forward_code;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
