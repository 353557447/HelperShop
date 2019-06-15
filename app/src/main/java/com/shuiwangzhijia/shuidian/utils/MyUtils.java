package com.shuiwangzhijia.shuidian.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.App;
import com.shuiwangzhijia.shuidian.base.Constant;

import java.text.DecimalFormat;


/**
 * Created by Lijn on 2018/10/30.
 */

public class MyUtils {
    private static DecimalFormat mDecimalFormat;

    public static void setImg(ImageView iv, String imgPath, String elsePath) {
        if (imgPath != null && imgPath.length() != 0) {
            Glide.with(App.getContext()).load(Constant.url + elsePath + imgPath).placeholder(R.drawable.morentouxiang).into(iv);
        } else {
            //占位图
            iv.setImageResource(R.drawable.morentouxiang);
        }
        iv = null;
    }

    public static void setLocalImg(ImageView iv, String imgPath) {
        if (imgPath != null && imgPath.length() != 0) {
            Glide.with(App.getContext()).load(imgPath).placeholder(R.drawable.morentouxiang).into(iv);
        } else {
            //占位图
            iv.setImageResource(R.drawable.login_logo);
        }
        iv = null;
    }

    public static boolean isEmpty(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        } else {
            return false;
        }
    }
    public static String formatPrice(double price){
        if(mDecimalFormat==null)
        mDecimalFormat = new DecimalFormat("######0.00");
        return mDecimalFormat.format(price);
    }
    public static void showKeyboard(View view){
        InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm!=null){
            view.requestFocus();
            imm.showSoftInput(view,0);
        }
    }



    /**
     * 坐标转换，腾讯地图转换成百度地图坐标
     * @param lat 腾讯纬度
     * @param lon 腾讯经度
     * @return 返回结果：经度,纬度
     */
    public static String map_tx2bd(double lat, double lon){
        double bd_lat;
        double bd_lon;
        double x_pi=3.14159265358979324;
        double x = lon, y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        bd_lon = z * Math.cos(theta) + 0.0065;
        bd_lat = z * Math.sin(theta) + 0.006;

        System.out.println("bd_lat:"+bd_lat);
        System.out.println("bd_lon:"+bd_lon);
        return bd_lon+","+bd_lat;
    }


    /**
     * 坐标转换，百度地图坐标转换成腾讯地图坐标
     * @param lat  百度坐标纬度
     * @param lon  百度坐标经度
     * @return 返回结果：纬度,经度
     */
    public static String map_bd2tx(double lat, double lon) {
        double tx_lat;
        double tx_lon;
        double x_pi = 3.14159265358979324;
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        tx_lon = z * Math.cos(theta);
        tx_lat = z * Math.sin(theta);
        return tx_lat + "," + tx_lon;
    }
}
