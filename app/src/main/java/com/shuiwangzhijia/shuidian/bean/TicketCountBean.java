package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;

/**
 * created by wangsuli on 2018/9/27.
 */
public class TicketCountBean implements Serializable {

    /**
     * use : 0
     * nuse : 0
     * overdue : 0
     */

    private int use;
    private int nuse;
    private int overdue;

    public int getUse() {
        return use;
    }

    public void setUse(int use) {
        this.use = use;
    }

    public int getNuse() {
        return nuse;
    }

    public void setNuse(int nuse) {
        this.nuse = nuse;
    }

    public int getOverdue() {
        return overdue;
    }

    public void setOverdue(int overdue) {
        this.overdue = overdue;
    }
}
