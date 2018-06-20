package com.example.dux.p015cc.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.dux.p015cc.R;

import java.util.ArrayList;
import java.util.List;

import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;

public class Home extends Fragment {

    private static Home home = new Home();
    private Slider slider;
    View view;

    public static Home getInstance() {
        return home;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        slider = view.findViewById(R.id.slider);
        loadScreen();

        return view;
    }

    private void loadSlider() {
        //create list of slides
        List<Slide> slideList = new ArrayList<>();
        slideList.add(new Slide(0,"https://naqyr37xcg93tizq734pqsx1-wpengine.netdna-ssl.com/wp-content/uploads/2014/06/Dont-Make-Excuses-Picture-Quote.jpg" , getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(1,"https://shortstatusquotes.com/wp-content/uploads/Great-self-motivation-status-and-short-quotes.jpg" , getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(2,"https://naqyr37xcg93tizq734pqsx1-wpengine.netdna-ssl.com/wp-content/uploads/2014/06/A-Dream-Inspiration-Picture-Quote-Colin-Powell.jpg" , getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(3,"https://naqyr37xcg93tizq734pqsx1-wpengine.netdna-ssl.com/wp-content/uploads/2014/06/Conrad-Hilton-Success-Picture-Quote.jpg" , getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(4,"https://naqyr37xcg93tizq734pqsx1-wpengine.netdna-ssl.com/wp-content/uploads/2014/06/Obstacles-Picture-Quote.jpg" , getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));

//handle slider click listener
        slider.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //do what you want
            }
        });

//add slides to slider
        slider.addSlides(slideList);
    }


    public void loadScreen() {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                loadSlider();
            }
        });
    }


}
