package com.example.a1d;

import java.util.*;

public class TravellingSalesman {
    private static int[] bestPath; // stores the best path found so far
    private static double bestCost = Double.POSITIVE_INFINITY; // stores the cost of the best path

    public static int[] solve(double[][] distances, int origin) {
        int n = distances.length;
        int[] path = new int[n];
        for (int i = 0; i < n; i++) {
            path[i] = i;
        }
        permute(path, 1, n-1, distances, origin); // start from the second city (index 1)
        return bestPath;
    }

    private static void permute(int[] path, int start, int end, double[][] distances, int origin) {
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

    private static double calculateCost(int[] path, double[][] distances, int origin) {
        double cost = 0.0;
        int n = path.length;
        for (int i = 0; i < n-1; i++) {
            cost += distances[path[i]][path[i+1]];
        }
        cost += distances[path[n-1]][origin]; // add the cost of returning to the origin
        return cost;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

   public static void main(String[] args) throws Exception {
        DistanceMatrixExample DM = new DistanceMatrixExample();
        int numCities = DM.originArrLen;
        HashMap<Integer, String> indexes = DM.indexes;

        double[][] distanceMatrix = DM.getDistances("");
        int[] path = TravellingSalesman.solve(distanceMatrix, 0);
       System.out.println(indexes);
       // System.out.println(indexes.get(path[0]));

//       System.out.println(indexes.size());

        System.out.println(path.length);
        ArrayList<String> finalPath = new ArrayList<>();

//        for(int i: path) {
//            finalPath.add(indexes.get(i));
//        }
//        System.out.println(finalPath);

        System.out.println(Arrays.toString(path));

    }

}



//    private int numPlaces;
//    private double[][] distances;
//
//    public TravellingSalesman(int numPlaces, double[][] distances) {
//        this.numPlaces = numPlaces;
//        this.distances = distances;
//    }
//
//    public List<Integer> solve(int origin) {
//        List<Integer> places = new ArrayList<>();
//        for (int i = 0; i < numPlaces; i++) {
//            if (i != origin) {
//                places.add(i);
//            }
//        }
//
//        List<Integer> bestRoute = null;
//        double bestDistance = Double.MAX_VALUE;
//
//        for (List<Integer> route : permute(places)) {
//            route.add(0, origin);
//            route.add(origin);
//            double distance = calculateDistance(route);
//            if (distance < bestDistance) {
//                bestDistance = distance;
//                bestRoute = route;
//            }
//        }
//
//        return bestRoute;
//    }
//
//    private List<List<Integer>> permute(List<Integer> cities) {
//        List<List<Integer>> permutations = new ArrayList<>();
//        permute(cities, 0, permutations);
//        return permutations;
//    }
//
//    private void permute(List<Integer> cities, int start, List<List<Integer>> permutations) {
//        if (start == cities.size()) {
//            permutations.add(new ArrayList<>(cities));
//        } else {
//            for (int i = start; i < cities.size(); i++) {
//                Collections.swap(cities, start, i);
//                permute(cities, start + 1, permutations);
//                Collections.swap(cities, start, i);
//            }
//        }
//    }
//
//    private double calculateDistance(List<Integer> route) {
//        double distance = 0;
//        for (int i = 1; i < route.size(); i++) {
//            distance += distances[route.get(i - 1)][route.get(i)];
//        }
//        return distance;
//    }