package com.shuiwangzhijia.shuidian.bean;

import org.json.JSONArray;

/**
 * Created by Lijn on 2019/4/29.
 */

public class ReturnMoneyRtypeElseBean {
    private int rule;
    private int rbasis;
    private int r_way;
    private String rebate_date;
    private int status;
    private String total_amount;
    private int s_count;
    private String rebate_no;
    private JSONArray data;

    public String getRebate_no() {
        return rebate_no;
    }

    public void setRebate_no(String rebate_no) {
        this.rebate_no = rebate_no;
    }

    public int getS_count() {
        return s_count;
    }

    public void setS_count(int s_count) {
        this.s_count = s_count;
    }

    public int getRule() {
        return rule;
    }

    public void setRule(int rule) {
        this.rule = rule;
    }

    public int getRbasis() {
        return rbasis;
    }

    public void setRbasis(int rbasis) {
        this.rbasis = rbasis;
    }

    public int getR_way() {
        return r_way;
    }

    public void setR_way(int r_way) {
        this.r_way = r_way;
    }

    public String getRebate_date() {
        return rebate_date;
    }

    public void setRebate_date(String rebate_date) {
        this.rebate_date = rebate_date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public JSONArray getData() {
        return data;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }
}
