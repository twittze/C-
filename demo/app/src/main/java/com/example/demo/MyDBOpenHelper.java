package com.example.demo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class MyDBOpenHelper extends SQLiteOpenHelper {




    public MyDBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE agenda("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "title TEXT NOT NULL,"+
                "date TEXT NOT NULL,"+
                "time TEXT NOT NULL,"+
                "content TEXT ,"+
                "state TEXT NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE scorelist("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "title INTEGER NOT NULL,"+
                "state TEXT NOT NULL)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS agenda");
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS scorelist");
        onCreate(sqLiteDatabase);

    }
    public int calculateAverage() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(title), COUNT(title) FROM " + "scorelist";
        Cursor cursor = db.rawQuery(query, null);
        int sum = 0;
        int count = 0;
        if (cursor.moveToFirst()) {
            sum = cursor.getInt(0);
            count = cursor.getInt(1);
        }
        cursor.close();
        if (count > 0) {
            return sum / count;
        } else {
            return 0;
        }
    }
    public Integer calculateTotal() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  COUNT(title) FROM " + "agenda";
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        if (count > 0) {
            return count;
        } else {
            return 0;
        }
    }
    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("scorelist", null, null);
        db.close();
    }
    public void deleteData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("agenda", null, null);
        db.close();
    }



}



