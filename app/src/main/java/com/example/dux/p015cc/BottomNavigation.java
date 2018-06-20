package com.example.dux.p015cc;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.dux.p015cc.ccsu_notes.CCSUnotes;
import com.example.dux.p015cc.govt_exam.GovtExam;
import com.example.dux.p015cc.home.Home;

public class BottomNavigation extends AppCompatActivity {

    public static Fragment selectFragment;
    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            selectFragment = null;
            switch (item.getItemId()) {

                case R.id.navigation_item1:
                    selectFragment = Home.getInstance();
                    break;

                case R.id.navigation_item2:
                    selectFragment = new CCSUnotes();
                    break;


                case R.id.navigation_item3:
                    selectFragment = new GovtExam();
                    break;
//
//                case R.id.navigation_offline_download:
//                    selectFragment = new OfflineDownload();
//                    break;

            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.bottomNavigationConstraintLayout, selectFragment);
            transaction.commit();
            return true;
        }
    };
}
