package com.shuiwangzhijia.shuidian.http;


import com.shuiwangzhijia.shuidian.bean.ActivityPlantsBean;
import com.shuiwangzhijia.shuidian.bean.AddressBean;
import com.shuiwangzhijia.shuidian.bean.AlbumItem;
import com.shuiwangzhijia.shuidian.bean.AlipayNewBean;
import com.shuiwangzhijia.shuidian.bean.BannerBean;
import com.shuiwangzhijia.shuidian.bean.BannerBeanNew;
import com.shuiwangzhijia.shuidian.bean.BucketBean;
import com.shuiwangzhijia.shuidian.bean.BucketOrderBean;
import com.shuiwangzhijia.shuidian.bean.BucketPayBean;
import com.shuiwangzhijia.shuidian.bean.BucketRecordBean;
import com.shuiwangzhijia.shuidian.bean.BucketRecordData;
import com.shuiwangzhijia.shuidian.bean.BucketSaveData;
import com.shuiwangzhijia.shuidian.bean.BuyOrderListBean;
import com.shuiwangzhijia.shuidian.bean.CashBean;
import com.shuiwangzhijia.shuidian.bean.CommentBean;
import com.shuiwangzhijia.shuidian.bean.CommitOrderBean;
import com.shuiwangzhijia.shuidian.bean.ConsumerDetailBean;
import com.shuiwangzhijia.shuidian.bean.CountBean;
import com.shuiwangzhijia.shuidian.bean.CouponBean;
import com.shuiwangzhijia.shuidian.bean.CustomerBean;
import com.shuiwangzhijia.shuidian.bean.CustomerDetailBean;
import com.shuiwangzhijia.shuidian.bean.DefaultWareHouseBean;
import com.shuiwangzhijia.shuidian.bean.GetVersionBean;
import com.shuiwangzhijia.shuidian.bean.GoodsBean;
import com.shuiwangzhijia.shuidian.bean.GoodsManageBean;
import com.shuiwangzhijia.shuidian.bean.GoodsTypeBean;
import com.shuiwangzhijia.shuidian.bean.LoginBean;
import com.shuiwangzhijia.shuidian.bean.MycouBean;
import com.shuiwangzhijia.shuidian.bean.OperationSureBean;
import com.shuiwangzhijia.shuidian.bean.OrderBean;
import com.shuiwangzhijia.shuidian.bean.OrderShowBean;
import com.shuiwangzhijia.shuidian.bean.PurchaseDetailsBean;
import com.shuiwangzhijia.shuidian.bean.SailOrderListBean;
import com.shuiwangzhijia.shuidian.bean.PayBean;
import com.shuiwangzhijia.shuidian.bean.SaveOweBean;
import com.shuiwangzhijia.shuidian.bean.ShareShopBean;
import com.shuiwangzhijia.shuidian.bean.ShopBean;
import com.shuiwangzhijia.shuidian.bean.ShopInfoData;
import com.shuiwangzhijia.shuidian.bean.ShowCouponBean;
import com.shuiwangzhijia.shuidian.bean.ShowPlantsBean;
import com.shuiwangzhijia.shuidian.bean.StatisticsBean;
import com.shuiwangzhijia.shuidian.bean.StoreListBean;
import com.shuiwangzhijia.shuidian.bean.TencentLocationBean;
import com.shuiwangzhijia.shuidian.bean.TicketBean;
import com.shuiwangzhijia.shuidian.bean.TicketCountBean;
import com.shuiwangzhijia.shuidian.bean.TicketOrderBean;
import com.shuiwangzhijia.shuidian.bean.UpdateInfo;
import com.shuiwangzhijia.shuidian.bean.UseTicketBean;
import com.shuiwangzhijia.shuidian.bean.ShopMarketData;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


public interface AppService {

    /**
     * 登录
     *
     * @param phone
     * @param pswd
     * @return
     */
    @POST("shop/v1.User/tologin")
    Call<EntityObject<LoginBean>> getLogin(@Query("phone") String phone, @Query("pswd") String pswd, @Query("serial") String serial);

    /**
     * 登出原因
     *
     * @param phone
     * @return
     */
    @POST("shop/v1.User/outlogin")
    Call<EntityObject<String>> loginOut(@Query("phone") String phone);

    /**
     * 版本信息
     *
     * @param type        0 Android  1 ios
     * @param client_type 0用户端 1 水店 2 司机端
     * @return
     */
    @GET("shop/v1.Versions/GetVersionInfo")
    Call<EntityObject<UpdateInfo>> getAppVersionInfo(@Query("type") int type, @Query("client_type") int client_type);

    @Streaming //大文件时要加不然会OOM
    @GET
    Call<ResponseBody> downloadFile(@Url String url);

    /**
     * 管理-店铺营业状态
     *
     * @return
     */
    @GET("shop/v1.Shop/business")
    Call<EntityObject<Object>> getShopState();

    /**
     * 店铺推广 店铺信息
     *
     * @return
     */
    @GET("shop/v1.Spread/showShop")
    Call<EntityObject<ShopBean>> getShopInfo();

    /**
     * 店铺管理信息
     *
     * @return
     */
    @GET("shop/v1.Shop/showShopInfo")
    Call<EntityObject<ShopBean>> showShopInfo();

    /**
     * 商品类型
     *
     * @return
     */
    @GET("shop/v1.Index/showGoods_header")
    Call<EntityObject<ArrayList<GoodsTypeBean>>> getGoodsType();

