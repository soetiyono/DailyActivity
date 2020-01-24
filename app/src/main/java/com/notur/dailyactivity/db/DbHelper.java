package com.notur.dailyactivity.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "daily.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE = String.format("create table %s"
                    + " (%s integer primary key autoincrement," +
                    "%s text null," +
                    "%s INTEGER nullL," +
                    "%s text null)",
            DbContract.TABLE_DATA,
            DbContract.Table.ID,
            DbContract.Table.KATAGORI,
            DbContract.Table.KETERANGAN,
            DbContract.Table.TGL);


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //String sql = "create table data( _id integer primary key autoincrement,store text null, location integer null, art text null, qty text null, date varchar null, user text null, no integer null )";
        //Log.d("Data", "onCreate: " + sql);
        //db.execSQL(sql);
        //sql = "INSERT INTO data (store, location, art, qty) VALUES ( 'store', 'location', 'art','qty');";
        //db.execSQL(sql);
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+ DbContract.TABLE_DATA);
        onCreate(db);
    }
}
