package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FavDB extends SQLiteOpenHelper {
    private static String databaseName = "FavDB";
    SQLiteDatabase FavDB;
    public FavDB(Context context){
        super(context , databaseName , null , 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table Favorite(RowID INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,username TEXT , p_image BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists Favorite");
        onCreate(sqLiteDatabase);
    }



    public void insertFav(String name, String userName, Bitmap p_image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        p_image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        // Check if the item already exists before inserting
        if (!isFavExists(p_image, userName ,name)) {
            ContentValues usersValues = new ContentValues();
            usersValues.put("name", name);
            usersValues.put("username", userName);
            usersValues.put("p_image", byteArray);

            SQLiteDatabase FavDB = getWritableDatabase(); // Ensure you are getting a writable database

            FavDB.insert("Favorite", null, usersValues);
            FavDB.close();
        }
    }

    private boolean isFavExists(Bitmap p_image, String userName, String Name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Favorite WHERE p_image = ? AND username = ? AND name = ?", new String[]{p_image.toString(), userName,Name});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    //    public List<FavContent> getAllData() {
//        List<FavContent> dataList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        User user = new User();
//        String u_name = user.getUserName();
//        Cursor cursor = db.rawQuery("SELECT * FROM Favorite WHERE username = ?", new String[]{u_name});
//        try {
//            if (cursor != null && cursor.moveToFirst()) {
//                do {
//                    FavContent data = new FavContent();
//                    data.setP_name(cursor.getString(1));
//                    data.setP_image(cursor.getBlob(3));
//                    dataList.add(data);
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//        return dataList;
//    }
public List<FavContent> getAllData(String userName) {
    List<FavContent> dataList = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();

    // Check if u_name is null
    if (userName == null) {
        Log.e("getAllData", "Username is null");
        return dataList; // Returning empty list if username is null
    }

    Cursor cursor = db.rawQuery("SELECT * FROM Favorite WHERE username = ?", new String[]{userName});
    try {
        if (cursor != null && cursor.moveToFirst()) {
            do {
                FavContent data = new FavContent();
                data.setP_name(cursor.getString(1));
                data.setP_image(cursor.getBlob(3));
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
