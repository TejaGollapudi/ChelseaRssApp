package com.example.teja.threads;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by teja on 23-03-2017.
 */

public class FeedAdapter extends ArrayAdapter {
    private static final String TAG = "FeedAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<ChelseaFeed> apps;

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){

        convertView=layoutInflater.inflate(layoutResource,parent,false);
       viewHolder =new ViewHolder(convertView);
        convertView.setTag(viewHolder) ;

    }
        else{
            viewHolder=(ViewHolder) convertView.getTag();
        }


        ChelseaFeed currentnews=apps.get(position);

       viewHolder.name.setText(currentnews.getTitle());
        viewHolder.link.setText(currentnews.getLink());
        viewHolder.des.setText(currentnews.getDescription());
        viewHolder.date.setText(currentnews.getDate().toString());
        return convertView;
    }


    private class ViewHolder{
        final TextView name;
        final TextView link;
        final TextView des;
        final TextView date;
        ViewHolder (View v){
            this.name=(TextView) v.findViewById(R.id.idName);
            this.link=(TextView) v.findViewById(R.id.idLink);
            this.des=(TextView)  v.findViewById(R.id.idTextDescrpition);
            this.date=(TextView) v.findViewById(R.id.date);

        }

    }



    @Override
    public int getCount() {
       return apps.size();
    }

    public FeedAdapter(Context context, int resource, List<ChelseaFeed> apps) {
        super(context, resource);
        this.layoutResource=resource;
        this.layoutInflater=LayoutInflater.from(context);
        this.apps = apps;

    }
}
