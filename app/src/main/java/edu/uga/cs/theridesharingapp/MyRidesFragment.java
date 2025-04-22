package edu.uga.cs.theridesharingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

        acceptedRidesButton.setOnClickListener(new acceptedRidesButtonClickListener());
        postedRidesButton.setOnClickListener(new postedRidesButtonClickListener());
        addButton.setOnClickListener(new AddRideButtonClickListener());
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