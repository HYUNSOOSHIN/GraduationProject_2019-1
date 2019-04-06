package com.example.graduationproject_2019_1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.graduationproject_2019_1.Data.WeatherRecycleObject;
import com.example.graduationproject_2019_1.R;

import java.util.ArrayList;

public class WeatherInfoRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        ImageView mise_image;
        TextView mise_value;
        ImageView temp_image;
        TextView temp_value;
        ImageView rain_image;
        TextView rain_value;

        WeatherViewHolder(View view){
            super(view);
            time = view.findViewById(R.id.weather_time);
            mise_image = view.findViewById(R.id.weather_miseimage);
            mise_value = view.findViewById(R.id.weather_misevalue);
            temp_image = view.findViewById(R.id.weather_tempimage);
            temp_value = view.findViewById(R.id.weather_tempvalue);
            rain_image = view.findViewById(R.id.weather_rainimage);
            rain_value = view.findViewById(R.id.weather_rainvalue);
        }
    }

    private ArrayList<WeatherRecycleObject> weatherInfoArrayList;
    public WeatherInfoRecyclerAdapter(ArrayList<WeatherRecycleObject> weatherInfoArrayList){
        this.weatherInfoArrayList = weatherInfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weatherinfo_template, parent, false);

        return new WeatherViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final WeatherViewHolder weatherViewHolder = (WeatherViewHolder) holder;

        weatherViewHolder.time.setText(weatherInfoArrayList.get(position).time);
        weatherViewHolder.mise_image.setImageResource(weatherInfoArrayList.get(position).miseimage);
        weatherViewHolder.mise_value.setText(weatherInfoArrayList.get(position).misevalue);
        weatherViewHolder.temp_image.setImageResource(weatherInfoArrayList.get(position).tempimage);
        weatherViewHolder.temp_value.setText(weatherInfoArrayList.get(position).tempvalue);
        weatherViewHolder.rain_image.setImageResource(weatherInfoArrayList.get(position).rainimage);
        weatherViewHolder.rain_value.setText(weatherInfoArrayList.get(position).rainvalue);

        weatherViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Toast.makeText(context, position+" ",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return weatherInfoArrayList.size();
    }
}
