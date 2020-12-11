package com.example.hes_kodu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    final String PHONE_NUMBER = "2023";

    EditText tcKimlikNo, serialNo;
    Button senderButton, saveButton;
    String smsMessage;
    ConstraintLayout myLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tcKimlikNo = findViewById(R.id.txtTcNo);
        serialNo = findViewById(R.id.txtSerialNo);
        senderButton = findViewById(R.id.btnSender);
        myLayout = findViewById(R.id.myLayout);
        saveButton = findViewById(R.id.btnSaveHES);
        saveButton.setVisibility(View.GONE);




        if(!checkPermisson(Manifest.permission.SEND_SMS)){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS},PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECEIVE_SMS},PackageManager.PERMISSION_GRANTED);



        senderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsMessage ="HES".concat(" ").concat(tcKimlikNo.getText().toString()).concat(" ").concat(serialNo.getText().toString())
                        .concat(" ").concat("540");
                if(checkPermisson(Manifest.permission.SEND_SMS) && checkPermisson(Manifest.permission.READ_SMS)&& checkPermisson(Manifest.permission.RECEIVE_SMS)){
                    if(tcKimlikNo.getText().length()< 11 || serialNo.getText().length() < 4){
                        Toast.makeText(getApplicationContext(), R.string.lessNumber, Toast.LENGTH_LONG).show();
                    }else{
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(PHONE_NUMBER, null, smsMessage, null, null);
                        Snackbar mySnack = Snackbar.make(myLayout, R.string.checkMessageBox, Snackbar.LENGTH_LONG);
                        View snackView = mySnack.getView();
                        snackView.setBackgroundColor(Color.parseColor("#FF018786"));
                        mySnack.show();
                        tcKimlikNo.setText("");
                        serialNo.setText("");
                        closeKeyboard();



                        new CountDownTimer(10000, 1000){

                            @Override
                            public void onTick(long millisUntilFinished) {
                            }

                            @Override
                            public void onFinish() {
                                saveButton.setVisibility(View.VISIBLE);

                            }
                        }.start();
                    }
                }else{
                    if(!checkPermisson(Manifest.permission.RECEIVE_SMS)){
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.RECEIVE_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
                    }
                    Toast.makeText(getApplicationContext(), R.string.permissionInvalid, Toast.LENGTH_LONG).show();

                }
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SaveHesCodes.class);
                startActivity(intent);
            }
        });
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public boolean checkPermisson(String permisson){
        int check = ContextCompat.checkSelfPermission(this, permisson);
        return (check == PackageManager.PERMISSION_GRANTED);
    }


}