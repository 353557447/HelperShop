package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/17.
 */

public class EquipModifySelGoodsBean {

    /**
     * code : 200.0
     * msg : ok
     * data : [{"picturl":"20180921/83370704d0e11bbd014f1c6a084d84dd.jpg","gid":"95392132","gname":"景田纯净水18.9L"},{"picturl":"20180921/400203843c3c10bdc268463004bb09c9.jpg","gid":"38354731","gname":"农夫山泉18.9L"},{"picturl":"20180921/8379d78ad6feb88665b46a56705041b6.jpg","gid":"60580575","gname":"益力矿泉水18.9L"},{"picturl":"20180921/7653043954d4fe32cf728f78196efdef.jpg","gid":"36692117","gname":"麦宝纯净水16.8L"},{"picturl":"20180921/af7175a2f94e458f53de960339f46eab.jpg","gid":"42300565","gname":"麦宝纯净水18.9L"}]
     * scode : 0.0
     */

    private double code;
    private String msg;
    private double scode;
    private List<DataBean> data;

    public double getCode() {
        return code;
    }

    public void setCode(double code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public double getScode() {
        return scode;
    }

    public void setScode(double scode) {
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
         * picturl : 20180921/83370704d0e11bbd014f1c6a084d84dd.jpg
         * gid : 95392132
         * gname : 景田纯净水18.9L
         */

        private String picturl;
        private String gid;
        private String gname;
        private boolean isChecked;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public String getPicturl() {
            return picturl;
        }

        public void setPicturl(String picturl) {
            this.picturl = picturl;
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
