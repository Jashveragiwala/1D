package com.example.a1d;

import java.util.*;

public class TravellingSalesman {

    public int[] bestPath; // stores the best path found so far
    public double bestCost = Double.POSITIVE_INFINITY; // stores the cost of the best path

    public double[][] distances;

    TravellingSalesman(double[][] distances) {
        this.distances = distances;
    }

    public int[] solve(double[][] distances, int origin) {
        int n = distances.length;
        int[] path = new int[n];
        for (int i = 0; i < n; i++) {
            path[i] = i;
        }
        permute(path, 1, n-1, distances, origin); // start from the second city (index 1)
        return bestPath;
    }

    private void permute(int[] path, int start, int end, double[][] distances, int origin) {
        if (start == end) {
            double cost = calculateCost(path, distances, origin);
            if (cost < bestCost) {
                bestPath = path.clone();
                bestCost = cost;
            }
        } else {
            for (int i = start; i <= end; i++) {
                swap(path, start, i);
                permute(path, start+1, end, distances, origin);
                swap(path, start, i);
            }
        }
    }

    private double calculateCost(int[] path, double[][] distances, int origin) {
        double cost = 0.0;
        int n = path.length;
        for (int i = 0; i < n-1; i++) {
            cost += distances[path[i]][path[i+1]];
        }
        cost += distances[path[n-1]][origin]; // add the cost of returning to the origin
        return cost;
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }


    public static void main(String[] args) throws Exception {
        DistanceMatrixExample DM = new DistanceMatrixExample();
        //double[][] distanceMatrix = DM.getDistances("SUTD%7C%Simei MRT%7C%51 Changi Village Rd%7C%Waterway Point%7C%Toppers Education Centre%7C%Singapore Management University%7C%Global Indian International School, Singapore");
        double[][] distanceMatrix= DM.getDistances("SUTD%7C%NUS%7C%Singapore Institute of Technology%7C%HomeTeamNS Bukit Batok%7C%NTU%7C%");
        HashMap<Integer, String> indexes = DM.indexes;

        //System.out.println(Arrays.deepToString(distanceMatrix));

        TravellingSalesman ts = new TravellingSalesman(distanceMatrix);

        int[] path = ts.solve(distanceMatrix, 0);

//        List<Integer> shortestPath = shortestPath(distanceMatrix, 0);
        System.out.println(indexes);
//        System.out.println(shortestPath);
        // System.out.println(indexes.get(path[0]));

//       System.out.println(indexes.size());

//        System.out.println(path.length);
        ArrayList<String> finalPath = new ArrayList<>();

        for(int i: path) {
            finalPath.add(indexes.get(i));
        }
        System.out.println(finalPath);

        System.out.println(Arrays.toString(path));

    }

}