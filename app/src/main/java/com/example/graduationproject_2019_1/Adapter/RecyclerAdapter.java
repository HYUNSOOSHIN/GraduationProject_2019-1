package com.example.graduationproject_2019_1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.graduationproject_2019_1.Data.RecycleObject;
import com.example.graduationproject_2019_1.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView comment;

        MyViewHolder(View view){
            super(view);
            image = view.findViewById(R.id.re_image);
            title = view.findViewById(R.id.re_title);
            comment = view.findViewById(R.id.re_comment);
        }
    }

    private ArrayList<RecycleObject> foodInfoArrayList;
    public RecyclerAdapter(ArrayList<RecycleObject> foodInfoArrayList){
        this.foodInfoArrayList = foodInfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_template, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.image.setImageResource(foodInfoArrayList.get(position).image);
        myViewHolder.title.setText(foodInfoArrayList.get(position).title);
        myViewHolder.comment.setText(foodInfoArrayList.get(position).comment);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Toast.makeText(context, position+" ",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodInfoArrayList.size();
    }
}