package com.example.nearestcities;

public class City {
    String id;
    String name;
    String country;
    Coord coord;
    Float distance;
    class Coord {
        Float lon;
        Float lat;
    }
}
