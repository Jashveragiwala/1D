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

public class DistanceMatrixExample {

    public int originArrLen = 0;
    public HashMap<Integer, String> indexes;
    DistanceMatrixExample () {
        indexes = new HashMap<>();
    }

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

    public static void main(String[] args) throws Exception {
        DistanceMatrixExample DM = new DistanceMatrixExample();
        int originArrLen = DM.originArrLen;
        HashMap<Integer, String> indexes = DM.getIndexes();
        System.out.println(indexes);

    }

}
