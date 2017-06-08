package com.example.teja.threads;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

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

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "MainActivity";
    private ListView listView;
    private WebView w;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView =(ListView) findViewById(R.id.listView);
        progressBar=(ProgressBar) findViewById(R.id.progressBar2);
        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        progressBar.setProgress(0);

        DownloadData downloadData =new DownloadData();
        downloadData.execute("http://talksport.com/rss/football/chelsea/feed","http://www.football.co.uk/teams/chelsea/rss.xml","http://www.dailymail.co.uk/sport/teampages/chelsea.rss");
        Log.d(TAG, "onCreate: sd");


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                    Log.d(TAG,"refresh called ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
                    DownloadData downloadData =new DownloadData();
                    downloadData.execute("http://talksport.com/rss/football/chelsea/feed","http://www.football.co.uk/teams/chelsea/rss.xml","http://www.dailymail.co.uk/sport/teampages/chelsea.rss");
                    Log.d(TAG, "onRefresh: sd");


//code for updating screen

//following line is important to stop animation for refreshing
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public void onRefresh() {
        Log.d(TAG,"refresh called ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
        DownloadData downloadData =new DownloadData();
        downloadData.execute("http://talksport.com/rss/football/chelsea/feed","http://www.football.co.uk/teams/chelsea/rss.xml","http://www.dailymail.co.uk/sport/teampages/chelsea.rss");
        Log.d(TAG, "onRefresh: sd");


//code for updating screen

//following line is important to stop animation for refreshing
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public AssetManager getAssets() {
        return getResources().getAssets();
    }


    private  class DownloadData extends AsyncTask<String,Void,String>{
        private static final String TAG = "DownloadData";
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            findViewById(R.id.listView).setVisibility(View.VISIBLE);
       // findViewById(R.id.imageView3).setVisibility(View.INVISIBLE);
           // findViewById(R.id.imageView2).setVisibility(View.INVISIBLE);

            String [] de=s.split("lalallalalalalalaalalalalullalalalalalalalalalalalalalalalalalalalalala");

          /*  ParseXml parseXml =new ParseXml();
            parseXml.parse(s);

            ArrayAdapter<FeedEntry> arrayAdapter=new ArrayAdapter<FeedEntry>(MainActivity.this,R.layout.list_item,parseXml.getApps());
 //      listView.setAdapter(arrayAdapter);*/
         ParseChelsea parseChelsea=new ParseChelsea();
            ParseChelsea parseChelsea1=new ParseChelsea();
            ParseChelsea parseChelsea2=new ParseChelsea();
            parseChelsea.parse(de[0]);
            parseChelsea1.parse(de[1]);
            parseChelsea2.parse(de[2]);
Log.d(TAG,de[2]);
            final ArrayList<ChelseaFeed> p1 = parseChelsea.getApps();
            ArrayList<ChelseaFeed> p2=parseChelsea1.getApps();
            ArrayList<ChelseaFeed> p3=parseChelsea2.getApps();
           // Log.d(TAG,"p1 length"+p1.size());
           // Log.d(TAG,"p1 length"+p2.size());
            p1.addAll(p2);
            p1.addAll(p3);
            //Log.d(TAG,"p1 and p2 length"+p1.size());
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
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String url=p1.get(position).getLink();
                    if (!url.startsWith("https://") && !url.startsWith("http://")){
                        Log.d("ds","misformed url");
                        url=url.substring(2);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("url", url );
                    Fragment web = new Web();
                    web.setArguments(bundle);

                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.activity_main,web);
                    transaction.addToBackStack(null);
                    transaction.commit();



                }
            });

        }


        @Override
        protected String doInBackground(String... strings) {
          //  Log.d(TAG, "doInBackground:" +strings[0]);
            String rssFeed =downloadXML(strings[0]);
            String rssFeed2=downloadXML(strings[1]);
            String rssFeed3=downloadXML(strings[2]);
            if(rssFeed==null)
            {
                Log.e(TAG,"rssfeed url is none");
            }
            String a;
            a=rssFeed+"lalallalalalalalaalalalalullalalalalalalalalalalalalalalalalalalalalala";
            String c;
            c=a+rssFeed2+"lalallalalalalalaalalalalullalalalalalalalalalalalalalalalalalalalalala"+rssFeed3;
            return c;

        }


        protected void onProgressUpdate(Integer... values) {

            progressBar.setProgress(values[0]);
        }
        private String downloadXML(String urlPath){
            StringBuilder xmlResult=new StringBuilder();
            try{
                URL url=new URL(urlPath);
                HttpURLConnection connection =(HttpURLConnection) url.openConnection();
                int response=connection.getResponseCode();
            //   Log.d(TAG,"url response" +response);
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
              //  Log.d(TAG,xmlResult.toString());
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
