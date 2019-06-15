package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;

/**
 * Created by xxc on 2019/4/9.
 */

public class CommitOrderBean implements Serializable{

    /**
     * delivery_type : 0
     * nickname : 测试六号
     */

    private int delivery_type;
    private String nickname;

    public int getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(int delivery_type) {
        this.delivery_type = delivery_type;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
