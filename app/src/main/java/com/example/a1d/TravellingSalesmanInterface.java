package com.example.a1d;

// This is a public interface named "TravellingSalesmanInterface"
// It defines a method called "solve" that takes in a 2D array of distances between locations and an integer representing the starting location.
// The method returns an array of integers representing the order in which the locations should be visited to minimize the total distance traveled.
// The interface does not provide an implementation for the "solve" method, it only specifies the method signature.
// Any class that implements this interface must provide its own implementation for the "solve" method.
public interface TravellingSalesmanInterface {
    int[] solve(double[][] distances, int origin);
}
