package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/17.
 */

public class SmartEquipCountersBean {


    /**
     * code : 200.0
     * msg : success
     * data : [{"id":485,"cabinet_id":17,"goods_id":"95392132","lattice_number":1,"add_time":1.555467816E9,"device_id":5,"picturl":"20180921/83370704d0e11bbd014f1c6a084d84dd.jpg"},{"id":486,"cabinet_id":17,"goods_id":"38354731","lattice_number":2,"add_time":1.555467816E9,"device_id":5,"picturl":"20180921/400203843c3c10bdc268463004bb09c9.jpg"},{"id":487,"cabinet_id":17,"goods_id":"83032112","lattice_number":3,"add_time":1.555467816E9,"device_id":5,"picturl":"20180921/a260d215b1f1dfa585823266a7281e3f.jpg"},{"id":488,"cabinet_id":17,"goods_id":"60580575","lattice_number":4,"add_time":1.555467816E9,"device_id":5,"picturl":"20180921/8379d78ad6feb88665b46a56705041b6.jpg"},{"id":489,"cabinet_id":17,"lattice_number":5,"add_time":1.555467816E9,"device_id":5,"update_time":1.555477344E9},{"id":490,"cabinet_id":17,"lattice_number":6,"add_time":1.555467816E9,"device_id":5}]
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
         * id : 485.0
         * cabinet_id : 17.0
         * goods_id : 95392132
         * lattice_number : 1.0
         * add_time : 1.555467816E9
         * device_id : 5.0
         * picturl : 20180921/83370704d0e11bbd014f1c6a084d84dd.jpg
         * update_time : 1.555477344E9
         */

        private int id;
        private int cabinet_id;
        private String goods_id;
        private int lattice_number;
        private long add_time;
        private int device_id;
        private String picturl;
        private long update_time;
        private boolean isChecked;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCabinet_id() {
            return cabinet_id;
        }

        public void setCabinet_id(int cabinet_id) {
            this.cabinet_id = cabinet_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public int getLattice_number() {
            return lattice_number;
        }

        public void setLattice_number(int lattice_number) {
            this.lattice_number = lattice_number;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public int getDevice_id() {
            return device_id;
        }

        public void setDevice_id(int device_id) {
            this.device_id = device_id;
        }

        public String getPicturl() {
            return picturl;
        }

        public void setPicturl(String picturl) {
            this.picturl = picturl;
        }

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }
    }
}
