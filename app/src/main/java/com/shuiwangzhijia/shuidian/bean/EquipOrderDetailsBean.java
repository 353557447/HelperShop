package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/17.
 */

public class EquipOrderDetailsBean {

    /**
     * code : 200.0
     * msg : ok
     * data : {"dname":"1号设备","did":5,"amount_price":"10.00","pay_status":0,"refund_status":0,"order_time":1.555482816E9,"total_num":1,"order_code":"123456","discount_price":"0.00","picket_amount":"0.00","pay_method":0,"equipment_number":"aaa111","province":"广东省","city":"深圳市","district":"罗湖区","address":"某某大厦1号","goods":[{"goods_id":6.0580575E7,"good_pic":"20180921/8379d78ad6feb88665b46a56705041b6.jpg","gname":"益力矿泉水18.9L","price":"10.00","num":1}]}
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
         * dname : 1号设备
         * did : 5.0
         * amount_price : 10.00
         * pay_status : 0.0
         * refund_status : 0.0
         * order_time : 1.555482816E9
         * total_num : 1.0
         * order_code : 123456
         * discount_price : 0.00
         * picket_amount : 0.00
         * pay_method : 0.0
         * equipment_number : aaa111
         * province : 广东省
         * city : 深圳市
         * district : 罗湖区
         * address : 某某大厦1号
         * goods : [{"goods_id":6.0580575E7,"good_pic":"20180921/8379d78ad6feb88665b46a56705041b6.jpg","gname":"益力矿泉水18.9L","price":"10.00","num":1}]
         */

        private String dname;
        private int did;
        private String amount_price;
        private int pay_status;
        private int refund_status;
        private long order_time;
        private int total_num;
        private String order_code;
        private String discount_price;
        private String picket_amount;
        private int pay_method;
        private String equipment_number;
        private String province;
        private String city;
        private String district;
        private String address;
        private String user_name;
        private int cabinet_number;
        private int lattice_number;
        private List<GoodsBean> goods;

        public int getCabinet_number() {
            return cabinet_number;
        }

        public void setCabinet_number(int cabinet_number) {
            this.cabinet_number = cabinet_number;
        }

        public int getLattice_number() {
            return lattice_number;
        }

        public void setLattice_number(int lattice_number) {
            this.lattice_number = lattice_number;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getDname() {
            return dname;
        }

        public void setDname(String dname) {
            this.dname = dname;
        }

        public int getDid() {
            return did;
        }

        public void setDid(int did) {
            this.did = did;
        }

        public String getAmount_price() {
            return amount_price;
        }

        public void setAmount_price(String amount_price) {
            this.amount_price = amount_price;
        }

        public int getPay_status() {
            return pay_status;
        }

        public void setPay_status(int pay_status) {
            this.pay_status = pay_status;
        }

        public int getRefund_status() {
            return refund_status;
        }

        public void setRefund_status(int refund_status) {
            this.refund_status = refund_status;
        }

        public long getOrder_time() {
            return order_time;
        }

        public void setOrder_time(long order_time) {
            this.order_time = order_time;
        }

        public int getTotal_num() {
            return total_num;
        }

        public void setTotal_num(int total_num) {
            this.total_num = total_num;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public String getDiscount_price() {
            return discount_price;
        }

        public void setDiscount_price(String discount_price) {
            this.discount_price = discount_price;
        }

        public String getPicket_amount() {
            return picket_amount;
        }

        public void setPicket_amount(String picket_amount) {
            this.picket_amount = picket_amount;
        }

        public int getPay_method() {
            return pay_method;
        }

        public void setPay_method(int pay_method) {
            this.pay_method = pay_method;
        }

        public String getEquipment_number() {
            return equipment_number;
        }

        public void setEquipment_number(String equipment_number) {
            this.equipment_number = equipment_number;
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

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * goods_id : 6.0580575E7
             * good_pic : 20180921/8379d78ad6feb88665b46a56705041b6.jpg
             * gname : 益力矿泉水18.9L
             * price : 10.00
             * num : 1.0
             */

            private int goods_id;
            private String good_pic;
            private String gname;
            private String price;
            private int num;

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getGood_pic() {
                return good_pic;
            }

            public void setGood_pic(String good_pic) {
                this.good_pic = good_pic;
            }

            public String getGname() {
                return gname;
            }

            public void setGname(String gname) {
                this.gname = gname;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }
        }
    }
}
