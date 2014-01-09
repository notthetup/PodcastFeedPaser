package com.crayonio.podcastfeedparser.itunes;

import com.crayonio.podcastfeedparser.rss.Category;

import java.util.ArrayList;

/**
 * Created by chinmay on 9/1/14.
 */
public class iTunesCategory extends Category {

    private ArrayList<iTunesCategory> subCategories;

    public iTunesCategory(String domain, String category) {
        super(domain, category);
    }
}
