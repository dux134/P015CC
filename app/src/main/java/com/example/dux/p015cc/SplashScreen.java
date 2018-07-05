package com.example.dux.p015cc;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.dux.p015cc.home.Home;
import com.example.dux.p015cc.home.NewsAndAlertDataModel;
import com.example.dux.p015cc.utils.PrefManager;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ir.apend.slider.model.Slide;

import static android.support.constraint.Constraints.TAG;
import static com.example.dux.p015cc.home.NewsAndAlert_ViewAll.list;

public class SplashScreen extends AppCompatActivity {
    public static String currentCourse = "";
    private PrefManager prefManager;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        prefManager = new PrefManager(SplashScreen.this);
        loadAddId();

//        Toast.makeText(getApplicationContext(),prefManager.getAdmobId()+" \n "+prefManager.getAdmobIdDux(),Toast.LENGTH_LONG).show();

        currentCourse = prefManager.getCurentlyEnrolledCourse();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadSliderImageFromFireStore();
                loadNotificationFromFireStore();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (prefManager.isFirstTimeLaunch()) {
                            startActivity(new Intent(SplashScreen.this, SelectCourse.class));
                            finish();
                        } else {
                            startActivity(new Intent(SplashScreen.this, Dashboard.class));
                            finish();
                        }

                    }
                },1000);
            }
        });


    }

    private void loadSliderImageFromFireStore() {
        final FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        db.collection("image_slider").orderBy("priority", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen error", e);
                            return;
                        }


                        assert querySnapshot != null;
                        int count = 0;
                        for (DocumentChange change : querySnapshot.getDocumentChanges()) {
                            if (change.getType() == DocumentChange.Type.ADDED) {

                                String url = change.getDocument().get("url").toString();
                                Integer priority =Integer.valueOf(change.getDocument().get("priority").toString());
                                Home.slideList.add(new Slide(priority,url, getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));

                            }

                            String source = querySnapshot.getMetadata().isFromCache() ?
                                    "local cache" : "server";
                            Log.d(TAG, "Data fetched from " + source);
                        }
                    }
                });
    }
    private void loadNotificationFromFireStore() {
        list.clear();
        final FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        db.collection("news_and_alert").orderBy("time", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen error", e);
                            return;
                        }
                        assert querySnapshot != null;
                        for (DocumentChange change : querySnapshot.getDocumentChanges()) {
                            if (change.getType() == DocumentChange.Type.ADDED) {

                                String link = change.getDocument().get("link").toString();
                                String title = change.getDocument().get("title").toString();
                                String description = change.getDocument().get("description").toString();
                                String type = change.getDocument().get("type").toString();

                                list.add(new NewsAndAlertDataModel(title,description,link,type));

                            }

                            String source = querySnapshot.getMetadata().isFromCache() ?
                                    "local cache" : "server";
                            Log.d(TAG, "Data fetched from " + source);
                        }
                    }
                });
    }
    private void loadAddId() {
        final FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        db.collection("admob_add")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen error", e);
                            return;
                        }
                        assert querySnapshot != null;
                        for (DocumentChange change : querySnapshot.getDocumentChanges()) {
                            if (change.getType() == DocumentChange.Type.ADDED) {

                                String id = change.getDocument().get("id").toString();
                                String type = change.getDocument().get("type").toString();

                                if(type.equalsIgnoreCase("dux"))
                                    prefManager.setAdmobIdDux(id);

                                else
                                    prefManager.setAdmobId(id);


                            }

                            String source = querySnapshot.getMetadata().isFromCache() ?
                                    "local cache" : "server";
                            Log.d(TAG, "Data fetched from " + source);
                        }
                    }
                });
    }
}
