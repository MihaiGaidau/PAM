package com.example.denis.calendarview;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Data.db";
    public static final String TABLE_NAME  = "event_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "DDATE";
    public static final String COL_3 = "TITLE";
    public static final String COL_4 = "LOCATION";
    public static final String COL_5 = "STARTTIME";
    public static final String COL_6 = "ENDTIME";
    public static final String COL_7 = "NOTE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("misa","baza s-a creat");
        //onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("misa","tabelul urmeaza sa se creeze");
        db.execSQL("create table "+ TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, DDATE TEXT, TITLE TEXT, LOCATION TEXT, STARTTIME TEXT, ENDTIME TEXT, NOTE TEXT)");
        Log.d("misa","tabelul sa creat");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        Log.d("misa","nustiu de ce sterge tabelul");
        onCreate(db);
    }
    public boolean insertData(Event myE){
        long error;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,myE.getDate());
        contentValues.put(COL_3,myE.getTitle());
        contentValues.put(COL_4,myE.getLocation());
        contentValues.put(COL_5,myE.getStartTime());
        contentValues.put(COL_6,myE.getEndTime());
        contentValues.put(COL_7,myE.getNote());
        error = db.insert(TABLE_NAME,null,contentValues);
        if (error == -1)
            return false;
        return  true;
    }
    public boolean insertData(String data, String title, String location, String startt,String endt, String note){
        long error;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,data);
        contentValues.put(COL_3,title);
        contentValues.put(COL_4,location);
        contentValues.put(COL_5,startt);
        contentValues.put(COL_6,endt);
        contentValues.put(COL_7,note);
        error = db.insert(TABLE_NAME,null,contentValues);
        if (error == -1)
            return false;
        return  true;
    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME,null);
        return res;

    }
    public boolean updateData(String id, Event myE){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,myE.getDate());
        contentValues.put(COL_3,myE.getTitle());
        contentValues.put(COL_4,myE.getLocation());
        contentValues.put(COL_5,myE.getStartTime());
        contentValues.put(COL_6,myE.getEndTime());
        contentValues.put(COL_7,myE.getNote());
        db.update(TABLE_NAME, contentValues,"ID = ?",new String[]{id});
        return true;
    }
    public boolean updateData(String id, String data, String title, String location, String startt,String endt, String note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,data);
        contentValues.put(COL_3,title);
        contentValues.put(COL_4,location);
        contentValues.put(COL_5,startt);
        contentValues.put(COL_6,endt);
        contentValues.put(COL_7,note);
        db.update(TABLE_NAME, contentValues,"ID = ?",new String[]{id});
        return true;
    }
    public Cursor getById(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where ID = ?",new String[] {id});
        return res;
    }
    public Cursor getAllDay(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where DDATE= ?",new String[]{date});
        return res;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?", new String[] {id});
    }
}
