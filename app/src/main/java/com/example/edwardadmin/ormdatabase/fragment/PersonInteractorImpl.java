package com.example.edwardadmin.ormdatabase.fragment;

import android.content.Context;
import android.util.Log;

import com.example.edwardadmin.ormdatabase.database.PersonInfoOperationTask;
import com.example.edwardadmin.ormdatabase.database.PersonTokenOperationTask;
import com.example.edwardadmin.ormdatabase.entity.PersonInfo;
import com.example.edwardadmin.ormdatabase.entity.PersonToken;

import java.util.ArrayList;

public class PersonInteractorImpl implements IPersonInteractor {

    private PersonInfoOperationTask personInfoOperationTask;
    private PersonTokenOperationTask personTokenOperationTask;

    public PersonInteractorImpl(Context mContext) {
        personInfoOperationTask = new PersonInfoOperationTask(mContext);
        personTokenOperationTask = new PersonTokenOperationTask(mContext);
    }

    @Override
    public void loadPersonData(onLoadingFinishedListener loadingFinishedListener) {
        ArrayList<PersonInfo> personInfoArrayList = personInfoOperationTask.queryPersonData();
        Log.d("jiangsu", "loadPersonData personInfoArrayList = " + personInfoArrayList);
        if (personInfoArrayList != null) {
            loadingFinishedListener.loadPersonDataSuccess(personInfoArrayList);
        } else {
            loadingFinishedListener.loadPersonDataError();
        }
    }

    @Override
    public void addPersonData(PersonInfo personInfo, onLoadingFinishedListener loadingFinishedListener) {
        boolean insert = personInfoOperationTask.insertPersonData(personInfo);
        if (insert) {
            loadingFinishedListener.addPersonDataSuccess();
        } else {
            loadingFinishedListener.addPersonDataError();
        }
    }

    @Override
    public void addPersonData(PersonInfo personInfo, PersonToken personToken, onLoadingFinishedListener loadingFinishedListener) {
        //insert person
        boolean insertPersonInfo = personInfoOperationTask.insertPersonData(personInfo);

        //insert personToken
        boolean insertPersonToken = false;
        insertPersonToken = personTokenOperationTask.insertPersonTokenData(personToken, personInfo);

        if (insertPersonInfo && insertPersonToken) {
            loadingFinishedListener.addPersonDataSuccess();
        } else {
            loadingFinishedListener.addPersonDataError();
        }
    }

    @Override
    public void updateOrAddPersonData(String personNumber, PersonInfo personInfo, PersonToken personToken, onLoadingFinishedListener loadingFinishedListener) {
        //get personInfo first,then update it to personToken table.
        PersonInfo info = personInfoOperationTask.queryPersonDataByPersonNumber(personNumber);
        Log.d("jiangsu", "info = " + info);
        if (info == null) {
            //add personInfo
            addPersonData(personInfo, personToken, loadingFinishedListener);
        } else {
            //update personInfo
            boolean insertPersonToken = personTokenOperationTask.insertPersonTokenData(personToken, info);
            if (insertPersonToken) {
                loadingFinishedListener.addPersonDataSuccess();
            } else {
                loadingFinishedListener.addPersonDataError();
            }
        }
    }
}
