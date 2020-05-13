package com.maksimohotnikov.mydiary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "recordDb";
    public static final String TABLE_NAME = "records";

    public static final String KEY_ID = "_id";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
    public static final String KEY_SUGAR = "sugar";
    public static final String KEY_BREAD_UNITS = "bread_units";
    public static final String KEY_SHORT_INSULIN = "short_insulin";
    public static final String KEY_LONG_INSULIN = "long_insulin";
    public static final String KEY_COMMENT = "comment";

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_DATE + " TEXT,"
                + KEY_TIME + " TEXT,"
                + KEY_SUGAR + " TEXT,"
                + KEY_BREAD_UNITS + " TEXT,"
                + KEY_SHORT_INSULIN + " TEXT,"
                + KEY_LONG_INSULIN + " TEXT,"
                + KEY_COMMENT + " TEXT" + ")");
       /*db.execSQL("CREATE TABLE " + TABLE_NAME + "("
               + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
               + KEY_SUGAR + " TEXT,"
               + KEY_BREAD_UNITS + " TEXT,"
               + KEY_SHORT_INSULIN + " TEXT" + ")");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
