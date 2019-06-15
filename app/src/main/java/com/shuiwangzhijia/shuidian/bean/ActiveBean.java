package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xxc on 2019/4/3.
 */

public class ActiveBean implements Serializable{


    /**
     * full : 123
     * links : [{"sid":21250220,"num":"1","name":"幸福水票"},{"sid":60470503,"num":"10","name":"高兴水票"},{"sid":21250220,"num":"20","name":"幸福水票"},{"sid":60470503,"num":"50","name":"高兴水票"}]
     */

    private String full = "0.0";
    private String reduce = "0.0";

    public String getReduce() {
        return reduce;
    }

    public void setReduce(String reduce) {
        this.reduce = reduce;
    }
    private List<LinksBean> links;

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public List<LinksBean> getLinks() {
        return links;
    }

    public void setLinks(List<LinksBean> links) {
        this.links = links;
    }

    public static class LinksBean {
        /**
         * sid : 21250220
         * num : 1
         * name : 幸福水票
         */

        private int sid;
        private String num;
        private String name;

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
