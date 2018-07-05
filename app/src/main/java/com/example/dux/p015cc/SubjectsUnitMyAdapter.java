package com.example.dux.p015cc;

import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SubjectsUnitMyAdapter extends RecyclerView.Adapter<SubjectsUnitMyAdapter.SubjectsUnitViewHolder>{
    private SubjectsUnitMyAdapter.RecyclerClickListener listener;
    private ArrayList<SubjectsUnitDataModel> list;
    private String activityName;

    public SubjectsUnitMyAdapter(ArrayList<SubjectsUnitDataModel> mList,SubjectsUnitMyAdapter.RecyclerClickListener listener1,String mActivityName) {
        list = mList;
        listener = listener1;
        activityName = mActivityName;
    }

    @Override
    public SubjectsUnitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_recycler_item,parent,false);
        SubjectsUnitViewHolder holder = new SubjectsUnitViewHolder(view, listener);
        return  holder;
    }

    @Override
    public void onBindViewHolder(SubjectsUnitViewHolder holder, int position) {

        holder.subjectTitle.setText(list.get(position).getTitle());
        if(activityName.equalsIgnoreCase("notes")) {
            holder.subjectImage.setImageResource(R.mipmap.pdf);
        } else {
            holder.subjectImage.setImageResource(R.mipmap.youtube);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface RecyclerClickListener {
        void onClick(View view, int adapterPosition);
    }

    public class SubjectsUnitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView subjectTitle;
        private ImageView subjectImage;
        private SubjectsUnitMyAdapter.RecyclerClickListener lv;

        public SubjectsUnitViewHolder(View itemView, RecyclerClickListener listener) {
            super(itemView);
            subjectTitle = itemView.findViewById(R.id.subject_title);
            subjectImage = itemView.findViewById(R.id.subject_image);
            lv = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            lv.onClick(view,getAdapterPosition());
        }
    }
}
