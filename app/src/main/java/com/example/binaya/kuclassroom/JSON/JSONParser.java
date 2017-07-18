package com.example.binaya.kuclassroom.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Binaya on 7/16/17.
 */

public class JSONParser {
    private static final String TAG = "JSONParser";
    private static final String check = "https://binayachaudari.github.io/KUScheduleFiles/IIYIIS.json";

    public String getJson(String Passed_url){
        HttpURLConnection connection = null;
        try{
            URL url = new URL(Passed_url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int status = connection.getResponseCode();
//            Log.d(TAG, "getstatus: " + status);

            //InputStreamReader: it reads bytes and decodes them into characters using a specified charset.
            //BufferedReader: Reads text from a character-input stream, buffering characters so as to provide for the efficient reading of characters, arrays, and lines.
            switch (status){
                case 200:
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
//                        Log.d(TAG, "getJson: "+ sb);
                    }
                    br.close();
//                    Log.d(TAG, "getJson: "+sb.toString());
                    return sb.toString();
            }
        }

        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

