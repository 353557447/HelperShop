package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xxc on 2019/4/3.
 */

public class ShowPlantsBean implements Serializable{

    /**
     * sname : 银米科技
     * did : 11
     * delivery_type : 0
     * harvest_info : {"sname":"小徐","phone":"18390188923","address":"湖南省岳阳市岳阳楼区岳阳楼区小福星幼儿园","order_sn":"2019030416587704","snum":2,"total_price":"4.00","order_goods":[{"gid":"21250220","snum":2,"picturl":"20180926/056604c7b108db20bc01fe8a54c73254.jpg","price":"2.00","did":11,"active":"[{\"full\":10,\"reduce\":1}]","activity_type":1}]}
     */

    private String sname;
    private int did;
    private int delivery_type;
    private HarvestInfoBean harvest_info;

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public int getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(int delivery_type) {
        this.delivery_type = delivery_type;
    }

    public HarvestInfoBean getHarvest_info() {
        return harvest_info;
    }

    public void setHarvest_info(HarvestInfoBean harvest_info) {
        this.harvest_info = harvest_info;
    }

    public static class HarvestInfoBean implements Serializable{
        /**
         * sname : 小徐
         * phone : 18390188923
         * address : 湖南省岳阳市岳阳楼区岳阳楼区小福星幼儿园
         * order_sn : 2019030416587704
         * snum : 2
         * total_price : 4.00
         * order_goods : [{"gid":"21250220","snum":2,"picturl":"20180926/056604c7b108db20bc01fe8a54c73254.jpg","price":"2.00","did":11,"active":"[{\"full\":10,\"reduce\":1}]","activity_type":1}]
         */

        private String sname;
        private String phone;
        private String address;
        private String order_sn;
        private int snum;
        private String total_price;
        private List<OrderGoodsBean> order_goods;
        private int address_id;

        public String getAname() {
            return aname;
        }

        public void setAname(String aname) {
            this.aname = aname;
        }

        private String aname;
        public int getAddress_id() {
            return address_id;
        }

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public int getSnum() {
            return snum;
        }

        public void setSnum(int snum) {
            this.snum = snum;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public List<OrderGoodsBean> getOrder_goods() {
            return order_goods;
        }

        public void setOrder_goods(List<OrderGoodsBean> order_goods) {
            this.order_goods = order_goods;
        }

        public static class OrderGoodsBean implements Serializable{
            /**
             * gid : 21250220
             * snum : 2
             * picturl : 20180926/056604c7b108db20bc01fe8a54c73254.jpg
             * price : 2.00
             * did : 11
             * active : [{"full":10,"reduce":1}]
             * activity_type : 1
             */

            private String gid;
            private int snum;
            private String picturl;
            private String price;
            private int did;
            private String active;
            private int activity_type;
            private String gname;
            private String pprice;
            private int count;
            private boolean check;//选择状态

            public boolean isCheck() {
                return check;
            }

            public void setCheck(boolean check) {
                this.check = check;
            }

            public String getPprice() {
                return pprice;
            }

            public void setPprice(String pprice) {
                this.pprice = pprice;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getGname() {
                return gname;
            }

            public void setGname(String gname) {
                this.gname = gname;
            }

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public int getSnum() {
                return snum;
            }

            public void setSnum(int snum) {
                this.snum = snum;
            }

            public String getPicturl() {
                return picturl;
            }

            public void setPicturl(String picturl) {
                this.picturl = picturl;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getDid() {
                return did;
            }

            public void setDid(int did) {
                this.did = did;
            }

            public String getActive() {
                return active;
            }

            public void setActive(String active) {
                this.active = active;
            }

            public int getActivity_type() {
                return activity_type;
            }

            public void setActivity_type(int activity_type) {
                this.activity_type = activity_type;
            }
        }
    }
}
