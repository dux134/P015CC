package com.example.dux.p015cc.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dux.p015cc.R;

import java.util.ArrayList;

public class MoreWebsiteMyAdapter extends RecyclerView.Adapter<MoreWebsiteMyAdapter.MorreWebsiteViewHolder> {
    private ArrayList<MoreWebsiteDataModel> list;
    private Context context;
    private MoreWebsiteMyAdapter.RecyclerViewClickListener recyclerViewClickListener;


    public MoreWebsiteMyAdapter(ArrayList<MoreWebsiteDataModel> mList, MoreWebsiteMyAdapter.RecyclerViewClickListener mListener) {
        recyclerViewClickListener = mListener;
        list = mList;
    }


    @Override
    public MorreWebsiteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.website_recycler_item,parent,false);
        MoreWebsiteMyAdapter.MorreWebsiteViewHolder holder = new MoreWebsiteMyAdapter.MorreWebsiteViewHolder(view , recyclerViewClickListener);
        context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(MorreWebsiteViewHolder holder, int position) {
        holder.websiteTextView.setText(list.get(position).getWebsiteName());

        Glide.with(context)
                .load(list.get(position).getWebsiteImageUrl())
                .into(holder.websiteImageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int adapterPosition);
    }

    public class MorreWebsiteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView websiteImageView;
        private TextView websiteTextView;
        private MoreWebsiteMyAdapter.RecyclerViewClickListener mListener;

        public MorreWebsiteViewHolder(View itemView, RecyclerViewClickListener recyclerViewClickListener) {
            super(itemView);

            websiteImageView = itemView.findViewById(R.id.website_recycler_image);
            websiteTextView = itemView.findViewById(R.id.website_recycler_name);

            mListener = recyclerViewClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view,getAdapterPosition());
        }
    }
}
