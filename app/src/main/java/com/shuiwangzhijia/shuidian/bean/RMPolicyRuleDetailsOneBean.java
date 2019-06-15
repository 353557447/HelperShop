package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/15.
 */

public class RMPolicyRuleDetailsOneBean {

    /**
     * gid : 64497632
     * rule : [{"gid":"64497632","s_gid":222,"full":5,"amount":"0.10","snum":1,"gname":"铁山泉（不含运费）","s_name":"哈哈水票"},{"gid":"64497632","s_gid":222,"full":10,"amount":"0.30","snum":1,"gname":"铁山泉（不含运费）","s_name":"哈哈水票"}]
     */

    private String gid;
    private List<RuleBean> rule;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public List<RuleBean> getRule() {
        return rule;
    }

    public void setRule(List<RuleBean> rule) {
        this.rule = rule;
    }

    public static class RuleBean {
        /**
         * gid : 64497632
         * s_gid : 222
         * full : 5
         * amount : 0.10
         * snum : 1
         * gname : 铁山泉（不含运费）
         * s_name : 哈哈水票
         */

        private String gid;
        private int s_gid;
        private int full;
        private String amount;
        private int snum;
        private String gname;
        private String s_name;

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public int getS_gid() {
            return s_gid;
        }

        public void setS_gid(int s_gid) {
            this.s_gid = s_gid;
        }

        public int getFull() {
            return full;
        }

        public void setFull(int full) {
            this.full = full;
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

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public String getS_name() {
            return s_name;
        }

        public void setS_name(String s_name) {
            this.s_name = s_name;
        }
    }
}