    /**
     * 获取商品列表
     *
     * @return
     */
    @GET("shop/v1.Index/showGoods_new")
    Call<EntityObject<ArrayList<GoodsBean>>> getGoodsList(@Query("gtype") String gtype, @Query("start") int page, @Query("limit") int pageSize);

    /**
     * 获取我的水票列表
     *
     * @return
     */
    @GET("shop/v1.Waterticket/showMyTickets_v1")
    Call<EntityObject<ArrayList<TicketBean>>> getTicketList(@Query("status") int status, @Query("start") int page, @Query("limit") int pageSize);

    /**
     * 可用水票列表
     *
     * @return
     */
    @GET("shop/v1.Waterticket/showTicketsUse_v1")
    Call<EntityObject<UseTicketBean>> showTicketsUse(@Query("data") String data);

    /**
     * 优惠水票列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GET("shop/v1.Waterticket/showTickets_v1")
    Call<EntityObject<ArrayList<TicketBean>>> getDiscountTicketList(@Query("start") int page, @Query("limit") int pageSize);

    /**
     * 水票生成订单
     *
     * @param sprice
     * @param data
     * @return
     */
    @GET("shop/v1.Waterticket/ticketOrder_v1")
    Call<EntityObject<TicketOrderBean>> createTicketOrder(@Query("token") String token, @Query("sprice") String sprice, @Query("data") String data);

    /**
     * 显示我的水票数量
     *
     * @return
     */
    @GET("shop/v1.Waterticket/showMyTicketsCount_v1")
    Call<EntityObject<TicketCountBean>> showMyTicketsCount();

    /**
     * 地址
     *
     * @return
     */
    @GET("shop/v1.Address/ListAddress")
    Call<EntityObject<ArrayList<AddressBean>>> getAddressList();

    /**
     * 设置默认地址
     *
     * @return
     */
    @GET("shop/v1.Address/DefaultAddress")
    Call<EntityObject<String>> setDefaultAddress(@Query("id") int id);

    /**
     * 获取默认地址
     *
     * @return
     */
    @GET("shop/v1.Address/defaultAddr")
    Call<EntityObject<ArrayList<AddressBean>>> getDefaultAddress();

    /**
     * 删除地址
     *
     * @return
     */
    @GET("shop/v1.Address/DeleteAddress")
    Call<EntityObject<String>> deleteAddress(@Query("id") int id);

    /**
     * 新增和修改地址
     *
     * @param aname
     * @param sphone
     * @param province
     * @param city
     * @param dist
     * @param daddress
     * @param ulnglat
     * @param type     1发货，2自提
     * @param id       用于修改地址
     * @return
     */
    @GET("shop/v1.Address/AddAddress")
    Call<EntityObject<Object>> addAddress(@Query("aname") String aname, @Query("sphone") String sphone,
                                          @Query("province") String province, @Query("city") String city,
                                          @Query("dist") String dist, @Query("daddress") String daddress,
                                          @Query("ulnglat") String ulnglat, @Query("type") int type, @Query("id") String id);

    /**
     * 商品管理列表
     *
     * @param status 1上架，0下架
     * @return
     */
    @GET("shop/v1.Goods/goodsManagementList")
    Call<EntityObject<ArrayList<GoodsManageBean>>> getGoodsManageList(@Query("status") int status);

    /**
     * 商品管理 上架下架处理
     *
     * @return
     */
    @GET("shop/v1.Goods/upDownGoods")
    Call<EntityObject<String>> upDownGoods(@Query("sid") int sid, @Query("gid") String gid);

    /**
     * 评论列表
     *
     * @return
     */
    @GET("shop/v1.Spread/showComments")
    Call<EntityObject<ArrayList<CommentBean>>> getCommentList(@Query("start") int page, @Query("limit") int pageSize);

    /**
     * 店铺统计
     *
     * @return
     */
    @GET("shop/v1.Statistics/shop_statistics")
    Call<EntityObject<StatisticsBean>> getShopStatistics();

    /**
     * 客户管理
     *
     * @return
     */
    @GET("shop/v1.Statistics/customer")
    Call<EntityObject<ArrayList<CustomerBean>>> getCustomerList(@Query("start") int page, @Query("limit") int pageSize);

    /**
     * 客户明细
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GET("shop/v1.Statistics/customer_detail")
    Call<EntityObject<ArrayList<CustomerDetailBean>>> getCustomerDetailList(@Query("id") int id, @Query("start") int page, @Query("limit") int pageSize);

    /**
     * 消费明细
     *
     * @return
     */
    @GET("shop/v1.Statistics/detailed")
    Call<EntityObject<ArrayList<ConsumerDetailBean>>> getConsumerDetailList(@Query("start") int page, @Query("limit") int pageSize);


    /***
     * 采购统计列表
     * @param start_time
     * @param end_time
     * @return
     */
    @GET("shop/v1.Statistics/purchase_count")
    Call<EntityObject<ArrayList<CountBean>>> getPurchaseCountList(@Query("start_time") long start_time, @Query("end_time") long end_time);

    /***
     * 订单统计列表
     * @param start_time
     * @param end_time
     * @return
     */
    @GET("shop/v1.Statistics/order_count")
    Call<EntityObject<ArrayList<CountBean>>> getOrderCountList(@Query("start_time") long start_time, @Query("end_time") long end_time);

