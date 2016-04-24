package com.example.anu.share2go;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Priya on 3/2/2016.
 */
public class CarPooling extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_pooling);
        View backgroundimage = findViewById(R.id.back);
        Drawable background = backgroundimage.getBackground();
        background.setAlpha(80);
    }
    public void offer_ride(View v)
    {
        Toast.makeText(getApplicationContext(), "Offer Ride Clicked", Toast.LENGTH_LONG).show();
        Intent goToSecond = new Intent();
        goToSecond.setClass(CarPooling.this, car_detail.class);
        // pass the rating value to the second activity
        // start the second activity
        startActivity(goToSecond);
    }
    public void Take_A_Ride(View v)
    {
        Toast.makeText(getApplicationContext(), "Take A Ride Clicked", Toast.LENGTH_LONG).show();
        Intent goToSecond = new Intent();
        goToSecond.setClass(CarPooling.this, Take_A_Ride.class);
        // pass the rating value to the second activity
        // start the second activity
        startActivity(goToSecond);
    }
}