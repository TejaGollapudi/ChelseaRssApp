package com.example.teja.threads;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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


        final ChelseaFeed currentnews=apps.get(position);

       viewHolder.name.setText(currentnews.getTitle());
      //  viewHolder.link.setText(currentnews.getLink());
        viewHolder.des.setText(currentnews.getDescription());
        viewHolder.date.setText(currentnews.getDate().toString());
        //Loading Image from URL
        Picasso.with(getContext())
                .load(currentnews.getImgurl()).placeholder(R.drawable.placeholder)
                .error(R.drawable.index)
                .resize(450,320)
                .into(viewHolder.img);
/*try {
    viewHolder.img.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String url=currentnews.getLink();
            if (!url.startsWith("https://") && !url.startsWith("http://")){
                Log.d("ds","misformed url");
                url=url.substring(2);
            }
            intent.setData(Uri.parse(url));
            Log.d("dfdsdfs",url);
            getContext().startActivity(intent);
        }

    });
}
        catch (ActivityNotFoundException e)
        {
            Log.d("sd","activity not found");
        }
        */
        return convertView;

    }


    private class ViewHolder{
        final TextView name;
        final TextView link;
        final TextView des;
        final TextView date;
        final ImageView img;
        ViewHolder (View v){
            this.name=(TextView) v.findViewById(R.id.idName);
            this.link=(TextView) v.findViewById(R.id.idLink);
            this.des=(TextView)  v.findViewById(R.id.idTextDescrpition);
            this.date=(TextView) v.findViewById(R.id.date);
            this.img=(ImageView) v.findViewById(R.id.imageView2);


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
