package com.abraxel.hes_kodu.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.abraxel.hes_kodu.DatabaseHelper;
import com.abraxel.hes_kodu.R;
import com.abraxel.hes_kodu.entity.HesModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerCardActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HesAdapter adapter;
    private List<HesModel> model;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_card);

        recyclerView = findViewById(R.id.rv_card);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabaseHelper = new DatabaseHelper(this);
        model = new ArrayList<>();
        model = dataPopulator(model);
        adapter = new HesAdapter(this, model);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new HesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                model.get(position);
            }

            @Override
            public void onShareItemClick(int position) {
                String hesCode = model.get(position).getHesCode();
                shareHes(hesCode);

            }

            @Override
            public void onCopyClick(int position) {
               String hesCode =  model.get(position).getHesCode();
               copyHes(hesCode);
            }

            @Override
            public void onGoQRClick(int position) {
                String qrLink = model.get(position).getQrCode();
                goToURL(qrLink);

            }

            @Override
            public void onDeleteClick(int position) {
                mDatabaseHelper.deleteEntry(model.get(position));
                model.remove(model.get(position));
                adapter.notifyItemRemoved(position);
            }

        });


    }

    private List<HesModel> dataPopulator(List<HesModel> model){
        HesModel hesModel = new HesModel();
        Cursor data = mDatabaseHelper.getData();
        while (data.moveToNext()){
            hesModel.setID(data.getString(0));
            hesModel.setName (data.getString(1));
            hesModel.setHesCode(data.getString(2));
            hesModel.setQrCode(data.getString(3));
            model.add(hesModel);
        }
        return model;
    }

    private void goToURL(String url){
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void  shareHes(String HesCode){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, HesCode);
        intent.setType("text/plain");
        intent = Intent.createChooser(intent, "Paylaş");
        startActivity(intent);

    }

    private void copyHes(String hesCode){
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("button", hesCode);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(getApplicationContext(), "BAŞARIYLA KOPYALANDI", Toast.LENGTH_LONG).show();
    }
}