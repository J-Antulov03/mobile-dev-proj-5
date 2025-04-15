package edu.uga.cs.theridesharingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import edu.uga.cs.theridesharingapp.R;

/**
 * This fragment displays the main screen of the app. From the main screen the user can navigate
 * other pages.
 */
public class MainScreen extends Fragment {

    /**
     * This is the required default constructor.
     */
    public MainScreen() {

    }

    /**
     * This method creates a new instance of the MainScreen fragment.
     *
     * @return fragment
     */
    public static MainScreen newInstance() {
        MainScreen fragment = new MainScreen();
        return fragment;
    }

    /**
     * Called when the view for this fragment is being created.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return inflater.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_main_screen, container, false );
    }

    /**
     * Called after the view for this fragment has been created.
     *
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
    }

    /**
     * Resumes the app when returning after being paused.
     */
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( getResources().getString( R.string.app_name ) );
    }
}

