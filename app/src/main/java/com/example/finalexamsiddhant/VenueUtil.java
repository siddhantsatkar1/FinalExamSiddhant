package com.example.finalexamsiddhant;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sid on 12-12-2016.
 */

public class VenueUtil {
    static public class VenueJSONParser {
        static ArrayList<Venue> parseVenues(String in) {
            ArrayList<Venue> appsList = new ArrayList<Venue>();
            try {
                JSONObject root = new JSONObject(in);
                JSONArray appsJSONArray = root.getJSONArray("predictions");

                for (int i = 0; i < appsJSONArray.length(); i++) {
                    JSONObject appJSONObject = (JSONObject) appsJSONArray.get(i);
                    Venue venue = Venue.createVenue(appJSONObject);
                    Log.d("each app", venue.toString());
                    appsList.add(venue);
                }

            } catch (JSONException e) {
                appsList = null;
                e.printStackTrace();
            }


            return appsList;
        }


    }
}

