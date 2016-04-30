package com.example.anu.share2go;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class List_Of_Rides extends AppCompatActivity {
    String jsonString;
    private ListView listView;
    private static  JSONObject json = null;
    private static JSONArray ja=null;
    List<String> date = new ArrayList<String>();
    List<String> time = new ArrayList<String>();
    List<String> cost = new ArrayList<String>();
    List<String> root = new ArrayList<String>();
    List<String> seats = new ArrayList<String>();
    List<String> destination = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_rides);

        String data=getIntent().getStringExtra("sample");

        try {

            json = new JSONObject(data);
            ja=json.getJSONArray("1");
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



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "You Clicked " + date.get(i), Toast.LENGTH_SHORT).show();
                Intent d = new Intent(List_Of_Rides.this,full_details.class);
                try {
                    d.putExtra("list_details",ja.getJSONObject(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(d);


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }
            */
        return super.onOptionsItemSelected(item);
    }
}