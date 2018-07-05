package com.example.dux.p015cc.home;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.dux.p015cc.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.dux.p015cc.utils.CheckNetworkConnection.isConnectionAvailable;

public class NewsAndAlert_ViewAll extends AppCompatActivity {
    private RecyclerView recyclerView1;
    private RecyclerView.Adapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static ArrayList<NewsAndAlertDataModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_and_alert__view_all);

        if(!isConnectionAvailable(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "You are currently offline, Please connect to internet", Toast.LENGTH_LONG).show();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.newsAndAlertToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        recyclerView1 = findViewById(R.id.news_viewall_recyclerview);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new NewsAndAlertMyAdapter(list, new NewsAndAlertMyAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(!isConnectionAvailable(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "You are currently offline, Please connect to internet", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (list.get(position).getNotificationType().equalsIgnoreCase("pdf")) {

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(list.get(position).getNotificationUrl()), "application/pdf");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Log.d("l", "l");
                    }

                } else if (list.get(position).getNotificationType().equalsIgnoreCase("www")) {

                    String url = list.get(position).getNotificationUrl();
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setPackage("com.android.chrome");
                    try {
                        startActivity(i);
                    } catch (ActivityNotFoundException e) {
                        // Chrome is probably not installed
                        // Try with the default browser
                        i.setPackage(null);
                        startActivity(i);
                    }
                }
            }
        },"NewsAndAlert");
        recyclerView1.setAdapter(adapter);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
