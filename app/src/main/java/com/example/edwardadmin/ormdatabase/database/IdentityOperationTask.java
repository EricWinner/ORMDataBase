package com.example.edwardadmin.ormdatabase.database;

import android.content.Context;

import com.example.edwardadmin.ormdatabase.entity.IdentityInfo;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;

public class IdentityOperationTask extends BaseOperationTask implements IIdentityOperation {

    private Dao<IdentityInfo, Integer> identityInfoDao;
    private ArrayList<IdentityInfo> mAllIdentityData;

    public IdentityOperationTask(Context mContext) {
        super(mContext);
        identityInfoDao = new IdentityDao().getIdentityInfoDao(mContext);
    }

    @Override
    public boolean insertIdentityData(IdentityInfo identityInfo) {
        Savepoint savepoint = null;
        try {
            savepoint = androidDatabaseConnection.setSavePoint("start");
            androidDatabaseConnection.setAutoCommit(false);
            int result = identityInfoDao.create(identityInfo);
            if (result != -1) {
                if (identityChangeListener != null) {
                    identityChangeListener.identityDataChange();
                }
            }
            androidDatabaseConnection.commit(savepoint);
            return true;
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
    public ArrayList<IdentityInfo> queryIdentityData() {
        mAllIdentityData = new ArrayList<>();
        Savepoint savepoint = null;
        try {
            savepoint = androidDatabaseConnection.setSavePoint("start");
            androidDatabaseConnection.setAutoCommit(false);
            mAllIdentityData = (ArrayList<IdentityInfo>) identityInfoDao.queryBuilder().orderBy("personToken", false).query();
            androidDatabaseConnection.commit(savepoint);
            return mAllIdentityData;
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

    public interface IdentityChangeListener {
        void identityDataChange();
    }

    private IdentityChangeListener identityChangeListener;

    public void setIdentityDataChangeListener(IdentityChangeListener identityChangeListener) {
        this.identityChangeListener = identityChangeListener;
    }
}
