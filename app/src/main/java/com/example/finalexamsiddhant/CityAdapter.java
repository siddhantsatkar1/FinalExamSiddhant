package com.example.finalexamsiddhant;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sid on 12-12-2016.
 */

public class CityAdapter extends
        RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private Context mContext;
    private DatabaseReference mRef;
    private String mUserId;
    List<String> mCityIds = new ArrayList<>();
    List<Trip> mCities = new ArrayList<>();
    // Define listener member variable
    private static OnItemClickListener listener;
    private static OnLongItemClickListener longlistener;
    String key;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnLongItemClickListener {
        void onItemLongClick(View itemView, int position);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnLongClickListener(OnLongItemClickListener listener) {
        this.longlistener = listener;
    }

    // Pass in the contact array into the constructor
    public CityAdapter(Context context, DatabaseReference ref) {

        mRef = ref;
        mContext = context;

       /* mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                mExpenses.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Expense exp = postSnapshot.getValue(Expense.class);
                    mExpenses.add(exp);
                    Log.d("i1","listener");
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }


        });
*/
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("adapter", "onChildAdded:" + dataSnapshot.getKey());
                // A new comment has been added, add it to the displayed list
                Trip city = dataSnapshot.getValue(Trip.class);
                Log.d("exp", city.toString());
                Log.d("i2", "child");
                // [START_EXCLUDE]
                // Update RecyclerView
                mCityIds.add(dataSnapshot.getKey());
                mCities.add(city);
                notifyItemInserted(mCities.size() - 1);
                // [END_EXCLUDE]
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }
/*
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("adapter", "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                Comment newComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();

                // [START_EXCLUDE]
                int commentIndex = mCommentIds.indexOf(commentKey);
                if (commentIndex > -1) {
                    // Replace with the new data
                    mComments.set(commentIndex, newComment);

                    // Update the RecyclerView
                    notifyItemChanged(commentIndex);
                } else {
                    Log.w(TAG, "onChildChanged:unknown_child:" + commentKey);
                }
                // [END_EXCLUDE]
            }*/

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("adapter", "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String expenseKey = dataSnapshot.getKey();

                // [START_EXCLUDE]
                int expIndex = mCityIds.indexOf(expenseKey);
                if (expIndex > -1) {
                    // Remove data from the list
                    mCityIds.remove(expIndex);
                    mCities.remove(expIndex);

                    // Update the RecyclerView
                    notifyItemRemoved(expIndex);
                } else {
                    Log.w("adapter", "onChildRemoved:unknown_child:" + expenseKey);
                }
                // [END_EXCLUDE]
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

           /* @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                Comment movedComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }*/

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("adapter", "postComments:onCancelled", databaseError.toException());
                Toast.makeText(mContext, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mRef.addChildEventListener(childEventListener);
    }
    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }
    // Your holder should contain a member variable
    // for any view that will be set as you render a row
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Store the context for easy access

        public TextView tripnameTV;
        public TextView citynameTV;
        //public TextView expenseAmt;
        ImageView imgMap;
        ImageView imgAdd;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            tripnameTV = (TextView) itemView.findViewById(R.id.textViewTripNameMain);

            citynameTV = (TextView) itemView.findViewById(R.id.textViewCityNameMain);
            //expenseAmt = (TextView) itemView.findViewById(R.id.textViewAmount);
            imgMap = (ImageView) itemView.findViewById(R.id.imageViewMapRowItem);
            imgAdd = (ImageView) itemView.findViewById(R.id.imageViewAddPlaceRowItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (listener != null && position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (longlistener != null) {
                        int position = getAdapterPosition();
                        if (longlistener != null && position != RecyclerView.NO_POSITION) {
                            longlistener.onItemLongClick(itemView, position);
                        }
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.row_item_main_activity, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(CityAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Trip current = mCities.get(position);
        final String tname = current.getTripname();
        final String cname = current.getCityname();
        // Set item views based on your views and data model
        TextView trName = viewHolder.tripnameTV;
        trName.setText(current.getTripname());
        TextView expName = viewHolder.citynameTV;
        expName.setText(current.getCityname());
        //TextView expAmt = viewHolder.expenseAmt;
        //expAmt.setText("$" + current.getAmount());
        ImageView mappic = viewHolder.imgMap;
        ImageView addpic = viewHolder.imgAdd;
        /*mappic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //mContext.startActivity(mContext, MapsActivity.class);
                /*DatabaseReference Userref = FirebaseDatabase.getInstance().getReference().getRoot().child("Favcity").child(mUserId);
                Userref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("kkk", "in here ");
                        for(DataSnapshot child : dataSnapshot.getChildren()) {
                            child.getValue();
                            Log.d("demotostring", child.toString());
                            Log.d("demovalue", child.getValue().toString());
                            Log.d("demovalue", child.getKey().toString());
                            if (child.getValue().toString().contains(cname)) {
                                //addfavcity.setImageResource(R.drawable.favorite);
                                key = child.getKey().toString();
                                DatabaseReference db_node = FirebaseDatabase.getInstance().getReference().getRoot().child("Favcity").child(mUserId).child(key);
                                db_node.setValue(null);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("chat11", "failed");
                    }
                });
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }

    // Returns the total count of items in the list

}
