package com.shuiwangzhijia.shuidian.bean;

/**
 * created by wangsuli on 2018/10/8.
 */
public class CouponBean {
    /**
     * id : 7
     * cid : 818553
     * price : 30.00
     * full : 90
     * start : 1539014400
     * end : 1539273600
     * is_give : 0
     * type : 0
     * is_brand : 0
     */

    private int id;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private int position;

    public int getIs_up() {
        return is_up;
    }

    public void setIs_up(int is_up) {
        this.is_up = is_up;
    }

    private int is_up;

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    private int channel;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    private boolean isShow;//是否可以弹窗
    private String cid;
    private String price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private String full;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    private long start;
    private long end;
    private int is_give;
    private int type;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    private int state;//可使用 已使用 已过期
    private int is_brand;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }


    public int getIs_give() {
        return is_give;
    }

    public void setIs_give(int is_give) {
        this.is_give = is_give;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIs_brand() {
        return is_brand;
    }

    public void setIs_brand(int is_brand) {
        this.is_brand = is_brand;
    }
}
