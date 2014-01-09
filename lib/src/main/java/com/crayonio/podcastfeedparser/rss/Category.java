package com.crayonio.podcastfeedparser.rss;

/**
 * Created by chinmay on 9/1/14.
 */
public class Category {

    protected String domain;
    protected String category;

    public Category(String domain, String category) {
        this.domain = domain;
        this.category = category;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
