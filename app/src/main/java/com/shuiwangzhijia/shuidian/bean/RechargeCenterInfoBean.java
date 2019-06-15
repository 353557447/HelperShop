package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lijn on 2019/4/3.
 */

public class RechargeCenterInfoBean implements Serializable{

    /**
     * code : 200.0
     * msg : 获取成功
     * data : {"list":[{"did":11,"sid":925,"balance":"0.00","nickname":"测试新37号","water_name":"银米科技","header_pic":"","recharge":[{"r_id":1,"rname":"15215165","ramount":"10.00","sail_amount":"20.00","aging":0,"dname":"银米科技"}]}]}
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
             * recharge : [{"r_id":1,"rname":"15215165","ramount":"10.00","sail_amount":"20.00","aging":0,"dname":"银米科技"}]
             */

            private int did;
            private int sid;
            private String balance;
            private String nickname;
            private String water_name;
            private String header_pic;
            private List<RechargeBean> recharge;

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

            public List<RechargeBean> getRecharge() {
                return recharge;
            }

            public void setRecharge(List<RechargeBean> recharge) {
                this.recharge = recharge;
            }

            public static class RechargeBean implements Serializable{
                /**
                 * r_id : 1.0
                 * rname : 15215165
                 * ramount : 10.00
                 * sail_amount : 20.00
                 * aging : 0.0
                 * dname : 银米科技
                 */

                private int r_id;
                private String rname;
                private String ramount;
                private String sail_amount;
                private int aging;
                private String dname;
                private long start_time;
                private long end_time;
                private boolean isChecked;

                public long getStart_time() {
                    return start_time;
                }

                public void setStart_time(long start_time) {
                    this.start_time = start_time;
                }

                public long getEnd_time() {
                    return end_time;
                }

                public void setEnd_time(long end_time) {
                    this.end_time = end_time;
                }

                public String getRegulation() {
                    return regulation;
                }

                public void setRegulation(String regulation) {
                    this.regulation = regulation;
                }

                private String regulation;

                public boolean isChecked() {
                    return isChecked;
                }

                public void setChecked(boolean checked) {
                    isChecked = checked;
                }

                public int getR_id() {
                    return r_id;
                }

                public void setR_id(int r_id) {
                    this.r_id = r_id;
                }

                public String getRname() {
                    return rname;
                }

                public void setRname(String rname) {
                    this.rname = rname;
                }

                public String getRamount() {
                    return ramount;
                }

                public void setRamount(String ramount) {
                    this.ramount = ramount;
                }

                public String getSail_amount() {
                    return sail_amount;
                }

                public void setSail_amount(String sail_amount) {
                    this.sail_amount = sail_amount;
                }

                public int getAging() {
                    return aging;
                }

                public void setAging(int aging) {
                    this.aging = aging;
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
}
