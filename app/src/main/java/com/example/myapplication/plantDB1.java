package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class plantDB1 extends SQLiteOpenHelper {

    private static String name = "plantDB1";
    SQLiteDatabase plantDB1;

    public plantDB1(@Nullable Context context) {
        super(context, name, null, 1);
//        this.productDatabase = productDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table PLANTHOME (_id integer primary key AUTOINCREMENT ," +
                "NAME TEXT,IMAGE BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PLANTHOME");
        onCreate(db);
    }


//    public List<homeContent> getAllData() {
//        List<homeContent> dataList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM PLANTHOME", null);
//        if (cursor.moveToFirst()) {
//            do {
//                homeContent data = new homeContent();
//                data.setPlantname(cursor.getString(1));
//                data.setPlantimage(cursor.getBlob(2));
//                dataList.add(data);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return dataList;
//    }

    public List<homeContent> getAllData() {
        List<homeContent> dataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PLANTHOME", null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    homeContent data = new homeContent();
                    data.setPlantname(cursor.getString(1));
//                    data.setPlantimage(cursor.getBlob(2));
                    dataList.add(data);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return dataList;
    }


}
