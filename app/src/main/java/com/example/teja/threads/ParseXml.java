package com.example.teja.threads;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by teja on 21-03-2017.
 */

public class ParseXml {
    private static final String TAG = "ParseXml";
    private ArrayList<FeedEntry> apps;

    public ParseXml() {

        this.apps = new ArrayList<>();
    }

    public ArrayList<FeedEntry> getApps() {
        return apps;
    }
    public boolean parse(String xmlData){
        boolean status=true;
        boolean inEntry=false;
        String textValue="";
        FeedEntry currentRecord=null;
        Log.d(TAG, "parse:xmldara "+ xmlData);
        try{
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp=factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));
            int eventType=xpp.getEventType();
            while(eventType!=XmlPullParser.END_DOCUMENT){
                String tagname=xpp.getName();
                Log.d(TAG,"outside switch "+tagname+" eventtype" +eventType +XmlPullParser.TEXT);
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        Log.d(TAG,tagname);
                        if("entry".equalsIgnoreCase(tagname)){
                            inEntry=true;

                            currentRecord=new FeedEntry();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue=xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        Log.d(TAG,"parse:ENding tag for:"+tagname);
                        if(inEntry) {
                            if ("entry".equalsIgnoreCase(tagname)) {
                                apps.add(currentRecord);
                                inEntry = false;
                            } else if ("name".equalsIgnoreCase(tagname)) {
                                currentRecord.setName(textValue);
                            } else if ("artist".equalsIgnoreCase(tagname)) {
                                currentRecord.setArtist(textValue);
                            } else if ("releaseDate".equalsIgnoreCase(tagname)) {
                                currentRecord.setReleaseDate(textValue);
                            } else if ("summary".equalsIgnoreCase(tagname)) {
                                currentRecord.setSummary(textValue);
                            } else if ("image".equalsIgnoreCase(tagname)) {
                                currentRecord.setImageUrl(textValue);
                            }
                        }
                            break;
                    default:

                }
                eventType=xpp.next();
            }
            for(FeedEntry app:apps){
                Log.d(TAG,"---------------------");
                Log.d(TAG,app.toString());
            }

        }catch(Exception e){
            status=false;
            e.printStackTrace();
        }
        return status;
    }
}
