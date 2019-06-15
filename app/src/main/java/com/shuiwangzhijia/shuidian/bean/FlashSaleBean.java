package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/16.
 */

public class FlashSaleBean {

    /**
     * code : 200.0
     * msg : 查询成功
     * data : [{"sid":878,"seckill_id":124586,"is_exit":0,"date_time":1.555409104E9,"start_time":"06:02:00","end_time":"12:02:02","flag":2,"goods":[{"gname":"农夫山泉18.9L","pprice":"24.00","picturl":"20180921/400203843c3c10bdc268463004bb09c9.jpg","seckill_price":"50.00","seckill_restrictions":12,"seckill_stock":60,"seckill_sales_volume":0},{"gname":"麦宝纯净水16.8L","pprice":"15.00","picturl":"20180921/7653043954d4fe32cf728f78196efdef.jpg","seckill_price":"32.00","seckill_restrictions":90,"seckill_stock":18,"seckill_sales_volume":0},{"gname":"农夫山泉18.9L","pprice":"24.00","picturl":"20180921/400203843c3c10bdc268463004bb09c9.jpg","seckill_price":"1000.00","seckill_restrictions":80,"seckill_stock":60,"seckill_sales_volume":0},{"gname":"益力矿泉水18.9L","pprice":"21.00","picturl":"20180921/8379d78ad6feb88665b46a56705041b6.jpg","seckill_price":"6000.00","seckill_restrictions":9000,"seckill_stock":8000,"seckill_sales_volume":0},{"gname":"麦宝纯净水18.9L","pprice":"15.00","picturl":"20180921/af7175a2f94e458f53de960339f46eab.jpg","seckill_price":"456446.00","seckill_restrictions":4646,"seckill_stock":57646,"seckill_sales_volume":0}]}]
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
         * sid : 878.0
         * seckill_id : 124586.0
         * is_exit : 0.0
         * date_time : 1.555409104E9
         * start_time : 06:02:00
         * end_time : 12:02:02
         * flag : 2.0
         * goods : [{"gname":"农夫山泉18.9L","pprice":"24.00","picturl":"20180921/400203843c3c10bdc268463004bb09c9.jpg","seckill_price":"50.00","seckill_restrictions":12,"seckill_stock":60,"seckill_sales_volume":0},{"gname":"麦宝纯净水16.8L","pprice":"15.00","picturl":"20180921/7653043954d4fe32cf728f78196efdef.jpg","seckill_price":"32.00","seckill_restrictions":90,"seckill_stock":18,"seckill_sales_volume":0},{"gname":"农夫山泉18.9L","pprice":"24.00","picturl":"20180921/400203843c3c10bdc268463004bb09c9.jpg","seckill_price":"1000.00","seckill_restrictions":80,"seckill_stock":60,"seckill_sales_volume":0},{"gname":"益力矿泉水18.9L","pprice":"21.00","picturl":"20180921/8379d78ad6feb88665b46a56705041b6.jpg","seckill_price":"6000.00","seckill_restrictions":9000,"seckill_stock":8000,"seckill_sales_volume":0},{"gname":"麦宝纯净水18.9L","pprice":"15.00","picturl":"20180921/af7175a2f94e458f53de960339f46eab.jpg","seckill_price":"456446.00","seckill_restrictions":4646,"seckill_stock":57646,"seckill_sales_volume":0}]
         */

        private int sid;
        private int seckill_id;
        private int is_exit;
        private long date_time;
        private String start_time;
        private String end_time;
        private int flag;
        private List<GoodsBean> goods;

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public int getSeckill_id() {
            return seckill_id;
        }

        public void setSeckill_id(int seckill_id) {
            this.seckill_id = seckill_id;
        }

        public int getIs_exit() {
            return is_exit;
        }

        public void setIs_exit(int is_exit) {
            this.is_exit = is_exit;
        }

        public long getDate_time() {
            return date_time;
        }

        public void setDate_time(long date_time) {
            this.date_time = date_time;
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

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * gname : 农夫山泉18.9L
             * pprice : 24.00
             * picturl : 20180921/400203843c3c10bdc268463004bb09c9.jpg
             * seckill_price : 50.00
             * seckill_restrictions : 12.0
             * seckill_stock : 60.0
             * seckill_sales_volume : 0.0
             */

            private String gname;
            private String pprice;
            private String picturl;
            private String seckill_price;
            private int seckill_restrictions;
            private int seckill_stock;
            private int seckill_sales_volume;

            public String getGname() {
                return gname;
            }

            public void setGname(String gname) {
                this.gname = gname;
            }

            public String getPprice() {
                return pprice;
            }

            public void setPprice(String pprice) {
                this.pprice = pprice;
            }

            public String getPicturl() {
                return picturl;
            }

            public void setPicturl(String picturl) {
                this.picturl = picturl;
            }

            public String getSeckill_price() {
                return seckill_price;
            }

            public void setSeckill_price(String seckill_price) {
                this.seckill_price = seckill_price;
            }

            public int getSeckill_restrictions() {
                return seckill_restrictions;
            }

            public void setSeckill_restrictions(int seckill_restrictions) {
                this.seckill_restrictions = seckill_restrictions;
            }

            public int getSeckill_stock() {
                return seckill_stock;
            }

            public void setSeckill_stock(int seckill_stock) {
                this.seckill_stock = seckill_stock;
            }

            public int getSeckill_sales_volume() {
                return seckill_sales_volume;
            }

            public void setSeckill_sales_volume(int seckill_sales_volume) {
                this.seckill_sales_volume = seckill_sales_volume;
            }
        }
    }
}
