package com.example.hes_kodu;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SaveHesCodes extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    EditText namePicker;
    Button saveDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_hes_codes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabaseHelper = new DatabaseHelper(this);
        namePicker = findViewById(R.id.txtName);
        saveDB = findViewById(R.id.btnSaveDB);

        saveDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!namePicker.getText().equals("")) {
                    mDatabaseHelper.addData(smsReader(), namePicker.getText().toString());
                    Snackbar.make(view, "Hes Kodu Başarıyla Kaydedildi", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent intent = new Intent(SaveHesCodes.this, ListDataActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


    private String smsReader(){
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms"),null, null, null,null);
        cursor.moveToFirst();
        return cursor.getString(12);
    }


}