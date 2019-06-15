package com.shuiwangzhijia.shuidian.network;

import android.app.Application;
import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.socks.library.KLog;
import com.shuiwangzhijia.shuidian.http.FndUtils;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class OkgoRequest {
    //超时时间设置
    private int connectTimeOut = 60;
    private int writeTimeOut = 60;
    private int readTimeOut = 60;

    private static Context mContext ;
    private static OkgoRequest okgoRequestInstance;

    private static volatile OkHttpClient okHttpClient;

    private OkgoRequest() {
    }

    public static OkgoRequest getInstance() {
        if (okgoRequestInstance == null)
            okgoRequestInstance = new OkgoRequest();
        return okgoRequestInstance;
    }

    public static void initOkgo(Application application) {
         /*//---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
        HttpHeaders headers = new HttpHeaders();
        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文，不允许有特殊字符
        headers.put("commonHeaderKey2", "commonHeaderValue2");
        HttpParams params = new HttpParams();
        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
        params.put("commonParamsKey2", "这里支持中文参数");*/
        OkGo.getInstance().init(application)                       //必须调用初始化
                .setOkHttpClient(OkgoRequest.getInstance().getOkHttpClient())               //建议设置OkHttpClient，不设置将使用默认的
                //  .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                // .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
        //   .addCommonHeaders(headers)                      //全局公共头
        //     .addCommonParams(params)                      //全局公共参数
        ;
    }

    public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            synchronized (OkgoRequest.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder()//
                            .addInterceptor(mLoggingInterceptor)//
                            .connectTimeout(connectTimeOut, TimeUnit.SECONDS)//
                            .writeTimeout(writeTimeOut, TimeUnit.SECONDS)//
                            .readTimeout(readTimeOut, TimeUnit.SECONDS)//
                            //使用sp保持cookie，如果cookie不过期，则一直有效
                            //           .cookieJar(new CookieJarImpl(new SPCookieStore(mContext)))
                            //使用数据库保持cookie，如果cookie不过期，则一直有效
                            //           .cookieJar(new CookieJarImpl(new DBCookieStore(mContext)))
                            //使用内存保持cookie，app退出后，cookie消失
                            //           .cookieJar(new CookieJarImpl(new MemoryCookieStore()))
                            //可进行https请求相关的配置
                            .build();
                }
            }
        }
        return okHttpClient;
    }

    //日志拦截器
    // 打印返回的json数据拦截器
    private Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder requestBuilder = request.newBuilder();
            requestBuilder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");

            request = requestBuilder.build();

            final Response response = chain.proceed(request);

            KLog.d("请求网址: \n" + request.url() + " \n " + "请求头部信息：\n" + request.headers() + "响应头部信息：\n" + response.headers());

            final ResponseBody responseBody = response.body();
            final long contentLength = responseBody.contentLength();

            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(charset);
                } catch (UnsupportedCharsetException e) {
                    KLog.e("Couldn't decode the response body; charset is likely malformed.");
                    return response;
                }
            }
           /* if (contentLength != 0) {
                KLog.v("--------------------------------------------开始打印返回数据----------------------------------------------------");
                String returnData = buffer.clone().readString(charset);
                KLog.json(returnData);
                KLog.e(returnData);
                if (returnData.startsWith("{")) {
                    try {
                        JSONObject object = new JSONObject(returnData);
                        if("账号或密码错误".equals(object.getString("msg")))
                            return response;
                        Intent intent = new Intent();
                        intent.putExtra("errorCode", object.getInt("code"));
                        intent.setAction("request.error");
                        //适配8.0广播
                        intent.setComponent(new
                                ComponentName("cn.waterhelper.shop", "cn.waterhelper.shop.base.ErrorReceiver"));
                        mContext.sendBroadcast(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                KLog.v("--------------------------------------------结束打印返回数据----------------------------------------------------");
            }*/
            return response;
        }
    };

    public static void doPost(Context context, Map<String, String> params, String url, StringCallback callback) {
        mContext=context;
        if (FndUtils.isConnected(context)) {
            PostRequest<String> stringPostRequest = OkGo.<String>post(url);
            if (params.size() != 0) {
                for (Map.Entry entry : params.entrySet()) {
                    String key = entry.getKey().toString();
                    stringPostRequest.params(key, params.get(key));
                }
            }
            stringPostRequest.tag(context)
                    .execute(callback);
            KLog.e(url);
            KLog.e(stringPostRequest.getParams());
        } else {
            //ToastUtils.show("请保持网络通畅！");
        }
    }

    public static void doPostWithToken(Context context, Map<String, String> params, String url, StringCallback callback) {
        mContext=context;
        if (FndUtils.isConnected(context)) {
            PostRequest<String> stringPostRequest = OkGo.<String>post(url);
            stringPostRequest.params("token", CommonUtils.getToken());
            if (params.size() != 0) {
                for (Map.Entry entry : params.entrySet()) {
                    String key = entry.getKey().toString();
                    stringPostRequest.params(key, params.get(key));
                }
            }
            stringPostRequest.tag(context)
                    .execute(callback);
            KLog.e(url);
            KLog.e(stringPostRequest.getParams());
        } else {
            ToastUtils.show("请保持网络通畅！");
        }
    }
}
