package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class DatabaseClass extends SQLiteOpenHelper {
    private static String databaseName = "Database";
    SQLiteDatabase plantDataBase;
    public DatabaseClass(Context context){
        super(context , databaseName , null , 1);

    }
    private static final String USER = "users";
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create Table users(name TEXT,username TEXT primary key, password TEXT, BIRTHDATE TEXT,profileImage BLOB)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop Table if exists users");

        onCreate(sqLiteDatabase);
    }





    public void change_profile_photo(Bitmap newImage, String username){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        newImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        // Open the database for writing
        SQLiteDatabase db = this.getWritableDatabase();

        // Update the image in the database
        ContentValues values = new ContentValues();
        values.put("profileImage", byteArray); // Assuming "image_data" is the column name where you store the image
        String whereClause = "username = ?"; // Assuming "id" is the primary key column
        String[] whereArgs = {username}; // Assuming you're updating the image of the row with id 1
        db.update("users", values, whereClause, whereArgs);

        // Close the database connection
        db.close();
    }
    public Bitmap getImageProfileFromDatabase(String username) {
        Bitmap bitmap = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Query the database to get the image data
            cursor = db.query("users", new String[]{"profileImage"}, "username = ?", new String[]{username}, null, null, null);

            // Check if cursor has data
            if (cursor != null && cursor.moveToFirst()) {
                // Retrieve the byte array representing the image data
                byte[] imageData = cursor.getBlob(cursor.getColumnIndexOrThrow("profileImage"));

                // Convert byte array to Bitmap
                bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            }
        } finally {
            // Close cursor and database
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return bitmap;
    }
    public boolean updatePassword(String username, String password) {
        ContentValues usersValues = new ContentValues();
        usersValues.put("password", password);
        int result = plantDataBase.update("users", usersValues, "username = ?", new String[]{username});
        return result != -1;    }
    public void insertUser(String name, String email, String password, String birthdate) {
        ContentValues usersValues = new ContentValues();
        usersValues.put("name", name);
        usersValues.put("username", email);
        usersValues.put("password", password);
        usersValues.put("BIRTHDATE", birthdate);
        plantDataBase = getWritableDatabase();
        plantDataBase.insert("users", null, usersValues);
        plantDataBase.close();
    }

    public Cursor User_found() {

        plantDataBase = getReadableDatabase();
        String[] Row_user = {"name", "username", "password", "BIRTHDATE"};
        Cursor user = plantDataBase.query("users", Row_user, null, null, null, null, null);
        if (user != null)
            user.moveToFirst();
        plantDataBase.close();
        return user;
    }
    public Boolean UpdatePassword(String user,String password)
    {
        plantDataBase = getWritableDatabase();
        ContentValues usersValues = new ContentValues();
        usersValues.put("password",password);
        long result;
        result = plantDataBase.update("users",usersValues,"name =?",new String[]{user});
        if(result==-1)
            return false;
        else
            return true;
    }

}



