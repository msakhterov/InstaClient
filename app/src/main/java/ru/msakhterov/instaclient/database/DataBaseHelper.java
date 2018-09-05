package ru.msakhterov.instaclient.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.msakhterov.instaclient.database.DataBaseSchema.PicturesTable;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "instaclientDB";
    private static final int DB_VERSION = 1;
    private static final String TAG = "DataBaseHelper";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + PicturesTable.NAME + "(" +
                " " + PicturesTable.Colons.ID + " integer primary key autoincrement, " +
                PicturesTable.Colons.PATH + ", " +
                PicturesTable.Colons.IS_FAVOURITE +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

