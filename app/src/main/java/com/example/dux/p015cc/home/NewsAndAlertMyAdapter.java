package com.example.dux.p015cc.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dux.p015cc.R;

import java.util.ArrayList;

public class NewsAndAlertMyAdapter extends RecyclerView.Adapter<NewsAndAlertMyAdapter.NewsAndAlertViewHolder> {
    private ArrayList<NewsAndAlertDataModel> list;
    private Context context;
    private String activityName;
    private NewsAndAlertMyAdapter.RecyclerViewClickListener recyclerViewClickListener;

    public NewsAndAlertMyAdapter(ArrayList<NewsAndAlertDataModel> mlist,NewsAndAlertMyAdapter.RecyclerViewClickListener mListener,String mActivityName) {
        list = mlist;
        recyclerViewClickListener = mListener;
        activityName = mActivityName;
    }


    @Override
    public NewsAndAlertViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_and_alert_item_view,parent,false);
        NewsAndAlertMyAdapter.NewsAndAlertViewHolder holder = new NewsAndAlertMyAdapter.NewsAndAlertViewHolder(view , recyclerViewClickListener);
        context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(NewsAndAlertViewHolder holder, int position) {
        holder.notificationTitle.setText(list.get(position).getNotificationTitle());
        holder.notificationDesciption.setText(list.get(position).getNotificationDescription());
    }

    @Override
    public int getItemCount() {
        if(activityName.equalsIgnoreCase("home")) {
            if(list.size() >= 5)
                return 5;
        }
        return list.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int adapterPosition);
    }

    public class NewsAndAlertViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView notificationTitle;
        private TextView notificationDesciption;
        private NewsAndAlertMyAdapter.RecyclerViewClickListener viewClickListener;

        public NewsAndAlertViewHolder(View itemView, RecyclerViewClickListener recyclerViewClickListener) {
            super(itemView);

            notificationDesciption  = itemView.findViewById(R.id.notificationDescription);
            notificationTitle = itemView.findViewById(R.id.notificationTitle);

            viewClickListener = recyclerViewClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            viewClickListener.onClick(view,getAdapterPosition());
        }
    }
}
