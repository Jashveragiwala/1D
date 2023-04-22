package com.example.a1d;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Clustering implements ClusteringInterface {

    // An ArrayList of Double objects to hold the distance values
    public ArrayList<Double> values = new ArrayList<>();

    public ArrayList<String> getClusters(Integer days, String start, String locationsString) {
        try {
            // Creates a new DistanceMatrix object
            DistanceMatrix DMT = new DistanceMatrix();
            // Gets a HashMap of distances between all locations from the DistanceMatrix object
            HashMap<String, HashMap> locations = DMT.getDistancesClusters(locationsString);
            // Gets the HashMap of distances between the starting location and all other locations
            HashMap<String, Double> origin = locations.get(start);
            // Adds all distance values to the values ArrayList
            for (Map.Entry<String, Double> value : origin.entrySet()) {
                values.add(value.getValue());
            }
            // Sort the distances in ascending order of distance from origin
            Collections.sort(values);
            // Initialise new ArrayList to store the clusters.
            // (each cluster is an ArrayList<Double> that stores the distances of the locations from the origin)
            ArrayList<ArrayList<Double>> dpa = new ArrayList<>();
            // size -> number of locations in a cluster
            int size = values.size() / days;
            // Calculates the reminder if any
            int remainder = values.size() % days;
            // s -> An index value to keep track of the elements traversed in the ArrayList values
            int s = 0;
            // outer for loop to create a cluster for each day of stay
            for (int i = 0; i < days; i++) {
                // Cluster initialised to store distances
                ArrayList<Double> group = new ArrayList<>();
                // uses remainder to check if cluster size needs to be increased by 1 if reminder is not 0
                // Ex) values.size=13, days=3, size=4, remainder=1.
                // To accommodate for 13th element, cluster size increased by 1
                int end = s + size + (remainder-- > 0 ? 1 : 0);
                // inner for loop to add all elements in ArrayList values to a cluster
                for (int j = s; j < end; j++) {
                    group.add(values.get(j));
                }
                // Add group (cluster) to ArrayList dpa
                dpa.add(group);
                // index s = index of first element to be added to new group/cluster
                s = end;
            }
            // To keep track of locations already added to the cluster
            ArrayList<String> inCluster = new ArrayList<>();
            // To store the finalised clusters. These clusters are in the form of strings.
            // Each string is a set of locations separated by %7c%
            ArrayList<String> clusters = new ArrayList<>();
            // initialise string to make the cluster. The start location will be the first location of the cluster
            String S = start + "%7C%";

            // to go through each ArrayList<Double> in the ArrayList dpa
            for (ArrayList<Double> ad : dpa) {
                // to go through each distance value d in ArrayList ad
                for (Double d : ad) {
                    // to go through each key j ( location name ) stored in the HashMap origin
                    for (String j : origin.keySet()) {
                        // if the distance value which is mapped to j equals the distance value d
                        // and if the key j has not already been added to a cluster,
                        if (origin.get(j).equals(d) && !inCluster.contains(j)) {
                            // add the key j to the cluster string and break the innermost loop
                            inCluster.add(j);
                            S += j + "%7C%";
                            break;
                        }
                    }
                }
                // add the cluster string to the ArrayList clusters
                clusters.add(S);
                // Re initialise the String to make a new cluster
                S = start + "%7C%";
            }
            // returns the ArrayList clusters as the output
            return clusters;
            // in case of exception, print the stackTrace and return null.
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
