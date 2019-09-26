package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnect extends SQLiteOpenHelper {

    private static final  String DATABASE_NAME = "Payment.db";
    private static final  String TABLE_ONE_NAME = "PayPal_table";
    private static final  String TABLE_TWO_NAME = "CreditCard_table";
    private static final  String TABLE_NAME = "Pay_table";

    private static final  String COL_1 = "PayID";
    private static final  String COL_2 = "HouseID";
    private static final  String COL_3 = "Time";
    private static final  String COL_4 = "Phone";


    private static final  String COL_1_1 = "PID";
    private static final  String COL_1_2 = "ProductID";
    private static final  String COL_1_3 = "Payment";

    private static final  String COL_2_1 = "CID";
    private static final  String COL_2_2 = "CNUMBER";
    private static final  String COL_2_3 = "CDATE";
    private static final  String COL_2_4 = "CKEY";
    private static final  String COL_2_5 = "CNAME";


    public DBConnect(Context context) {
        super(context, DATABASE_NAME , null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table "+TABLE_ONE_NAME + " (PID INTEGER PRIMARY KEY AUTOINCREMENT, ProductID INTEGER , Payment REAL )");
            db.execSQL("create table CreditCard_table (CID INTEGER PRIMARY KEY AUTOINCREMENT, CNUMBER TEXT, CDATE TEXT ,CKEY TEXT ,CNAME TEXT )");
            db.execSQL("create table Pay_table (PayID INTEGER PRIMARY KEY AUTOINCREMENT, HouseID TEXT, Time TEXT , Phone TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " +TABLE_ONE_NAME );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TWO_NAME );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );

        onCreate(db);
    }

    public boolean insertDataPay(String HouseID , String Time, String Phone )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,HouseID);
        contentValues.put(COL_3,Time);
        contentValues.put(COL_4,Phone);
        long result = db.insert(TABLE_NAME , null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertData(String CNUMBER ,String CDATE , String CKEY , String CNAME){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_2,CNUMBER);
        contentValues.put(COL_2_3,CDATE);
        contentValues.put(COL_2_4,CKEY);
        contentValues.put(COL_2_5,CNAME);
        long result = db.insert(TABLE_TWO_NAME , null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }


    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_TWO_NAME,null );
        return res;
    }

    public Cursor getAllDataPay()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME,null );
        return res;
    }


    public boolean updateData( String CID , String CNUMBER ,String CDATE , String CKEY , String CNAME){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_1,CID);
        contentValues.put(COL_2_2,CNUMBER);
        contentValues.put(COL_2_3,CDATE);
        contentValues.put(COL_2_4,CKEY);
        contentValues.put(COL_2_5,CNAME);
        db.update(TABLE_TWO_NAME, contentValues ,"CID = ?",  new String[] {CID});
        return true;
    }

    public boolean updateDataPay(String PayID, String HouseID , String Time, String Phone)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,PayID);
        contentValues.put(COL_2,HouseID);
        contentValues.put(COL_3,Time);
        contentValues.put(COL_4,Phone);
        db.update(TABLE_NAME, contentValues ,"PayID = ?",  new String[] {PayID});
        return true;
    }

    public Integer deleteData(String CID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TWO_NAME, "CID = ?", new String[] {CID});
    }
}
