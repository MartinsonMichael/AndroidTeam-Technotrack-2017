package com.technothack.michael.music;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;


public class AuthActivity extends AppCompatActivity {

    TelegramClient client;
    TextView text_auth;
    EditText phoneNumber, code;
    Button send_auth;
    int step; // 0 - ввод номера телефона, 1 - ввод кода

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        client = new TelegramClient(this.getApplicationContext(), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {

            }
        });
        if (savedInstanceState == null) {
            step = 0;
        }
        text_auth = (TextView) findViewById(R.id.text_auth);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        code = (EditText) findViewById(R.id.code);
        send_auth = (Button) findViewById(R.id.send_auth);
        //TODO: проверить подключение к интернету
        send_auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (step == 0 && phoneNumber.getText() != null) {
                    client.sendPhone(phoneNumber.getText().toString(), new Client.ResultHandler() {
                        @Override
                        public void onResult(TdApi.TLObject object) {
                            text_auth.setText(object.toString());
                            step++; // это нужно делать только в случае если не возникло ошибки
                        }
                    });
                }
                if (step == 1 && code.getText() != null) {
                    // все что написано дальше должно выполняться только в случае успешной авторизации
                    client.checkCode(code.getText().toString(), "", "", new Client.ResultHandler() {
                        @Override
                        public void onResult(TdApi.TLObject object) {
                            Intent intent = new Intent(AuthActivity.this, StartingActivity.class);
                            startActivity(intent);
                            AuthActivity.this.finish();
                        }
                    });
                }
            }
        });
    }
}
