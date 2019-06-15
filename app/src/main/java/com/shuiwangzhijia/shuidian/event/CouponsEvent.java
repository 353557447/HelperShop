package com.shuiwangzhijia.shuidian.event;

import com.shuiwangzhijia.shuidian.bean.ShowCouponBean;

/**
 * Created by xxc on 2019/4/8.
 */

public class CouponsEvent {
    private ShowCouponBean.ListBean bean;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;

    public CouponsEvent(ShowCouponBean.ListBean bean,int type) {
        this.bean = bean;
        this.type = type;
    }

    public ShowCouponBean.ListBean getMsg() {
        return bean;
    }

    public void setMsg(ShowCouponBean.ListBean msg) {
        this.bean = msg;
    }
}
