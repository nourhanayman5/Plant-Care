package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
//    RecyclerView recyclerView;
//    List<MyDataModel> dataList;
//    DataBase dbHelper;
//    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        recyclerView = findViewById(R.id.recyclerview);
//        dataList = new ArrayList<>();
//        dbHelper = new DataBase(this);
//        dataList = dbHelper.getAllData();
//        adapter = new MyAdapter(this, dataList);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // Assume dbHelper is an instance of your SQLiteOpenHelper subclass
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//
//// Create a ContentValues object to store the values
//        String[] names = {"John", "Alice", "Bob","Nour","Marwa"};
//        String[] emails = {"John@gmail", "Alice@gmail", "Bob@gmail","Nour@gmail","Marwa@gmail"};
//
//// Loop through the static data and insert each row into the database
//        for (int i = 0; i < names.length; i++) {
//            // Create a ContentValues object to store the values
//            ContentValues values = new ContentValues();
//            values.put("NAME", names[i]); // Assuming "name" is the column name
//            values.put("EMAIL", emails[i]);
//
//
//// Insert the data into the table
//            long newRowId = db.insert("user", null, values);
//
//// Check if the insertion was successful
//            if (newRowId != -1) {
//                // Data inserted successfully
//                Log.d("Insertion", "Data inserted successfully. Row ID: " + newRowId);
//            } else {
//                // Failed to insert data
//                Log.e("Insertion", "Failed to insert data.");
//            }
//
//        }
//// Close the database connection
//            db.close();
//

    }
}