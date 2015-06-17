package com.okason.attendanceapp.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.okason.attendanceapp.Helpers.Constants;
import com.okason.attendanceapp.R;
import com.okason.attendanceapp.models.Attendance;
import com.okason.attendanceapp.models.Attendant;
import com.okason.attendanceapp.models.Event;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Valentine on 6/16/2015.
 */
public class AttendantsAdapter extends RecyclerView.Adapter<AttendantsAdapter.ViewHolder>{
    private List<Attendant> mAttendants;
    private Context mContext;
    View rowView;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView attendantHeadshot;
        TextView attendantName, attendantEmail;
        ToggleButton checkInCheckOutButton;

        public ViewHolder(View itemView) {
            super(itemView);
            attendantHeadshot = (ImageView) itemView.findViewById(R.id.image_view_attendant_head_shot);
            attendantName = (TextView)itemView.findViewById(R.id.text_view_attendants_name);
            attendantEmail = (TextView)itemView.findViewById(R.id.text_view_attendants_email);
            checkInCheckOutButton = (ToggleButton)itemView.findViewById(R.id.togglebutton);
        }
    }

    public AttendantsAdapter(List<Attendant> attendantsList, Context context){
        mAttendants = attendantsList;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AttendantsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendants_list_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(rowView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AttendantsAdapter.ViewHolder holder, int position) {
        final Attendant selectedAttendant = mAttendants.get(position);

        holder.attendantName.setText(selectedAttendant.getName());
        holder.attendantEmail.setText(selectedAttendant.getEmail());
        Picasso.with(mContext).load(selectedAttendant.getProfileImageId()).into(holder.attendantHeadshot);

        if (position % 2 == 0){
            rowView.setBackgroundColor(mContext.getResources().getColor(R.color.activated_color));
        }

        holder.checkInCheckOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Is the toggle on?
                boolean on = ((ToggleButton) v).isChecked();

                //Create new attendance object
                Attendance mAttendance = new Attendance();

                //obtain an instance of SharedPreference
                SharedPreferences mPref = mContext.getSharedPreferences(Constants.ATTENDANT_ID, Context.MODE_PRIVATE);
                SharedPreferences.Editor mEditor = mPref.edit();


                if (on){
                    selectedAttendant.setIsCheckedIn(true);

                    //get the id of the currently Active Event
                    long idOfTheActiveEvent = mPref.getLong(Constants.ACVTIVE_EVENT_ID, 0);

                    //Check if the active event is null
                    if (idOfTheActiveEvent > 0){
                        //Find the Active event from the database bawsed on the event ID
                        Event activeEvent = Event.findById(Event.class, idOfTheActiveEvent);
                        //Check if the event that you got back is null
                        if (activeEvent != null){
                            //associate this Attendance record to the selected active event
                            mAttendance.setEvent(activeEvent);

                            //associate this Attendance record to the selected Attendant
                            mAttendance.setAttendant(selectedAttendant);

                            //set the current time as the check-in time for this Attendance record
                            mAttendance.setCheckInTime(System.currentTimeMillis());

                            //notify user of successful check in
                            Toast.makeText(mContext, selectedAttendant.getName() + " checked in ", Toast.LENGTH_SHORT).show();

                        }else {
                            //The event is null, notify the user that there is no event to check
                            //into or to go to the Event list and set one of the listed events as active
                            Toast.makeText(mContext, "Unable to  checked in, no active event found ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, "Unable to  checked in, no active event found ", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    //If this is a checkout
                    List<Attendance> AllTheAttendanceRecords = Attendance.listAll(Attendance.class);

                    //Ensure the Attendance record is not empty
                    if (AllTheAttendanceRecords != null && AllTheAttendanceRecords.size() > 0) {
                        //Go through all the attendance record to find out which record belongs to this attendant
                        for (Attendance record: AllTheAttendanceRecords){
                            if (record.getAttendant().equals(selectedAttendant)){
                                //This attendance record belongs to the selected Attendanc
                                //Check if check-out time is empty
                                if (record.getCheckInTime() != null && record.getCheckOutTime() == null){
                                    //set the check-out time
                                    record.setCheckOutTime(System.currentTimeMillis());
                                    Toast.makeText(mContext, selectedAttendant.getName() + " checked out ", Toast.LENGTH_SHORT).show();
                                }
                                //no need to continue checking
                                break;
                            }
                            Toast.makeText(mContext, " Unable to check out - no attendance record found ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mAttendants.size();
    }

    public void Add(Attendant attendant){
        mAttendants.add(attendant);
        notifyDataSetChanged();

    }


}

