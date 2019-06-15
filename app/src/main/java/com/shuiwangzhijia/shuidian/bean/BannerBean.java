package com.shuiwangzhijia.shuidian.bean;

/**
 * created by wangsuli on 2018/9/4.
 */
public class BannerBean {
    /**
     * id : 7
     * picturl : 20180611/005fabed2f5cd66eac992023222c3b84.jpg
     * type : 1
     * url_type : null
     * url_addr : null
     */

    private int id;
    private String picturl;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    private String detail;
    private int type;
    private int url_type;
    private String url_addr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicturl() {
        return picturl;
    }

    public void setPicturl(String picturl) {
        this.picturl = picturl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUrl_type() {
        return url_type;
    }

    public void setUrl_type(int url_type) {
        this.url_type = url_type;
    }

    public String getUrl_addr() {
        return url_addr;
    }

    public void setUrl_addr(String url_addr) {
        this.url_addr = url_addr;
    }
}
