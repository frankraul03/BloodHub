package com.example.rp.bloodhub;

import android.provider.BaseColumns;

/**
 * Created by rp on 5/20/17.
 */

public class TableData {

    public TableData()
    {

    }
    //Base columns interface is implemented to use of keys in future

    public static abstract class TableInfo implements BaseColumns
    {

        public static final String USER_NAME = "user_name";
        public static final String USER_PASS = "user_pass";
        public static final String DATABASE_NAME = "user_info";
        public static final String TABLE_NAME = "reg_info";

    }

}
