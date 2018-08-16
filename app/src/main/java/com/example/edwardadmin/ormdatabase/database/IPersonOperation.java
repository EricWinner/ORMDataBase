package com.example.edwardadmin.ormdatabase.database;

import com.example.edwardadmin.ormdatabase.entity.PersonInfo;

import java.util.ArrayList;

public interface IPersonOperation {

    boolean insertPersonData(PersonInfo personInfo);
    boolean deletePersonData(PersonInfo personInfo);
    ArrayList<PersonInfo> queryPersonData();
    PersonInfo queryPersonDataByPersonNumber(String personNumber);
}
