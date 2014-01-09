package com.crayonio.podcastfeedparser.itunes;

import android.util.Log;

import com.crayonio.podcastfeedparser.rss.Channel;

import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

/**
 * Created by chinmay on 9/1/14.
 */
public class iTunesParserHandler extends DefaultHandler2 {

    private static final String TAG = "iTunesParserHandler";
    private Channel parsedPodcast;
    private boolean doneParsing = false;

    @Override
    public void startDocument() throws SAXException {
        parsedPodcast = new Channel();
        doneParsing = false;
        super.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        doneParsing = true;
        super.endDocument();
    }

    @Override
    public void startEntity(String name) throws SAXException {
        super.startEntity(name);
    }

    @Override
    public void endEntity(String name) throws SAXException {
        super.endEntity(name);
    }

    @Override
    public void comment(char[] ch, int start, int length) throws SAXException {
        super.comment(ch, start, length);
    }


    public Channel getParsedPodcast() {
        if (!doneParsing)
            Log.w(TAG,"Parsing not done");

        return parsedPodcast;
    }
}
