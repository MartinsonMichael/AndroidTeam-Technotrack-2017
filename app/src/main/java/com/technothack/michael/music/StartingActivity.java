package com.technothack.michael.music;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;
import java.util.concurrent.Semaphore;


public class StartingActivity extends AppCompatActivity {

    ProgressBar _initprgs;
    private boolean loader_start = false;
    private boolean author_start = false;
    public static final String save_loader_name = "loader_start";
    public static final String save_author_name = "author_start";
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

    private int PERMISSION_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

//        if (savedInstanceState.containsKey(save_loader_name)) {
//            loader_start = savedInstanceState.getBoolean(save_loader_name);
//        }
//        if (savedInstanceState.containsKey(save_author_name)) {
//            author_start = savedInstanceState.getBoolean(save_author_name);
//        }

        if (checkMemoryPermission()){

            // TODO : DEBUG OUTPUT
            Toast toast = Toast.makeText(
                    getApplicationContext(),
                    "This application has external storage read/write permission" +
                            "\n\rDeal with it! :)",
                    Toast.LENGTH_LONG);
            toast.show();

            loading();
        }
        else {
            tryToGetPermission();
        }
    }

    protected void loading(){

        if (author_start == false) {
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
            }

            author_start = true;
        }

        if (loader_start == false && author_start == true) {

            _initprgs = (ProgressBar) findViewById(R.id.progressBar);
            MusicHolder.getMe().scanForMusic(_initprgs, new Runnable() {
                @Override
                public void run() {

                    // TODO : DEBUG OUTPUT
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "I find " + MusicHolder.getMe().mp3Scanner.getAbsolutePaths().size() + " files",
                            Toast.LENGTH_LONG);
                    toast.show();

                    Intent intent = new Intent(StartingActivity.this,
                            ListActivity.class);
                    startActivity(intent);
                }
            });

            loader_start = true;
        }
    }

    protected boolean checkMemoryPermission(){
        // return false if we still have no permission

        // может уже есть разрешение?
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
            return true;
        }

        return false;
    }

    protected void tryToGetPermission(){
        // запрашиваем
        ActivityCompat.requestPermissions(this,
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                },
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length == 1){
            loading();
        }
        else{
            Toast toast = Toast.makeText(
                    getApplicationContext(),
                    "I am player! I need access to you memory!",
                    Toast.LENGTH_LONG);
            toast.show();
            tryToGetPermission();
        }
    }

    protected void onSaveInstanceState(Bundle state){
        super.onSaveInstanceState(state);

        state.putBoolean(save_loader_name, loader_start);
        state.putBoolean(save_author_name, author_start);
    }

    protected void onRestoreInstanceState(Bundle state){
        super.onRestoreInstanceState(state);

        loader_start = state.getBoolean(save_loader_name);
        author_start = state.getBoolean(save_author_name);
    }

}
