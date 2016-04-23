package com.example.anu.share2go;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class select_Ride extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__ride);
        View backgroundimage = findViewById(R.id.back);
        Drawable background = backgroundimage.getBackground();
        background.setAlpha(80);
    }
    public void cabShare(View v)
    {

        Toast.makeText(getApplicationContext(), "Cab Share Clicked", Toast.LENGTH_SHORT).show();
        Intent goToSecond = new Intent();
        goToSecond.setClass(select_Ride.this, CarPooling.class);
        // pass the rating value to the second activity
        // start the second activity
        startActivity(goToSecond);

    }
    public void c2c(View v)
    {

        Toast.makeText(getApplicationContext(), "Cab Share Clicked", Toast.LENGTH_SHORT).show();
        Intent goToSecond = new Intent();
        goToSecond.setClass(select_Ride.this, City2City.class);
        // pass the rating value to the second activity
        // start the second activity
        startActivity(goToSecond);

    }

}
