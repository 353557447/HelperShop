package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/18.
 */

public class EquipGoodsBean {

    /**
     * code : 200.0
     * msg : success
     * data : [{"type_name":"全部","goods":[{"price":"19.00","is_up":1,"gid":"95392132","gname":"景田纯净水18.9L","pprice":"19.00","descrip":"景田","picturl":"20180921/83370704d0e11bbd014f1c6a084d84dd.jpg","t_name":"桶装水","id":5497},{"price":"20.00","is_up":1,"gid":"38354731","gname":"农夫山泉18.9L","pprice":"24.00","descrip":"有点甜哦","picturl":"20180921/400203843c3c10bdc268463004bb09c9.jpg","t_name":"桶装水","id":5498},{"price":"21.00","is_up":1,"gid":"83032112","gname":"景田百岁山18.9L","pprice":"21.00","descrip":"景田","picturl":"20180921/a260d215b1f1dfa585823266a7281e3f.jpg","t_name":"桶装水","id":5499},{"price":"22.00","is_up":1,"gid":"60580575","gname":"益力矿泉水18.9L","pprice":"21.00","descrip":"益力","picturl":"20180921/8379d78ad6feb88665b46a56705041b6.jpg","t_name":"桶装水","id":5500},{"price":"24.00","is_up":1,"gid":"19251100","gname":"景田纯净水360ml*24","pprice":"32.00","descrip":"景田纯净水","picturl":"20180926/53fcdc81c64a83628114bbfb82dbb55a.jpg","t_name":"瓶装水","id":5501},{"price":"25.00","is_up":1,"gid":"30425532","gname":"景田纯净水1.5L*12","pprice":"36.00","descrip":"景田纯净水","picturl":"20180926/13b68ce97f1a813592b95f19d977302f.jpg","t_name":"瓶装水","id":5502}]},{"type_name":"桶装水","goods":[{"price":"19.00","is_up":1,"gid":"95392132","gname":"景田纯净水18.9L","pprice":"19.00","descrip":"景田","picturl":"20180921/83370704d0e11bbd014f1c6a084d84dd.jpg","t_name":"桶装水","id":5497},{"price":"20.00","is_up":1,"gid":"38354731","gname":"农夫山泉18.9L","pprice":"24.00","descrip":"有点甜哦","picturl":"20180921/400203843c3c10bdc268463004bb09c9.jpg","t_name":"桶装水","id":5498},{"price":"21.00","is_up":1,"gid":"83032112","gname":"景田百岁山18.9L","pprice":"21.00","descrip":"景田","picturl":"20180921/a260d215b1f1dfa585823266a7281e3f.jpg","t_name":"桶装水","id":5499},{"price":"22.00","is_up":1,"gid":"60580575","gname":"益力矿泉水18.9L","pprice":"21.00","descrip":"益力","picturl":"20180921/8379d78ad6feb88665b46a56705041b6.jpg","t_name":"桶装水","id":5500}]},{"type_name":"瓶装水","goods":[{"price":"24.00","is_up":1,"gid":"19251100","gname":"景田纯净水360ml*24","pprice":"32.00","descrip":"景田纯净水","picturl":"20180926/53fcdc81c64a83628114bbfb82dbb55a.jpg","t_name":"瓶装水","id":5501},{"price":"25.00","is_up":1,"gid":"30425532","gname":"景田纯净水1.5L*12","pprice":"36.00","descrip":"景田纯净水","picturl":"20180926/13b68ce97f1a813592b95f19d977302f.jpg","t_name":"瓶装水","id":5502}]}]
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
         * type_name : 全部
         * goods : [{"price":"19.00","is_up":1,"gid":"95392132","gname":"景田纯净水18.9L","pprice":"19.00","descrip":"景田","picturl":"20180921/83370704d0e11bbd014f1c6a084d84dd.jpg","t_name":"桶装水","id":5497},{"price":"20.00","is_up":1,"gid":"38354731","gname":"农夫山泉18.9L","pprice":"24.00","descrip":"有点甜哦","picturl":"20180921/400203843c3c10bdc268463004bb09c9.jpg","t_name":"桶装水","id":5498},{"price":"21.00","is_up":1,"gid":"83032112","gname":"景田百岁山18.9L","pprice":"21.00","descrip":"景田","picturl":"20180921/a260d215b1f1dfa585823266a7281e3f.jpg","t_name":"桶装水","id":5499},{"price":"22.00","is_up":1,"gid":"60580575","gname":"益力矿泉水18.9L","pprice":"21.00","descrip":"益力","picturl":"20180921/8379d78ad6feb88665b46a56705041b6.jpg","t_name":"桶装水","id":5500},{"price":"24.00","is_up":1,"gid":"19251100","gname":"景田纯净水360ml*24","pprice":"32.00","descrip":"景田纯净水","picturl":"20180926/53fcdc81c64a83628114bbfb82dbb55a.jpg","t_name":"瓶装水","id":5501},{"price":"25.00","is_up":1,"gid":"30425532","gname":"景田纯净水1.5L*12","pprice":"36.00","descrip":"景田纯净水","picturl":"20180926/13b68ce97f1a813592b95f19d977302f.jpg","t_name":"瓶装水","id":5502}]
         */

        private String type_name;
        private List<GoodsBean> goods;

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * price : 19.00
             * is_up : 1.0
             * gid : 95392132
             * gname : 景田纯净水18.9L
             * pprice : 19.00
             * descrip : 景田
             * picturl : 20180921/83370704d0e11bbd014f1c6a084d84dd.jpg
             * t_name : 桶装水
             * id : 5497.0
             */

            private String price;
            private int is_up;
            private String gid;
            private String gname;
            private String pprice;
            private String descrip;
            private String picturl;
            private String t_name;
            private int id;

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getIs_up() {
                return is_up;
            }

            public void setIs_up(int is_up) {
                this.is_up = is_up;
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

            public String getPprice() {
                return pprice;
            }

            public void setPprice(String pprice) {
                this.pprice = pprice;
            }

            public String getDescrip() {
                return descrip;
            }

            public void setDescrip(String descrip) {
                this.descrip = descrip;
            }

            public String getPicturl() {
                return picturl;
            }

            public void setPicturl(String picturl) {
                this.picturl = picturl;
            }

            public String getT_name() {
                return t_name;
            }

            public void setT_name(String t_name) {
                this.t_name = t_name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
