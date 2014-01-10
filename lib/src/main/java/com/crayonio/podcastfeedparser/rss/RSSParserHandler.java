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
        if (localName.equalsIgnoreCase(CHANNEL)){
            this.currentChannel = new Channel();
        }else if (localName.equalsIgnoreCase(ITEM)){
            this.currentItem = new Item();
        }else if (localName.equalsIgnoreCase(IMAGE)){
            this.currentImage = new Image();
        }

        if (localName.equalsIgnoreCase(CATEGORIES)){
            categoryDomain = attributes.getValue(DOMAIN);
        }

        if (localName.equalsIgnoreCase(ENCLOSURE)){
            enclosureURL = attributes.getValue(URL_TAG);
            enclosureLength = attributes.getValue(LENGTH);
            enclosureType = attributes.getValue(TYPE);
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        String content = builder.toString().trim();

        if (localName.equalsIgnoreCase(ITEM)){
            currentChannel.addItem(currentItem);
            this.currentItem = null;
        }else if (localName.equalsIgnoreCase(CHANNEL)){
            parsedPodcast.add(currentChannel);
            this.currentChannel = null;
        }

        if (this.currentChannel != null && this.currentItem == null){
            /*For Channel Tags*/

            /*Parse Common tags first*/
            if (localName.equalsIgnoreCase(TITLE)){
                currentChannel.setTitle(content);
            } else if (localName.equalsIgnoreCase(LINK)){
                try {
                    currentChannel.setLink(new URL(content));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else if (localName.equalsIgnoreCase(DESCRIPTION)){
                currentChannel.setDescription(content);
            } else if (localName.equalsIgnoreCase(PUB_DATE)){
                try {
                    currentChannel.setPubDate(rfcFormat.parse(content));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else if (localName.equalsIgnoreCase(CATEGORIES)){
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

            if (localName.equalsIgnoreCase(LANGUAGE)){
                currentChannel.setLanguage(content);
            } else if (localName.equalsIgnoreCase(COPYRIGHT)){
                currentChannel.setCopyright(content);
            } else if (localName.equalsIgnoreCase(MANAGING_EDITOR)){
                currentChannel.setManagingEditor(content);
            } else if (localName.equalsIgnoreCase(WEBMASTER)){
                currentChannel.setWebMaster(content);
            } else if (localName.equalsIgnoreCase(LAST_BUILD_DATE)){
                try {
                    currentChannel.setLastBuildDate(rfcFormat.parse(content));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (localName.equalsIgnoreCase(GENERATOR)){
                currentChannel.setGenerator(content);
            } else if (localName.equalsIgnoreCase(DOCS)){
                try {
                    currentChannel.setDocs(new URL(content));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else if (localName.equalsIgnoreCase(TTL)){
                currentChannel.setTTL(Integer.parseInt(content));
            } else if (localName.equalsIgnoreCase(IMAGE)){
                currentChannel.setImage(this.currentImage);
                this.currentImage = null;
            }


            /*For Sub tags of Image tag*/
            if (this.currentImage != null){
                if (localName.equalsIgnoreCase(LINK)){
                    try {
                        currentImage.setLink(new URL(content));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                } else if (localName.equalsIgnoreCase(URL_TAG)){
                    try {
                        currentImage.setURL(new URL(content));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                } else if (localName.equalsIgnoreCase(TITLE)){
                    currentImage.setTitle(content);
                } else if (localName.equalsIgnoreCase(WIDTH)){
                    currentImage.setWidth(Integer.parseInt(content));
                } else if (localName.equalsIgnoreCase(HEIGHT)){
                    currentImage.setHeight(Integer.parseInt(content));
                }
            }



        }else if (this.currentItem != null){
            /* For Item Tags*/

            /*Parse Common tags first*/
            if (localName.equalsIgnoreCase(TITLE)){
                currentItem.setTitle(content);
            } else if (localName.equalsIgnoreCase(LINK)){
                try {
                    currentItem.setLink(new URL(content));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else if (localName.equalsIgnoreCase(DESCRIPTION)){
                currentItem.setDescription(content);
            } else if (localName.equalsIgnoreCase(PUB_DATE)){
                try {
                    currentItem.setPubDate(rfcFormat.parse(content));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else if (localName.equalsIgnoreCase(CATEGORIES)){
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

            if (localName.equalsIgnoreCase(AUTHOR)){
                currentItem.setAuthor(content);
            } else if (localName.equalsIgnoreCase(COMMENTS)){
                try {
                    currentItem.setComments(new URL(content));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else if (localName.equalsIgnoreCase(ENCLOSURE)){
                URL url = null;
                int length = 0;

                if (enclosureURL != null){
                    try {
                        url = new URL(enclosureURL);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }

                if (enclosureLength != null)
                    length = Integer.parseInt(enclosureLength);

                currentItem.setEnclosure(new Enclosure(url,length,enclosureType));
            } else if (localName.equalsIgnoreCase(GUID)){
                currentItem.setGUID(content);
            }
        }

        builder.setLength(0);
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
