package com.example.a1d;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DistanceMatrixExample2 {
    public HashMap<String,HashMap> GetDistances(String origins) throws Exception {

        //HashMap<String, Double[]> locations = new HashMap<>();
        HashMap<String, HashMap> locations = new HashMap<>();
        //s=s.substring(0, s.length()-4);
        //String origins = "HomeTeamNS Bukit Batok%7C%SUTD%7C%NUS%7C%NTU%7C%Singapore Management University%7C%Singapore Institute of Technology%7C%Simei MRT%7C%51 Changi Village Rd%7C%Toppers Education Centre%7C%Waterway Point"; //%7C%NTU
        origins = origins.substring(0, origins.length()-4);
        // SUTD -> SUTD, SUTD -> NUS, SUTD -> NTU
        // NUS -> SUTD, NUS -> NUS, NUS -> NTU
        // NTU -> SUTD, NTU -> NUS. NTU -> NTU

        String destinations = origins;

        String originArr[] = origins.split("%7C%");
        int originArrLen = originArr.length;

        for (String s: originArr) {
            //Double[] arr = new Double[originArrLen];
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
        // .url("https://maps.googleapis.com/maps/api/distancematrix/json?origins=Vancouver%20BC%7CSeattle&destinations=San%20Francisco%7CVictoria%20BC&mode=driving&language=en-EN&key=AIzaSyD03pQpPpanpGgrJyTfCagPxTLAya8pQws")

        Response response = client.newCall(request).execute();

        String output = response.body().string();

        //System.out.println(output);

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(output);
        JSONObject jsonobj = (JSONObject) obj;

        JSONArray dist = (JSONArray) jsonobj.get("rows");

        for (int i=0; i<originArrLen; i++) {
            JSONObject objX = (JSONObject) dist.get(i);
            //ArrayList<Double> locArr = new ArrayList<>();
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

//                    System.out.println(durationText);
//
//                    System.out.println(distIngX.get(j));
//                    System.out.println();
                    //Double[] locArr2=locArr.toArray(new Double[0]);
            }

                locations.put(originArr[i], loc);
        }


        response.close();
//        System.out.println(client.connectionPool().idleConnectionCount());
        client.connectionPool().evictAll();


//        for (Entry<String, HashMap> set : locations.entrySet()) {
//            System.out.print(set.getKey() + " = ");
//            HashMap<String, Double> vals=set.getValue();
//            for (Entry<String,Double> value: vals.entrySet()) {
//                System.out.print(value.getKey()+" "+value.getValue().toString()+" ");
//            }
//            System.out.println();
//        }

        return locations;
    }

}
