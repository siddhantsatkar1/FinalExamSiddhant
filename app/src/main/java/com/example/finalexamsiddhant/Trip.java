package com.example.finalexamsiddhant;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sid on 12-12-2016.
 */

public class Trip implements Parcelable {

    String tripname;
    String cityname;
    String lat;
    String longi;

    public Trip(String tripname, String cityname, String lat, String longi){
        this.tripname = tripname;
        this.cityname = cityname;
        this.lat = lat;
        this.longi = longi;
    }

    public Trip(Parcel in) {
        this.tripname = in.readString();
        this.cityname = in.readString();
        this.lat = in.readString();
        this.longi = in.readString();
    }

    public Trip(){}


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("tripname", tripname);
        result.put("cityname", cityname);
        result.put("lat", lat);
        result.put("longi", longi);
        return result;
    }

    public String getTripname() {
        return tripname;
    }

    public void setTripname(String tripname) {
        this.tripname = tripname;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripname='" + tripname + '\'' +
                ", cityname='" + cityname + '\'' +
                ", lat='" + lat + '\'' +
                ", longi='" + longi + '\'' +
                '}';
    }

    static public Trip createTrip(JSONObject js) {
        Trip trip = new Trip();
        try {
            JSONObject geo = js.getJSONObject("geometry");
            JSONObject location = geo.getJSONObject("location");
            trip.setLat(location.getString("lat"));
            trip.setLongi(location.getString("lng"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trip;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(tripname);
        dest.writeString(cityname);
        dest.writeString(lat);
        dest.writeString(longi);
    }
    public static final Parcelable.Creator<Venue> CREATOR
            = new Parcelable.Creator<Venue>() {
        public Venue createFromParcel(Parcel in) {
            return new Venue(in);
        }

        public Venue[] newArray(int size) {
            return new Venue[size];
        }
    };
}
