package com.crayonio.podcastfeedparser;

import com.crayonio.podcastfeedparser.rss.Channel;
import com.crayonio.podcastfeedparser.rss.RSSParserHandler;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by chinmay on 9/1/14.
 */
public class PodcastFeedParser {

    private XMLReader xmlReader;
    private RSSParserHandler parserHandler;

    public PodcastFeedParser (){
        try {
            xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Channel> parseFeed(URL url){

        try {
            InputStream inputStream = url.openStream();

            parserHandler = new RSSParserHandler();
            xmlReader.setContentHandler(parserHandler);

            xmlReader.parse(new InputSource(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return parserHandler.getParsedPodcast();
    }



}
