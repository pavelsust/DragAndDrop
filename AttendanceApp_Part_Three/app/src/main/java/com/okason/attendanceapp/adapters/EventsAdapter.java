package com.okason.attendanceapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.okason.attendanceapp.R;
import com.okason.attendanceapp.models.Event;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

/**
 * Created by Valentine on 6/16/2015.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>{
    private List<Event> mEvents;
    private Context mContext;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_list_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Get a reference to the Event that this row represents
        final Event selectEvent = mEvents.get(position);

        //set the event image using Picasso library
        Picasso.with(mContext).load(selectEvent.getEventPicturePath()).into(holder.eventImage);

        //set the event name, location and number of guests for this event
        holder.eventLocation.setText(selectEvent.getVenue() + ", " + selectEvent.getCity());
        holder.eventName.setText(selectEvent.getName());

        //Format event date
        java.text.DateFormat dateFormat = DateFormat.getMediumDateFormat(mContext);
        holder.eventDate.setText(dateFormat.format(selectEvent.getEventDate()));


        if (selectEvent.getGuestList() != null && selectEvent.getGuestList().size() > 0) {
            int numberOfGuests = selectEvent.getGuestList().size();
            if (numberOfGuests > 1){
                holder.labelGuestComing.setText("Expected Guests");
            } else if (numberOfGuests == 0){
                holder.labelGuestComing.setText("Expected Guest");
            }
            //Add random number to the guest list
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(50);

            holder.numberGuest.setText(String.valueOf(numberOfGuests + randomInt));
        }
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    //Constructor for the adapter
    public EventsAdapter(List<Event> eventsList, Context context){
        mEvents = eventsList;
        mContext = context;

    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView eventName, eventLocation, numberGuest, labelGuestComing, eventDate;
        ImageView eventImage;

        public ViewHolder(View itemView) {
            super(itemView);
            eventImage = (ImageView)itemView.findViewById(R.id.image_view_event_image);
            eventName = (TextView)itemView.findViewById(R.id.text_view_event_name);
            eventLocation = (TextView)itemView.findViewById(R.id.text_view_event_location);
            numberGuest = (TextView)itemView.findViewById(R.id.text_view_number_of_guests);
            eventDate = (TextView)itemView.findViewById(R.id.text_view_event_date);
            labelGuestComing = (TextView)itemView.findViewById(R.id.text_view_label_guest);
        }
    }

}