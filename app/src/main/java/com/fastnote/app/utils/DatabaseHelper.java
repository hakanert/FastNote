package com.fastnote.app.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fastnote.app.models.NotesModel;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "my_notes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NOTE_CREATE =
            "CREATE TABLE " + TablesInfo.NoteEntry.TABLE_NAME + " (" +
                    TablesInfo.NoteEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TablesInfo.NoteEntry.COLUMN_NOTE + " TEXT, " +
                    TablesInfo.NoteEntry.COLUMN_TITLE + " TEXT, " +
                    TablesInfo.NoteEntry.COLUMN_CREATE_DATE + " TEXT " +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_NOTE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TablesInfo.NoteEntry.TABLE_NAME);

        onCreate(db);
    }

    public void addNote(String note,String title,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TablesInfo.NoteEntry.COLUMN_NOTE, note.trim());
        cv.put(TablesInfo.NoteEntry.COLUMN_TITLE, title.trim());
        cv.put(TablesInfo.NoteEntry.COLUMN_CREATE_DATE, date.trim());

        db.insert(TablesInfo.NoteEntry.TABLE_NAME, null, cv);
        db.close();
    }

    public void editNote(String note, String title, String id,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TablesInfo.NoteEntry.COLUMN_NOTE, note.trim());
        cv.put(TablesInfo.NoteEntry.COLUMN_TITLE, title.trim());
        cv.put(TablesInfo.NoteEntry.COLUMN_CREATE_DATE, date.trim());

        db.update(TablesInfo.NoteEntry.TABLE_NAME, cv,TablesInfo.NoteEntry.COLUMN_ID + "=?",new String[]{id});
        db.close();
    }

    public void deleteNote(String noteID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TablesInfo.NoteEntry.TABLE_NAME, TablesInfo.NoteEntry.COLUMN_ID + "=?", new String[]{noteID});
        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<NotesModel> getNoteList() {
        ArrayList<NotesModel> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                TablesInfo.NoteEntry.COLUMN_ID,
                TablesInfo.NoteEntry.COLUMN_NOTE,
                TablesInfo.NoteEntry.COLUMN_TITLE,
                TablesInfo.NoteEntry.COLUMN_CREATE_DATE};

        Cursor c = db.query(TablesInfo.NoteEntry.TABLE_NAME, projection, null, null, null, null, null);
        while (c.moveToNext()) {
            data.add(new NotesModel(c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_ID)),
                    c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_NOTE)),
                    c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_TITLE)),
                    c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_CREATE_DATE))));
        }

        c.close();
        db.close();

        return data;
    }
}