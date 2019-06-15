package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;

/**
 * Created by xxc on 2019/2/14.
 */

public class SaveOweBean implements Serializable{

    /**
     * gid : 78133110
     * gname : 三号商品1
     * save_owe : -9
     */

    private int gid;
    private String gname;
    private int save_owe;

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public int getSave_owe() {
        return save_owe;
    }

    public void setSave_owe(int save_owe) {
        this.save_owe = save_owe;
    }
}
