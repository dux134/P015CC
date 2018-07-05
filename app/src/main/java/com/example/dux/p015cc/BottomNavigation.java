package com.example.dux.p015cc;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.dux.p015cc.ccsu.CCSUnotes;
import com.example.dux.p015cc.govt.GovtExam;
import com.example.dux.p015cc.home.Home;
import com.example.dux.p015cc.utils.PrefManager;

public class BottomNavigation extends AppCompatActivity {

    public static Fragment selectFragment;
    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            selectFragment = null;
            switch (item.getItemId()) {

                case R.id.navigation_item1:
                    selectFragment = new Home();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.change_course) {
            startActivity(new Intent(BottomNavigation.this, SelectCourse.class));
            finish();
            return true;
        } else if(id == R.id.feedback) {
            startActivity(new Intent(BottomNavigation.this, Feedback.class));
            finish();
            return true;
        } else if(id == R.id.share_notes) {
            startActivity(new Intent(BottomNavigation.this, ShareNotes.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
