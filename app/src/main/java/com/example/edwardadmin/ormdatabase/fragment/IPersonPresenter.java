package com.example.edwardadmin.ormdatabase.fragment;

import com.example.edwardadmin.ormdatabase.entity.PersonInfo;
import com.example.edwardadmin.ormdatabase.entity.PersonToken;

import java.util.ArrayList;

public interface IPersonPresenter {

    void loadPersonData();

    //old table
    void addPersonData(PersonInfo personInfo);

    //add new table
    void addPersonData(PersonInfo personInfo, PersonToken personToken);

    //add for test
    void updateOrAddPersonData(String personNumber, PersonInfo personInfo, PersonToken personToken);
}
