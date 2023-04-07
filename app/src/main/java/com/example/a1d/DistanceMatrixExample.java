package com.example.a1d;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DistanceMatrixExample {

    public int originArrLen = 0;
    public HashMap<Integer, String> indexes;


    public double[][] getDistances(String locationsString) throws Exception {
        HashMap<String, double[]> locations = new HashMap<>();
        indexes = new HashMap<>();

        String origins = "SUTD%7C%NUS%7C%NTU"; //%7C%NTU
        locationsString = origins;

        // SUTD -> SUTD, SUTD -> NUS, SUTD -> NTU
        // NUS -> SUTD, NUS -> NUS, NUS -> NTU
        // NTU -> SUTD, NTU -> NUS. NTU -> NTU

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
        // .url("https://maps.googleapis.com/maps/api/distancematrix/json?origins=Vancouver%20BC%7CSeattle&destinations=San%20Francisco%7CVictoria%20BC&mode=driving&language=en-EN&key=AIzaSyD03pQpPpanpGgrJyTfCagPxTLAya8pQws")

        Response response = client.newCall(request).execute();

        String output = response.body().string();

//        System.out.println(output);

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

//            for(int i=0; i<originArrLen; i++) {
//                System.out.println(Arrays.toString(distanceMatrix[i]));
//            }

            // System.out.println(locations);

//            for (Map.Entry<String, double[]> set : locations.entrySet()) {
//                System.out.println(set.getKey() + " = "
//                        + Arrays.toString(set.getValue()));
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return distanceMatrix;
    }

    public static void main(String[] args) throws Exception {
        DistanceMatrixExample DM = new DistanceMatrixExample();
        int originArrLen = DM.originArrLen;
        HashMap<Integer, String> indexes = DM.indexes;
        System.out.println(indexes);

    }

}


//            JSONObject obj2 = (JSONObject) dist.get(0);
//            JSONObject obj3 = (JSONObject) dist.get(1);
//            JSONObject obj4 = (JSONObject) dist.get(2);
//
//            JSONArray disting = (JSONArray) obj2.get("elements");
//            JSONArray disting2 = (JSONArray) obj3.get("elements");
//            JSONArray disting3 = (JSONArray) obj4.get("elements");
//
//            System.out.println(disting);
//            System.out.println(disting2);
//            System.out.println(disting3);
//
//            System.out.println();
//
//            System.out.println(disting.get(0));
//            System.out.println(disting.get(1));
//
//            System.out.println();
//
//            System.out.println(disting2.get(0));
//            System.out.println(disting2.get(1));

//            JSONObject obj3 = (JSONObject) disting.get(0);
//            JSONObject obj4 = (JSONObject) disting.get(1);
//            JSONObject obj5 = (JSONObject) disting.get(2);
//
//            JSONObject obj4 = (JSONObject) obj3.get("distance");
//            JSONObject obj5 = (JSONObject) obj3.get("duration");
//System.out.println(obj4.get("text"));
//System.out.println(obj5.get("text"));
//        System.out.println(client.connectionPool().idleConnectionCount());
//

//                    System.out.println(durationText);
//
//                    System.out.println(distIngX.get(j));
//                    System.out.println();
//        System.out.println(response.body().getClass().getSimpleName());
//        System.out.println(response.body().toString() instanceof String);

//        JSONObject jsonObject = new JSONObject(response);
//        System.out.println(jsonObject);
//        JSONArray stuff = jsonObject.getJSONArray("employees");
//        String safe_title = jsonObject.getString("safe_title");

//        System.out.println("test");
//        System.out.println(response);
//        System.out.println(response.body().string());



//        // Set up the GeoApiContext with your API key
//        GeoApiContext context = new GeoApiContext.Builder()
//                .apiKey("YOUR_API_KEY")
//                .build();
//
//        // Set up the origins and destinations as LatLng objects
//        LatLng[] origins = {
//                new LatLng(37.7749, -122.4194), // San Francisco, CA
//                new LatLng(40.7128, -74.0060), // New York, NY
//                new LatLng(51.5074, -0.1278), // London, UK
//        };
//        LatLng[] destinations = {
//                new LatLng(34.0522, -118.2437), // Los Angeles, CA
//                new LatLng(41.8781, -87.6298), // Chicago, IL
//                new LatLng(35.6895, 139.6917), // Tokyo, Japan
//        };
//
//        // Create a DistanceMatrixApiRequest with the specified origins and destinations
//        DistanceMatrixApiRequest request = DistanceMatrixApi.newRequest(context)
//                .origins(origins)
//                .destinations(destinations)
//                .mode(TravelMode.DRIVING);
//
//        // Execute the request and get the DistanceMatrix response
//        DistanceMatrix response = request.await();
//
//        // Print out the results
//        DistanceMatrixElement[][] elements = response.rows;
//        for (int i = 0; i < elements.length; i++) {
//            for (int j = 0; j < elements[i].length; j++) {
//                DistanceMatrixElement element = elements[i][j];
//                System.out.println("From " + origins[i] + " to " + destinations[j] + ":");
//                System.out.println("Distance: " + element.distance);
//                System.out.println("Duration: " + element.duration);