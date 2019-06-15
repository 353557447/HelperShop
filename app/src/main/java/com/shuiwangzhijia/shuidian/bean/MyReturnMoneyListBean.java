package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

public class MyReturnMoneyListBean {


    /**
     * code : 200
     * msg : 查询成功
     * data : {"history":[{"r_id":154469,"r_way":1,"rname":"满5桶返0.02","rtype":1,"start_time":1557763200,"rbasis":2,"rule":1,"end_time":1558108800,"flag":2,"isfailure":1,"already_rebate":"0.00","not_deal_rebate":"0.00","rule_detail":{"order":1,"detail":[{"gid":"","s_gid":0,"full":5,"amount":"0.02","snum":0,"gname":null,"s_name":null}]},"time":[{"timeStamp":1558108800,"time_array":[1557763200,1558108800]}],"rebate":{"amount":0,"snum":0,"goods_num":0,"rebate_details":[],"order_num":0}},{"r_id":154465,"r_way":1,"rname":"财富买一桶返0.01","rtype":1,"start_time":1557763200,"rbasis":1,"rule":1,"end_time":1557763200,"flag":2,"isfailure":1,"already_rebate":"0.00","not_deal_rebate":"0.00","rule_detail":{"order":0,"detail":[[{"gid":"65201504","s_gid":0,"full":1,"amount":"0.01","snum":0,"gname":"财富（仅供测试）","s_name":null}]]},"time":[{"timeStamp":1557763200,"time_array":[1557763200,1557763200]}],"rebate":{"amount":0,"snum":0,"goods_num":0,"rebate_details":[],"order_num":0}},{"r_id":154472,"r_way":1,"rname":"6月返利","rtype":2,"start_time":1551369600,"rbasis":1,"rule":1,"end_time":1569859200,"flag":1,"isfailure":1,"already_rebate":"0.00","not_deal_rebate":"0.00","rule_detail":{"order":0,"detail":[[{"gid":"21250220","s_gid":0,"full":1,"amount":"1.00","snum":0,"gname":"幸福","s_name":null}],[{"gid":"37181373","s_gid":0,"full":1,"amount":"1.00","snum":0,"gname":"平安","s_name":null}],[{"gid":"41030405","s_gid":0,"full":1,"amount":"1.00","snum":0,"gname":"一号水桶（仅供测试）","s_name":null}],[{"gid":"65201504","s_gid":0,"full":1,"amount":"1.00","snum":0,"gname":"财富（仅供测试）","s_name":null}],[{"gid":"93902350","s_gid":0,"full":1,"amount":"1.00","snum":0,"gname":"健康","s_name":null}]]},"time":[{"is_rebate":0,"time":"2019年3月","timeStamp":1551369600},{"is_rebate":0,"time":"2019年4月","timeStamp":1554048000},{"is_rebate":0,"time":"2019年5月","timeStamp":1556640000},{"is_rebate":0,"time":"2019年6月","timeStamp":1559318400},{"is_rebate":0,"time":"2019年7月","timeStamp":1561910400},{"is_rebate":0,"time":"2019年8月","timeStamp":1564588800},{"is_rebate":0,"time":"2019年9月","timeStamp":1567267200}],"rebate":{"amount":1,"snum":0,"goods_num":1,"rebate_details":[{"gnum":"1","rule":"[{\"full\":1,\"amount\":\"1.00\",\"type\":1}]","amount":1,"snum":0,"gid":"37181373","s_gid":""}],"order_num":2}}],"conduct":[{"r_id":154474,"r_way":2,"rname":"测试返利（月度返利 水票返利  范围返利 按订单）","rtype":2,"start_time":1559318400,"rbasis":2,"rule":2,"end_time":1577808000,"flag":1,"isfailure":2,"already_rebate":"0","not_deal_rebate":"0","rule_detail":{"order":1,"detail":[{"gid":"","s_gid":56599428,"full":3,"amount":"0.00","snum":3,"gname":null,"s_name":"财富（仅供测试）水票"},{"gid":"","s_gid":56599639,"full":5,"amount":"0.00","snum":2,"gname":null,"s_name":"幸福水票"}]},"time":[],"rebate":{"amount":0,"snum":0,"goods_num":0,"rebate_details":[],"order_num":0}},{"r_id":154473,"r_way":1,"rname":"测试返利（单笔 现金返利 倍数返利  按商品）","rtype":1,"start_time":1559318400,"rbasis":1,"rule":1,"end_time":1561737600,"flag":1,"isfailure":2,"already_rebate":"0.00","not_deal_rebate":"0.00","rule_detail":{"order":0,"detail":[[{"gid":"65201504","s_gid":0,"full":2,"amount":"1.00","snum":0,"gname":"财富（仅供测试）","s_name":null}],[{"gid":"93902350","s_gid":0,"full":3,"amount":"2.00","snum":0,"gname":"健康","s_name":null}]]},"time":[{"timeStamp":1561737600,"time_array":[1559318400,1561737600]}],"rebate":{"amount":0,"snum":0,"goods_num":0,"rebate_details":[],"order_num":0}}]}
     * scode : 0
     */

