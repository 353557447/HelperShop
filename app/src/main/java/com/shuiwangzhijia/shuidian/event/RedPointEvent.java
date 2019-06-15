package com.shuiwangzhijia.shuidian.event;

/**
 * Created by Administrator on 2019/5/13.
 */

public class RedPointEvent {
    private int redPointCounts;

    public RedPointEvent(int redPointCounts) {
        this.redPointCounts = redPointCounts;
    }

    public int getRedPointCounts() {
        return redPointCounts;
    }

    public void setRedPointCounts(int redPointCounts) {
        this.redPointCounts = redPointCounts;
    }
}
