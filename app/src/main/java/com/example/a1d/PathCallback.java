package com.example.a1d;

import java.util.ArrayList;

// This is an interface for a callback function that is called when paths are ready
public interface PathCallback {
    // This method is called when all paths are ready and returns an ArrayList of ArrayLists of strings representing the paths
    void onPathsReady(ArrayList<ArrayList<String>> allPaths);
}