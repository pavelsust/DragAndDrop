package com.okason.attendanceapp.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.okason.attendanceapp.listener.OnSelectEventDateListener;

import java.util.Calendar;

/**
 * Created by Valentine on 6/20/2015.
 */
public class EventDatePickerDialogFragment extends DialogFragment
                        implements DatePickerDialog.OnDateSetListener{

        public OnSelectEventDateListener mListener;

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
        mListener.onEventDateSelected(year, month, day);
    }

    public void setOnSelectEventDateListener(
            OnSelectEventDateListener eventDateListener ){
        mListener = eventDateListener;

    }
}
