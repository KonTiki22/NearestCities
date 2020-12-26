package com.example.nearestcities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
    Context context;
    ArrayAdapter<String> adapter;
    Spinner spinner;
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

    class MyThread extends Thread {
        MyThread(String name){
            super(name);
        }

        public void run(){
            InputStream is = getResources().openRawResource(R.raw.cities);
            Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name());
            String jstring = scanner.useDelimiter("\\A").next();
            Gson gson = new Gson();
            cities = gson.fromJson(jstring, City[].class);
            List<String> nameList = new ArrayList<String>();
            for(City city: cities) {
                //Log.d("myTag", String.valueOf(city.country == "RU"));
                if(city.country.equals("RU")) nameList.add(city.name);
            }
            Collections.reverse(nameList);
            adapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_list_item_1, nameList);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        spinner = findViewById(R.id.cities);
        final MyThread myThread = new MyThread("MyThread");
        myThread.start();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    myThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                spinner.setAdapter(adapter);
            }
        });

    }
}