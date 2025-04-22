package edu.uga.cs.theridesharingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;


public class LoginPage extends AppCompatActivity {

    public static final String TAG = "LoginPage";


    EditText emailView;
    EditText passwordView;
    String email;
    String password;
    Button loginButton;
    Button registerButton;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginButton = findViewById(R.id.button4);
        loginButton.setOnClickListener( new ButtonClickListener(

                ) );

        registerButton = findViewById(R.id.button3);
        registerButton.setOnClickListener( (view) -> {
            Intent intent = new Intent(LoginPage.this, UserRegisterActivity.class);
            startActivity(intent);
        } );
    }

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            mAuth = FirebaseAuth.getInstance();

            emailView = findViewById( R.id.editTextText );
            passwordView = findViewById( R.id.editTextTextPassword );

            email = emailView.getText().toString();
            password = passwordView.getText().toString();

            //mAuth.signOut(); // Clear any existing session



            Log.d("here","here");

            mAuth.signInWithEmailAndPassword( email, password )
                    .addOnCompleteListener( LoginPage.this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("here2", "here2");
                            if (task.isSuccessful()) {
                                // Sign in success
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(LoginPage.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails
                                Log.d(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginPage.this, "Incorrect Login or Password", Toast.LENGTH_SHORT).show();
                            }
                            Log.d("here3", "here3");

                        }
                    }).addOnCanceledListener(LoginPage.this, new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                            Log.d("Sign In Listener", "Error, the sign in was cancelled.");
                        }
                    });

        }
    }
}