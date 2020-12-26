package com.example.nearestcities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CitiesActivity extends AppCompatActivity {
    String cityName;
    Float maxDistance;
    City[] cities;
    RecyclerView recyclerView;

    public void back(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        Bundle args = getIntent().getExtras();
        if(args != null) {
            cityName = args.getString("city_name");
            maxDistance = args.getFloat("distance");

            InputStream is = getResources().openRawResource(R.raw.cities);
            Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name());
            String jstring = scanner.useDelimiter("\\A").next();
            Gson gson = new Gson();
            cities = gson.fromJson(jstring, City[].class);
            List<City> result = new ArrayList<>();
            City city = null;
            Log.d("myTag", cityName);
            for(City c: cities) {
                if(c.name.equals(cityName)) city = c;
            }
            for(City c: cities) {
                if(!c.name.equals(cityName)) {
                    float[] distRes = new float[3];
                    Location.distanceBetween(city.coord.lat, city.coord.lon, c.coord.lat, c.coord.lon, distRes);
                    if(distRes[0] <= maxDistance) {
                        c.distance = distRes[0];
                        result.add(c);
                    }
                }

            }
            TextView near = this.findViewById(R.id.nearest);
            near.setText(String.format("Cities within a %.0f km radius", maxDistance/1000));
            Collections.reverse(result);
            recyclerView = findViewById(R.id.recList);
            CityAdapter adapter = new CityAdapter(this, result, cityName);
            recyclerView.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(adapter);

        }
    }
}