package com.example.edwardadmin.ormdatabase.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "t_personToken")
public class PersonToken {

    @DatabaseField(columnName = "tokenId", generatedId = true)
    public int tokenId;

    @DatabaseField(columnName = "dataToken")
    private long dataToken;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true)
    public PersonInfo personInfo;

    public PersonToken() {
    }

    public PersonToken(long dataToken) {
        this.dataToken = dataToken;
    }

    public long getDataToken() {
        return dataToken;
    }

    @Override
    public String toString() {
        return "PersonToken{" +
                "tokenId=" + tokenId +
                ", dataToken=" + dataToken +
                ", personInfo=" + personInfo +
                '}';
    }
}
