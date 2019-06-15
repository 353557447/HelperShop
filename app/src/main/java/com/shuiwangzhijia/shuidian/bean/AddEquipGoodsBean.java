package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/19.
 */

public class AddEquipGoodsBean {

    /**
     * code : 200
     * msg : success
     * data : [{"type_name":"全部","goods":[{"gid":"95392132","gname":"景田纯净水18.9L","pprice":"19.00","descrip":"景田","picturl":"20180921/83370704d0e11bbd014f1c6a084d84dd.jpg","t_name":"桶装水","id":8,"did":1,"is_up":0}]}]
     * scode : 0
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
         * goods : [{"gid":"95392132","gname":"景田纯净水18.9L","pprice":"19.00","descrip":"景田","picturl":"20180921/83370704d0e11bbd014f1c6a084d84dd.jpg","t_name":"桶装水","id":8,"did":1,"is_up":0}]
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
             * gid : 95392132
             * gname : 景田纯净水18.9L
             * pprice : 19.00
             * descrip : 景田
             * picturl : 20180921/83370704d0e11bbd014f1c6a084d84dd.jpg
             * t_name : 桶装水
             * id : 8
             * did : 1
             * is_up : 0
             */

            private String gid;
            private String gname;
            private String pprice;
            private String descrip;
            private String picturl;
            private String t_name;
            private int id;
            private int did;
            private int is_up;

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

            public int getDid() {
                return did;
            }

            public void setDid(int did) {
                this.did = did;
            }

            public int getIs_up() {
                return is_up;
            }

            public void setIs_up(int is_up) {
                this.is_up = is_up;
            }
        }
    }
}
