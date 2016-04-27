package com.example.anu.share2go;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register_user extends AppCompatActivity {
    com.example.anu.share2go.JSONParser jsonParser=new com.example.anu.share2go.JSONParser();
    private static String url_create_product = "http://172.16.93.38:8084/WebApplication2/register.jsp";
    EditText fullname=null;
    EditText email=null;
    EditText phone=null;
    EditText pass=null;
    EditText cpass=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);

        fullname= (EditText)findViewById(R.id.full_name);
        email=(EditText)findViewById(R.id.email_id);
        phone=(EditText)findViewById(R.id.phone_number);
        pass=(EditText)findViewById(R.id.password);
        cpass=(EditText)findViewById(R.id.conf_password);
        Button register = (Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateFullname(fullname.getText().toString()))
                {
                  if(fullname.getText().toString()=="") {
                      fullname.setError("field can not be left blank");
                      fullname.requestFocus();
                  }

                      fullname.setError("full name should contain only alphabet");
                      fullname.requestFocus();

                }
                if(!validateEmailid(email.getText().toString()))
                {
                    email.setError("invalid email-id");
                    email.requestFocus();
                }
                if(!validatePhoneno(phone.getText().toString()))
                {
                    phone.setError("invalid phone number");
                    phone.requestFocus();
                }
                if(!validatePass(pass.getText().toString()))
                {
                    pass.setError("password should be aleast 8 character long");
                    pass.requestFocus();
                }
                if(!validateCpass(cpass.getText().toString(), pass.getText().toString()))
                {
                    cpass.setError("password mismatch");
                    cpass.requestFocus();
                }

                if(validateFullname(fullname.getText().toString()) && validateEmailid(email.getText().toString()) && validatePhoneno(phone.getText().toString()) && validatePass(pass.getText().toString()) && validateCpass(cpass.getText().toString(), pass.getText().toString()))
                {
                    new CreateNewProduct().execute();
                    Toast.makeText(Register_user.this,"Register Successfully",Toast.LENGTH_LONG).show();
                    fullname.setText("");
                    email.setText("");
                    phone.setText("");
                    pass.setText("");
                    cpass.setText("");

                    //Intent intent = new Intent(Register_user.this, login_page.class);
                    //startActivity(intent);
                }

                 }
        });
        TextView textView= (TextView)findViewById(R.id.back_login);
        textView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Register_user.this, login_page.class);
                startActivity(intent);
            }
        });


    }
    class CreateNewProduct extends AsyncTask<String, String, String> {

        protected String doInBackground(String... args) {
            String email_id = email.getText().toString();
            String password1 = pass.getText().toString();
            String full_name = fullname.getText().toString();
            String contact_num = phone.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", email_id));
            params.add(new BasicNameValuePair("pwd", password1));
            params.add(new BasicNameValuePair("fname", full_name));
            params.add(new BasicNameValuePair("contact", contact_num));

            JSONObject json = jsonParser.makeHttpRequest(url_create_product, "GET", params);

            String s=null;

            try {
                s= json.getString("result");
                Log.d("Msg", json.getString("result"));
                if(s.equals("success")){
                    Intent login = new Intent(getApplicationContext(), login_page.class);
                    startActivity(login);
                    finish();
                }
                else{
                    Toast.makeText(Register_user.this,"Register Unsuccessful",Toast.LENGTH_LONG).show();
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

    private boolean validateCpass(String cpass, String pass) {
        if(cpass.equals(pass))
        {
            return true;
        }
        else {
            return false;
        }
    }


    private boolean validatePhoneno(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
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

    private boolean validateFullname(String fullname) {
        String name_pattern="^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(name_pattern);
        Matcher matcher = pattern.matcher(fullname);
        return matcher.matches();

    }



    }

