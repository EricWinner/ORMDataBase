package com.example.edwardadmin.ormdatabase.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.example.edwardadmin.ormdatabase.entity.IdentityInfo;
import com.example.edwardadmin.ormdatabase.entity.PersonInfo;
import com.example.edwardadmin.ormdatabase.entity.PersonToken;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DetailDataOpenHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_PATH = Environment.getExternalStorageDirectory() + "/detail.db";
    public static final int DATABASE_VERSION = 3;

    private static DetailDataOpenHelper instance;

    public static DetailDataOpenHelper getInstance(Context mContext) {
        if (instance == null) {
            synchronized (DetailDataOpenHelper.class) {
                if (instance == null) {
                    instance = new DetailDataOpenHelper(mContext);
                }
            }
        }
        return instance;
    }

    public DetailDataOpenHelper(Context context) {
        super(context, DATABASE_PATH, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        Log.d("jiangsu", "DetailDataOpenHelper onCreate ");
        try {
            TableUtils.createTable(connectionSource, PersonInfo.class);
            TableUtils.createTable(connectionSource, IdentityInfo.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.d("jiangsu", "DetailDataOpenHelper onUpgrade oldVersion = " + oldVersion + ",newVersion = " + newVersion);
        if (oldVersion < 2) {
            try {
                //version is 2, add PersonToken table.
                TableUtils.dropTable(connectionSource, IdentityInfo.class, false);

                TableUtils.createTable(connectionSource, PersonToken.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DetailDataBaseUtil.upgradeTable(sqLiteDatabase, connectionSource, PersonInfo.class, DetailDataBaseUtil.OPERATION_TYPE.ADD);
        }
        if (oldVersion < 3) {
            try {
                TableUtils.dropTable(connectionSource, IdentityInfo.class, false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DetailDataBaseUtil.upgradeTable(sqLiteDatabase, connectionSource, PersonInfo.class, DetailDataBaseUtil.OPERATION_TYPE.ADD);
        }
        onCreate(sqLiteDatabase, connectionSource);
    }
}
