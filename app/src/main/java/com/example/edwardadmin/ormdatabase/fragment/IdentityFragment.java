package com.example.edwardadmin.ormdatabase.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.edwardadmin.ormdatabase.R;

/**
 * Created by EdwardAdmin on 2018/8/13.
 */

public class IdentityFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View identityView = inflater.inflate(R.layout.identity_layout, null);
        return identityView;
    }
}
