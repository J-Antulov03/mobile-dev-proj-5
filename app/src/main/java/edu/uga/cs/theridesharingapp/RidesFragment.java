package edu.uga.cs.theridesharingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A fragment showing information on posted rides.
 */
public class RidesFragment extends Fragment {

    /**
     * Default constructor
     */
    public RidesFragment() {
        // Required empty public constructor
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
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rides, container, false);
    }

    /**
     *
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button requestButton = view.findViewById(R.id.button1);
        Button offerButton = view.findViewById(R.id.button2);
        Button addButton = view.findViewById(R.id.AddButton);
        TextView pointsTextView = view.findViewById(R.id.pointsTextView);

        requestButton.setOnClickListener(new RequestButtonClickListener());
        offerButton.setOnClickListener(new OfferButtonClickListener());
        addButton.setOnClickListener(new AddRideButtonClickListener());

        FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference points = database.getReference("points").child(currUser.getUid());
        points.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String text = "Points balance: " + snapshot.getValue(Long.class);
                pointsTextView.setText(text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Rides", "error reading database.",error.toException());
            }
        });
    }

    /**
     * Loads fragment
     *
     * @param fragment
     */
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragmentContainerView1, fragment);

        transaction.commit();
    }

    /**
     * On click listener for opening the AddRideActivity
     */
    private class AddRideButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(requireContext(), AddRideActivity.class);
            startActivity(intent);
        }
    }

    /**
     * On click listener for opening RideOffersFragment
     */
    private class OfferButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            RideOffersFragment offersFragment = new RideOffersFragment();
            loadFragment(offersFragment);
        }
    }

    /**
     * On click listener for opening RideRequestsFragment
     */
    private class RequestButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            loadFragment(new RideRequestsFragment());

        }
    }
}