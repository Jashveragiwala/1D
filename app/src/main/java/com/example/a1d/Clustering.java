package com.example.a1d;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Clustering implements ClusteringInterface {

    // Initializes a public ArrayList of Double objects to hold the distance values
    public ArrayList<Double> values = new ArrayList<>();

    // This method takes in parameters including number of days for the trip, the starting location,
    // and a string of locations, and returns an ArrayList of strings containing the clusters
    public ArrayList<String> getClusters(Integer days, String start, String locationsString) {
        try {
            // Creates a new DistanceMatrix object
            DistanceMatrix DMT = new DistanceMatrix();
            // Gets a HashMap of distances between all locations from the DistanceMatrix object
            HashMap<String, HashMap> locations = DMT.getDistancesClusters(locationsString);
            // Gets a HashMap of distances between the starting location and all other locations
            HashMap<String, Double> origin = locations.get(start);
            // Adds all distance values to the values ArrayList
            for (Map.Entry<String, Double> value : origin.entrySet()) {
                values.add(value.getValue());
            }

            // Sorts the values ArrayList in ascending order
            Collections.sort(values);
            // Initializes an ArrayList of ArrayLists of Double objects to hold the daily path averages
            ArrayList<ArrayList<Double>> dpa = new ArrayList<>();
            // Calculates the size of each group
            int size = values.size() / days;
            // Calculates the remainder
            int remainder = values.size() % days;
            // Initializes the starting index
            int s = 0;
            // Loops through the number of days
            for (int i = 0; i < days; i++) {
                // Initializes a new ArrayList of Double objects to hold the group
                ArrayList<Double> group = new ArrayList<>();
                // Calculates the ending index
                int end = s + size + (remainder-- > 0 ? 1 : 0);
                // Loops through the range of indices for the group
                for (int j = s; j < end; j++) {
                    // Adds the value at the current index to the group ArrayList
                    group.add(values.get(j));
                }
                // Adds the group ArrayList to the dpa ArrayList
                dpa.add(group);
                // Updates the starting index
                s = end;
            }

            // Initializes an ArrayList of strings to hold the locations in each cluster
            ArrayList<String> inCluster = new ArrayList<>();
            // Initializes an ArrayList of strings to hold the clusters
            ArrayList<String> clusters = new ArrayList<>();

            // Initializes a string with the starting location and a separator
            String S = start + "%7C%";
            // Loops through the daily path averages
            for (ArrayList<Double> ad : dpa) {
                // Loops through the values in the daily path average group
                for (Double d : ad) {
                    // Loops through the keys in the origin HashMap
                    for (String j : origin.keySet()) {
                        // If the value of the key in the origin HashMap matches the current value in the group
                        // and the key is not already in the inCluster ArrayList, add the key to the inCluster ArrayList
                        if (origin.get(j).equals(d) && !inCluster.contains(j)) {
                            inCluster.add(j);
                            // Concatenates the key with the separator to the S string
                            S += j + "%7C%";
                            // Breaks out of the inner loop
                            break;
                        }
                    }
                }
                // Adds the S string to the clusters ArrayList
                clusters.add(S);
                // Resets the S string with the starting location and separator
                S = start + "%7C%";
            }
            // Returns the clusters ArrayList
            return clusters;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