    /**
     * 店铺管理
     *
     * @param busi_time
     * @param is_free
     * @param effic
     * @param amount
     * @param full_free
     * @return
     */
    @GET("shop/v1.Shop/AddShopInfo")
    Call<EntityObject<Object>> addShopInfo(@Query("busi_time") String busi_time, @Query("is_free") int is_free,
                                           @Query("effic") String effic, @Query("distance") String distance, @Query("amount") String amount, @Query("full_free") String full_free, @Query("announce") String announce);


    /**
     * 生成订单
     *
     * @param sprice
     * @param addr
     * @param data
     * @param remark
     * @return
     */
    @POST("shop/v1.Order/generateOrderShopS")
    @FormUrlEncoded
    Call<EntityObject<OrderBean>> createOrder(@Field("token") String token, @Field("sprice") String sprice, @Field("addr") String addr,
                                              @Field("data") String data, @Field("datas") String dataTicket, @Field("remark") String remark, @Field("address_id") int id);

    /**
     * 再来一单
     *
     * @param sprice
     * @param addr
     * @param data
     * @param remark
     * @return
     */
    @POST("shop/v1.Order/anotherOrder")
    @FormUrlEncoded
    Call<EntityObject<OrderBean>> anotherOrder(@Field("token") String token, @Field("sprice") String sprice, @Field("addr") String addr,
                                               @Field("data") String data, @Field("datas") String dataTicket, @Field("remark") String remark, @Field("address_id") int id);

    /**
     * 货到付款
     *
     * @return
     */
    @GET("shop/v1.Order/preGenerateOrderShop")
    Call<EntityObject<PayBean>> payOffLine(@Query("order_no") String order_no);

    /**
     * 在线支付
     *
     * @return
     */
    @GET("shop/v1.Pingpp/pay")
    Call<EntityObject<PayBean>> getPayChannelInfo(@Query("order_no") String order_no, @Query("channel") String channel);
    /**
     * 支付宝在线支付
     *
     * @return
     */
    @GET("shop/v1.Pingpp/pay")
    Call<EntityObject<String>> getAliPayChannelInfo(@Query("order_no") String order_no, @Query("channel") String channel);
    /**
     * 订单管理-采购订单
     *
     * @return
     */
    @GET("shop/v1.Order/purchaseOrderList_ss")
    Call<EntityObject<ArrayList<BuyOrderListBean>>> getPurchaseOrderList(@Query("status") int status, @Query("start") int page, @Query("limit") int pageSize);


    /**
     * 订单管理-卖水订单
     *
     * @return
     */
    @GET("shop/v1.Order/sailOrderList")
    Call<EntityObject<ArrayList<SailOrderListBean>>> getSailOrderList(@Query("status") int status, @Query("start") int page, @Query("limit") int pageSize);

    /**
     * 接单
     *
     * @param order_no
     * @return
     */
    @GET("shop/v1.Order/reseiveOrder")
    Call<EntityObject<Object>> receiptOrder(@Query("order_no") String order_no);

    /**
     * 取消订单
     *
     * @param order_no
     * @return
     */
    @GET("shop/v1.Order/exitOrder")
    Call<EntityObject<Object>> cancelOrder(@Query("order_no") String order_no);

    /**
     * 接单
     *
     * @param order_no
     * @return
     */
    @GET("shop/v1.Order/sendOrder")
    Call<EntityObject<Object>> sendOrder(@Query("order_no") String order_no, @Query("status") int status);

    /**
     * 提现
     *
     * @return
     */
    @GET("shop/v1.Statistics/reflect")
    Call<EntityObject<CashBean>> getCashInfo();
    /**
     * 提交提现
     *
     * @return
     */
    /**
     * @param amount
     * @param remark
     * @param account
     * @param bank
     * @param card_no
     * @param id      驳回提现再次提现传id  其他不用传
     * @return
     */
    @GET("shop/v1.Statistics/addreflect")
    Call<EntityObject<CashBean>> postCash(@Query("amount") String amount, @Query("remark") String remark, @Query("account") String account,
                                          @Query("bank") String bank, @Query("card_no") String card_no, @Query("id") String id,@Query("order_sn[]") String[] order_sn, @Query("branch") String branch);

    /**
     * 广告
     *
     * @return
     */
    @GET("shop/v1.Index/newBanner")
    Call<EntityObject<BannerBeanNew>> getBannerList();

    /**
     * 轮播图 图文详情
     *
     * @param urlId
     */
    @GET("shop/v1.Index/BannerDetail")
    Call<EntityObject<BannerBean>> getBannerDetail(@Query("id") String urlId);

    /**
     * 店铺优惠券列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GET("shop/v1.Coupon/couponlist")
    Call<EntityObject<ArrayList<CouponBean>>> getCouponList(@Query("start") int page, @Query("limit") int pageSize);

    /**
     * 店铺创建优惠券
     *
     * @return
     */
    @GET("shop/v1.Coupon/addreflect")
    Call<EntityObject<Object>> createCoupon(@Query("price") String price, @Query("full") String full, @Query("start") String start, @Query("end") String end, @Query("is_give") int is_give);

    /**
     * 店铺优惠券 暂停中 和使用中
     *
     * @return
     */
    @GET("shop/v1.Coupon/suspend")
    Call<EntityObject<Object>> upDownCoupon(@Query("id") int id);

    /**
     * 店铺营销接口
     *
     * @return
     */
    @GET("shop/v1.Coupon/shopmarketing")
    Call<EntityObject<ShopMarketData>> getShopMarketData();

