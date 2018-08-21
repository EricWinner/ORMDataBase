package com.example.edwardadmin.ormdatabase.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.example.edwardadmin.ormdatabase.config.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Handler;

public class CacheUtil {

    private static CacheUtil instance;
    private MemoryCacheUtil memoryCacheUtil;
    private LocalCacheUtil localCacheUtil;
    private NetworkCacheUtil networkCacheUtil;

    private CacheUtil() {
        this.memoryCacheUtil = MemoryCacheUtil.getInstance();
        this.localCacheUtil = LocalCacheUtil.getInstance();
        this.networkCacheUtil = new NetworkCacheUtil();
    }

    public static CacheUtil getInstance() {
        if (instance == null) {
            synchronized (CacheUtil.class) {
                if (instance == null) {
                    instance = new CacheUtil();
                }
            }
        }
        return instance;
    }

    public void putBitmapIntoCache(String filePath, Bitmap bitmap) {
        //1.将图片的字节数组写入到内存中
        memoryCacheUtil.setBitmapToMemory(filePath, bitmap);
        //2.将图片保存到文件中
        localCacheUtil.setBitmapToLocal(filePath, bitmap);
    }

    public void displayImage(Uri imageUri, ImageView imageView, String personNumber) {
        String cacheFilePath =  Constant.SYSTEM_DATABASE_CACHE_PATH + imageUri.getLastPathSegment();
        //1.先从缓存中取bitmap
        Bitmap bitmap;
        bitmap = memoryCacheUtil.getBitmapFromMemory(cacheFilePath);
        if (bitmap == null) {
            //2.再从缓存文件中取bitmap
            bitmap = localCacheUtil.getBitmapFromLocal(cacheFilePath);

            //3.bitmap存在文件中，但是没有在内存中，所以此处添加到内存中
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                memoryCacheUtil.setBitmapToMemory(cacheFilePath, bitmap);
                return;
            }

            //4.download image from network.
            if (bitmap == null) {
                networkCacheUtil.downloadImageFromNetWork(imageUri, imageView);
            }
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

}
