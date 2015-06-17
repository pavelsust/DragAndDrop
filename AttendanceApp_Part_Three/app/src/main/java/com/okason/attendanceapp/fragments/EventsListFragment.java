package com.okason.attendanceapp.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.okason.attendanceapp.Helpers.Constants;
import com.okason.attendanceapp.MainActivity;
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
public class EventsListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Event> mEventsList;
    private View mRootView;



    public EventsListFragment() {
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
                promptForAdd();
            }
        });

        final GestureDetector mGestureDetector =
                new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }
                });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    int pos = recyclerView.getChildLayoutPosition(child);
                    Event selectedEvent = mEventsList.get(pos);
                    PickActionPrompt(selectedEvent);
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        mAdapter = new EventsAdapter(mEventsList, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        return mRootView;
    }

    private void setEventAsActive(Event selectedEvent){
        //obtain an instance of the SharedPreference
        SharedPreferences mPref = getActivity().getSharedPreferences(Constants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mPref.edit();

        //put the id of the selected event to the SharedPreference
        mEditor.putLong(Constants.ACVTIVE_EVENT_ID, selectedEvent.getId());
        mEditor.commit();
        Toast.makeText(getActivity(), selectedEvent.getName() + " is now the Active Event", Toast.LENGTH_SHORT).show();
    }

    private void shareEvent(Event selectedEvent){
        java.text.DateFormat dateFormat = DateFormat.getMediumDateFormat(getActivity());

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "You are invited to our upcoming event - " +
                selectedEvent.getName() + " on " + dateFormat.format(selectedEvent.getEventDate()));
        shareIntent.setType("text/plain");
        startActivity(shareIntent);
    }

    private void deleteEvent(Event selectedEvent){
        final Event eventToBeDeleted = selectedEvent;
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(getActivity());
        saveDialog.setTitle("Delete Event!");
        saveDialog.setMessage("Are you sure you want to delete " + selectedEvent.getName() + "?");
        saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                eventToBeDeleted.delete();
            }
        });
        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        saveDialog.show();
    }

    private void PickActionPrompt(final Event selectedEvent){
        final String[] options = { getString(R.string.share),  getString(R.string.delete_events), getString(R.string.set_active_event), getString(R.string.cancel)};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(selectedEvent.getName());
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        shareEvent(selectedEvent);
                        break;
                    case 1:
                        deleteEvent(selectedEvent);
                        break;
                    case 2:
                        setEventAsActive(selectedEvent);
                    case 3:
                        dialog.dismiss();
                        break;
                    default:
                        dialog.dismiss();
                        break;
                }
            }
        });
        builder.show();
    }

    /**
     * method that is called when the Floating Action Button is clicked
     * This shows a dialog to pick an item add and the options are an Event or Attendant
     */
    private void promptForAdd(){
        //Create a list of options or actions to be shown in the dialog prompt
        final String[] options = {getString(R.string.title_create_event), getString(R.string.title_registration), getString(R.string.cancel)};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add ...");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Get a reference to the parent Activity so we can call methods
                //In the parent activity, the signature of those methods has to be public

                MainActivity parentActivity = (MainActivity)getActivity();

                switch (which){
                    case 0:
                        parentActivity.openFragment(new AddEventFragment());
                        break;
                    case 1:
                        parentActivity.openFragment(new AddAttendantFragment());
                        break;
                    case 2:
                        dialog.dismiss();
                        break;
                }

            }
        });

        builder.show();
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
