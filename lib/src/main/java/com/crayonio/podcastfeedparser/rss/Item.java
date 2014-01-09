package com.crayonio.podcastfeedparser.rss;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by chinmay on 9/1/14.
 */


/* Following attributes are not implemented.
 *
 * source	The RSS channel that the item came from. More.
 *
 */

public class Item {

    protected String title;
    protected URL link;
    protected String description;

    protected String author;
    protected ArrayList<Category> categories;

    protected URL comments;

    protected Enclosure enclosure;

    protected String guid;

    protected Date pubDate;

    public Item() {
        this.categories = new ArrayList<Category>();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public URL getComments() {
        return comments;
    }

    public void setComments(URL comments) {
        this.comments = comments;
    }

    public Enclosure getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
    }

    public String getGUID() {
        return guid;
    }

    public void setGUID(String guid) {
        this.guid = guid;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }
}
