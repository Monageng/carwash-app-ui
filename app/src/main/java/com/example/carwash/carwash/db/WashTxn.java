package com.example.carwash.carwash.db;

import android.provider.BaseColumns;

/**
 * Created by monageng on 2017/05/11.
 */

public class WashTxn {
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Entry.TABLE_NAME + " (" +
                    Entry._ID + " INTEGER PRIMARY KEY," +
                    Entry.COLUMN_NAME_DATE + " DATE DEFAULT CURRENT_DATE," +
                    Entry.COLUMN_NAME_MONTH + " TEXT," +
                    Entry.COLUMN_NAME_COUNT + " INTEGER," +
                    Entry.COLUMN_NAME_REG_NO + " TEXT )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

    // make the constructor private.
    private WashTxn() {}

    /* Inner class that defines the table contents */
    public static class Entry implements BaseColumns {
        public static final String TABLE_NAME = "WashTxn";
        public static final String _ID = "ID";
        public static final String COLUMN_NAME_DATE= "DATE";
        public static final String COLUMN_NAME_REG_NO= "REGNO";
        public static final String COLUMN_NAME_MONTH= "MONTH";
        public static final String COLUMN_NAME_COUNT= "COUNT";
    }
}
