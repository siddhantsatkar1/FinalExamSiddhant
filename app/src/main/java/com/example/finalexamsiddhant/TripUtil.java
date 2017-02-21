package com.example.finalexamsiddhant;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sid on 12-12-2016.
 */

public class TripUtil {
    static public class TripJSONParser {
        static ArrayList<Trip> parseTrips(String in) {
            ArrayList<Trip> appsList = new ArrayList<Trip>();
            try {
                JSONObject root = new JSONObject(in);
                JSONObject feed = root.getJSONObject("result");
                //JSONArray appsJSONArray = feed.getJSONArray("address_components");

                //for (int i = 0; i < appsJSONArray.length(); i++) {
                    //JSONObject appJSONObject = (JSONObject) appsJSONArray.get(i);
                    Trip trip = Trip.createTrip(feed);
                    Log.d("each app", trip.toString());
                    appsList.add(trip);
                //}

            } catch (JSONException e) {
                appsList = null;
                e.printStackTrace();
            }


            return appsList;
        }


    }
}

