package com.example.dux.p015cc.ccsu;

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

public class CCSUnotesMyAdapter extends RecyclerView.Adapter<CCSUnotesMyAdapter.CCSUnotesViewHolder>{
    private ArrayList<CCSUnotesDataModel> list;
    private CCSUnotesMyAdapter.RecyclerViewClickListener listener;
    private Context context;

    public CCSUnotesMyAdapter(ArrayList<CCSUnotesDataModel> mList,CCSUnotesMyAdapter.RecyclerViewClickListener mListener,Context mContext){
        list = mList;
        listener = mListener;
        context = mContext;
    }

    @Override
    public CCSUnotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ccsu_recycler_item_view,parent,false);
        CCSUnotesMyAdapter.CCSUnotesViewHolder holder = new CCSUnotesMyAdapter.CCSUnotesViewHolder(view,listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(CCSUnotesViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        Glide.with(context)
                .load(list.get(position).getImageUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface RecyclerViewClickListener {

        void onClick(View view, int adapterPosition);
    }

    public class CCSUnotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;
        private TextView title;
        private CCSUnotesMyAdapter.RecyclerViewClickListener recyclerViewClickListener;

        public CCSUnotesViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.subject_image);
            title = itemView.findViewById(R.id.ccsu_notes_title);

            recyclerViewClickListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            recyclerViewClickListener.onClick(view,getAdapterPosition());
        }
    }
}
