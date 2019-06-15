package com.shuiwangzhijia.shuidian.bean;

/**
 * Created by xxc on 2019/4/3.
 */

public class ActivityBean {
    /**
     * id : 7
     * tittle : aaaa
     * picturl : 20190401/bd3eff1315812ed5acdbd9113cb95af2.png
     * defaults : 1
     * type : 1
     * platform : 1
     * url_type : 0
     * url_addr : null
     * is_waterworks : 2
     * waterworks_id : 11,2,3
     * background_color : null
     * detail :
     * update_time : 1554104208
     * delete_time : null
     */

    private int id;
    private String tittle;
    private String picturl;
    private int defaults;
    private int type;
    private int platform;
    private int url_type;
    private String url_addr;
    private int is_waterworks;
    private String waterworks_id;
    private Object background_color;
    private String detail;
    private int update_time;
    private Object delete_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getPicturl() {
        return picturl;
    }

    public void setPicturl(String picturl) {
        this.picturl = picturl;
    }

    public int getDefaults() {
        return defaults;
    }

    public void setDefaults(int defaults) {
        this.defaults = defaults;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
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

    public int getIs_waterworks() {
        return is_waterworks;
    }

    public void setIs_waterworks(int is_waterworks) {
        this.is_waterworks = is_waterworks;
    }

    public String getWaterworks_id() {
        return waterworks_id;
    }

    public void setWaterworks_id(String waterworks_id) {
        this.waterworks_id = waterworks_id;
    }

    public Object getBackground_color() {
        return background_color;
    }

    public void setBackground_color(Object background_color) {
        this.background_color = background_color;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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