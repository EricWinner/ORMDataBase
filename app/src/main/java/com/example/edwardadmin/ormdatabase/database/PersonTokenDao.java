package com.example.edwardadmin.ormdatabase.database;

import android.content.Context;

import com.example.edwardadmin.ormdatabase.entity.PersonToken;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class PersonTokenDao {

    private Dao<PersonToken, Integer> personTokenDao;

    public Dao<PersonToken, Integer> getPersonTokenDao(Context mContext) {
        if (personTokenDao == null) {
            personTokenDao = createPersonTokenDao(mContext);
        }
        return personTokenDao;
    }

    private Dao createPersonTokenDao(Context mContext) {
        try {
            return DetailDataOpenHelper.getInstance(mContext).getDao(PersonToken.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
