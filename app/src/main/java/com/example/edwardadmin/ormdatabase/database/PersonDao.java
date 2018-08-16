package com.example.edwardadmin.ormdatabase.database;

import android.content.Context;

import com.example.edwardadmin.ormdatabase.entity.PersonInfo;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class PersonDao {

    private Dao<PersonInfo,Integer> personInfoDao;

    public Dao<PersonInfo, Integer> getPersonInfoDao(Context mContext) {
        if (personInfoDao == null) {
            personInfoDao = createPersonDao(mContext);
        }
        return personInfoDao;
    }

    private Dao createPersonDao(Context mContext) {
        try {
            return DetailDataOpenHelper.getInstance(mContext).getDao(PersonInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
