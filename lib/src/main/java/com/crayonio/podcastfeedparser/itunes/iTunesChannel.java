package com.crayonio.podcastfeedparser.itunes;

import com.crayonio.podcastfeedparser.rss.Channel;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by chinmay on 9/1/14.
 */
public class iTunesChannel extends Channel {


    private String iTunesAuthor;

    private boolean iTunesBlock;

    private ArrayList<iTunesCategory> iTunesCategories;

    private URL iTunesImage;

    private boolean iTunesExplicit;
    private boolean iTunesComplete;

    private String iTunesKeywords;

    private URL iTunesNewFeedURL;

    private String iTunesOwner;

    private String iTunesSubtitle;
    private String iTunesSummary;

}
