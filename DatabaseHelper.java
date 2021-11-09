package com.example.databaseexcercisedataentry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Worker.db";
    public static final String TABLE_NAME="Worker_table";
    public static final String COL_1="ID";
    public static final String COL_2="NAME";
    public static final String COL_3="ADDRESS";
    public static final String COL_4="SALARY";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TXT, ADDRESS TEXT, SALARY TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name,String salary,String address){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,address);
        contentValues.put(COL_4,salary);
        long results = db.insert(TABLE_NAME, null, contentValues);
        if(results<=0)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME , null);
        return res;
    }

    public boolean updateData(String id,String name,String salary,String address)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,address);
        contentValues.put(COL_4,salary);
        long results = db.insert(TABLE_NAME,null,contentValues);
        long results1 = db.update(TABLE_NAME,contentValues,"ID = ?",new String[] {id});
        if(results>=0||results1>=0)
            return true;
        else
            return false;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ? ", new String[] {id});
    }

    public Cursor betweenSalary(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ COL_4 + " BETWEEN "+ 2000 + " AND " + 3000 ,null);
        return cursor;
    }

    public Cursor maxSalary(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select max(salary) as salary from " + TABLE_NAME + " WHERE "+ COL_4,null);
        Log.e(TAG, "maxSalary"+cursor);
        return cursor;
    }

    public  Cursor orderSalary(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME + " ORDER BY " + COL_4,null);
        return cursor;
    }

    public Cursor groupSalary(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(ID),SALARY FROM " + TABLE_NAME + " GROUP BY "+ COL_4 + " HAVING COUNT(ID) > " +1, null);
        return cursor;
    }
}
