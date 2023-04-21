package com.example.a1d;

import java.util.*;

// Implements the TravellingSalesmanInterface to solve the travelling salesman problem.
//The solve method takes a distance matrix and the index of the origin, and returns the best path as an array of indices.
public class TravellingSalesman implements TravellingSalesmanInterface {

    private int[] bestPath; // the best path found so far
    private double bestCost = Double.POSITIVE_INFINITY; // the cost of the best path found so far

    // Returns the best path found so far in an array
    public int[] getBestPath() {
        return bestPath;
    }

    // Returns the cost of the best path found so far.
    public double getBestCost() {
        return bestCost;
    }

    // Solves the travelling salesman problem given a distance matrix and the index of the origin city.
    @Override
    public int[] solve(double[][] distances, int origin) {
        int n = distances.length;
        int[] path = new int[n];
        for (int i = 0; i < n; i++) {
            path[i] = i;
        }
        permute(path, 1, n-1, distances, origin); // start from the second city (index 1)
        return bestPath;
    }

    // Recursively generates all permutations of the path starting from the given start index.
    // Calculates the cost of each permutation and updates the best path and best cost if a new minimum cost is found.
    private void permute(int[] path, int start, int end, double[][] distances, int origin) {
        if (start == end) {
            // we have generated a new permutation
            double cost = calculateCost(path, distances, origin);  // calculate the cost of the permutation
            if (cost < bestCost) {
                // update the best path and best cost if the new cost is smaller
                bestPath = path.clone();
                bestCost = cost;
            }
        } else {
            // generate all permutations by swapping the current element with each
            for (int i = start; i <= end; i++) {
                swap(path, start, i);
                permute(path, start+1, end, distances, origin);
                swap(path, start, i);
            }
        }
    }

    // Calculates the cost of the given path by summing the distances between adjacent cities and adding the distance from the last city back to the origin.
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