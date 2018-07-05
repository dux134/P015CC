package com.example.dux.p015cc.govt;

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

public class GovtExamMyAdapter extends RecyclerView.Adapter<GovtExamMyAdapter.GovtExamViewHolder>{
    private ArrayList<GovtExamDataModel> list;
    private Context context;
    private GovtExamMyAdapter.RecyclerItemClickListener recyclerItemClickListener;

    public GovtExamMyAdapter(ArrayList<GovtExamDataModel> mList,GovtExamMyAdapter.RecyclerItemClickListener listener,Context mContext) {
        list = mList;
        recyclerItemClickListener = listener;
        context = mContext;
    }

    @Override
    public GovtExamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.govt_exam_recycler_item,parent,false);
        GovtExamMyAdapter.GovtExamViewHolder holder = new GovtExamMyAdapter.GovtExamViewHolder(view,recyclerItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(GovtExamViewHolder holder, int position) {
        holder.description.setText(list.get(position).getDescription());
        holder.title.setText(list.get(position).getTitle().toUpperCase());
        holder.examDate.setText(list.get(position).getExamDate());

        Glide.with(context)
                .load(list.get(position).getImageUrl())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface RecyclerItemClickListener {
        void onClick(View view, int adapterPosition);
    }

    public class GovtExamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView image;
        private TextView title;
        private TextView description;
        private TextView examDate;
        private GovtExamMyAdapter.RecyclerItemClickListener mListener;

        public GovtExamViewHolder(View itemView, RecyclerItemClickListener recyclerItemClickListener) {
            super(itemView);

            image = itemView.findViewById(R.id.govt_exam_recycler_image);
            title = itemView.findViewById(R.id.govt_exam_recycler_title);
            description = itemView.findViewById(R.id.govt_exam_recycler_description);
            examDate = itemView.findViewById(R.id.examDateTextView);
            mListener = recyclerItemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view,getAdapterPosition());
        }
    }
}
