package com.crayonio.podcastfeedparser.rss;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Based of the original RSS 2.0 spec from http://cyber.law.harvard.edu/rss/
 *
 * http://cyber.law.harvard.edu/rss/examples/rss2sample.xml is an example feed which will be parsed.
 *
 * Created by chinmay on 9/1/14.
 */
public class RSSParserHandler extends DefaultHandler2 {


    /*XML Tags*/

    /* High level Tags*/
    public static final String CHANNEL = "channel";
    public static final String ITEM = "item";

    /*Common Tags between Channel and Item */
    public static final String TITLE = "title";
    public static final String LINK = "link";
    public static final String DESCRIPTION = "description";

    public static final String CATEGORIES = "category";
    public static final String PUB_DATE = "pubDate";


    /*Tags for Channel*/
    public static final String LANGUAGE = "language";
    public static final String COPYRIGHT = "copyright";

    public static final String MANAGING_EDITOR = "managingEditor";
    public static final String WEBMASTER = "webMaster";

    public static final String LAST_BUILD_DATE = "lastBuildDate";

    public static final String GENERATOR = "generator";

    public static final String DOCS = "docs";

    public static final String TTL = "tt;";

    public static final String IMAGE = "image";


    /*Tags for Item*/
    public static final String AUTHOR = "author";
    public static final String COMMENTS = "comments";
    public static final String ENCLOSURE = "enclosure";

    public static final String GUID = "guid";

    /*Sub Tags for Image*/
    public static final String URL_TAG = "url";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";

    /*Attributes for Tags*/

    /*Category*/
    public static final String DOMAIN = "domain";

    /*Enclosure*/
    public static final String LENGTH = "length";
    public static final String TYPE = "type";




    private static final String TAG = "RSSParserHandler";
    private ArrayList<Channel> parsedPodcast;
    private boolean doneParsing = false;

    private StringBuilder builder;
    private Item currentItem = null;
    private Channel currentChannel = null;
    private Image currentImage = null;

    private String categoryDomain = null;

    private String enclosureURL;
    private String enclosureLength;
    private String enclosureType;


    private static final SimpleDateFormat rfcFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz",  Locale.ENGLISH);

    public RSSParserHandler() {
        builder = new StringBuilder();
    }

