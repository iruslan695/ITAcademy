package com.tc.webapp.entity;

import java.io.Serializable;
import java.util.Objects;

public class Journal implements Serializable {

    private String url;
    private String title;
    private int number;
    private int price;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Journal journal = (Journal) o;
        return number == journal.number && price == journal.price && Objects.equals(url, journal.url) && Objects.equals(title, journal.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, title, number, price);
    }

    @Override
    public String toString() {
        return "Journal{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", number=" + number +
                ", price=" + price +
                '}';
    }
}
