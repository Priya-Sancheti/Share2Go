package com.example.anu.share2go;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Priya on 3/2/2016.
 */
public class City2City extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city2city);
        View backgroundimage = findViewById(R.id.back);
        Drawable background = backgroundimage.getBackground();
        background.setAlpha(80);
    }
    public void offer_ride(View v)
    {
        Toast.makeText(getApplicationContext(), "Offer Ride Clicked", Toast.LENGTH_LONG).show();
        Intent goToSecond = new Intent();
        goToSecond.setClass(City2City.this, car_detail.class);
        // pass the rating value to the second activity
        // start the second activity
        startActivity(goToSecond);
    }
    public void Take_A_Ride(View v)
    {
        Toast.makeText(getApplicationContext(), "Take A Ride Clicked", Toast.LENGTH_LONG).show();
        Intent goToSecond = new Intent();
        goToSecond.setClass(City2City.this, c2c_take.class);
        // pass the rating value to the second activity
        // start the second activity
        startActivity(goToSecond);
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_pass) {
            Intent intent1 = new Intent(City2City.this,change_pass.class);
            startActivity(intent1);

        }
        if (id == R.id.action_profile) {
            Intent intent1 = new Intent(City2City.this,profile.class);
            startActivity(intent1);

        }
        if (id == R.id.action_logout) {
            SharedPreferences sharedpreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
            Intent i=new Intent(City2City.this,MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();

        }
        if (id == R.id.action_offer) {
            Intent intent1 = new Intent(City2City.this, show_offer.class);
            startActivity(intent1);
        }
        if (id == R.id.action_take) {
            Intent intent1 = new Intent(City2City.this, show_take.class);
            startActivity(intent1);
        }


        return super.onOptionsItemSelected(item);
    }
}