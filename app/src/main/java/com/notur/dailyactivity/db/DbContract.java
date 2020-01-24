package com.notur.dailyactivity.db;

import android.provider.BaseColumns;

public class DbContract {
    static final String TABLE_DATA = "data";

    public static final class Table implements BaseColumns {
        static String ID = "id";
        static String KATAGORI = "katagori";
        static String KETERANGAN = "keterangan";
        static String TGL = "tgl";
    }
}
