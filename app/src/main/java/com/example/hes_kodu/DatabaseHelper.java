package com.example.hes_kodu;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "hescode_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "hesCode";
    private static final String IF_TABLE_EXIST = "DROP IF TABLE EXISTS";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE".concat(TABLE_NAME).concat("(ID INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .concat(COL2).concat("TEXT");
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(IF_TABLE_EXIST.concat(TABLE_NAME));
        onCreate(db);
    }

    public boolean addData(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);
        Log.d(TAG,"addData: Adding".concat(item).concat("to").concat(TABLE_NAME));
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
            return false;
            else
                return true;
    }
}
