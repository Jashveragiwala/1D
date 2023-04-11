package com.example.a1d;

import java.util.*;

public class TravellingSalesman {

    public int[] bestPath; // stores the best path found so far
    public double bestCost = Double.POSITIVE_INFINITY; // stores the cost of the best path

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


}