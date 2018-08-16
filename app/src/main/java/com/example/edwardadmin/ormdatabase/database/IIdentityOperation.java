package com.example.edwardadmin.ormdatabase.database;

import com.example.edwardadmin.ormdatabase.entity.IdentityInfo;

import java.util.ArrayList;

public interface IIdentityOperation {

    boolean insertIdentityData(IdentityInfo identityInfo);
    ArrayList<IdentityInfo> queryIdentityData();
}
