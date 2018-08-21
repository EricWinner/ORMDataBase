package com.example.edwardadmin.ormdatabase.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.example.edwardadmin.ormdatabase.config.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class LocalCacheUtil {
    private static LocalCacheUtil instance;

    private LocalCacheUtil() {
    }

    public static LocalCacheUtil getInstance() {
        if (instance == null) {
            synchronized (LocalCacheUtil.class) {
                if (instance == null) {
                    instance = new LocalCacheUtil();
                }
            }
        }
        return instance;
    }

    //从文件终获取file，然后生成bitmap
    public Bitmap getBitmapFromLocal(String imagePath) {
        String fileName;
        try {
            //根据MD5Encoder将imagePath生成fileName
            fileName = MD5Encoder.encode(imagePath);
            Log.d("edward", "getBitmapFromLocal fileName = " + fileName);
            File file = new File(Constant.SYSTEM_DATABASE_CACHE_PATH, fileName);
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //将bitmap保存成文件
    public void setBitmapToLocal(String imagePath, Bitmap bitmap) {
        FileOutputStream fos = null;
        try {
            //根据MD5Encoder将imagePath生成fileName
            String fileName = MD5Encoder.encode(imagePath);
            Log.d("edward", "setBitmapToLocal fileName = " + fileName);
            File file = new File(Constant.SYSTEM_DATABASE_CACHE_PATH, fileName);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //clear local file cache
    public boolean clearLocalCache(String imagePath) {
        String fileName = null;
        try {
            fileName = MD5Encoder.encode(imagePath);
            Log.d("edward", "clearLocalCache fileName = " + fileName);
            File file = new File(Constant.SYSTEM_DATABASE_CACHE_PATH, fileName);
            if (!file.exists()) {
                return false;
            } else {
                return file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
