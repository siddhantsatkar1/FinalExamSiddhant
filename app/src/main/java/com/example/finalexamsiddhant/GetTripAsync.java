package com.example.finalexamsiddhant;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sid on 12-12-2016.
 */


public class GetTripAsync  extends AsyncTask<String, Void, ArrayList<Trip>> {

    ITrip activity;
    StringBuilder sb;

    public GetTripAsync(ITrip activity){
        this.activity = activity;
    }



    @Override
    protected ArrayList<Trip> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                int statusCode = conn.getResponseCode();
                if(statusCode==HttpURLConnection.HTTP_OK)
                {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    sb = new StringBuilder();
                    String line = br.readLine();
                    while(line != null)
                    {
                        sb.append(line);
                        line = br.readLine();
                    }
                    //  Log.d("demo" , sb.toString());
                    return TripUtil.TripJSONParser.parseTrips(sb.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(ArrayList<Trip> tripArray) {
        if(tripArray != null)
        {
            activity.setTripList(tripArray);
        }

    }

    public  static interface ITrip {
        public void setTripList(ArrayList<Trip> result);
        //public void setError(String error);
    }
}
