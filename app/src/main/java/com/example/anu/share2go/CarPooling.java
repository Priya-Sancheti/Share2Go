package com.example.anu.share2go;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CarPooling extends AppCompatActivity {
    String userid = null;
    JSONParser jsonParser = new JSONParser();
    private static String url_create_product = "http://172.16.93.38:8084/WebApplication2/offer_ride_car_check.jsp";

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_pooling);
        View backgroundimage = findViewById(R.id.back);
        Drawable background = backgroundimage.getBackground();
        background.setAlpha(80);
    }

    public void offer_ride(View v) {
        Toast.makeText(getApplicationContext(), "Offer Ride Clicked", Toast.LENGTH_LONG).show();
        new CreateNewProduct().execute();

    }

    class CreateNewProduct extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {
            SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
            userid = prefs.getString("id", "0");

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userid));

            JSONObject json = jsonParser.makeHttpRequest(url_create_product, "GET", params);
            String s = null;

            try {
                s = json.getString("result");
                Log.d("Msg", json.getString("result"));
                if (s.equals("success")) {
                    Intent offer = new Intent(CarPooling.this, Offer_Ride.class);
                    finish();
                    startActivity(offer);
                } else {
                    Intent car = new Intent(CarPooling.this, car_detail.class);
                    startActivity(car);
                    finish();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
            return null;

        }

    }

    public void Take_A_Ride(View v) {
        Toast.makeText(getApplicationContext(), "Take A Ride Clicked", Toast.LENGTH_LONG).show();
        Intent goToSecond = new Intent();
        goToSecond.setClass(CarPooling.this, Take_A_Ride.class);
        // pass the rating value to the second activity
        // start the second activity
        startActivity(goToSecond);
    }
}
