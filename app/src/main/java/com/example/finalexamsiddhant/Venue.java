package com.example.finalexamsiddhant;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sid on 12-12-2016.
 */

public class Venue implements Parcelable {

    String venue_id;
    String name;
    String placeid;

    public Venue(String venue_id, String name, String placeid){
        this.venue_id = venue_id;
        this.name = name;
        this.placeid = placeid;
    }
    public Venue(Parcel in) {
        this.venue_id = in.readString();
        this.name = in.readString();
        this.placeid = in.readString();
    }
    public Venue(){}

    public String getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(String venue_id) {
        this.venue_id = venue_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceid() {
        return placeid;
    }

    public void setPlaceid(String placeid) {
        this.placeid = placeid;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "venue_id='" + venue_id + '\'' +
                ", name='" + name + '\'' +
                ", placeid='" + placeid + '\'' +
                '}';
    }

    static public Venue createVenue(JSONObject js)
    {
        Venue venue = new Venue();
        try {
            venue.setName(js.getString("description"));
            //JSONObject id = js.getJSONObject("id");
            venue.setVenue_id(js.getString("id"));

            venue.setPlaceid(js.getString("place_id"));

//            JSONObject name = js.getJSONObject("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  venue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(venue_id);
        dest.writeString(name);
        dest.writeString(placeid);
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
