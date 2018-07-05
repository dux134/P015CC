package com.example.dux.p015cc;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.dux.p015cc.utils.MyActivity;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;
import static com.example.dux.p015cc.home.NewsAndAlert_ViewAll.list;
import static com.example.dux.p015cc.utils.CheckNetworkConnection.isConnectionAvailable;

public class SubjectsUnit extends MyActivity {
    public static String subjectName;

    private ArrayList<SubjectsUnitDataModel> listNotes = new ArrayList<>();
    private RecyclerView recyclerViewNotes;
    private RecyclerView recyclerViewVideos;
    private RecyclerView.Adapter adapterNotes;
    private RecyclerView.Adapter adapterVideos;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<SubjectsUnitDataModel> listVideos = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects_unit);

        if(!isConnectionAvailable(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "You are currently offline, Please connect to internet", Toast.LENGTH_LONG).show();
        }

        progressDialog = new ProgressDialog(SubjectsUnit.this);
        progressDialog.setMessage("Loading Notes...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        loadAdvertisement();
        loadListFromFireStore();
        Toolbar toolbar = (Toolbar) findViewById(R.id.subjectUnitToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        recyclerViewNotes = findViewById(R.id.notes_recycler_item);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        adapterNotes = new SubjectsUnitMyAdapter(listNotes, new SubjectsUnitMyAdapter.RecyclerClickListener() {
            @Override
            public void onClick(View view, int adapterPosition) {
                if(!isConnectionAvailable(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "You are currently offline, Please connect to internet", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(listNotes.get(adapterPosition).getLink()), "application/pdf");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Log.d("l", "l");
                }

                showMyAdd(intent);

            }
        },"notes");
        recyclerViewNotes.setAdapter(adapterNotes);
        recyclerViewNotes.setItemAnimator(new DefaultItemAnimator());


        recyclerViewVideos = findViewById(R.id.videos_recycler_item);
        recyclerViewVideos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterVideos = new SubjectsUnitMyAdapter(listVideos, new SubjectsUnitMyAdapter.RecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(!isConnectionAvailable(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "You are currently offline, Please connect to internet", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(listVideos.get(position).getLink()));
                startActivity(i);
                showMyAdd(i);
            }
        },"videos");
        recyclerViewVideos.setAdapter(adapterVideos);
        recyclerViewVideos.setItemAnimator(new DefaultItemAnimator());


    }

    private void loadListFromFireStore() {

        listNotes.clear();
        final FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        db.collection("bcom1").document(subjectName).collection("notes")

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

//                                list.add(new CCSUnotesDataModel(title,imageUrl));
                                listNotes.add(new SubjectsUnitDataModel(title,link));
                                refereshContentNotes();
                            }

                            String source = querySnapshot.getMetadata().isFromCache() ?
                                    "local cache" : "server";
                            Log.d(TAG, "Data fetched from " + source);
                        }
                        progressDialog.setMessage("Loading Videos...");
                    }
                });



        listVideos.clear();
        db.setFirestoreSettings(settings);

        db.collection("bcom1").document(subjectName).collection("videos")

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

//                                list.add(new CCSUnotesDataModel(title,imageUrl));
                                listVideos.add(new SubjectsUnitDataModel(title,link));
                                refereshContentVideos();
                            }

                            String source = querySnapshot.getMetadata().isFromCache() ?
                                    "local cache" : "server";
                            Log.d(TAG, "Data fetched from " + source);
                        }
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                            }
                        },500);

                    }
                });
    }

    private void refereshContentNotes() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

//                mySwipeRefreshLayout.setRefreshing(false);
            }
        }, 0);
        adapterNotes.notifyDataSetChanged();
    }

    private void refereshContentVideos() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

//                mySwipeRefreshLayout.setRefreshing(false);
            }
        }, 0);
        adapterVideos.notifyDataSetChanged();
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
