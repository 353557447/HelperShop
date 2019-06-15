package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * created by wangsuli on 2018/8/23.
 */
public class GoodsManageBean {
    /**
     * gtype : 7
     * gname : 瓶装水
     */

    private String gtype;
    private String gname;

    public String getGtype() {
        return gtype;
    }

    public void setGtype(String gtype) {
        this.gtype = gtype;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    private List<GoodsBean>  list;

    public List<GoodsBean> getList() {
        return list;
    }

    public void setList(List<GoodsBean> list) {
        this.list = list;
    }
}
