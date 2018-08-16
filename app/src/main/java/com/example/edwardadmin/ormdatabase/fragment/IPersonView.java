package com.example.edwardadmin.ormdatabase.fragment;

import com.example.edwardadmin.ormdatabase.entity.PersonInfo;

import java.util.ArrayList;

public interface IPersonView {

    void addPersonDataSuccess();
    void addPersonDataError();
    void loadPersonDataSuccess(ArrayList<PersonInfo> personInfoArrayList);
    void loadPersonDataError();
}
