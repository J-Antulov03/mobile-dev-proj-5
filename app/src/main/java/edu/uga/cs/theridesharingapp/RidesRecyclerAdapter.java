package edu.uga.cs.theridesharingapp;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RidesRecyclerAdapter
                    extends RecyclerView.Adapter<RidesRecyclerAdapter.RideHolder>{

    public static final String DEBUG_TAG = "QuizRecyclerAdapter";

    private final Context context;
    private List<Ride> rideList;

    private String pageType;

    /**
     * Default constructor
     * @param context the context of the app
     * @param rideList the list of quizzes to display
     */
    public RidesRecyclerAdapter(Context context, List<Ride> rideList, String pageType) {
        this.context = context;
        this.rideList = rideList;
        this.pageType = pageType;
    }

    /**
     * Class to create the recyclerview
     */
    public static class RideHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView rideStart;
        TextView rideEnd;

        Button acceptButton;

        Button deleteButton;

        Button editButton;

        /**
         * default constructor to hold a quiz object
         * @param itemView the itemview to "hold"
         */
        public RideHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.rideDate);
            rideStart = itemView.findViewById(R.id.startLoc);
            rideEnd = itemView.findViewById(R.id.destLoc);
            acceptButton = itemView.findViewById(R.id.acceptButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }

    /**
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return the new Quizholder object
     */
    @NonNull
    @Override
    public RideHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ride, parent, false);
        return new RideHolder(view);
    }

    /**
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(RideHolder holder, int position) {
        Ride ride = rideList.get(position);

        holder.date.setText("Date Of Ride: " + ride.getDate());
        holder.rideStart.setText("Starting Location: " + ride.getStartLoc());
        holder.rideEnd.setText("Destination: " + (ride.getDestLoc()));
        if(pageType.equals("myRides")) {
            holder.acceptButton.setVisibility(View.GONE);
            holder.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,EditRide.class);
                    intent.putExtra("key", ride.getKey());
                    context.startActivity(intent);
                }
            });
        } else {
            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
        }

    }

    /**
     *
     * @return int count the counts of items in the recyclerview
     */
    @Override
    public int getItemCount() {
        if(rideList != null) {
            return rideList.size();
        } else {
            return 0;
        }
    }
}
