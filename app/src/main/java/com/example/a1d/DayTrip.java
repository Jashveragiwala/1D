package com.example.a1d;

import java.util.ArrayList;
import java.util.HashMap;

public class DayTrip {
    private String transport;

    private String Locations;
    private ArrayList<String> finalPath=new ArrayList<>();

    DayTrip(String transport, String Locations){
        this.Locations = Locations;
        this.transport = transport;
    }

    ArrayList<String> shortestpath() throws Exception {
        System.out.println(Locations);
        DistanceMatrix DM = new DistanceMatrix();
        double[][] distanceMatrix = DM.getDistances(Locations);
        TravellingSalesman ts = new TravellingSalesman();
        int[] path = ts.solve(distanceMatrix, 0);
        HashMap<Integer, String> indexes = DM.getIndexes();
        for (int i : path) {
            finalPath.add(indexes.get(i));
        }
        return finalPath;
    }
}

