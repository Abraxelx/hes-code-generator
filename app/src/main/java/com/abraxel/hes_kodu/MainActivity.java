package com.abraxel.hes_kodu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.abraxel.hes_kodu.about.AboutPageActivity;
import com.abraxel.hes_kodu.entity.HesModel;
import com.abraxel.hes_kodu.loading.LoadingDialog;
import com.abraxel.hes_kodu.recyclerview.RecyclerCardActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    final int REQUEST_CODE = 123;
    final String PHONE_NUMBER = "2023";
    private InterstitialAd interstitialAd;

    EditText tcKimlikNo, serialNo;
    Button senderButton, saveButton, copyButton, showEntries, goQR;
    String smsMessage;
    ConstraintLayout myLayout;
    HesSMSReceiver smsCounter;
    TextView smsWarner, labelCopy, labelAbout;

    //Alert Dialog
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    EditText namePicker;
    Button saveDB;
    HesModel model;
    DatabaseHelper mDatabaseHelper;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_main);
        smsCounter.setCounter(0);

        LoadingDialog loadingDialog = new LoadingDialog(MainActivity.this);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.myAd);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.admob_insterstitial_ad));
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed(){
                super.onAdClosed();
            }
        });




        tcKimlikNo = findViewById(R.id.txtTcNo);
        serialNo = findViewById(R.id.txtSerialNo);
        labelAbout = findViewById(R.id.about);
        senderButton = findViewById(R.id.btnSender);
        smsWarner = findViewById(R.id.messageWarner);
        showEntries = findViewById(R.id.btnShowEntries);
        goQR = findViewById(R.id.btnGoQR);
        goQR.setVisibility(View.GONE);
        smsWarner.setVisibility(View.GONE);
        labelCopy = findViewById(R.id.labelCopy);
        labelCopy.setVisibility(View.GONE);
        copyButton = findViewById(R.id.btnCopyHes);
        copyButton.setVisibility(View.GONE);
        myLayout = findViewById(R.id.myLayout);
        saveButton = findViewById(R.id.btnSaveHES);
        saveButton.setVisibility(View.GONE);
        smsCounter = new HesSMSReceiver();

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) !=
                PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)||
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS) ||
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)){
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        this);
                builder.setTitle("Lütfen İzinleri Onaylayınız.");
                builder.setMessage("Mesaj Gönderme, Mesaj Okuma ve Mesaj Alma izinlerini Onaylayınız.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(
                                MainActivity.this,
                                new String[] {Manifest.permission.SEND_SMS,
                                Manifest.permission.READ_SMS,
                                Manifest.permission.RECEIVE_SMS}, REQUEST_CODE
                        );
                    }
                });
                builder.setNegativeButton("Cancel", null);
                AlertDialog alertDialog =  builder.create();
                alertDialog.show();
            } else {
                ActivityCompat.requestPermissions(
                        this,
                        new String[] {Manifest.permission.SEND_SMS,
                                Manifest.permission.READ_SMS,
                                Manifest.permission.RECEIVE_SMS}, REQUEST_CODE
                );
            }
        }



        senderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsMessage ="HES".concat(" ").concat(tcKimlikNo.getText().toString()).concat(" ").concat(serialNo.getText().toString())
                        .concat(" ").concat("540");
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) +
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS) +
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECEIVE_SMS) ==
                PackageManager.PERMISSION_GRANTED){
                    if(tcKimlikNo.getText().length()< 11 || serialNo.getText().length() < 4){
                        Toast.makeText(getApplicationContext(), R.string.lessNumber, Toast.LENGTH_LONG).show();
                    }else{
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(PHONE_NUMBER, null, smsMessage, null, null);
                        tcKimlikNo.setText("");
                        serialNo.setText("");
                        smsWarner.setVisibility(View.VISIBLE);
                        closeKeyboard();
                        loadingDialog.startLoadingDialog();



                        new CountDownTimer(25000, 1000){

                            @Override
                            public void onTick(long millisUntilFinished) {
                                if(smsCounter.getCounter() > 1){
                                    loadingDialog.dismissDialog();
                                    saveButton.setVisibility(View.VISIBLE);
                                    smsWarner.setText("HES KODU BAŞARIYLA ALINDI");
                                    labelCopy.setVisibility(View.VISIBLE);
                                    copyButton.setText(hesParser());
                                    copyButton.setVisibility(View.VISIBLE);
                                    goQR.setVisibility(View.VISIBLE);
                                    smsCounter.setCounter(0);
                                }
                            }
                            @Override
                            public void onFinish() {
                                smsWarner.setVisibility(View.GONE);
                                smsCounter.setCounter(0);

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
                if(interstitialAd.isLoaded()){
                    interstitialAd.show();
                }
                createSaveDialog();
                saveButton.setEnabled(false);
            }
        });
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("button", copyButton.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getApplicationContext(), "BAŞARIYLA KOPYALANDI", Toast.LENGTH_LONG).show();

            }
        });
        labelAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(interstitialAd.isLoaded()){
                    interstitialAd.show();
                }
                Intent intent = new Intent(MainActivity.this, AboutPageActivity.class);
                startActivity(intent);

            }
        });
        goQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToURL(qrParser());

            }
        });

        showEntries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecyclerCardActivity.class);
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

    public String smsReader(){
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms"),null, null, null,null);
        cursor.moveToFirst();
        return cursor.getString(12);
    }

    private String hesParser(){
        String text = smsReader();
        String [] parsed = text.split(" ");
        String hesCode = parsed[0];
        if(hesCode.indexOf('*') != -1){
            try {
                Thread.sleep(3000);
                String trueText = smsReader();
                String [] trueParsed = trueText.split(" ");
                String trueHesCode = trueParsed[0];
                return trueHesCode;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return hesCode;
    }

    private String qrParser(){
        String message = smsReader();
        String [] parsed = message.split(" ");
        if(parsed[0].indexOf("*") != -1){
            String trueMessage = smsReader();
            String [] trueParsed = trueMessage.split(" ");
            return trueParsed[6];
        }
        return parsed[6];
    }

    private void goToURL(String url){
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void createSaveDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View savePopupView = getLayoutInflater().inflate(R.layout.activity_save_hes_codes, null);
        namePicker = savePopupView.findViewById(R.id.txtName);
        saveDB = savePopupView.findViewById(R.id.btnSaveDB);
        model = new HesModel();
        mDatabaseHelper = new DatabaseHelper(this);

        dialogBuilder.setView(savePopupView);
        dialog = dialogBuilder.create();
        dialog.show();


        saveDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    if (!namePicker.getText().equals("")) {
                        smsWarner.setVisibility(View.GONE);
                        saveDB.setEnabled(false);
                        model = new HesModel();
                        model.setName(namePicker.getText().toString());
                        model.setHesCode(hesParser());
                        model.setQrCode(qrParser());
                        mDatabaseHelper.addData(model);
                        Snackbar.make(v, "Hes Kodu Başarıyla Kaydedildi", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        dialog.cancel();
                    }
                }
            }
        });
        namePicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(serialNo.getText().length()>1) {
                    copyButton.setText("");
                    copyButton.setVisibility(View.GONE);
                    goQR.setVisibility(View.GONE);
                    smsWarner.setVisibility(View.GONE);
                    saveButton.setEnabled(true);
                    saveButton.setVisibility(View.GONE);
                    smsCounter.setCounter(0);
                    labelCopy.setVisibility(View.GONE);
                    return false;
                }
                return false;
            }

        });

        serialNo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(namePicker.getText().length()>1) {
                    copyButton.setText("");
                    copyButton.setVisibility(View.GONE);
                    goQR.setVisibility(View.GONE);
                    smsWarner.setVisibility(View.GONE);
                    saveButton.setEnabled(true);
                    saveButton.setVisibility(View.GONE);
                    labelCopy.setVisibility(View.GONE);
                    smsCounter.setCounter(0);
                    return false;
                }
                return false;
            }
        });


    }


}