package com.example.edwardadmin.ormdatabase.database;

import android.content.Context;

import com.example.edwardadmin.ormdatabase.entity.PersonInfo;
import com.example.edwardadmin.ormdatabase.entity.PersonToken;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;

public class PersonTokenOperationTask extends BaseOperationTask implements IPersonTokenOperation {

    private Dao<PersonToken, Integer> personTokenDao;
    private Dao<PersonInfo, Integer> personInfoDao;
    private ArrayList<PersonToken> mAllPersonData;

    public PersonTokenOperationTask(Context mContext) {
        super(mContext);
        personTokenDao = new PersonTokenDao().getPersonTokenDao(mContext);
        personInfoDao = new PersonDao().getPersonInfoDao(mContext);
    }

    @Override
    public boolean insertPersonTokenData(PersonToken personToken, PersonInfo personInfo) {
        Savepoint savepoint = null;
        try {
            savepoint = androidDatabaseConnection.setSavePoint("start");
            androidDatabaseConnection.setAutoCommit(false);
            //query personInfo:
            personToken.personInfo = personInfo;
            int result = personTokenDao.create(personToken);
            androidDatabaseConnection.commit(savepoint);
            if (result != -1) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                androidDatabaseConnection.rollback(savepoint);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public ArrayList<PersonToken> queryPersonTokenData() {
        mAllPersonData = new ArrayList<>();
        Savepoint savepoint = null;
        try {
            savepoint = androidDatabaseConnection.setSavePoint("start");
            androidDatabaseConnection.setAutoCommit(false);
            mAllPersonData = (ArrayList<PersonToken>) personTokenDao.queryBuilder().orderBy("tokenId" +
                    "", false).query();
            androidDatabaseConnection.commit(savepoint);
            return mAllPersonData;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                androidDatabaseConnection.rollback(savepoint);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public PersonToken queryPersonTokenDataByNumber() {
        return null;
    }

}
