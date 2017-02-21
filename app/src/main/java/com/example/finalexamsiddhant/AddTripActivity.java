package com.example.finalexamsiddhant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddTripActivity extends AppCompatActivity implements GetVenueAsync.IVenue, GetTripAsync.ITrip{

    String trip;
    String city;
    ProgressDialog progress;
    ArrayList<Venue> transfer = new ArrayList<Venue>();
    ArrayList<Trip> transfer1 = new ArrayList<Trip>();
    private DatabaseReference mDatabase;

    EditText tripname, cityname;
    Button addtrip;
    String placeid;
    private DatabaseReference mFavCityReference;
    String key;
    String newcity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        tripname = (EditText) findViewById(R.id.editTextTripName);
        cityname = (EditText) findViewById(R.id.editTextCityName);

        Button search = (Button) findViewById(R.id.buttonSearchAddTrip);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trip = tripname.getText().toString();
                city = cityname.getText().toString();
                executeAsyncTask();
            }
        });


    }
    public void executeAsyncTask(){
        String url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?key=AIzaSyCSXXzVkVWGTL4L8GSe7pb-FRYhpGGtNLM&types=(cities)&input="+city;
        new GetVenueAsync(this).execute(url);
    }
    @Override
    public void setVenueList(ArrayList<Venue> result) {
//        progress.cancel();
        ArrayList<Venue> all_days = new ArrayList<Venue>();
        all_days = result;
        transfer = all_days;
        // Log.d("demo", all_days.get(0).getIcon_url());
        RecyclerView rvDaily = (RecyclerView) findViewById(R.id.rvAddTrip);
        final RVAdapter adapter = new RVAdapter(this, all_days);
        // Attach the adapter to the recyclerview to populate items
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        // Attach layout manager to the RecyclerView
        rvDaily.setLayoutManager(new LinearLayoutManager(this));
        rvDaily.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Log.d("demo1",transfer.get(position).getName().toString());
                newcity = transfer.get(position).getName().toString();
                cityname.setText(transfer.get(position).getName().toString());
                placeid = transfer.get(position).getPlaceid().toString();
            }
        });

        addtrip = (Button) findViewById(R.id.buttonAddTripAddTrip);
        addtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                executeAsyncTask1();
            }
        });
    }
    public void executeAsyncTask1(){
        String url = "https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyCSXXzVkVWGTL4L8GSe7pb-FRYhpGGtNLM&placeid="+placeid;
        new GetTripAsync(this).execute(url);
    }

    @Override
    public void setTripList(ArrayList<Trip> result) {

        ArrayList<Trip> all_days1 = new ArrayList<Trip>();
        all_days1 = result;
        transfer1 = all_days1;
        mFavCityReference = FirebaseDatabase.getInstance().getReference().child("Favtrip");
        key = mFavCityReference.push().getKey();
        String lat = transfer1.get(0).getLat();
        String longi =transfer1.get(0).getLongi();
        Trip e = new Trip(trip, newcity, lat,longi);
        Map<String, Object> postValues = e.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Favtrip/" + key, postValues);
       // e.setKey(key);
        // childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
        Log.d("mystery", "here");
        mDatabase.updateChildren(childUpdates);
        Intent i1 = new Intent(AddTripActivity.this, MainActivity.class);
        startActivity(i1);

    }
}
