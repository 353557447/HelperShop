package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

public class RMCheckOutOrderListBean {


    /**
     * code : 200
     * msg : success
     * data : {"list":[{"order_sn":"2019030116586820","create_time":1551426214,"goods":[{"snum":1,"gid":"54754261","gname":"幸福"}],"goods_num":"1","amount":1,"snum":0}],"tatol":{"amount":21,"snum":0,"goods_num":"21","rebate_details":[{"gnum":"21","rule":"[{\"full\":1,\"amount\":\"1.00\",\"type\":2}]","amount":21,"snum":0,"gid":"","s_gid":""}]}}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public static class DataBean {
        /**
         * list : [{"order_sn":"2019030116586820","create_time":1551426214,"goods":[{"snum":1,"gid":"54754261","gname":"幸福"}],"goods_num":"1","amount":1,"snum":0}]
         * tatol : {"amount":21,"snum":0,"goods_num":"21","rebate_details":[{"gnum":"21","rule":"[{\"full\":1,\"amount\":\"1.00\",\"type\":2}]","amount":21,"snum":0,"gid":"","s_gid":""}]}
         */

        private TatolBean tatol;
        private List<ListBean> list;

        public TatolBean getTatol() {
            return tatol;
        }

        public void setTatol(TatolBean tatol) {
            this.tatol = tatol;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class TatolBean {
            /**
             * amount : 21
             * snum : 0
             * goods_num : 21
             * rebate_details : [{"gnum":"21","rule":"[{\"full\":1,\"amount\":\"1.00\",\"type\":2}]","amount":21,"snum":0,"gid":"","s_gid":""}]
             */

            private double amount;
            private int snum;
            private int goods_num;
            private List<RebateDetailsBean> rebate_details;

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public int getSnum() {
                return snum;
            }

            public void setSnum(int snum) {
                this.snum = snum;
            }

            public int getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(int goods_num) {
                this.goods_num = goods_num;
            }

            public List<RebateDetailsBean> getRebate_details() {
                return rebate_details;
            }

            public void setRebate_details(List<RebateDetailsBean> rebate_details) {
                this.rebate_details = rebate_details;
            }

            public static class RebateDetailsBean {
                /**
                 * gnum : 21
                 * rule : [{"full":1,"amount":"1.00","type":2}]
                 * amount : 21
                 * snum : 0
                 * gid :
                 * s_gid :
                 */

                private String gnum;
                private String rule;
                private int amount;
                private int snum;
                private String gid;
                private String s_gid;

                public String getGnum() {
                    return gnum;
                }

                public void setGnum(String gnum) {
                    this.gnum = gnum;
                }

                public String getRule() {
                    return rule;
                }

                public void setRule(String rule) {
                    this.rule = rule;
                }

                public int getAmount() {
                    return amount;
                }

                public void setAmount(int amount) {
                    this.amount = amount;
                }

                public int getSnum() {
                    return snum;
                }

                public void setSnum(int snum) {
                    this.snum = snum;
                }

                public String getGid() {
                    return gid;
                }

                public void setGid(String gid) {
                    this.gid = gid;
                }

                public String getS_gid() {
                    return s_gid;
                }

                public void setS_gid(String s_gid) {
                    this.s_gid = s_gid;
                }
            }
        }

        public static class ListBean {
            /**
             * order_sn : 2019030116586820
             * create_time : 1551426214
             * goods : [{"snum":1,"gid":"54754261","gname":"幸福"}]
             * goods_num : 1
             * amount : 1
             * snum : 0
             */

            private String order_sn;
            private long create_time;
            private String goods_num;
            private int amount;
            private int snum;
            private List<GoodsBean> goods;

            public String getOrder_sn() {
                return order_sn;
            }

            public void setOrder_sn(String order_sn) {
                this.order_sn = order_sn;
            }

            public long getCreate_time() {
                return create_time;
            }

            public void setCreate_time(long create_time) {
                this.create_time = create_time;
            }

            public String getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(String goods_num) {
                this.goods_num = goods_num;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public int getSnum() {
                return snum;
            }

            public void setSnum(int snum) {
                this.snum = snum;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            public static class GoodsBean {
                /**
                 * snum : 1
                 * gid : 54754261
                 * gname : 幸福
                 */

                private int snum;
                private String gid;
                private String gname;

                public int getSnum() {
                    return snum;
                }

                public void setSnum(int snum) {
                    this.snum = snum;
                }

                public String getGid() {
                    return gid;
                }

                public void setGid(String gid) {
                    this.gid = gid;
                }

                public String getGname() {
                    return gname;
                }

                public void setGname(String gname) {
                    this.gname = gname;
                }
            }
        }
    }
}
