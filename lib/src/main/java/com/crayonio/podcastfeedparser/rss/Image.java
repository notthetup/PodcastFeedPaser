package com.crayonio.podcastfeedparser.rss;

import java.net.URL;

/**
 * Created by chinmay on 9/1/14.
 */
public class Image {

    private URL url;
    private String title;
    private URL link;
    private int width = 88;
    private int height = 31;

    public Image() {

    }


    public URL getURL() {
        return url;
    }

    public void setURL(URL url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
