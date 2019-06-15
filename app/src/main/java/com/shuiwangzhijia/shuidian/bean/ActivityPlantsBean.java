package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xcc on 2019/4/3.
 */

public class ActivityPlantsBean implements Serializable{

    /**
     * sname : 直营供水
     * did : 1712
     * activity_type : 2
     * goods_tpe : 1
     * ruler : {"full":"0.02","links":[{"sid":16594042,"num":"1","name":"屈臣氏水票"},{"sid":16593907,"num":"1"}]}
     * full_giving : [{"gname":"景田百岁山","gid":"36420361","picturl":"20190415/8e3de50e63dc1b8848b40af47e27f465.jpg","did":1712,"least_p":1,"price":"0.10","is_shield":0},{"gname":"百岁山","gid":"48250360","picturl":"20190415/b1e42237d9c68849dbee71a757a54bbc.jpg","did":1712,"least_p":1,"price":"0.01","is_shield":null},{"gname":"水文章","gid":"45245242","picturl":"20190416/d0aaaaef71e52a82a5b85aa09a833e9a.jpg","did":1712,"least_p":1,"price":"0.01","is_shield":null},{"gname":"屈臣氏","gid":"64081030","picturl":"20190416/ebba2dab56ec33865819dc9fae153e2e.jpg","did":1712,"least_p":1,"price":"0.01","is_shield":null}]
     * rebate : []
     * rebate_type : 0
     * recharge : 充20.00赠-19.99,充2.00赠0.00,充2.00赠0.00,充2.00赠0.00,充2.00赠0.00,充2.00赠0.00
     */

    private String sname;
    private int did;
    private int activity_type;
    private int goods_tpe;
    private String ruler;
    private int rebate_type;
    private String recharge;
    private List<GoodsBean> full_giving;
    private List<GoodsBean> rebate;

    public List<GoodsBean> getFull_minus() {
        return full_minus;
    }

    public void setFull_minus(List<GoodsBean> full_minus) {
        this.full_minus = full_minus;
    }

    private List<GoodsBean> full_minus;

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

    public int getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(int activity_type) {
        this.activity_type = activity_type;
    }

    public int getGoods_tpe() {
        return goods_tpe;
    }

    public void setGoods_tpe(int goods_tpe) {
        this.goods_tpe = goods_tpe;
    }

    public String getRuler() {
        return ruler;
    }

    public void setRuler(String ruler) {
        this.ruler = ruler;
    }

    public int getRebate_type() {
        return rebate_type;
    }

    public void setRebate_type(int rebate_type) {
        this.rebate_type = rebate_type;
    }

    public String getRecharge() {
        return recharge;
    }

    public void setRecharge(String recharge) {
        this.recharge = recharge;
    }

    public List<GoodsBean> getFull_giving() {
        return full_giving;
    }

    public void setFull_giving(List<GoodsBean> full_giving) {
        this.full_giving = full_giving;
    }

    public List<GoodsBean> getRebate() {
        return rebate;
    }

    public void setRebate(List<GoodsBean> rebate) {
        this.rebate = rebate;
    }

    public static class FullGivingBean {
        /**
         * gname : 景田百岁山
         * gid : 36420361
         * picturl : 20190415/8e3de50e63dc1b8848b40af47e27f465.jpg
         * did : 1712
         * least_p : 1
         * price : 0.10
         * is_shield : 0
         */

        private String gname;
        private String gid;
        private String picturl;
        private int did;
        private int least_p;
        private String price;
        private int is_shield;

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

        public String getPicturl() {
            return picturl;
        }

        public void setPicturl(String picturl) {
            this.picturl = picturl;
        }

        public int getDid() {
            return did;
        }

        public void setDid(int did) {
            this.did = did;
        }

        public int getLeast_p() {
            return least_p;
        }

        public void setLeast_p(int least_p) {
            this.least_p = least_p;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getIs_shield() {
            return is_shield;
        }

        public void setIs_shield(int is_shield) {
            this.is_shield = is_shield;
        }
    }
}
