package com.example.edwardadmin.ormdatabase.database;

import android.content.Context;

import com.j256.ormlite.android.AndroidDatabaseConnection;

public class BaseOperationTask {

    public DetailDataOpenHelper detailDataOpenHelper;
    public AndroidDatabaseConnection androidDatabaseConnection;

    private static BaseOperationTask instance;

    public BaseOperationTask(Context mContext) {
        if (detailDataOpenHelper == null) {
            detailDataOpenHelper = DetailDataOpenHelper.getInstance(mContext);
            androidDatabaseConnection = new AndroidDatabaseConnection(detailDataOpenHelper.getWritableDatabase(), true);
        }
    }

    public static BaseOperationTask getInstance(Context mContext) {
        if (instance == null) {
            synchronized (BaseOperationTask.class) {
                if (instance == null) {
                    instance = new BaseOperationTask(mContext);
                }
            }
        }
        return instance;
    }

}