    /**
     * 店铺水票列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GET("shop/v1.Coupon/waterticket_v2")
    Call<EntityObject<ArrayList<TicketBean>>> getShopTicketList(@Query("start") int page, @Query("limit") int pageSize);

    /**
     * 店铺创建水票券
     *
     * @return
     */
    @GET("shop/v1.Coupon/createwaterticket_v2")
    Call<EntityObject<Object>> createTicket(@Query("gid") String gid, @Query("s_name") String s_name, @Query("num") String num, @Query("pprice") String pprice, @Query("sprice") String sprice, @Query("start") String start, @Query("end") String end);

    /**
     * 选择商品列表
     *
     * @return
     */
    @GET("shop/v1.Coupon/getgoods")
    Call<EntityObject<ArrayList<GoodsBean>>> getSelectGoodsList(@Query("gtype") String gtype, @Query("start") int page, @Query("limit") int pageSize);

    /**
     * 选择商品类型
     *
     * @return
     */
    @GET("shop/v1.Coupon/getgoodstype")
    Call<EntityObject<ArrayList<GoodsManageBean>>> getSelectGoodsType();

    /**
     * 水票是否售卖
     *
     * @return
     */
    @GET("shop/v1.Coupon/is_sell")
    Call<EntityObject<Object>> upDownTicket(@Query("id") int id);

    /**
     * 店铺基本信息
     *
     * @return
     */
    @GET("shop/v1.Shop/showShopBaseInfo")
    Call<EntityObject<ShopInfoData>> getShopInfoData();

    /**
     * 店铺基本信息
     *
     * @return
     */
    @GET("shop/v1.Shop/addShopBaseInfo")
    Call<EntityObject<ShopInfoData>> updateShopInfoData(@Query("header_pic") String image, @Query("sname") String sname, @Query("address") String address, @Query("lnglat") String lnglat, @Query("province") String province, @Query("city") String city, @Query("dist") String dist);

    /**
     * 提交图片
     *
     * @return
     */
    @Multipart
    @POST("shop/v1.Watershop/addgoodsimg")
    Call<EntityObject<AlbumItem>> uploadImage(@Part MultipartBody.Part file);
    //TODO
    /**
     * 地址模糊查询
     *
     * @param keyword
     * @return
     */
    @GET("/ws/place/v1/search")
    Call<TencentLocationBean> searchLocationList(@Query("keyword") String keyword, @Query("boundary") String boundary, @Query("policy") int policy, @Query("page_size") int page_size, @Query("key") String key);

    /**
     * 购买空桶列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GET("shop/v1.Bucketmanege/emptyBucketList")
    Call<EntityObject<ArrayList<BucketBean>>> getEmptyBucketList(@Query("start") int page, @Query("limit") int pageSize);

    /**
     * 购买空桶
     *
     * @return
     */
    @GET("shop/v1.Bucketmanege/buyEmptyBucket")
    Call<EntityObject<BucketPayBean>> buyEmptyBucket(@Query("gid") int gid, @Query("num") int num, @Query("cost_price") String price, @Query("pay_way") String payWay);

    /**
     * 空桶订单列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GET("shop/v1.Bucketmanege/emptyBucketOrderList")
    Call<EntityObject<ArrayList<BucketBean>>> getEmptyBucketOrderList(@Query("status") int status, @Query("start") int page, @Query("limit") int pageSize);

    /**
     * 店铺优惠券列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GET("shop/v1.Bucketmanege/plantsList")
    Call<EntityObject<ArrayList<ShopBean>>> getWaterPlantList(@Query("start") int page, @Query("limit") int pageSize);

    /**
     * 存欠详情列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GET("shop/v1.Bucketmanege/emptyBucketSaveList")
    Call<EntityObject<BucketSaveData>> getEmptyBucketSaveList(@Query("did") int did, @Query("status") int status, @Query("start") int page, @Query("limit") int pageSize);

    /**
     * 空桶记录详情
     *
     * @return
     */
    @GET("shop/v1.Bucketmanege/emptyBucketRecord")
    Call<EntityObject<BucketRecordData>> getEmptyBucketRecord(@Query("did") int did);

    /**
     * 空桶记录列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GET("shop/v1.Bucketmanege/emptyBucketRecordList")
    Call<EntityObject<ArrayList<BucketRecordBean>>> getEmptyBucketRecordList(@Query("did") int did, @Query("status") int status, @Query("start") int page, @Query("limit") int pageSize);

    /**
     * 记录详情
     *
     * @return
     */
    @GET("shop/v1.Bucketmanege/emptyBucketDetail")
    Call<EntityObject<BucketBean>> getEmptyBucketDetail(@Query("bid") int bid, @Query("status") int status);

    /**
     * 分享店铺（二维码分享/个性分享）
     */
    @POST("shop/v1.Share/getShareInfoV2")
    @FormUrlEncoded
    Call<EntityObject<ShareShopBean>> shareShop(@Field("token") String token);

    /**
     * 检查购物车
     *
     * @return
     */
    @GET("shop/v1.Cart/checkCart")
    Call<EntityObject<ArrayList<GoodsBean>>> getCheckCart(@Query("data") String data);

    /**
     * 存欠详情接口
     *
     * @return
     */
    @GET("shop/v1.Bucketmanege/saveOwnDetail")
    Call<EntityObject<ArrayList<SaveOweBean>>> saveOwnDetail(@Query("did") int did);

