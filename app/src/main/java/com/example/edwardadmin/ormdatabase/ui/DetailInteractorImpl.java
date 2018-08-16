package com.example.edwardadmin.ormdatabase.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.example.edwardadmin.ormdatabase.R;
import com.example.edwardadmin.ormdatabase.fragment.IdentityFragment;
import com.example.edwardadmin.ormdatabase.fragment.PersonFragment;

/**
 * Created by EdwardAdmin on 2018/8/13.
 */

public class DetailInteractorImpl implements IDetailInteractor {

    public static final int PERSON_PAGE = 0x1;
    public static final int IDENTITY_PAGE = 0x2;

    private PersonFragment personFragment;
    private IdentityFragment identityFragment;
    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    public DetailInteractorImpl(Activity activity) {
        mFragmentManager = activity.getFragmentManager();
    }

    @Override
    public void changeFragment(int selection, onLoadingFinishedListener onLoadingFinishedListener) {
        setTabSelection(selection, onLoadingFinishedListener);
    }

    public void setTabSelection(int selection, onLoadingFinishedListener onLoadingFinishedListener) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (selection) {
            case PERSON_PAGE:
                if (personFragment == null) {
                    personFragment = new PersonFragment();
                    if (mFragment != null) {
                        transaction.hide(mFragment);
                    }
                    mFragment = personFragment;
                    transaction.add(R.id.detail_framelayout, personFragment);
                } else {
                    transaction.show(personFragment);
                }
                onLoadingFinishedListener.loadFragmentSuccess();
                break;
            case IDENTITY_PAGE:
                if (identityFragment == null) {
                    identityFragment = new IdentityFragment();
                    if (mFragment != null) {
                        transaction.hide(mFragment);
                    }
                    mFragment = identityFragment;
                    transaction.add(R.id.detail_framelayout, identityFragment);
                } else {
                    transaction.show(identityFragment);
                }
                onLoadingFinishedListener.loadFragmentSuccess();
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (personFragment != null) {
            transaction.hide(personFragment);
        }
        if (identityFragment != null) {
            transaction.hide(identityFragment);
        }
    }
}