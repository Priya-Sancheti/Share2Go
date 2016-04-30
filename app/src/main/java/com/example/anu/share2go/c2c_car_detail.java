package com.example.anu.share2go;

/**
 * Created by Priya on 4/20/2016.
 */
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class c2c_car_detail extends AppCompatActivity {
    com.example.anu.share2go.JSONParser jsonParser=new com.example.anu.share2go.JSONParser();
    private static String url_create_product = "http://172.16.92.8:9090/WebApplication2/c2c_car.jsp";
    EditText model;
    EditText color;
    EditText car_number;
    EditText licence;
    String userid=null;
    Spinner sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c2c_car_detail);
        View backgroundimage = findViewById(R.id.back);
        Drawable background = backgroundimage.getBackground();
        background.setAlpha(80);

        Button login = (Button) findViewById(R.id.login);
        model = (EditText) findViewById(R.id.text2);
        color = (EditText) findViewById(R.id.text10);
        car_number = (EditText) findViewById(R.id.text4);
        licence = (EditText) findViewById(R.id.text6);
        sp = (Spinner) findViewById(R.id.spinner1);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                Toast.makeText(getBaseContext(), sp.getSelectedItem().toString(),
                        Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("MyPref",MODE_PRIVATE);
                userid = prefs.getString("id","0");
                String model1=model.getText().toString();
                String color1=color.getText().toString();
                String cno=car_number.getText().toString();
                String licence1=licence.getText().toString();
                String seats=sp.getSelectedItem().toString();
                if(validateNumberPlate(cno) && validateLicence(licence1) && !model1.equals("") && !seats.equals("") && !color1.equals("")) {
                    new CreateNewProduct().execute();
                }
                else {

                    Toast.makeText(getApplicationContext(), "Invalid details", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    class CreateNewProduct extends AsyncTask<String, String, String> {



        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("model", model.getText().toString()));
            params.add(new BasicNameValuePair("color", color.getText().toString()));
            params.add(new BasicNameValuePair("car_number", car_number.getText().toString()));
            params.add(new BasicNameValuePair("licence", licence.getText().toString()));
            params.add(new BasicNameValuePair("seats",sp.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("session", userid));


            JSONObject json = jsonParser.makeHttpRequest(url_create_product, "GET", params);

            String s=null;
            String msg="";

            try {
                s= json.getString("result");
                Log.d("Msg", json.getString("result"));
                if(s.equals("success")){
                    Intent login = new Intent(c2c_car_detail.this,c2c_offer.class);
                    startActivity(login);
                    msg="car registration successful";

                    finish();
                }
                else{
                    msg="car registration unsuccessful";
                      }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return msg;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateNumberPlate(String number) {
        String NUMBER_PATTERN = "^[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)? [0-9]{4}$";

        Pattern pattern = Pattern.compile(NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    private boolean validateLicence(String number) {
        String NUMBER_PATTERN = "^[A-Z]\\d{2}-\\d{2}-\\d{6}$";

        Pattern pattern = Pattern.compile(NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
