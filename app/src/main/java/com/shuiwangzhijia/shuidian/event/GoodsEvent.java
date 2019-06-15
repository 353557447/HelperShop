package com.shuiwangzhijia.shuidian.event;

/**
 * created by wangsuli on 2018/8/22.
 */
public class GoodsEvent {
    public int count;
    public String url;

    public GoodsEvent(int count,String url) {
        this.count = count;
        this.url = url;
    }
}
