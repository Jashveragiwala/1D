package com.example.a1d;

import java.util.ArrayList;

/**
 * An interface for clustering locations for a trip.
 */
public interface ClusteringInterface {
    /**
     * Returns an ArrayList of strings representing the clusters of locations for a trip.
     *
     * days            the number of days for the trip
     * start           the starting location of the trip
     * locationsString a list of locations in the format of a string
     * an ArrayList of strings representing the clusters of locations for the trip
     */
    ArrayList<String> getClusters(Integer days, String start, String locationsString);
}
