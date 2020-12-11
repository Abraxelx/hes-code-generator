package com.example.hes_kodu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "hescode_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "hesCode";
    private static final String COL3 = "name";
    private static final String IF_TABLE_EXIST = "DROP TABLE IF EXISTS ";
    private static final String CREATE_TABLE = "CREATE TABLE";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = CREATE_TABLE.concat(" ").concat(TABLE_NAME).concat("(ID INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .concat(COL2).concat(" TEXT").concat(", ").concat(COL3).concat(" TEXT)");
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(IF_TABLE_EXIST.concat(TABLE_NAME));
        onCreate(db);
    }

    public boolean addData(String hesCode, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, hesCode);
        contentValues.put(COL3,name);
        Log.d(TAG,"addData: Adding".concat(hesCode).concat(name).concat("to").concat(TABLE_NAME));
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
            return false;
            else
                return true;
    }


    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }
}
