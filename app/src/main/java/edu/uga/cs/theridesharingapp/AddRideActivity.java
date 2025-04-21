package edu.uga.cs.theridesharingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddRideActivity extends AppCompatActivity {

    public static final String DEBUG_TAG = "AddRideActivity";

    private EditText dateView;
    private EditText startLocView;
    private EditText destLocView;
    private RadioGroup radioGroup;
    private RadioButton offerOption; //false
    private RadioButton requestOption; //true


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ride);

        dateView = findViewById(R.id.editTextText3);
        startLocView = findViewById(R.id.editTextText4);
        destLocView = findViewById(R.id.editTextText5);
        radioGroup = findViewById(R.id.radioGroup1);
        offerOption = findViewById(R.id.radioButton1);
        requestOption = findViewById(R.id.radioButton2);

        offerOption.setChecked(true);


        Button saveButton = findViewById(R.id.button7);

        saveButton.setOnClickListener(new ButtonClickListener());

    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String date = dateView.getText().toString();
            String startLoc = startLocView.getText().toString();
            String destLoc = destLocView.getText().toString();

            Boolean rideType;
            int selectedId = radioGroup.getCheckedRadioButtonId();

            if (selectedId == R.id.radioButton2) { // Request
                rideType = true;
            } else if (selectedId == R.id.radioButton1) { // Offer
                rideType = false;
            } else {
                // No selection made
                Toast.makeText(getApplicationContext(), "Please select ride type", Toast.LENGTH_SHORT).show();
                return;
            }

            final Ride ride = new Ride(date, startLoc, destLoc, rideType); //oof

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("rides");

            myRef.push().setValue( ride )
                    .addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Show a quick confirmation
                            Toast.makeText(getApplicationContext(), "ride created for " + ride.getAuthor(),
                                    Toast.LENGTH_SHORT).show();

                            // Clear the EditTexts for next use.
                            dateView.setText("");
                            startLocView.setText("");
                            destLocView.setText("");
                            radioGroup.clearCheck(); // Clear radio selection

                        }
                    })
                    .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure( @NonNull Exception e ) {
                            Toast.makeText( getApplicationContext(), "Failed to create a ride for " + ride.getAuthor(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }
}