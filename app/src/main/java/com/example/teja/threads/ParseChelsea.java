package com.example.teja.threads;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by teja on 23-03-2017.
 */

public class ParseChelsea {
    private static final String TAG = "ParseChelsea";
    private ArrayList<ChelseaFeed> apps;
    public ParseChelsea() {

        this.apps = new ArrayList<>();
    }

    public ArrayList<ChelseaFeed> getApps() {
        return apps;
    }
    public boolean parse(String xmlData){
        boolean status=true;
        boolean inEntry=false;
        String textValue="";
       ChelseaFeed currentRecord=null;
        try{
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp=factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));
            int eventType=xpp.getEventType();
            while(eventType!=XmlPullParser.END_DOCUMENT){
                String tagname=xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if("item".equalsIgnoreCase(tagname)){
                            inEntry=true;

                            currentRecord=new ChelseaFeed();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue=xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(inEntry) {
                            if ("item".equalsIgnoreCase(tagname)) {
                                apps.add(currentRecord);
                                inEntry = false;
                            } else if ("title".equalsIgnoreCase(tagname)) {
                                currentRecord.setTitle(textValue);
                            } else if ("link".equalsIgnoreCase(tagname)) {
                                currentRecord.setLink(textValue);
                            } else if ("description".equalsIgnoreCase(tagname)) {
                                currentRecord.setDescription(textValue);

                            }
                            else if ("pubdate".equalsIgnoreCase(tagname)) {
                                currentRecord.setDate(textValue);
                            }
                            else if("enclosure".equalsIgnoreCase(tagname)){
                                currentRecord.setImgurl(xpp.getAttributeValue(null,"url"));
                            }
                        }
                        break;
                    default:

                }
                eventType=xpp.next();
            }
            for(ChelseaFeed app:apps){
                //Log.d(TAG,"---------------------");
                //Log.d(TAG,app.toString());
            }

        }catch(Exception e){
            status=false;
            e.printStackTrace();
        }
        return status;
    }
}
