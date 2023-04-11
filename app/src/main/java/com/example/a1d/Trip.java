package com.example.a1d;

import java.util.ArrayList;

public class Trip {
    private ArrayList<ArrayList<String>> Itinerary = null;
    private String AllLocations;

    private ArrayList<String> cluster;

    private int numberOfDays;

    private String startLocation;

    Trip(int numberOfDays, String startLocation, String AllLocations){
        this.AllLocations = AllLocations;
        this.startLocation = startLocation;
        this.numberOfDays = numberOfDays;
    }

    public String getAllLocations() {
        return AllLocations;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public ArrayList<ArrayList<String>> getItinerary() {
        return Itinerary;
    }

    public ArrayList<String> getCluster() {
        return cluster;
    }

    public void setCluster() {
        Clustering c = new Clustering();
        try {
            cluster = c.getClusters(getNumberOfDays(), getStartLocation(), getAllLocations());
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert cluster != null;
    }

    public void setItinerary() throws Exception {
        ArrayList<ArrayList<String>> allpaths = new ArrayList<>();
        for (String s : getCluster()){
            DayTrip day = new DayTrip("car",s);
            allpaths.add(day.shortestpath());
        }
        Itinerary = allpaths;

    }

}
