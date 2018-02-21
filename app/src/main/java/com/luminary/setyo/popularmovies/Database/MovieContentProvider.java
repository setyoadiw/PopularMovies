package com.luminary.setyo.popularmovies.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MovieContentProvider extends ContentProvider {
    //langkah 3 membuat uri matcher

    public static final int ALL_FILM = 100;
    public static final int FILM_WITH_ID = 101;

    public static  final UriMatcher sUrimatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
     UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
     uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_TASKS ,ALL_FILM);
    uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_TASKS+"/#" ,FILM_WITH_ID);

    return uriMatcher;
    }

    public MovieContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int Match = sUrimatcher.match(uri);

        int numRowDeleted;

        switch (Match){
            case ALL_FILM:
                numRowDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME,selection,selectionArgs);

                break;


            case FILM_WITH_ID:
                numRowDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME,
                        MovieContract.MovieEntry.COLUMN_ID+ " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;

            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }


        return numRowDeleted;

    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int Match = sUrimatcher.match(uri);
        Uri returnuri = null;

        switch (Match){
            case ALL_FILM:
                long id = db.insert(MovieContract.MovieEntry.TABLE_NAME,null,values);

                if(id>0){
                    returnuri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI,id);
                }else {
                    throw new SQLException("Failed to insert row into "+uri);
                }
                break;
            case FILM_WITH_ID:
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }


        return returnuri;
    }

    // 1. inisialisasi

    MovieDbHelper dbHelper;

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        dbHelper = new MovieDbHelper(getContext());

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int match = sUrimatcher.match(uri);
        Cursor cursor = null;


        switch (match){
            case ALL_FILM:
                cursor = db.query(MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case FILM_WITH_ID:
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }


        cursor.setNotificationUri(getContext().getContentResolver(),uri);
return cursor;
    }



    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
