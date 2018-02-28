package com.ejemplo.recyclerviewcv1;


public class DataProvider {
    private String title;
    private String shortdesc;
    private int image;
    private int color;


    public DataProvider(String title, String shortdesc, int image, int color) {
        this.title = title;
        this.shortdesc = shortdesc;
        this.image = image;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public String getShortdesc() {
        return shortdesc;
    }


    public int getImage() {
        return image;
    }

    public int getColor() { return color;}


}
