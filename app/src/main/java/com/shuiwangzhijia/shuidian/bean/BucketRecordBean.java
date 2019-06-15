package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;

/**
 * created by wangsuli on 2018/10/23.
 */
public class BucketRecordBean implements Serializable {

    /**
     * id : 44
     * sid : 16
     * did : 20
     * order_no : 2018102375863838
     * type : 2
     * update_time : 1540266914
     * tnum : 1
     */

    private int id;
    private int sid;
    private int did;
    /**
     * gid : 44547640
     * gname : 2号商品
     * update_time : 1547457601
     */

    private int gid;
    private String gname;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    private int num;

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    private int bid;

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    private String sname;
    private String order_no;

    public int getSave_owe() {
        return save_owe;
    }

    public void setSave_owe(int save_owe) {
        this.save_owe = save_owe;
    }

    /**
     * update_time : null
     * owe : 20
     */

    private int save_owe;


    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    private String bname;
    private int type;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;
    private long update_time;
    private int tnum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public int getTnum() {
        return tnum;
    }

    public void setTnum(int tnum) {
        this.tnum = tnum;
    }


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
}
