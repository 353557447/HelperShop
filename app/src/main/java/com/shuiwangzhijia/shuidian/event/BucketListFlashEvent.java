package com.shuiwangzhijia.shuidian.event;

/**
 * created by wangsuli on 2018/10/26.
 */
public class BucketListFlashEvent {
    public int did;

    public BucketListFlashEvent(int did, int type) {
        this.did = did;
        this.type = type;
    }

    public int type;
}
