package com.example.edwardadmin.ormdatabase.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.edwardadmin.ormdatabase.R;
import com.example.edwardadmin.ormdatabase.adapter.PersonAdapter;
import com.example.edwardadmin.ormdatabase.entity.PersonInfo;
import com.example.edwardadmin.ormdatabase.entity.PersonToken;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by EdwardAdmin on 2018/8/13.
 */

public class PersonFragment extends Fragment implements IPersonView {

    private static final String TAG = PersonFragment.class.getSimpleName();
    private static final int MSG_UPDATE_DATA = 0x1;
    private static final int MSG_UPDATE_ERROR = 0x2;

    private Button mAddOldPersonButton;
    private Button mAddNewPersonButton;
    private Button mUpdatePersonButton;
    private Button mAddPersonImageButton;
    private EditText mInputAddNumber;
    private EditText mInputUpdateNumber;
    private EditText mInputAddImage;
    private ListView mPersonListView;
    private ContentLoadingProgressBar mContentLoadingProgressBar;
    private FrameLayout mContentFrameLayout;

    private ArrayList<PersonInfo> mAllPersonData;
    private IPersonPresenter iPersonPresenter;
    private PersonAdapter personAdapter;

    private Random random;

    private Handler mPersonHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_UPDATE_DATA:
                    Log.d(TAG, "loadPersonDataSuccess !");
                    mContentFrameLayout.setVisibility(View.GONE);
                    personAdapter = new PersonAdapter(getActivity(), mAllPersonData);
                    mPersonListView.setAdapter(personAdapter);
                    mPersonListView.invalidate();
                    break;
                case MSG_UPDATE_ERROR:
                    Log.d(TAG, "loadPersonDataError !!");
                    mContentFrameLayout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "No Data", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View personView = inflater.inflate(R.layout.person_layout, null);
        initViews(personView);
        initData();
        return personView;
    }

    private void initData() {
        random = new Random(1000);
        iPersonPresenter = new PersonPresenterImpl(this, new PersonInteractorImpl(getActivity()));
        startLoadPersonData();
    }

    private void startLoadPersonData() {
        Thread thread = new Thread(new PersonRunnable());
        thread.start();
    }

    private void initViews(View personView) {
        mAddOldPersonButton = personView.findViewById(R.id.add_person);
        mAddNewPersonButton = personView.findViewById(R.id.add_person1);
        mUpdatePersonButton = personView.findViewById(R.id.update_person);
        mAddPersonImageButton = personView.findViewById(R.id.add_image);
        mInputAddImage = personView.findViewById(R.id.input_add_image);

        mInputAddNumber = personView.findViewById(R.id.input_add_number);
        mInputUpdateNumber = personView.findViewById(R.id.input_update_number);
        mPersonListView = personView.findViewById(R.id.person_list);
        mContentLoadingProgressBar = personView.findViewById(R.id.person_content_loading);
        mContentFrameLayout = personView.findViewById(R.id.progress_framelayout);

        mAddOldPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOldPersonData();
            }
        });

        mAddNewPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String personNumber = mInputAddNumber.getText().toString().trim();
                addNewPersonData(personNumber);
            }
        });

        mUpdatePersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String personNumber = mInputUpdateNumber.getText().toString().trim();
                updateOrAddPersonData(personNumber);
            }
        });

        mAddPersonImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String personNumber = mInputAddImage.getText().toString().trim();
                addImageData(personNumber);
            }
        });
    }

    private void addImageData(String personNumber) {
        //add image
        String personImage = "http://pic9.photophoto.cn/20081229/0034034829945374_b.jpg";
        PersonInfo personInfo = new PersonInfo("Edward", "male", "26",
                "175", "Han nationality", personNumber, personImage);
        PersonToken personToken = new PersonToken(System.currentTimeMillis());
        iPersonPresenter.updateOrAddPersonData(personNumber, personInfo, personToken);
    }

    private void updateOrAddPersonData(String personNumber) {
        //update personInfo
        PersonInfo personInfo = new PersonInfo("Edward", "male", "26",
                "175", "Han nationality", personNumber);
        PersonToken personToken = new PersonToken(System.currentTimeMillis());
        iPersonPresenter.updateOrAddPersonData(personNumber, personInfo, personToken);
    }

    private void addOldPersonData() {
        //1.common table
        PersonInfo personInfo = new PersonInfo("Edward", "male", "26",
                "175", "Han nationality", random.nextInt(1000) + "");
        iPersonPresenter.addPersonData(personInfo);
    }

    private void addNewPersonData(String personNumber) {
        Log.d(TAG, "addNewPersonData !!");
        //2.add foreign key
        PersonToken personToken = new PersonToken(System.currentTimeMillis());
        PersonInfo personInfo = new PersonInfo("Edward", "male", "26",
                "175", "Han nationality", personNumber);
        iPersonPresenter.addPersonData(personInfo, personToken);
    }

    @Override
    public void addPersonDataSuccess() {
        Toast.makeText(getActivity(), "Add data success !", Toast.LENGTH_SHORT).show();
        startLoadPersonData();
    }

    @Override
    public void addPersonDataError() {
        Toast.makeText(getActivity(), "Add data error !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadPersonDataSuccess(ArrayList<PersonInfo> personInfoArrayList) {
        mAllPersonData = personInfoArrayList;
        Message message = Message.obtain();
        message.what = MSG_UPDATE_DATA;
        mPersonHandler.sendMessage(message);
    }

    @Override
    public void loadPersonDataError() {
        Message message = Message.obtain();
        message.what = MSG_UPDATE_ERROR;
        mPersonHandler.sendMessage(message);
    }

    private class PersonRunnable implements Runnable {

        @Override
        public void run() {
            iPersonPresenter.loadPersonData();
        }
    }
}
