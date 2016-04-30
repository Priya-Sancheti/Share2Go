package com.example.anu.share2go;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class change_pass extends AppCompatActivity {
    JSONParser jsonParser = new JSONParser();
    private static String url_create_product = "http://172.16.92.8:9090/WebApplication2/change_pass.jsp";
    String userid = null;


    private EditText old_pass,new_pass,conf_pass;
    private Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        old_pass = (EditText) findViewById(R.id.old_password);
        new_pass = (EditText) findViewById(R.id.new_password);
        conf_pass = (EditText) findViewById(R.id.cnfnew_password);

        update = (Button) findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateCpass(conf_pass.getText().toString(), new_pass.getText().toString())) {
                    conf_pass.setError("password mismatch");
                    conf_pass.requestFocus();
                }
                if(!validatePass(new_pass.getText().toString()))
                {
                    new_pass.setError("password should be aleast 8 character long");
                    new_pass.requestFocus();
                }
                if( validatePass(new_pass.getText().toString()) && validateCpass(conf_pass.getText().toString(), new_pass.getText().toString()))
                {
                    SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
                    userid = prefs.getString("id", "0");

                    new CreateNewProduct().execute();


                }

            }
        });
    }

    class CreateNewProduct extends AsyncTask<String, String, String> {

        protected String doInBackground(String... args) {
            String password1 = old_pass.getText().toString();
            String password2 = new_pass.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("new_pwd", password2));
            params.add(new BasicNameValuePair("old_pwd", password1));
            params.add(new BasicNameValuePair("session", userid));

            JSONObject json = jsonParser.makeHttpRequest(url_create_product, "GET", params);
            String s=null;

            try {
                s= json.getString("result");
                Log.d("Msg", json.getString("result"));
                if(s.equals("success")){
                    runOnUiThread(new Runnable() {

                        public void run() {

                            Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(change_pass.this, login_page.class);
                            startActivity(intent);
                            finish();

                        }
                    });



                }
                else{
                    runOnUiThread(new Runnable() {

                        public void run() {
                            old_pass.setError("Invalid Password");
                            old_pass.requestFocus();
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

    private boolean validateCpass(String cpass, String pass) {
        if(cpass.equals(pass))
        {
            return true;
        }
        else {
            return false;
        }
    }
    private boolean validatePass(String pass) {
        if (pass != null && pass.length() > 7) {
            return true;
        }
        return false;
    }


}
