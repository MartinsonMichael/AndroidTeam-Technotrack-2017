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
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class StartingActivity extends AppCompatActivity {

    ProgressBar _initprgs;
    boolean loader_start = false;
    private final String save_loader_name = "loader_start";
    private Scanner mp3Scanner = new Scanner(".mp3");
    private List<String> absolutePaths = new ArrayList<>();
    TelegramClient client;
    private TdApi.TLObject result = new TdApi.AuthStateWaitPhoneNumber();
    private Semaphore semaphore = new Semaphore(0);
    private Client.ResultHandler resultHandler = new Client.ResultHandler() {
        @Override
        public void onResult(TdApi.TLObject object) {
            result = object;
            semaphore.release();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        client = TelegramClient.getInstance(this.getApplicationContext(), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {

            }
        });
        client.getAuthState(resultHandler);
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (result.getConstructor() != 1222968966) { //authStateOk
            Intent intent = new Intent(StartingActivity.this, AuthActivity.class);
            startActivity(intent);
            StartingActivity.this.finish();  // вроде в этом случае для неавторизованных пользователей загрузка не начнется
        } else {
            if (loader_start == false) {
                _initprgs = (ProgressBar) findViewById(R.id.progressBar);
                new Loader(_initprgs).execute();
                loader_start = true;
            }
        }
    }

    protected void onSaveInstanceState(Bundle state){
        super.onSaveInstanceState(state);

        state.putBoolean(save_loader_name, loader_start);
    }

    protected void onRestoreInstanceState(Bundle state){
        super.onRestoreInstanceState(state);

        loader_start = state.getBoolean(save_loader_name);
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
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
            progressBar.setProgress(50);
//                }
//            });

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
