package com.shuiwangzhijia.shuidian.event;

/**
 * created by wangsuli on 2018/8/27.
 */
public class OrderListFlashEvent {
    public boolean isFlash;//true 代表刷新 采购列表 false 刷新售卖列表
    public int type;

    public OrderListFlashEvent(boolean isBuy, int type) {
        isFlash = isBuy;
        this.type = type;
    }
}
