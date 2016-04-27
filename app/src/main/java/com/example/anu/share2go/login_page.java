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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class login_page extends AppCompatActivity {

    com.example.anu.share2go.JSONParser jsonParser=new com.example.anu.share2go.JSONParser();
    EditText email=null;
    EditText pass=null;
    private static String url_create_product = "http://172.16.93.38:8084/WebApplication2/login.jsp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        email=(EditText)findViewById(R.id.email_id1);
        pass=(EditText)findViewById(R.id.password1);
        Button login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateEmailid(email.getText().toString()))
                {
                    email.setError("invalid email-id");
                    email.requestFocus();
                }
                if(!validatePass(pass.getText().toString()))
                {
                    pass.setError("password should be aleast 8 character long");
                    pass.requestFocus();
                }
                if(validateEmailid(email.getText().toString()) && validatePass(pass.getText().toString()) )
                {
                    new CreateNewProduct().execute();
                   // Intent intent = new Intent(login_page.this,select_Ride.class);
                    //startActivity(intent);
                }
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
            String email_id = email.getText().toString();
            String password1 = pass.getText().toString();


            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", email_id));
            params.add(new BasicNameValuePair("pwd", password1));

            JSONObject json = jsonParser.makeHttpRequest(url_create_product, "GET", params);
            Log.d("content",json.toString());
            String s=null;

            try {
                s= json.getString("result");
                Log.d("Msg", json.getString("result"));
                if(s.equals("success")){
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("id",json.getString("user_id"));
                    editor.commit();
                    Intent login = new Intent(login_page.this,select_Ride.class);
                    startActivity(login);
                    finish();
                }
                else{

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }
    }


    private boolean validatePass(String pass) {
        if (pass != null && pass.length() > 7) {
            return true;
        }
        return false;
    }
    private boolean validateEmailid(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
