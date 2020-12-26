package com.example.nearestcities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    City[] cities;
    public void search(View view)
    {
        String cityName = ((Spinner) this.findViewById(R.id.cities)).getSelectedItem().toString();
        City city = null;
        for(City c: cities) {
            if(c.name.equals(cityName)) city = c;
        }
        Float distance = Float.valueOf(((TextView) this.findViewById(R.id.max_distance)).getText().toString()) * 1000;
        Intent intent = new Intent(this, CitiesActivity.class);
        intent.putExtra("city_name", cityName);
        intent.putExtra("distance", distance);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InputStream is = getResources().openRawResource(R.raw.cities);
        Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name());
        String jstring = scanner.useDelimiter("\\A").next();
        Gson gson = new Gson();
        cities = gson.fromJson(jstring, City[].class);
        Spinner spinner = findViewById(R.id.cities);
        List<String> nameList = new ArrayList<String>();
        for(City city: cities) {
            //Log.d("myTag", String.valueOf(city.country == "RU"));
            if(city.country.equals("RU")) nameList.add(city.name);
        }
        Collections.reverse(nameList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nameList);
        spinner.setAdapter(adapter);

    }
}