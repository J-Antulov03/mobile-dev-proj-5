package edu.uga.cs.theridesharingapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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

public class MyRidesFragment extends Fragment {

    public MyRidesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_rides, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button acceptedRidesButton = view.findViewById(R.id.button1);
        Button postedRidesButton = view.findViewById(R.id.button2);
        Button addButton = view.findViewById(R.id.AddButton);
        TextView pointsTextView = view.findViewById(R.id.pointsTextView2);

        acceptedRidesButton.setOnClickListener(new acceptedRidesButtonClickListener());
        postedRidesButton.setOnClickListener(new postedRidesButtonClickListener());
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
                Log.d("myRides", "error reading database.",error.toException());
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragmentContainerView2, fragment);

        transaction.commit();
    }

    private class AddRideButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(requireContext(), AddRideActivity.class);
            startActivity(intent);
        }
    }

    private class postedRidesButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            loadFragment(new PostedRidesFragment());
        }
    }

    private class acceptedRidesButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            loadFragment(new AcceptedRidesFragment());

        }
    }
}