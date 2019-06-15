package com.shuiwangzhijia.shuidian.bean;

/**
 * created by wangsuli on 2018/9/27.
 */
public class CityBean {
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;// 1表示字母

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    private String letter;
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
