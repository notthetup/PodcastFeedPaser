package com.crayonio.podcastfeedparser.rss;

import java.net.URL;

/**
 * Created by chinmay on 9/1/14.
 */
public class Enclosure {
    private URL url;
    private int length;
    private String mimetype;

    public Enclosure(URL url, int length, String mimetype) {
        this.url = url;
        this.length = length;
        this.mimetype = mimetype;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }
}
