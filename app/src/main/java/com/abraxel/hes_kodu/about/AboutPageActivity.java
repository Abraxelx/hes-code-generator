package com.abraxel.hes_kodu.about;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.abraxel.hes_kodu.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);

        Element versionElement = new Element();
        versionElement.setTitle("Version 1.0.0");

        String Desc = "Bu uygulama Ahmet Şahin tarafından tasarlanıp, Halil Şahin tarafından kodlanmıştır. " +
                "Uygulamanın amacı;\n" +
                "HAYAT EVE SIĞAR uygulamasının uzun süreli HES kodu sorgularını daha hızlı ve daha pratik hale getirmektir. \n" +
                "Uygulamamız Hayat eve sığar " +
                "uygulamasının aksine arkaplanda sürekli çalışıp sizleri rahatsız etmez.\n Soru ve sorunlarınız için lütfen iletişime geçmekten " +
                "çekinmeyiniz";

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .enableDarkMode(false)
                .setImage(R.mipmap.hes_icon_new)
                .setDescription(Desc)
                .addItem(versionElement)
                .addGroup("Bize Ulaşın")
                .addEmail("xelilim@hotmail.com")
                .addWebsite("https://xelcode.blogspot.com/")
                .addPlayStore("ccom.abraxel.cryptocurrency")
                .addGitHub("abraxelx")
                .create();
        setContentView(aboutPage);


    }
}