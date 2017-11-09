package com.technothack.michael.music;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import static com.technothack.michael.music.ChatFragmentList.ARG_MSG;

public class MyAlertDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = "Ошибка";
        String message = "какая-то ошибка";
        if (getArguments() != null) {
            message = getArguments().getString(ARG_MSG);
        }
        String button1String = "Ok";

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);  // заголовок
        builder.setMessage(message); // сообщение
        builder.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setCancelable(true);

        return builder.create();
    }
}