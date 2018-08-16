package com.example.edwardadmin.ormdatabase.ui;

/**
 * Created by EdwardAdmin on 2018/8/13.
 */

public interface IDetailInteractor {

    interface onLoadingFinishedListener {
        void loadFragmentSuccess();
        void loadFragmentError();
    }

    void changeFragment(int selection, onLoadingFinishedListener onLoadingFinishedListener);
}
