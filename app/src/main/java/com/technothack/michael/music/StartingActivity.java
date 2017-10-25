package com.technothack.michael.music;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;

import java.util.ArrayList;
import java.util.List;

public class StartingActivity extends AppCompatActivity {

    ProgressBar _initprgs;
    Thread _load;
    private Scanner mp3Scanner= new Scanner(".mp3");
    private List<String> absolutePaths = new ArrayList<>();
    TelegramClient client;
    TdApi.TLObject result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        client = new TelegramClient(this.getApplicationContext(), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {

            }
        });
        client.getAuthState(new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                result = object;
            }
        });
        if (result.getConstructor() != new TdApi.AuthStateOk().getConstructor()) {
            Intent intent = new Intent(StartingActivity.this, AuthActivity.class);
            startActivity(intent);
            StartingActivity.this.finish();  // вроде в этом случае для неавторизованных пользователей загрузка не начнется
        }

        _initprgs = (ProgressBar)findViewById(R.id.progressBar);
        _load = new Thread(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        });
        //loadData();
        //_load.start();
        new Loader(_initprgs).execute();
    }

    // this func run in additional thread, load some data and update progress bar
    private void loadData(){
        //_initprgs.setMin(0);
        //_initprgs.setMax(100);
        for (int i = 0; i < _initprgs.getMax(); i++){
            _initprgs.setProgress(i);
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

    static private String getShortName(String fullName) {
        String bufString = "";
        int pos = fullName.length() - 1;
        while (fullName.charAt(pos) != '/') {
            bufString += fullName.charAt(pos);
            pos--;
        }
        String result = "";
        for (int i = bufString.length() - 1; i >= 0; i--) {
            result += bufString.charAt(i);
        }
        return result;
    }

    private class Loader extends AsyncTask<Void, Void, Void> {
        private ProgressBar progressBar;

        Loader(ProgressBar progressBar) {
            super();
            this.progressBar = progressBar;
        }

        @Override
        protected Void doInBackground(Void... unused) {
            mp3Scanner.run();
            publishProgress();
            absolutePaths = mp3Scanner.getAbsolutePaths();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... unused) {
            // Need to rewrite
            progressBar.setProgress(50);
        }

        @Override
        protected void onPostExecute(Void unused) {
            // for example
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Files found: " + absolutePaths.size(), Toast.LENGTH_LONG);
            toast.show();

            //Opening next activity
            Intent intent = new Intent(StartingActivity.this, ListActivity.class);
            startActivity(intent);
        }
    }
}
