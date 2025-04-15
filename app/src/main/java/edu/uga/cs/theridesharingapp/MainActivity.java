package edu.uga.cs.theridesharingapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;


/**
 * This is the main activity of the app, and contains the fragments of the fragments you can
 * access from the main screen and nav bar.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    /**
     * Called to create activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main );

        toolbar = findViewById( R.id.toolbar );

        setSupportActionBar( toolbar );

        drawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawerToggle = setupDrawerToggle();

        drawerToggle.setDrawerIndicatorEnabled( true );
        drawerToggle.syncState();

        drawerLayout.addDrawerListener( drawerToggle );

        navigationView = findViewById( R.id.nvView );
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem( menuItem );
                    return true;
                });
    }

    /**
     * Method handling selecting a drawer item.
     *
     * @param menuItem
     */
    public void selectDrawerItem( MenuItem menuItem ) {
        Fragment fragment = null;

        // Create a new fragment based on the used selection in the nav drawer
        if (menuItem.getItemId() == R.id.menu_help) {
            fragment = new HelpFragment();
        } /*else if (menuItem.getItemId() == R.id.menu_review) {
            fragment = new ReviewQuizGrades();
        } else if (menuItem.getItemId() == R.id.menu_add){
            fragment = new NewQuiz();
        } */else if (menuItem.getItemId() == R.id.menu_close){
            System.exit(1);
            return;
        } else {
            return;
        };

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace( R.id.fragmentContainerView, fragment).addToBackStack("main screen" ).commit();

        drawerLayout.closeDrawers();
    }

    /**
     * Method handling toggling the drawer.
     *
     * @return
     */
    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,  R.string.drawer_close );
    }

    /**
     * Called after view is crated.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate( Bundle savedInstanceState ) {
        super.onPostCreate( savedInstanceState );
        drawerToggle.syncState();
    }

    /**
     * Called when configuration is changed.
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged( @NonNull Configuration newConfig ) {
        super.onConfigurationChanged( newConfig );
        drawerToggle.onConfigurationChanged( newConfig );
    }

    /**
     * Returns true if the item selected is an item.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        if( drawerToggle.onOptionsItemSelected( item ) ) {
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

    /**
     * This runs when the app is paused.
     */
    @Override
    public void onPause() {
        super.onPause();
    }

}