package edu.uga.cs.theridesharingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class RidesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rides);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button requestButton = findViewById(R.id.button1);
        Button offerButton = findViewById(R.id.button2);

        Button addButton = findViewById(R.id.AddButton);

        requestButton.setOnClickListener(new RequestButtonClickListener());
        offerButton.setOnClickListener(new OfferButtonClickListener());

        addButton.setOnClickListener(new AddRideButtonClickListener());





    }

    private class AddRideButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), AddRideActivity.class);
            view.getContext().startActivity( intent );
        }
    }

    private class OfferButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Fragment fragment = null;

            fragment = new RideOffersFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace( R.id.fragmentContainerView1, fragment).addToBackStack("main screen" ).commit();
        }
    }

    private class RequestButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Fragment fragment = null;

            fragment = new RideRequestsFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace( R.id.fragmentContainerView1, fragment).addToBackStack("main screen" ).commit();
        }
    }


}