    private int code;
    private String msg;
    private DataBean data;
    private int scode;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getScode() {
        return scode;
    }

    public void setScode(int scode) {
        this.scode = scode;
    }

    public static class DataBean {
        private List<HistoryBean> history;
        private List<ConductBean> conduct;

        public List<HistoryBean> getHistory() {
            return history;
        }

        public void setHistory(List<HistoryBean> history) {
            this.history = history;
        }

        public List<ConductBean> getConduct() {
            return conduct;
        }

        public void setConduct(List<ConductBean> conduct) {
            this.conduct = conduct;
        }

        public static class HistoryBean {
            /**
             * r_id : 154469
             * r_way : 1
             * rname : 满5桶返0.02
             * rtype : 1
             * start_time : 1557763200
             * rbasis : 2
             * rule : 1
             * end_time : 1558108800
             * flag : 2
             * isfailure : 1
             * already_rebate : 0.00
             * not_deal_rebate : 0.00
             * rule_detail : {"order":1,"detail":[{"gid":"","s_gid":0,"full":5,"amount":"0.02","snum":0,"gname":null,"s_name":null}]}
             * time : [{"timeStamp":1558108800,"time_array":[1557763200,1558108800]}]
             * rebate : {"amount":0,"snum":0,"goods_num":0,"rebate_details":[],"order_num":0}
             */

            private int r_id;
            private int r_way;
            private String rname;
            private int rtype;
            private long start_time;
            private int rbasis;
            private int rule;
            private long end_time;
            private int flag;
            private int isfailure;
            private String already_rebate;
            private String not_deal_rebate;
            private RuleDetailBean rule_detail;
            private RebateBean rebate;
            private List<TimeBean> time;

            public int getR_id() {
                return r_id;
            }

            public void setR_id(int r_id) {
                this.r_id = r_id;
            }

            public int getR_way() {
                return r_way;
            }

            public void setR_way(int r_way) {
                this.r_way = r_way;
            }

            public String getRname() {
                return rname;
            }

            public void setRname(String rname) {
                this.rname = rname;
            }

            public int getRtype() {
                return rtype;
            }

            public void setRtype(int rtype) {
                this.rtype = rtype;
            }

            public long getStart_time() {
                return start_time;
            }

            public void setStart_time(long start_time) {
                this.start_time = start_time;
            }

            public int getRbasis() {
                return rbasis;
            }

            public void setRbasis(int rbasis) {
                this.rbasis = rbasis;
            }

            public int getRule() {
                return rule;
            }

            public void setRule(int rule) {
                this.rule = rule;
            }

            public long getEnd_time() {
                return end_time;
            }

