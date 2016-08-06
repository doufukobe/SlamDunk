package com.fpd.slamdunk.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by t450s on 2016/8/6.
 */
public class SDDBHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "slamdunk.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_SLAMDUNK = "slamdunk";
    public static final String CREATE_SQL = "CREATE TABLE " + TABLE_SLAMDUNK + " ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "actid INT, "
            + "actname TEXT, "
            + "actimg TEXT, "
            + "actoriginatorname TEXT, "
            + "acttime REAL, "
            + "curpeoplenum INT, "
            + "maxpeoplenum INT, "
            + "addressdist TEXT"
            +  ")";
    public SDDBHelper(Context mContext){
        super(mContext, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
