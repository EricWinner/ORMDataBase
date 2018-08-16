package com.example.edwardadmin.ormdatabase.fragment;

import com.example.edwardadmin.ormdatabase.entity.PersonInfo;
import com.example.edwardadmin.ormdatabase.entity.PersonToken;

import java.util.ArrayList;

public interface IPersonInteractor {

    interface onLoadingFinishedListener {
        void loadPersonDataSuccess(ArrayList<PersonInfo> personInfoArrayList);
        void loadPersonDataError();
        void addPersonDataSuccess();
        void addPersonDataError();
    }

    void loadPersonData(onLoadingFinishedListener loadingFinishedListener);
    void addPersonData(PersonInfo personInfo, onLoadingFinishedListener loadingFinishedListener);

    //add new table
    void addPersonData(PersonInfo personInfo, PersonToken personToken,onLoadingFinishedListener loadingFinishedListener);

    //update for test
    void updateOrAddPersonData(String personNumber, PersonInfo personInfo,PersonToken personToken, onLoadingFinishedListener loadingFinishedListener);
}
