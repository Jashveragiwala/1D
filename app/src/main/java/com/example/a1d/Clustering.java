package com.example.a1d;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Clustering implements ClusteringInterface {

    public ArrayList<Double> values = new ArrayList<>();

    public ArrayList<String> getClusters(Integer days, String start, String locationsString) {
        try {
            DistanceMatrix DMT = new DistanceMatrix();
            HashMap<String, HashMap> locations = DMT.getDistancesClusters(locationsString);
            HashMap<String, Double> origin = locations.get(start);
            for (Map.Entry<String, Double> value : origin.entrySet()) {
                values.add(value.getValue());
            }

            Collections.sort(values);
            ArrayList<ArrayList<Double>> dpa = new ArrayList<>();
            int size = values.size() / days;
            int remainder = values.size() % days;
            int s = 0;
            for (int i = 0; i < days; i++) {
                ArrayList<Double> group = new ArrayList<>();
                int end = s + size + (remainder-- > 0 ? 1 : 0);
                for (int j = s; j < end; j++) {
                    group.add(values.get(j));
                }
                dpa.add(group);
                s = end;
            }

            ArrayList<String> inCluster = new ArrayList<>();
            ArrayList<String> clusters = new ArrayList<>();

            String S = start + "%7C%";
            for (ArrayList<Double> ad : dpa) {
                for (Double d : ad) {
                    for (String j : origin.keySet()) {
                        if (origin.get(j).equals(d) && !inCluster.contains(j)) {
                            inCluster.add(j);
                            S += j + "%7C%";
                            break;
                        }
                    }
                }
                clusters.add(S);
                S = start + "%7C%";
            }
            return clusters;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
