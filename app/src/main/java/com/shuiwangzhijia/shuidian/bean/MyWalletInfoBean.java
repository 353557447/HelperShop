package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lijn on 2019/4/3.
 */

public class MyWalletInfoBean implements Serializable{

    /**
     * code : 200.0
     * msg : 获取成功
     * data : {"list":[{"did":11,"sid":925,"balance":"0.00","nickname":"测试新37号","water_name":"银米科技","header_pic":"","total_mypicket":"3","total_coupon":0}]}
     * scode : 200.0
     */

    private int code;
    private String msg;
    private DataBean data;
    private int scode;

    public double getCode() {
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

    public double getScode() {
        return scode;
    }

    public void setScode(int scode) {
        this.scode = scode;
    }

    public static class DataBean implements Serializable{
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            /**
             * did : 11.0
             * sid : 925.0
             * balance : 0.00
             * nickname : 测试新37号
             * water_name : 银米科技
             * header_pic :
             * total_mypicket : 3
             * total_coupon : 0.0
             */

            private int did;
            private int sid;
            private String balance;
            private String nickname;
            private String water_name;
            private String header_pic;
            private String total_mypicket;
            private int total_coupon;
            private String rebate_amount;

            public String getRebate_amount() {
                return rebate_amount;
            }

            public void setRebate_amount(String rebate_amount) {
                this.rebate_amount = rebate_amount;
            }

            public int getDid() {
                return did;
            }

            public void setDid(int did) {
                this.did = did;
            }

            public int getSid() {
                return sid;
            }

            public void setSid(int sid) {
                this.sid = sid;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getWater_name() {
                return water_name;
            }

            public void setWater_name(String water_name) {
                this.water_name = water_name;
            }

            public String getHeader_pic() {
                return header_pic;
            }

            public void setHeader_pic(String header_pic) {
                this.header_pic = header_pic;
            }

            public String getTotal_mypicket() {
                return total_mypicket;
            }

            public void setTotal_mypicket(String total_mypicket) {
                this.total_mypicket = total_mypicket;
            }

            public int getTotal_coupon() {
                return total_coupon;
            }

            public void setTotal_coupon(int total_coupon) {
                this.total_coupon = total_coupon;
            }
        }
    }
}
