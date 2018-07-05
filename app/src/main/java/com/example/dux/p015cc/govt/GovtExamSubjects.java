package com.example.dux.p015cc.govt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.dux.p015cc.R;
import com.example.dux.p015cc.SubjectsUnit;
import com.example.dux.p015cc.ccsu.CCSUnotesDataModel;
import com.example.dux.p015cc.ccsu.CCSUnotesMyAdapter;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;
import static com.example.dux.p015cc.utils.CheckNetworkConnection.isConnectionAvailable;

public class GovtExamSubjects extends AppCompatActivity {
    public static String exam;
    private RecyclerView recyclerView1;
    private RecyclerView.Adapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<CCSUnotesDataModel> list = new ArrayList<>();
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_govt_exam_subjects);
        if(!isConnectionAvailable(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "You are currently offline, Please connect to internet", Toast.LENGTH_LONG).show();
        }

        progressDialog = new ProgressDialog(GovtExamSubjects.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        loadListFromFireStore();

        Toolbar toolbar = (Toolbar) findViewById(R.id.govtExamSubjectToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        recyclerView1 = findViewById(R.id.govtSubjectRecyclerView);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new CCSUnotesMyAdapter(list, new CCSUnotesMyAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

                SubjectsUnit.subjectName = list.get(position).getTitle();
                startActivity(new Intent(GovtExamSubjects.this,SubjectsUnit.class));
            }
        },this);
        recyclerView1.setAdapter(adapter);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());

    }

    private void loadListFromFireStore() {
        list.clear();
        final FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        db.collection("govt_exam_subjects").whereEqualTo(exam,true)
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

                                String imageUrl = change.getDocument().get("image_url").toString();
                                String title = change.getDocument().get("title").toString();

                                list.add(new CCSUnotesDataModel(title,imageUrl));
                                refereshContent();
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

    private void refereshContent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

//                mySwipeRefreshLayout.setRefreshing(false);
            }
        }, 0);
        adapter.notifyDataSetChanged();
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
