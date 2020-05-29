package com.navjot.decemberapplication_seller.model;

public class Vegetable {

    private String name;
    private String url;
    private long price;

    public Vegetable() {

    }

    public Vegetable(String name, String url, long price) {
        this.name = name;
        this.url = url;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

}
