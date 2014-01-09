package com.crayonio.podcastfeedparser.rss;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by chinmay on 9/1/14.
 */


/* Following optional properties are not implemented
 *
 * cloud        Allows processes to register with a cloud to be notified of updates to the channel, implementing a lightweight publish-subscribe protocol for RSS feeds. More info here.	<cloud domain="rpc.sys.com" port="80" path="/RPC2" registerProcedure="pingMe" protocol="soap"/>
 * rating	    The PICS rating for the channel.
 * textInput	Specifies a text input box that can be displayed with the channel. More info here.
 * skipHours	A hint for aggregators telling them which hours they can skip. More info here.
 * skipDays	    A hint for aggregators telling them which days they can skip. More info here.
 *
 */

public class Channel {

    protected String title;
    protected URL link;
    protected String description;

    protected String language;
    protected String copyright;

    protected String managingEditor;
    protected String webMaster;

    protected Date pubDate;
    protected Date lastBuildDate;

    protected ArrayList<Category> categories;

    protected String generator;

    protected URL docs;

    protected int ttl;

    protected Image image;

    protected ArrayList<Item> items;

    public Channel() {
        categories = new ArrayList<Category>();
        items = new ArrayList<Item>();
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getManagingEditor() {
        return managingEditor;
    }

    public void setManagingEditor(String managingEditor) {
        this.managingEditor = managingEditor;
    }

    public String getWebMaster() {
        return webMaster;
    }

    public void setWebMaster(String webMaster) {
        this.webMaster = webMaster;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public Date getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(Date lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public URL getDocs() {
        return docs;
    }

    public void setDocs(URL docs) {
        this.docs = docs;
    }

    public int getTTL() {
        return ttl;
    }

    public void setTTL(int ttl) {
        this.ttl = ttl;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }
}
