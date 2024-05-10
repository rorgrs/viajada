package com.example.viajada.database.dao;

import android.database.sqlite.SQLiteDatabase;

import com.example.viajada.database.DBOpenHelper;

public abstract class BaseDao {

    protected SQLiteDatabase db;
    protected DBOpenHelper db_helper;

    protected final void Open() {
        db = db_helper.getWritableDatabase();
    }

    protected final void Close() {
        db_helper.close();
    }

}
