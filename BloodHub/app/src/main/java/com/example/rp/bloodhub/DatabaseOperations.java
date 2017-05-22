package com.example.rp.bloodhub;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.R.attr.version;

/**
 * Created by rp on 5/20/17.
 */

public class DatabaseOperations extends SQLiteOpenHelper {
    //2.
    public static final int database_version = 1;
    //3.
    public String CREATE_QUERY = "CREATE TABLE" + TableData.TableInfo.TABLE_NAME+"("+

            TableData.TableInfo.USER_NAME+"TEXT," +

            TableData.TableInfo.USER_PASS+"TEXT);";

    //1.
    public DatabaseOperations(Context context) {
    //4.
        super(context, TableData.TableInfo.DATABASE_NAME, null, database_version);
        Log.d("Database Operations","Database Created Successfully");
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {
    //5.
        sdb.execSQL(CREATE_QUERY);
        Log.d("Database Operations","Table Created Successfully");

    }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    public void inserttodb(DatabaseOperations dop, String name, String pass){
        SQLiteDatabase SQ = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableData.TableInfo.USER_NAME, name);
        cv.put(TableData.TableInfo.USER_PASS, pass);
        long k = SQ.insert(TableData.TableInfo.TABLE_NAME,null,cv);
        Log.d("Database Operations","One row inserted");
    }
}
