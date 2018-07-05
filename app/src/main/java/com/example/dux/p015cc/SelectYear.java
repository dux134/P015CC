package com.example.dux.p015cc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.dux.p015cc.utils.PrefManager;

public class SelectYear extends AppCompatActivity {
    private PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_year);

        prefManager = new PrefManager(getApplicationContext());
        Button firstYear = findViewById(R.id.Iyr);
        Button secondYear = findViewById(R.id.IIyr);
        Button thirdYear = findViewById(R.id.IIIyr);

        firstYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SplashScreen.currentCourse += "1";
                prefManager.setCurentlyEnrolledCourse(SplashScreen.currentCourse);
                prefManager.setEnrolledYear("1");
                prefManager.setFirstTimeLaunch(false);
                startActivity(new Intent(SelectYear.this,SplashScreen.class));
            }
        });
        secondYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SplashScreen.currentCourse += "2";
                prefManager.setCurentlyEnrolledCourse(SplashScreen.currentCourse);
                prefManager.setEnrolledYear("2");
                prefManager.setFirstTimeLaunch(false);
                startActivity(new Intent(SelectYear.this,SplashScreen.class));
            }
        });

        thirdYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SplashScreen.currentCourse += "3";
                prefManager.setCurentlyEnrolledCourse(SplashScreen.currentCourse);
                prefManager.setEnrolledYear("3");
                prefManager.setFirstTimeLaunch(false);
                startActivity(new Intent(SelectYear.this,SplashScreen.class));
            }
        });
    }
}
