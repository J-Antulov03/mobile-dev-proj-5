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

        requestButton.setOnClickListener(new RequestButtonClickListener());
        offerButton.setOnClickListener(new OfferButtonClickListener());
        addButton.setOnClickListener(new AddRideButtonClickListener());

        // Load default fragment (optional)
        loadFragment(new RideOffersFragment());
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragmentContainerView1);
        if (currentFragment != null) {
            transaction.remove(currentFragment);
        }

        //fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        transaction.replace(R.id.fragmentContainerView1, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
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
            loadFragment(new RideOffersFragment());
        }
    }

    private class RequestButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            loadFragment(new RideRequestsFragment());
        }
    }
}