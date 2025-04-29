package edu.uga.cs.theridesharingapp;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Fragment displaying ride offers.
 */
public class RideOffersFragment extends Fragment {

    public static final String DEBUG_TAG = "RideOffersFragment";
    private RecyclerView recyclerView;
    private RidesRecyclerAdapter recyclerAdapter;
    private List<Ride> rideList;
    private FirebaseDatabase database;

    /**
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
    }

    /**
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ride_offers, container, false);
    }

    /**
     *
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated( @NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated(view,savedInstanceState);

        rideList = new ArrayList<Ride>();

        recyclerView = view.findViewById(R.id.offersRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new RidesRecyclerAdapter(requireContext(), rideList, "rideOffers");
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
                    if(!ride.isRideType() && !ride.getAccepted() && !Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid().equals(ride.getAuthor())) {
                        rideList.add(ride);
                    }
                    Log.d(DEBUG_TAG, "ValueEventListener: added: " + ride);
                    Log.d(DEBUG_TAG, "ValueEventListener: key: " + postSnapshot.getKey());
                }

                // Sort by date (soonest first)
                Collections.sort(rideList, new Comparator<Ride>() {
                    @Override
                    public int compare(Ride r1, Ride r2) {
                        if (r1.getDate() == null) return 1;
                        if (r2.getDate() == null) return -1;
                        return r1.getDate().compareTo(r2.getDate());
                    }
                });

                Log.d(DEBUG_TAG, "ValueEventListener: notifying recyclerAdapter");
                recyclerAdapter.notifyDataSetChanged();
            }

            /**
             * Shows error when cancelled.
             *
             * @param databaseError A description of the error that occurred
             */
            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                System.out.println( "ValueEventListener: reading failed: " + databaseError.getMessage() );
            }
        } );

    }
}
