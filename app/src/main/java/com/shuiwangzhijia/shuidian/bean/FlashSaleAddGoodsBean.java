package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/16.
 */

public class FlashSaleAddGoodsBean {

    /**
     * code : 200.0
     * msg : 查询成功
     * data : [{"gid":"95392132","gname":"景田纯净水18.9L","pprice":"19.00","picturl":"20180921/83370704d0e11bbd014f1c6a084d84dd.jpg"},{"gid":"38354731","gname":"农夫山泉18.9L","pprice":"24.00","picturl":"20180921/400203843c3c10bdc268463004bb09c9.jpg"},{"gid":"83032112","gname":"景田百岁山18.9L","pprice":"21.00","picturl":"20180921/a260d215b1f1dfa585823266a7281e3f.jpg"},{"gid":"60580575","gname":"益力矿泉水18.9L","pprice":"21.00","picturl":"20180921/8379d78ad6feb88665b46a56705041b6.jpg"},{"gid":"19251100","gname":"景田纯净水360ml*24","pprice":"32.00","picturl":"20180926/53fcdc81c64a83628114bbfb82dbb55a.jpg"},{"gid":"30425532","gname":"景田纯净水1.5L*12","pprice":"36.00","picturl":"20180926/13b68ce97f1a813592b95f19d977302f.jpg"}]
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
         * gid : 95392132
         * gname : 景田纯净水18.9L
         * pprice : 19.00
         * picturl : 20180921/83370704d0e11bbd014f1c6a084d84dd.jpg
         */

        private String gid;
        private String gname;
        private String pprice;
        private String picturl;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
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

        public String getPicturl() {
            return picturl;
        }

        public void setPicturl(String picturl) {
            this.picturl = picturl;
        }
    }
}
