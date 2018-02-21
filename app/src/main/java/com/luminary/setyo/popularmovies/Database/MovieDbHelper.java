package com.luminary.setyo.popularmovies.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by luminary on 2/4/2018.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "film.db";

    //kalau tiap kali ganti database harus dinaikan +1
    private static final int DATABASE_VERSION = 1;

    private static final String TAG = "MovieDbHelper";
    //logt

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_WEATHER_TABLE =

                "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                        MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MovieContract.MovieEntry.COLUMN_ID + " INTEGER, " +
                        MovieContract.MovieEntry.COLUMN_JUDUL + " TEXT, " +
                        MovieContract.MovieEntry.COLUMN_POSTER + " TEXT, " +
                        MovieContract.MovieEntry.COLUMN_OVERVIEW + " TEXT, " +
                        //2 tambah sini
                        "UNIQUE (" + MovieContract.MovieEntry.COLUMN_JUDUL + ") ON CONFLICT REPLACE);";

        Log.d(TAG, "onCreate: "+SQL_CREATE_WEATHER_TABLE);

        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


}
