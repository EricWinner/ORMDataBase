package com.example.edwardadmin.ormdatabase.database;

import android.content.Context;

import com.example.edwardadmin.ormdatabase.entity.IdentityInfo;
import com.example.edwardadmin.ormdatabase.entity.PersonInfo;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class IdentityDao {

    private Dao<IdentityInfo, Integer> identityInfoDao;

    public Dao<IdentityInfo, Integer> getIdentityInfoDao(Context mContext) {
        if (identityInfoDao == null) {
            identityInfoDao = createIdentityDao(mContext);
        }
        return identityInfoDao;
    }

    private Dao createIdentityDao(Context mContext) {
        try {
            return DetailDataOpenHelper.getInstance(mContext).getDao(IdentityInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
