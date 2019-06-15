package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/23.
 */

public class GMNewBean {

    /**
     * data : [{"gtype":9,"gname":"瓶装水","list":[{"id":5501,"gid":"19251100","gtype":9,"gname":"景田纯净水360ml*24","pprice":"32.00","descrip":"景田纯净水","picturl":"20180926/53fcdc81c64a83628114bbfb82dbb55a.jpg","is_up":1,"sid":673,"price":"1.00"},{"id":5502,"gid":"30425532","gtype":9,"gname":"景田纯净水1.5L*12","pprice":"36.00","descrip":"景田纯净水","picturl":"20180926/13b68ce97f1a813592b95f19d977302f.jpg","is_up":1,"sid":673,"price":"1.00"}]},{"gtype":16,"gname":"桶装水","list":[{"id":5500,"gid":"60580575","gtype":16,"gname":"益力矿泉水18.9L","pprice":"21.00","descrip":"益力","picturl":"20180921/8379d78ad6feb88665b46a56705041b6.jpg","is_up":1,"sid":673,"price":"1.00"}]}]
     * code : 200.0
     * msg : success
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
         * gtype : 9.0
         * gname : 瓶装水
         * list : [{"id":5501,"gid":"19251100","gtype":9,"gname":"景田纯净水360ml*24","pprice":"32.00","descrip":"景田纯净水","picturl":"20180926/53fcdc81c64a83628114bbfb82dbb55a.jpg","is_up":1,"sid":673,"price":"1.00"},{"id":5502,"gid":"30425532","gtype":9,"gname":"景田纯净水1.5L*12","pprice":"36.00","descrip":"景田纯净水","picturl":"20180926/13b68ce97f1a813592b95f19d977302f.jpg","is_up":1,"sid":673,"price":"1.00"}]
         */
        private String gname;
        private List<ListBean> list;

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 5501.0
             * gid : 19251100
             * gtype : 9.0
             * gname : 景田纯净水360ml*24
             * pprice : 32.00
             * descrip : 景田纯净水
             * picturl : 20180926/53fcdc81c64a83628114bbfb82dbb55a.jpg
             * is_up : 1.0
             * sid : 673.0
             * price : 1.00
             */

            private int id;
            private String gid;
            private int gtype;
            private String gname;
            private String pprice;
            private String descrip;
            private String picturl;
            private int is_up;
            private int sid;
            private String price;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public int getGtype() {
                return gtype;
            }

            public void setGtype(int gtype) {
                this.gtype = gtype;
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

            public int getIs_up() {
                return is_up;
            }

            public void setIs_up(int is_up) {
                this.is_up = is_up;
            }

            public int getSid() {
                return sid;
            }

            public void setSid(int sid) {
                this.sid = sid;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
