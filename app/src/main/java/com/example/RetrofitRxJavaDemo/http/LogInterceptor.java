package com.example.RetrofitRxJavaDemo.http;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 打印日志拦截器
 */
public class LogInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request request = chain.request();
        Response response = chain.proceed(request);
        String method = request.method();
        if ("GET".equals(method)) {
            Log.e(method, request.url().toString());
        }

        if ("POST".equals(method)||"PUT".equals(method)) {
            if (request.body() != null) {
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("url：").append(request.url().toString()).append("\n");
                    MediaType contentType = request.body().contentType();
                    if (contentType != null) {
                        stringBuilder.append("type：").append(contentType.toString()).append("\n");
                        if ("application/x-www-form-urlencoded".equals(contentType.toString())) {
                            FormBody formBody = (FormBody) request.body();
                            for (int i = 0; i < formBody.size(); ++i) {
                                //取出表单请求参数
                                stringBuilder.append(formBody.name(i))
                                        .append("：")
                                        .append(formBody.value(i))
                                        .append("\n");
                            }
                        }
                    }
                    Log.e(method, stringBuilder.toString());
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    Log.e(method, "类型不是表单");
                }
            }
        }

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();

        //if (!HttpEngine.hasBody(response)) {
        if (!HttpHeaders.hasBody(response)) {
            //END HTTP
        } else if (bodyEncoded(response.headers())) {
            //HTTP (encoded body omitted)
        } else {
            BufferedSource source = responseBody.source();
            // Buffer the entire body.
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    //Couldn't decode the response body; charset is likely malformed.
                    return response;
                }
            }

            if (!isPlaintext(buffer)) {
                Log.e("<-- END HTTP (binary " + buffer.size(), "-byte body omitted)");
                return response;
            }

            if (contentLength != 0) {
                String result = buffer.clone().readString(charset);
                //获取到response的body的string字符串
                Log.e("请求结果", result);
            }
        }
        return response;
    }

    private static boolean isPlaintext(Buffer buffer) throws EOFException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            // Truncated UTF-8 sequence.
            return false;
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}
