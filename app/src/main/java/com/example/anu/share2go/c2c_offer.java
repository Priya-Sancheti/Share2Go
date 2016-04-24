package com.example.anu.share2go;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Priya on 3/7/2016.
 */
public class c2c_offer extends AppCompatActivity {
    com.example.anu.share2go.JSONParser jsonParser=new com.example.anu.share2go.JSONParser();
    private static String url_create_product = "http://172.16.92.8:9090/WebApplication2/c2c_offer.jsp";
    private static TextView fromDateEtxt;
    private static TextView fromTimeEtxt;
    String fromdate;
    String fromtime;
    String cost1;
    String comments;

    private static DatePickerDialog fromDatePickerDialog;
    static SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
    private static EditText cost;
    private static EditText comment;
    String from;
    String to;
    String via1;
    String via2;
    String userid=null;
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
    String TAG="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c2c_offer);
        View backgroundimage = findViewById(R.id.back);
        Drawable background = backgroundimage.getBackground();
        background.setAlpha(100);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                from = place.getAddress().toString();
                Log.i(TAG, "Place: " + place.getName());//get place details here
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.

                Log.i(TAG, "An error occurred: " + status);
            }
        });
        PlaceAutocompleteFragment autocompleteFragment1 = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment1);
        autocompleteFragment1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                via1 = place.getAddress().toString();
                Log.i(TAG, "Place: " + place.getName());//get place details here
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
        PlaceAutocompleteFragment autocompleteFragment2 = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment2);
        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                via2 = place.getAddress().toString();
                Log.i(TAG, "Place: " + place.getName());//get place details here
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
        PlaceAutocompleteFragment autocompleteFragment3 = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment3);
        autocompleteFragment3.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                to = place.getAddress().toString();
                Log.i(TAG, "Place: " + place.getName());//get place details here
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });



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

        cost=(EditText)findViewById(R.id.editText5);
        comment=(EditText)findViewById(R.id.editText12);
        Button submit = (Button)findViewById(R.id.button6);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
                userid = prefs.getString("id", "0");
                fromdate = fromDateEtxt.getText().toString();
                fromtime = fromTimeEtxt.getText().toString();
                cost1 = cost.getText().toString();
                comments = comment.getText().toString();
                if (!fromdate.equals("") && !fromtime.equals("") && !from.equals("") && !to.equals("") && cost.equals("")) {
                    Log.d("date", fromDateEtxt.getText().toString());
                    Log.d("time", fromTimeEtxt.getText().toString());
                    Log.d("cost", cost.getText().toString());
                    Log.d("comment", comment.getText().toString());
                    Log.d("session", userid);
                    new CreateNewProduct().execute();
                } else {
                    Toast.makeText(getApplicationContext(), "fill all the details", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    class CreateNewProduct extends AsyncTask<String, String, String> {


        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("from", from));
            params.add(new BasicNameValuePair("via1", via1));
            params.add(new BasicNameValuePair("via2", via2));
            params.add(new BasicNameValuePair("to", to));
            params.add(new BasicNameValuePair("date",fromdate));
            params.add(new BasicNameValuePair("time", fromtime));
            params.add(new BasicNameValuePair("cost", cost1));
            params.add(new BasicNameValuePair("comment", comments));
            params.add(new BasicNameValuePair("session",userid));

            JSONObject json = jsonParser.makeHttpRequest(url_create_product, "GET", params);

            String s=null;

            try {
                s= json.getString("result");
                Log.d("Msg", json.getString("result"));
                if(s.equals("success")){
                    Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                    Intent login = new Intent(c2c_offer.this,CarPooling.class);
                    startActivity(login);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }
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
            fromDatePickerDialog=new DatePickerDialog(getActivity(), this, year, month, day);
            fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return fromDatePickerDialog;        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            syear=year;
            smonth=month;
            sday=day;
            fromDateEtxt.setText(day+"/" + (month+1)+"/"+year);
            setselectedDate();
            System.out.println("priya syear=" + syear + "smonth=" + smonth + "sdate=" + sday);
            System.out.println("priya selected date2="+selectedDate);
            if (selectedDate.compareTo(currentDate) < 0) {
                shour=currentDate.getHours();
                smin=currentDate.getMinutes();
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
            System.out.println("priya selected date2="+selectedDate);
            if (selectedDate.compareTo(currentDate) < 0) {
                shour=currentDate.getHours();
                smin=currentDate.getMinutes();
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
        syear=year;
        smonth=month;
        sday=day;
        shour=hour;
        smin=minute;
        System.out.println("current="+year+" "+month+" "+day);
        currentDate = new Date(year, month, day, hour, minute);
        selectedDate = new Date(year, month, day, hour, minute);;

    }

    private static void setselectedDate() {
        selectedDate = new Date(syear, smonth, sday, shour, smin);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}