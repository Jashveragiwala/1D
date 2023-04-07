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
        // days = k
        int k = 3;
        ArrayList<Double> values = new ArrayList<>();
        ArrayList<String> clusters = new ArrayList<>();
//
//        try {
//            ArrayList<Double> values = new ArrayList<>();
//
//            DistanceMatrixExample DM = new DistanceMatrixExample();
//            int originArrLen = DM.originArrLen;
//            HashMap<Integer, String> indexes = DM.indexes;
//            System.out.println(indexes);
//            double[][] distanceMatrix = DM.getDistances(locationsString);
//
//
//            for (int i=0; i<originArrLen; i++) {
//                if (start == indexes.get(i)) {
//                    for(int j=0; j<originArrLen; j++) {
//                        if (start != indexes.get(j))
//                            values.add(distanceMatrix[i][j]);
//                    }
//                }
//            }


        DistanceMatrixExample2 DMT = new DistanceMatrixExample2();
        HashMap<String, HashMap> locations = DMT.GetDistances("HomeTeamNS Bukit Batok%7C%SUTD%7C%NUS%7C%NTU%7C%Singapore Management University%7C%Singapore Institute of Technology%7C%Simei MRT%7C%51 Changi Village Rd%7C%Toppers Education Centre%7C%Waterway Point%7C%");
        start = "SUTD";
        HashMap<String, Double> origin = locations.get(start);
        // {"SUTD" : {"NUS" : 28, "NTU": 29}, "NUS" : {"SUTD" : 28}}
        for (Map.Entry<String, Double> value : origin.entrySet()) {
            values.add(value.getValue());
        }
        //System.out.println(values);
        Collections.sort(values);


        // [28, 29]
        //System.out.println(values);

        ArrayList<ArrayList<Double>> dpa = new ArrayList<>();
        // dpa is the list of clusters

        ArrayList<ArrayList<HashMap<Double, String>>> dps = new ArrayList<>();

        int size = values.size() / k;
        int remainder = values.size() % k;
        int s = 0;
        for (int i = 0; i < k; i++) {
            ArrayList<Double> group = new ArrayList<>();
            ArrayList<HashMap<Double, String>> groupS = new ArrayList<>();
            int end = s + size + (remainder-- > 0 ? 1 : 0);
            for (int j = s; j < end; j++) {
                group.add(values.get(j));
            }
            dpa.add(group);
            s = end;
        }
        System.out.println(dpa);
        // dpa is sorted distances in clusters
        // dpa = [[2.0, 8.1, 11.9], [17.8, 18.8, 26.2], [30.4, 32.6, 36.1]]

        ArrayList<String> inCluster = new ArrayList<>();

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
                System.out.println(S);
                //DMT_Cluster(S);
                //Travelling Salesman(DMT_Cluster.getDistances);
                clusters.add(S);
                S = start + "%7C%";


//                SUTD%7C%Simei MRT%7C%51 Changi Village Rd%7C%Waterway Point%7C%
//                SUTD%7C%Toppers Education Centre%7C%Singapore Management University%7C%NUS%7C%
//                SUTD%7C%Singapore Institute of Technology%7C%HomeTeamNS Bukit Batok%7C%NTU%7C%

            }
        }

        return clusters;
    }
}

//                    for (Map.Entry<String,Double> value: origin.entrySet()) {
//                        if (value.getValue() == d) {
//                            //System.out.print(value.getKey()+",");
//                            S += value.getKey() + "%7C%";
//                            break;
//                        }
//                    }