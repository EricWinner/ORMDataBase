package com.example.edwardadmin.ormdatabase.database;

import com.example.edwardadmin.ormdatabase.entity.PersonInfo;
import com.example.edwardadmin.ormdatabase.entity.PersonToken;

import java.util.ArrayList;

public interface IPersonTokenOperation {

    boolean insertPersonTokenData(PersonToken personToken, PersonInfo personInfo);
    ArrayList<PersonToken> queryPersonTokenData();
    PersonToken queryPersonTokenDataByNumber();
}
