package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/3.
 */

public class MyFragmentInfoBean {


    /**
     * code : 200.0
     * msg : success
     * data : {"shop":{"header_pic":"","sname":"测试7号","address":"华强北","lnglat":"114.069475,22.546186","province":"广东省","city":"深圳市","dist":"福田区","is_business":1,"is_perfect":0,"account":"15912345608","total_mypicket":"3","total_coupon":0,"total_balance":"0.00"},"recharge":{"list":[{"r_id":15,"did":11,"rname":"基本面基本面","ramount":"321321.00","sail_amount":"65165.00","aging":0,"end_time":1.5555168E9,"status":1,"add_time":1.554282504E9,"update_time":1.554282504E9},{"r_id":14,"did":11,"rname":"asdsad","ramount":"561456.00","sail_amount":"154.00","aging":0,"end_time":0,"status":1,"add_time":1.554282423E9,"update_time":1.554282423E9},{"r_id":13,"did":11,"rname":"231","ramount":"213.00","sail_amount":"231321.00","aging":0,"end_time":0,"status":1,"add_time":1.554282401E9,"update_time":1.554282401E9}],"total":13}}
     * scode : 0.0
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
         * shop : {"header_pic":"","sname":"测试7号","address":"华强北","lnglat":"114.069475,22.546186","province":"广东省","city":"深圳市","dist":"福田区","is_business":1,"is_perfect":0,"account":"15912345608","total_mypicket":"3","total_coupon":0,"total_balance":"0.00"}
         * recharge : {"list":[{"r_id":15,"did":11,"rname":"基本面基本面","ramount":"321321.00","sail_amount":"65165.00","aging":0,"end_time":1.5555168E9,"status":1,"add_time":1.554282504E9,"update_time":1.554282504E9},{"r_id":14,"did":11,"rname":"asdsad","ramount":"561456.00","sail_amount":"154.00","aging":0,"end_time":0,"status":1,"add_time":1.554282423E9,"update_time":1.554282423E9},{"r_id":13,"did":11,"rname":"231","ramount":"213.00","sail_amount":"231321.00","aging":0,"end_time":0,"status":1,"add_time":1.554282401E9,"update_time":1.554282401E9}],"total":13}
         */

        private ShopBean shop;
        private RechargeBean recharge;

        public ShopBean getShop() {
            return shop;
        }

        public void setShop(ShopBean shop) {
            this.shop = shop;
        }

        public RechargeBean getRecharge() {
            return recharge;
        }

        public void setRecharge(RechargeBean recharge) {
            this.recharge = recharge;
        }

        public static class ShopBean {
            /**
             * header_pic :
             * sname : 测试7号
             * address : 华强北
             * lnglat : 114.069475,22.546186
             * province : 广东省
             * city : 深圳市
             * dist : 福田区
             * is_business : 1.0
             * is_perfect : 0.0
             * account : 15912345608
             * total_mypicket : 3
             * total_coupon : 0.0
             * total_balance : 0.00
             */

            private String header_pic;
            private String sname;
            private String address;
            private String lnglat;
            private String province;
            private String city;
            private String dist;
            private int is_business;
            private int is_perfect;
            private String account;
            private String total_mypicket;
            private int total_coupon;
            private String total_balance;
            private String total_rebate_amount;

            public String getTotal_rebate_amount() {
                return total_rebate_amount;
            }

            public void setTotal_rebate_amount(String total_rebate_amount) {
                this.total_rebate_amount = total_rebate_amount;
            }

            public String getHeader_pic() {
                return header_pic;
            }

            public void setHeader_pic(String header_pic) {
                this.header_pic = header_pic;
            }

            public String getSname() {
                return sname;
            }

            public void setSname(String sname) {
                this.sname = sname;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getLnglat() {
                return lnglat;
            }

            public void setLnglat(String lnglat) {
                this.lnglat = lnglat;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDist() {
                return dist;
            }

            public void setDist(String dist) {
                this.dist = dist;
            }

            public int getIs_business() {
                return is_business;
            }

            public void setIs_business(int is_business) {
                this.is_business = is_business;
            }

            public int getIs_perfect() {
                return is_perfect;
            }

            public void setIs_perfect(int is_perfect) {
                this.is_perfect = is_perfect;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
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

            public String getTotal_balance() {
                return total_balance;
            }

            public void setTotal_balance(String total_balance) {
                this.total_balance = total_balance;
            }
        }

        public static class RechargeBean {
            /**
             * list : [{"r_id":15,"did":11,"rname":"基本面基本面","ramount":"321321.00","sail_amount":"65165.00","aging":0,"end_time":1.5555168E9,"status":1,"add_time":1.554282504E9,"update_time":1.554282504E9},{"r_id":14,"did":11,"rname":"asdsad","ramount":"561456.00","sail_amount":"154.00","aging":0,"end_time":0,"status":1,"add_time":1.554282423E9,"update_time":1.554282423E9},{"r_id":13,"did":11,"rname":"231","ramount":"213.00","sail_amount":"231321.00","aging":0,"end_time":0,"status":1,"add_time":1.554282401E9,"update_time":1.554282401E9}]
             * total : 13.0
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
                 * r_id : 15.0
                 * did : 11.0
                 * rname : 基本面基本面
                 * ramount : 321321.00
                 * sail_amount : 65165.00
                 * aging : 0.0
                 * end_time : 1.5555168E9
                 * status : 1.0
                 * add_time : 1.554282504E9
                 * update_time : 1.554282504E9
                 */

                private int r_id;
                private int did;
                private String rname;
                private String ramount;
                private String sail_amount;
                private int aging;
                private long end_time;
                private int status;
                private long add_time;
                private long update_time;

                public int getR_id() {
                    return r_id;
                }

                public void setR_id(int r_id) {
                    this.r_id = r_id;
                }

                public int getDid() {
                    return did;
                }

                public void setDid(int did) {
                    this.did = did;
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

                public long getEnd_time() {
                    return end_time;
                }

                public void setEnd_time(long end_time) {
                    this.end_time = end_time;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public long getAdd_time() {
                    return add_time;
                }

                public void setAdd_time(long add_time) {
                    this.add_time = add_time;
                }

                public long getUpdate_time() {
                    return update_time;
                }

                public void setUpdate_time(long update_time) {
                    this.update_time = update_time;
                }
            }
        }
    }
}
