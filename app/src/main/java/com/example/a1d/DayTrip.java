package com.example.a1d;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class representing a day trip.
 */
public class DayTrip {

    // The locations to visit in the day trip
    private String locations;

    //locations a string of comma-separated locations
    public String getLocations() {
        return locations;
    }

    //a string of comma-separated locations
    DayTrip(String locations) {
        this.locations = locations;
    }

    // an ArrayList of strings representing the shortest path to visit all locations
    public ArrayList<String> shortestPath() {
        ArrayList<String> finalPath = new ArrayList<>();

        // Compute the distance matrix between locations
        DistanceMatrix DM = new DistanceMatrix();
        try {
            double[][] distanceMatrix = DM.getDistances(getLocations());

            // Use Interface instead of Class, if TravellingSalesman Implementation changed later on
            // Find the shortest path through the locations using the TravellingSalesman algorithm
            TravellingSalesmanInterface ts = new TravellingSalesman();
            int[] path = ts.solve(distanceMatrix, 0);

            // Map the index of each location in the path to its name
            HashMap<Integer, String> indexes = DM.getIndexes();

            // Add the names of the locations in the shortest path to the final path ArrayList
            for (int i : path) {
                finalPath.add(indexes.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    return finalPath;
    }
}

