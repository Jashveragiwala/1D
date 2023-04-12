package com.example.a1d;

import java.util.ArrayList;
import java.util.HashMap;

public class DayTrip {

    private String locations;


    public String getLocations() {
        return locations;
    }

    DayTrip(String locations) {
        this.locations = locations;
    }

    public ArrayList<String> shortestPath() {
        ArrayList<String> finalPath = new ArrayList<>();

        DistanceMatrix DM = new DistanceMatrix();
        try {
            double[][] distanceMatrix = DM.getDistances(getLocations());

            // Use Interface instead of Class, if TravellingSalesman Implementation changed later on
            TravellingSalesmanInterface ts = new TravellingSalesman();
            int[] path = ts.solve(distanceMatrix, 0);

            HashMap<Integer, String> indexes = DM.getIndexes();

            for (int i : path) {
                finalPath.add(indexes.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    return finalPath;
    }
}

