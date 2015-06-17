package com.okason.attendanceapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.okason.attendanceapp.R;
import com.okason.attendanceapp.models.Attendant;
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

                if (on){
                    selectedAttendant.setIsCheckedIn(true);
                    Toast.makeText(mContext, selectedAttendant.getName() + " checked in ", Toast.LENGTH_SHORT).show();
                } else {
                    selectedAttendant.setIsCheckedIn(false);
                    Toast.makeText(mContext, selectedAttendant.getName() + " checked out ", Toast.LENGTH_SHORT).show();
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

