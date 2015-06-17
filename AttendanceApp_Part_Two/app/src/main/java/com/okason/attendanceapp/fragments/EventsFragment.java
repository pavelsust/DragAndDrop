package com.okason.attendanceapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;
import com.okason.attendanceapp.R;
import com.okason.attendanceapp.adapters.EventsAdapter;
import com.okason.attendanceapp.models.Attendant;
import com.okason.attendanceapp.models.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Event> mEventsList;
    private View mRootView;



    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mRootView = inflater.inflate(R.layout.fragment_events, container, false);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.events_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        mEventsList = new ArrayList<>();
        addTestEvents();

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        FloatingActionButton fab = (FloatingActionButton) mRootView.findViewById(R.id.fab);
        fab.attachToRecyclerView(mRecyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //promptForAdd();
            }
        });

        mAdapter = new EventsAdapter(mEventsList, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        return mRootView;
    }


    private void addTestEvents(){

        Event event1 = new Event();
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.MONTH, 1);
        event1.setName("Home Coming Party");
        event1.setEventDate(cal1.getTimeInMillis());
        event1.setVenue("500 West Broadway");
        event1.setCity("San Diego");
        event1.setEventPicturePath("http://www.proveninc.com/images/localpics/local-SanDiego-txgciexre.png");

        List<Attendant>  guestList1 = new ArrayList<>();
        Attendant guest1 = new Attendant();
        guest1.setName("Debbie Sam");
        guest1.setEmail("deb@email.net");
        guest1.setProfileImageId(R.drawable.headshot_1);
        guestList1.add(guest1);

        Attendant guest2 = new Attendant();
        guest2.setName("Keisha Williams");
        guest2.setEmail("diva@comcast.com");
        guest2.setProfileImageId(R.drawable.headshot_2);
        guestList1.add(guest2);

        Attendant guest3 = new Attendant();
        guest3.setName("Gregg McQuire");
        guest3.setEmail("emailing@nobody.com");
        guest3.setProfileImageId(R.drawable.headshot_3);
        guestList1.add(guest3);
        event1.setGuestList(guestList1);
        event1.setEventPictureId(R.drawable.val_okafor);
        mEventsList.add(event1);

        Event event2 = new Event();
        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.MONTH, 2);
        cal2.add(Calendar.DAY_OF_WEEK, 8);
        event2.setName("Sarah's Wedding");
        event2.setEventDate(cal2.getTimeInMillis());
        event2.setVenue("3240 La Jolla Village Drive");
        event2.setCity("La Jolla");
        event2.setEventPicturePath("https://c1.staticflickr.com/9/8375/8424083864_43bd772810_b.jpg");
        List<Attendant>  guestList2 = new ArrayList<>();

        Attendant guest4 = new Attendant();
        guest4.setName("Nancy Watson");
        guest4.setEmail("nancy@hotmail.com");
        guest4.setProfileImageId(R.drawable.headshot_4);
        guestList2.add(guest4);

        Attendant guest5 = new Attendant();
        guest5.setName("Sarah Domingo");
        guest5.setEmail("sarah@yahoo.com");
        guest5.setProfileImageId(R.drawable.headshot_5);
        guestList2.add(guest5);

        Attendant guest6 = new Attendant();
        guest6.setName("Anthony Lopez");
        guest6.setEmail("toney@gmail.com");
        guest6.setProfileImageId(R.drawable.headshot_6);
        guestList2.add(guest6);
        event2.setGuestList(guestList2);
        mEventsList.add(event2);

        Event event3 = new Event();
        Calendar cal3 = Calendar.getInstance();
        cal3.add(Calendar.MONTH, 3);
        cal2.add(Calendar.DAY_OF_WEEK, -2);
        event3.setName("Ben's Graduation Celebration");
        event3.setEventDate(cal3.getTimeInMillis());
        event3.setVenue("3245 Main Street");
        event3.setCity("Chula Vista");
        event3.setEventPicturePath("https://c2.staticflickr.com/8/7156/6565302335_21780223bf_z.jpg");
        List<Attendant>  guestList3 = new ArrayList<>();

        Attendant guest8 = new Attendant();
        guest8.setName("Frank Krueger");
        guest8.setEmail("frank@ymail.com");
        guest8.setProfileImageId(R.drawable.headshot_8);
        guestList3.add(guest8);

        Attendant guest9 = new Attendant();
        guest9.setName("Bella Florentino");
        guest9.setEmail("bella@outlook.com");
        guest9.setProfileImageId(R.drawable.headshot_9);
        guestList3.add(guest9);

        Attendant guest10 = new Attendant();
        guest10.setName("Donna Simons");
        guest10.setEmail("donna@company.com");
        guest10.setProfileImageId(R.drawable.headshot_10);
        guestList3.add(guest10);
        event3.setGuestList(guestList3);
        mEventsList.add(event3);
    }


}
