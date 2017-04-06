package com.example.sagun.inote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


public class DBOpenHelper extends SQLiteOpenHelper {
    //constants for db name and version
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    //constants for identifying table and columns
    public static final String TABLE_NOTES = "notes";
    public static final String NOTE_ID = "_id";
    public static final String NOTE_TEXT = "noteText";
    public static final String NOTE_CREATED = "noteCreated";

    public static final String[] ALL_COLUMNS = {NOTE_ID, NOTE_TEXT, NOTE_CREATED};

    //SQL to create table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NOTES + " (" +
                    NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOTE_TEXT + " TEXT, " +
                    NOTE_CREATED + " TEXT default CURRENT_TIMESTAMP" +
                    ")";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null , DATABASE_VERSION);
        Log.d("Database Operations", "Database created...");
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(TABLE_CREATE);
        Log.d("Database Operations", "Table created... ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NOTES );
        onCreate(db);
        Log.d("Database operations", "Database updated...");
    }

    public void insertNote(String text, SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_TEXT, text);
        db.insert(TABLE_NOTES, null, contentValues);
        Log.d("Database operations", "One row inserted...");
    }

    public void updateNote(int id, SQLiteDatabase db, String newText){
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_TEXT, newText );
        db.update(TABLE_NOTES,contentValues,NOTE_ID + " =" + id, null);
    }

    public Cursor getNotes(SQLiteDatabase db){
        String[] projection = {NOTE_ID, NOTE_TEXT, NOTE_CREATED};
        Cursor cursor = db.query(TABLE_NOTES,projection,null, null, null, null, NOTE_CREATED + " DESC");
        return  cursor;
    }

    public void deleteAllNotes(SQLiteDatabase db){
        db.delete(TABLE_NOTES, null, null);

    }

    public void deleteNote(int id, SQLiteDatabase db, Context context){
        db.delete(TABLE_NOTES, NOTE_ID + " =" + id, null);
        Toast.makeText(context, "Note deleted ", Toast.LENGTH_SHORT).show();
    }
}
