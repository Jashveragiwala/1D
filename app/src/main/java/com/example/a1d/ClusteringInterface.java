package com.example.a1d;

import java.util.ArrayList;

// Interface for clustering locations for a trip.

public interface ClusteringInterface {

    ArrayList<String> getClusters(Integer days, String start, String locationsString);
}