    /**
     * 采集订单详情
     *
     * @return
     */
    @GET("shop/v1.Order/orderdetail")
    Call<EntityObject<PurchaseDetailsBean>> getOrderDetail(@Query("order_no") String data);

    /**
     * 操作订单显示接口
     */
    @GET("shop/v1.Order/getOrderDetail")
    Call<EntityObject<OrderShowBean>> orderShowNew(@Query("order_sn") String orderSn);

    /**
     * 可换取的自营桶列表
     */
    @GET("shop/v1.Changebucket/canChangeBucketList")
    Call<EntityObject<ArrayList<OrderShowBean.RecyclerBean>>> canHuiTonglist(@Query("order_sn") String orderSn);

    /**
     * 杂桶回收--去支付
     */
    @POST("shop/v1.Changebucket/goPay")
    @FormUrlEncoded
    Call<EntityObject<OrderBean>> goPay(@Field("token") String token,
                                        @Field("order_sn") String order_sn,
                                        @Field("z_num") String num,
                                        @Field("po_num") String po_num,
                                        @Field("data_bucket") String data_bucket,
                                        @Field("price") String price);

    /**
     * 删除换桶
     *
     * @return
     */
    @GET("shop/v1.Changebucket/clearChangeBucket")
    Call<EntityObject<String>> clearChangeBucket(@Query("order_sn") String orderNo);

    /**
     * 确认订单
     *
     * @param order_sn
     * @param data_recycler
     * @return
     */
    @POST("shop/v1.Order/sureOrderNew")
    @FormUrlEncoded
    Call<EntityObject<OperationSureBean>> sureOrder(@Field("token") String token,
                                                    @Field("order_sn") String order_sn,
                                                    @Field("data_goods") String data_goods,
                                                    @Field("cid") String cid,
                                                    @Field("data_recycler") String data_recycler);//退水

    //获取默认仓库
    @GET("shop/v1.Order/defaultWareHouse")
    Call<EntityObject<DefaultWareHouseBean>> defaultWareHouse(@Query("order_sn") String orderNo);

    //获取仓库列表
    @GET("shop/v1.Order/getWareHouseList")
    Call<EntityObject<ArrayList<StoreListBean>>> getWareHouseList(@Query("order_sn") String orderNo);

    /**
     * 活动中心
     *
     * @return
     */
    @GET("shop/v1.Activitycenter/ShowActivityPlants")
    Call<EntityObject<ArrayList<ActivityPlantsBean>>> ShowActivityPlants(@Query("status") int status);

    /**
     * 常购清单
     *
     * @return
     */
    @GET("shop/v1.Activitycenter/ShowPlants")
    Call<EntityObject<ArrayList<ShowPlantsBean>>> ShowPlants();

    /**
     * 获取可以使用的优惠券
     *
     * @return
     */
    @GET("shop/v1.Activitycenter/ShowCoupon")
    Call<EntityObject<ShowCouponBean>> ShowCoupon(@Query("did") int did, @Query("amount") String money, @Query("data") String data);

    /**
     * 选择可添加的商品
     *
     * @return
     */
    @GET("shop/v1.Activitycenter/getGoods")
    Call<EntityObject<ArrayList<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean>>> getGoods(@Query("did") int did);

    /**
     * 我的优惠券列表
     *
     * @return
     */
    @GET("shop/v1.Mywallet/myCouponList")
    Call<EntityObject<MycouBean>> myCouponList(@Query("status") int status, @Query("page") int page, @Query("limit") int limit);


    /**
     * 我的钱包
     */
    @POST("shop/v1.Mywallet/getWalletInfo")
    @FormUrlEncoded
    Call<Object> getMyWalletInfo(@Field("token") String token);


    /**
     * 充值中心
     */
    @POST("shop/v1.Recharge/getRechargeList")
    @FormUrlEncoded
    Call<Object> getRechargeCenterList(@Field("token") String token);


    /**
     * 充值记录
     * token	是	string	token
     * page	否	int	page
     * limit
     */
    @POST("shop/v1.Recharge/myRechargeRecordList")
    @FormUrlEncoded
    Call<Object> getRechargeRecordList(@Field("token") String token, @Field("page") int page, @Field("limit") int limit);

    /**
     * 余额明细
     * token	是	string	token
     * did	是	int	水厂id
     * page	否	int	page
     * limit
     */
    @POST("shop/v1.Recharge/myRechargeList")
    @FormUrlEncoded
    Call<Object> getMyRechargeList(@Field("token") String token, @Field("did") String did, @Field("page") int page, @Field("limit") int limit);


    /**
     * 我的fragment 信息
     * token	是	string	token
     */
    @POST("shop/v1.Shop/myShopInfo")
    @FormUrlEncoded
    Call<Object> getMyFragmentInfo(@Field("token") String token);

    /**
     * 我的fragment 信息
     * token	是	string	token
     * r_id	是	int	充值券id
     * price  是	string	金额
     */
    @POST("shop/v1.Recharge/confirmOrder")
    @FormUrlEncoded
    Call<Object> rechargeConfirmOrder(@Field("token") String token, @Field("r_id") int r_id, @Field("price") String price);

    /**
     * 采购订单下单
     * token	是	string	token
     */
    @POST("shop/v1.Order/generateOrderShopS")
    @FormUrlEncoded
    Call<EntityObject<OrderBean>> generateOrderShopS(@Field("token") String token, @Field("did") int did, @Field("sprice") String sprice, @Field("goods") String goods, @Field("ticket") String ticket, @Field("c_id") int cid, @Field("address_id") int address_id);

