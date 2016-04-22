package com.example.anu.share2go;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Priya on 3/7/2016.
 */
public class c2c_take extends AppCompatActivity {
    private static TextView fromDateEtxt;
    private static TextView fromTimeEtxt;
    private static DatePickerDialog fromDatePickerDialog;
    static SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
    static Date currentDate;
    static Date selectedDate;
    static int syear;
    static int smonth;
    static int sday;
    static int shour;
    static int smin;
    static Calendar newCalendar;
    static Calendar newDate;
    private SimpleDateFormat dateFormatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c2c_take);
        View backgroundimage = findViewById(R.id.back);
        Drawable background = backgroundimage.getBackground();
        background.setAlpha(80);

        setCurrentDate();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();

        fromDateEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        fromTimeEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonTimePickerDialog(v);
            }
        });

    }

    private void findViewsById() {
        fromDateEtxt = (TextView) findViewById(R.id.etxt_fromdate);

        fromTimeEtxt = (TextView) findViewById(R.id.etxt_todate);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            fromDatePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return fromDatePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            syear = year;
            smonth = month;
            sday = day;
            fromDateEtxt.setText(day + "/" + (month + 1) + "/" + year);
            setselectedDate();
            System.out.println("priya syear=" + syear + "smonth=" + smonth + "sdate=" + sday);
            System.out.println("priya selected date2=" + selectedDate);
            if (selectedDate.compareTo(currentDate) < 0) {
                shour = currentDate.getHours();
                smin = currentDate.getMinutes();
                int hour;
                String am_pm;

                setselectedDate();
                if (shour > 12) {
                    hour = shour - 12;
                    am_pm = "PM";
                } else {
                    hour = shour;
                    am_pm = "AM";
                }

                //fromTimeEtxt.setText(hour + ":" + smin+am_pm);
                fromTimeEtxt.setText(dateFormat.format(currentDate));
            }


        }
    }

    public void showTruitonTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();

            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePick erDialog and return it

            System.out.println(currentDate);

            return new TimePickerDialog(getActivity(), this, hour, minute,
                    false);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            int hour;
            shour = hourOfDay;
            smin = minute;
            String am_pm;
            if (hourOfDay > 12) {
                hour = hourOfDay - 12;
                am_pm = "PM";
            } else {
                hour = hourOfDay;
                am_pm = "AM";
            }


            setselectedDate();
            System.out.println("priya syear=" + syear + "smonth=" + smonth + "sdate=" + sday);
            System.out.println("priya selected date2=" + selectedDate);
            if (selectedDate.compareTo(currentDate) < 0) {
                shour = currentDate.getHours();
                smin = currentDate.getMinutes();
                setselectedDate();
                if (shour > 12) {
                    hour = shour - 12;
                    am_pm = "PM";
                } else {
                    hour = shour;
                    am_pm = "AM";
                }

                //fromTimeEtxt.setText(hour + ":" + smin+am_pm);
                fromTimeEtxt.setText(dateFormat.format(currentDate));
            } else
                // fromTimeEtxt.setText(hour + ":" + minute+am_pm );
                fromTimeEtxt.setText(dateFormat.format(selectedDate));
        }


    }

    private void setCurrentDate() {
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        syear = year;
        smonth = month;
        sday = day;
        shour = hour;
        smin = minute;
        System.out.println("current=" + year + " " + month + " " + day);
        currentDate = new Date(year, month, day, hour, minute);
        selectedDate = new Date(year, month, day, hour, minute);
        ;

    }

    private static void setselectedDate() {
        selectedDate = new Date(syear, smonth, sday, shour, smin);
    }

    public void need_ride(View v) {

        Intent goToSecond = new Intent();
        goToSecond.setClass(c2c_take.this, car_detail.class);
        // pass the rating value to the second activity
        // start the second activity
        startActivity(goToSecond);


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}


