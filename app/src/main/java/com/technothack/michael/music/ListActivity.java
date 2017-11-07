package com.technothack.michael.music;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.technothack.michael.music.dummy.DummyContent;

public class ListActivity extends AppCompatActivity implements MainFragmentList.OnListFragmentInteractionListener{

    private TextView mTextMessage;
    final static  String TAG_HOME = "HOME";
    final static  String TAG_PLAYLIST = "PLAYLIST";
    final static  String TAG_TRACK = "TRACK";
    final static  String TAG_CREATER = "CREATER";
    FragmentManager myFragmentManager = getSupportFragmentManager();
    MainFragmentList fragmentListHome;
    MainFragmentList fragmentListPlayList;
    MainFragmentList fragmentListTrack;
    MainFragmentList fragmentListCreater;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    if (myFragmentManager.findFragmentByTag(TAG_HOME) == null) {
                        FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content, fragmentListHome, TAG_HOME);
                        fragmentTransaction.commit();
                    }
                    return true;
                case R.id.navigation_cur_list:

                    //MainFragmentList list = new MainFragmentList();
                    //ft.replace(0, (android.app.Fragment)list);
                    //ft.commit();

                    mTextMessage.setText(R.string.title_cur_list);
                    if (myFragmentManager.findFragmentByTag(TAG_PLAYLIST) == null) {
                        FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content, fragmentListPlayList, TAG_PLAYLIST);
                        fragmentTransaction.commit();
                    }
                    return true;
                case R.id.navigation_cur_track:
                    mTextMessage.setText(R.string.title_cur_track);
                    if (myFragmentManager.findFragmentByTag(TAG_TRACK) == null) {
                        FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content, fragmentListTrack, TAG_TRACK);
                        fragmentTransaction.commit();
                    }
                    return true;
                case R.id.navigation_chat:
                    mTextMessage.setText(R.string.title_chat);
                    if (myFragmentManager.findFragmentByTag(TAG_CREATER) == null) {
                        FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content, fragmentListCreater, TAG_CREATER);
                        fragmentTransaction.commit();
                    }
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mTextMessage = (TextView) findViewById(R.id.message);
        fragmentListCreater = MainFragmentList.newInstance(1);
        fragmentListHome = MainFragmentList.newInstance(1);
        fragmentListPlayList = MainFragmentList.newInstance(1);
        fragmentListTrack = MainFragmentList.newInstance(1);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.content, fragmentListHome, TAG_HOME);
            fragmentTransaction.commit();
        }
    }


    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
