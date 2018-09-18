package com.promobitech.nytimesapidemo.room.tables;

public class Multimedia {
    private String height;
    private String width;
    private String src;
    private String type;

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ClassPojo [height = " + height + ", width = " + width + ", src = " + src + ", type = " + type + "]";
    }
}