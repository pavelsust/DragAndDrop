package com.okason.datetimedemo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class MainActivity extends AppCompatActivity {
    private TextView mTodayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTodayDate = (TextView)findViewById(R.id.today_date);
        dateDemo_1();

        Product temp = new Product();
        temp.productName = "Nice Product";
        temp.dateCreated = System.currentTimeMillis();
        saveDemo(temp);
    }

    private void dateDemo() {
        mTodayDate = (TextView)findViewById(R.id.today_date);

        //Get or Generate Date
        Date todayDate = new Date();

        //Get an instance of the formatter
        DateFormat dateFormat = DateFormat.getDateTimeInstance();

        //If you want to show only the date then you will use
        //DateFormat dateFormat = DateFormat.getDateInstance();

        //Format date
        String todayDateTimeString = dateFormat.format(todayDate);

        //display Date
        mTodayDate.setText(todayDateTimeString);
    }

    private void dateDemo_1(){
        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDateString = formatter.format(currentDate);
        mTodayDate.setText(formattedDateString);
    }

    public static class Product{
        private long id;
        private String productName;
        private long dateCreated;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public long getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(long dateCreated) {
            this.dateCreated = dateCreated;
        }
    }

    private String createProductTable = "CREATE TABLE ProductTable(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "productname TEXT NOT NULL," +
            "datecreated BIGINT";

    private String createProductTable2 = "CREATE TABLE ProductTable(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "productname TEXT NOT NULL," +
            "datecreated DATETIME DEFAULT CURRENT_TIMESTAMP";

    private long saveDemo(Product product){
        Long result;
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("productname", product.getProductName());
        values.put("datecreated", product.getDateCreated());
        result = database.insert("ProductTable", null, values);
        database.close();
        return result;
    }

    public class Person{
        private int id;
        private String name;
        private long birthday;
    }

    //Create a person instance
    Person person1 = new Person();
    person1.setname("John Doe");
    if (mBirthday != null){
        person1.setbirthday(mBirthday.getTimeInMillis());
    }

    public GregorianCalendar mBirthday;

    private void showDatePickerDialog() {
        //fire up the logic show the date picker dialog
        DialogFragment datePickerFragment = new BirthDatePickerDialogFragment();
        datePickerFragment.setTargetFragment(BirthdayFragment.this, 0);
        datePickerFragment.show(getFragmentManager(), "datePicker");

    }


    public static class BirthDatePickerDialogFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener{

        @NonNull
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
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            BirthDateFragment targetFragment = (BirthDateFragment)getTargetFragment();
            if (year < 0){
                targetFragment.mBirthday = null;
            } else {
                targetFragment.mBirthday = Calendar.getInstance();
                targetFragment.mBirthday.set(year, monthOfYear, dayOfMonth);
            }

        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
