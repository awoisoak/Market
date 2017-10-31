package com.awoisoak.market.data;


/**
 * Product Object
 */

public class Product {

    public final static String STATUS_SOLD_OUT = "sold_out";
    public final static String STATUS_ON_SALE = "on_sale";

    private String id;
    private String name;
    private String status;
    private int numLikes;
    private int numComments;
    private int price;
    private String photo;


    public Product(String id, String name, String status, int numLikes, int numComments, int price,
            String photo) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.numLikes = numLikes;
        this.numComments = numComments;
        this.price = price;
        this.photo = photo;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public int getNumComments() {
        return numComments;
    }

    public int getPrice() {
        return price;
    }

    public String getPhoto() {
        return photo;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Product)) {
            return false;
        }
        Product other = (Product) o;
        return id.equals(other.id);
    }
}
