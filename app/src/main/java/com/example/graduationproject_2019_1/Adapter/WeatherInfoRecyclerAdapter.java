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

        ImageView day_image;
        TextView day_value;

        ImageView temp_image;
        TextView temp_value;

        ImageView wfKor_image;
        TextView wfKor_value;

        ImageView pop_image;
        TextView pop_value;

        ImageView reh_image;
        TextView reh_value;

        WeatherViewHolder(View view){
            super(view);
            time = view.findViewById(R.id.weather_time);

            day_image = view.findViewById(R.id.weather_dayimage);
            day_value = view.findViewById(R.id.weather_dayvalue);

            temp_image = view.findViewById(R.id.weather_tempimage);
            temp_value = view.findViewById(R.id.weather_tempvalue);

            wfKor_image = view.findViewById(R.id.weather_wfKorimage);
            wfKor_value = view.findViewById(R.id.weather_wfKorvalue);

            pop_image = view.findViewById(R.id.weather_popimage);
            pop_value = view.findViewById(R.id.weather_popvalue);

            reh_image = view.findViewById(R.id.weather_rehimage);
            reh_value = view.findViewById(R.id.weather_rehvalue);
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
        weatherViewHolder.day_image.setImageResource(weatherInfoArrayList.get(position).dayimage);
        weatherViewHolder.day_value.setText(weatherInfoArrayList.get(position).dayvalue);
        weatherViewHolder.temp_image.setImageResource(weatherInfoArrayList.get(position).tempimage);
        weatherViewHolder.temp_value.setText(weatherInfoArrayList.get(position).tempvalue);
        weatherViewHolder.wfKor_image.setImageResource(weatherInfoArrayList.get(position).wfKorimage);
        weatherViewHolder.wfKor_value.setText(weatherInfoArrayList.get(position).wfKorvalue);

        weatherViewHolder.pop_image.setImageResource(weatherInfoArrayList.get(position).wfKorimage);
        weatherViewHolder.pop_value.setText(weatherInfoArrayList.get(position).wfKorvalue);
        weatherViewHolder.reh_image.setImageResource(weatherInfoArrayList.get(position).wfKorimage);
        weatherViewHolder.reh_value.setText(weatherInfoArrayList.get(position).wfKorvalue);

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
