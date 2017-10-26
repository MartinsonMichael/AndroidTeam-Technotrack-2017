package com.technothack.michael.music;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;


public class AuthActivity extends AppCompatActivity {

    TelegramClient client;
    TextView text_auth;
    EditText phoneNumber, code;
    Button send_auth;
    int step; // 0 - ввод номера телефона, 1 - ввод кода
    private TdApi.TLObject result = new TdApi.AuthStateOk();
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
        setContentView(R.layout.activity_auth);
        client = TelegramClient.getInstance();
        if (savedInstanceState == null) {
            step = 0;
        }
        text_auth = (TextView) findViewById(R.id.text_auth);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        code = (EditText) findViewById(R.id.code);
        send_auth = (Button) findViewById(R.id.send_auth);
        // проверить подключение к интернету
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            send_auth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    client.getAuthState(resultHandler);
                    try {
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    text_auth.post(new Runnable() {
                        @Override
                        public void run() {
                            text_auth.setText(result.toString());
                        }
                    });
                    if (step == 0 && phoneNumber.getText().toString().length() != 0) {
                        client.sendPhone(phoneNumber.getText().toString(), new Client.ResultHandler() {
                            @Override
                            public void onResult(TdApi.TLObject object) {
                                text_auth.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        text_auth.setText(object.toString());
                                    }
                                });
                            }
                        });
                        client.getAuthState(resultHandler);
                        try {
                            semaphore.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (result.getConstructor() == 2103299766) { //authStateWaitCode
                            step = 1; // это нужно делать только в случае если не возникло ошибки
                            code.setVisibility(View.VISIBLE);
                        }
                    }
                    if (step == 1 && code.getText().toString().length() != 0) {
                        client.checkCode(code.getText().toString(), "", "", new Client.ResultHandler() {
                            @Override
                            public void onResult(TdApi.TLObject object) {
                                text_auth.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        text_auth.setText(object.toString());
                                    }
                                });
                            }
                        });
                        //в случае успешной авторизации
                        client.getAuthState(resultHandler);
                        try {
                            semaphore.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (result.getConstructor() == 1222968966) { //authStateOk
                            Intent intent = new Intent(AuthActivity.this, StartingActivity.class);
                            startActivity(intent);
                            AuthActivity.this.finish();
                        }
                    }
                }
            });
        } else {
            text_auth.setText(R.string.network_state_0);
        }
    }
}
