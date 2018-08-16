package com.example.edwardadmin.ormdatabase.fragment;

import com.example.edwardadmin.ormdatabase.entity.PersonInfo;
import com.example.edwardadmin.ormdatabase.entity.PersonToken;

import java.util.ArrayList;

public class PersonPresenterImpl implements IPersonPresenter, IPersonInteractor.onLoadingFinishedListener {

    private IPersonView iPersonView;
    private IPersonInteractor iPersonInteractor;

    public PersonPresenterImpl(IPersonView iPersonView, IPersonInteractor iPersonInteractor) {
        this.iPersonView = iPersonView;
        this.iPersonInteractor = iPersonInteractor;
    }

    @Override
    public void loadPersonData() {
        iPersonInteractor.loadPersonData(this);
    }

    @Override
    public void addPersonData(PersonInfo personInfo) {
        iPersonInteractor.addPersonData(personInfo, this);
    }

    @Override
    public void addPersonData(PersonInfo personInfo, PersonToken personToken) {
        iPersonInteractor.addPersonData(personInfo, personToken, this);
    }

    @Override
    public void updateOrAddPersonData(String personNumber, PersonInfo personInfo, PersonToken personToken) {
        iPersonInteractor.updateOrAddPersonData(personNumber, personInfo, personToken, this);
    }

    @Override
    public void loadPersonDataSuccess(ArrayList<PersonInfo> personInfoArrayList) {
        iPersonView.loadPersonDataSuccess(personInfoArrayList);
    }

    @Override
    public void loadPersonDataError() {
        iPersonView.loadPersonDataError();
    }

    @Override
    public void addPersonDataSuccess() {
        iPersonView.addPersonDataSuccess();
    }

    @Override
    public void addPersonDataError() {
        iPersonView.addPersonDataError();
    }
}
