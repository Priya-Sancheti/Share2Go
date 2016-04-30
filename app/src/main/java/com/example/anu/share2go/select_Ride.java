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
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_pass) {
            Intent intent1 = new Intent(select_Ride.this,change_pass.class);
            startActivity(intent1);

        }
        if (id == R.id.action_profile) {
            Intent intent1 = new Intent(select_Ride.this,profile.class);
            startActivity(intent1);

        }
        if (id == R.id.action_logout) {

            SharedPreferences sharedpreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
            Intent i=new Intent(select_Ride.this,MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();

        }
        if (id == R.id.action_offer) {
            Intent intent1 = new Intent(select_Ride.this, show_offer.class);
            startActivity(intent1);
        }
        if (id == R.id.action_take) {
            Intent intent1 = new Intent(select_Ride.this, show_take.class);
            startActivity(intent1);
        }

        return super.onOptionsItemSelected(item);
    }
}
