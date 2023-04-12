package com.example.a1d;

import java.util.ArrayList;

public class Trip {
    private ArrayList<ArrayList<String>> Itinerary = null;
    private String allLocations;

    private ArrayList<String> cluster;

    private int numberOfDays;

    private String startLocation;

    Trip(int numberOfDays, String startLocation, String allLocations){
        this.allLocations = allLocations;
        this.startLocation = startLocation;
        this.numberOfDays = numberOfDays;
    }

    public String getAllLocations() {
        return allLocations;
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
        ClusteringInterface c = new Clustering();
        try {
            cluster = c.getClusters(getNumberOfDays(), getStartLocation(), getAllLocations());
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert cluster != null;
    }

    public void setItinerary() throws Exception {
        ArrayList<ArrayList<String>> allPaths = new ArrayList<>();
        for (String s : getCluster()) {
            DayTrip day = new DayTrip(s);
            allPaths.add(day.shortestPath());
        }
        Itinerary = allPaths;

    }

}
