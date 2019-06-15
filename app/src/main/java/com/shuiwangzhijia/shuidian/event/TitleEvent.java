package com.shuiwangzhijia.shuidian.event;

/**
 * Created by Administrator on 2019/4/9.
 */

public class TitleEvent {
    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    private String adress;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;
    public TitleEvent(String msg,String adress,int type) {
        this.adress = adress;
        this.type = type;
        this.msg = msg;
    }
}