    /**
     * 水店配送信息
     *
     * @return
     */
    @GET("shop/v1.Shop/getShopWateInfo")
    Call<EntityObject<CommitOrderBean>> getShopWateInfo(@Query("did") int did);

    /**
     * 余额
     *  did  水厂id
     * @return
     */
    @GET("shop/v1.Order/getBalance")
    Call<EntityObject<BucketOrderBean>> getBalance(@Query("did") int did);

    /**
     * 采购订单余额支付
     *
     * @return
     */
    @GET("shop/v1.Order/balancePayment")
    Call<EntityObject<PayBean>> balancePayment(@Query("order_no") String order);

    /**
     * 购桶订单余额支付
     *
     * @return
     */
    @GET("shop/v1.Bucketmanege/balancePayment")
    Call<EntityObject<String>> bucketPayment(@Query("gid") int gid, @Query("num") int num, @Query("cost_price") String price);


    /**
     * 获取返利信息
     * token	是	string	token
     * did
     *
     * @return
     */
    @GET("shop/v1.Rebate/ShowPlantsRebateInfo")
    Call<Object> getReturnMoneyInfo(@Query("token") String token, @Query("did") int did);

    /**
     * 返利金提现
     * token	是	string	token
     * did	是	string	水厂ID
     * amount	是	string	金额
     *
     * @return
     */
    @GET("shop/v1.Rebate/rebateWithdrawal")
    Call<Object> returnMoneyWithdrawDeposit(@Query("token") String token, @Query("did") int did, @Query("amount") String amount);

    /**
     * 获取提现信息
     * token	是	string	token
     * did	是	int	水店did
     *
     * @return
     */
    @GET("shop/v1.Rebate/getWithdrawal")
    Call<Object> getWithdrawDepositInfo(@Query("token") String token, @Query("did") int did);

    /**
     * 转至余额
     * token	是	string	token
     * did	是	string	水厂ID
     * amount	是	string	金额
     *
     * @return
     */
    @GET("shop/v1.Rebate/rebateTurnAmount")
    Call<Object> returnMoneyToBalance(@Query("token") String token, @Query("did") String did, @Query("amount") String amount);


    /**
     * 我的返利顶部信息
     * token	是	string	token
     * did	是	int	水店did
     *
     * @return
     */
    @GET("shop/v1.Rebate/myRebateDetails")
    Call<Object> getMyReturnMoney(@Query("token") String token, @Query("did") int did);


    /**
     * 我的返利底部信息
     * token	是	string	token
     * did	是	int	水店did
     *
     * @return
     */
    @GET("shop/v1.Rebateoperation/rebateList")
    Call<Object> getMyReturnMoneyList(@Query("did") int did);
    /**
      * 结算返利订单列表
      token	是	string	token
      did	是	int	水厂id
      r_id	是	int	水厂id
      start_time	是	int	周期开始时间
      end_time	是	int	周期结束时间
     *
     * @return
     */
    @GET("shop/v1.Rebateoperation/getOrderList")
    Call<Object> getMyReturnMoneyCheckOutList(@Query("did") int did,@Query("r_id") int r_id,@Query("start_time") long start_time,@Query("end_time") long end_time);
    /**
     * 我的返利底部信息
     token	是	string	token
     did	是	int	水厂id
     r_id	是	int	水厂id
     start_time	是	int	周期开始时间
     end_time	是	int	周期结束时间
     *
     * @return
     */
    @GET("shop/v1.Rebateoperation/getOrderList")
    Call<Object> getMyReturnMoneyOrder(@Query("did") int did,@Query("r_id") int rId,@Query("start_time") String start_time,@Query("end_time") String end_time);

    /**
     * 我的返利记录
     * token	是	string	token
     * did	是	int	水店did
     * startus	是	int	0 全部，1单笔返利，2月度返利，3季度返利，4年度返利，5其他返利
     * start	否	int	默认0页
     * limit	否	int	默认10条
     *
     * @return
     */
    @GET("shop/v1.Rebate/myRebateList")
    Call<Object> getMyReturnMoneyRecord(@Query("token") String token, @Query("did") int did, @Query("status") int startus, @Query("start") int start, @Query("limit") int limit);

    /**
     * 返利金明细
     * token	是	string	token
     * did
     *
     * @return
     */
    @GET("shop/v1.Rebate/rebateDetails")
    Call<Object> getMyReturnMoneyDetails(@Query("token") String token, @Query("did") String did, @Query("start") int start, @Query("limit") int limit);


    /**
     * 获取水厂返利模版列表
     * token	是	string	token
     * did	是	int	水店did
     *
     * @return
     */
    @GET("shop/v1.Rebate/rebatePolicyList")
    Call<Object> getReturnMoneyTempList(@Query("token") String token, @Query("did") int did);


    /**
     * 获取水厂返利模版列表
     * token	是	string	token
     * r_id	是	int	模板ID
     *
     * @return
     */
    @GET("shop/v1.Rebate/rebatePolicy")
    Call<Object> getReturnMoneyTempPolicy(@Query("token") String token, @Query("r_id") int r_id);