    @Override
    public void startDocument() throws SAXException {
        parsedPodcast = new ArrayList<Channel>();
        doneParsing = false;
        super.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        doneParsing = true;
        bless(parsedPodcast);
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        super.startElement(uri, localName, qName, attributes);

        if (qName.equalsIgnoreCase(CHANNEL)){
            this.currentChannel = new Channel();
        }else if (localName.equalsIgnoreCase(ITEM)){
            this.currentItem = new Item();
        }else if (localName.equalsIgnoreCase(IMAGE)){
            this.currentImage = new Image();
        }

        if (qName.equalsIgnoreCase(CATEGORIES)){
            categoryDomain = attributes.getValue(DOMAIN);
        }

        if (qName.equalsIgnoreCase(ENCLOSURE)){
            enclosureURL = attributes.getValue(URL_TAG);
            enclosureLength = attributes.getValue(LENGTH);
            enclosureType = attributes.getValue(TYPE);
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        super.endElement(uri, localName, qName);

        String content = builder.toString().trim();

        if (qName.equalsIgnoreCase(ITEM)){
            currentChannel.addItem(currentItem);
            this.currentItem = null;
        }else if (qName.equalsIgnoreCase(CHANNEL)){
            parsedPodcast.add(currentChannel);
            this.currentChannel = null;
        }

        if (this.currentChannel != null && this.currentItem == null){
            /*For Channel Tags*/

            /*Parse Common tags first*/
            if (qName.equalsIgnoreCase(TITLE)){
                currentChannel.setTitle(content);
            } else if (qName.equalsIgnoreCase(LINK)){
                try {
                    currentChannel.setLink(new URL(content));
                } catch (MalformedURLException e) {
                    logParseError("URL", content, LINK);
                    //e.printStackTrace();
                }
            } else if (qName.equalsIgnoreCase(DESCRIPTION)){
                currentChannel.setDescription(content);
            } else if (qName.equalsIgnoreCase(PUB_DATE)){
                try {
                    currentChannel.setPubDate(rfcFormat.parse(content));
                } catch (ParseException e) {
                    logParseError("Date", content,PUB_DATE);
                    //e.printStackTrace();
                }
            }else if (qName.equalsIgnoreCase(CATEGORIES)){
                currentChannel.addCategory(new Category(categoryDomain,content));
                categoryDomain = null;
            }


             /*Parse Channel specific tags */

            /*
                public static final String LANGUAGE = "language";
                public static final String COPYRIGHT = "copyright";

                public static final String MANAGING_EDITOR = "managingEditor";
                public static final String WEBMASTER = "webMaster";

                public static final String LAST_BUILD_DATE = "lastBuildDate";

                public static final String GENERATOR = "generator";

                public static final String DOCS = "docs";

                public static final String TTL = "tt;";

                public static final String IMAGE = "image";
             */

            if (qName.equalsIgnoreCase(LANGUAGE)){
                currentChannel.setLanguage(content);
            } else if (qName.equalsIgnoreCase(COPYRIGHT)){
                currentChannel.setCopyright(content);
            } else if (qName.equalsIgnoreCase(MANAGING_EDITOR)){
                currentChannel.setManagingEditor(content);
            } else if (qName.equalsIgnoreCase(WEBMASTER)){
                currentChannel.setWebMaster(content);
            } else if (qName.equalsIgnoreCase(LAST_BUILD_DATE)){
                try {
                    currentChannel.setLastBuildDate(rfcFormat.parse(content));
                } catch (ParseException e) {
                    logParseError("Date", content,LAST_BUILD_DATE);
                    //e.printStackTrace();
                }
            } else if (qName.equalsIgnoreCase(GENERATOR)){
                currentChannel.setGenerator(content);
            } else if (qName.equalsIgnoreCase(DOCS)){
                try {
                    currentChannel.setDocs(new URL(content));
                } catch (MalformedURLException e) {
                    logParseError("URL", content,DOCS);
                    //e.printStackTrace();
                }
            } else if (qName.equalsIgnoreCase(TTL)){
                currentChannel.setTTL(Integer.parseInt(content));
            } else if (qName.equalsIgnoreCase(IMAGE)){
                currentChannel.setImage(this.currentImage);
                this.currentImage = null;
            }


            /*For Sub tags of Image tag*/
            if (this.currentImage != null){
                if (qName.equalsIgnoreCase(LINK)){
                    try {
                        currentImage.setLink(new URL(content));
                    } catch (MalformedURLException e) {
                        logParseError("URL", content,LINK);
                        //e.printStackTrace();
                    }
                } else if (qName.equalsIgnoreCase(URL_TAG)){
                    try {
                        currentImage.setURL(new URL(content));
                    } catch (MalformedURLException e) {
                        logParseError("URL", content,URL_TAG);
                        //e.printStackTrace();
                    }
                } else if (qName.equalsIgnoreCase(TITLE)){
                    currentImage.setTitle(content);
                } else if (qName.equalsIgnoreCase(WIDTH)){
                    currentImage.setWidth(Integer.parseInt(content));
                } else if (qName.equalsIgnoreCase(HEIGHT)){
                    currentImage.setHeight(Integer.parseInt(content));
                }
            }



        }else if (this.currentItem != null){
            /* For Item Tags*/

            /*Parse Common tags first*/
            if (qName.equalsIgnoreCase(TITLE)){
                currentItem.setTitle(content);
            } else if (qName.equalsIgnoreCase(LINK)){
                try {
                    currentItem.setLink(new URL(content));
                } catch (MalformedURLException e) {
                    logParseError("URL", content,LINK);
                    //e.printStackTrace();
                }
            } else if (qName.equalsIgnoreCase(DESCRIPTION)){
                currentItem.setDescription(content);
            } else if (qName.equalsIgnoreCase(PUB_DATE)){
                try {
                    currentItem.setPubDate(rfcFormat.parse(content));
                } catch (ParseException e) {
                    logParseError("Date", content,PUB_DATE);
                    //e.printStackTrace();
                }
            }else if (qName.equalsIgnoreCase(CATEGORIES)){
                currentItem.addCategory(new Category(categoryDomain,content));
                categoryDomain = null;
            }


            /*Parse Item specific tags */
            /*
                public static final String AUTHOR = "author";
                public static final String COMMENTS = "comments";
                public static final String ENCLOSURE = "enclosure";

                public static final String GUID = "guid";
             */

            if (qName.equalsIgnoreCase(AUTHOR)){
                currentItem.setAuthor(content);
            } else if (qName.equalsIgnoreCase(COMMENTS)){
                try {
                    currentItem.setComments(new URL(content));
                } catch (MalformedURLException e) {
                    //e.printStackTrace();
                }
            } else if (qName.equalsIgnoreCase(ENCLOSURE)){
                URL url = null;
                int length = 0;

                if (enclosureURL != null){
                    try {
                        url = new URL(enclosureURL);
                    } catch (MalformedURLException e) {
                        logParseError("URL", content,ENCLOSURE);
                        //e.printStackTrace();
                    }
                }

                if (enclosureLength != null){
                    try{
                        length = Integer.parseInt(enclosureLength);
                    }catch (NumberFormatException e){
                        logParseError("Number", content,ENCLOSURE);
                        //e.printStackTrace();
                    }
                }

                currentItem.setEnclosure(new Enclosure(url,length,enclosureType));

                enclosureURL = null;
                enclosureLength = null;
                enclosureType = null;

            } else if (qName.equalsIgnoreCase(GUID)){
                currentItem.setGUID(content);
            }
        }

        builder.setLength(0);
    }

    private void logParseError(String type, String value, String tag) {

        if (currentItem == null)
            Log.d(TAG, "Unable to parse " + type + ": " + value + " inside the " + tag + " tag in channel " + currentChannel.getTitle());
        else
            Log.d(TAG, "Unable to parse " + type + ": " + value + " inside the " + tag + " tag in item " + currentItem.getTitle() + " in the channel " + currentChannel.getTitle());
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        builder.append(ch, start, length);
    }


    public ArrayList<Channel> getParsedPodcast() {
        if (!doneParsing)
            Log.w(TAG,"Parsing not done");

        return parsedPodcast;
    }


    private void bless(ArrayList<Channel> parsedPodcast) {
        /*Deals with various rules in the RSS spec, esp cross population.*/
    }
}
