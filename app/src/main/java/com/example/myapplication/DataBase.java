package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {

    private static String name = "DataBase";
    SQLiteDatabase DataBase;

    public DataBase(@Nullable Context context) {
        super(context, name, null, 1);
//        this.productDatabase = productDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user (_id integer primary key AUTOINCREMENT ," +
                "NAME TEXT,EMAIL TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    public List<MyDataModel> getAllData() {
        List<MyDataModel> dataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user", null);
        if (cursor.moveToFirst()) {
            do {
                MyDataModel data = new MyDataModel();
                data.setEmail(cursor.getString(2));
                data.setName(cursor.getString(1));
                dataList.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dataList;
    }



//    public void insertUser(String name, String email, String password, String birthdate) {
//        ContentValues usersValues = new ContentValues();
//        usersValues.put("NAME", name);
//        usersValues.put("EMAIL", email);
//        usersValues.put("PASSWORD", password);
//        usersValues.put("BIRTHDATE", birthdate);
//        plantDB = getWritableDatabase();
//        plantDB.insert("USERS", null, usersValues);
//        plantDB.close();
//    }
//
//    public Cursor User_found() {
//
//        plantDB = getReadableDatabase();
//        String[] Row_user = {"NAME", "EMAIL", "PASSWORD", "BIRTHDATE"};
//        Cursor user = plantDB.query("USERS", Row_user, null, null, null, null, null);
//        if (user != null)
//            user.moveToFirst();
//        plantDB.close();
//        return user;
//    }
//    public Boolean UpdatePassword(String user,String password)
//    {
//        plantDB = getWritableDatabase();
//        ContentValues usersValues = new ContentValues();
//        usersValues.put("PASSWORD",password);
//        long result;
//        result = plantDB.update("USERS",usersValues,"NAME =?",new String[]{user});
//        if(result==-1)
//            return false;
//        else
//            return true;
//    }
}

