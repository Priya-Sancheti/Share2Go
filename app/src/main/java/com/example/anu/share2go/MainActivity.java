package com.example.anu.share2go;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Connect cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        View backgroundImage = findViewById(R.id.relative_out);
        Drawable background = backgroundImage.getBackground();
        background.setAlpha(200);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        cd = new Connect(getApplicationContext());
        Boolean info=cd.isConnectingToInternet();
        //final ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        //NetworkInfo info = manager.getActiveNetworkInfo();
        if (info) {
            System.out.println("Wifi Connected");
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

            // Setting Dialog Title
            alertDialog.setTitle("Network Connection ");

            // Setting Dialog Message
            alertDialog.setMessage("This app wants to enable wifi :");

            // Setting Icon to Dialog

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {

                    // Write your code here to invoke YES event
                   WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                    wifi.setWifiEnabled(true);
//                   startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    //startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
                    SystemClock.sleep(4000);
                    Boolean info1=cd.isConnectingToInternet();
                    if(info1)
                    Toast.makeText(getApplicationContext(), "You are connected to network", Toast.LENGTH_SHORT).show();
                    else
                    {

                        Toast.makeText(getApplicationContext(), "Connect to Wifi", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke NO event

                    Toast.makeText(getApplicationContext(), "Exit", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                    finish();

                }
            });

            // Showing Alert Message
            alertDialog.show();
        }
        Button btn = (Button)findViewById(R.id.Sign_up);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register_user.class);
                startActivity(intent);
            }
        });

        Button btn1 = (Button)findViewById(R.id.log_in);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, login_page.class);
                startActivity(intent1);
            }
        });
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
