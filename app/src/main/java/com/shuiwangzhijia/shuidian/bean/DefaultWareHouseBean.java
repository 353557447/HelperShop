package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;

/**
 * Created by xxc on 2019/3/13.
 */

public class DefaultWareHouseBean implements Serializable {

    /**
     * id : 8
     * cname : 仓库20
     * did : 11
     * create_time : 1551677227
     * update_time : 1551677227
     * delete_time : null
     */

    private int id;
    private String cname;
    private int did;
    private int create_time;
    private int update_time;
    private Object delete_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    public Object getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(Object delete_time) {
        this.delete_time = delete_time;
    }
}
