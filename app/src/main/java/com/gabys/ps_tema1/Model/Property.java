package com.gabys.ps_tema1.Model;

import java.util.Objects;

public class Property {

    private int id;
    private String title;
    private String location;
    private int roomsNo;
    private String type;
    private Float price;
    private boolean isAvailable;
    private String imageURL;

    public Property() {}

    public Property(String title, String location, int roomsNo, String type, Float price, boolean isAvailable, String imageURL) {
        this.title = title;
        this.location = location;
        this.roomsNo = roomsNo;
        this.type = type;
        this.price = price;
        this.isAvailable = isAvailable;
        this.imageURL = imageURL;
    }

    public Property(int id, String title, String location, int rooms, String type, float price, boolean b, String imageURL) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.roomsNo = rooms;
        this.type = type;
        this.price = price;
        this.isAvailable = b;
        this.imageURL = imageURL;
    }

    public Property(String title, String location, int rooms, String type, float price, boolean isAvailable) {
        this.title = title;
        this.location = location;
        this.roomsNo = rooms;
        this.type = type;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getRoomsNo() {
        return roomsNo;
    }

    public void setRoomsNo(int roomsNo) {
        this.roomsNo = roomsNo;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return roomsNo == property.roomsNo && isAvailable == property.isAvailable && title.equals(property.title) && location.equals(property.location) && type.equals(property.type) && price.equals(property.price) && Objects.equals(imageURL, property.imageURL);
    }
}