    /**
     * 返利记录列表（展示）
     * token	是	string	token
     * did	是	int	水厂did
     * r_id	是	int	模板ID
     * start	否	int	默认0页
     * limit	否	int	默认10条
     *
     * @return
     */
    @GET("shop/v1.Rebate/rebateRecord")
    Call<Object> getReturnMoneyRecordByTem(@Query("token") String token, @Query("did") int did, @Query("r_id") int r_id, @Query("start") int start, @Query("limit") int limit);


    /**
     * 申请返利
     token	是	string	token
     did	是	string	rebate_no
     *
     * @return
     */
    @GET("shop/v1.Rebate/applyRebate")
    Call<Object> returnMoneyRecordApplyRebate( @Query("rebate_no") String rebate_no);


    /**
     * 可供添加活动商品列表
     * token	是	string	token
     *
     * @return
     */
    @GET("shop/v1.Secondskill/ShowGoods")
    Call<Object> getFlashSaleGoodsAddList(@Query("token") String token);


    /**
     * 添加活动商品
     * token	是	string	token
     * seckill_id	是	int	秒杀时段ID
     * gid	是	int	商品ID
     * seckill_price	是	string	秒杀价
     * seckill_restrictions	是	int	秒杀限购
     * seckill_stock	是	int	秒杀库存
     * date_time	是	string	日期
     *
     * @return
     */
    @GET("shop/v1.Secondskill/AddGoods")
    Call<Object> addFlashSaleGoods(@Query("seckill_id") int seckill_id, @Query("gid") String gid, @Query("seckill_price") String seckill_price, @Query("seckill_restrictions") int seckill_restrictions, @Query("seckill_stock") int seckill_stock, @Query("date_time") String date_time);

    /**
     * 编辑活动商品
     * token	是	string	token
     * seckill_id	是	int	秒杀时段ID
     * gid	是	int	商品ID
     * seckill_price	是	string	秒杀价
     * seckill_restrictions	是	int	秒杀限购
     * seckill_stock	是	int	秒杀库存
     * date_time	是	string	日期
     * id	否	int	商品秒杀id
     *
     * @return
     */
    @GET("shop/v1.Secondskill/AddGoods")
    Call<Object> editFlashSaleGoods(@Query("token") String token, @Query("seckill_id") int seckill_id, @Query("gid") String gid, @Query("seckill_price") String seckill_price, @Query("seckill_restrictions") int seckill_restrictions, @Query("seckill_stock") int seckill_stock, @Query("date_time") String date_time, @Query("id") int id);

    /**
     * 获取默认场次
     * token	是	string	token
     */
    @GET("shop/v1.Secondskill/defualtTime")
    Call<Object> getFlashSaleDefualtSession(@Query("token") String token);


    /**
     * 场次数据
     * token	是	string	token
     */
    @GET("shop/v1.Secondskill/ShowTime")
    Call<Object> getFlashSaleSession(@Query("token") String token);

    /**
     * 获取场次商品列表
     * token	是	string	token
     */
    @GET("shop/v1.Secondskill/ShowSecondsKillGoods")
    Call<Object> getFlashSaleSessionGoodsList(@Query("token") String token, @Query("seckill_id") int seckill_id,@Query("date_time") String date_time);

    /**
     * 删除场次商品列表
     * token	是	string	token
     * id	否	int	商品秒杀id
     */
    @GET("shop/v1.Secondskill/deleteSecondsKillGoods")
    Call<Object> deleteFlashSaleSessionGoods(@Query("token") String token, @Query("id") int id);


    /**
     * 显示限时抢购列表
     * token	是	string	token
     * start	否	string	默认0
     * limit	否	string	默认10
     */
    @GET("shop/v1.Secondskill/ShowTimeSecondsKillList")
    Call<Object> getFlashSaleList(@Query("token") String token, @Query("start") String start, @Query("limit") String limit);

    /**
     * 已结束、已退出列表详情
     * token	是	string	token
     */
    @GET("shop/v1.Secondskill/alreadyExitSecondsKillList")
    Call<Object> getFlashSaleExitDetails(@Query("token") String token);


    /**
     * 退出活动
     * token	是	string	token
     * seckill_id	否	int	商品id
     */
    @GET("shop/v1.Secondskill/ExitSecondsKillGoods")
    Call<Object> exitAct(@Query("date_time") String date_time,@Query("seckill_id") int seckill_id);


    /**
     * 我的设备列表
     * token	是	string	token
     * seckill_id	否	int	商品id
     */
    @GET("shop/v1.Device/deviceList")
    Call<Object> getMySmartEquipList(@Query("token") String token);

    /**
     * 设备柜子列表
     * token	是	string	token
     * seckill_id	否	int	商品id
     */
    @GET("shop/v1.Device/cabinetList")
    Call<Object> getSmartEquipData(@Query("token") String token, @Query("device_id") int seckill_id);


    /**
     * 设备柜子柜位列表
     * token	是	string	token
     * seckill_id	否	int	商品id
     */
    @GET("shop/v1.Device/latticeList")
    Call<Object> getSmartEquipCounters(@Query("token") String token, @Query("cabinet_id") int cabinet_id);

    /**
     * 打开柜子
     * token	是	string	token
     * seckill_id	否	int	商品id
     */
    @GET("shop/v1.Device/openSomeBox")
    Call<Object> openSmartEquipCounters(@Query("token") String token, @Query("id") String id);


    /**
     * 删除柜位商品
     * token	是	string	token
     * id	是	int	id:柜位的id;
     */
    @GET("shop/v1.Device/removeLatticeGoods")
    Call<Object> removeCounterGoods(@Query("token") String token, @Query("id") String id);


