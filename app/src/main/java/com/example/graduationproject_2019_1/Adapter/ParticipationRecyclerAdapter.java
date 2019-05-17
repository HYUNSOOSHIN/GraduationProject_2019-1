package com.example.graduationproject_2019_1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.graduationproject_2019_1.Data.ParticipationRecycleObject;
import com.example.graduationproject_2019_1.R;

import java.util.ArrayList;

public class ParticipationRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static class ParticipationViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView comment;

        ParticipationViewHolder(View view){
            super(view);
            image = view.findViewById(R.id.re_image);
            title = view.findViewById(R.id.re_title);
            comment = view.findViewById(R.id.re_comment);
        }
    }

    private ArrayList<ParticipationRecycleObject> participationInfoArrayList;
    public ParticipationRecyclerAdapter(ArrayList<ParticipationRecycleObject> participationInfoArrayList){
        this.participationInfoArrayList = participationInfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.participation_template, parent, false);

        return new ParticipationRecyclerAdapter.ParticipationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ParticipationRecyclerAdapter.ParticipationViewHolder participationViewHolder = (ParticipationRecyclerAdapter.ParticipationViewHolder) holder;

        participationViewHolder.image.setImageResource(participationInfoArrayList.get(position).image);
        participationViewHolder.title.setText(participationInfoArrayList.get(position).title);
        participationViewHolder.comment.setText(participationInfoArrayList.get(position).comment);

        participationViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Toast.makeText(context, position+" ",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return participationInfoArrayList.size();
    }
}
