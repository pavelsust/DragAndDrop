package com.okason.attendanceapp.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.okason.attendanceapp.R;
import com.okason.attendanceapp.models.Event;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEventFragment extends Fragment {
    private EditText mName,  mVenue, mCity, mImage, mOrganizer;
    Button mSaveButton,mDate ;
    private View mRootView;
    private Event mEvent;    public Calendar mEventDate;




    public AddEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_add_event, container, false);
        initView();
        return mRootView;
    }

    private void initView() {
        mName = (EditText) mRootView.findViewById(R.id.edit_text_event_name);
        mDate = (Button)mRootView.findViewById(R.id.event_date_button);
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        mVenue = (EditText) mRootView.findViewById(R.id.edit_text_event_venue);
        mCity = (EditText) mRootView.findViewById(R.id.edit_text_event_city);
        mImage = (EditText)mRootView.findViewById(R.id.edit_text_event_image);
        mOrganizer = (EditText) mRootView.findViewById(R.id.edit_text_event_organizer);
        mSaveButton = (Button)mRootView.findViewById(R.id.button_add_event);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requiredFieldsCompleted()) {
                    saveAttendant();
                }

            }
        });
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new EventDatePickerDialogFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private boolean requiredFieldsCompleted() {
        //Check if required information are entered, here we are only checking for
        //Name and email and you can check for more or none
        boolean result = false;
        if (mName.getText() != null && !mName.getText().toString().isEmpty()) {
            result = true;
        } else {
            mName.setError(getString(R.string.required_fields_empty));
        }

        if (mVenue.getText() != null && !mVenue.getText().toString().isEmpty()) {
            result = true;
        } else {
            mVenue.setError(getString(R.string.required_fields_empty));
        }
        return result;
    }

    private void resetFields(){
        //Wipe out any information the user entered so they can enter another
        //Attendant
        mName.setText("");
        mDate.setText("");
        mVenue.setText("");
        mCity.setText("");
        mOrganizer.setText("");
    }

    private void saveAttendant() {
        //populate the Attendant object with the data entered in the screen
        mEvent = new Event();
        mEvent.setName(mName.getText().toString());
        mEvent.setVenue(mVenue.getText().toString());
        mEvent.setCity(mCity.getText().toString());
        mEvent.setCity(mCity.getText().toString());
        mEvent.setOrganizerName(mOrganizer.getText().toString());
        mEvent.setEventPicturePath(mImage.getText().toString());

        if (mEventDate != null){
            mEvent.setEventDate(mEventDate.getTimeInMillis());
        }

        //Save to the database
        mEvent.save();

        //Wipe all fields
        resetFields();

        //Provide feedback to the user
        Toast.makeText(getActivity(), mEvent.getName() + " saved", Toast.LENGTH_SHORT).show();

    }


     class EventDatePickerDialogFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener{


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }


        public void onDateSet(DatePicker view, int year, int month, int day) {
            if (year < 0){
                mEventDate = null;
            } else {
                mEventDate = Calendar.getInstance();
                mEventDate.set(year, month, day);
            }
        }
    }



}