    /**
     * 添加和修改柜位商品
     * token	是	string	token
     * data	是	json	[{“id”:1,”goods_id”:23}],id:柜位的id;goods_id:商品的id
     */
    @GET("shop/v1.Device/updateLatticeGoods")
    Call<Object> modifyCounterGoods(@Query("token") String token, @Query("data") String data);


    /**
     * 柜位修改选择商品列表
     * token	是	string	token
     * data	是	json	[{“id”:1,”goods_id”:23}],id:柜位的id;goods_id:商品的id
     */
    @GET("shop/v1.Device/LatticeGoods")
    Call<Object> selectCounterGoods(@Query("token") String token);


    /**
     * 设备订单列表
     * token	是	string	token
     * start	是	int	start 0
     * limit	是	int	limit
     */
    @GET("shop/v1.Device/orderList")
    Call<Object> getEquipOrderList(@Query("token") String token, @Query("start") int start, @Query("limit") int limit);

    /**
     * 设备订单退款
     * token	是	string	token
     * order_code	是	string	订单号
     */
    @GET("shop/v1.Device/refund")
    Call<Object> equipOrderRefund(@Query("token") String token, @Query("order_code") String order_code);


    /**
     * 设备订单退款
     * token	是	string	token
     * order_code	是	string	订单号
     */
    @GET("shop/v1.Device/orderInfo")
    Call<Object> equipOrderDetails(@Query("token") String token, @Query("order_code") String order_code);


    /**
     * 设备商品列表
     * token	是	string	token
     */
    @GET("shop/v1.Devicegoods/deviceGgoods")
    Call<Object> getDeviceGoods(@Query("token") String token);

    /**
     * 修改设备商品售价
     * token	是	string	token
     * gid	是	string	商品gid
     * price	是	string	价格
     */
    @GET("shop/v1.Devicegoods/editPrice")
    Call<Object> editDeviceGoodsPrice(@Query("token") String token, @Query("id") String id, @Query("price") String price);


    /**
     * 上下架设备商品
     * token	是	string	token
     * gid	是	string	商品gid
     * price	是	string	价格
     */
    @GET("shop/v1.Devicegoods/upDownGoods")
    Call<Object> upDownDeviceGoods(@Query("gid") String id);

    /**
     * 设备可添加的所有商品
     * token	是	string	token
     * gid	是	string	商品gid
     * price	是	string	价格
     */
    @GET("shop/v1.Devicegoods/goods")
    Call<Object> getAddableDeviceGoods();

    /**
     * 店铺收益余额
     * token	是	string	token
     */
    @GET("shop/v1.Statistics/getbanlance")
    Call<Object> getShopEarnBalance(@Query("token") String token);

    /**
     * 店铺收益申请提现
     * token	是	string	token
     * start	否	int	start 0
     * limit	否	int	limit
     * status	是	int	1可提现收益，2冻结中收益，3 提现中收益
     */
    @GET("shop/v1.Statistics/forward_order")
    Call<Object> shopEarnApplyWDList(@Query("token") String token, @Query("start") int start, @Query("limit") int limit, @Query("status") int status);

    /**
     * 店铺收益提现记录
     token	是	string	token
     start	否	int	start 0
     limit
     */
    @GET("shop/v1.Statistics/forward_record")
    Call<Object> getShopEarnWdRecord(@Query("token") String token, @Query("start") int start, @Query("limit") int limit);

    /**
     * 店铺收益提现记录详情
     token	是	string	token
     forward_code	是	string	提现单号
     */
    @GET("shop/v1.Statistics/forward_record_details")
    Call<Object> getShopEarnWdRecordDetails(@Query("token") String token, @Query("forward_code") String forward_code);


    /**
     * 商品管理修改售价
     *
     * token	是	string	token
     id	是	int	id
     price	是	float	售价
     */
    @GET("shop/v1.Goods/editPrice")
    Call<Object> editGoodsManagementPrice(@Query("id") String id,@Query("price") String price);



    /**
     * 新商品管理列表
     *
     * @param status 1上架，0下架
     * @return
     */
    @GET("shop/v1.Goods/goodsManagementList")
    Call<Object> getGoodsManageNewList(@Query("status") int status);


    /**
     * 商品管理 上架下架处理
     *
     * @return
     */
    @GET("shop/v1.Goods/upDownGoods")
    Call<Object> goodsManageNewUpDownGoods(@Query("sid") int sid, @Query("gid") String gid);

    /**
     * 水厂地址电话
     */
    @GET("shop/v1.Shop/getWate")
    Call<EntityObject<AddressBean>> getWate(@Query("did") int did);

    /**
     * 水票余额支付
     */
    @GET("shop/v1.Waterticket/balancePayment")
    Call<EntityObject<PayBean>> balancePaymentYuer(@Query("order_no") String order);

    /**
     * 获取可购买的优惠水票列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GET("shop/v1.Waterticket/showTickets_v1")
    Call<Object> getDiscountWaterCouponList(@Query("start") int page, @Query("limit") int pageSize);


    /**
     * 版本更新（接口域名控制）
     */
    @GET("shop/v1.Versionsapi/GetVersionInfo")
    Call<EntityObject<GetVersionBean>> getVersionInfo(@Query("type") int type, @Query("client_type") int client_type, @Query("version") String version);
}
