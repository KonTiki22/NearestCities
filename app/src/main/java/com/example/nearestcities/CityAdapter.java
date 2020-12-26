package com.example.nearestcities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder>  {
    LayoutInflater inflater;
    List<City> cities;
    String cityName;


    public  CityAdapter(Context context, List<City> cities, String city) {
        inflater = LayoutInflater.from((context));
        this.cities = cities;
        cityName = city;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, lon, lat, distance;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            lon = itemView.findViewById(R.id.lon);
            lat = itemView.findViewById(R.id.lat);
            distance = itemView.findViewById(R.id.distance);
        }

        void bind(int listIndex) {
            name.setText(cities.get(listIndex).name);
            lon.setText("Latitude:" + cities.get(listIndex).coord.lon.toString());
            lat.setText("Longitude:" + cities.get(listIndex).coord.lat.toString());
            distance.setText(String.format("Distance: %.2f km to %s", cities.get(listIndex).distance/1000, cityName));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    public  int getItemCount() {
        return cities.size();
    }
}
