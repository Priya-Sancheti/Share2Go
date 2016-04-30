package com.example.anu.share2go;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class full_details extends AppCompatActivity  {
    com.example.anu.share2go.JSONParser jsonParser=new com.example.anu.share2go.JSONParser();
    private static String url_create_product = "http://172.16.92.8:9090/WebApplication2/carpool_take.jsp";
    JSONObject json_data=null;
    private static TextView source;
    private static TextView destination;
    private static TextView seats_avail;
    private static TextView start_time;
    private static TextView name;
    private static TextView phn;
    private static TextView model;
    private static TextView car_number;
    private static TextView from;
    private static TextView via1;
    private static TextView via2;
    private static TextView to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_details);

        String data=getIntent().getStringExtra("list_details");
        try {
            json_data= new JSONObject(data);
            Log.d("data aaya", json_data.toString());
            source=(TextView)findViewById(R.id.source);
            destination=(TextView)findViewById(R.id.destination);
            seats_avail=(TextView)findViewById(R.id.seats_avail);
            start_time=(TextView)findViewById(R.id.start_time);
            name=(TextView)findViewById(R.id.name);
            phn=(TextView)findViewById(R.id.Phn);
            model=(TextView)findViewById(R.id.model);
            car_number=(TextView)findViewById(R.id.license_no);
            from=(TextView)findViewById(R.id.from);
            via1=(TextView)findViewById(R.id.via1);
            via2=(TextView)findViewById(R.id.via2);
            to=(TextView)findViewById(R.id.to);


            source.setText("SOURCE :  "+json_data.get("source").toString());
            destination.setText("DESTINATION :  "+json_data.get("destination").toString());
            seats_avail.setText("SEATS AVAILABLE :  "+json_data.get("seats").toString());
            start_time.setText("TIME :  "+json_data.get("time").toString());
            name.setText("Name :  "+json_data.get("full_name").toString());
            phn.setText("Contact :  "+json_data.get("phone").toString());
            model.setText("Car Model :  "+json_data.get("model").toString());
            car_number.setText("Car number :  "+json_data.get("car_number").toString());
            from.setText("From :  "+json_data.get("source").toString() );
            via1.setText("Via1 :  "+json_data.get("via1").toString());
            via2.setText("Via2 :  "+json_data.get("via2").toString());
            to.setText("To :  " + json_data.get("destination").toString());




        } catch (JSONException e) {
            e.printStackTrace();
        }

        Button submit = (Button)findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new CreateNewProduct().execute();

            }
        });

    }


    class CreateNewProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */


        /**
         * Creating product
         * */

        protected String doInBackground(String... args) {
            SharedPreferences take = getSharedPreferences("take_a_ride", MODE_PRIVATE);

            try {
                //Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("session", take.getString("session", null)));
                params.add(new BasicNameValuePair("source",take.getString("source", null)));
                params.add(new BasicNameValuePair("source_lat", take.getString("source_lat", null)));
                params.add(new BasicNameValuePair("source_lng", take.getString("source_lng", null)));
                params.add(new BasicNameValuePair("destination", take.getString("destination", null)));
                params.add(new BasicNameValuePair("destination_lat",take.getString("destination_lat", null)));
                params.add(new BasicNameValuePair("destination_lng", take.getString("destination_lng", null)));
                params.add(new BasicNameValuePair("date", take.getString("date", null)));
                params.add(new BasicNameValuePair("duration", take.getString("duration", null)));
                params.add(new BasicNameValuePair("offer_id",json_data.get("offer_id").toString()));
                params.add(new BasicNameValuePair("user_offering",json_data.get("user_id").toString()));


                JSONObject json = jsonParser.makeHttpRequest(url_create_product, "GET", params);

                String s = null;

                    s = json.getString("result");
                    Log.d("Msg", json.getString("result"));
                    if (s.equals("success")) {
                        runOnUiThread(new Runnable() {

                            public void run() {

                                Toast.makeText(getApplicationContext(), "Ride confirmed", Toast.LENGTH_SHORT).show();

                            }
                        });


                        Intent login = new Intent(full_details.this, select_Ride.class);
                        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(login);
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {

                            public void run() {

                                Toast.makeText(getApplicationContext(), "Error!!!", Toast.LENGTH_SHORT).show();

                            }
                        });


                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                return null;

        }
    }


}
