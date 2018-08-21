package com.example.edwardadmin.ormdatabase.cache;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.example.edwardadmin.ormdatabase.config.Constant;
import com.example.edwardadmin.ormdatabase.http.OkHttpUtil;
import com.example.edwardadmin.ormdatabase.util.BitmapDecodeUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Response;

public class NetworkCacheUtil {

    private MemoryCacheUtil memoryCacheUtil;
    private LocalCacheUtil localCacheUtil;

    public NetworkCacheUtil() {
        this.memoryCacheUtil = MemoryCacheUtil.getInstance();
        this.localCacheUtil = LocalCacheUtil.getInstance();
    }

    public void downloadImageFromNetWork(final Uri imageUri, final ImageView imageView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                doGetRequest(imageUri, imageView);
            }
        }).start();
    }

    private void doGetRequest(final Uri imageUri, final ImageView imageView) {
        OkHttpUtil.getInstance().asyncGetRequest(imageUri, new OkHttpUtil.OkHttpResultCallback() {
            @Override
            public void onCallbackSuccess(Response response) {
                BufferedOutputStream bufferedOutputStream = null;
                FileOutputStream fileOutputStream = null;
                File file = null;
                String filePath = null;
                try {
                    byte[] bytes = response.body().bytes();
                    File fileDir = new File(Constant.SYSTEM_DATABASE_IMAGE_PATH);
                    if (!fileDir.exists()) {
                        fileDir.mkdirs();
                    }
                    //Download image, local file path, /sdcard/database/image/...jpg
                    file = new File(Constant.SYSTEM_DATABASE_IMAGE_PATH + imageUri.getLastPathSegment());
                    filePath = file.getPath();
                    fileOutputStream = new FileOutputStream(file);
                    bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                    bufferedOutputStream.write(bytes);

                    new NetWorkDecodeAsyncTask().execute(filePath, imageView, imageUri.getLastPathSegment());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (bufferedOutputStream != null) {
                        try {
                            bufferedOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onCallbackError() {
                Log.d("edward", "doGetRequest onCallbackError !!");
            }
        });
    }

    private class NetWorkDecodeAsyncTask extends AsyncTask<Object, Void, Bitmap> {

        private String localFilePath;
        private ImageView imageView;
        private String fileName;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(Object... objects) {
            localFilePath = (String) objects[0];
            imageView = (ImageView) objects[1];
            fileName = (String) objects[2];
            return decodeImage(localFilePath);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                String cacheFilePath = Constant.SYSTEM_DATABASE_CACHE_PATH + fileName;
                Log.d("edward", "NetWorkDecodeAsyncTask set bitmap from network cacheFilePath = " + cacheFilePath);
                localCacheUtil.setBitmapToLocal(cacheFilePath, bitmap);
                memoryCacheUtil.setBitmapToMemory(cacheFilePath, bitmap);
            }
        }
    }

    private Bitmap decodeImage(String localFilePath) {
        return BitmapDecodeUtil.decodeSampledBitmapFromFile(localFilePath, 150, 150);
    }

}
