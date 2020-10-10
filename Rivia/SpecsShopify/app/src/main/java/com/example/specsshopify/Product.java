package com.example.specsshopify;

public class Product {
    String productId, productName, productQuantity;

    public Product() {
        this.productId = "null";
        this.productName = "null";
        this.productQuantity = "null";
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }
}
