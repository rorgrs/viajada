package com.example.viajada.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.viajada.database.model.UsuarioModel;
import com.example.viajada.database.model.ViagemCustoAdicionalModel;
import com.example.viajada.database.model.ViagemModel;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String BANCO_NOME = "viajada.db";
    private static final int VERSAO_BANCO = 2;

    public DBOpenHelper(Context context) {
        super(context, BANCO_NOME, null, VERSAO_BANCO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UsuarioModel.CREATE_TABLE);
        db.execSQL(ViagemModel.CREATE_TABLE);
        db.execSQL(ViagemCustoAdicionalModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UsuarioModel.DROP_TABLE);
        db.execSQL(ViagemModel.DROP_TABLE);
        db.execSQL(ViagemCustoAdicionalModel.DROP_TABLE);
    }
}
