package com.example.viajada.helper;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class AlertHelper {

    private final Context context;

    public AlertHelper(Context context){
        this.context = context;
    }

    public void CriarAlerta(String title, String text) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(text);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

}