            public void setEnd_time(long end_time) {
                this.end_time = end_time;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public int getIsfailure() {
                return isfailure;
            }

            public void setIsfailure(int isfailure) {
                this.isfailure = isfailure;
            }

            public String getAlready_rebate() {
                return already_rebate;
            }

            public void setAlready_rebate(String already_rebate) {
                this.already_rebate = already_rebate;
            }

            public String getNot_deal_rebate() {
                return not_deal_rebate;
            }

            public void setNot_deal_rebate(String not_deal_rebate) {
                this.not_deal_rebate = not_deal_rebate;
            }

            public RuleDetailBean getRule_detail() {
                return rule_detail;
            }

            public void setRule_detail(RuleDetailBean rule_detail) {
                this.rule_detail = rule_detail;
            }

            public RebateBean getRebate() {
                return rebate;
            }

            public void setRebate(RebateBean rebate) {
                this.rebate = rebate;
            }

            public List<TimeBean> getTime() {
                return time;
            }

            public void setTime(List<TimeBean> time) {
                this.time = time;
            }

            public static class RuleDetailBean {
                /**
                 * order : 1
                 * detail : [{"gid":"","s_gid":0,"full":5,"amount":"0.02","snum":0,"gname":null,"s_name":null}]
                 */

                private int order;
                private List<DetailBean> detail;

                public int getOrder() {
                    return order;
                }

                public void setOrder(int order) {
                    this.order = order;
                }

                public List<DetailBean> getDetail() {
                    return detail;
                }

                public void setDetail(List<DetailBean> detail) {
                    this.detail = detail;
                }

                public static class DetailBean {
                    /**
                     * gid :
                     * s_gid : 0
                     * full : 5
                     * amount : 0.02
                     * snum : 0
                     * gname : null
                     * s_name : null
                     */

                    private String gid;
                    private int s_gid;
                    private int full;
                    private String amount;
                    private int snum;
                    private Object gname;
                    private Object s_name;

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

                    public Object getGname() {
                        return gname;
                    }

                    public void setGname(Object gname) {
                        this.gname = gname;
                    }

                    public Object getS_name() {
                        return s_name;
                    }

                    public void setS_name(Object s_name) {
                        this.s_name = s_name;
                    }
                }
            }

            public static class RebateBean {
                /**
                 * amount : 0
                 * snum : 0
                 * goods_num : 0
                 * rebate_details : []
                 * order_num : 0
                 */

                private int amount;
                private int snum;
                private int goods_num;
                private int order_num;
                private List<?> rebate_details;

                public int getAmount() {
                    return amount;
                }

                public void setAmount(int amount) {
                    this.amount = amount;
                }

                public int getSnum() {
                    return snum;
                }

                public void setSnum(int snum) {
                    this.snum = snum;
                }

                public int getGoods_num() {
                    return goods_num;
                }

                public void setGoods_num(int goods_num) {
                    this.goods_num = goods_num;
                }

                public int getOrder_num() {
                    return order_num;
                }

                public void setOrder_num(int order_num) {
                    this.order_num = order_num;
                }

                public List<?> getRebate_details() {
                    return rebate_details;
                }

                public void setRebate_details(List<?> rebate_details) {
                    this.rebate_details = rebate_details;
                }
            }

            public static class TimeBean {
                /**
                 * timeStamp : 1558108800
                 * time_array : [1557763200,1558108800]
                 */

                private int timeStamp;
                private List<Integer> time_array;

                public int getTimeStamp() {
                    return timeStamp;
                }

                public void setTimeStamp(int timeStamp) {
                    this.timeStamp = timeStamp;
                }

                public List<Integer> getTime_array() {
                    return time_array;
                }

                public void setTime_array(List<Integer> time_array) {
                    this.time_array = time_array;
                }
            }
        }

        public static class ConductBean {
            /**
             * r_id : 154474
             * r_way : 2
             * rname : 测试返利（月度返利 水票返利  范围返利 按订单）
             * rtype : 2
             * start_time : 1559318400
             * rbasis : 2
             * rule : 2
             * end_time : 1577808000
             * flag : 1
             * isfailure : 2
             * already_rebate : 0
             * not_deal_rebate : 0
             * rule_detail : {"order":1,"detail":[{"gid":"","s_gid":56599428,"full":3,"amount":"0.00","snum":3,"gname":null,"s_name":"财富（仅供测试）水票"},{"gid":"","s_gid":56599639,"full":5,"amount":"0.00","snum":2,"gname":null,"s_name":"幸福水票"}]}
             * time : []
             * rebate : {"amount":0,"snum":0,"goods_num":0,"rebate_details":[],"order_num":0}
             */

            private int r_id;
            private int r_way;
            private String rname;
            private int rtype;
            private long start_time;
            private int rbasis;
            private int rule;
            private long end_time;
            private int flag;
            private int isfailure;
            private String already_rebate;
            private String not_deal_rebate;
            private RuleDetailBeanX rule_detail;
            private RebateBeanX rebate;
            private List<?> time;

