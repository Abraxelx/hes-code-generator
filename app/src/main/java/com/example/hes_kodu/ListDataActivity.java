package com.example.hes_kodu;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {


    DatabaseHelper mDatabaseHelper;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);
        mDatabaseHelper = new DatabaseHelper(this);
        mListView = findViewById(R.id.listView);
        dataPopulator();


    }

    private void dataPopulator(){
        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData  = new ArrayList<>();
        while (data.moveToNext()){
            listData.add(data.getString(1));
            listData.add(data.getString(2));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

    }
}