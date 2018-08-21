package com.example.edwardadmin.ormdatabase.http;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtil {

    //"application/x-www-form-urlencoded"，是默认的MIME内容编码类型，一般可以用于所有的情况，但是在传输比较大的二进制或者文本数据时效率低。
    //这时候应该使用"multipart/form-data",如上传文件或者二进制数据和非ASCII数据。
    public static final MediaType MEDIA_TYPE_NORMAL_FORM = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");

    //既可以提交普通键值对，也可以提交(多个)文件键值对。
    public static final MediaType MEDIA_TYPE_MULTIPART_FORM = MediaType.parse("multipart/form-data;charset=utf-8");

    //只能提交二进制，而且只能提交一个二进制，如果提交文件的话，只能提交一个文件,后台接收参数只能有一个，而且只能是流（或者字节数组）
    public static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");
    public static final MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain;charset=utf-8");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");

    private static OkHttpUtil instance;
    private OkHttpClient okHttpClient;

    private OkHttpUtil() {
        okHttpClient = new OkHttpClient();
    }

    public static OkHttpUtil getInstance() {
        if (instance == null) {
            synchronized (OkHttpUtil.class) {
                if (instance == null) {
                    instance = new OkHttpUtil();
                }
            }
        }
        return instance;
    }

    //async get request.
    public void asyncGetRequest(Uri imageUri, final OkHttpResultCallback okHttpResultCallback) {
        final Request request = new Request.Builder().get()
                .url(imageUri.toString())
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("jiangsu", "asyncGetRequest onFailure e = " + e);
                if (okHttpResultCallback != null) {
                    okHttpResultCallback.onCallbackError();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("jiangsu", "asyncGetRequest onResponse ");
                if (okHttpResultCallback != null) {
                    okHttpResultCallback.onCallbackSuccess(response);
                }
            }
        });
    }

    //synchronized get request.
    public void synchronizedGetRequest(Uri imageUri, final OkHttpResultCallback okHttpResultCallback) {
        final Request synchronizedRequst = new Request.Builder()
                .url(imageUri.toString())
                .build();

        try {
            Response response = okHttpClient.newCall(synchronizedRequst).execute();
            if (response.isSuccessful()) {
                if (okHttpResultCallback != null) {
                    okHttpResultCallback.onCallbackSuccess(response);
                }
            } else {
                if (okHttpResultCallback != null) {
                    okHttpResultCallback.onCallbackError();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public interface OkHttpResultCallback {
        void onCallbackSuccess(Response response);
        void onCallbackError();
    }

}
