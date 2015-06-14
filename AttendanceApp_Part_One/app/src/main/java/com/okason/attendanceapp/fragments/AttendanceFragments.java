package com.okason.attendanceapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.okason.attendanceapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceFragments extends Fragment {


    public AttendanceFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attendance_fragments, container, false);
    }


}
