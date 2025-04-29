package edu.uga.cs.theridesharingapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * An activity for editing an existing ride posted by the current user.
 */
public class EditRide extends AppCompatActivity {

    public static final String DEBUG_TAG = "AddRideActivity";

    private EditText dateView;
    private EditText startLocView;
    private EditText destLocView;
    private RadioGroup radioGroup;
    private RadioButton offerOption; //false
    private RadioButton requestOption; //true
    private Calendar selectedDateTime;

    private String key;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ride);

        key = getIntent().getStringExtra("key");

        dateView = findViewById(R.id.editTextText3);
        startLocView = findViewById(R.id.editTextText4);
        destLocView = findViewById(R.id.editTextText5);
        radioGroup = findViewById(R.id.radioGroup1);
        offerOption = findViewById(R.id.radioButton1);
        requestOption = findViewById(R.id.radioButton2);
        offerOption.setChecked(true);

        selectedDateTime = Calendar.getInstance();

        updateDateDisplay();

        dateView.setOnClickListener(v -> showDateTimePicker());

        Button saveButton = findViewById(R.id.button7);

        saveButton.setOnClickListener(new ButtonClickListener());

    }

    /**
     * A method for setting the default date to now.
     */
    private void showDateTimePicker() {
        Calendar currentDate = Calendar.getInstance();

        new DatePickerDialog(this, (DatePicker view, int year, int month, int day) -> {
            selectedDateTime.set(year, month, day);
            new TimePickerDialog(this, (TimePicker view1, int hour, int minute) -> {
                selectedDateTime.set(Calendar.HOUR_OF_DAY, hour);
                selectedDateTime.set(Calendar.MINUTE, minute);
                updateDateDisplay();
            },
                    currentDate.get(Calendar.HOUR_OF_DAY),
                    currentDate.get(Calendar.MINUTE),
                    false).show();
        },
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * Method for updating the date.
     */
    private void updateDateDisplay() {
        dateView.setText(android.text.format.DateFormat.getDateFormat(this).format(selectedDateTime.getTime()) + " " +
                android.text.format.DateFormat.getTimeFormat(this).format(selectedDateTime.getTime()));
    }

    /**
     * Button click listener for accepting the edit.
     */
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currUser == null) {
                Toast.makeText(getApplicationContext(), "No user currently logged in", Toast.LENGTH_SHORT).show();
                return;
            }

            String userId = currUser.getUid();


            String startLoc = startLocView.getText().toString();
            String destLoc = destLocView.getText().toString();

            if (startLoc.isEmpty() || destLoc.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Boolean rideType = radioGroup.getCheckedRadioButtonId() == R.id.radioButton2;

            Ride ride = new Ride(selectedDateTime.getTime(), startLoc, destLoc, false, rideType);
            ride.setAuthor(userId);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("rides").child(key);
            if(rideType) {
                ride.setRider(currUser.getEmail());
            } else {
                ride.setDriver(currUser.getEmail());
            }
            myRef.setValue(ride)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getApplicationContext(), "Ride created successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity after successful save
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getApplicationContext(), "Failed to create ride", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
