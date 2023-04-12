package com.example.a1d;

import java.util.ArrayList;

public class StorePaths {
    private ArrayList<ArrayList<String>> paths;

    private static StorePaths instance = new StorePaths();

    public static StorePaths getInstance() {
        return instance;
    }

    public void setPaths(ArrayList<ArrayList<String>> paths) {
        this.paths = paths;
    }

    public ArrayList<ArrayList<String>> getPaths() {
        return paths;
    }

    private StorePaths() {
        paths = null;
    }

    private StorePaths(ArrayList<ArrayList<String>> paths) {
        this.paths = paths;
    }
}
