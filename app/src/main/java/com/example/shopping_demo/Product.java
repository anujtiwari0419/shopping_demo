package com.example.shopping_demo;

public class Product {

    private int id;
    private String productName;
    private String productCompany;
    private int productPrice;
    private int shippingCharge;
    private String image;

    public Product(int id, String image) {
        this.id = id;
        this.image = image;
    }

    public Product(int id, String productName, String productCompany, int productPrice, int shippingCharge, String image) {
        this.id = id;
        this.productName = productName;
        this.productCompany = productCompany;
        this.productPrice = productPrice;
        this.shippingCharge = shippingCharge;
        this.image = image;
    }

    public int getId() {
        return id;
    }


    public String getProductName() {
        return productName;
    }


    public String getProductCompany() {
        return productCompany;
    }


    public int getProductPrice() {
        return productPrice;
    }

    public int getShippingCharge() {
        return shippingCharge;
    }


    public String getImage() {
        return image;
    }


}
