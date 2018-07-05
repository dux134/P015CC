package com.example.dux.p015cc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.dux.p015cc.utils.PrefManager;

public class SelectCourse extends AppCompatActivity {

    private PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course);

        prefManager = new PrefManager(getApplicationContext());
        Button bsc = findViewById(R.id.bscButton);
        Button bcom = findViewById(R.id.bcombutton);
        Button ba = findViewById(R.id.baButton);

        bsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SplashScreen.currentCourse = "bsc";
                prefManager.setEnrolledCourse("bsc");
                startActivity(new Intent(SelectCourse.this,SelectYear.class));
            }
        });

        bcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SplashScreen.currentCourse = "bcom";
                prefManager.setEnrolledCourse("bcom");
                startActivity(new Intent(SelectCourse.this,SelectYear.class));
            }
        });

        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SplashScreen.currentCourse = "ba";
                prefManager.setEnrolledCourse("ba");
                startActivity(new Intent(SelectCourse.this,SelectYear.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(prefManager.isFirstTimeLaunch())
            System.exit(0);
        else
            startActivity(new Intent(SelectCourse.this,SplashScreen.class));
    }
}
