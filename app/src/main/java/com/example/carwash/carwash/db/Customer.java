package com.example.carwash.carwash.db;

import android.provider.BaseColumns;

/**
 * Created by monageng on 2017/05/03.
 */



public class Customer {
    // To prevent someone from accidentally instantiating the contract class,

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Entry.TABLE_NAME + " (" +
                    Entry._ID + " INTEGER PRIMARY KEY," +
                    Entry.COLUMN_NAME_NAME + " TEXT," +
                    Entry.COLUMN_NAME_SURNAME + " TEXT," +
                    Entry.COLUMN_NAME_REG_NO + " TEXT," +
                    Entry.COLUMN_NAME_CELL_NO + " TEXT," +
                    Entry.COLUMN_MODIFIED_DATE + " DATE DEFAULT CURRENT_DATE, " +
                    Entry.COLUMN_NAME_DATE_OF_BIRTH + " DATE )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

    // make the constructor private.
    private Customer() {}

    /* Inner class that defines the table contents */
    public static class Entry implements BaseColumns {
        public static final String TABLE_NAME = "customer";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_SURNAME= "surname";
        public static final String COLUMN_NAME_REG_NO= "regno";
        public static final String COLUMN_NAME_CELL_NO= "cellno";
        public static final String COLUMN_NAME_DATE_OF_BIRTH= "dateOfBirth";
        public static final String COLUMN_MODIFIED_DATE = "modifiedDate";
    }
}



