package com.example.teja.threads;

import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView listView;
    private WebView w;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView =(ListView) findViewById(R.id.listView);


        DownloadData downloadData =new DownloadData();
        downloadData.execute("http://talksport.com/rss/football/chelsea/feed","http://www.football.co.uk/teams/chelsea/rss.xml");
        Log.d(TAG, "onCreate: sd");

    }

    private  class DownloadData extends AsyncTask<String,Void,String>{
        private static final String TAG = "DownloadData";
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            findViewById(R.id.listView).setVisibility(View.VISIBLE);
        findViewById(R.id.imageView3).setVisibility(View.INVISIBLE);
           // findViewById(R.id.imageView2).setVisibility(View.INVISIBLE);
            String [] de=s.split("lalallalalalalalaalalalalullalalalalalalalalalalalalalalalalalalalalala");

          /*  ParseXml parseXml =new ParseXml();
            parseXml.parse(s);

            ArrayAdapter<FeedEntry> arrayAdapter=new ArrayAdapter<FeedEntry>(MainActivity.this,R.layout.list_item,parseXml.getApps());
 //      listView.setAdapter(arrayAdapter);*/
         ParseChelsea parseChelsea=new ParseChelsea();
            ParseChelsea parseChelsea1=new ParseChelsea();
            parseChelsea.parse(de[0]);
            parseChelsea1.parse(de[1]);
            ArrayList<ChelseaFeed> p1 = parseChelsea.getApps();
            ArrayList<ChelseaFeed> p2=parseChelsea1.getApps();
            Log.d(TAG,"p1 length"+p1.size());
            Log.d(TAG,"p1 length"+p2.size());
            p1.addAll(p2);
            Log.d(TAG,"p1 and p2 length"+p1.size());
            int i;
            Log.d(TAG, "onPostExecute:wooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                    "oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooocxgb ");
            for(i=0;i<p1.size();i++)
            {
                Log.d(TAG,p1.get(i).toString());
                Log.d(TAG,"sessweseseseessesesesessrsresresrsedfrddtftfdgfdtfctrfgftfycgcfft");
            }

            Collections.sort(p1, new DateComparator());
            Collections.reverse(p1);

           // ArrayAdapter<ChelseaFeed> arrayAdapter=new ArrayAdapter<ChelseaFeed>(MainActivity.this,R.layout.list_item,parseChelsea.getApps());
         //   listView.setAdapter(arrayAdapter);
          FeedAdapter feedAdapter=new FeedAdapter(MainActivity.this,R.layout.list_record,p1);
            listView.setAdapter(feedAdapter);
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground:" +strings[0]);
            String rssFeed =downloadXML(strings[0]);
            String rssFeed2=downloadXML(strings[1]);
            if(rssFeed==null)
            {
                Log.e(TAG,"rssfeed url is none");
            }
            String a;
            a=rssFeed+"lalallalalalalalaalalalalullalalalalalalalalalalalalalalalalalalalalala";
            String c;
            c=a+rssFeed2;
            return c;

        }
        private String downloadXML(String urlPath){
            StringBuilder xmlResult=new StringBuilder();
            try{
                URL url=new URL(urlPath);
                HttpURLConnection connection =(HttpURLConnection) url.openConnection();
                int response=connection.getResponseCode();
               Log.d(TAG,"url response" +response);
                InputStream st=connection.getInputStream();
                InputStreamReader inp=new InputStreamReader(st);
                BufferedReader bf=new BufferedReader(inp);
                int charcount;
                char[] inputBuffer=new char[500];
                while (true){
                    charcount=bf.read(inputBuffer);
                    if(charcount<0)
                    {
                        break;
                    }
                    if(charcount>0)
                    {
                        xmlResult.append(String.copyValueOf(inputBuffer,0,charcount));
                    }
                }
                bf.close();
                Log.d(TAG,xmlResult.toString());
                return xmlResult.toString();
            }


            catch (MalformedURLException e){
                Log.e(TAG,"exception in url forming"+ e.getMessage());
            }
            catch (IOException e)
            {
                Log.e(TAG,"IO exception occured at url call"+e.getMessage());
            }
            catch (SecurityException e)
            {
                Log.e(TAG,"security exception occured:" +e.getMessage());
            }
            return null;
        }

    }
    class DateComparator implements Comparator<ChelseaFeed> {
        public int compare(ChelseaFeed o1, ChelseaFeed o2) {
            return o1.getDate().compareTo(o2.getDate());
        }
    }

}
