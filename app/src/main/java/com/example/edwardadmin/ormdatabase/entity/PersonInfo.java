package com.example.edwardadmin.ormdatabase.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;

@DatabaseTable(tableName = "t_person")
public class PersonInfo implements Serializable {

    @DatabaseField(columnName = "id", generatedId = true)
    public int id;

    @DatabaseField(columnName = "personName")
    private String personName;

    @DatabaseField(columnName = "personSex")
    private String personSex;

    @DatabaseField(columnName = "personAge")
    private String personAge;

    @DatabaseField(columnName = "personHeight")
    private String personHeight;

    @DatabaseField(columnName = "personNative")
    private String personNative;

    @DatabaseField(columnName = "personNumber")
    private String personNumber;

    @DatabaseField(columnName = "personImage")
    private String personImage;

    @ForeignCollectionField(eager = true)
    public ForeignCollection<PersonToken> personTokens;

    public PersonInfo() {
    }

    public PersonInfo(String personName, String personSex, String personAge, String personHeight, String personNative, String personNumber) {
        this.personName = personName;
        this.personSex = personSex;
        this.personAge = personAge;
        this.personHeight = personHeight;
        this.personNumber = personNumber;
        this.personNative = personNative;
    }

    public PersonInfo(String personName, String personSex, String personAge, String personHeight, String personNative, String personNumber, String personImage) {
        this.personName = personName;
        this.personSex = personSex;
        this.personAge = personAge;
        this.personHeight = personHeight;
        this.personNumber = personNumber;
        this.personNative = personNative;
        this.personImage = personImage;
    }

    public String getPersonName() {
        return personName;
    }

    public String getPersonSex() {
        return personSex;
    }

    public String getPersonAge() {
        return personAge;
    }

    public String getPersonHeight() {
        return personHeight;
    }

    public String getPersonNative() {
        return personNative;
    }

    public String getPersonNumber() {
        return personNumber;
    }

    public String getPersonImage() {
        return personImage;
    }

    @Override
    public String toString() {
        return "PersonInfo{" +
                "id=" + id +
                ", personName='" + personName + '\'' +
                ", personSex='" + personSex + '\'' +
                ", personAge='" + personAge + '\'' +
                ", personHeight='" + personHeight + '\'' +
                ", personNative='" + personNative + '\'' +
                ", personNumber='" + personNumber + '\'' +
                ", personImage='" + personImage + '\'' +
                ", personTokens=" + personTokens +
                '}';
    }
}
