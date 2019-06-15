package com.shuiwangzhijia.shuidian.base;

import android.os.Environment;

import java.io.File;

/**
 * 常量类
 * Created by wangsuli on 2017/5/23.
 */

public class Constant {
//          public static final String url = "http://192.168.1.18:8010/";
//    public static final String url = "https://apiwaters.zhidian668.com/";
//    public static final String url = "https://testapi.fndwater.com/";
   // public static String url = "https://api.fndwater.com/";
    public static String url = "https://api.waterhelper.cn/";
//    public static   String GOODS_IMAGE_URL;
//    public static   String SHOP_IMAGE_URL;
//    public static   String COMMENT_IMAGE_URL;
//    public static   String BANNER_IMAGE_URL;
//    public static   String CACHE_IMAGE_URL;
    public static void setUrl(String s) {
        url = s;
        GOODS_IMAGE_URL =  s+ "goods/";
        BANNER_IMAGE_URL = s + "/banner/";
        SHOP_IMAGE_URL = s+"wtshop/";
        COMMENT_IMAGE_URL = s+ "/comments/";
        CACHE_IMAGE_URL = s+"/cache/";
    }

    public static String getUrl(){
        return url;
    }

    public static  String GOODS_IMAGE_URL = Constant.getUrl() + "goods/";
    public static  String SHOP_IMAGE_URL = Constant.getUrl() + "wtshop/";
    public static  String COMMENT_IMAGE_URL = Constant.getUrl() + "/comments/";
    public static  String BANNER_IMAGE_URL = Constant.getUrl() + "/banner/";
    public static  String CACHE_IMAGE_URL = Constant.getUrl() + "/cache/";


    //图片裁剪
    public static final int IMAGE_CROP = 1001;
    //默认图片压缩质量
    public static final int IMAGE_QUALITY = 40;
    public static final String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String BASE = absolutePath + File.separator + "waterExpress/";
    // 更新版本缓存
    public static final String UPDATE_FILE_DIR = BASE + "updateFile/";
    // 图片缓存
    public static final String CACHE_DIR_IMAGE = BASE + "image/";

    static {
        File f1 = new File(UPDATE_FILE_DIR);
        if (!f1.exists()) {
            f1.mkdirs();
        }
        File f2 = new File(CACHE_DIR_IMAGE);
        if (!f2.exists()) {
            f2.mkdirs();
        }
    }
    //登录返回
    public static final String WATER_FACTORY_ID = "jcy666888";
    public static final String WATER_FACTORY_NAME = "九重岩水厂";
    public static final String WXAPPID = "wxf8880011c1f9ba3d";
}
