package com.promobitech.nytimesapidemo.room.tables;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Movie {
    @NonNull
    @PrimaryKey()
    private String headline;
    private Multimedia multimedia;
    private String byline;
    private Link link;
    private String date_updated;
    private String display_title;
    private String opening_date;
    private String mpaa_rating;
    private String publication_date;
    private String summary_short;
    private String critics_pick;

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public Multimedia getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(Multimedia multimedia) {
        this.multimedia = multimedia;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public String getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(String date_updated) {
        this.date_updated = date_updated;
    }

    public String getDisplay_title() {
        return display_title;
    }

    public void setDisplay_title(String display_title) {
        this.display_title = display_title;
    }

    public String getOpening_date() {
        return opening_date;
    }

    public void setOpening_date(String opening_date) {
        this.opening_date = opening_date;
    }

    public String getMpaa_rating() {
        return mpaa_rating;
    }

    public void setMpaa_rating(String mpaa_rating) {
        this.mpaa_rating = mpaa_rating;
    }

    public String getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(String publication_date) {
        this.publication_date = publication_date;
    }

    public String getSummary_short() {
        return summary_short;
    }

    public void setSummary_short(String summary_short) {
        this.summary_short = summary_short;
    }

    public String getCritics_pick() {
        return critics_pick;
    }

    public void setCritics_pick(String critics_pick) {
        this.critics_pick = critics_pick;
    }

    @Override
    public String toString() {
        return "ClassPojo [headline = " + headline + ", multimedia = " + multimedia + ", byline = " + byline + ", link = " + link + ", date_updated = " + date_updated + ", display_title = " + display_title + ", opening_date = " + opening_date + ", mpaa_rating = " + mpaa_rating + ", publication_date = " + publication_date + ", summary_short = " + summary_short + ", critics_pick = " + critics_pick + "]";
    }
}