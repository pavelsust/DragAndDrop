package com.okason.attendanceapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.okason.attendanceapp.adapters.NavDrawerAdapter;
import com.okason.attendanceapp.fragments.AddEventFragment;
import com.okason.attendanceapp.fragments.AttendanceFragment;
import com.okason.attendanceapp.fragments.AttendantFragment;
import com.okason.attendanceapp.fragments.EventsFragment;
import com.okason.attendanceapp.models.DrawerItem;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    public String HEADER_NAME = "Val Okafor";
    public String HEADER_EMAIL = "valokafor@someemail.com";
    public int HEADER_IMAGE = R.drawable.val_okafor;

    private final static int ATTENDANCE_FRAGMENT = 1;
    private final static int EVENTS_FRAGMENT = 2;
    private final static int ATTENDANT_FRAGMENT = 3;
    private final static int CREATE_EVENT_FRAGMENT = 4;

    private int currentFragment = 1;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DrawerLayout Drawer;
    private RecyclerView.Adapter mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private List<DrawerItem> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        dataList = new ArrayList<DrawerItem>();
        addItemsToDataList();

        mAdapter = new NavDrawerAdapter(dataList, this, HEADER_NAME, HEADER_EMAIL, HEADER_IMAGE);

        mRecyclerView.setAdapter(mAdapter);

        final GestureDetector mGestureDetector =
                new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }
                });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());

                if (child != null && mGestureDetector.onTouchEvent(motionEvent)){
                    Drawer.closeDrawers();
                    onTouchDrawer(recyclerView.getChildLayoutPosition(child));
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

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        Drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        onTouchDrawer(currentFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openFragment(final Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    private void onTouchDrawer(final int position) {
    // if (currentFragment == position) return;
     currentFragment = position;
        switch (position) {
            case ATTENDANCE_FRAGMENT:
                openFragment(new AttendanceFragment());
                setTitle(getString(R.string.title_attendants));
                break;
            case EVENTS_FRAGMENT:
                openFragment(new EventsFragment());
                setTitle(getString(R.string.title_events));
                break;
            case ATTENDANT_FRAGMENT:
                openFragment(new AttendantFragment());
                setTitle(getString(R.string.title_registration));
                break;
            case CREATE_EVENT_FRAGMENT:
                openFragment(new AddEventFragment());
                setTitle(getString(R.string.title_create_event));
                break;
            default:
                return;
        }
    }

    public void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    private void addItemsToDataList() {
        dataList.add(new DrawerItem(getString(R.string.title_attendants), R.drawable.ic_action_attendants));
        dataList.add(new DrawerItem(getString(R.string.title_events), R.drawable.ic_action_events));
        dataList.add(new DrawerItem(getString(R.string.title_registration), R.drawable.ic_action_attendant));
        dataList.add(new DrawerItem(getString(R.string.title_create_event), R.drawable.ic_action_create_event));}
}
