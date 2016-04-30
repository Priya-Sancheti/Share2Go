package com.example.anu.share2go;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomList extends ArrayAdapter<String> {
    List<String> date = new ArrayList<String>();
    List<String> time = new ArrayList<String>();
    List<String> cost = new ArrayList<String>();
    List<String> root = new ArrayList<String>();
    List<String> seats = new ArrayList<String>();
    List<String> destination = new ArrayList<String>();
    private Activity context;

    public CustomList(Activity context, List<String> date, List<String> time,List<String> cost,List<String> root,List<String> seats,List<String> dest ) {
        super(context, R.layout.listview_item_model,date);
        this.context = context;
        this.date = date;
        this.time = time;
        this.cost = cost;
        this.root = root;
        this.seats= seats;
        this.destination=dest;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.listview_item_model, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewDate);
        TextView textViewDesc = (TextView) listViewItem.findViewById(R.id.textViewTime);
        TextView textViewCost = (TextView) listViewItem.findViewById(R.id.textViewCost);
        TextView textViewRoot = (TextView) listViewItem.findViewById(R.id.textViewRoot);
        TextView textViewDest = (TextView) listViewItem.findViewById(R.id.textViewDest);
        TextView textViewSeats = (TextView) listViewItem.findViewById(R.id.textViewSeats);
        //ImageView image = (ImageView) listViewItem.findViewById(R.id.textViewRoot);

        textViewName.setText(date.get(position));
        textViewDesc.setText(time.get(position));
        textViewCost.setText(cost.get(position)+"/person");
        textViewRoot.setText("FROM : "+root.get(position));
        textViewDest.setText("TO       : "+destination.get(position));
        textViewSeats.setText("seats available: "+seats.get(position));
        //image.setImageResource(imageid[position]);
        return  listViewItem;
    }
}