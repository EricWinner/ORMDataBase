package com.example.edwardadmin.ormdatabase.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.edwardadmin.ormdatabase.config.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapDecodeUtil {

    public static Bitmap decodeSampledBitmapFromFile(String filePath, int requestWidth, int requestHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        FileInputStream is = null;
        Bitmap bitmap = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return bitmap;
            }
            is = new FileInputStream(filePath);
            BitmapFactory.decodeFileDescriptor(is.getFD(), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        options.inSampleSize = calculateInSampleSize(options, requestWidth, requestHeight);
        options.inJustDecodeBounds = false;
        try {
            bitmap = BitmapFactory.decodeFileDescriptor(is.getFD(), null, options);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return bitmap;
    }

    public static Bitmap decodeSampledBitmapFromBytes(byte[] bytes, int requestWidth, int requestHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        Bitmap bitmap = null;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        options.inSampleSize = calculateInSampleSize(options, requestWidth, requestHeight);

        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int requestWidth, int requestHeight) {
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;

        if (width > requestWidth || height > requestHeight) {
            final int halfWidth = width / 2;
            final int halfHeight = height / 2;
            while ((halfHeight / inSampleSize) >= requestHeight && (halfWidth / inSampleSize) > requestWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

}
