package com.example.a1d;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DistanceMatrix {

    public int originArrLen = 0;
    public HashMap<Integer, String> indexes = new HashMap<>();

    public HashMap<Integer, String> getIndexes() {
        return indexes;
    }

    public double[][] getDistances(String locationsString) throws Exception {
        HashMap<String, double[]> locations = new HashMap<>();

        String origins = locationsString;
        String destinations = origins;

        String originArr[] = origins.split("%7C%");
        originArrLen = originArr.length;

        int x = 0;
        for (String s : originArr) {
            double arr[] = new double[originArrLen];
            locations.put(s, arr);
            indexes.put(x, s);
            x++;
        }

        double[][] distanceMatrix = new double[originArrLen][originArrLen];

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/distancematrix/json?origins=" +
                        origins + "&destinations=" + destinations + "&mode=driving&language=en-EN&" +
                        "key=AIzaSyD03pQpPpanpGgrJyTfCagPxTLAya8pQws")
                .method("GET", null) // body)
                .build();

        Response response = client.newCall(request).execute();

        String output = response.body().string();

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(output);
            JSONObject jsonobj = (JSONObject) obj;

            JSONArray dist = (JSONArray) jsonobj.get("rows");

            for (int i = 0; i < originArrLen; i++) {
                JSONObject objX = (JSONObject) dist.get(i);
                double locArr[] = new double[originArrLen];

                for (int j = 0; j < originArrLen; j++) {
                    JSONArray distIngX = (JSONArray) objX.get("elements");
                    JSONObject objY = (JSONObject) distIngX.get(j);

                    JSONObject distance = (JSONObject) objY.get("distance");
                    JSONObject duration = (JSONObject) objY.get("duration");

                    Object distanceText = distance.get("text");
                    Object durationText = duration.get("text");

                    double value = Double.parseDouble(((String) distanceText).replaceAll("[^0-9.]", ""));
                    locArr[j] = value;

                    if (value == 1.0)
                        distanceMatrix[i][j] = 0.0;
                    else
                        distanceMatrix[i][j] = value;
                }
                locations.put(originArr[i], locArr);


            }
            response.close();
            client.connectionPool().evictAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return distanceMatrix;
    }

    public HashMap<String,HashMap> getDistancesClusters(String locationString) throws Exception {

        HashMap<String, HashMap> locations = new HashMap<>();
        String origins = locationString;

        origins = origins.substring(0, origins.length()-4);

        //String origins = "HomeTeamNS Bukit Batok%7C%SUTD%7C%NUS%7C%NTU%7C%Singapore Management University%7C%Singapore Institute of Technology%7C%Simei MRT%7C%51 Changi Village Rd%7C%Toppers Education Centre%7C%Waterway Point"; //%7C%NTU

        String destinations = origins;

        String originArr[] = origins.split("%7C%");
        int originArrLen = originArr.length;

        for (String s: originArr) {
            locations.put(s, new HashMap<>());
        }

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/distancematrix/json?origins=" +
                        origins + "&destinations=" + destinations + "&mode=driving&language=en-EN&" +
                        "key=AIzaSyD03pQpPpanpGgrJyTfCagPxTLAya8pQws")
                .method("GET", null) // body)
                .build();

        Response response = client.newCall(request).execute();

        String output = response.body().string();

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(output);
        JSONObject jsonobj = (JSONObject) obj;

        JSONArray dist = (JSONArray) jsonobj.get("rows");

        for (int i=0; i<originArrLen; i++) {
            JSONObject objX = (JSONObject) dist.get(i);
            HashMap<String, Double> loc = new HashMap<>();

            for (int j = 0; j < originArrLen; j++) {
                JSONArray distIngX = (JSONArray) objX.get("elements");
                JSONObject objY = (JSONObject) distIngX.get(j);

                JSONObject distance = (JSONObject) objY.get("distance");
                JSONObject duration = (JSONObject) objY.get("duration");

                Object distanceText = distance.get("text");
                Object durationText = duration.get("text");

                Double value = Double.parseDouble(((String) distanceText).replaceAll("[^0-9.]", ""));
                if (value != 1.0) {
                    loc.put(originArr[j],value);
                }
            }

            locations.put(originArr[i], loc);
        }


        response.close();
        client.connectionPool().evictAll();
        return locations;
    }

}
