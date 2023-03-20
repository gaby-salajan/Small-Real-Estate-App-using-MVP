package com.gabys.ps_tema1.Model.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBConnection {
    private static SQLiteDatabase db = null;


    private static DBConnection instance;
    private SQLiteDatabase connection;

    private DBConnection(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        //SQLiteDatabase.openOrCreateDatabase("/data/data/com.gabys.ps_tema1/databases/agency.db", null);
        this.connection = databaseHelper.getWritableDatabase();
    }

    public SQLiteDatabase getConnection() {
        return connection;
    }

    public static DBConnection getInstance(Context context) {
        if (instance == null) {
            instance = new DBConnection(context);
        } else if (!instance.getConnection().isOpen()) {
            instance = new DBConnection(context);
        }

        return instance;
    }
}
