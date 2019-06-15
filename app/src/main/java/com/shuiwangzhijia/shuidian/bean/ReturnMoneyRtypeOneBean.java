package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/29.
 */

public class ReturnMoneyRtypeOneBean {

    /**
     * code : 200.0
     * msg : 查询成功
     * data : [{"rebate_no":"F2019042516594403","r_type":1,"rbasis":1,"r_way":1,"rule":"1","total_amount":"0.01","s_count":0,"status":3,"update_time":1.556176799E9,"apply_time":1.556176799E9,"goods":[{"gid":"76676264","r_id":154405,"r_way":1,"rule":1,"rbasis":1,"gname":"小分子空气水","gnum":3,"amount":"0.01","snum":0,"ratio":"150%","full":2}]},{"rebate_no":"F2019042516594398","r_type":1,"rbasis":1,"r_way":1,"rule":"1","total_amount":"0.01","s_count":0,"status":3,"update_time":1.55617572E9,"apply_time":1.55617572E9,"goods":[{"gid":"76676264","r_id":154405,"r_way":1,"rule":1,"rbasis":1,"gname":"小分子空气水","gnum":3,"amount":"0.01","snum":0,"ratio":"150%","full":2}]}]
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
         * rebate_no : F2019042516594403
         * r_type : 1.0
         * rbasis : 1.0
         * r_way : 1.0
         * rule : 1
         * total_amount : 0.01
         * s_count : 0.0
         * status : 3.0
         * update_time : 1.556176799E9
         * apply_time : 1.556176799E9
         * goods : [{"gid":"76676264","r_id":154405,"r_way":1,"rule":1,"rbasis":1,"gname":"小分子空气水","gnum":3,"amount":"0.01","snum":0,"ratio":"150%","full":2}]
         */

        private String rebate_no;
        private int r_type;
        private int rbasis;
        private int r_way;
        private String rule;
        private String total_amount;
        private int s_count;
        private int status;
        private long update_time;
        private long apply_time;
        private List<GoodsBean> goods;

        public String getRebate_no() {
            return rebate_no;
        }

        public void setRebate_no(String rebate_no) {
            this.rebate_no = rebate_no;
        }

        public int getR_type() {
            return r_type;
        }

        public void setR_type(int r_type) {
            this.r_type = r_type;
        }

        public int getRbasis() {
            return rbasis;
        }

        public void setRbasis(int rbasis) {
            this.rbasis = rbasis;
        }

        public int getR_way() {
            return r_way;
        }

        public void setR_way(int r_way) {
            this.r_way = r_way;
        }

        public String getRule() {
            return rule;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public int getS_count() {
            return s_count;
        }

        public void setS_count(int s_count) {
            this.s_count = s_count;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }

        public long getApply_time() {
            return apply_time;
        }

        public void setApply_time(long apply_time) {
            this.apply_time = apply_time;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * gid : 76676264
             * r_id : 154405.0
             * r_way : 1.0
             * rule : 1.0
             * rbasis : 1.0
             * gname : 小分子空气水
             * gnum : 3.0
             * amount : 0.01
             * snum : 0.0
             * ratio : 150%
             * full : 2.0
             */

            private String gid;
            private int r_id;
            private int r_way;
            private int rule;
            private int rbasis;
            private String gname;
            private int gnum;
            private String amount;
            private int snum;
            private String ratio;
            private int full;

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public int getR_id() {
                return r_id;
            }

            public void setR_id(int r_id) {
                this.r_id = r_id;
            }

            public int getR_way() {
                return r_way;
            }

            public void setR_way(int r_way) {
                this.r_way = r_way;
            }

            public int getRule() {
                return rule;
            }

            public void setRule(int rule) {
                this.rule = rule;
            }

            public int getRbasis() {
                return rbasis;
            }

            public void setRbasis(int rbasis) {
                this.rbasis = rbasis;
            }

            public String getGname() {
                return gname;
            }

            public void setGname(String gname) {
                this.gname = gname;
            }

            public int getGnum() {
                return gnum;
            }

            public void setGnum(int gnum) {
                this.gnum = gnum;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public int getSnum() {
                return snum;
            }

            public void setSnum(int snum) {
                this.snum = snum;
            }

            public String getRatio() {
                return ratio;
            }

            public void setRatio(String ratio) {
                this.ratio = ratio;
            }

            public int getFull() {
                return full;
            }

            public void setFull(int full) {
                this.full = full;
            }
        }
    }
}
