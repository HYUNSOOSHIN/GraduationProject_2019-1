package com.example.graduationproject_2019_1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.graduationproject_2019_1.Data.ActionRecycleObject;
import com.example.graduationproject_2019_1.R;

import java.util.ArrayList;

public class ActionInfoRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static class ActionViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView comment;

        ActionViewHolder(View view){
            super(view);
            image = view.findViewById(R.id.re_image);
            title = view.findViewById(R.id.re_title);
            comment = view.findViewById(R.id.re_comment);
        }
    }

    private ArrayList<ActionRecycleObject> adtionInfoArrayList;
    public ActionInfoRecyclerAdapter(ArrayList<ActionRecycleObject> adtionInfoArrayList){
        this.adtionInfoArrayList = adtionInfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.actioninfo_template, parent, false);

        return new ActionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ActionViewHolder actionViewHolder = (ActionViewHolder) holder;

        actionViewHolder.image.setImageResource(adtionInfoArrayList.get(position).image);
        actionViewHolder.title.setText(adtionInfoArrayList.get(position).title);
        actionViewHolder.comment.setText(adtionInfoArrayList.get(position).comment);

        actionViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Context context = view.getContext();
//                Toast.makeText(context, position+" ",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return adtionInfoArrayList.size();
    }
}