package com.spaghetti.earthquake.model;

public record Earthquake(String date, String hour, double latitude, double longitude, double depth, Size size,
                         String location,
                         String solutionQuality) {

}
