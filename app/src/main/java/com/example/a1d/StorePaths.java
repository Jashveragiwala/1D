package com.example.a1d;

import java.util.ArrayList;

// A class to store and access path data.
public class StorePaths {
    // The paths that have been generated
    private ArrayList<ArrayList<String>> paths;

    // Singleton instance of the StorePaths class
    private static StorePaths instance = new StorePaths();

    // Get the singleton instance of the StorePaths class the singleton instance of the StorePaths class
    public static StorePaths getInstance() {
        // If instance is null, create a new one
        if (instance == null) {
            instance = new StorePaths();
        }
        return instance;
    }

    // Get the paths that have been generated the paths that have been generated
    public ArrayList<ArrayList<String>> getPaths() {
        return paths;
    }

    // Set the paths that have been generated paths the paths that have been generated
    public void setPaths(ArrayList<ArrayList<String>> paths) {
        // does not allow modification after setPaths() is called once
        this.paths = paths;
    }

    // Private constructor to prevent direct instantiation of the class
    private StorePaths() {
        paths = null;
    }
}
