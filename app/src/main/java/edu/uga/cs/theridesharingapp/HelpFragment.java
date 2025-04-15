package edu.uga.cs.theridesharingapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * This class is the fragment called when going to the help page. It display information to help
 * the user understand the use of the app.
 */
public class HelpFragment extends Fragment {

    /**
     * Required default constructor.
     */
    public HelpFragment() {
        // Required empty public constructor
    }

    /**
     * This returns a new instance of the HelpFragment.
     *
     * @return
     */
    public static HelpFragment newInstance() {
        HelpFragment fragment = new HelpFragment();
        return fragment;
    }

    /**
     * Called when view is being created.
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Called when view is being created.
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
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_help, container, false );
    }

    /**
     * Called after view has been created. Sets the textviews of the fragment to the necessary info.
     *
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated( @NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated(view,savedInstanceState);

        TextView textView = getView().findViewById( R.id.textView3 );
        String text = getResources().getString( R.string.help_fragment_text );
        textView.setText( HtmlCompat.fromHtml( text, HtmlCompat.FROM_HTML_MODE_LEGACY ) );
    }
}