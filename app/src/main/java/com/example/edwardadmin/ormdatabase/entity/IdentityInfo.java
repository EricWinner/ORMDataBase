package com.example.edwardadmin.ormdatabase.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "t_identity")
public class IdentityInfo implements Serializable {

    @DatabaseField(columnName = "id", generatedId = true)
    public int id;

    @DatabaseField(columnName = "identityName")
    private String identityName;

    @DatabaseField(columnName = "identityGender")
    private String identityGender;

    @DatabaseField(columnName = "identityNumber")
    private String identityNumber;

    @DatabaseField(columnName = "identityAddress")
    private String identityAddress;

    @DatabaseField(columnName = "identityToken")
    private long identityToken;

    public IdentityInfo() {
    }

    public IdentityInfo(String identityName, String identityGender, String identityNumber, String identityAddress, long identityToken) {
        this.identityName = identityName;
        this.identityGender = identityGender;
        this.identityNumber = identityNumber;
        this.identityAddress = identityAddress;
        this.identityToken = identityToken;
    }

    public String getIdentityName() {
        return identityName;
    }

    public String getIdentityGender() {
        return identityGender;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public String getIdentityAddress() {
        return identityAddress;
    }

    public long getIdentityToken() {
        return identityToken;
    }

    @Override
    public String toString() {
        return "IdentityInfo{" +
                "id=" + id +
                ", identityName='" + identityName + '\'' +
                ", identityGender='" + identityGender + '\'' +
                ", identityNumber='" + identityNumber + '\'' +
                ", identityAddress='" + identityAddress + '\'' +
                ", identityToken=" + identityToken +
                '}';
    }
}
