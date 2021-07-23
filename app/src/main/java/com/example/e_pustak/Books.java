package com.example.e_pustak;

import java.util.ArrayList;

public class Books {
    String title;
    ArrayList<String> authors;
    String publisher;
    String publishedDate;
    String description;
    String thumbnail;
    String previewLink;
    String infoLink;
    String buyLink;
    String price;

    public Books(String title, ArrayList<String> authors, String publisher, String publishedDate,
                 String description, String thumbnail, String previewLink, String infoLink, String buyLink, String price) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.thumbnail = thumbnail;
        this.previewLink = previewLink;
        this.infoLink = infoLink;
        this.buyLink = buyLink;
        this.price=price;
    }

    public Books() {
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public String getPrice() {
        return price;
    }
}
