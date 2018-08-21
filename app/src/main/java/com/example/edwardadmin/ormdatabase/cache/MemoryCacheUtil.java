package com.example.edwardadmin.ormdatabase.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

public class MemoryCacheUtil {

    private LruCache<String, Bitmap> mLruCache;
    private static MemoryCacheUtil instance;

    private MemoryCacheUtil() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;

        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }

    public static MemoryCacheUtil getInstance() {
        if (instance == null) {
            synchronized (LocalCacheUtil.class) {
                if (instance == null) {
                    instance = new MemoryCacheUtil();
                }
            }
        }
        return instance;
    }

    public Bitmap getBitmapFromMemory(String imagePath) {
        return mLruCache.get(imagePath);
    }

    public void setBitmapToMemory(String imagePath, Bitmap bitmap) {
        if (getBitmapFromMemory(imagePath) == null) {
            mLruCache.put(imagePath, bitmap);
        }
    }

    //clear memory cache
    public void clearMemoryCache(String imagePath) {
        if (getBitmapFromMemory(imagePath) != null) {
            mLruCache.remove(imagePath);
        }
    }
}
