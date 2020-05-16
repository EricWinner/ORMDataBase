package com.example.edwardadmin.ormdatabase.database;

import android.content.Context;
import android.util.Log;

import com.example.edwardadmin.ormdatabase.entity.PersonInfo;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;

public class PersonInfoOperationTask extends BaseOperationTask implements IPersonOperation {

    private Dao<PersonInfo, Integer> personInfoDao;
    private ArrayList<PersonInfo> mAllPersonData;

    public PersonInfoOperationTask(Context mContext) {
        super(mContext);
        personInfoDao = new PersonDao().getPersonInfoDao(mContext);
    }

    @Override
    public boolean insertPersonData(PersonInfo personInfo) {
        Savepoint savepoint = null;
        try {
            savepoint = androidDatabaseConnection.setSavePoint("start");
            androidDatabaseConnection.setAutoCommit(false);
            int startResult = personInfoDao.create(personInfo);
            androidDatabaseConnection.commit(savepoint);
            if (startResult != -1) {
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
    public boolean deletePersonData(PersonInfo personInfo) {
        Savepoint savePoint = null;
        try {
            savePoint = androidDatabaseConnection.setSavePoint("start");
            androidDatabaseConnection.setAutoCommit(false);
            int deleteResult = personInfoDao.delete(personInfo);
            androidDatabaseConnection.commit(savePoint);
            if (deleteResult != -1) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                androidDatabaseConnection.rollback(savePoint);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public ArrayList<PersonInfo> queryPersonData() {
        mAllPersonData = new ArrayList<>();
        Savepoint savepoint = null;
        try {
            savepoint = androidDatabaseConnection.setSavePoint("start");
            androidDatabaseConnection.setAutoCommit(false);
            mAllPersonData = (ArrayList<PersonInfo>) personInfoDao.queryBuilder().orderBy("id", false).query();
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
    public PersonInfo queryPersonDataByPersonNumber(String personNumber) {
        PersonInfo personInfo = null;
        Savepoint savepoint = null;
        try {
            savepoint = androidDatabaseConnection.setSavePoint("start");
            ArrayList<PersonInfo> list = (ArrayList<PersonInfo>) personInfoDao.queryForAll();
            for(int index = 0; index < list.size(); index ++) {
                PersonInfo info1 = list.get(index);
                if (info1.getPersonNumber().equals(personNumber)) {
                    personInfo = info1;
                }
            }
            androidDatabaseConnection.setAutoCommit(false);
            mAllPersonData = (ArrayList<PersonInfo>) personInfoDao.queryBuilder().orderBy("id", false).query();
            androidDatabaseConnection.commit(savepoint);
            return personInfo;
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
}
