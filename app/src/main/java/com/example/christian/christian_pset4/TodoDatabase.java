package com.example.christian.christian_pset4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoDatabase extends SQLiteOpenHelper {

    private static TodoDatabase instance = null;

    private static final String TABLE_NAME = "todos";
    private static final String COL1 = "_id";
    private static final String COL2 = "title";
    private static final String COL3 = "completed";

    private TodoDatabase(Context context) {
        super(context, TABLE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT, " + COL3 + " BIT);";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean addTodo(String todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, todo);
        contentValues.put(COL3, 0);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        }
        else {
            return true;
        }


    }

    public static TodoDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new TodoDatabase(context);
        }
        return instance;
    }

    public Cursor getTodos() {
        SQLiteDatabase db = this.getWritableDatabase();
        String all = "SELECT * FROM " + TABLE_NAME;
        Cursor entries = db.rawQuery(all, null);
        return entries;
    }

    public void deleteThis(String selected) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COL2 + " = '" + selected + "';");
    }
}
