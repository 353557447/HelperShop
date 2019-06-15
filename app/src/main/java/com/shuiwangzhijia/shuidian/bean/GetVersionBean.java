package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;

/**
 * Created by xxc on 2019/5/9.
 */

public class GetVersionBean implements Serializable{

    /**
     * online_stauts : 1
     */

    private int online_stauts;

    public int getOnline_stauts() {
        return online_stauts;
    }

    public void setOnline_stauts(int online_stauts) {
        this.online_stauts = online_stauts;
    }
}
