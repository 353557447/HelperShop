package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xxc on 2019/4/4.
 */

public class GoodsBeanNew implements Serializable{
    private int Did;
    private String waterFactoryName;
    private boolean isChecked;
    private List<GoodsBean> goodsList;


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getWaterFactoryName() {
        return waterFactoryName;
    }

    public void setWaterFactoryName(String waterFactoryName) {
        this.waterFactoryName = waterFactoryName;
    }
    public int getDid() {
        return Did;
    }

    public void setDid(int did) {
        Did = did;
    }

    public List<GoodsBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsBean> goodsList) {
        this.goodsList = goodsList;
    }
}
