package com.notur.dailyactivity.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.notur.dailyactivity.model.Data;

import java.util.ArrayList;

import static com.notur.dailyactivity.db.DbContract.TABLE_DATA;
import static com.notur.dailyactivity.db.DbContract.Table.DESC;
import static com.notur.dailyactivity.db.DbContract.Table.ID;
import static com.notur.dailyactivity.db.DbContract.Table.KATAGORI;
import static com.notur.dailyactivity.db.DbContract.Table.TGL;

public class DataHelper {

    private SQLiteDatabase db;
    private DbHelper helper;

    public DataHelper(Context context){
        helper = new DbHelper(context);
    }

    public void openDB() throws SQLException {
        db = helper.getWritableDatabase();
    }

    public void closeDB(){
        helper.close();
        if (db.isOpen())
            db.close();
    }

    public ArrayList<Data> getData(){
        Cursor cursor = db.query(TABLE_DATA, null, null, null, null, null, ID + " DESC", null);
        cursor.moveToFirst();
        ArrayList<Data>list = new ArrayList<>();
        Data data;
        if (cursor.getCount()>0){
            do {
                data = new Data();
                data.setKatagori(cursor.getString(cursor.getColumnIndexOrThrow(KATAGORI)));
                data.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DESC)));
                data.setTgl(cursor.getString(cursor.getColumnIndexOrThrow(TGL)));
                list.add(data);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return list;
    }

    public void addData(Data data){

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KATAGORI, data.getKatagori());
        contentValues.put(DESC, data.getDesc());
        contentValues.put(TGL, data.getTgl());

        db.insert(TABLE_DATA,null,contentValues);

    }

    public void removeData(int id) {
        db.delete(TABLE_DATA, ID + " = '" + id + "'", null);
    }

    public void resetTable(){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from data");
        db.execSQL("delete from sqlite_sequence where name='data'");
    }
}
