package com.example.a1d;

import java.util.ArrayList;

public class Trip {
    private ArrayList<ArrayList<String>> Itinerary = null; // A list of all the locations in the trip
    private String allLocations; // A string containing all locations in the trip

    private ArrayList<String> cluster; // A list of locations clustered based on similarity

    private int numberOfDays; // The number of days the trip will take

    private String startLocation;  // The starting location of the trip

    Trip(int numberOfDays, String startLocation, String allLocations){
        // Assigning the values of the input variables to the private variables of the class
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
            // Calling the getClusters() method of the Clustering object to cluster the locations
            cluster = c.getClusters(getNumberOfDays(), getStartLocation(), getAllLocations());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setItinerary() throws Exception {
        // Creating a new list to store all the paths of the day trips
        ArrayList<ArrayList<String>> allPaths = new ArrayList<>();
        for (String s : getCluster()) {
            // Creating a new DayTrip object with the current cluster
            DayTrip day = new DayTrip(s);
            // Adding the shortest path of the current day trip to the list of paths
            allPaths.add(day.shortestPath());
        }
        // Assigning the list of paths to the Itinerary variable
        Itinerary = allPaths;
    }
}
