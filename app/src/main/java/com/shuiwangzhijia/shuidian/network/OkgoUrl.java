package com.shuiwangzhijia.shuidian.network;



public interface OkgoUrl {
    //根地址
   // String BASE_URL="http://39.108.254.109/";
   // String BASE_URL="https://testapi.fndwater.com/";
    String BASE_URL="https://api.waterhelper.cn/";
    //http://api.waterhelper.cn/shop/v1.Versions/GetVersionInfo

    //登录
   // String LOGIN="shop/v1.User/tologin";
   // String LOGIN=BASE_URL+"api/wtapiShop.User/tologin";
    String LOGIN=BASE_URL+"shop/v1.User/tologin";

   // String GET_BANNER=BASE_URL+"api/wtapiShop.Index/newBanner";
    String GET_BANNER=BASE_URL+"shop/v1.Index/newBanner";

    //String GET_GOODS_TYPE=BASE_URL+"api/wtapiShop.Index/showGoods_header";
    String GET_GOODS_TYPE=BASE_URL+"shop/v1.Index/showGoods_header";

   // String GET_GOODS_LIST=BASE_URL+"api/wtapiShop.Index/showGoods_new";
    String GET_GOODS_LIST=BASE_URL+"shop/v1.Index/showGoods_new";
    //获取我的返利列表
    String GET_RETURNMONEY_LIST=BASE_URL+"shop/v1.Rebateoperation/rebateList";
}
