package edu.uga.cs.theridesharingapp;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RideOffersFragment extends Fragment {

    public static final String DEBUG_TAG = "RideOffersFragment";
    private RecyclerView recyclerView;
    private RidesRecyclerAdapter recyclerAdapter;
    private List<Ride> rideList;
    private FirebaseDatabase database;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ride_offers, container, false);
    }

    @Override
    public void onViewCreated( @NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated(view,savedInstanceState);

        rideList = new ArrayList<Ride>();

        recyclerView = view.findViewById(R.id.offersRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new RidesRecyclerAdapter(requireContext(), rideList);
        recyclerView.setAdapter( recyclerAdapter );

        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("rides");

        myRef.addValueEventListener( new ValueEventListener() {

            @Override
            public void onDataChange( @NonNull DataSnapshot snapshot ) {
                // Once we have a DataSnapshot object, we need to iterate over the elements and place them on our job lead list.
                rideList.clear(); // clear the current content; this is inefficient!
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Ride ride = postSnapshot.getValue(Ride.class);
                    ride.setKey(postSnapshot.getKey());
                    if(!ride.getRideType()) {
                        rideList.add(ride);
                    }
                    Log.d(DEBUG_TAG, "ValueEventListener: added: " + ride);
                    Log.d(DEBUG_TAG, "ValueEventListener: key: " + postSnapshot.getKey());
                }

                Log.d(DEBUG_TAG, "ValueEventListener: notifying recyclerAdapter");
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                System.out.println( "ValueEventListener: reading failed: " + databaseError.getMessage() );
            }
        } );

    }


/*
    public void addJobLead(Ride ride) {
        // add the new job lead
        // Add a new element (JobLead) to the list of job leads in Firebase.
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("rides");

        // First, a call to push() appends a new node to the existing list (one is created
        // if this is done for the first time).  Then, we set the value in the newly created
        // list node to store the new job lead.
        // This listener will be invoked asynchronously, as no need for an AsyncTask, as in
        // the previous apps to maintain job leads.
        myRef.push().setValue(ride)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        // Reposition the RecyclerView to show the JobLead most recently added (as the last item on the list).
                        // Use of the post method is needed to wait until the RecyclerView is rendered, and only then
                        // reposition the item into view (show the last item on the list).
                        // the post method adds the argument (Runnable) to the message queue to be executed
                        // by Android on the main UI thread.  It will be done *after* the setAdapter call
                        // updates the list items, so the repositioning to the last item will take place
                        // on the complete list of items.
                        recyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.smoothScrollToPosition(rideList.size() - 1);
                            }
                        });

                        Log.d(DEBUG_TAG, "Ride saved: " + ride);
                        // Show a quick confirmation
                        Toast.makeText(getApplicationContext(), "Ride created for " + ride.getAuthor(),
                                Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to create a Job lead for " + ride.getAuthor(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

        public void updateJobLead( int position, Ride ride, int action ) {
            if( action == EditJobLeadDialogFragment.SAVE ) {
                Log.d( DEBUG_TAG, "Updating job lead at: " + position + "(" + ride.getAuthor() + ")" );

                // Update the recycler view to show the changes in the updated job lead in that view
                recyclerAdapter.notifyItemChanged( position );

                // Update this job lead in Firebase
                // Note that we are using a specific key (one child in the list)
                DatabaseReference ref = database
                        .getReference()
                        .child( "rides" )
                        .child( ride.getKey() );

                // This listener will be invoked asynchronously, hence no need for an AsyncTask class, as in the previous apps
                // to maintain job leads.
                ref.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                        dataSnapshot.getRef().setValue( ride ).addOnSuccessListener( new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d( DEBUG_TAG, "updated job lead at: " + position + "(" + ride.getAuthor() + ")" );
                                Toast.makeText(getApplicationContext(), "Job lead updated for " + ride.getAuthor(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled( @NonNull DatabaseError databaseError ) {
                        Log.d( DEBUG_TAG, "failed to update job lead at: " + position + "(" + ride.getAuthor() + ")" );
                        Toast.makeText(getApplicationContext(), "Failed to update " + ride.getAuthor(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else if( action == EditJobLeadDialogFragment.DELETE ) {
                Log.d( DEBUG_TAG, "Deleting job lead at: " + position + "(" + ride.getAuthor() + ")" );

                // remove the deleted job lead from the list (internal list in the App)
                rideList.remove( position );

                // Update the recycler view to remove the deleted job lead from that view
                recyclerAdapter.notifyItemRemoved( position );

                // Delete this job lead in Firebase.
                // Note that we are using a specific key (one child in the list)
                DatabaseReference ref = database
                        .getReference()
                        .child( "rides" )
                        .child( ride.getKey() );

                // This listener will be invoked asynchronously, hence no need for an AsyncTask class, as in the previous apps
                // to maintain job leads.
                ref.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                        dataSnapshot.getRef().removeValue().addOnSuccessListener( new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d( DEBUG_TAG, "deleted job lead at: " + position + "(" + ride.getAuthor() + ")" );
                                Toast.makeText(getApplicationContext(), "Job lead deleted for " + ride.getAuthor(),
                                        Toast.LENGTH_SHORT).show();                        }
                        });
                    }

                    @Override
                    public void onCancelled( @NonNull DatabaseError databaseError ) {
                        Log.d( DEBUG_TAG, "failed to delete job lead at: " + position + "(" + ride.getAuthor() + ")" );
                        Toast.makeText(getApplicationContext(), "Failed to delete " + ride.getAuthor(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        */

    }
