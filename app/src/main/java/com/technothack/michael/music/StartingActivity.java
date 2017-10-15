package com.technothack.michael.music;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class StartingActivity extends AppCompatActivity {

    ProgressBar _initprgs;
    Thread _load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        _initprgs = (ProgressBar)findViewById(R.id.progressBar);
        _load = new Thread(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        });
        loadData();
        //_load.start();
    }

    // this func run in additional thread, load some data and update progress bar
    private void loadData(){
        //_initprgs.setMin(0);
        //_initprgs.setMax(100);
        for (int i = 0; i < _initprgs.getMax(); i++){

//            try {
//                //Thread.currentThread().sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    // this func run in additional thread, and scan for initial music
    private void scanData(){

    }
}
