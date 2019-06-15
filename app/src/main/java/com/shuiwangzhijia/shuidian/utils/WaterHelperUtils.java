package com.shuiwangzhijia.shuidian.utils;


import com.shuiwangzhijia.shuidian.event.RedPointEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2019/5/13.
 */

public class WaterHelperUtils {
    public static void updateRedPointCounts() {
        if (CommonUtils.isLogin()) {
            if(CommonUtils.getShopCart()!=null) {
                int size = CommonUtils.getShopCart().size();
                EventBus.getDefault().post(new RedPointEvent(size));
            }
        } else {
            EventBus.getDefault().post(new RedPointEvent(0));
        }
    }
}
