package com.shuiwangzhijia.shuidian.event;

public class WechatPayFromWhichEvent {
    private String fromWhich;

    public WechatPayFromWhichEvent(String fromWhich) {
        this.fromWhich = fromWhich;
    }

    public String getFromWhich() {
        return fromWhich;
    }

    public void setFromWhich(String fromWhich) {
        this.fromWhich = fromWhich;
    }
}
