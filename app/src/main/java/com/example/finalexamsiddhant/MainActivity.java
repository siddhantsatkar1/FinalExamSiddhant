package com.example.finalexamsiddhant;
// AIzaSyCSXXzVkVWGTL4L8GSe7pb-FRYhpGGtNLM
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    ProgressDialog progress;
    private DatabaseReference mCityReference;
    private RecyclerView mCityRecycler;
    private CityAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCityReference = FirebaseDatabase.getInstance().getReference()
                .child("Favtrip");
        mCityRecycler = (RecyclerView) findViewById(R.id.rvMain);

        mCityRecycler.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new CityAdapter(this, mCityReference);
        mCityRecycler.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        ImageView addtrip = (ImageView) findViewById(R.id.imageViewAddTrip);
        addtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddTripActivity.class);
                startActivity(i);
            }
        });

    }
}
