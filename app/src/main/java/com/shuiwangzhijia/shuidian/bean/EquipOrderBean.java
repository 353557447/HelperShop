package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/17.
 */

public class EquipOrderBean {

    /**
     * code : 200.0
     * msg : ok
     * data : [{"dname":"1号设备","amount_price":"10.00","pay_status":0,"refund_status":0,"total_num":1,"order_code":"123456","goods":[{"goods_id":6.0580575E7,"good_pic":"20180921/8379d78ad6feb88665b46a56705041b6.jpg","gname":"益力矿泉水18.9L","price":"10.00","num":1}]}]
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
         * dname : 1号设备
         * amount_price : 10.00
         * pay_status : 0.0
         * refund_status : 0.0
         * total_num : 1.0
         * order_code : 123456
         * goods : [{"goods_id":6.0580575E7,"good_pic":"20180921/8379d78ad6feb88665b46a56705041b6.jpg","gname":"益力矿泉水18.9L","price":"10.00","num":1}]
         */

        private String dname;
        private String amount_price;
        private int pay_status;
        private int refund_status;
        private int total_num;
        private String order_code;
        private long order_time;
        private List<GoodsBean> goods;

        public long getOrder_time() {
            return order_time;
        }

        public void setOrder_time(long order_time) {
            this.order_time = order_time;
        }

        public String getDname() {
            return dname;
        }

        public void setDname(String dname) {
            this.dname = dname;
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
