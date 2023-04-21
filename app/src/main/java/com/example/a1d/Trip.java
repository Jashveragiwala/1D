package com.example.a1d;

import java.util.ArrayList;

public class Trip {
    // Declaring private variables
    private ArrayList<ArrayList<String>> Itinerary = null; // A list of all the locations in the trip
    private String allLocations; // A string containing all locations in the trip

    private ArrayList<String> cluster; // A list of locations clustered based on similarity

    private int numberOfDays; // The number of days the trip will take

    private String startLocation;  // The starting location of the trip

    // Constructor for the "Trip" class that takes in the number of days, starting location, and all locations
    Trip(int numberOfDays, String startLocation, String allLocations){
        // Assigning the values of the input variables to the private variables of the class
        this.allLocations = allLocations;
        this.startLocation = startLocation;
        this.numberOfDays = numberOfDays;
    }

    // Getter method for the "allLocations" variable
    public String getAllLocations() {
        return allLocations;
    }

    // Getter method for the "numberOfDays" variable
    public int getNumberOfDays() {
        return numberOfDays;
    }


    // Getter method for the "startLocation" variable
    public String getStartLocation() {
        return startLocation;
    }

    // Getter method for the "Itinerary" variable
    public ArrayList<ArrayList<String>> getItinerary() {
        return Itinerary;
    }

    // Getter method for the "cluster" variable
    public ArrayList<String> getCluster() {
        return cluster;
    }

    // Setter method for the "cluster" variable
    public void setCluster() {
        // Creating a new object of the "Clustering" class
        ClusteringInterface c = new Clustering();
        try {
            // Calling the "getClusters" method of the "Clustering" object to cluster the locations
            cluster = c.getClusters(getNumberOfDays(), getStartLocation(), getAllLocations());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Asserting that the "cluster" variable is not null
        assert cluster != null;
    }

    // Setter method for the "Itinerary" variable
    public void setItinerary() throws Exception {
        // Creating a new list to store all the paths of the day trips
        ArrayList<ArrayList<String>> allPaths = new ArrayList<>();
        // Iterating over all the clusters
        for (String s : getCluster()) {
            // Creating a new "DayTrip" object with the current cluster
            DayTrip day = new DayTrip(s);
            // Adding the shortest path of the current day trip to the list of paths
            allPaths.add(day.shortestPath());
        }
        // Assigning the list of paths to the "Itinerary" variable
        Itinerary = allPaths;
    }
}