            public int getR_id() {
                return r_id;
            }

            public void setR_id(int r_id) {
                this.r_id = r_id;
            }

            public int getR_way() {
                return r_way;
            }

            public void setR_way(int r_way) {
                this.r_way = r_way;
            }

            public String getRname() {
                return rname;
            }

            public void setRname(String rname) {
                this.rname = rname;
            }

            public int getRtype() {
                return rtype;
            }

            public void setRtype(int rtype) {
                this.rtype = rtype;
            }

            public long getStart_time() {
                return start_time;
            }

            public void setStart_time(long start_time) {
                this.start_time = start_time;
            }

            public int getRbasis() {
                return rbasis;
            }

            public void setRbasis(int rbasis) {
                this.rbasis = rbasis;
            }

            public int getRule() {
                return rule;
            }

            public void setRule(int rule) {
                this.rule = rule;
            }

            public long getEnd_time() {
                return end_time;
            }

            public void setEnd_time(long end_time) {
                this.end_time = end_time;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public int getIsfailure() {
                return isfailure;
            }

            public void setIsfailure(int isfailure) {
                this.isfailure = isfailure;
            }

            public String getAlready_rebate() {
                return already_rebate;
            }

            public void setAlready_rebate(String already_rebate) {
                this.already_rebate = already_rebate;
            }

            public String getNot_deal_rebate() {
                return not_deal_rebate;
            }

            public void setNot_deal_rebate(String not_deal_rebate) {
                this.not_deal_rebate = not_deal_rebate;
            }

            public RuleDetailBeanX getRule_detail() {
                return rule_detail;
            }

            public void setRule_detail(RuleDetailBeanX rule_detail) {
                this.rule_detail = rule_detail;
            }

            public RebateBeanX getRebate() {
                return rebate;
            }

            public void setRebate(RebateBeanX rebate) {
                this.rebate = rebate;
            }

            public List<?> getTime() {
                return time;
            }

            public void setTime(List<?> time) {
                this.time = time;
            }

            public static class RuleDetailBeanX {
                /**
                 * order : 1
                 * detail : [{"gid":"","s_gid":56599428,"full":3,"amount":"0.00","snum":3,"gname":null,"s_name":"财富（仅供测试）水票"},{"gid":"","s_gid":56599639,"full":5,"amount":"0.00","snum":2,"gname":null,"s_name":"幸福水票"}]
                 */

                private int order;
                private List<DetailBeanX> detail;

                public int getOrder() {
                    return order;
                }

                public void setOrder(int order) {
                    this.order = order;
                }

                public List<DetailBeanX> getDetail() {
                    return detail;
                }

                public void setDetail(List<DetailBeanX> detail) {
                    this.detail = detail;
                }

                public static class DetailBeanX {
                    /**
                     * gid :
                     * s_gid : 56599428
                     * full : 3
                     * amount : 0.00
                     * snum : 3
                     * gname : null
                     * s_name : 财富（仅供测试）水票
                     */

                    private String gid;
                    private int s_gid;
                    private int full;
                    private String amount;
                    private int snum;
                    private Object gname;
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

                    public Object getGname() {
                        return gname;
                    }

                    public void setGname(Object gname) {
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

            public static class RebateBeanX {
                /**
                 * amount : 0
                 * snum : 0
                 * goods_num : 0
                 * rebate_details : []
                 * order_num : 0
                 */

                private int amount;
                private int snum;
                private int goods_num;
                private int order_num;
                private List<?> rebate_details;

                public int getAmount() {
                    return amount;
                }

                public void setAmount(int amount) {
                    this.amount = amount;
                }

                public int getSnum() {
                    return snum;
                }

                public void setSnum(int snum) {
                    this.snum = snum;
                }

                public int getGoods_num() {
                    return goods_num;
                }

                public void setGoods_num(int goods_num) {
                    this.goods_num = goods_num;
                }

                public int getOrder_num() {
                    return order_num;
                }

                public void setOrder_num(int order_num) {
                    this.order_num = order_num;
                }

                public List<?> getRebate_details() {
                    return rebate_details;
                }

                public void setRebate_details(List<?> rebate_details) {
                    this.rebate_details = rebate_details;
                }
            }
        }
    }
}
