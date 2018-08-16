package com.example.edwardadmin.ormdatabase.ui;

/**
 * Created by EdwardAdmin on 2018/8/13.
 */

public class DetailPresenterImpl implements IDetailPresenter, IDetailInteractor.onLoadingFinishedListener{

    private IDetailView iDetailView;
    private IDetailInteractor iDetailInteractor;

    public DetailPresenterImpl(IDetailView iDetailView, IDetailInteractor iDetailInteractor) {
        this.iDetailView = iDetailView;
        this.iDetailInteractor = iDetailInteractor;
    }

    @Override
    public void changeFragment(int selection) {
        iDetailInteractor.changeFragment(selection, this);
    }

    @Override
    public void loadFragmentSuccess() {
        iDetailView.changeFragmentSuccess();
    }

    @Override
    public void loadFragmentError() {
        iDetailView.changeFragmentError();
    }
}
