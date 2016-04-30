package com.example.anu.share2go;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LENOVO on 30/04/2016.
 */
public class show_offer extends AppCompatActivity{

    String userid;
    com.example.anu.share2go.JSONParser jsonParser=new com.example.anu.share2go.JSONParser();
    private ProgressDialog progressDialog;
    List<String> date = new ArrayList<String>();
    List<String> time = new ArrayList<String>();
    List<String> cost = new ArrayList<String>();
    List<String> root = new ArrayList<String>();
    List<String> seats = new ArrayList<String>();
    List<String> destination = new ArrayList<String>();
    private ListView listView;
    private static String url_create_product = "http://172.16.92.8:9090/WebApplication2/show_offer.jsp";
    private static JSONArray ja=null;
    private static JSONObject jobj=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_rides);



        class CreateNewProduct extends AsyncTask<String, String, String> {

            /**
             * Before starting background thread Show Progress Dialog
             * */
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(show_offer.this);
                progressDialog.setCancelable(true);
                progressDialog.setMessage("Loading...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setProgress(0);
                progressDialog.show();
            }


            /**
             * Creating product
             * */

            protected String doInBackground(String... args) {

            SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
            userid = prefs.getString("id","0");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("session", userid));
            jobj=jsonParser.makeHttpRequest(url_create_product,"GET",params);

            try {


                ja=jobj.getJSONArray("1");
    //            Log.d("jarray",ja.toString());
    //            Log.d("jarray[1]", String.valueOf(ja.length()));
                CustomList customList = null;
                for(int i=0;i<ja.length();i++) {
                    date.add(ja.getJSONObject(i).get("date").toString());
                    time.add(ja.getJSONObject(i).get("time").toString());
                    cost.add(ja.getJSONObject(i).get("cost").toString());
                    root.add(ja.getJSONObject(i).get("source").toString());
                    seats.add(ja.getJSONObject(i).get("seats").toString());
                    destination.add(ja.getJSONObject(i).get("destination").toString());
    //                Log.d("date",ja.getJSONObject(i).get("date").toString());
    //                Log.d("time",ja.getJSONObject(i).get("time").toString());
    //                Log.d("cost",ja.getJSONObject(i).get("cost").toString());
    //                Log.d("source",ja.getJSONObject(i).get("source").toString());
    //                Log.d("seats",ja.getJSONObject(i).get("seats").toString());


                }
                customList = new CustomList(this, date,time,cost,root,seats,destination);
                listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(customList);

            } catch (JSONException e) {
                e.printStackTrace();
            }






    }


}
