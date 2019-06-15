package com.shuiwangzhijia.shuidian.event;

/**
 * Created by xxc on 2019/5/7.
 */

public class ShoppingcartEvent {
    private final int did;
    public int getDid() {
        return did;
    }

    public ShoppingcartEvent(int did) {
        this.did = did;
    }
}
