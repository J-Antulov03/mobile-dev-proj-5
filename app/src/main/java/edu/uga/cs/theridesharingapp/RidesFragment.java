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

public class RidesFragment extends Fragment {

    public RidesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rides, container, false);
    }

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

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragmentContainerView1, fragment);

        transaction.commit();

        /*Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragmentContainerView1);
        if (currentFragment != null) {
            transaction.remove(currentFragment);
        }

        //fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        transaction.replace(R.id.fragmentContainerView1, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();*/
    }

    private class AddRideButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(requireContext(), AddRideActivity.class);
            startActivity(intent);
        }
    }

    private class OfferButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            RideOffersFragment offersFragment = new RideOffersFragment();
            loadFragment(offersFragment);
        }
    }

    private class RequestButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            loadFragment(new RideRequestsFragment());

        }
    }
}