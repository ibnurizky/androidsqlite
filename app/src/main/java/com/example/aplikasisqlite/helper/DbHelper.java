package com.example.aplikasisqlite.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "datadiri.db";
    public static final String TABLE_NAME = "biodata";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAMA = "nama";
    public static final String COLUMN_ALAMAT = "alamat";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryTable = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY autoincrement, " + COLUMN_NAMA +
                " TEXT NOT NULL, " + COLUMN_ALAMAT + " TEXT NOT NULL" +")";
        db.execSQL(queryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

  public ArrayList<HashMap<String,String>> getAllData() {
      ArrayList<HashMap<String, String>> wordList;
      wordList = new ArrayList<HashMap<String, String>>();
      String querySelect = "SELECT * FROM " + TABLE_NAME;
      SQLiteDatabase database = this.getReadableDatabase();
      Cursor cursor = database.rawQuery(querySelect, null);
      if (cursor.moveToFirst()){
          do {
              HashMap<String,String> map = new HashMap<String, String>();
              map.put(COLUMN_ID, cursor.getString(0));
              map.put(COLUMN_NAMA, cursor.getString(1));
              map.put(COLUMN_ALAMAT, cursor.getString(2));
              wordList.add(map);
          } while (cursor.moveToNext());
      }
      Log.e("select sqlite", "" + wordList);
      database.close();
      return wordList;
  }

  public void insert(String nama, String alamat) {
        SQLiteDatabase database = this.getWritableDatabase();
        String queryInsert = "INSERT INTO " + TABLE_NAME + " (nama, alamat) " +
                             "VALUES ('" + nama + "', '" + alamat + "')";
        Log.e("insert sqlite", "" + queryInsert);
        database.execSQL(queryInsert);
        database.close();
  }

  public void update(int id, String nama, String alamat) {
        SQLiteDatabase database = this.getWritableDatabase();
        String queryUpdate =  "UPDATE " + TABLE_NAME + " SET "
                + COLUMN_NAMA + "='" + nama + "', "
                + COLUMN_ALAMAT + "='" + alamat + "'"
                + " WHERE " + COLUMN_ID + "=" + "'" + id + "'";
        Log.e("update sqlite", "" + queryUpdate);
        database.execSQL(queryUpdate);
        database.close();
  }

  public void delete(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        String queryDelete = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" +
                             "'" + id + "'";
        Log.e("delete sqlite", "" + queryDelete);
        database.execSQL(queryDelete);
        database.close();
  }
}
