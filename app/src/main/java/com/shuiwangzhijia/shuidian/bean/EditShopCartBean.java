package com.shuiwangzhijia.shuidian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xxc on 2019/2/14.
 */

public class EditShopCartBean implements Serializable {

    private List<GoodsBean> editCartGoods;
    private List<?> delCartGoods;

    public List<GoodsBean> getEditCartGoods() {
        return editCartGoods;
    }

    public void setEditCartGoods(List<GoodsBean> editCartGoods) {
        this.editCartGoods = editCartGoods;
    }

    public List<?> getDelCartGoods() {
        return delCartGoods;
    }

    public void setDelCartGoods(List<?> delCartGoods) {
        this.delCartGoods = delCartGoods;
    }
}
