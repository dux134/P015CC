package com.example.dux.p015cc.home;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.dux.p015cc.Feedback;
import com.example.dux.p015cc.R;
import com.example.dux.p015cc.SelectCourse;
import com.example.dux.p015cc.ShareNotes;
import com.example.dux.p015cc.utils.MyFragment;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Query.Direction;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;

import static android.support.constraint.Constraints.TAG;
import static com.example.dux.p015cc.home.NewsAndAlert_ViewAll.list;
import static com.example.dux.p015cc.utils.CheckNetworkConnection.isConnectionAvailable;

public class Home extends MyFragment {

    private RecyclerView recyclerView1;
    private RecyclerView.Adapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Slider slider;
    public static List<Slide> slideList = new ArrayList<>();
    View view;
    private ScrollView scrollView;

    private ImageView changeCourse,uploadNotes,feedback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        if(!isConnectionAvailable(getContext())) {
            Toast.makeText(getContext(), "You are currently offline, Please connect to internet", Toast.LENGTH_LONG).show();
        }

        loadAdvertisement();


        scrollView = view.findViewById(R.id.homeScrollView);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        slider = view.findViewById(R.id.slider);
        loadScreen();

        recyclerView1 = view.findViewById(R.id.home_news_recyclerView);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new NewsAndAlertMyAdapter(list, new NewsAndAlertMyAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(!isConnectionAvailable(getContext())) {
                    Toast.makeText(getContext(), "You are currently offline, Please connect to internet", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (list.get(position).getNotificationType().equalsIgnoreCase("pdf")) {

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(list.get(position).getNotificationUrl()), "application/pdf");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Log.d("l", "l");
                    }

                    showMyAdd(intent);

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
                    showMyAdd(i);
                }

            }
        },"home");
        recyclerView1.setAdapter(adapter);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        ImageView ccsuWebsite = view.findViewById(R.id.ccsuWebsite);
        ccsuWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isConnectionAvailable(getContext())) {
                    Toast.makeText(getContext(), "You are currently offline, Please connect to internet", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.ccsuniversity.ac.in/default.htm"));
                startActivity(i);

                showMyAdd(i);
            }
        });

        ImageView ccsuForm = view.findViewById(R.id.ccsuForm);
        ccsuForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isConnectionAvailable(getContext())) {
                    Toast.makeText(getContext(), "You are currently offline, Please connect to internet", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://ccsuweb.in/"));
                startActivity(i);

                showMyAdd(i);
            }
        });

        ImageView ccsuResult = view.findViewById(R.id.ccsuResult);
        ccsuResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isConnectionAvailable(getContext())) {
                    Toast.makeText(getContext(), "You are currently offline, Please connect to internet", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://192.163.211.186/~ccsuresu/"));
                startActivity(i);
                showMyAdd(i);
            }
        });

        ImageView admitCard = view.findViewById(R.id.admitCard);
        admitCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isConnectionAvailable(getContext())) {
                    Toast.makeText(getContext(), "You are currently offline, Please connect to internet", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://exams.ccsuresults.com/"));
                startActivity(i);
                showMyAdd(i);
            }
        });

        ImageView sarkariResult = view.findViewById(R.id.sarkariResult);
        sarkariResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isConnectionAvailable(getContext())) {
                    Toast.makeText(getContext(), "You are currently offline, Please connect to internet", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.sarkariresult.com/"));
                startActivity(i);
                showMyAdd(i);
            }
        });

        ImageView more = view.findViewById(R.id.moreImage);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),MoreWebsites.class));
            }
        });

        CardView newsAndAlertViewAllButton = view.findViewById(R.id.viewAll);
        newsAndAlertViewAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),NewsAndAlert_ViewAll.class));
            }
        });

        uploadNotes = view.findViewById(R.id.uploadnotesImageView);
        feedback = view.findViewById(R.id.feedbackImageVIew);
        changeCourse = view.findViewById(R.id.changeCourseImageView);
        uploadNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ShareNotes.class));
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Feedback.class));
            }
        });
        changeCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SelectCourse.class));
                getActivity().finish();
            }
        });

        return view;
    }

   public void loadScreen() {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                slider.addSlides(slideList);
                slider.setItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                });

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
