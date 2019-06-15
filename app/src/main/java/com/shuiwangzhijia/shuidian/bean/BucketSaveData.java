package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * created by wangsuli on 2018/10/23.
 */
public class BucketSaveData {

    /**
     * sname : 0108
     * id : 700
     * save_sum : 7
     * owe_sum : 0
     * all_sum : 7
     * goods : [{"gid":44547640,"gname":"2号商品","update_time":1547457601,"num":3,"status":0},{"gid":31337637,"gname":"3号商品","update_time":1547450129,"num":3,"status":0},{"gid":31337637,"gname":"3号商品","update_time":1547445221,"num":1,"status":0}]
     */

    private String sname;
    private int id;
    private int save_sum;
    private int owe_sum;
    private int all_sum;
    private List<BucketRecordBean> goods;

    public List<BucketRecordBean> getList() {
        return list;
    }

    public void setList(List<BucketRecordBean> list) {
        this.list = list;
    }

    private List<BucketRecordBean> list;

    /**
     * list : [{"id":2,"sid":16,"did":20,"bid":11111111,"save":30,"owe":20,"buy":65,"bet":35,"refund":20,"exchange":6,"back":4,"update_time":null,"delete_time":null}]
     * info : {"sname":"什么水厂","save":"30","owe":"20","total":"50"}
     */

    private InfoBean info;


    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSave_sum() {
        return save_sum;
    }

    public void setSave_sum(int save_sum) {
        this.save_sum = save_sum;
    }

    public int getOwe_sum() {
        return owe_sum;
    }

    public void setOwe_sum(int owe_sum) {
        this.owe_sum = owe_sum;
    }

    public int getAll_sum() {
        return all_sum;
    }

    public void setAll_sum(int all_sum) {
        this.all_sum = all_sum;
    }

    public List<BucketRecordBean> getGoods() {
        return goods;
    }

    public void setGoods(List<BucketRecordBean> goods) {
        this.goods = goods;
    }


    public static class InfoBean {
        /**
         * sname : 什么水厂
         * save : 30
         * owe : 20
         * total : 50
         */

        private String sname;
        private String save;
        private String owe;
        private String total;

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public String getSave() {
            return save;
        }

        public void setSave(String save) {
            this.save = save;
        }

        public String getOwe() {
            return owe;
        }

        public void setOwe(String owe) {
            this.owe = owe;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }
    }

    public static class GoodsBean {
        /**
         * gid : 44547640
         * gname : 2号商品
         * update_time : 1547457601
         * num : 3
         * status : 0
         */

        private int gid;
        private String gname;
        private int update_time;
        private int num;
        private int status;

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
