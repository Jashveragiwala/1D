//package algstest.Kmeans;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.ArrayList;
//import java.util.Map;
//import java.util.Set;
//import algstest.Kmeans.DataPointComparator;
//import javax.xml.crypto.Data;
//
//public class Kmeanstest {
//    public static void main(String[] args) throws Exception {
//        try {
//            List<Double> values= new ArrayList<>();
//            DistanceMatrixExample DMT = new DistanceMatrixExample();
//            HashMap<String, HashMap> locations = DMT.GetD("HomeTeamNS Bukit Batok%7C%SUTD%7C%NUS%7C%NTU%7C%Singapore Management University%7C%Singapore Institute of Technology%7C%Simei MRT%7C%51 Changi Village Rd%7C%Toppers Education Centre%7C%Waterway Point%7C%");
//            String start="SUTD";
//            HashMap<String,Double> origin=locations.get(start);
//            for (Map.Entry<String,Double> value: origin.entrySet()) {
//                    values.add(value.getValue());
//            }
//            int k=3;
//            kmt2 kMeansClustering = new kmt2(values,k);
//            kMeansClustering.cluster();
//
//            Set<Centroid> centroids = kMeansClustering.getCentroids();
//            int centroidIndex = 1;
//            for (Centroid centroid : centroids) {
//                System.out.println("Centroid " + centroidIndex + ": " + centroid.getValue());
//                centroidIndex++;
//            }
//
//            List<Set<DataPoint>> clusteredDataPoints = kMeansClustering.getClusteredDataPoints();
//            int clusterIndex = 1;
//            String S="";
//            ArrayList<DataPoint> temp=new ArrayList<>();
//            for (Set<DataPoint> cluster : clusteredDataPoints) {
//                System.out.print("Cluster " + clusterIndex + ": ");
//                System.out.println(cluster);
//                for (DataPoint dataPoint : cluster) {
////                    for (Map.Entry<String,Double> value: origin.entrySet()) {
////                        if (value.getValue()== dataPoint.getValue()){
////                            System.out.print(value.getKey()+",");
////                            break;
////                        }
////                    }
//                    temp.add(dataPoint);
//                }
//                System.out.println();
//                clusterIndex++;
//            }
//            System.out.println(temp);
//            Collections.sort(temp,new DataPointComparator());
//            List<ArrayList<DataPoint>> temp2=new ArrayList<>();
//            System.out.println(temp.size());
//            int l=temp.size()/k;
//            System.out.println(l);
//           // ArrayList<DataPoint> dpa=new ArrayList<>();
//            int size = temp.size() / k;
//            int remainder = temp.size() % k;
//            int s = 0;
//            for (int i = 0; i < k; i++) {
//                ArrayList<DataPoint> group = new ArrayList<>();
//                int end = s + size + (remainder-- > 0 ? 1 : 0);
//                for (int j = s; j < end; j++) {
//                    group.add(temp.get(j));
//                }
//                temp2.add(group);
//                s = end;
//            }
//            System.out.println(temp2);
//        }
//        catch (Exception e){
//            System.out.println("Error");
//        }
//    }
//}
package com.example.a1d;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Clustering {

    public ArrayList<String> getClusters(Integer days, String start, String locationsString) throws Exception {
        try {
            ArrayList<Double> values = new ArrayList<>();
            DistanceMatrixExample2 DMT = new DistanceMatrixExample2();
            HashMap<String, HashMap> locations = DMT.GetDistances(locationsString);
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
            throw new RuntimeException(e);
        }

    }
}
