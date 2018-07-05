package com.example.dux.p015cc.ccsu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dux.p015cc.R;
import com.example.dux.p015cc.SplashScreen;
import com.example.dux.p015cc.SubjectsUnit;
import com.example.dux.p015cc.utils.MyFragment;
import com.example.dux.p015cc.utils.PrefManager;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;
import static com.example.dux.p015cc.utils.CheckNetworkConnection.isConnectionAvailable;

public class CCSUnotes extends MyFragment {

    private RecyclerView recyclerView1;
    private RecyclerView.Adapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<CCSUnotesDataModel> list = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ccsunotes, container, false);

        if(!isConnectionAvailable(getContext())) {
            Toast.makeText(getContext(), "You are currently offline, Please connect to internet", Toast.LENGTH_LONG).show();
        }

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        PrefManager prefManager = new PrefManager(getActivity());
        String str = "You are currently enrolled in " + (prefManager.getEnrolledCourse()).toUpperCase()+" ";
        String yr = prefManager.getEnrolledYear();
        if(yr.equalsIgnoreCase("1"))
            str += "1st year";

        if(yr.equalsIgnoreCase("2"))
            str += "2nd year";

        if(yr.equalsIgnoreCase("3"))
            str += "3rd year";
        TextView enrolledCourse = view.findViewById(R.id.enrolledcourse);
        enrolledCourse.setText(str);


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadListFromFireStore();
            }
        });

        recyclerView1 = view.findViewById(R.id.ccsu_notes_recycler_view);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new CCSUnotesMyAdapter(list, new CCSUnotesMyAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                SubjectsUnit.subjectName = list.get(position).getTitle();
                startActivity(new Intent(getActivity(),SubjectsUnit.class));
            }
        },getContext());
        recyclerView1.setAdapter(adapter);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    private void loadListFromFireStore() {
        list.clear();
        final FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        db.collection(SplashScreen.currentCourse)
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
}

