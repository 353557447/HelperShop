package com.shuiwangzhijia.shuidian.http;


import com.socks.library.KLog;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;


public class LoggingInterceptor implements Interceptor {
    private final Charset UTF8 = Charset.forName("UTF-8");
    private String TAG = "LoggingInterceptor";


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request oldRequest = chain.request();
        RequestBody requestBody = oldRequest.body();

        String body = null;

        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            body = buffer.readString(charset);
        }
//        String send=String.format("发送请求\nmethod：%s\nurl：%s\nheaders: %sbody：%s",
//                request.method(), request.url(), request.headers(), body);
//        Log.i(TAG,send);

        //新增公共参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url().newBuilder()
                .scheme(oldRequest.url().scheme()).host(oldRequest.url().host())
                .addQueryParameter("token", CommonUtils.getToken());

        // 新的请求
        Request newRequest = oldRequest.newBuilder().method(oldRequest.method(), oldRequest.body()).url(authorizedUrlBuilder.build()).build();
        long startNs = System.nanoTime();
        Response response = chain.proceed(newRequest);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        String rBody = null;
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                e.printStackTrace();
            }
        }
        //发正式版本的时候将其删除，不然后的手机会报OOM
        rBody = buffer.clone().readString(charset);
        String res = String.format("收到响应 %s %s %ss\n请求url：%s\n返回数据：%s",
                response.code(), response.message(), tookMs, response.request().url(), rBody);
        KLog.e(res);
        return response;
    }

}
